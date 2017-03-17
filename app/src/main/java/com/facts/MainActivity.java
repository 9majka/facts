package com.facts;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.facts.view.ListAdapter;

import java.util.Random;

public class MainActivity extends Activity implements HttpModelObserver {
    static int testCounter = 0;
    static int testCounterBest = 0;
    static boolean testBest = false;
    private int controlCounter = 0;

    @Override
    public void onFactsUpdate(FactItems items) {
        if(items == null) {
            return;
        }
        ListView listView = (ListView)findViewById(R.id.listView);

        ListAdapter listAdapter = new ListAdapter(this, R.layout.factlist, items);
        listView.setAdapter(listAdapter);
    }

    @Override
    public void onFactsReady(FactItems items) {
        if(items == null) {
            return;
        }
        ListView listView = (ListView)findViewById(R.id.listView);

        ListAdapter listAdapter = new ListAdapter(this, R.layout.factlist, items);
        listView.setAdapter(listAdapter);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActionBar().hide();
        HttpModel.getInstance().setObserver(this);

        test();

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testAction(true);
            }
        });
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testAction(false);
            }
        });
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testBest = !testBest;
                test();
            }
        });

        findViewById(R.id.go).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go();
            }
        });
    }

    private void go() {

        Random rand = new Random();
        int n = 10*rand.nextInt(330) + 1;
        if(testBest) {
            testCounterBest =n;
        } else {
            testCounter=n;
        }
        test();
    }

    private void testAction(boolean next) {
        if(testBest) {
            if(next) {
                testCounterBest+=10;
            } else{
                testCounterBest-=10;
            }
            if(testCounterBest < 0) {
                testCounterBest = 0;
            }
            HttpModel.getInstance().loadBest(testCounterBest);

        } else {
            if(next) {
                testCounter+=10;
            } else{
                testCounter-=10;
            }
            if(testCounter < 0) {
                testCounter = 0;
            }
            HttpModel.getInstance().loadNew(testCounter);
        }
    }
    private void test() {
        if(testBest) {
            if(testCounterBest < 0) {
                testCounterBest = 0;
            }
            HttpModel.getInstance().loadBest(testCounterBest);

        } else {
            if(testCounter < 0) {
                testCounter = 0;
            }
            HttpModel.getInstance().loadNew(testCounter);
        }
    }
}
