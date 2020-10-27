package com.example.jonadan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class WordDatabase extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "wordCollection.db";

    public static String DATABASE_TABLE_WORDCOLLECTION = "wordCollection";  //테이블 이름
    public static String DATABASE_TABLE_WORDS = "words";

    public static int DATABASE_VERSION = 1;                                 //버전

    public static final String WORDCOLLECTIONID = "wordCollectionId";       //속성
    public static final String TITLE = "title";
    public static final String MAKEDATE = "makeDate";
    public static final String ANSWERRATE = "answerRate";

    public static final String WORDID = "wordId";
    public static final String WORD = "word";
    public static final String MEAN = "mean";

    public WordDatabase(Context context) { super(context, DATABASE_NAME, null, DATABASE_VERSION); }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + DATABASE_TABLE_WORDCOLLECTION + "("
                + WORDCOLLECTIONID + " INTEGER PRIMARY KEY,"
                + TITLE + " VARCHAR(1000), "
                + MAKEDATE + " DATETIME DEFAULT (datetime('now', '+9 hours')),"
                + ANSWERRATE + " FLOAT"
                + ");");

        db.execSQL("CREATE TABLE " + DATABASE_TABLE_WORDS + "("
                + WORDCOLLECTIONID + " INTEGER, "
                + WORDID + " INTERGER PRIMARY KEY, "
                + WORD + " VARCHAR(1000), "
                + MEAN + " VARCHAR(3000)"
                + ");");

        db.execSQL("INSERT INTO " + DATABASE_TABLE_WORDCOLLECTION + "(" + TITLE + "," +ANSWERRATE + ")"
                    + " VALUES " + "('견본 단어장', 0.0);");
        db.execSQL("INSERT INTO " + DATABASE_TABLE_WORDCOLLECTION + "(" + TITLE + "," +ANSWERRATE + ")"
                + " VALUES " + "('정처기 단어장', 0.0);");
        db.execSQL("INSERT INTO " + DATABASE_TABLE_WORDS + "(" + WORDCOLLECTIONID + "," +WORD + "," + MEAN + ")"
                + " VALUES " + "(1, 'apple', '사과');");
        db.execSQL("INSERT INTO " + DATABASE_TABLE_WORDS + "(" + WORDCOLLECTIONID + "," +WORD + "," + MEAN + ")"
                + " VALUES " + "(1, 'banana', '바나나');");
        db.execSQL("INSERT INTO " + DATABASE_TABLE_WORDS + "(" + WORDCOLLECTIONID + "," +WORD + "," + MEAN + ")"
                + " VALUES " + "(1, 'camera', '카메라');");
        db.execSQL("INSERT INTO " + DATABASE_TABLE_WORDS + "(" + WORDCOLLECTIONID + "," +WORD + "," + MEAN + ")"
                + " VALUES " + "(1, 'destory', '파괴하다');");
        db.execSQL("INSERT INTO " + DATABASE_TABLE_WORDS + "(" + WORDCOLLECTIONID + "," +WORD + "," + MEAN + ")"
                + " VALUES " + "(1, 'english', '영어');");
        db.execSQL("INSERT INTO " + DATABASE_TABLE_WORDS + "(" + WORDCOLLECTIONID + "," +WORD + "," + MEAN + ")"
                + " VALUES " + "(1, 'finish', '끝나다');");
        db.execSQL("INSERT INTO " + DATABASE_TABLE_WORDS + "(" + WORDCOLLECTIONID + "," +WORD + "," + MEAN + ")"
                + " VALUES " + "(1, 'global', '세계적인');");
        db.execSQL("INSERT INTO " + DATABASE_TABLE_WORDS + "(" + WORDCOLLECTIONID + "," +WORD + "," + MEAN + ")"
                + " VALUES " + "(1, 'hidden', '숨겨진');");
        db.execSQL("INSERT INTO " + DATABASE_TABLE_WORDS + "(" + WORDCOLLECTIONID + "," +WORD + "," + MEAN + ")"
                + " VALUES " + "(1, 'international', '국제적');");
        db.execSQL("INSERT INTO " + DATABASE_TABLE_WORDS + "(" + WORDCOLLECTIONID + "," +WORD + "," + MEAN + ")"
                + " VALUES " + "(1, 'july', '7월');");
        db.execSQL("INSERT INTO " + DATABASE_TABLE_WORDS + "(" + WORDCOLLECTIONID + "," +WORD + "," + MEAN + ")"
                + " VALUES " + "(2, '현행 시스템 파악', '어떤 하위 시스템으로 구성되고 있고, " +
                "제공 기능 및 연계 정보는 무엇이며 어떤 기술요소를 사용하는지를 파악하는 활동');");
        db.execSQL("INSERT INTO " + DATABASE_TABLE_WORDS + "(" + WORDCOLLECTIONID + "," +WORD + "," + MEAN + ")"
                + " VALUES " + "(2, '분석 모델 검증', '요구사항 도출 기법을 활용하여 업무 분석하고 제시한" +
                "분석 모델에 대해서 확인하는 활동');");
        db.execSQL("INSERT INTO " + DATABASE_TABLE_WORDS + "(" + WORDCOLLECTIONID + "," +WORD + "," + MEAN + ")"
                + " VALUES " + "(2, '소프트웨어 아키텍처', '시스템에 대한 기본 조직 체계로 시스템을 이루는" +
                "구성요소와 구성요소등 사이의 관계, 구성요소와 주변 환경들과의 관계 및 시스템의 진화와 설계를" +
                "지배하는 원칙');");
        db.execSQL("INSERT INTO " + DATABASE_TABLE_WORDS + "(" + WORDCOLLECTIONID + "," +WORD + "," + MEAN + ")"
                + " VALUES " + "(2, '프로토타이핑', '새로운 요구사항을 도출하기 위한 수단 또는 소프트웨어 요구사항에" +
                "대해 소프트웨어 엔지니어가 해석한 것을 확인하기 위한 수단으로 사용하는 기법');");
        db.execSQL("INSERT INTO " + DATABASE_TABLE_WORDS + "(" + WORDCOLLECTIONID + "," +WORD + "," + MEAN + ")"
                + " VALUES " + "(2, '푸트남 모형', '소프트웨어 개발 주기의 단계별로 요구할 인력의 분포를" +
                "가정하는 모형');");
        db.execSQL("INSERT INTO " + DATABASE_TABLE_WORDS + "(" + WORDCOLLECTIONID + "," +WORD + "," + MEAN + ")"
                + " VALUES " + "(2, '유스케이스 모델 검증', '시스템 기능에 대한 유스케이스 모형 상세화 수준 및" +
                "적정성 검증을 위해서 액터, 유스케이스, 유스케이스 명세서를 점검하는 방법');");
        db.execSQL("INSERT INTO " + DATABASE_TABLE_WORDS + "(" + WORDCOLLECTIONID + "," +WORD + "," + MEAN + ")"
                + " VALUES " + "(2, 'KDSI', '전체 소스 코드 라인 수를 1000라인으로 묶은 단위');");
        db.execSQL("INSERT INTO " + DATABASE_TABLE_WORDS + "(" + WORDCOLLECTIONID + "," +WORD + "," + MEAN + ")"
                + " VALUES " + "(2, '액터', '시스템의 외부에 있고, 시스템과 상호작용을 하는 사람 또는 시스템');");
        db.execSQL("INSERT INTO " + DATABASE_TABLE_WORDS + "(" + WORDCOLLECTIONID + "," +WORD + "," + MEAN + ")"
                + " VALUES " + "(2, '분석 자동화 도구', '요구사항을 자동으로 분석하고, 요구사항 분석 명세서를" +
                " 기술하도록 개발된 요구사항분석을 위한 자동화 도구');");
        db.execSQL("INSERT INTO " + DATABASE_TABLE_WORDS + "(" + WORDCOLLECTIONID + "," +WORD + "," + MEAN + ")"
                + " VALUES " + "(2, '릴레이션', '행과 열로 구성된 테이블');");
        db.execSQL("INSERT INTO " + DATABASE_TABLE_WORDS + "(" + WORDCOLLECTIONID + "," +WORD + "," + MEAN + ")"
                + " VALUES " + "(2, '이해관계자', '시스템 개발에 관련된 모든 사람과 조직');");
        db.execSQL("INSERT INTO " + DATABASE_TABLE_WORDS + "(" + WORDCOLLECTIONID + "," +WORD + "," + MEAN + ")"
                + " VALUES " + "(2, '뷰(View)', '서로 관련있는 관심사들의 집합이라는 관점에서 전체 시스템을 표현하는 것');");
        db.execSQL("INSERT INTO " + DATABASE_TABLE_WORDS + "(" + WORDCOLLECTIONID + "," +WORD + "," + MEAN + ")"
                + " VALUES " + "(2, '반 정규화', '정규화된 엔티티, 속성, 관계에 대해 성능 향상과 개발운영의 단순화를" +
                "위해 중복, 통합, 분리 등을 수행하는 데이터 모델링의 기법');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_WORDCOLLECTION);
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_WORDS);
        onCreate(db);
    }

    public void retriveWordCollection(ArrayList<WordCollectionVo> myWordCollectionList) {
        SQLiteDatabase db = getReadableDatabase();

        try(Cursor c = db.query(DATABASE_TABLE_WORDCOLLECTION, new String[] {WORDCOLLECTIONID, TITLE, MAKEDATE, ANSWERRATE},
                null, null, null, null, null))
        {
            myWordCollectionList.clear();

            if(c.moveToFirst()) {
                do {
                    WordCollectionVo wordCollectionVo = getWordCollection(c);
                    myWordCollectionList.add(wordCollectionVo);
                } while (c.moveToNext());
                c.close();
            }
        }
    }

    private WordCollectionVo getWordCollection(Cursor c) {
        int wordCollectionId = c.getInt(c.getColumnIndex(WORDCOLLECTIONID));
        String title = c.getString(c.getColumnIndex(TITLE));
        String makeDate = c.getString(c.getColumnIndex(MAKEDATE));
        double answerRate = c.getDouble(c.getColumnIndex(ANSWERRATE));

        WordCollectionVo wordCollectionVo = new WordCollectionVo(wordCollectionId, title, makeDate, answerRate);
        return wordCollectionVo;
    }

    public void retriveWord(WordCollectionVo wordCollectionVo) {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<WordVo> wordVoList = new ArrayList<WordVo>();

        try(Cursor c = db.query(DATABASE_TABLE_WORDS, new String[] {WORDCOLLECTIONID, WORD, MEAN},
                WORDCOLLECTIONID + " =?", new String[] {wordCollectionVo.getId()+""}, null, null, null, null))
        {
            if(c.moveToFirst()) {
                do {
                    WordVo wordVo = getWord(c);
                    wordVoList.add(wordVo);
                } while (c.moveToNext());
                c.close();

                wordCollectionVo.setWords(wordVoList);
            }
        }
    }

    private WordVo getWord(Cursor c) {
        String word = c.getString(c.getColumnIndex(WORD));
        String mean = c.getString(c.getColumnIndex(MEAN));

        WordVo wordVo = new WordVo(word, mean);
        return wordVo;
    }

    public void MakeWordCollection(String name) {
        SQLiteDatabase db = getReadableDatabase();
        db.execSQL("INSERT INTO " + DATABASE_TABLE_WORDCOLLECTION
                + "("+ TITLE + "," + ANSWERRATE +") " + "VALUES"
                + "('" + name + "',"
                + 0.0 + ");");
    }

    public void DeleteWordCollection(int id) {
        SQLiteDatabase db = getReadableDatabase();
        db.execSQL("DELETE FROM " + DATABASE_TABLE_WORDCOLLECTION
                    + " WHERE " + WORDCOLLECTIONID + " = " + id);
        db.execSQL("DELETE FROM " + DATABASE_TABLE_WORDS
                + " WHERE " + WORDCOLLECTIONID + " = " + id);
    }

    public void UpdateWordCollection(WordCollectionVo wordCollectionVo) {
        SQLiteDatabase db = getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put( TITLE, wordCollectionVo.getName());
        db.update( DATABASE_TABLE_WORDCOLLECTION, values, WORDCOLLECTIONID + "=?", new String[]{ wordCollectionVo.getId()+""});
    }

    public void UpdateWordCollectionAnswerRate(double answerRate, int wordCollectionId) {
        SQLiteDatabase db = getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put( ANSWERRATE, answerRate);
        db.update( DATABASE_TABLE_WORDCOLLECTION, values, WORDCOLLECTIONID + "=?", new String[]{ wordCollectionId+""});
    }

    public void UpdateWords(WordCollectionVo wordCollectionVo) {
        SQLiteDatabase db = getReadableDatabase();
        db.execSQL("DELETE FROM " + DATABASE_TABLE_WORDS
                + " WHERE " + WORDCOLLECTIONID + " = " + wordCollectionVo.getId());
        for(int i=0;i<wordCollectionVo.getWords().size();i++) {
            db.execSQL("INSERT INTO " + DATABASE_TABLE_WORDS
                    + "("+ WORDCOLLECTIONID + "," + WORD + "," + MEAN +") " + "VALUES"
                    + "(" + wordCollectionVo.getId() + ", '"
                    + wordCollectionVo.getWords().get(i).getWord() + "' , '"
                    + wordCollectionVo.getWords().get(i).getMean() + "');");
        }
    }
}
