package com.example.clickable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class ClickActivity extends AppCompatActivity {

    private TextView mtvTimer, mtvCount;
    private LinearLayout mllTabArea;
    private long backpressedTime = 0;
    private AdView mAdView;

    // 다이얼로그 부분
    private Dialog dialog;
//    private TextView mDialogScore;

    private int count = 0;
    private int count_result = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {

            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView = new AdView(this);
        mAdView.setAdSize(AdSize.BANNER);
        mAdView.setAdUnitId("ca-app-pub-2604303771066079~9458324096");

        mtvTimer = (TextView) findViewById(R.id.tv_timer);
        mtvCount = (TextView) findViewById(R.id.tv_count);
//        mDialogScore = (TextView) findViewById(R.id.dialog_score);

        mllTabArea = (LinearLayout) findViewById(R.id.tab_area);

        // oncreate 시 count 개수 0으로 초기화
        mtvCount.setText("0");

        // 화면 터치시 count +1
        mllTabArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count_result = count++;
                mtvCount.setText(count+"");

            }
        });

        // 다이얼로그
        Context mContext = getApplicationContext();
        dialog = new Dialog(mContext);

        dialog = new Dialog(ClickActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog);

        // 타이머
        CountDownTimer countDownTimer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int num = (int) (millisUntilFinished / 1000);
                mtvTimer.setText(Integer.toString(num + 1));
            }

            @Override
            public void onFinish() {
                mtvTimer.setText("0");
                mllTabArea.setClickable(false);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showDialog();
                    }
                }, 1000);

            }
        }.start();
    }

    public void showDialog() {
        TextView test = dialog.findViewById(R.id.dialog_score);
        Button mBtnRetry = dialog.findViewById(R.id.btn_retry);
        test.setText("스코어: " + count);

        dialog.show();
        dialog.setCancelable(false); //  다이얼로그 외부 터치 금지
        mBtnRetry.setClickable(false); // 다시하기버튼 바로 클릭 금지

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
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mBtnRetry.setClickable(true);
                mBtnRetry.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ClickActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        },1000);
    }

    @Override
    public void onBackPressed() {

        if (System.currentTimeMillis() > backpressedTime + 2000) {
            backpressedTime = System.currentTimeMillis();
            Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
        } else if (System.currentTimeMillis() <= backpressedTime + 2000) {
            finish();
        }

    }
}