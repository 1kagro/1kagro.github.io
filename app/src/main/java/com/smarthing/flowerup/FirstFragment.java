package com.smarthing.flowerup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.smarthing.flowerup.model.ListElement;

import java.util.ArrayList;
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
    FloatingActionButton fab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View firstFragment = inflater.inflate(R.layout.fragment_first, container, false);

        fab = firstFragment.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NewPlant.class);
                startActivity(intent);
            }
        });

        recyclerView = (RecyclerView) firstFragment.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        ListAdapter listAdapter = new ListAdapter(elementList2);
        recyclerView.setAdapter(listAdapter);


        return firstFragment;
    }

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