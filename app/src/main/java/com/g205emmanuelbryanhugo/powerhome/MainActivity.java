package com.g205emmanuelbryanhugo.powerhome;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import android.view.MenuItem;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.navigation.NavigationView;
import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toolbar configuration
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize DrawerLayout
        drawerLayout = findViewById(R.id.drawer_layout);

        // Initialize NavController with correct setup
        Fragment navHostFragment = getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        if (navHostFragment != null) {
            navController = NavHostFragment.findNavController(navHostFragment);
        
            // Setup NavigationView
            NavigationView navigationView = findViewById(R.id.nav_view);
            NavigationUI.setupWithNavController(navigationView, navController);

            // Setup ActionBar
            NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout);
        }

        // Handle back button
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    if (isEnabled()) {
                        setEnabled(false);
                        getOnBackPressedDispatcher().onBackPressed();
                    }
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, drawerLayout) 
            || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle the navigation menu items
        int id = item.getItemId();

        // Navigation to specific fragments based on the selected item
        if (id == R.id.nav_home) {
            navController.navigate(R.id.homeFragment);  // Navigate to Home Fragment
        } else if (id == R.id.nav_my_habitat) {
            navController.navigate(R.id.myHabitatFragment);  // Navigate to My Habitat Fragment
        }

        // Close the drawer after selecting an item
        drawerLayout.closeDrawer(GravityCompat.START);
        return super.onOptionsItemSelected(item);
    }
}
