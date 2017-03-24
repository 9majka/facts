package com.facts.model;

import com.facts.FactItem;
import com.facts.FactItems;
import com.facts.controller.IFactsHolder;

public class OfflineFactsHolder implements IFactsHolder {

    public boolean saveFact(FactItem item) {
        return false;
    }

    @Override
    public FactItems prepareNextFacts() {
        return null;
    }

    @Override
    public FactItems preparePreviousFacts() {
        return null;
    }

    @Override
    public FactItems getCurrentFacts() {
        return null;
    }

    @Override
    public FactItem getFactById(int id) {
        return null;
    }
}
