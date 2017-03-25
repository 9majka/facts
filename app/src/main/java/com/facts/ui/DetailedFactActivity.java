package com.facts.ui;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import com.facts.R;

public class DetailedFactActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        int factId = getIntent().getIntExtra(DetailedFactFragment.ARG_FACT_ID, 0);
        return DetailedFactFragment.newInstance(factId);
    }

    public static Intent newIntent(Context packageContext, int factId) {
        Intent intent = new Intent(packageContext, DetailedFactActivity.class);
        intent.putExtra(DetailedFactFragment.ARG_FACT_ID, factId);
        return intent;
    }

}
