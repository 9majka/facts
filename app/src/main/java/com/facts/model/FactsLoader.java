package com.facts.model;

import com.facts.FactItem;
import com.facts.FactItems;

public class FactsLoader {
    private FactItems mFacts = null;
    private static FactsLoader sInstance = null;
    public static FactsLoader getInstance() {
        if(sInstance == null) {
            sInstance = new FactsLoader();
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
