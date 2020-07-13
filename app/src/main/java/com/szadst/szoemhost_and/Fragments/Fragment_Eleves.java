package com.szadst.szoemhost_and.Fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.szadst.szoemhost_and.Adapters.Eleves_recycler_view_adapter;
import com.szadst.szoemhost_and.Ajouter_eleve_Activity;
import com.szadst.szoemhost_and.DAO.DatabaseHelper;
import com.szadst.szoemhost_and.Domaine.Eleves;
import com.szadst.szoemhost_and.R;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Eleves extends Fragment {

    public static RecyclerView myRecyclerview;
    public static List<Eleves> Data;
    public static View v;
    public static Eleves_recycler_view_adapter adapter;
    private FloatingActionButton FABEleve;
    public static DatabaseHelper myDb;
    public static Cursor result;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_fragment__eleves, container, false);

        //Refreshing recyclerView
        actualiserRecyclerView();

        //Managing Floating Action Button
        FABEleve = (FloatingActionButton)v.findViewById(R.id.FABEleve);
        FABEleve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Ajouter_eleve_Activity.myDb = myDb;
                Intent intent = new Intent(getContext(), Ajouter_eleve_Activity.class);
                getActivity().startActivity(intent);

            }
        });

        return v;
    }

    public static void actualiserRecyclerView(){
        //Fetching and displaying data from database
        myDb = new DatabaseHelper(v.getContext());
        result = myDb.getAllData();
        Data  = new ArrayList<>();
        while(result.moveToNext()){
            Data.add(new Eleves(result.getInt(0),result.getString(1), result.getString(2), result.getString(3), result.getString(4)));
        }
        myRecyclerview = (RecyclerView)v.findViewById(R.id.eleves_recyclerview);
        myRecyclerview.setLayoutManager(new LinearLayoutManager(v.getContext()));
        adapter = new Eleves_recycler_view_adapter(v.getContext(),Data);
        adapter.setListDesProduits(Data);
        myRecyclerview.setAdapter(adapter);
    }

    public Fragment_Eleves() {
    }


}
