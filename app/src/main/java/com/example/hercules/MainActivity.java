package com.example.hercules;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hercules.model.User;
import com.example.hercules.view.ClosetFragment;
import com.example.hercules.view.HomeFragment;
import com.example.hercules.view.QuestsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.hercules.model.admin_overhead.Admin;
import com.example.hercules.model.admin_overhead.Stats;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    private Admin admin;
    public User user; //The current user
    public Boolean lastPage = Boolean.FALSE; // Default home page
    private HomeFragment homeFragment;
    private ClosetFragment closetFragment;
    private QuestsFragment questsFragment;

    private static MainActivity instance;

    private void initializeUser() {
        user = admin.createUser("69420","password","Achilles","2002-01-01", Admin.GoalTypes.CARDIO.toString());
        initializeUserStats(user.getUserStats());
    }

    private void initializeUserStats(Stats s) {
        s.setBench(250);
        s.setCurrentStreak(6);
        s.setLongestStreak(9);
        s.setExperience(80);
        s.setGold(420);
        s.setHeight(183);
        s.setLevel(10);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            instance = this;
            setContentView(R.layout.activity_main);
            // Initialize views AFTER setContentView
            bottomNavigationView = findViewById(R.id.bottomNav);
            admin = new Admin();
            initializeUser();
            homeFragment = HomeFragment.getInstance(user);
            closetFragment = ClosetFragment.getInstance(user);
            questsFragment = QuestsFragment.getInstance(user);
            bottomNavigationView = findViewById(R.id.bottomNav);
            bottomNavigationView.setOnItemSelectedListener(bottomNavMethod);
            getSupportFragmentManager().beginTransaction().add(R.id.container, homeFragment, homeFragment.getTag()).commit();
        } catch (Exception e) {
            Log.e("MainActivity", "Error occurred", e);
        }
    }

    private final BottomNavigationView.OnItemSelectedListener bottomNavMethod = menuItem -> {
        int id = menuItem.getItemId();
        if (id == R.id.nav_home) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment, homeFragment.getTag()).commit();
            lastPage = false;
        } else if (id == R.id.nav_closet) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, closetFragment, closetFragment.getTag()).commit();
            lastPage = true;
        } else if (id == R.id.nav_quests) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, questsFragment, questsFragment.getTag()).commit();
        }
        return true;
    };

    public Admin getAdmin() {
        return admin;
    }

    public User getUser() {
        return user;
    }

    public static MainActivity getInstance() {
        return instance;
    }

    public Boolean getLastPage() {
        return lastPage;
    }
}