package com.facts.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.facts.R;
import com.facts.controller.NavigationController;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public class FactsListActivity extends AppCompatActivity {
    private Toolbar mToolbar;

    protected Fragment createFragment() {
        return new FactsListFragmentNew();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_list_new);
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);

        initDrawer(savedInstanceState);
        initBottomNavigation();

        FragmentManager fm = getFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragmentContainer, fragment)
                    .commit();
        }

        NavigationController.getInstance().updateContext(this);
    }

    private void initDrawer(Bundle savedInstanceState) {
        new Drawer()
                .withActivity(this)
                .withToolbar(mToolbar)
                .withActionBarDrawerToggle(true)
                .withHeader(R.layout.drawer_header)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("All facts").withIcon(FontAwesome.Icon.faw_home).withIdentifier(0),
                        new PrimaryDrawerItem().withName("Top facts").withIcon(FontAwesome.Icon.faw_arrow_up).withIdentifier(1),
                        new PrimaryDrawerItem().withName("Random facts").withIcon(FontAwesome.Icon.faw_random).withIdentifier(2),
                        new PrimaryDrawerItem().withName("New facts").withIcon(FontAwesome.Icon.faw_clock_o).withIdentifier(3),
                        new PrimaryDrawerItem().withName("Offline").withIcon(FontAwesome.Icon.faw_cloud_download).withIdentifier(4),
                        new PrimaryDrawerItem().withName("Favorites").withIcon(FontAwesome.Icon.faw_star).withIdentifier(5),
                        new SectionDrawerItem().withName("Settings"),
                        new SecondaryDrawerItem().withName("Settings").withIcon(FontAwesome.Icon.faw_cog)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
                        if (drawerItem != null) {;
                            switch(drawerItem.getIdentifier()) {
                                case 0:
                                    NavigationController.getInstance().sortByDate();
                                    break;
                                case 1:
                                    NavigationController.getInstance().sortByPopularity();
                                    break;
                                case 2:
                                    NavigationController.getInstance().sortByRandom();
                                    break;
                                case 3:
                                    NavigationController.getInstance().showLatest();
                                    break;
                                case 4:
                                    NavigationController.getInstance().showOffline();
                                    break;
                                case 5:
                                    NavigationController.getInstance().showFavorites();
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                })
                .build();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("FactsListActivity", "onStop\n");
        NavigationController.getInstance().save();
    }


    private void initBottomNavigation() {
        BottomNavigationView bottomNavigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigation.inflateMenu(R.menu.my_navigation_items);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id) {
                    case R.id.action_prev:
                        NavigationController.getInstance().onPrevClicked();
                        break;
                    case R.id.action_next:
                        NavigationController.getInstance().onNextClicked();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }
}
