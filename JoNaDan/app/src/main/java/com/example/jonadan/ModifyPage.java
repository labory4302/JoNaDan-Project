package com.example.jonadan;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ModifyPage extends AppCompatActivity {
    private int ADD_WORD = 1000;
    private int MODIFY_TITLE = 2000;

    private int REMOVE_WORD_COLLECTION = 4;
    private int MODIFY_WORD_COLLECTION = -1;

    ModifyPage.MyWordAdapter adapter;
    WordCollectionVo wordCollectionVo;
    ArrayList<WordVo> wordList = new ArrayList<WordVo>();
    TextView mTitle;
    TextView answerRate;
    TextView mWordNum;
    TextView mNoWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_word_collection);

        mTitle = (TextView)findViewById(R.id.title);
        TextView makeDate = (TextView)findViewById(R.id.makeTime);
        answerRate = (TextView)findViewById(R.id.answerRate);
        mWordNum = (TextView)findViewById(R.id.wordNum);
        mNoWord = (TextView)findViewById(R.id.noWord);
        ListView listView = (ListView)findViewById(R.id.wordList);
        Button addWord = (Button)findViewById(R.id.addWord);

        wordCollectionVo = (WordCollectionVo)getIntent().getSerializableExtra("wordCollectionVo");
        wordList = wordCollectionVo.getWords();
        mTitle.setText(wordCollectionVo.getName());
        makeDate.setText("만든 시간 : " + wordCollectionVo.getDate());
        answerRate.setText("정답률 : " +wordCollectionVo.getAnswerRate()+"%");
        mWordNum.setText("단어수 : " + wordCollectionVo.getWords().size() + "개");
        setmNoWordText();

        adapter = new MyWordAdapter(this, R.layout.word_information, wordList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                ConfirmWordDelete(position, wordList.get(position).getWord());
            }
        });

        mTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ModifyPage.this, PopupPage_ModifyWordCollectionName.class);
                startActivityForResult(intent, MODIFY_TITLE);
            }
        });

        addWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ModifyPage.this, PopupPage_AddWord.class);
                startActivityForResult(intent, ADD_WORD);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_WORD) {
            if(resultCode == -1) {
                String word = data.getStringExtra("word");
                String mean = data.getStringExtra("mean");
                wordList.add(new WordVo(word, mean));
                wordCollectionVo.setWords(wordList);
                wordCollectionVo.setAnswerRate(0.0);
                mWordNum.setText("단어수 : " + wordCollectionVo.getWords().size() + "개");
                answerRate.setText("정답률 : " + wordCollectionVo.getAnswerRate() + "%");
                setmNoWordText();
                adapter.notifyDataSetChanged();
                Toast.makeText(ModifyPage.this, "단어가 추가되었습니다.", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == MODIFY_TITLE) {
            if(resultCode == -1) {
                String modifyName = data.getStringExtra("modifyName");
                wordCollectionVo.setName(modifyName);
                mTitle.setText(wordCollectionVo.getName());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.modift_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        final int id = item.getItemId();
        switch (id) {
            case R.id.delete:
                ConfirmWordCollectionListDelete();
                break;
            case R.id.complete:
                ModyfyWordCollection();
                break;
            default:
                break;
        }
        return true;
    }

    public void ConfirmWordCollectionListDelete() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(ModifyPage.this);

        dialog.setTitle("정말로 삭제하시겠습니까?");
        dialog.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent();
                intent.putExtra("wordCollectionId", wordCollectionVo.getId());
                setResult(REMOVE_WORD_COLLECTION, intent);
                finish();
            }
        });
        dialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {}
        });
        dialog.show();
    }

    public void ConfirmWordDelete(final int position, final String word) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(ModifyPage.this);

        dialog.setTitle(word + "을(를) 삭제하시겠습니까?");
        dialog.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                wordList.remove(position);
                wordCollectionVo.setWords(wordList);
                wordCollectionVo.setAnswerRate(0.0);
                mWordNum.setText("단어수 : " + wordCollectionVo.getWords().size() + "개");
                answerRate.setText("정답률 : " + wordCollectionVo.getAnswerRate() + "%");
                setmNoWordText();
                adapter.notifyDataSetChanged();
                Toast.makeText(ModifyPage.this, "단어가 삭제되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {}
        });
        dialog.show();
    }

    public void ModyfyWordCollection() {
        Intent intent = new Intent();
        intent.putExtra("wordCollectionVo", wordCollectionVo);
        setResult(MODIFY_WORD_COLLECTION, intent);
        finish();
    }

    public void setmNoWordText() {
        if(wordList.size() == 0) {
            mNoWord.setVisibility(View.VISIBLE);
        } else {
            mNoWord.setVisibility(View.INVISIBLE);
        }
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
                view = View.inflate( mContext, R.layout.word_information, null);
            } else {
                view = convertView;
            }

            TextView word = (TextView) view.findViewById(R.id.word);
            TextView mean = (TextView)view.findViewById(R.id.mean);

            WordVo wordVoTmp = wordList.get(position);

            word.setText(wordVoTmp.getWord());
            mean.setText(wordVoTmp.getMean());

            return view;
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(ModifyPage.this);

        dialog.setTitle("변경사항을 저장하지 않고 나가시겠습니까?");
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
}
