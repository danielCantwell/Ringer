package com.cantwellcode.ringer.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.cantwellcode.ringer.utils.Statics;

/**
 * Created by Daniel on 5/9/2014.
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private enum State {
        Ready, Running, Done
    }

    private State state = State.Ready;

    private Game mGame;
    private GameLoop mLoop;

    private Paint paint;

    public GameView(FullscreenActivity activity) {
        super(activity);
        // create the game loop thread
        mLoop = new GameLoop(getHolder(), this);

        getHolder().addCallback(this);

        // set focusable so the surface view can handle events
        setFocusable(true);

        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setTextSize(48);
    }

    public GameView(Context context) {
        super(context);
        // create the game loop thread
        mLoop = new GameLoop(getHolder(), this);

        getHolder().addCallback(this);

        // set focusable so the surface view can handle events
        setFocusable(true);

        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setTextSize(24);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // create the game loop thread
        mLoop = new GameLoop(getHolder(), this);

        getHolder().addCallback(this);

        // set focusable so the surface view can handle events
        setFocusable(true);

        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setTextSize(24);
    }

    public GameView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // create the game loop thread
        mLoop = new GameLoop(getHolder(), this);

        getHolder().addCallback(this);

        // set focusable so the surface view can handle events
        setFocusable(true);

        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setTextSize(24);
    }

    public void setup(Game game) {

        mGame = game;
    }

    public void update() {
        switch (state) {
            case Ready:
                updateReady();
                break;
            case Running:
                updateRunning();
                break;
            case Done:
                updateDone();
                break;
        }
    }

    private void updateReady() {

    }

    private void updateRunning() {
        mGame.updateObjects();
    }

    private void updateDone() {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        switch (state) {
            case Ready:
                drawReady(canvas);
                break;
            case Running:
                drawRunning(canvas);
                break;
            case Done:
                drawDone(canvas);
                break;
        }
    }

    private void drawReady(Canvas canvas) {
        canvas.drawText("READY", getWidth() / 2, getHeight() / 2, paint);
    }

    private void drawRunning(Canvas canvas) {

        canvas.drawText("RUNNING", getWidth() / 3, getHeight() / 3, paint);
        mGame.drawObjects(canvas);
    }

    private void drawDone(Canvas canvas) {
        canvas.drawText("GAME OVER", getWidth() / 2, getHeight() / 2, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (state) {
            case Ready:
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    state = State.Running;
                }
                break;
            case Running:
                mGame.onTouch(event);
                break;
            case Done:
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    state = State.Ready;
                }
                break;
        }

        return true;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        setWillNotDraw(false);
        mLoop.setRunning(true);
        mLoop.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        mLoop.setRunning(false);
        while (retry) {
            try {
                mLoop.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }

    public void onPause() {
        mLoop.onPause();
    }

    public void onResume() {
        mLoop.onResume();
    }
}
