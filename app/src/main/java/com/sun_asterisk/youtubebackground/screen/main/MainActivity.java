package com.sun_asterisk.youtubebackground.screen.main;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import com.sun_asterisk.youtubebackground.R;
import com.sun_asterisk.youtubebackground.screen.home.HomeFragment;
import com.sun_asterisk.youtubebackground.screen.search.SearchFragment;
import com.sun_asterisk.youtubebackground.utils.Navigator;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public Navigator mNavigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle =
                new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
                        R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.navigationMain);
        navigationView.setNavigationItemSelectedListener(this);
        mNavigator = new Navigator();
        mNavigator.addFragment(MainActivity.this, HomeFragment.newInstance(),
                R.layout.fragment_home);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawerLayout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.actionSearch) {
            mNavigator.addFragment(MainActivity.this, SearchFragment.newInstance(),
                    R.layout.fragment_search);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigationHome:
                mNavigator.addFragment(MainActivity.this, HomeFragment.newInstance(),
                        R.layout.fragment_home);
                break;
            case R.id.navigationHistory:
                break;
            case R.id.navigationSetting:
                break;
            case R.id.navigationShare:
                break;
            case R.id.navigationIntroduce:
                break;
        }
        DrawerLayout drawer = findViewById(R.id.drawerLayout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
