package com.my.voenmeh.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.my.voenmeh.R;

public class NewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_news);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.news), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setSelectedItemId(R.id.navigation_news);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();

                if (id == R.id.navigation_news) {

                    return true;
                }
                else if (id == R.id.navigation_tracker) {
                    startActivity(new Intent(getApplicationContext(), TrackerActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                }
                else if (id == R.id.navigation_schedule) {
                    startActivity(new Intent(getApplicationContext(), ScheduleActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                }
                else if (id == R.id.navigation_mail) {
                    startActivity(new Intent(getApplicationContext(), MailActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                }
                else if (id == R.id.navigation_service) {
                    startActivity(new Intent(getApplicationContext(), ServicesActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                }

                return false;
            }
        });
    }
}