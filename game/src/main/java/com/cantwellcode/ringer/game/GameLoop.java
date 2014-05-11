package com.cantwellcode.ringer.game;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by Daniel on 5/9/2014.
 */
public class GameLoop extends Thread {

    static final long FPS = 30;

    private GameView gameView;
    private SurfaceHolder surfaceHolder;

    private boolean running = false;
    private boolean mPaused = false;
    private Object mPauseLock;

    public GameLoop(SurfaceHolder holder, GameView gameView) {
        this.surfaceHolder = holder;
        this.gameView = gameView;
        mPauseLock = new Object();
    }

    public void setRunning(boolean run) {
        running = run;
    }

    @Override
    public void run() {
        long ticksPS = 1000 / FPS;
        long startTime;
        long sleepTime;
        while (running) {
            Canvas c = null;
            startTime = System.currentTimeMillis();
            try {
                c = surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    /* Update Graphics */
                    gameView.update();
                    gameView.postInvalidate();
                    //gameView.draw(c);
                }
            } finally {
                if (c != null) {
                    surfaceHolder.unlockCanvasAndPost(c);
                }
            }
            sleepTime = ticksPS - (System.currentTimeMillis() - startTime);
            try {
                if (sleepTime > 0)
                    sleep(sleepTime);
                else
                    sleep(10);
            } catch (Exception e) {
            }

            // Handle Pause
            synchronized (mPauseLock) {
                while (mPaused) {
                    try {
                        mPauseLock.wait();
                    } catch (InterruptedException e) {
                    }
                }
            }
        }

    }

    public void onPause() {
        synchronized (mPauseLock) {
            mPaused = true;
        }
    }

    public void onResume() {
        synchronized (mPauseLock) {
            mPaused = false;
            mPauseLock.notifyAll();
        }
    }
}
