package com.facts.controller;

import com.facts.FactItem;
import com.facts.FactItems;

public class FactsHolder {
    private FactItems mFacts = null;
    private static FactsHolder sInstance = null;
    public static FactsHolder getInstance() {
        if(sInstance == null) {
            sInstance = new FactsHolder();
        }
        return sInstance;
    }

    public FactItem getFactById(int id) {
        if(mFacts != null) {
            for (FactItem fact : mFacts) {
                if(fact.getID() == id) {
                    return fact;
                }
            }
        }
        return null;
    }

    void setCurrentFactItems(FactItems items) {
        mFacts = items;
    }

    public FactItems getCurrentFactItems() {
        return mFacts;
    }
}
