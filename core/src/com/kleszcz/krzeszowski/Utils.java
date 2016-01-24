package com.kleszcz.krzeszowski;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;
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

    public static byte[] serializeToBytes(Object object) throws IOException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutput out = new ObjectOutputStream(bos)) {
            out.writeObject(object);
            return bos.toByteArray();
        }
    }

    public static Object deserializeFromBytes(byte[] bytes) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
             ObjectInput in = new ObjectInputStream(bis)) {
            return in.readObject();
        }
    }

    public static int tryParseInt(String value, int defaultInt) {
        int result = 0;
        try {
            result = Integer.parseInt(value);
        } catch (NumberFormatException e) {

        }
        return result;
    }

    public static byte[] toBytes(char[] chars) {
        CharBuffer charBuffer = CharBuffer.wrap(chars);
        ByteBuffer byteBuffer = Charset.forName("UTF-8").encode(charBuffer);
        byte[] bytes = Arrays.copyOfRange(byteBuffer.array(),
                byteBuffer.position(), byteBuffer.limit());
        Arrays.fill(charBuffer.array(), '\u0000'); // clear sensitive data
        Arrays.fill(byteBuffer.array(), (byte) 0); // clear sensitive data
        return bytes;
    }

    public static void copyToActor(Actor actorSource, Actor actorDest) {
        actorDest.setName(actorSource.getName());
        actorDest.setX(actorSource.getX());
        actorDest.setY(actorSource.getY());
        actorDest.setWidth(actorSource.getWidth());
        actorDest.setHeight(actorSource.getHeight());
        actorDest.setOriginX(actorSource.getOriginX());
        actorDest.setOriginY(actorSource.getOriginY());
        actorDest.setScaleX(actorSource.getScaleX());
        actorDest.setScaleY(actorSource.getScaleY());
        actorDest.setRotation(actorSource.getRotation());
        //Color color = actorSource.getColor();
        //actorDest.setColor(color);
    }
}
