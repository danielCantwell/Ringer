package com.cantwellcode.ringer.objects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;

import com.cantwellcode.ringer.utils.Statics;

/**
 * Created by Daniel on 5/9/2014.
 */
public class Ring {

    // size
    private float x;
    private final float y = 300;
    private float insideRadius = 80;
    private float outsideRadius = 100;
    private final float maxRadius;
    private final float minRadius;

    private float mLastTouchX;
    private float mLastTouchY;
    private int mActivePointerId = MotionEvent.INVALID_POINTER_ID;

    private Paint paint;

    public Ring() {
        x = 500;
        maxRadius = 400;
        minRadius = 100;

        paint = new Paint();
        paint.setStrokeWidth(5);
        paint.setColor(Color.BLUE);
    }

    public void update() {
        /* Currently nothing to do in update */
    }

    public void draw(Canvas canvas) {
        canvas.drawOval(new RectF(x, y, 20, x + insideRadius), paint);
        canvas.drawOval(new RectF(x, y, 20, x + outsideRadius), paint);
    }

    public synchronized void onTouch(MotionEvent event) {
        switch (MotionEventCompat.getActionMasked(event)) {
            case MotionEvent.ACTION_DOWN: {
                final int pointerIndex = MotionEventCompat.getActionIndex(event);
                mLastTouchX = MotionEventCompat.getX(event, pointerIndex);
                mLastTouchY = MotionEventCompat.getY(event, pointerIndex);
                mActivePointerId = MotionEventCompat.getPointerId(event, pointerIndex);
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                final int pointerIndex = MotionEventCompat.findPointerIndex(event, mActivePointerId);
                final float touchX = MotionEventCompat.getX(event, pointerIndex);
                final float touchY = MotionEventCompat.getY(event, pointerIndex);

                final float dx = touchX - mLastTouchX;
                final float dy = touchY - mLastTouchY;

                x += dx;
                // scale radius with dy

                mLastTouchX = touchX;
                mLastTouchY = touchY;

                break;
            }
            case MotionEvent.ACTION_UP: {
                mActivePointerId = MotionEvent.INVALID_POINTER_ID;
                break;
            }

            case MotionEvent.ACTION_CANCEL: {
                mActivePointerId = MotionEvent.INVALID_POINTER_ID;
                break;
            }

            case MotionEvent.ACTION_POINTER_UP: {

                final int pointerIndex = MotionEventCompat.getActionIndex(event);
                final int pointerId = MotionEventCompat.getPointerId(event, pointerIndex);

                if (pointerId == mActivePointerId) {
                    // This was our active pointer going up. Choose a new
                    // active pointer and adjust accordingly.
                    final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                    mLastTouchX = MotionEventCompat.getX(event, newPointerIndex);
                    mLastTouchY = MotionEventCompat.getY(event, newPointerIndex);
                    mActivePointerId = MotionEventCompat.getPointerId(event, newPointerIndex);
                }
                break;
            }
        }
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getInsideRadius() {
        return insideRadius;
    }

    public float getOutsideRadius() {
        return outsideRadius;
    }
}
