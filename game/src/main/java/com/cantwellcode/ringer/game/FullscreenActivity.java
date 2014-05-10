package com.cantwellcode.ringer.game;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.ToggleButton;

public class FullscreenActivity extends Activity implements Game.ScoreKeeper{

    private Game game;
    private GameView gameView;

    private TextView timeView;
    private TextView pointsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_fullscreen);

        final View controlsView = findViewById(R.id.fullscreen_controls);

        game = new Game(this);
        gameView = (GameView) findViewById(R.id.gameView);
        gameView.setup(game);

        timeView = (TextView) controlsView.findViewById(R.id.time);
        pointsView = (TextView) controlsView.findViewById(R.id.points);
    }

    public void onPause(View view) {
        // Is the toggle on?
        boolean on = ((ToggleButton) view).isChecked();

        if (on) {
            gameView.onPause();
        } else {
            gameView.onResume();
        }
    }

    @Override
    public void updateTime(final int time) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                timeView.setText(" " + time);
            }
        });
    }

    @Override
    public void updatePoints(final int score) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                pointsView.setText(" " + score);
            }
        });
    }
}
