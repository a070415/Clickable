package com.example.clickable;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Point;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ClickActivity extends AppCompatActivity {

    private TextView mtvTimer, mtvCount;
    private LinearLayout mllTabArea;

    private Dialog dialog;

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

        // 다이얼로그
        dialog = new Dialog(ClickActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog);

        // 타이머
        CountDownTimer countDownTimer = new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int num = (int) (millisUntilFinished / 1000);
                mtvTimer.setText(Integer.toString(num + 1));
            }

            @Override
            public void onFinish() {
                mtvTimer.setText("끝");
                mllTabArea.setClickable(false);
                showDialog();
            }
        }.start();
    }

    public void showDialog() {
        dialog.show();
        dialog.setCancelable(false); //  다이얼로그 외부 터치 금지

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        Window window = dialog.getWindow();

        int x = (int)(size.x * 0.7f);
        int y = (int)(size.y * 0.2f);

        window.setLayout(x,y);

//        // no 버튼을 눌렀을때
//        Button noBtn = dialog.findViewById(R.id.noBtn);
//        noBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });

        // yes 버튼을 눌렀을때
        Button mBtnRetry = dialog.findViewById(R.id.btn_retry);
        mBtnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}