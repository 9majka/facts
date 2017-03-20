package com.facts.controller;

import com.facts.model.HttpModel;

import java.util.Random;

/**
 * Created by Oleksandr on 19.03.2017.
 */

public class TempController {
    static int testCounterNew = 0;
    static int testCounterBest = 0;
    static int testCounterRand = 0;
    static Type mType = Type.NEW;

    private enum Type {
        NEW,
        TOP,
        RAND,
        OFFLINE
    }

    public static void onNewClicked() {
        mType = Type.NEW;
        HttpModel.getInstance().loadNew(testCounterNew);
    }
    public static void onTopClicked() {
        mType = Type.TOP;
        HttpModel.getInstance().loadBest(testCounterBest);
    }
    public static void onRandClicked() {
        mType = Type.RAND;
        Random rand = new Random();
        testCounterRand = 10*rand.nextInt(330);
        HttpModel.getInstance().loadNew(testCounterRand);
    }
    public static void onOfflineClicked() {
        //mType = Type.OFFLINE;
        // TODO
    }
    public static void onPrevClicked() {
        switch (mType) {
            case NEW:
                testCounterNew -= 10;
                if(testCounterNew < 0) testCounterNew = 0;
                HttpModel.getInstance().loadNew(testCounterNew);
                break;
            case TOP:
                testCounterBest -= 10;
                if(testCounterBest < 0) testCounterBest = 0;
                HttpModel.getInstance().loadBest(testCounterBest);
                break;
            case RAND:
                testCounterRand -= 10;
                if(testCounterRand < 0) testCounterRand = 0;
                HttpModel.getInstance().loadNew(testCounterRand);
                break;
            case OFFLINE:
                break;
        }
    }
    public static void onNextClicked() {
        switch (mType) {
            case NEW:
                testCounterNew += 10;
                if(testCounterNew < 0) testCounterNew = 0;
                HttpModel.getInstance().loadNew(testCounterNew);
                break;
            case TOP:
                testCounterBest += 10;
                if(testCounterBest < 0) testCounterBest = 0;
                HttpModel.getInstance().loadBest(testCounterBest);
                break;
            case RAND:
                testCounterRand += 10;
                if(testCounterRand < 0) testCounterRand = 0;
                HttpModel.getInstance().loadNew(testCounterRand);
                break;
            case OFFLINE:
                break;
        }
    }

}
