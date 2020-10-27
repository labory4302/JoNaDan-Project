package com.example.jonadan;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

public class PopupPage_SelectGame extends AppCompatActivity {
    private int VS_WORD = 1;
    private int VS_MEAN = 2;
    private int VS_ME_WORD = 3;
    private int VS_ME_MEAN = 4;

    int mPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //상태바 제거 전체화면모드
        setContentView(R.layout.popup_select_game);

        TextView vsWord = (TextView)findViewById(R.id.vsWord);
        TextView vsMean = (TextView)findViewById(R.id.vsMean);
        TextView vsMeWord = (TextView)findViewById(R.id.vsMeWord);
        TextView vsMeMean = (TextView)findViewById(R.id.vsMeMean);

        mPosition = getIntent().getIntExtra("position", 0);

        vsWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendIntent(VS_WORD);
            }
        });
        vsMean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendIntent(VS_MEAN);
            }
        });
        vsMeWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendIntent(VS_ME_WORD);
            }
        });
        vsMeMean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendIntent(VS_ME_MEAN);
            }
        });
    }

    public void sendIntent (int resultCode) {
        Intent intent = new Intent();
        intent.putExtra("position", mPosition);
        setResult(resultCode, intent);
        finish();
    }
}
