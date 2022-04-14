package com.smarthing.flowerup;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetallePlanta extends AppCompatActivity {

    ImageView imageViewProfile;
    TextView tx_name, tx_cat, tx_site, tx_water_tank, tx_humidity, tx_temper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_planta);

        imageViewProfile = findViewById(R.id.imageView);

        tx_name = findViewById(R.id.tx_name);
        tx_cat = findViewById(R.id.tx_cat);
        tx_site = findViewById(R.id.tx_site);

        tx_water_tank = findViewById(R.id.tx_water_tank);
        tx_temper = findViewById(R.id.tx_temper);
        tx_humidity = findViewById(R.id.tx_humidity);

        initValues();
    }

    public void initValues(){
        Intent intent = getIntent();

        if(intent.getExtras() != null){
            tx_name.setText(intent.getStringExtra("name"));
            tx_cat.setText(intent.getStringExtra("category"));
            tx_site.setText(intent.getStringExtra("room"));
            tx_humidity.setText(intent.getStringExtra("temp"));
            tx_temper.setText(intent.getStringExtra("humedad"));
        }
    }
}