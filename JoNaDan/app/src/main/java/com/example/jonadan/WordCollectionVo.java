package com.example.jonadan;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

//단어모음 VO클래스
public class WordCollectionVo implements Serializable {
    private int id;                                 //단어모음 ID
    private String name;                            //단어모음 제목
    private String date;                              //단어모음 생성 시간
    private ArrayList<WordVo> wordVoList;
    private double answerRate;                      //단어모음 정답률

    WordCollectionVo(String name) {
        this.name = name;
        wordVoList = new ArrayList<WordVo>();
        answerRate = 0.0;
    }

    WordCollectionVo(int id, String name, String date, double answerRate) {
        this.id = id;
        this.name = name;
        wordVoList = new ArrayList<WordVo>();
        this.date = date;
        this.answerRate = answerRate;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDate() { return date; }

    public ArrayList<WordVo> getWords() { return wordVoList; }
    public void setWords(ArrayList<WordVo> wordVo) { this.wordVoList = wordVo; }

    public double getAnswerRate() { return Math.round(answerRate*100)/100.0; }
    public void setAnswerRate(double answerRate) { this.answerRate = answerRate; }
}