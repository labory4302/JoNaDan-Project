package com.example.jonadan;

import java.io.Serializable;

public class WordVo implements Serializable {
    private String word;
    private String mean;

    WordVo(String word, String mean) {
        this.word = word;
        this.mean = mean;
    }

    public String getWord() { return word; }
    public void setWord(String word) { this.word = word; }

    public String getMean() { return mean; }
    public void setMean(String mean) { this.mean = mean; }
}
