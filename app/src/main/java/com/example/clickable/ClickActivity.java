package com.example.clickable;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ClickActivity extends AppCompatActivity {

    private TextView mtvTimer, mtvCount;
    private LinearLayout mllTabArea;

    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click);

        mtvTimer = (TextView) findViewById(R.id.tv_timer);
        mtvCount = (TextView) findViewById(R.id.tv_count);

        mllTabArea = (LinearLayout) findViewById(R.id.tab_area);

        // oncreate 시 count 개수 0으로 초기화
        mtvCount.setText("0");

        // 화면 터치시 count +1
        mllTabArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = count++;
                mtvCount.setText(count+"");
            }
        });

        // 타이머
        CountDownTimer countDownTimer = new CountDownTimer(15000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int num = (int) (millisUntilFinished / 1000);
                mtvTimer.setText(Integer.toString(num + 1));
            }

            @Override
            public void onFinish() {
                mtvTimer.setText("끝");
                mllTabArea.setClickable(false);
            }
        }.start();
    }
}