package com.facts;

/**
 * Created by oshch on 3/13/17.
 */

public class FactItem {
    private String mContent;
    private int mID;
    private String mImgUrl;

    public FactItem(String content) {
        mContent = content;
    }

    public String getContent() {
        return mContent;
    }
}
