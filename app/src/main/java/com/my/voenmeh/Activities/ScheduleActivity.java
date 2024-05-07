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

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ScheduleActivity extends AppCompatActivity {

    private String GroupToShow = "О723Б";
    ArrayList<Day> EvenWeek = new ArrayList<Day>();
    ArrayList<Day> OddWeek = new ArrayList<Day>();

    private class Day {
        ArrayList<String> time, teacher, subject, place;
        String dayName;

        public Day(String DayName) {
            dayName = DayName;
            time = new ArrayList<String>();
            teacher = new ArrayList<String>();
            subject = new ArrayList<String>();
            place = new ArrayList<String>();
        }

        public void Append(String _time, String _teacher, String _subject, String _place) {
            time.add(_time);
            teacher.add(_teacher);
            subject.add(_subject);
            place.add(_place);
        }

        @Override
        @NonNull
        public String toString() {
            String result = "";
            result += dayName + "\n";
            for (int i = 0, n = time.size(); i < n; i++) {
                result += time.get(i) + " " + teacher.get(i) + " " + subject.get(i) + " " + place.get(i) + "\n";
            }
            result += "\n";
            return result;
        }
    }

    ;


    /**
     * пуллим_расписание_начало
     **/
    private void pullSchedule() {
        Thread GettingSchedule; //второй поток во избежание перегрузки мэйна
        Runnable runnable;
        runnable = new Runnable() {
            @Override
            public void run() {
                getWeb();
            }
        };
        GettingSchedule = new Thread(runnable); //запускаем поток
        GettingSchedule.start();
    }

    private void getWeb() {
        Document WebSchedule;
        try {
            WebSchedule = Jsoup.connect("http://www.voenmeh.com/schedule_green.php").get();
            Elements semesters = WebSchedule.getElementsByTag("option");
            String group_id = "", semester_id = semesters.get(0).val();
            String url = "http://www.voenmeh.com/schedule_green.php?semestr_id=" + semester_id + "&page_mode=group";
            WebSchedule = Jsoup.connect(url).get();
            Elements groups = WebSchedule.getElementsByTag("option");
            for (Element el : groups) {
                if (el.text().equals(GroupToShow)) {
                    group_id = el.val();
                }
            }
            url = "http://www.voenmeh.com/schedule_green.php?group_id=" + group_id + "&semestr_id=" + semester_id;
            WebSchedule = Jsoup.connect(url).get();
            Elements dayNames = WebSchedule.getElementsByClass("day");
            Elements dayInfo = WebSchedule.getElementsByClass("inner_table");
            Day DayForEven, DayForOdd;
            String[] oneLineData = new String[4];
            int k = 0;
            for (int i = 0, n = dayNames.size(); i < n; i++) {
                DayForEven = new Day(dayNames.get(i).text());
                DayForOdd = new Day(dayNames.get(i).text());
                Elements CurrentDayInfo = dayInfo.get(i).getElementsByTag("td");
                for (int j = 0, m = CurrentDayInfo.size(); j < m; j++) {
                    if (k == 4) {
                        switch (CurrentDayInfo.get(j).text()) {
                            case "чёт":
                                DayForEven.Append(oneLineData[0], oneLineData[1], oneLineData[2], oneLineData[3]);
                                break;
                            case "нечет":
                                DayForOdd.Append(oneLineData[0], oneLineData[1], oneLineData[2], oneLineData[3]);
                                break;
                            case "все":
                                DayForEven.Append(oneLineData[0], oneLineData[1], oneLineData[2], oneLineData[3]);
                                DayForOdd.Append(oneLineData[0], oneLineData[1], oneLineData[2], oneLineData[3]);
                                break;
                        }
                        k = 0;
                    } else {
                        oneLineData[k++] = CurrentDayInfo.get(j).text();
                    }
                }
                EvenWeek.add(DayForEven);
                OddWeek.add(DayForOdd);
            }
            /*for(int s = 0, d = EvenWeek.size(); s < d; s++){
                Log.d("MyTag", EvenWeek.get(s).toString());
            } //ПРОВЕРКА РАСПИСАНИЯ В КОНСОЛИ
            Log.d("MyTag", "\n\n\n");
            for(int s = 0, d = OddWeek.size(); s < d; s++){
                Log.d("MyTag", OddWeek.get(s).toString());
            }*/
        } catch (IOException e) {
            Log.d("Exceptions", e.toString());
        }
    }

    // Метод для отображения расписания
    private void displaySchedule() {
        TextView textViewSchedule = findViewById(R.id.textViewSchedule);
        StringBuilder scheduleText = new StringBuilder("Расписание для группы " + GroupToShow + ":\n\n");

        // Добавление расписания четной недели
        scheduleText.append("Четная неделя:\n");
        for (Day day : EvenWeek) {
            scheduleText.append(day.toString());
        }

        // Добавление расписания нечетной недели
        scheduleText.append("\nНечетная неделя:\n");
        for (Day day : OddWeek) {
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