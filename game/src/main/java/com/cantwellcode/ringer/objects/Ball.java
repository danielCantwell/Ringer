package com.cantwellcode.ringer.objects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.cantwellcode.ringer.utils.Statics;
import com.cantwellcode.ringer.utils.Vector2;

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
    private float width;
    private float height;

    // properties
    private int value;

    private Paint paint;

    /**
     * Constructor for a Ball object
     */
    public Ball() {
        this.width = 20;
        this.height = 30;
        position = new Vector2(200, 300);

        velocity = new Vector2(0, 0);
        acceleration = new Vector2(0, ACC);

        value = Math.round(100 / width);

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
