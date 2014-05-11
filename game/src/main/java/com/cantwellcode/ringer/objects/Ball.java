package com.cantwellcode.ringer.objects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.cantwellcode.ringer.utils.Statics;
import com.cantwellcode.ringer.utils.Vector2;

import java.util.Random;

/**
 * Created by Daniel on 5/9/2014.
 */
public class Ball {

    // movements
    private Vector2 position;
    private Vector2 velocity;
    private Vector2 acceleration;

    private final float ACC = 0;

    // size
    private int width;
    private int height;
    private int minWidth = 35;
    private int maxWidth = 130;
    private int minHeight = 35;
    private int maxHeight = 130;

    // properties
    private int value;

    private Paint paint;

    /**
     * Constructor for a Ball object
     */
    public Ball() {
        Random randWidth = new Random();
        width = randWidth.nextInt(maxWidth - minWidth + 1) + minWidth;
        Random randHeight = new Random();
        height = randHeight.nextInt(maxHeight - minHeight + 1) + minHeight;

        Random randX = new Random();
        int x = randX.nextInt((1080 - width / 2 - 300) - (width / 2 + 80) + 1) + (width / 2 + 200);
        position = new Vector2(x, height + 50);

        velocity = new Vector2(0, 10);
        acceleration = new Vector2(0, ACC);

        value = Math.round(width / 10);

        paint = new Paint();
        paint.setColor(Color.GREEN);
    }

    /**
     * Draw the ball
     */
    public void draw(Canvas canvas) {
        canvas.drawCircle(position.x, position.y, width, paint);
    }

    /**
     * Update the state of the ball
     */
    public synchronized void update() {
        velocity.add(acceleration);
        position.add(velocity);
    }

    public boolean groundCollision() {
        if (position.y > Statics.GROUND_POS) {
            return true;
        } else {
            return false;
        }
    }

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public Vector2 getAcceleration() {
        return acceleration;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public int getValue() {
        return value;
    }
}
