package com.example.jonadan;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainPage extends AppCompatActivity {
    ArrayList<WordCollectionVo> myWordCollectionList = new ArrayList<WordCollectionVo>();

    MyWordAdapter adapter;
    private WordDatabase wordDatabase;

    TextView mNoWordCollectionText;

    private int ADD_WORD_COLLECTION = 100;    //requestCode
    private int MODIFY_WORD_COLLECTION = 200;
    private int SELECT_GAME = 300;
    private int VS_WORD_MEAN_GAME = 10000;
    private int VS_ME_GAME = 20000;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button addwordCollectionListButton = (Button)findViewById(R.id.createWordCollection);
        ListView listView = findViewById(R.id.listView);
        mNoWordCollectionText = (TextView)findViewById(R.id.noWordCollection);

        adapter = new MyWordAdapter(this, R.layout.word_collection_information, myWordCollectionList);
        wordDatabase = new WordDatabase(this);

        wordDatabase.retriveWordCollection(myWordCollectionList);
        setmNoWordCollectionText();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(MainPage.this, PopupPage_SelectGame.class);
                intent.putExtra("position", position);
                startActivityForResult(intent, SELECT_GAME);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(MainPage.this, ModifyPage.class);
                WordCollectionVo wordCollectionVo = myWordCollectionList.get(position);
                wordDatabase.retriveWord(wordCollectionVo);
                intent.putExtra("wordCollectionVo", wordCollectionVo);
                startActivityForResult(intent, MODIFY_WORD_COLLECTION);
                return true;
            }
        });
        addwordCollectionListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPage.this, PopupPage_AddWordCollection.class);
                startActivityForResult(intent, ADD_WORD_COLLECTION);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_WORD_COLLECTION) {
            if(resultCode == -1) {
                String wordCollectionName = data.getStringExtra("wordCollectionName");
                wordDatabase.MakeWordCollection(wordCollectionName);
                Toast.makeText(this, "단어모음이 생성되었습니다.", Toast.LENGTH_SHORT).show();
                renewal();
            }
        } else if (requestCode == MODIFY_WORD_COLLECTION) {
            if(resultCode == 4) {
                int wordCollectionId = data.getIntExtra("wordCollectionId", 0);
                wordDatabase.DeleteWordCollection(wordCollectionId);
                Toast.makeText(this, "삭제가 완료되었습니다.", Toast.LENGTH_SHORT).show();
                renewal();
            } else if (resultCode == -1) {
                WordCollectionVo wordCollectionVo = (WordCollectionVo)data.getSerializableExtra("wordCollectionVo");
                wordDatabase.UpdateWordCollection(wordCollectionVo);
                wordDatabase.UpdateWords(wordCollectionVo);
                Toast.makeText(this, "수정이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                renewal();
            }
        } else if (requestCode == SELECT_GAME) {
            int getPosition;
            switch (resultCode) {
                case 1:
                    getPosition = data.getIntExtra("position", 0);
                    executeGame(getPosition, VS_WORD_MEAN_GAME, GameVsWordAndMean.class, 1);
                    break;
                case 2:
                    getPosition = data.getIntExtra("position", 0);
                    executeGame(getPosition, VS_WORD_MEAN_GAME, GameVsWordAndMean.class, 2);
                    break;
                case 3:
                    getPosition = data.getIntExtra("position", 0);
                    executeGame(getPosition, VS_ME_GAME, GameVsMe.class, 3);
                    break;
                case 4:
                    getPosition = data.getIntExtra("position", 0);
                    executeGame(getPosition, VS_ME_GAME, GameVsMe.class, 4);
                    break;
                default:
                    break;
            }
        } else if (requestCode == VS_WORD_MEAN_GAME) {
            if (resultCode == 555) {
                double getAnswerRate = data.getDoubleExtra("getAnswerRate", 0.0);
                int wordCollectionId = data.getIntExtra("wordCollectionId", 0);
                wordDatabase.UpdateWordCollectionAnswerRate(getAnswerRate, wordCollectionId);
                Toast.makeText(this, "게임을 종료합니다! 고생하셨습니다ㅎㅎ", Toast.LENGTH_SHORT).show();
                renewal();
            }
        }
    }

    public void executeGame(int position, int requestCode, Class instance, int select) {
        WordCollectionVo wordCollectionVo = myWordCollectionList.get(position);
        wordDatabase.retriveWord(wordCollectionVo);
        if (wordCollectionVo.getWords().size() < 10) {
            Toast.makeText(this, "단어가 모자랍니다.\n최소한 10개 이상의 단어를\n저장 후 게임을 진행해주세요.", Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(MainPage.this, instance);
            intent.putExtra("wordCollectionVo", wordCollectionVo);
            intent.putExtra("category", select);
            startActivityForResult(intent, requestCode);
        }
    }

    public void setmNoWordCollectionText() {
        if(myWordCollectionList.size() == 0) {
            mNoWordCollectionText.setVisibility(View.VISIBLE);
        } else {
            mNoWordCollectionText.setVisibility(View.INVISIBLE);
        }
    }

    public void renewal() {
        wordDatabase.retriveWordCollection(myWordCollectionList);
        setmNoWordCollectionText();
        adapter.notifyDataSetChanged();
    }

    class MyWordAdapter extends ArrayAdapter<WordCollectionVo> {
        Context mContext;

        public MyWordAdapter(Context context, int resource, List<WordCollectionVo> objects) {
            super(context, resource, objects);
            mContext = context;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            if (convertView == null) {
                view = View.inflate( mContext, R.layout.word_collection_information, null);
            } else {
                view = convertView;
            }

            TextView wordCollTitle = (TextView) view.findViewById(R.id.wordCollTitle);
            TextView answerRating = (TextView)view.findViewById(R.id.answerRating);
            TextView wordNum = (TextView)view.findViewById(R.id.wordNum);

            WordCollectionVo wordCollectionTmp = myWordCollectionList.get(position);
            wordDatabase.retriveWord(wordCollectionTmp);

            wordCollTitle.setText(wordCollectionTmp.getName());
            answerRating.setText("정답률 : " + wordCollectionTmp.getAnswerRate() + "%");
            wordNum.setText("단어수 : " + wordCollectionTmp.getWords().size() + "개");

            return view;
        }
    }
}