package com.jikexueyuan.clock;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TimePicker;

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

    private static final String KEY_ALARM_LIST = "alarmlist";
    private Button btnAddAlarm;
    private ListView lvAlarmList;

    private ArrayAdapter<AlarmData> adapter;

    private static class AlarmData {

        public AlarmData(long time) {
            this.time = time;

            date = Calendar.getInstance();
            date.setTimeInMillis(time);

            timeLabel = String.format("%d月%d日 %d:%d",
                    date.get(Calendar.MONTH) + 1,
                    date.get(Calendar.DAY_OF_MONTH),
                    date.get(Calendar.HOUR_OF_DAY),
                    date.get(Calendar.MINUTE));
        }
        private long time = 0;//闹钟响起的时间
        private String timeLabel = "";
        private Calendar date;

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
        readSavedAlarmList();//读取闹钟数据

        btnAddAlarm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                addAlarm();
            }
        });
    }

    /**
     * 添加闹钟
     */
    private void addAlarm() {
        Calendar c = Calendar.getInstance();

        new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, i);
                calendar.set(Calendar.MINUTE, i1);

                Calendar currentTime = Calendar.getInstance();
                if (calendar.getTimeInMillis() <= currentTime.getTimeInMillis()) {//时间小于当前
                    calendar.setTimeInMillis(calendar.getTimeInMillis() + 24 * 60 * 60 * 1000L);//后推24小时
                }
                adapter.add(new AlarmData(calendar.getTimeInMillis()));
                saveAlarmList();//保存闹钟数据
            }
        }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true).show();
    }

    /**
     * 保存闹钟数据
     */
    private void saveAlarmList(){
        SharedPreferences.Editor editor = getContext().getSharedPreferences(AlarmView.class.getName(), Context.MODE_PRIVATE).edit();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < adapter.getCount(); i++) {
            sb.append(adapter.getItem(i).getTime()).append(",");
        }

        String content = sb.toString().substring(0, sb.length() - 1);//去掉最后面的逗号
        editor.putString(KEY_ALARM_LIST, content);
        editor.apply();
    }

    /**
     * 读取闹钟数据
     */
    private void readSavedAlarmList(){
        SharedPreferences sp = getContext().getSharedPreferences(AlarmView.class.getName(), Context.MODE_PRIVATE);
        String content = sp.getString(KEY_ALARM_LIST, null);
        if (content != null) {
            String[] timeStrings = content.split(",");
            for (String string : timeStrings
                    ) {
                adapter.add(new AlarmData(Long.parseLong(string)));
            }
        }
    }
}
