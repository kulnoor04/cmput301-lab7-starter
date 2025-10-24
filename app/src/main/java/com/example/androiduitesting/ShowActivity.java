package com.example.androiduitesting;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class ShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        TextView cityNameText = findViewById(R.id.text_cityName);
        Button backButton = findViewById(R.id.button_back);

        // Get the city name sent from MainActivity
        String cityName = getIntent().getStringExtra("CITY_NAME");
        if (cityName != null) {
            cityNameText.setText(cityName);
        }
        // Go back to MainActivity
        backButton.setOnClickListener(v -> finish());
    }
}
