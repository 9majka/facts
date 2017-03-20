package com.facts.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.facts.R;
import com.facts.controller.TempController;

public class FactsListActivity extends AppCompatActivity {
    private String[] mPlanetTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    protected Fragment createFragment() {
        return new FactsListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_list);

        BottomNavigationView bottomNavigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigation.inflateMenu(R.menu.my_navigation_items);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
         @Override
             public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                 int id = item.getItemId();
                 switch(id) {
                     case R.id.action_prev:
                         TempController.onPrevClicked();
                         break;
                     case R.id.action_next:
                         TempController.onNextClicked();
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
        mPlanetTitles = new String[] {
                "New",
                "Top",
                "Random",
                "Offline"
        };
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mPlanetTitles));
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.string.action_best,  /* "open drawer" description */
                R.string.action_best  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(R.string.app_name);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Navigation");
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position) {
                    case 0:
                        TempController.onNewClicked();
                        break;
                    case 1:
                        TempController.onTopClicked();
                        break;
                    case 2:
                        TempController.onRandClicked();
                        break;
                    case 3:
                        TempController.onOfflineClicked();
                        break;
                    default:
                        break;
                }
                mDrawerList.setItemChecked(position, true);
                mDrawerLayout.closeDrawer(mDrawerList);
            }
        });
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }
}
