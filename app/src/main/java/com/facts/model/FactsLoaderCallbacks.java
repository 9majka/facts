package com.facts.model;

import com.facts.FactItems;

public interface FactsLoaderCallbacks {
    void onFactsCreated(FactItems items);
    void onFactsUpdated(FactItems items);
    void onError();
}
