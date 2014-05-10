package com.cantwellcode.ringer.game;

import android.graphics.Canvas;
import android.view.MotionEvent;

import com.cantwellcode.ringer.objects.Ball;
import com.cantwellcode.ringer.objects.Ring;

import java.util.ArrayList;
import java.util.Collections;
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
    private int mPoints = 1000;

    private Ring ring;
    private List<Ball> balls;

    private int ballsNeeded = 5;

    public Game(FullscreenActivity fullscreenActivity) {
        balls = Collections.synchronizedList(new ArrayList<Ball>());
        mScoreKeeper = fullscreenActivity;
        ring = new Ring();
    }

    public void createBall() {
        balls.add(new Ball());
    }

    public void updateObjects() {
        /* Update ring */
        ring.update();

        /* Create Balls if Needed */
        if (balls.size() < ballsNeeded) {
            createBall();
        }

        /* Update balls */
        synchronized (balls) {
            for (Ball ball : balls) {
                ball.update();
            /* Check ground collisions */
                if (ball.groundCollision()) {
                    mPoints -= ball.getValue();
                    balls.remove(ball);
                }
            /* Check collision with ring */
                if (ballCollidesWithRing(ball)) {
                    mPoints -= ball.getValue();
                    balls.remove(ball);
                }
            }
        }


        /* Update Score */
        mTime++;
        mScoreKeeper.updateTime(mTime / 15); // FPS = 15
        mScoreKeeper.updatePoints(mPoints);
    }

    public void drawObjects(Canvas canvas) {
        ring.draw(canvas);

        synchronized (balls) {
            for (Ball ball : balls) {
                ball.draw(canvas);
            }
        }
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
