package com.facts;

/**
 * Created by oshch on 3/13/17.
 */

interface HttpModelObserver {
    void onFactsReady(FactItems items);
    void onFactsUpdate(FactItems items);
}
