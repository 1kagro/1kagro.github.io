package com.smarthing.flowerup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NewPlant extends AppCompatActivity {

    TextInputEditText plant_name, botanical_name, site;
    Switch humedad, temp;
    String name;
    String botanical_nameS;
    String siteS;
    String sw_humedad;
    String sw_temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_plant);

        plant_name = findViewById(R.id.txI_nameIn);
        botanical_name = findViewById(R.id.botanical_nameIn);
        site = findViewById(R.id.txI_siteIn);

        humedad = findViewById(R.id.sw_humedad);
        temp = findViewById(R.id.sw_temp);

        if(!MainActivity.isOn) {
            Toast.makeText(this, "Puede crear una maceta, pero sólo existirá en su corazón :(", Toast.LENGTH_LONG).show();
        }
    }

    public void agregarPlanta(View view) {
        if(MainActivity.elementList.size() == 0) {
            Toast.makeText(this, getResources().getString(R.string.no_hay_plantas), Toast.LENGTH_SHORT).show();
        }

        Intent intent = new Intent(this, MainActivity.class);
        name = plant_name.getText().toString();
        botanical_nameS = botanical_name.getText().toString();
        siteS = site.getText().toString();
        if(name.replace(" ", "").equals("")
                || botanical_nameS.replace(" ", "").equals("")
                || siteS.replace(" ", "").equals("")){
            Toast.makeText(this, "No puede dejar el ningun campo vacío", Toast.LENGTH_SHORT).show();
        }else{
            savePot("http://" + getResources().getString(R.string.ip_pc) + "/flowerup/php/androidBd/crear_planta.php");
            sw_temp = (temp.isChecked() == true ? 1 : 0) + "";
            sw_humedad = (humedad.isChecked() == true ? 1 : 0) + "";

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

    private void savePot(String URL){
        SharedPreferences preferences = getSharedPreferences("login_preferences", Context.MODE_PRIVATE);
        String id = preferences.getString("id", "");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.isEmpty()){
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        System.out.println("Create plantas: " + jsonObject.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("Response main " + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Error Volley: " + error);
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                System.out.println("id main " + id);
                Map<String, String> parametros = new HashMap<String,String>();
                parametros.put("user_id", id);
                parametros.put("name", name);
                parametros.put("botanical_name", botanical_nameS);
                parametros.put("site", siteS);
                parametros.put("sw_humedad", sw_humedad);
                parametros.put("sw_temp", sw_temp);

                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}