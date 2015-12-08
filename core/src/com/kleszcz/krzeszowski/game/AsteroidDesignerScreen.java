package com.kleszcz.krzeszowski.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.kleszcz.krzeszowski.Asteroids;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Elimas on 2015-11-20.
 */
public class AsteroidDesignerScreen implements Screen {
    private Texture texture = new Texture(Gdx.files.internal("player2.png"));
    private Asteroids asteroids;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private ArrayList<Vector2> listPoints = new ArrayList<>();
    OrthographicCamera cam;

    private InputAdapter inputAdapter = new InputAdapter() {

        @Override
        public boolean touchUp (int screenX, int screenY, int pointer, int button) {
            Vector3 v3 = new Vector3();
            v3.set(screenX, screenY, 0);
            cam.unproject(v3);
            Vector2 v = new Vector2(v3.x, v3.y);
            listPoints.add(v);
            System.out.println("Added point: " + v);
            return true;
        }

        @Override
        public boolean keyDown (int keycode) {
            switch (keycode) {
                case Input.Keys.SPACE:
                    float[] points = new float[listPoints.size() * 2 + 2];
                    float[] pointsNormalized = new float[listPoints.size() * 2 + 2];

                    int i = 0;
                    Vector2 min = new Vector2(), max = new Vector2();
                    for (Vector2 v : listPoints) {
                        min.x = Math.min(min.x, v.x);
                        min.y = Math.min(min.y, v.y);
                        max.x = Math.max(max.x, v.x);
                        max.y = Math.max(max.y, v.y);
                        points[i] = v.x;
                        i++;
                        points[i] = v.y;
                        i++;
                    }
                    points[i] = points[0];
                    points[i + 1] = points[1];

                    i = 0;
                    for (Vector2 v : listPoints) {
                        min.x = Math.min(min.x, v.x);
                        min.y = Math.min(min.y, v.y);
                        max.x = Math.max(max.x, v.x);
                        max.y = Math.max(max.y, v.y);

                        pointsNormalized[i] = (v.x - min.x) / Math.max(max.x, max.y);
                        i++;
                        pointsNormalized[i] = (v.y - min.y) / Math.max(max.x, max.y);
                        i++;
                    }
                    pointsNormalized[i] = pointsNormalized[0];
                    pointsNormalized[i + 1] = pointsNormalized[1];

                    String vertices = "";
                    String verticesNormalized = "";
                    for (float point : points) {
                        vertices += point + "f, ";
                    }
                    for (float point : pointsNormalized) {
                        verticesNormalized += point + "f, ";
                    }
                    vertices = vertices.substring(0, vertices.length() - 2);
                    verticesNormalized = verticesNormalized.substring(0, verticesNormalized.length() - 2);
                    System.out.println(vertices);
                    System.out.println(verticesNormalized);
                    break;
            }
            return true;
        }
    };

    public AsteroidDesignerScreen(Asteroids asteroids) {
        if (asteroids == null) throw new NullPointerException("asteroids");
        this.asteroids = asteroids;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        Gdx.input.setInputProcessor(inputAdapter);

        cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.x = Gdx.graphics.getWidth()/2;
        cam.position.y = Gdx.graphics.getHeight()/2;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        cam.update();
        batch.begin();
        batch.setProjectionMatrix(cam.combined);
        batch.draw(texture, 0, 0, 0, 0, texture.getWidth() * 2,
                texture.getHeight() * 2, 2, 2, 0, 0, 0,
                texture.getWidth(), texture.getHeight(), false, false);
        batch.end();

        float[] points = new float[listPoints.size() * 2 + 2];
        int i = 0;
        for (Vector2 v : listPoints) {
            points[i] = v.x;
            i++;
            points[i] = v.y;
            i++;
        }
        points[i] = points[0];
        points[i + 1] = points[1];
        if (listPoints.size() > 0) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.YELLOW);
            shapeRenderer.setProjectionMatrix(cam.combined);
            for (Vector2 v : listPoints) {
                shapeRenderer.circle(v.x, v.y, 5);
            }
            shapeRenderer.end();
        }
        if (listPoints.size() >= 3) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setProjectionMatrix(cam.combined);
            shapeRenderer.setColor(Color.RED);
            shapeRenderer.polygon(points);
            shapeRenderer.end();
        }
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
}
