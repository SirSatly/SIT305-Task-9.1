package com.example.lostandfound;

import static android.text.format.DateUtils.getRelativeTimeSpanString;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ShowAdvertActivity extends AppCompatActivity {

    AdvertDatabase advertDatabase;
    Advert advert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_show_advert);

        advertDatabase = new AdvertDatabase(this);
        String advert_id = getIntent().getStringExtra("id");
        advert = advertDatabase.getAdvert(advert_id);

        TextView titleText = findViewById(R.id.textTitle);
        titleText.setText(advert.getType() + " " + advert.getName());

        TextView locationText = findViewById(R.id.textLocation);
        locationText.setText("At " + advert.getLocation());

        TextView timeText = findViewById(R.id.textTime);
        timeText.setText(getRelativeTimeSpanString(advert.getDate().getTime()));

        TextView phoneText = findViewById(R.id.textPhone);
        phoneText.setText("Call " + advert.getPhone());

        TextView descriptionText = findViewById(R.id.textDescription);
        descriptionText.setText(advert.getDescription());
    }

    public void Delete(View view) {
        advertDatabase.deleteAdvert(advert.getId());
        this.finish();
    }
}