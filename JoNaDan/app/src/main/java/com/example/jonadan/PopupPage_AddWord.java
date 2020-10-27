package com.example.jonadan;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PopupPage_AddWord extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //상태바 제거 전체화면모드
        setContentView(R.layout.popup_add_word);

        Button cancleBt = (Button)findViewById(R.id.cancleButton);
        Button makeBt = (Button)findViewById(R.id.addButton);

        makeBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText wordEt = (EditText)findViewById(R.id.inputTitle);
                EditText meanEt = (EditText)findViewById(R.id.inputMean);
                String word = wordEt.getText().toString();
                String mean = meanEt.getText().toString();
                if(wordEt.length() == 0) {
                    Toast.makeText(PopupPage_AddWord.this, "단어를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else if (meanEt.length() == 0) {
                    Toast.makeText(PopupPage_AddWord.this, "뜻을 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent();
                    intent.putExtra("word", word);
                    intent.putExtra("mean", mean);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
        cancleBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {    //바깥레이어 클릭시 안닫히게
        if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {   //백버튼 막기
        return;
    }
}
