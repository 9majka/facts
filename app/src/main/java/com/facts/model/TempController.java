package com.facts.model;

import java.util.Random;

/**
 * Created by Oleksandr on 19.03.2017.
 */

public class TempController {
    static int testCounter = 0;
    static int testCounterBest = 0;
    static boolean testBest = false;

    public static void prevClicked() {
        if(testBest) {
            testCounterBest-=10;
            if(testCounterBest < 0) {
                testCounterBest = 0;
            }
        } else {
            testCounter -= 10;
            if (testCounter < 0) {
                testCounter = 0;
            }
        }
        doAction();
    }
    public static void bestClicked() {
        testBest = !testBest;
        doAction();
    }
    public static void randClicked() {
        Random rand = new Random();
        int n = 10*rand.nextInt(330);
        if(testBest) {
            testCounterBest =n;
        } else {
            testCounter=n;
        }
        doAction();
    }
    public static void nextClicked() {
        if(testBest) {
            testCounterBest+=10;
            if(testCounterBest < 0) {
                testCounterBest = 0;
            }
        } else {
            testCounter+=10;
            if(testCounter < 0) {
                testCounter = 0;
            }
        }
        doAction();
    }

    private static void doAction() {
        if(testBest) {
            HttpModel.getInstance().loadBest(testCounterBest);
        } else {
            HttpModel.getInstance().loadNew(testCounter);
        }
    }
}
