package com.kleszcz.krzeszowski.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.utils.ShortArray;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.kleszcz.krzeszowski.*;
import com.kleszcz.krzeszowski.multiplayer.ClientHandler;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Elimas on 2015-11-20.
 * TODO add collision between players and bullets and asteroids
 * TODO add points
 */
public class GameScreen implements Screen, SendReceiveDataListener {
    private GameOptions gameOptions;
    private Rectangle map = Globals.MAP_BOUNDS;
    private Texture textureHeart = new Texture(Gdx.files.internal("heart.png"));
    private Texture textureShield = new Texture(Gdx.files.internal("shield.png"));
    private Asteroids asteroids;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private BitmapFont font;
    private OrthographicCamera camera;
    private Stage stage;
    private AsteroidsGenerator asteroidsGenerator;
    private Player player;
    private ArrayList<Player> otherPlayers = new ArrayList<>();
    private ArrayList<Shoot> shootsList;
    private ArrayList<Asteroid> asteroidsList;
    private ArrayList<FloatingScore> scoresList;
    private ReentrantLock lock = new ReentrantLock();

    private InputAdapter inputAdapter = new InputAdapter() {

        @Override
        public boolean keyDown (int keycode) {
            switch (keycode) {
                case Input.Keys.UP: player.setFlyingForward(true); break;
                case Input.Keys.LEFT: player.setRotatingLeft(true); break;
                case Input.Keys.RIGHT: player.setRotatingRight(true); break;
                case Input.Keys.SPACE: player.setFiring(true); break;
            }
            return true;
        };

        @Override
        public boolean keyUp (int keycode) {
            switch (keycode) {
                case Input.Keys.UP: player.setFlyingForward(false); break;
                case Input.Keys.LEFT: player.setRotatingLeft(false); break;
                case Input.Keys.RIGHT: player.setRotatingRight(false); break;
                case Input.Keys.SPACE: player.setFiring(false); break;
            }
            return true;
        }
    };

    public GameScreen(Asteroids asteroids, GameOptions gameOptions) {
        if (asteroids == null) throw new NullPointerException("asteroids");
        this.asteroids = asteroids;
        this.gameOptions = gameOptions;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        font = new BitmapFont(Gdx.files.internal("Arial32.fnt"));
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        /*camera = new OrthographicCamera(30, 30 * (h / w));
        camera.position.set(era.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();*/

        camera = new OrthographicCamera();
        camera.setToOrtho(false, w, h);
        //camera.zoom = 5.5f;
        FitViewport viewp = new FitViewport(w, h, camera); // change this to your needed viewport
        stage = new Stage(viewp, batch);
        asteroidsGenerator = new AsteroidsGenerator();

        player = new Player();
        player.setPosition((map.getX() + map.getWidth()) * 0.5f, (map.getY() + map.getHeight()) * 0.5f);
        player.setName("Player");
        if (gameOptions.isServer()) player.setClientId(0);
        else player.setClientId(1);
        Background bg = new Background();
        stage.addActor(bg);
        stage.addActor(player);
        Gdx.input.setInputProcessor(inputAdapter);

        shootsList = new ArrayList<>();
        asteroidsList = new ArrayList<>();
        scoresList = new ArrayList<>();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (gameOptions.isServer()) {
            for (ClientHandler clientHandler : gameOptions.getServer().getClientsList()) {
                onDataReceived(clientHandler.getLastObject());
            }
        } else {
            onDataReceived(gameOptions.getClient().getLastObject());
        }
        lock.lock();
        stage.act(delta);
        for (Player p : otherPlayers) {
            p.act(delta);
        }
        update(delta);
        clearOutside();
        camera.update();
        stage.draw();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for (Player p : otherPlayers) {
            p.draw(batch, 1);
        }
        batch.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.setColor(Color.WHITE);
        for (Asteroid asteroid : asteroidsList) {
            asteroid.update();
            shapeRenderer.polygon(asteroid.getTransformedVertices());
        }
        if (Globals.DEBUG_COLLISIONS) {
            Color previousColor = shapeRenderer.getColor();
            shapeRenderer.setColor(Color.RED);
            if (player.getPolygon() != null) shapeRenderer.polygon(player.getPolygon().getTransformedVertices());
            for (Shoot shoot : shootsList) {
                Vector2 shootStart = new Vector2();
                shootStart.x = shoot.getX() + 5 * shoot.getScaleY() * (float) Math.cos(Math.toRadians(shoot.getRotation() + 90));
                shootStart.y = shoot.getY() + 5 * shoot.getScaleY() * (float) Math.sin(Math.toRadians(shoot.getRotation() + 90));
                Vector2 shootEnd = new Vector2();
                shootEnd.x = shoot.getX();
                shootEnd.y = shoot.getY();
                shapeRenderer.line(shootEnd, shootStart);
            }
            shapeRenderer.setColor(previousColor);
        }
        shapeRenderer.end();
        //draw hud
        batch.begin();
        Vector3 playerPosition = camera.project(new Vector3(player.getX(), player.getY(), 0));
        player.drawName(batch);
        for (Player p : otherPlayers) {
            p.drawName(batch);
        }
        for (FloatingScore score : scoresList) {
            if (score.getTimeLeft() > 0) score.draw(batch);
        }
        font.draw(batch, "Score\r\n" + player.getScore(), 20, camera.viewportHeight - 20);
        font.draw(batch, String.valueOf(player.getLives()), camera.viewportWidth - 28, camera.viewportHeight - 20);
        batch.draw(textureHeart, camera.viewportWidth - 40 - textureHeart.getWidth(), camera.viewportHeight - 20 - textureHeart.getHeight() + 2, 0, 0, textureHeart.getWidth(),
                textureHeart.getHeight(), 1, 1, 0, 0, 0,
                textureHeart.getWidth(), textureHeart.getHeight(), false, false);
        if (player.isShieldEnabled()) {
            font.draw(batch, String.valueOf(Math.round(player.getShieldTimer() / 100f) / 10f), camera.viewportWidth - 50, camera.viewportHeight - 75);
            batch.draw(textureShield, camera.viewportWidth - 60 - textureShield.getWidth(), camera.viewportHeight - 30 - textureHeart.getHeight() - textureShield.getHeight() - 10, 0, 0, textureShield.getWidth(),
                    textureShield.getHeight(), 1, 1, 0, 0, 0,
                    textureShield.getWidth(), textureShield.getHeight(), false, false);
        }
        batch.end();
        lock.unlock();
    }

    private void update(float delta) {
        asteroidsGenerator.update(asteroidsList);
        //update bullets
        if (player.isFiring()) {
            if (player.canFire()) {
                player.fire();
                Shoot shoot = new Shoot(player);
                stage.addActor(shoot);
                shootsList.add(shoot);
            }
        }
        //clear bullets outside screen
        Iterator<Shoot> it = shootsList.iterator();
        while (it.hasNext()) {
            Shoot shoot = it.next();
            if (shoot.getX() + 3 * shoot.getWidth() < 0 || shoot.getX() - 3 * shoot.getWidth() > camera.viewportWidth ||
                    shoot.getY() + 3 * shoot.getHeight() < 0 || shoot.getY() - 3 * shoot.getHeight() > camera.viewportHeight) {
                stage.getActors().removeValue(shoot, false);
                it.remove();
            }
        }
        ListIterator<Asteroid> itA = asteroidsList.listIterator();
        while (itA.hasNext()) {
            Asteroid asteroid = itA.next();
            Boolean removeAsteroid = false;
            //clear asteroids outside screen
            if (asteroid.getX() + 4 * asteroid.getScaleX() < 0 || asteroid.getX() - 4 * asteroid.getScaleX() > camera.viewportWidth ||
                    asteroid.getY() + 4 * asteroid.getScaleY() < 0 || asteroid.getY() - 4 * asteroid.getScaleY() > camera.viewportHeight) {
                itA.remove();
                continue;
            }
            //check collisions asteroid - player
            if (!player.isShieldEnabled() && Vector2.dst(asteroid.getX(), asteroid.getY(), player.getX(), player.getY()) < Math.max(asteroid.getScaleX(), asteroid.getScaleY()) + Math.max(player.getWidth(), player.getHeight())) {
                float[] vertices = player.getPolygon().getTransformedVertices();
                for (int i = 0; i < vertices.length; i += 2) {
                    Vector2 v1 = new Vector2(), v2 = new Vector2();
                    v1.x = vertices[i];
                    if (i + 1 < vertices.length - 1) v1.y = vertices[i + 1];
                    if (i + 2 < vertices.length - 1) v2.x = vertices[i + 2];
                    if (i + 3 < vertices.length - 1) v2.y = vertices[i + 3];
                    if (i + 3 < vertices.length - 1) if (Intersector.intersectSegmentPolygon(v1, v2, asteroid)) {
                        player.setLives(player.getLives() - 1);
                        player.setPosition(Utils.randomRange((map.getX() + map.getWidth()) * 0.2f, (map.getX() + map.getWidth()) * 0.8f), Utils.randomRange((map.getY() + map.getHeight()) * 0.2f, (map.getY() + map.getHeight()) * 0.8f));
                        player.setShieldEnabled(true);
                        player.setShieldTimer(3000);
                    }
                }
            }
            //check collisions asteroid - bullet
            Asteroid a1 = null, a2 = null;
            Iterator<Shoot> itS = shootsList.iterator();
            while (itS.hasNext()) {
                Shoot shoot = itS.next();
                if (Vector2.dst(asteroid.getX(), asteroid.getY(), shoot.getX(), shoot.getY()) < Math.max(asteroid.getScaleX(), asteroid.getScaleY()) + Math.max(shoot.getScaleX(), shoot.getScaleY())) {
                    Vector2 shootStart = new Vector2();
                    shootStart.x = shoot.getX() + 5 * shoot.getScaleY() * (float) Math.cos(Math.toRadians(shoot.getRotation() + 90));
                    shootStart.y = shoot.getY() + 5 * shoot.getScaleY() * (float) Math.sin(Math.toRadians(shoot.getRotation() + 90));
                    Vector2 shootEnd = new Vector2();
                    shootEnd.x = shoot.getX();
                    shootEnd.y = shoot.getY();
                    if (Intersector.intersectSegmentPolygon(shootEnd, shootStart, asteroid)) {
                        if (asteroid.getScaleX() + asteroid.getScaleY() > 250) {
                            a1 = new Asteroid(asteroid);
                            a2 = new Asteroid(asteroid);
                            a1.setScale(a1.getScaleX() * 0.5f, a1.getScaleY() * 0.5f);
                            a2.setScale(a2.getScaleX() * 0.5f, a2.getScaleY() * 0.5f);
                            a1.setDirection(shoot.getRotation() + 90 + Utils.randomRange(-20, 20));
                            a2.setDirection(shoot.getRotation() - 90 + Utils.randomRange(-20, 20));
                            a1.setRotation(a1.getDirection());
                            a2.setRotation(a2.getDirection());
                        }
                        player.setScore(player.getScore() + asteroid.calcScore());
                        scoresList.add(new FloatingScore(String.valueOf(asteroid.calcScore()), shoot.getX(), shoot.getY(), 2000));
                        stage.getActors().removeValue(shoot, false);
                        itS.remove();
                        itA.remove();
                        break;
                    }
                }
            }
            if (a1 != null) itA.add(a1);
            if (a2 != null) itA.add(a2);
        }
        Iterator<FloatingScore> itFS = scoresList.iterator();
        while (itFS.hasNext()) {
            FloatingScore score = itFS.next();
            if (score.getTimeLeft() > 0) score.update(delta);
            else itFS.remove();
        }
    }

    private void clearOutside() {


    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public Object sendData() {
        lock.lock();
        if (gameOptions.isServer()) {
            GameDataServer data = new GameDataServer();
            ArrayList<Player> allPlayers = new ArrayList<>();
            allPlayers.add(player);
            allPlayers.addAll(otherPlayers);
            data.setAllPlayers(allPlayers);
            data.setAsteroids(asteroidsList);
            lock.unlock();
            return data;
        } else {
            GameDataClient data = new GameDataClient();
            data.setPlayer(player);
            lock.unlock();
            return data;
        }
    }

    @Override
    public void onDataReceived(Object object) {
        lock.lock();
        if (gameOptions.isServer()) {
            if (object instanceof GameDataClient) {
                GameDataClient data = (GameDataClient) object;
                boolean playerFound = false;
                for (int i = 0; i < otherPlayers.size(); i++) {
                    if (otherPlayers.get(i).getClientId() == data.getPlayer().getClientId()) {
                        otherPlayers.get(i).copyFrom(data.getPlayer());
                        playerFound = true;
                    }
                }
                if (!playerFound) {
                    Player newPlayer = new Player();
                    newPlayer.copyFrom(data.getPlayer());
                    otherPlayers.add(newPlayer);
                }
            }
        } else {
            if (object instanceof GameDataServer) {
                GameDataServer data = (GameDataServer) object;
                asteroidsList = data.getAsteroids();
                ArrayList<Player> allPlayers = data.getAllPlayers();
                for (Iterator<Player> iterator = allPlayers.iterator(); iterator.hasNext();) {
                    Player p = iterator.next();
                    if (player.getClientId() == p.getClientId()) {
                        //player.copyFrom(p);
                        iterator.remove();
                        break;
                    }
                }
                for (Player p : allPlayers) {
                    boolean playerFound = false;
                    for (Player p2 : otherPlayers) {
                        if (p.getClientId() == p2.getClientId()) {
                            p2.copyFrom(p);
                            playerFound = true;
                            break;
                        }
                    }
                    if (!playerFound) {
                        Player newPlayer = new Player();
                        newPlayer.copyFrom(p);
                        otherPlayers.add(newPlayer);
                    }
                }
            }
        }
        lock.unlock();
        /*lock.lock();
        if (gameOptions.isServer()) {
            if (object instanceof GameDataClient) {
                GameDataClient data = (GameDataClient) object;
                boolean playerFound = false;
                for (int i = 0; i < otherPlayers.size(); i++) {
                    if (otherPlayers.get(i).getClientId() == data.getPlayer().getClientId()) {
                        otherPlayers.get(i).copyFrom(data.getPlayer());
                        playerFound = true;
                    }
                }
                if (!playerFound) {
                    Gdx.app.postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            Player newPlayer = new Player();
                            newPlayer.copyFrom(data.getPlayer());
                            otherPlayers.add(newPlayer);
                        }
                    });
                }
            }
        } else {
            if (object instanceof GameDataServer) {
                GameDataServer data = (GameDataServer) object;
                asteroidsList = data.getAsteroids();
                ArrayList<Player> allPlayers = data.getAllPlayers();
                for (Iterator<Player> iterator = allPlayers.iterator(); iterator.hasNext();) {
                    Player p = iterator.next();
                    if (player.getClientId() == p.getClientId()) {
                        //player.copyFrom(p);
                        iterator.remove();
                        break;
                    }
                }
                for (Player p : allPlayers) {
                    boolean playerFound = false;
                    for (Player p2 : otherPlayers) {
                        if (p.getClientId() == p2.getClientId()) {
                            p2.copyFrom(p);
                            playerFound = true;
                            break;
                        }
                    }
                    if (!playerFound) {
                        Gdx.app.postRunnable(new Runnable() {
                            @Override
                            public void run() {
                                Player newPlayer = new Player();
                                newPlayer.copyFrom(p);
                                otherPlayers.add(newPlayer);
                            }
                        });
                    }
                }
            }
        }
        lock.unlock();*/
    }
}
