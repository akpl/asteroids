package com.kleszcz.krzeszowski;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Elimas on 2015-11-20.
 */
public class Utils {
    public static float normalizeDegrees(float degreesToNormalize) {
        float degrees = degreesToNormalize % 360;
        if (degreesToNormalize < 0) degrees += 360;
        return degrees;
    }

    public static int randomRange(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public static float randomRange(float min, float max) {
        return ThreadLocalRandom.current().nextFloat() * (max - min) + min;
    }

    public static float clipToRange(float value, float min, float max) {
        if (value < min) return min;
        else if (value > max) return max;
        else return value;
    }
}
