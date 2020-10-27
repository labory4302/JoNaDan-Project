package com.example.jonadan;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GameVsWordAndMean extends AppCompatActivity {
    private int WORD_GAME = 1;
    private int MEAN_GAME = 2;
    private int END_GAME = 555;

    WordCollectionVo wordCollectionVo;
    ArrayList<WordVo> wordList;
    TextView mTitle, mProblem, mProblemNum;
    TextView firstExample, secondExample, thirdExample, forthExample;
    Random random;
    int tmpCategory;
    int count, solveProblem;
    int right = 0;
    int rightAnswerAll = 0;
    int[] mExampleNum;
    double answerRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_vs_word_and_mean);

        wordCollectionVo = (WordCollectionVo)getIntent().getSerializableExtra("wordCollectionVo");
        tmpCategory = getIntent().getIntExtra("category", 0);

        mTitle = (TextView)findViewById(R.id.title);
        mTitle.setText(wordCollectionVo.getName());
        mProblemNum = (TextView)findViewById(R.id.problemNum);
        mProblem = (TextView)findViewById(R.id.problem);

        firstExample = (TextView)findViewById(R.id.firstExample);
        secondExample = (TextView)findViewById(R.id.secondExample);
        thirdExample = (TextView)findViewById(R.id.thirdExample);
        forthExample = (TextView)findViewById(R.id.forthExample);

        Button quitBt = (Button)findViewById(R.id.giveUp);
        Button passBt = (Button)findViewById(R.id.pass);

        firstExample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { checkAnswer(1); }
        });
        secondExample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { checkAnswer(2); }
        });
        thirdExample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { checkAnswer(3); }
        });
        forthExample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { checkAnswer(4); }
        });

        quitBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { quitGame(); }
        });
        passBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { pass(); }
        });

        wordList = wordCollectionVo.getWords();
        shuffleWord();

        count = 0;
        solveProblem = count+1;

        if (tmpCategory == WORD_GAME) {
            setWordGame();
        } else if (tmpCategory == MEAN_GAME) {
            setMeanGame();
        }
    }

    private void shuffleWord() {
        random = new Random();
        random.setSeed(System.currentTimeMillis());
        int num1, num2;
        for(int i=0;i<100;i++) {
            num1 = random.nextInt(wordList.size());
            do {
                num2 = random.nextInt(wordList.size());
            } while(num1 == num2);

            Collections.swap(wordList, num1, num2);
        }
    }

    private void setExample() {
        mExampleNum = new int[4];
        int place[] = new int[4];
        int wrongExample[] = new int[3];

        random = new Random();
        random.setSeed(System.currentTimeMillis());
        place[0] = random.nextInt(4);
        do {
            place[1] = random.nextInt(4);
        } while(place[0] == place[1]);
        do {
            place[2] = random.nextInt(4);
        } while(place[0] == place[2] || place[1] == place[2]);
        do {
            place[3] = random.nextInt(4);
        } while(place[0] == place[3] || place[1] == place[3] || place[2] == place[3]);

        do {
            wrongExample[0] = random.nextInt(wordList.size());
        } while(count == wrongExample[0]);
        do {
            wrongExample[1] = random.nextInt(wordList.size());
        } while(wrongExample[0] == wrongExample[1] || count == wrongExample[1]);
        do {
            wrongExample[2] = random.nextInt(wordList.size());
        } while(wrongExample[0] == wrongExample[2] || wrongExample[1] == wrongExample[2] || count == wrongExample[2]);

        mExampleNum[place[0]] = count;
        mExampleNum[place[1]] = wrongExample[0];
        mExampleNum[place[2]] = wrongExample[1];
        mExampleNum[place[3]] = wrongExample[2];

        right = place[0] + 1;
    }

    private void setWordGame() {
        setExample();
        mProblemNum.setText(solveProblem + "/" +wordList.size());
        mProblem.setText(wordList.get(count).getMean());

        firstExample.setText(wordList.get(mExampleNum[0]).getWord());
        secondExample.setText(wordList.get(mExampleNum[1]).getWord());
        thirdExample.setText(wordList.get(mExampleNum[2]).getWord());
        forthExample.setText(wordList.get(mExampleNum[3]).getWord());
    }

    private void setMeanGame() {
        setExample();
        mProblemNum.setText(solveProblem + "/" +wordList.size());
        mProblem.setText(wordList.get(count).getWord());

        firstExample.setText(wordList.get(mExampleNum[0]).getMean());
        secondExample.setText(wordList.get(mExampleNum[1]).getMean());
        thirdExample.setText(wordList.get(mExampleNum[2]).getMean());
        forthExample.setText(wordList.get(mExampleNum[3]).getMean());
    }

    private void checkAnswer(int check) {
        if (right == check) {
            Toast.makeText(this, "정답입니다!", Toast.LENGTH_SHORT).show();
            count++;
            solveProblem++;
            rightAnswerAll++;
            if (count == wordList.size()) {
                showResult();
            } else {
                if (tmpCategory == WORD_GAME) {
                    setWordGame();
                } else if (tmpCategory == MEAN_GAME) {
                    setMeanGame();
                }
            }
        } else {
            Toast.makeText(this, "오답입니다...", Toast.LENGTH_SHORT).show();
            count++;
            solveProblem++;
            if (count == wordList.size()) {
                showResult();
            } else {
                if (tmpCategory == WORD_GAME) {
                    setWordGame();
                } else if (tmpCategory == MEAN_GAME) {
                    setMeanGame();
                }
            }
        }
    }

    private void pass() {
        Toast.makeText(this, "패스했습니다.", Toast.LENGTH_SHORT).show();
        count++;
        solveProblem++;
        if (tmpCategory == WORD_GAME) {
            setWordGame();
        } else if (tmpCategory == MEAN_GAME) {
            setMeanGame();
        }
    }

    @Override
    public void onBackPressed() {
        quitGame();
    }

    private void quitGame() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(GameVsWordAndMean.this);

        dialog.setMessage("게임을 중간에 포기하면 정답률이 계산되지 않습니다. 그래도 나가시겠습니까?");
        dialog.setPositiveButton("나가기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) { finish(); }
        });
        dialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {}
        });
        dialog.show();
    }

    private void endGame() {
        int wordCollectionId = wordCollectionVo.getId();
        Intent intent = new Intent();
        intent.putExtra("getAnswerRate", answerRate);
        intent.putExtra("wordCollectionId", wordCollectionId);
        setResult(END_GAME, intent);
        finish();
    }

    private void showResult() {
        int wrongProblem = wordList.size() - rightAnswerAll;
        answerRate = ((double)rightAnswerAll/(double)(wordList.size()))*100;
        AlertDialog.Builder dialog = new AlertDialog.Builder(GameVsWordAndMean.this);

        dialog.setMessage("총 문제 : " + wordList.size() + "개\n\n맞은 문제 : " + rightAnswerAll +
                        "개\n\n틀린 문제 : " + wrongProblem + "개\n\n정답률 : " + answerRate);
        dialog.setCancelable(false);
        dialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) { endGame(); }
        });
        dialog.show();
    }
}
