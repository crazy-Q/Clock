package com.jikexueyuan.clock;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by QQQ on 2016/8/12.
 */
public class StopWatchView extends LinearLayout {

    private TextView tvHour,tvMinute,tvSecond, tvMSecond;
    private Button btnStart,btnPause,btnReset,btnNext, btnLap;
    private ListView lvTimeList;
    private ArrayAdapter<String> adapter;

    public StopWatchView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

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
        btnReset = (Button) findViewById(R.id.id_btn_sw_reset);
        btnNext = (Button) findViewById(R.id.id_btn_sw_next);
        btnLap = (Button) findViewById(R.id.id_btn_sw_lap);

        lvTimeList = (ListView) findViewById(R.id.id_lv_watctimelist);

        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1);
        lvTimeList.setAdapter(adapter);

    }
}
