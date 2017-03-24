package com.facts.controller;

import com.facts.FactItem;
import com.facts.FactItems;

public interface IFactsHolder {
    FactItems prepareNextFacts();
    FactItems preparePreviousFacts();
    FactItems getCurrentFacts();
    FactItem getFactById(int id);
}
