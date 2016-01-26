package com.kleszcz.krzeszowski.game;

import com.badlogic.gdx.math.Rectangle;
import com.kleszcz.krzeszowski.Globals;
import com.kleszcz.krzeszowski.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Elimas on 2015-11-21.
 */
public class AsteroidsGenerator {
    public static List<float []> asteroidsTemplates = Arrays.asList(
            new float [] { 0.5314401f, 0.014198781f, 0.72008103f, 0.09330634f, 0.8377281f, 0.07099391f, 0.94523317f, 0.28194726f, 0.79310334f, 0.35091275f, 0.90466523f, 0.5496957f, 0.740365f, 0.75862074f, 0.4401622f, 0.7931034f, 0.33874235f, 1.0f, 0.25152126f, 0.939148f, 0.25557807f, 0.53549695f, 0.29006082f, 0.35699794f, 0.46855977f, 0.18255576f, 0.5314401f, 0.014198781f },
            new float [] { 0.5151515f, 0.06666667f, 0.8646465f, 0.07878788f, 1.0f, 0.2828283f, 0.72121215f, 0.48686874f, 0.85050505f, 0.6464647f, 0.8909091f, 0.8181819f, 0.85858583f, 0.95959604f, 0.6727273f, 0.96565664f, 0.5272727f, 0.9373739f, 0.42828283f, 0.78989905f, 0.22626263f, 0.77777785f, 0.21010101f, 0.66060615f, 0.062626265f, 0.5171718f, 0.13939394f, 0.3737374f, 0.5151515f, 0.06666667f },
            new float [] { 0.06811594f, 0.28115943f, 0.26231885f, 0.09565217f, 0.53913045f, 0.050724637f, 0.8f, 0.17246379f, 0.9347826f, 0.4f, 0.842029f, 0.48115948f, 0.7478261f, 0.37536237f, 0.6942029f, 0.46956527f, 0.78695655f, 0.48550728f, 0.9057971f, 0.726087f, 0.6652174f, 0.8115943f, 0.65652174f, 0.9637682f, 0.49565217f, 1.0f, 0.46666667f, 0.94202906f, 0.40434784f, 0.91304356f, 0.2652174f, 0.9550726f, 0.17391305f, 0.8159421f, 0.055072464f, 0.7492754f, 0.12608695f, 0.7115942f, 0.21594203f, 0.40579715f, 0.06811594f, 0.28115943f }
    );
    private Rectangle map = Globals.MAP_BOUNDS;
    private int generateIntervalMin = 100;
    private int generateIntervalMax = 250;
    private int generateTimer = 0;
    private float scaleSize = 1;
    private float scaleSpeed = 1;

    public AsteroidsGenerator() {
        //generateTimer = Utils.randomRange(generateIntervalMin, generateIntervalMax);
    }

    public void update(ArrayList<Asteroid> asteroidsList) {
        if (generateTimer == 0) {
            generateTimer = Utils.randomRange(generateIntervalMin, generateIntervalMax);
            asteroidsList.add(generate());
            if (generateIntervalMin > 25) generateIntervalMin -= 1;
            if (generateIntervalMax > 50) generateIntervalMax -= 2;
            if (scaleSize < 1.8f) scaleSize += 0.01f;
            if (scaleSpeed < 1.8f) scaleSpeed += 0.01f;
            System.out.println("scaleSize: " + scaleSize + " scaleSpeed: " + scaleSpeed + " generateIMin: " + generateIntervalMin + " g Max: " + generateIntervalMax);
        }
        generateTimer--;
    }

    private Asteroid generate() {
        Asteroid asteroid = new Asteroid();
        asteroid.setVertices(asteroidsTemplates.get(Utils.randomRange(0, asteroidsTemplates.size() - 1)));
        asteroid.setOrigin(0.5f, 0.5f);
        float scaleX = Utils.randomRange(100, 300) * scaleSize;
        float scaleY = Utils.randomRange(100, 300) * scaleSize;
        if (ThreadLocalRandom.current().nextBoolean()) asteroid.setScale(scaleX, scaleY);
        else asteroid.setScale(scaleX, scaleX);
        int side = Utils.randomRange(1, 4);
        float posX = 0, posY = 0, direction = 0;
        if (side == 1) {
            //right side
            posX = map.x + map.width + scaleX;
            posY = Utils.randomRange(map.y, map.y + map.height);
            direction = Utils.randomRange(10, 170);
        }
        else if (side == 2) {
            //bottom
            posX = Utils.randomRange(map.x, map.x + map.width);
            posY = map.y - scaleY;
            direction = Utils.randomRange(-80, 80);
        }
        else if (side == 3) {
            //left
            posX = map.x - scaleX;
            posY = Utils.randomRange(map.y, map.y + map.height);
            direction = Utils.randomRange(190, 350);
        }
        else if (side == 4) {
            //top
            posX = Utils.randomRange(map.x, map.x + map.width);
            posY = map.y + map.width + scaleY;
            direction = Utils.randomRange(100, 260);
        }
        asteroid.setPosition(posX, posY);
        asteroid.setRotation(Utils.randomRange(0, 359));
        asteroid.setDirection(direction);
        asteroid.setSpeed(Utils.randomRange(1f, 3f) * scaleSpeed);
        asteroid.setRotationSpeed(Utils.randomRange(0.1f, 0.2f));
        return asteroid;
    }
}
