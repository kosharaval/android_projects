package com.example.wordsgame;

public class Word
{
    private int mId;
    private String mWord;

    public Word() { }

    public Word(String mWord) {
        this.mWord = mWord;
    }

    public int getmId() { return mId; }

    public String getmWord() { return mWord; }

    public void setmId(int mId) { this.mId = mId; }

    public void setmWord(String mWord) { this.mWord = mWord; }
}
