package com.my.voenmeh.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.my.voenmeh.R;
import com.my.voenmeh.Schedule.Schedule;

public class ScheduleActivity extends AppCompatActivity {

    String GroupToShow = "О721Б";
    Schedule schedule = new Schedule();

    private void pullSchedule()
    {
        Thread GettingSchedule;
        Runnable runnable = () -> schedule.PullSchedule(GroupToShow);
        GettingSchedule = new Thread(runnable);
        GettingSchedule.start();
        try {
            GettingSchedule.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void displaySchedule(boolean isOddWeek)
    {
        TableLayout tableLayout = findViewById(R.id.tableLayoutSchedule);
        tableLayout.removeAllViews();

        boolean isFirstDayAdded = false;

        for (Schedule.Day day : schedule.GetWeek(!isOddWeek)) {
            if (!isFirstDayAdded) {
                addDayTitleRow(tableLayout, day.dayName);
                createSpace(tableLayout, 7);
                isFirstDayAdded = true;
            } else {
                createSpace(tableLayout, 7);
                addSeparator(tableLayout);
                createSpace(tableLayout, 7);
                addDayTitleRow(tableLayout, day.dayName);
                createSpace(tableLayout, 7);
            }
            for (int i = 0; i < day.time.size(); i++) {
                addScheduleRow(tableLayout, day.time.get(i), day.subject.get(i),
                        day.teacher.get(i), day.place.get(i));
            }
        }
    }

    private void addDayTitleRow(TableLayout tableLayout, String dayName)
    {
        TableRow tableRowDay = new TableRow(this);
        TextView textViewDay = new TextView(this);
        textViewDay.setText(dayName);
        textViewDay.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        Typeface typeface = ResourcesCompat.getFont(this, R.font.montserrat_m);
        textViewDay.setTypeface(typeface);
        textViewDay.setTextColor(Color.parseColor("#000000"));
        tableRowDay.addView(textViewDay);
        tableLayout.addView(tableRowDay,
                new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT));
    }

    private void addSeparator(TableLayout tableLayout)
    {
        TableRow tableRowDivider = new TableRow(this);
        TextView textViewDivider = new TextView(this);
        textViewDivider.setText("—————————");
        textViewDivider.setTextColor(Color.parseColor("#000000"));
        textViewDivider.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        Typeface typeface5 = ResourcesCompat.getFont(this, R.font.montserrat_m);
        textViewDivider.setTypeface(typeface5);
        tableRowDivider.addView(textViewDivider);
        tableLayout.addView(tableRowDivider,
                new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT));
    }

    private void createSpace(ViewGroup parentLayout, int dp)
    {
        View whiteRectangle = new View(this);
        ViewGroup.LayoutParams layoutParams
                = new ViewGroup.LayoutParams(dpToPx(dp), dpToPx(dp));
        whiteRectangle.setLayoutParams(layoutParams);
        whiteRectangle.setBackgroundColor(Color.TRANSPARENT);
        parentLayout.addView(whiteRectangle);
    }

    private int dpToPx(int dp)
    {
        float density = getResources().getDisplayMetrics().density;
        return Math.round((float)dp * density);
    }

    private void addScheduleRow(TableLayout tableLayout, String time, String subject,
                                String teacher, String place)
    {
        TableRow tableRowTime = new TableRow(this);
        TextView textViewTime = new TextView(this);
        textViewTime.setText(time);
        textViewTime.setTextColor(Color.parseColor("#2975CC"));
        textViewTime.setTextSize(TypedValue.COMPLEX_UNIT_SP, 21);
        Typeface typeface = ResourcesCompat.getFont(this, R.font.montserrat_eb);
        textViewTime.setTypeface(typeface);
        tableRowTime.addView(textViewTime);
        tableLayout.addView(tableRowTime,
                new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT));

        TableRow tableRowSubject = new TableRow(this);
        TextView textViewSubject = new TextView(this);
        textViewSubject.setText(subject);
        textViewSubject.setTextColor(Color.BLACK);
        textViewSubject.setTextSize(TypedValue.COMPLEX_UNIT_SP, 21);
        Typeface typeface2 = ResourcesCompat.getFont(this, R.font.montserrat_b);
        textViewSubject.setTypeface(typeface2);
        tableRowSubject.addView(textViewSubject);
        tableLayout.addView(tableRowSubject,
                new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT));

        TableRow tableRowTeacher = new TableRow(this);
        TextView textViewTeacher = new TextView(this);
        textViewTeacher.setText(teacher);
        textViewTeacher.setTextColor(Color.parseColor("#8E8E8E"));
        textViewTeacher.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        Typeface typeface3 = ResourcesCompat.getFont(this, R.font.montserrat_m);
        textViewTeacher.setTypeface(typeface3);
        tableRowTeacher.addView(textViewTeacher);
        tableLayout.addView(tableRowTeacher,
                new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT));

        TableRow tableRowPlace = new TableRow(this);
        TextView textViewPlace = new TextView(this);
        textViewPlace.setText(place);
        textViewPlace.setTextColor(Color.parseColor("#8E8E8E"));
        textViewPlace.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        Typeface typeface4 = ResourcesCompat.getFont(this, R.font.montserrat_m);
        textViewPlace.setTypeface(typeface4);
        tableRowPlace.addView(textViewPlace);
        tableLayout.addView(tableRowPlace,
                new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT));

        createSpace(tableLayout, 13);
    }

    @Override protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        pullSchedule();
        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch schSwitch = findViewById(R.id.schSwitch);
        schSwitch.setChecked(false);
        displaySchedule(false);
        schSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> displaySchedule(isChecked));
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setSelectedItemId(R.id.navigation_schedule);
        navView.setOnNavigationItemSelectedListener(
                menuItem -> {
                    int id = menuItem.getItemId();

                    if (id == R.id.navigation_news) {
                        startActivity(
                                new Intent(getApplicationContext(), NewsActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    } else if (id == R.id.navigation_tracker) {
                        startActivity(
                                new Intent(getApplicationContext(), TrackerActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    } else if (id == R.id.navigation_schedule) {
                        return true;
                    } else if (id == R.id.navigation_mail) {
                        startActivity(
                                new Intent(getApplicationContext(), MailActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    } else if (id == R.id.navigation_service) {
                        startActivity(
                                new Intent(getApplicationContext(), ServicesActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    }
                    return false;
                });
    }
}