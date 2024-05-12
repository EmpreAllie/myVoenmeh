package com.my.voenmeh.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
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
        Thread GettingSchedule; // Второй поток для избежания перегрузки основного потока
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                schedule.PullSchedule(GroupToShow);
            }
        };
        GettingSchedule = new Thread(runnable); // Запуск потока
        GettingSchedule.start();
        try {
            GettingSchedule.join(); // Ожидание завершения потока
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Метод для отображения расписания в зависимости от типа недели
    private void displaySchedule(boolean isOddWeek) {
        // Получение TableLayout
        TableLayout tableLayout = findViewById(R.id.tableLayoutSchedule);
        tableLayout.removeAllViews(); // Очистка предыдущих строк

        // Определение типа недели и добавление заголовка недели
        String weekType = isOddWeek ? "Нечетная неделя" : "Четная неделя";
        addScheduleRow(tableLayout, weekType, "", "", "");

        // Проход по каждому дню в выбранной неделе
        for (Schedule.Day day : schedule.GetWeek(isOddWeek)) {
            // Добавление заголовка дня
            addScheduleRow(tableLayout, day.dayName, "", "", "");

            // Добавление информации о времени, предмете, преподавателе и месте
            for (int i = 0; i < day.time.size(); i++) {
                addScheduleRow(tableLayout, day.time.get(i), day.subject.get(i), day.teacher.get(i), day.place.get(i));
            }
        }
    }

    // Метод для добавления строки в TableLayout
    private void addScheduleRow(TableLayout tableLayout, String time, String subject, String teacher, String place) {
        // Добавление TextView для времени
        TableRow tableRowTime = new TableRow(this);
        TextView textViewTime = new TextView(this);
        textViewTime.setText(time);
        tableRowTime.addView(textViewTime);
        tableLayout.addView(tableRowTime, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

        // Добавление TextView для предмета
        TableRow tableRowSubject = new TableRow(this);
        TextView textViewSubject = new TextView(this);
        textViewSubject.setText(subject);
        textViewSubject.setTextColor(Color.RED); // Пример установки цвета текста
        tableRowSubject.addView(textViewSubject);
        tableLayout.addView(tableRowSubject, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

        // Добавление TextView для преподавателя
        TableRow tableRowTeacher = new TableRow(this);
        TextView textViewTeacher = new TextView(this);
        textViewTeacher.setText(teacher);
        textViewTeacher.setTextColor(Color.MAGENTA); // Пример установки цвета текста
        tableRowTeacher.addView(textViewTeacher);
        tableLayout.addView(tableRowTeacher, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

        // Добавление TextView для места
        TableRow tableRowPlace = new TableRow(this);
        TextView textViewPlace = new TextView(this);
        textViewPlace.setText(place);
        textViewPlace.setTextColor(Color.YELLOW); // Пример установки цвета текста
        tableRowPlace.addView(textViewPlace);
        tableLayout.addView(tableRowPlace, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        // Инициализация и другие настройки...

        pullSchedule(); // Загрузка расписания

        Switch bebra = findViewById(R.id.schSwitch);
        bebra.setChecked(false); // Установка начального состояния в false

        displaySchedule(false); // Отображение расписания для "четной недели" при запуске

        bebra.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                displaySchedule(isChecked); // Обновление расписания в зависимости от состояния Switch
            }
        });

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
        }); //////////////-0//
    }
}