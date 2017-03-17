package com.facts;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.facts.view.ListAdapter;

public class MainActivity extends Activity implements HttpModelObserver {
    static int testCounter = 0;

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
        test(testCounter);
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(testCounter < 0) {
                    testCounter = 0;
                }
                test(testCounter);
                testCounter+=10;
            }
        });
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(testCounter < 0) {
                    testCounter = 0;
                }
                test(testCounter);
                testCounter-=10;
            }
        });

    }
    private void test(final int from) {
        HttpModel.getInstance().loadNew(from);
    }

}
