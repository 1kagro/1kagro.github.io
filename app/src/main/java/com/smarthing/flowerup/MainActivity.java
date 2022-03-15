package com.smarthing.flowerup;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.smarthing.flowerup.model.ListElement;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FirstFragment firstFragment = new FirstFragment();
    SecondFragment secondFragment = new SecondFragment();
    ThirdFragment thirdFragment = new ThirdFragment();

    static List<ListElement> elementList = new ArrayList<>();

    FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = findViewById(R.id.floatingActionButton);

        BottomNavigationView navigationView = findViewById(R.id.bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //firstFragment.getInten();
        if(elementList.size() < 1) {
            elementList.add(new ListElement("Nube", "Magnoliophyta", "Dormitorio", true, true));
            elementList.add(new ListElement("Rosa", "Epipremnum aureum", "Sala", true, true));
            elementList.add(new ListElement("Verdecita", "Magnoliophyta", "Estudio", true, true));
            elementList.add(new ListElement("Pullas", "Epipremnum aureum", "Patio", true, true));
            elementList.add(new ListElement("Lengua de suegra", "Dracaena trifasciata", "Patio", true, true));
            elementList.add(new ListElement( "Sol", "Crassula ovata", "Patio", true, true));
            elementList.add(new ListElement("Alixc", "Crassula ovata", "Patio", true, true));
        }
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

    public void getInten(){

        Intent intent = getIntent();

        System.out.println("Intent: " + intent.getExtras());
        if(intent.getExtras() != null){
            String name = intent.getStringExtra("name");
            String botanical_name = intent.getStringExtra("botanical_name");
            String site = intent.getStringExtra("site");
            boolean sw_humedad = intent.getBooleanExtra("sw_humedad", false);
            boolean sw_temp = intent.getBooleanExtra("sw_temp", false);
            elementList.add(new ListElement(name, botanical_name, site, sw_temp, sw_humedad));
        } else {
            System.out.println("Cagaste, no hay datos");
        }
    }





}