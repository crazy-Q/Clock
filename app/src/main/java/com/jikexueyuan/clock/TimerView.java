package com.jikexueyuan.clock;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by QQQ on 2016/8/12.
 */
public class TimerView extends LinearLayout {

    public TimerView(Context context) {
        super(context);
    }

    public TimerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        btnPause = (Button) findViewById(R.id.id_btn_pause);
        btnPause.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                stopTimer();

                btnPause.setVisibility(GONE);
                btnNext.setVisibility(VISIBLE);
            }
        });

        btnReset = (Button) findViewById(R.id.id_btn_reset);
        btnReset.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                stopTimer();

                etHour.setText("0");
                etMinute.setText("0");
                etSecond.setText("0");

                btnReset.setVisibility(GONE);
                btnNext.setVisibility(GONE);
                btnPause.setVisibility(GONE);
                btnStart.setVisibility(VISIBLE);
            }
        });

        btnNext = (Button) findViewById(R.id.id_btn_next);
        btnNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startTimer();
                btnNext.setVisibility(GONE);
                btnPause.setVisibility(VISIBLE);
            }
        });

        btnStart = (Button) findViewById(R.id.id_btn_start);
        btnStart.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startTimer();

                btnStart.setVisibility(GONE);
                btnPause.setVisibility(VISIBLE);
                btnReset.setVisibility(VISIBLE);
            }
        });

        etHour = (EditText) findViewById(R.id.id_et_hour);
        etMinute = (EditText) findViewById(R.id.id_et_minute);
        etSecond = (EditText) findViewById(R.id.id_et_second);

        etHour.setText("00");
        etHour.addTextChangedListener(new TextWatcher() {  //设置文本的事件监听器
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(charSequence)) {//判断非空
                    int value = Integer.parseInt(charSequence.toString());

                    if (value > 59) {      //将大于59小于0的强制转换
                        etHour.setText("59");
                    } else if (value < 0) {
                        etHour.setText("0");
                    }
                }
                checkToEnableBtnStart();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        etMinute.setText("00");
        etMinute.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(charSequence)) {
                    int value = Integer.parseInt(charSequence.toString());

                    if (value > 59) {
                        etMinute.setText("59");
                    } else if (value < 0) {
                        etMinute.setText("0");
                    }
                }
                checkToEnableBtnStart();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        etSecond.setText("00");
        etSecond.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(charSequence)) {
                    int value = Integer.parseInt(charSequence.toString());

                    if (value > 59) {
                        etSecond.setText("59");
                    } else if (value < 0) {
                        etSecond.setText("0");
                    }
                }
                checkToEnableBtnStart();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btnStart.setVisibility(VISIBLE);
        btnStart.setEnabled(false);
        btnPause.setVisibility(GONE);
        btnReset.setVisibility(GONE);
        btnNext.setVisibility(GONE);
    }

    private void checkToEnableBtnStart() {
        btnStart.setEnabled((!TextUtils.isEmpty(etHour.getText())) && Integer.parseInt(etHour.getText().toString()) > 0 || //文本非空且大于0
                (!TextUtils.isEmpty(etMinute.getText())) && Integer.parseInt(etMinute.getText().toString()) > 0 ||
                (!TextUtils.isEmpty(etSecond.getText())) && Integer.parseInt(etSecond.getText().toString()) > 0);
    }

    private void startTimer() {
        if (timerTask == null) {
            allTimerCount = Integer.parseInt(etHour.getText().toString()) * 60 * 60
                    + Integer.parseInt(etMinute.getText().toString()) * 60
                    + Integer.parseInt(etSecond.getText().toString());
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    allTimerCount--;
                    handler.sendEmptyMessage(MSG_WHAT_TIME_IS_TICK);

                    if (allTimerCount <= 0) {   //时间到了就弹出对话框
                        handler.sendEmptyMessage(MSG_WHAT_TIME_IS_UP);
                        stopTimer();
                    }
                }
            };
            timer.schedule(timerTask, 1000, 1000);
        }
    }

    private void stopTimer() {
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_WHAT_TIME_IS_UP:
                    new AlertDialog.Builder(getContext())
                            .setTitle("时间到了")
                            .setMessage("到了啊")
                            .setNegativeButton("取消", null)
                            .setCancelable(false)
                            .show();

                    btnReset.setVisibility(GONE);
                    btnNext.setVisibility(GONE);
                    btnPause.setVisibility(GONE);
                    btnStart.setVisibility(VISIBLE);
                    break;
                case MSG_WHAT_TIME_IS_TICK://显示动态时间
                    int hour = allTimerCount / 60 / 60;
                    int minute = (allTimerCount / 60) % 60;
                    int second = allTimerCount % 60;
                    etHour.setText(hour + "");
                    etMinute.setText(minute + "");
                    etSecond.setText(second + "");
                    break;
            }
        }
    };

    private static final int MSG_WHAT_TIME_IS_UP = 1;//时间到了
    private static final int MSG_WHAT_TIME_IS_TICK = 2;//时间减少中
    private int allTimerCount = 0;//输入的总时间
    private Timer timer = new Timer();
    private TimerTask timerTask = null;
    private Button btnStart, btnPause, btnReset, btnNext;
    private EditText etHour, etMinute, etSecond;
}
