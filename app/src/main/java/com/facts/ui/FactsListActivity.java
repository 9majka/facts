package com.facts.ui;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.View;

import com.facts.R;
import com.facts.model.TempController;

public class FactsListActivity extends Activity implements View.OnClickListener {

    protected Fragment createFragment() {
        return new FactsListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_list);
        getActionBar().hide();
        FragmentManager fm = getFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragmentContainer, fragment)
                    .commit();
        }
        findViewById(R.id.prev).setOnClickListener(this);
        findViewById(R.id.next).setOnClickListener(this);
        findViewById(R.id.rand).setOnClickListener(this);
        findViewById(R.id.best).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.prev:
                TempController.prevClicked();
                break;
            case R.id.best:
                TempController.bestClicked();
                break;
            case R.id.rand:
                TempController.randClicked();
                break;
            case R.id.next:
                TempController.nextClicked();
                break;
            default:
                break;
        }
    }
}
