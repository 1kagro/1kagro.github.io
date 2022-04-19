package com.smarthing.flowerup;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.smarthing.flowerup.model.ListElement;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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

    private View searchFragment;
    private SearchView searchEt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        searchFragment = inflater.inflate(R.layout.fragment_search, container, false);

        searchEt = searchFragment.findViewById(R.id.tfbuscar);

        searchEt.clearFocus();
        searchEt.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filterList(s);
                return true;
            }
        });
        return searchFragment;
    }

    private void filterList(String text){
        List<ListElement> filteredList = new ArrayList<>();
        List<ListElement> filteredList2 = new ArrayList<>();

        for (ListElement item : FirstFragment.elementList2){
            if(item.getName().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }

        for (ListElement item : SecondFragment.elementList2){
            if(item.getName().toLowerCase().contains(text.toLowerCase())){
                filteredList2.add(item);
            }
        }

        if(filteredList.isEmpty() || filteredList2.isEmpty()) {
            Toast.makeText(getContext(), "No tiene plantas con ese nombre", Toast.LENGTH_SHORT).show();
        } else {
            FirstFragment.listAdapter.setFilteredList(filteredList);

            if(SecondFragment.listAdapter == null) {
                SecondFragment.listAdapter = new ListAdapter(SecondFragment.elementList2);
            }
            SecondFragment.listAdapter.setFilteredList(filteredList2);
        }
    }

}