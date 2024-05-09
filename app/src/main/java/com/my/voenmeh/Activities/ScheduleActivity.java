package com.my.voenmeh.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.my.voenmeh.Authentication.UserRepository;
import com.my.voenmeh.R;
import com.my.voenmeh.Schedule.Schedule;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ScheduleActivity extends AppCompatActivity {

    String GroupToShow = "О721Б";
    Schedule schedule = new Schedule();

    private void pullSchedule() {
        Thread GettingSchedule; //второй поток во избежание перегрузки мэйна
        Runnable runnable;
        runnable = new Runnable() {
            @Override
            public void run() {
                schedule.PullSchedule(GroupToShow);
            }
        };
        GettingSchedule = new Thread(runnable); //запускаем поток
        GettingSchedule.start();
    }

    // Метод для отображения расписания
    private void displaySchedule() {
        TextView textViewSchedule = findViewById(R.id.textViewSchedule);
        StringBuilder scheduleText = new StringBuilder("Расписание для группы " + GroupToShow + ":\n\n");

        // Добавление расписания четной недели
        scheduleText.append("Четная неделя:\n");
        for (Schedule.Day day : schedule.GetWeek(true)) {
            scheduleText.append(day.toString());
        }

        // Добавление расписания нечетной недели
        scheduleText.append("\nНечетная неделя:\n");
        for (Schedule.Day day : schedule.GetWeek(false)) {
            scheduleText.append(day.toString());
        }

        // Установка текста в TextView
        textViewSchedule.setText(scheduleText.toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_schedule);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.schedule), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            v.setPadding(0, systemBars.top, 0, 0);
            return insets;
        });

        pullSchedule();

        // Ожидание завершения потока перед отображением расписания
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                displaySchedule();
            }
        }, 5000); // Задержка в 5 секунд, предполагается, что за это время
        // расписание будет загружено

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setSelectedItemId(R.id.navigation_schedule);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();

                if (id == R.id.navigation_news) {
                    startActivity(new Intent(getApplicationContext(), NewsActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (id == R.id.navigation_tracker) {
                    startActivity(new Intent(getApplicationContext(), TrackerActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (id == R.id.navigation_schedule) {
                    // Текущая активность, ничего не делаем
                    return true;
                } else if (id == R.id.navigation_mail) {
                    startActivity(new Intent(getApplicationContext(), MailActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (id == R.id.navigation_service) {
                    startActivity(new Intent(getApplicationContext(), ServicesActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                }

                return false;
            }
        });
    }
}