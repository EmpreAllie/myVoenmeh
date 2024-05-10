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
import com.my.voenmeh.Authentication.UserRepository;
import com.my.voenmeh.R;

public class TrackerActivity extends AppCompatActivity {

    /**
     * Сколько конкретного предмета в семе - можно посмотреть
     * в ХешМапе Subjects в классе userReposiory, она заполняется
     * при первом открытии этого активити
     *
     * Ахуеть нахуй
     * фронтендеры не ЛОХИ
     */


    private void getSubjects() {
        Thread GettingSubjects; //второй поток во избежание перегрузки мэйна
        Runnable runnable;
        runnable = new Runnable() {
            @Override
            public void run() {
                UserRepository.GetSchedule();
            }
        };
        GettingSubjects = new Thread(runnable); //запускаем поток
        GettingSubjects.start();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSubjects();

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tracker);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.tracker), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            v.setPadding(0, systemBars.top, 0, 0);
            return insets;
        });

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setSelectedItemId(R.id.navigation_tracker);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();

                if (id == R.id.navigation_news) {
                    startActivity(new Intent(getApplicationContext(), NewsActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                }
                else if (id == R.id.navigation_tracker) {

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