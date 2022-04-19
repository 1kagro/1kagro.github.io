package com.smarthing.flowerup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    TextInputEditText usuario, password;
    Button btnlogin;
    String user, pass, name, first_name, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usuario = findViewById(R.id.txI_email);
        password = findViewById(R.id.txI_password);
        btnlogin=findViewById(R.id.btn_login);

        //recuperarPreferences();
        //btnlogin.setOnClickListener();
    }

    public void sign_in(View view) {
        user = usuario.getText().toString();
        pass = password.getText().toString();
        if(!user.isEmpty() && !pass.isEmpty()) {
            validarUsuario("http://" + getResources().getString(R.string.ip_pc) + "/flowerup/php/androidBd/validar_usuario.php");
        } else {
            Toast.makeText(this, "No se permiten campos vacios", Toast.LENGTH_SHORT).show();
        }

    }

    public void sign_up(View view) {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
        finish();
    }

    private void validarUsuario(String URL){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("Response: " + response);


                if (!response.isEmpty()){
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        System.out.println("Response object " + jsonObject.getString("Username"));
                        id = jsonObject.getString("ID");
                        first_name = jsonObject.getString("Nombre");
                        name = jsonObject.getString("Nombre") + " " + jsonObject.getString("Apellidos");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    savePreferences();
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(Login.this, "Usuario o contraseña incorrectas", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login.this, getResources().getString(R.string.pc_no_encendido), Toast.LENGTH_LONG).show();

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String,String>();
                parametros.put("Correo",user);
                parametros.put("Contrasena",pass);

                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void savePreferences(){
        SharedPreferences preferences = getSharedPreferences("login_preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("id", id);
        editor.putString("user", user);
        editor.putString("pass", pass);
        editor.putString("name", name);
        editor.putString("first_name", first_name);
        editor.putBoolean("sesion", true);
        editor.commit();
    }

    private void recuperarPreferences(){
        SharedPreferences preferences = getSharedPreferences("login_preferences", Context.MODE_PRIVATE);
        usuario.setText(preferences.getString("user", "correo@mail.com"));
        password.setText(preferences.getString("pass", "password"));
    }


}