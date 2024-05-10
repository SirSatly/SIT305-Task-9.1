package com.example.lostandfound;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.ParseException;
import java.util.Date;

public class CreateAdvertActivity extends AppCompatActivity {

    AdvertDatabase advertDatabase;
    Advert advert;

    EditText nameText;
    EditText phoneText;
    EditText descriptionText;
    EditText dateText;
    EditText locationText;
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
        locationText = findViewById(R.id.locationText);
        typeSelect = findViewById(R.id.typeRadio);

        dateText.setText(Advert.DATE_FORMAT.format(new Date()));
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

            String location = locationText.getText().toString();
            if (description.isEmpty()) {
                Toast.makeText(this, "Advert requires a location", Toast.LENGTH_LONG).show();
            }

            String type;
            int selection = typeSelect.getCheckedRadioButtonId();
            if (selection == R.id.lostRadio) type = "Lost";
            else type = "Found";

            Date date = Advert.DATE_FORMAT.parse(dateText.getText().toString());

            advert = new Advert(title, phone, description, date, location, type);
            advertDatabase.addAdvert(advert);

            this.finish();
        } catch (ParseException e) {
            Toast.makeText(this, "Date is not of valid format", Toast.LENGTH_LONG).show();
        }
    }
}