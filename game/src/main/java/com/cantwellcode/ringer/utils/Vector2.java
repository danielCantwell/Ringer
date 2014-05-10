package com.cantwellcode.ringer.utils;

/**
 * Created by Daniel on 5/9/2014.
 */
public class Vector2 {

    public float x;
    public float y;

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void add(Vector2 v) {
        this.x += v.x;
        this.y += v.y;
    }
}
