package com.jikexueyuan.clock;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.Calendar;

/**
 * Created by QQQ on 2016/8/11.
 */
public class AlarmView extends LinearLayout {

    public AlarmView(Context context) {
        super(context);
    }

    public AlarmView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AlarmView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private Button btnAddAlarm;
    private ListView lvAlarmList;
    private ArrayAdapter<AlarmData> adapter;

    private static class AlarmData {

        public AlarmData(long time) {
            this.time = time;

            c = Calendar.getInstance();
            timeLabel = c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE);

            Log.d("TAG", "" + c.get(Calendar.MINUTE));
        }

        private long time = 0;//闹钟响起的时间
        private String timeLabel = "";
        private Calendar c;

        public long getTime() {
            return time;
        }

        public String getTimeLabel() {
            return timeLabel;
        }

        @Override
        public String toString() {
            return getTimeLabel();
        }
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        btnAddAlarm = (Button) findViewById(R.id.id_btn_addAlarm);
        lvAlarmList = (ListView) findViewById(R.id.id_lv_alarm);

        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1);
        lvAlarmList.setAdapter(adapter);
        adapter.add(new AlarmData(System.currentTimeMillis()));

        btnAddAlarm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                addAlarm();
            }
        });
    }

    private void addAlarm() {
        // TODO: 2016/8/11
    }


}
