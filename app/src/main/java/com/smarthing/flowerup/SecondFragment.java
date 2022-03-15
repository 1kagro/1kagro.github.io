package com.smarthing.flowerup;

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
 * Use the {@link SecondFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SecondFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SecondFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SecondFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SecondFragment newInstance(String param1, String param2) {
        SecondFragment fragment = new SecondFragment();
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

    RecyclerView recyclerView;
    List<ListElement> elementList;
    FloatingActionButton fab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View secondFragment = inflater.inflate(R.layout.fragment_first, container, false);

        fab = secondFragment.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NewPlant.class);
                startActivity(intent);
            }
        });

        elementList = new ArrayList<>();
        recyclerView = (RecyclerView) secondFragment.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        elementList.add(new ListElement("Nube", "Magnoliophyta", "Dormitorio", true, true));
        elementList.add(new ListElement("Rosa", "Epipremnum aureum", "Sala", true, true));
        elementList.add(new ListElement("Verdecita", "Magnoliophyta", "Estudio", true, true));
        elementList.add(new ListElement("Pullas", "Epipremnum aureum", "Patio", true, true));
        elementList.add(new ListElement("Lengua de suegra", "Dracaena trifasciata", "Patio", true, true));
        elementList.add(new ListElement( "Sol", "Crassula ovata", "Patio", true, true));
        elementList.add(new ListElement("Alixc", "Crassula ovata", "Patio", true, true));


        ListAdapter listAdapter = new ListAdapter(elementList);
        recyclerView.setAdapter(listAdapter);


        return secondFragment;
    }
}