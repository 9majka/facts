package com.facts.model;

import com.facts.FactItems;

public interface FactsLoaderObserver {
    void onFactsReady(FactItems items);
    void onFactsUpdate(FactItems items);
}
