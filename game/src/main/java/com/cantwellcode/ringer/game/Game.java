package com.cantwellcode.ringer.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.cantwellcode.ringer.objects.Ball;
import com.cantwellcode.ringer.objects.Ring;
import com.cantwellcode.ringer.utils.Statics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Daniel on 5/9/2014.
 */
public class Game {

    public interface ScoreKeeper {
        public void updateTime(int time);

        public void updatePoints(int points);
    }

    private ScoreKeeper mScoreKeeper;

    private int mTime = 0;
    private int mPoints = 200;

    private Ring ring;
    private List<Ball> balls;

    private int ballsNeeded = 5;

    private Paint paint;

    public Game(FullscreenActivity fullscreenActivity) {
        balls = Collections.synchronizedList(new ArrayList<Ball>());
        mScoreKeeper = fullscreenActivity;
        ring = new Ring();

        paint = new Paint();
    }

    public void createBall() {
        balls.add(new Ball());
    }

    public void updateObjects() {
        /* Update ring */
        ring.update();

        /* Create Balls if Needed */
        if (balls.size() < ballsNeeded && mTime % 50 == 0) {
            createBall();
        }

        /* Update balls */
        synchronized (balls) {
            for (int i = 0; i < balls.size(); i++) {
                Ball ball = balls.get(i);
                ball.update();
            /* Check ground collisions */
                if (ball.groundCollision()) {
                    mPoints -= ball.getValue();
                    balls.remove(ball);
                }
            /* Check collision with ring */
                else if (ballCollidesWithRing(ball)) {
                    mPoints -= ball.getValue();
                    balls.remove(ball);
                }
            }
        }



        /* Update Score */
        mTime++;
        mScoreKeeper.updateTime(mTime / 15); // FPS = 30
        mScoreKeeper.updatePoints(mPoints);
    }

    public void drawObjects(Canvas canvas) {
        ring.draw(canvas);

        synchronized (balls) {
            for (Ball ball : balls) {
                ball.draw(canvas);
            }
        }

        // draw ground
        paint.setColor(Color.RED);
        paint.setStrokeWidth(3);
        canvas.drawRect(0, Statics.GROUND_POS, 1080, 1920, paint);
        paint.setColor(Color.YELLOW);
        paint.setStrokeWidth(0);
        canvas.drawRect(5, Statics.GROUND_POS + 5, 1075, 1915, paint);
    }

    public void onTouch(MotionEvent event) {
        ring.onTouch(event);
    }

    private boolean ballCollidesWithRing(Ball ball) {
        // TODO this is very rough collision detection
        if (ball.getPosition().y > ring.getY()) {
            if (((ball.getPosition().x > (ring.getX() + ring.getInsideRadius())) &&
                    (ball.getPosition().x < (ring.getX() + ring.getOutsideRadius()))) ||
                    ((ball.getPosition().x < (ring.getX() - ring.getInsideRadius())) &&
                            (ball.getPosition().x > (ring.getX() - ring.getOutsideRadius())))) {
                return true;
            }
        }
        return false;
    }
}
