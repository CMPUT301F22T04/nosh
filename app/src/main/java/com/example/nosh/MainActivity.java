package com.example.nosh;

import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.startup.AppInitializer;

import com.example.nosh.database.DBControllerFactoryInitializer;
import com.example.nosh.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onStart() {
        super.onStart();

        AppInitializer.getInstance(this).
                initializeComponent(DBControllerFactoryInitializer.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.example.nosh.databinding.ActivityMainBinding binding =
                ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_ingredients, R.id.nav_recipes, R.id.nav_list, R.id.nav_plan
        )
                .setOpenableLayout(drawer)
                .build();

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_content_main);

        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();

        NavigationUI.setupWithNavController(binding.bottomBar.bottomNavigation,
                navController);
        NavigationUI.setupActionBarWithNavController(this, navController,
                mAppBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this,
                R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) ||
                super.onSupportNavigateUp();
    }
}