package com.matloob.myresponsiveapp.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.matloob.myresponsiveapp.R;
import com.matloob.myresponsiveapp.models.Tag;
import com.matloob.myresponsiveapp.ui.home.HomeViewModel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private HomeViewModel homeViewModel;

    private final static String SELECTED_ITEM_ID_KEY = "selectedItemId";
    private int selectedItemId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup toolbar and drawer layout.
        setupNavigation();

        // Access the shared view model
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        // Observe changes in menu list
        homeViewModel.getTopTagsList().observe(this, new Observer<List<Tag>>() {
            @Override
            public void onChanged(List<Tag> tags) {
                // Update menu
                updateNavigationMenu(tags);
            }
        });

        if (savedInstanceState != null) {
            // retrieve selected item if any
            selectedItemId = savedInstanceState.getInt(SELECTED_ITEM_ID_KEY, -1);
            Log.i("TAG", "Checked item? " + selectedItemId);
        }
    }

    /**
     * Helper function to update navigation menu items
     *
     * @param tags a {@link List<Tag>} array
     */
    private void updateNavigationMenu(List<Tag> tags) {
        navigationView.getMenu().clear();

        Menu menu = navigationView.getMenu();
        Menu submenu = menu.addSubMenu("Top Tags");

        for (int i = 0; i < tags.size(); i++) {
            submenu.add(0, i + 1, 0, tags.get(i).getName()).setCheckable(true);
        }

        // If no item is selected before then select first item
        if (selectedItemId == -1) {
            navigationView.setCheckedItem(submenu.getItem(0));
            homeViewModel.setTagName(String.valueOf(submenu.getItem(0).getTitle()));
        }
    }

    /**
     * This function sets up the toolbar and navigation drawer menu.
     */
    private void setupNavigation() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        mAppBarConfiguration = new AppBarConfiguration.Builder().build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        navigationView.setNavigationItemSelectedListener(this);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onResume() {
        super.onResume();
        // Update layout
        updateLayoutVariation();
    }

    /**
     * This is helper function to update layout variation.
     */
    private void updateLayoutVariation() {
        // Get value from resource
        boolean isTabletOrLandscape = getResources().getBoolean(R.bool.isLandscapeOrTablet);

        if (isTabletOrLandscape) { // If landscape or tablet then lock the drawer open.
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);
            drawer.setScrimColor(Color.TRANSPARENT);
            // Hide hamburger icon
            toolbar.setNavigationIcon(null);
        } else { // unlock the drawer and retrieve the default shadow color.
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            drawer.setScrimColor(0x99000000);
            drawer.close();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Save selected item if any
        outState.putInt(SELECTED_ITEM_ID_KEY, navigationView.getCheckedItem() != null ? navigationView.getCheckedItem().getItemId() : -1);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Log.i("TAG", "Selected " + item.getItemId());
        navigationView.setCheckedItem(item.getItemId());
        homeViewModel.setTagName(String.valueOf(item.getTitle()));
        drawer.close();
        return false;
    }
}
