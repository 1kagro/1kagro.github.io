package com.smarthing.flowerup;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.smarthing.flowerup.model.ListElement;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FirstFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FirstFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FirstFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FirstFragment.
     */
    // TODO: Rename and change types and number of parameters

    RecyclerView recyclerView;

    public static FirstFragment newInstance(String param1, String param2) {
        FirstFragment fragment = new FirstFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    List<ListElement> elementList2;
    TextView name;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View firstFragment = inflater.inflate(R.layout.fragment_first, container, false);

        name = firstFragment.findViewById(R.id.tx_title2);
        recyclerView = (RecyclerView) firstFragment.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        ListAdapter listAdapter = new ListAdapter(elementList2);
        recyclerView.setAdapter(listAdapter);

        recuperarPreferences();


        return firstFragment;
    }
    private void recuperarPreferences(){
        SharedPreferences preferences = getActivity().getSharedPreferences("login_preferences", Context.MODE_PRIVATE);
        name.setText(preferences.getString("first_name", "Name"));
    }
    /*
    public void api(View view) {
        infoPlanta("http://192.168.18.5/flowerup/php/login.php");
    }


    private void infoPlanta(String URL){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.isEmpty()){
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(FirstFragment.this, "Usuario o contraseña incorrectas", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(FirstFragment.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String,String>();
                parametros.put("correo",usuario.getText().toString());
                parametros.put("pass",password.getText().toString());

                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }*/

    public void getInten(){

        /*MainActivity activity = (MainActivity) getActivity();

        Intent intent = activity.getIntent();

        System.out.println("Intent: " + intent.getExtras());
        if(intent.getExtras() != null){
            String name = intent.getStringExtra("name");
            String botanical_name = intent.getStringExtra("botanical_name");
            String site = intent.getStringExtra("botanical_name");
            boolean sw_humedad = intent.getBooleanExtra("sw_humedad", false);
            boolean sw_temp = intent.getBooleanExtra("sw_temp", false);
            elementList.add(new ListElement(name, botanical_name, site, sw_humedad, sw_temp));
        } else {
            System.out.println("Cagaste, no hay datos");
        }*/
    }
}