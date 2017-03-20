package com.facts.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.view.View;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;

import com.facts.R;
import com.facts.model.TempController;

public class FactsListActivity extends AppCompatActivity {

    protected Fragment createFragment() {
        return new FactsListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_list);
        //getActionBar().hide();

        BottomNavigationView bottomNavigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigation.inflateMenu(R.menu.my_navigation_items);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
         @Override
         public boolean onNavigationItemSelected(@NonNull MenuItem item) {
             int id = item.getItemId();
             switch(id) {
                 case R.id.action_prev:
                     TempController.prevClicked();
                     break;
                 case R.id.action_best:
                     TempController.bestClicked();
                     break;
                 case R.id.action_go:
                     TempController.randClicked();
                     break;
                 case R.id.action_next:
                     TempController.nextClicked();
                     break;
                 default:
                     break;
             }
             return true;
         }
     });

        FragmentManager fm = getFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragmentContainer, fragment)
                    .commit();
        }
    }
}
