package com.example.lostandfound;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;

public class CreateAdvertActivity extends AppCompatActivity {

    private PlacesClient placeDetectionClient;

    AdvertDatabase advertDatabase;
    Advert advert;

    EditText nameText;
    EditText phoneText;
    EditText descriptionText;
    EditText dateText;
    AutocompleteSupportFragment locationFragment;
    Place locationPlace;
    RadioGroup typeSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_advert);

        advertDatabase = new AdvertDatabase(this);

        nameText = findViewById(R.id.nameText);
        phoneText = findViewById(R.id.phoneText);
        descriptionText = findViewById(R.id.descriptionText);
        dateText = findViewById(R.id.dateText);
        typeSelect = findViewById(R.id.typeRadio);

        dateText.setText(Advert.DATE_FORMAT.format(new Date()));

        Places.initialize(this, "AIzaSyAU8-xBoIGWdGaVTuRSNJ09-BZB57xFIVs");
        placeDetectionClient = Places.createClient(this);

        // Initialize the AutocompleteSupportFragment.
        locationFragment = (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        // Specify the types of place data to return.
        locationFragment.setPlaceFields(Arrays.asList(Place.Field.NAME, Place.Field.LAT_LNG));

        // Set up a PlaceSelectionListener to handle the response.
        locationFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                locationPlace = place;
            }

            @Override
            public void onError(@NonNull Status status) {
                locationFragment.setText(locationPlace.getName());
            }
        });

    }

    public void GetCurrentLocation(View view) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

        placeDetectionClient.findCurrentPlace(FindCurrentPlaceRequest.newInstance(Arrays.asList(Place.Field.NAME, Place.Field.LAT_LNG))).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                locationPlace = task.getResult().getPlaceLikelihoods().get(0).getPlace();
                locationFragment.setText(locationPlace.getName());
            }
            else {
                Toast.makeText(this, "Failed to fetch location", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void Submit(View view) {
        try {
            String title = nameText.getText().toString();
            if (title.isEmpty()) {
                Toast.makeText(this, "Advert requires a title", Toast.LENGTH_LONG).show();
            }

            String phone = phoneText.getText().toString();
            if (phone.isEmpty()) {
                Toast.makeText(this, "Advert requires a phone number", Toast.LENGTH_LONG).show();
            }

            String description = descriptionText.getText().toString();
            if (description.isEmpty()) {
                Toast.makeText(this, "Advert requires a description", Toast.LENGTH_LONG).show();
            }

            String location = locationPlace.getName();
            if (location == null || location.isEmpty()) {
                Toast.makeText(this, "Advert requires a location", Toast.LENGTH_LONG).show();
            }

            String type;
            int selection = typeSelect.getCheckedRadioButtonId();
            if (selection == R.id.lostRadio) type = "Lost";
            else type = "Found";

            Date date = Advert.DATE_FORMAT.parse(dateText.getText().toString());

            advert = new Advert(title, phone, description, date, new Location(locationPlace), type);
            advertDatabase.addAdvert(advert);

            this.finish();
        } catch (ParseException e) {
            Toast.makeText(this, "Date is not of valid format", Toast.LENGTH_LONG).show();
        }
    }
}