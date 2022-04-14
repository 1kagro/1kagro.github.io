package com.smarthing.flowerup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class NewPlant extends AppCompatActivity {

    TextInputEditText plant_name, botanical_name, site;
    Switch humedad, temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_plant);

        plant_name = findViewById(R.id.txI_nameIn);
        botanical_name = findViewById(R.id.botanical_nameIn);
        site = findViewById(R.id.txI_siteIn);

        humedad = findViewById(R.id.sw_humedad);
        temp = findViewById(R.id.sw_temp);
    }

    public void agregarPlanta(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        String name = plant_name.getText().toString();
        String botanical_nameS = botanical_name.getText().toString();
        String siteS = site.getText().toString();
        if(name.replace(" ", "").equals("")
                || botanical_nameS.replace(" ", "").equals("")
                || siteS.replace(" ", "").equals("")){
            Toast.makeText(this, "No puede dejar el ningun campo vacío", Toast.LENGTH_SHORT).show();
        }else{
            intent.putExtra("name", name);
            intent.putExtra("botanical_name", botanical_nameS);
            intent.putExtra("site", siteS);
            intent.putExtra("sw_humedad", humedad.isChecked());
            intent.putExtra("sw_temp", temp.isChecked());
            startActivity(intent);
            finish();
        }

    }

    public void cancelarPlanta(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}