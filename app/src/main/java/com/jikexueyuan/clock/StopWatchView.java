package com.jikexueyuan.clock;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by QQQ on 2016/8/12.
 */
public class StopWatchView extends LinearLayout {

    private TextView tvHour,tvMinute,tvSecond, tvMSecond;
    private Button btnStart,btnPause,btnReset,btnNext, btnLap;
    private ListView lvTimeList;
    private ArrayAdapter<String> adapter;

    private int tenMSecs = 0;//10毫秒
    private Timer timer = new Timer();
    private TimerTask timerTask = null;
    private TimerTask showTimeTask = null;

    private static final int MSG_WHAT_SHOW_TIME = 1;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_WHAT_SHOW_TIME://呈现时间
                    tvHour.setText(tenMSecs / 100 / 60 / 60 + "");
                    tvMinute.setText(tenMSecs / 100 / 60 % 60 + "");
                    tvSecond.setText(tenMSecs / 100 % 60 + "");
                    tvMSecond.setText(tenMSecs % 100 + "");
                    break;
                default:
                    break;
            }
        }
    };

    public StopWatchView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        showTimeTask=new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(MSG_WHAT_SHOW_TIME);
            }
        };
        timer.schedule(showTimeTask, 200, 200);//一秒刷新5次

        tvHour = (TextView) findViewById(R.id.id_timer_hour);
        tvHour.setText("0");
        tvMinute = (TextView) findViewById(R.id.id_timer_minute);
        tvMinute.setText("0");
        tvSecond = (TextView) findViewById(R.id.id_timer_second);
        tvSecond.setText("0");
        tvMSecond = (TextView) findViewById(R.id.id_timer_msecond);
        tvMSecond.setText("0");

        btnStart = (Button) findViewById(R.id.id_btn_sw_start);

        btnPause = (Button) findViewById(R.id.id_btn_sw_pause);
        btnPause.setVisibility(GONE);
        btnPause.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                stopTimer();

                btnPause.setVisibility(GONE);
                btnNext.setVisibility(VISIBLE);
                btnLap.setVisibility(GONE);
                btnReset.setVisibility(VISIBLE);
            }
        });

        btnStart.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startTimer();

                btnStart.setVisibility(GONE);
                btnPause.setVisibility(VISIBLE);
                btnLap.setVisibility(VISIBLE);
            }
        });

        btnReset = (Button) findViewById(R.id.id_btn_sw_reset);
        btnReset.setVisibility(GONE);
        btnReset.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                stopTimer();
                tenMSecs = 0;
                adapter.clear();

                btnReset.setVisibility(GONE);
                btnNext.setVisibility(GONE);
                btnLap.setVisibility(GONE);
                btnPause.setVisibility(GONE);
                btnStart.setVisibility(VISIBLE);
            }
        });

        btnNext = (Button) findViewById(R.id.id_btn_sw_next);
        btnNext.setVisibility(GONE);
        btnNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startTimer();

                btnNext.setVisibility(GONE);
                btnPause.setVisibility(VISIBLE);
                btnReset.setVisibility(GONE);
                btnLap.setVisibility(VISIBLE);
            }
        });

        btnLap = (Button) findViewById(R.id.id_btn_sw_lap);
        btnLap.setVisibility(GONE);
        btnLap.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.insert(String.format("%d:%d:%d.%d", tenMSecs / 100 / 60 / 60, tenMSecs / 100 / 60 % 60, tenMSecs / 100 % 60, tenMSecs % 100), 0);
            }
        });

        lvTimeList = (ListView) findViewById(R.id.id_lv_watctimelist);

        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1);
        lvTimeList.setAdapter(adapter);

    }

    private void startTimer(){
        if (timerTask == null) {
            timerTask=new TimerTask() {
                @Override
                public void run() {
                    tenMSecs++;

                }
            };
            timer.schedule(timerTask,10,10);
        }
    }

    private void stopTimer(){
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
    }

    public void onDestroy() {
        timer.cancel();
    }
}
