package com.facts.model;

import com.facts.FactItems;

public interface HttpModelObserver {
    void onFactsReady(FactItems items);
    void onFactsUpdate(FactItems items);
}
