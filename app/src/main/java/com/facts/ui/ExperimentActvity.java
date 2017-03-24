package com.facts.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;


import com.facts.R;

public class ExperimentActvity extends AppCompatActivity {
    RecyclerView view = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experiment_actvity);
        view = (RecyclerView)findViewById(R.id.recycleView);
        view.setLayoutManager(new LinearLayoutManager(this));
    }

    private class TestHolder extends RecyclerView.ViewHolder {

        public TestHolder(View itemView) {
            super(itemView);
        }
    }

    private class TestAdapter extends RecyclerView.Adapter<TestHolder> {

        TestAdapter() {

        }

        @Override
        public TestHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(TestHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }
}
