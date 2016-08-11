package com.jikexueyuan.clock;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by QQQ on 2016/8/11.
 */
public class TimeView extends LinearLayout {

    public TimeView(Context context) {
        super(context);
    }

    public TimeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TimeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private TextView tvTime;

    /**
     * 初始化完成后执行
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        tvTime = (TextView) findViewById(R.id.id_tv_time);
        tvTime.setText("Hello");

   //     timerHandler.sendEmptyMessage(0);
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);

        if (visibility == View.VISIBLE) {
            timerHandler.sendEmptyMessage(0);
        } else {
            timerHandler.removeMessages(0);
        }
    }

    private void refreshTime(){
        Calendar c = Calendar.getInstance();
        tvTime.setText(String.format("%d:%d:%d", c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), c.get(Calendar.SECOND)));
    }

    private Handler timerHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            refreshTime();
            if (getVisibility() == View.VISIBLE) {
                timerHandler.sendEmptyMessageDelayed(0, 1000);
            }
        }
    };
}
