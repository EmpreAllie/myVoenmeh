package com.my.voenmeh.Activities;

import android.annotation.SuppressLint;
import android.os.Bundle;

import android.view.View;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.my.voenmeh.R;

public class MapActivity extends AppCompatActivity
{

    private SubsamplingScaleImageView map;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tracker);


        map = (SubsamplingScaleImageView)findViewById(R.id.map);
        map.setImage(ImageSource.resource(R.drawable.map11));
        findViewById(R.id.rad_btn1).setOnClickListener((view)->onRadioButtonClicked(view));
        findViewById(R.id.rad_btn2).setOnClickListener((view)->onRadioButtonClicked(view));
        findViewById(R.id.rad_btn3).setOnClickListener((view)->onRadioButtonClicked(view));
        findViewById(R.id.rad_btn4).setOnClickListener((view)->onRadioButtonClicked(view));
        findViewById(R.id.switch1).setOnClickListener((view)->onRadioButtonClicked(view));

    };

    public void onRadioButtonClicked(View view)
    {
        // Получаем выбранный этаж и корпус
        RadioGroup floorGroup = findViewById(R.id.group1);
        int floorId = floorGroup.getCheckedRadioButtonId();
        RadioButton floorButton = findViewById(floorId);
        String selectedFloor = floorButton.getText().toString();

        SwitchCompat buildingSwitch = findViewById(R.id.switch1);
        boolean isULK = buildingSwitch.isChecked();
        if ("1".equals(selectedFloor)) {
            if (isULK) {
                map.setImage(ImageSource.resource(R.drawable.map21));
            } else {
                map.setImage(ImageSource.resource(R.drawable.map11));
            }
        } else if ("2".equals(selectedFloor)) {
            if (isULK) {
                map.setImage(ImageSource.resource(R.drawable.map22));
            } else {
                map.setImage(ImageSource.resource(R.drawable.map12));
            }
        } else if ("3".equals(selectedFloor)) {
            if (isULK) {
                map.setImage(ImageSource.resource(R.drawable.map23));
            } else {
                map.setImage(ImageSource.resource(R.drawable.map13));
            }
        } else if ("4".equals(selectedFloor)) {
            if (isULK) {
                map.setImage(ImageSource.resource(R.drawable.map24));
            } else {
                map.setImage(ImageSource.resource(R.drawable.map14));
            }
        }
    }

}
