package com.example.lostandfound;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
    }

    public void CreateAdvert(View view) {
        Intent intent = new Intent(this, CreateAdvertActivity.class);
        startActivity(intent);
    }

    public void ViewAdverts(View view) {
        Intent intent = new Intent(this, ViewAdvertsActivity.class);
        startActivity(intent);
    }

    public void MapAdverts(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
}