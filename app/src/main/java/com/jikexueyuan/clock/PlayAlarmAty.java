package com.jikexueyuan.clock;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by QQQ on 2016/8/11.
 */
public class PlayAlarmAty extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_player_aty);

        mp = MediaPlayer.create(this, R.raw.test);
        mp.start();
    }

    @Override
    protected void onPause() {
        super.onPause();

        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mp.stop();
        mp.release();
    }

    private MediaPlayer mp;
}
