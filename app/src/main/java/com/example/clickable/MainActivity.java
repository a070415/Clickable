package com.example.clickable;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Dialog mInfoDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mInfoDialog = new Dialog(MainActivity.this);
        mInfoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mInfoDialog.setContentView(R.layout.info_dialog);

        findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }

    private void gotoActivity(Intent intent) {
        if(intent == null) {
            return;
        } else {
            startActivity(intent);
        }
    }

    public void showDialog() {
        mInfoDialog.show();
        mInfoDialog.setCancelable(false); //  다이얼로그 외부 터치 금지

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        Window window = mInfoDialog.getWindow();

        int x = (int)(size.x * 0.7f);
        int y = (int)(size.y * 0.2f);

        window.setLayout(x,y);

        // ok 버튼을 눌렀을때
        Button mBtnDialogOk = mInfoDialog.findViewById(R.id.btn_dialog_ok);
        mBtnDialogOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ClickActivity.class);
                gotoActivity(intent);
            }
        });
    }
}