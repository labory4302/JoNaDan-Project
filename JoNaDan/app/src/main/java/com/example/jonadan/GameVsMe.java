package com.example.jonadan;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class GameVsMe extends AppCompatActivity {
    GameVsMe.MyWordAdapter adapter;
    WordCollectionVo wordCollectionVo;
    ArrayList<WordVo> wordList = new ArrayList<WordVo>();
    int tmpCategory;
    int checkShow;
    private int NO_SHOW_ANSWER = 0;
    private int ON_SHOW_ANSWER = 1;

    TextView mTitle;
    TextView mProblemNum;
    TextView problem1;
    TextView problem2;

    Button quitBt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_vs_me);

        mTitle = (TextView)findViewById(R.id.title2);
        mProblemNum = (TextView)findViewById(R.id.problemNum2);
        ListView listView = (ListView)findViewById(R.id.gameList);
        Button showAnswerBt = (Button)findViewById(R.id.showAnswer);
        quitBt = (Button)findViewById(R.id.quit);

        wordCollectionVo = (WordCollectionVo)getIntent().getSerializableExtra("wordCollectionVo");
        tmpCategory = getIntent().getIntExtra("category", 0);
        wordList = wordCollectionVo.getWords();
        mTitle.setText(wordCollectionVo.getName());
        mProblemNum.setText(wordCollectionVo.getWords().size() + "문항");

        checkShow = NO_SHOW_ANSWER;

        adapter = new GameVsMe.MyWordAdapter(this, R.layout.game_vs_me_information, wordList);
        listView.setAdapter(adapter);

        showAnswerBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkShow == NO_SHOW_ANSWER) {
                    checkShow = ON_SHOW_ANSWER;
                } else if (checkShow == ON_SHOW_ANSWER) {
                    checkShow = NO_SHOW_ANSWER;
                }
                adapter.notifyDataSetChanged();
            }
        });
        quitBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { confirmQuit(); }
        });
    }

    class MyWordAdapter extends ArrayAdapter<WordVo> {
        Context mContext;

        public MyWordAdapter(Context context, int resource, List<WordVo> objects) {
            super(context, resource, objects);
            mContext = context;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            if (convertView == null) {
                view = View.inflate( mContext, R.layout.game_vs_me_information, null);
            } else {
                view = convertView;
            }

            problem1 = (TextView) view.findViewById(R.id.problem1);
            problem2 = (TextView) view.findViewById(R.id.problem2);

            WordVo wordVoTmp = wordList.get(position);

            if (tmpCategory == 3) {
                problem1.setText(position+1 + ". " + wordVoTmp.getMean());
                checkShowAnswer(problem2);
                problem2.setText(wordVoTmp.getWord());
            } else if (tmpCategory == 4) {
                problem1.setText(position+1 + ". " + wordVoTmp.getWord());
                checkShowAnswer(problem2);
                problem2.setText(wordVoTmp.getMean());
            }

            return view;
        }
    }

    private void confirmQuit() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(GameVsMe.this);

        dialog.setTitle("정말로 나가시겠습니까?");
        dialog.setPositiveButton("나가기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        dialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {}
        });
        dialog.show();
    }

    private void checkShowAnswer(TextView textView) {
        if (checkShow == NO_SHOW_ANSWER) {
            textView.setVisibility(View.INVISIBLE);
        } else if (checkShow == ON_SHOW_ANSWER) {
            textView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        confirmQuit();
    }
}
