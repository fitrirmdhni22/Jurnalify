package com.example.jurnalify;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class LocationFlagActivity extends AppCompatActivity {

    private static final String TAG = "LocationFlagActivity";
    public static final String EXTRA_LAT = "extra_lat";
    public static final String EXTRA_LON = "extra_lon";

    private static final Map<String, String> GREETINGS = new HashMap<>();
    private static final Map<String, String> FLAGS = new HashMap<>();

    static {
        GREETINGS.put("Indonesia", "Halo! Selamat Datang");
        GREETINGS.put("Japan", "Kon'nichiwa! Yokoso");
        GREETINGS.put("South Korea", "Annyeong! Hwan-yeong");
        GREETINGS.put("Turkey", "Merhaba! Hoş geldiniz");
        GREETINGS.put("United States", "Hello! Welcome");
        GREETINGS.put("France", "Bonjour! Bienvenue");
        GREETINGS.put("Germany", "Hallo! Willkommen");
        
        FLAGS.put("Indonesia", "🇮🇩");
        FLAGS.put("Japan", "🇯🇵");
        FLAGS.put("South Korea", "🇰🇷");
        FLAGS.put("Turkey", "🇹🇷");
        FLAGS.put("United States", "🇺🇸");
        FLAGS.put("France", "🇫🇷");
        FLAGS.put("Germany", "🇩🇪");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // MENGGUNAKAN LAYOUT activity_location_denied SESUAI PERMINTAAN
        setContentView(R.layout.activity_location_denied);

        TextView textFlag = findViewById(R.id.textLargeFlag);
        TextView textCountry = findViewById(R.id.textCountryName);
        TextView textGreeting = findViewById(R.id.textGreeting);
        Button btnContinue = findViewById(R.id.buttonContinue);

        double lat = getIntent().getDoubleExtra(EXTRA_LAT, 0);
        double lon = getIntent().getDoubleExtra(EXTRA_LON, 0);

        if (lat != 0 && lon != 0) {
            updateUI(lat, lon, textFlag, textCountry, textGreeting);
        }

        if (btnContinue != null) {
            btnContinue.setOnClickListener(v -> {
                Intent intent = new Intent(LocationFlagActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            });
        }
    }

    private void updateUI(double lat, double lon, TextView textFlag, TextView textCountry, TextView textGreeting) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lon, 1);
            if (addresses != null && !addresses.isEmpty()) {
                String country = addresses.get(0).getCountryName();
                String greeting = GREETINGS.getOrDefault(country, "Hello! Welcome");
                String flag = FLAGS.getOrDefault(country, "🌍");
                
                runOnUiThread(() -> {
                    if (textFlag != null) textFlag.setText(flag);
                    if (textCountry != null) textCountry.setText("NEGARA: " + country.toUpperCase());
                    if (textGreeting != null) textGreeting.setText(greeting);
                });
            }
        } catch (IOException e) {
            Log.e(TAG, "Geocoder error: " + e.getMessage());
        }
    }
}
