package com.facts;

import android.graphics.Bitmap;

public class FactItem {
    private String mContent;
    private int mID;
    //http://muzey-factov.ru/img/facts/7088.png
    private String mImgUrl;
    private Bitmap mBitmap = null;

    public FactItem(int id, String content, String imgUrl) {
        mID = id;
        mContent = content;
        mImgUrl = "http://muzey-factov.ru" + imgUrl;
    }

    public String getContent() {
        return mContent;
    }

    public String getImgUrl() {
        return mImgUrl;
    }

    public void setBitmap(Bitmap bm) {
        mBitmap = bm;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public int getID() {
        return mID;
    }
}
