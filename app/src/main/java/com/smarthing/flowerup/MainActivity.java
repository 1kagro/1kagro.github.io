package com.smarthing.flowerup;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.smarthing.flowerup.lib.MyFirebaseMessaging;
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

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseMessaging.getInstance().getToken()
            .addOnCompleteListener(new OnCompleteListener<String>() {
                @Override
                public void onComplete(@NonNull Task<String> task) {
                    if(!task.isSuccessful()){
                        return;
                    }
                    String token = task.getResult();
                    System.out.println("token " + token);
                }
            });



        System.out.println("token: " + FirebaseMessaging.getInstance().getToken());

        fab = findViewById(R.id.floatingActionButton);

        BottomNavigationView navigationView = findViewById(R.id.bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //firstFragment.getInten();

        loadPots("http://" + getResources().getString(R.string.ip_pc) + "/flowerup/php/androidBd/plantas_user.php");
        /*if(elementList.size() < 1) {
            elementList.add(new ListElement("Nube", "Magnoliophyta", "Dormitorio", 15, 20, true, true));
            elementList.add(new ListElement("Rosa", "Epipremnum aureum", "Sala", 100, 20, true, true));
            elementList.add(new ListElement("Verdecita", "Magnoliophyta", "Estudio", 18, 70, true, true));
            elementList.add(new ListElement("Pullas", "Epipremnum aureum", "Patio", 18, 20, true, true));
            elementList.add(new ListElement("Lengua de suegra", "Dracaena trifasciata", "Patio", 15, 20, true, true));
            elementList.add(new ListElement( "Sol", "Crassula ovata", "Patio", 11, 22, true, true));
            elementList.add(new ListElement("Alixc", "Crassula ovata", "Patio", 15, 20, true, true));
        }*/
        //loadApi("http://192.168.1.12/api"); // api servidor esp

        getInten();
        onApiReady();
        loadApi("http://" + getResources().getString(R.string.ip_esp8266) + "/api");
        firstFragment.elementList2 = elementList;
        secondFragment.elementList2 = elementList;
        loadFragment(firstFragment);
        System.out.println("Lista actualizada");
    }



    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.firstFragment:
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
        //onApiReady();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.commit();
    }

    private void loadApi(String URL) {
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
                        //loadFragment(firstFragment);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.esp8266_no_encendido), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, getResources().getString(R.string.esp8266_no_encendido), Toast.LENGTH_LONG).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();

                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
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
                        elementList.clear();
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
                            elementList.get(i).setDias(value.getInt("dias"));
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
                } else {
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.no_hay_plantas), Toast.LENGTH_LONG).show();
                }
                System.out.println("Count Response main " + response.length());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, getResources().getString(R.string.pc_no_encendido), Toast.LENGTH_LONG).show();
                //System.out.println("Error Volley: " + error);
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

    public void onApiReady() {
        consultarSensores();
    }

    private final int TIEMPO = 60000;

    public void consultarSensores() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                System.out.println("Consulté :D");
                loadApi("http://" + getResources().getString(R.string.ip_esp8266) + "/api");
                handler.postDelayed(this, TIEMPO);
            }
        }, TIEMPO);
    }

    /*
    public void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            showNotificaion();
        } else {
            showNewNotificaion();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void showNotificaion() {
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "NEW", NotificationManager.IMPORTANCE_DEFAULT);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.createNotificationChannel(channel);
        showNewNotificaion();
    }

    private void showNewNotificaion() {
        setPendingIntent(MainActivity.class);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_baseline_local_florist_24)
                .setContentTitle("¡Hola, soy Flower Up!")
                .setContentText(notificacionText)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent);
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getApplicationContext());
        managerCompat.notify(1, builder.build());
    }

    private void setPendingIntent(Class<?> ActivityClass) {
        Intent intent = new Intent(this, ActivityClass);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(ActivityClass);
        stackBuilder.addNextIntent(intent);
        pendingIntent = stackBuilder.getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT);
    }*/

}