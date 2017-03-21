package com.facts.controller;

import android.content.Context;
import android.content.SharedPreferences;

import com.facts.model.FactsLoader;

import java.lang.ref.WeakReference;
import java.util.Random;

public class NavigationController implements INavigationControl {
    private int mCounterByDate = 0;
    private int mCounterByTop = 0;
    private int mCounterRand = 0;
    private int mCounterLatest = 0;
    private static final String PREFS_NAME = "com.facts.controller.NavigationControllerPrefs";
    private static NavigationController sInstance = null;

    private WeakReference<Context> mContextPtr = null;
    private ViewType mViewType = ViewType.DATE;

    public static NavigationController getInstance() {
        if(sInstance == null) {
            sInstance = new NavigationController();
        }
        return sInstance;
    }

    public void updateContext(Context ctx) {
        mContextPtr = new WeakReference<Context> (ctx);
    }

    public void restore() {
        restoreSettings();
        doAction(0);
    }

    public void save() {
        saveSettings();
    }

    public void sortByDate() {
        mViewType = ViewType.DATE;
        doAction(0);
    }

    public void sortByPopularity() {
        mViewType = ViewType.TOP;
        doAction(0);
    }

    public void sortByRandom() {
        mViewType = ViewType.RAND;
        Random rand = new Random();
        mCounterRand = 10*rand.nextInt(330);
        doAction(0);
    }

    public void showLatest() {
        mViewType = ViewType.LATEST;
        mCounterLatest = 0;
        doAction(0);
    }

    public void showOffline() {
        //mViewType = Type.OFFLINE;
        // TODO
    }

    public void onPrevClicked() {
        doAction(-10);
    }

    public void onNextClicked() {
        doAction(10);
    }

    private void doAction(int shift) {
        switch (mViewType) {
            case DATE:
                mCounterByDate = mCounterByDate + shift;
                if(mCounterByDate < 0) mCounterByDate = 0;
                FactsLoader.getInstance().loadNew(mCounterByDate);
                break;
            case TOP:
                mCounterByTop = mCounterByTop + shift;
                if(mCounterByTop < 0) mCounterByTop = 0;
                FactsLoader.getInstance().loadBest(mCounterByTop);
                break;
            case RAND:
                mCounterRand = mCounterRand + shift;
                if(mCounterRand < 0) mCounterRand = 0;
                FactsLoader.getInstance().loadNew(mCounterRand);
                break;
            case LATEST:
                mCounterLatest = mCounterLatest + shift;
                if(mCounterLatest < 0) mCounterLatest = 0;
                FactsLoader.getInstance().loadNew(mCounterLatest);
                break;
            case OFFLINE:
                break;
        }
    }

    private void restoreSettings(){
        // Restore preferences
        SharedPreferences settings = mContextPtr.get().getSharedPreferences(PREFS_NAME, 0);
        mViewType = ViewType.fromInt(settings.getInt("viewType", 0));
        mCounterByDate = settings.getInt("counterNew", 0);
        mCounterByTop = settings.getInt("counterBest", 0);
        mCounterRand = settings.getInt("counterRand", 0);
    }

    private void saveSettings(){

        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences settings = mContextPtr.get().getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();

        editor.putInt("viewType", mViewType.getInt());
        editor.putInt("counterNew", mCounterByDate);
        editor.putInt("counterBest", mCounterByTop);
        editor.putInt("counterRand", mCounterRand);
        // Commit the edits!
        editor.apply();
    }

    private enum ViewType {
        DATE(0),
        TOP(1),
        RAND(2),
        LATEST(3),
        OFFLINE(4);

        public static final ViewType values[] = values();
        int mtype;

        ViewType(int type) {
            mtype  = type;
        }

        int getInt() {
            return mtype;
        }

        static ViewType fromInt(int ordinal) {
            return values[ordinal];
        }
    }

}
