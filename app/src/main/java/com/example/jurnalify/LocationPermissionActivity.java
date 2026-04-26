package com.example.jurnalify;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;

public class LocationPermissionActivity extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1003;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Menggunakan layout yang baru saja dibuat ulang
        setContentView(R.layout.activity_location_permission);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        Button btnAllow = findViewById(R.id.btnAllowLocation);
        Button btnDeny = findViewById(R.id.btnDenyLocation);

        if (btnAllow != null) {
            btnAllow.setOnClickListener(v -> requestLocation());
        }

        if (btnDeny != null) {
            btnDeny.setOnClickListener(v -> goToHome());
        }
    }

    private void requestLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            detectAndGoToFlag();
        }
    }

    private void detectAndGoToFlag() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            goToHome();
            return;
        }

        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
                .addOnSuccessListener(this, location -> {
                    Intent intent = new Intent(LocationPermissionActivity.this, LocationFlagActivity.class);
                    if (location != null) {
                        intent.putExtra(LocationFlagActivity.EXTRA_LAT, location.getLatitude());
                        intent.putExtra(LocationFlagActivity.EXTRA_LON, location.getLongitude());
                    } else {
                        intent.putExtra(LocationFlagActivity.EXTRA_LAT, 0.0);
                        intent.putExtra(LocationFlagActivity.EXTRA_LON, 0.0);
                    }
                    startActivity(intent);
                    finish();
                })
                .addOnFailureListener(e -> {
                    Intent intent = new Intent(LocationPermissionActivity.this, LocationFlagActivity.class);
                    startActivity(intent);
                    finish();
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                detectAndGoToFlag();
            } else {
                Toast.makeText(this, "Izin lokasi diperlukan untuk fitur ini", Toast.LENGTH_SHORT).show();
                goToHome();
            }
        }
    }

    private void goToHome() {
        Intent intent = new Intent(LocationPermissionActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
