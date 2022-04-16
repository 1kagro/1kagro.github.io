package com.smarthing.flowerup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.smarthing.flowerup.model.ListElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    FirstFragment firstFragment = new FirstFragment();
    SecondFragment secondFragment = new SecondFragment();
    ThirdFragment thirdFragment = new ThirdFragment();

    static List<ListElement> elementList = new ArrayList<>();

    FloatingActionButton fab;

    static boolean isOn = false;
    String string;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = findViewById(R.id.floatingActionButton);

        BottomNavigationView navigationView = findViewById(R.id.bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //firstFragment.getInten();

        if(elementList.size() < 1) {
            loadPots("http://192.168.1.10/flowerup/php/androidBd/plantas_user.php");
            /*elementList.add(new ListElement("Nube", "Magnoliophyta", "Dormitorio", 15, 20, true, true));
            elementList.add(new ListElement("Rosa", "Epipremnum aureum", "Sala", 100, 20, true, true));
            elementList.add(new ListElement("Verdecita", "Magnoliophyta", "Estudio", 18, 70, true, true));
            elementList.add(new ListElement("Pullas", "Epipremnum aureum", "Patio", 18, 20, true, true));
            elementList.add(new ListElement("Lengua de suegra", "Dracaena trifasciata", "Patio", 15, 20, true, true));
            elementList.add(new ListElement( "Sol", "Crassula ovata", "Patio", 11, 22, true, true));
            elementList.add(new ListElement("Alixc", "Crassula ovata", "Patio", 15, 20, true, true));
             */
        }
        loadApi("http://192.168.1.12/api", this); // api servidor esp
        getInten();
        firstFragment.elementList2 = elementList;
        secondFragment.elementList2 = elementList;
        loadFragment(firstFragment);
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.firstFragment:
                    loadApi("http://192.168.1.12/api", MainActivity.this); // api servidor esp
                    loadFragment(firstFragment);
                    return true;
                case R.id.secondFragment:
                    loadFragment(secondFragment);
                    return true;
                case R.id.thirdFragment:
                    loadFragment(thirdFragment);
                    return true;
            }
            return false;
        }
    };

    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.commit();
    }

    public static void loadApi(String URL, Context context) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.isEmpty()){
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        System.out.println("Api: " + jsonObject.toString());
                        isOn = true;

                        elementList.get(0).setHumedad((float) jsonObject.getDouble("h_t"));
                        elementList.get(0).setTemp((float) jsonObject.getDouble("t"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();

                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    private void loadPots(String URL){
        SharedPreferences preferences = getSharedPreferences("login_preferences", Context.MODE_PRIVATE);
        String id = preferences.getString("id", "");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.isEmpty()){
                    try {
                        string = response.replaceAll(" ", "");

                        string = "{" + string.substring(0, string.length() - 1) + "}";
                        System.out.println("Response main " + string);
                        JSONObject jsonObject = new JSONObject(string);
                        System.out.println("Plantas: " + jsonObject);
                        //System.out.println("Planta 1: " + jsonObject.getString("1"));
                        JSONArray keys = jsonObject.names ();
                        for (int i = 0; i < keys.length (); i++) {

                            String key = keys.getString (i); // Here's your key
                            JSONObject value = new JSONObject(jsonObject.getString (key)); // Here's your value

                            System.out.println("Key: " + key);
                            System.out.println("Value: " + value);
                            elementList.add(new ListElement(value.getString("Nombre"), value.getString("Categoria"), value.getString("Ubicacion"), 0, 0, value.getString("estado_temp").equals("1"), value.getString("estado_humedad").equals("1")));
                        }
                        /* Separar objeto plantas
                        String string = response;
                        Pattern pattern = Pattern.compile("\\{(.*?)\\}");
                        Matcher matcher = pattern.matcher(string);

                        for (int i = 0; i < matcher.groupCount(); i++) {
                            System.out.println("planta D>:");
                            System.out.println(matcher.group());
                        }
                        while (matcher.find()) {
                            System.out.println("planta D>:");
                            System.out.println(matcher.group(1));
                        }*/

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("Count Response main " + response.length());

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
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("user", id);

                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void getInten(){

        Intent intent = getIntent();

        System.out.println("Intent: " + intent.getExtras());
        if(intent.getExtras() != null){
            String name = intent.getStringExtra("name");
            String botanical_name = intent.getStringExtra("botanical_name");
            String site = intent.getStringExtra("site");
            boolean sw_humedad = intent.getBooleanExtra("sw_humedad", false);
            boolean sw_temp = intent.getBooleanExtra("sw_temp", false);
            elementList.add(new ListElement(name, botanical_name, site, 10, 15, sw_temp, sw_humedad));
        } else {
            System.out.println("No hay datos");
        }
    }





}