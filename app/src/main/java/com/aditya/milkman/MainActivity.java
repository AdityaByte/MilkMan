package com.aditya.milkman;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import com.aditya.milkman.ui.about.AboutFragment;
import com.aditya.milkman.ui.home.HomeFragment;
import com.google.android.material.navigation.NavigationView;

import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private TextView navHeaderText;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = getSharedPreferences("MILKMAN_DB", Context.MODE_PRIVATE);
        String user = sharedPreferences.getString("user", null);

        // Initializing the variables.
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        View view = navigationView.getHeaderView(0);
        navHeaderText = view.findViewById(R.id.nav_header_username);

        // Right now we haven't using this because of the color issue of the ActionBarDrawerToggle.
        // Now we have to create the ActionBarDrawerToggle to handle the open and close of the
        // nav bar.
        // ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);

        // Now we have to add the toggle as a Drawer Listener.
        // drawerLayout.addDrawerListener(toggle);

        // Synchronize the drawer state with the toggle button.
        // toggle.syncState();

        toolbar.setNavigationOnClickListener(value -> drawerLayout.openDrawer(GravityCompat.START));

        if ( savedInstanceState == null ) {
            replaceFragment(new HomeFragment());
            navigationView.setCheckedItem(R.id.nav_home);
        }

        // Here we need to set the Nav header username.
        navHeaderText.setText(user);

        // Setting a listener for when an item in the navigationView is selected.
        navigationView.setNavigationItemSelectedListener((menuItem) -> {
            if (menuItem.getItemId() == R.id.nav_home) {
                replaceFragment(new HomeFragment());
            } else if (menuItem.getItemId() == R.id.nav_about) {
                replaceFragment(new AboutFragment());
            }

            drawerLayout.closeDrawers();
            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    public void openCreateDataView(View view) {
        Intent intent = new Intent(this, CreateDataActivity.class);
        startActivity(intent);
    }
}
