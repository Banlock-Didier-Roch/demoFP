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

import com.szadst.szoemhost_and.Adapters.Parent_recycler_view_adapter;
import com.szadst.szoemhost_and.Ajouter_parent;
import com.szadst.szoemhost_and.DAO.DatabaseHelper;
import com.szadst.szoemhost_and.Domaine.Parent;
import com.szadst.szoemhost_and.R;

import java.util.ArrayList;
import java.util.List;


public class Fragment_parents extends Fragment {

    public static RecyclerView myRecyclerview;
    public static List<Parent> Data;
    public static View v;
    public static Parent_recycler_view_adapter adapter;
    private FloatingActionButton FABParent;
    public static DatabaseHelper myDb;
    public static Cursor result;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_fragment_parents, container, false);

        //Refreshing recyclerView
        actualiserRecyclerView();

        //Managing Floating Action Button
        FABParent = (FloatingActionButton)v.findViewById(R.id.FABParent);
        FABParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), Ajouter_parent.class);
                getActivity().startActivity(intent);

            }
        });

        return v;
    }

    public static void actualiserRecyclerView(){
        //Fetching and displaying data from database
        myDb = new DatabaseHelper(v.getContext());
        result = myDb.getAllParents();
        Data  = new ArrayList<>();
        while(result.moveToNext()){
            Data.add(new Parent(result.getInt(0),result.getString(1), result.getString(2), result.getString(5), result.getString(3),result.getString(4)));
        }
        myRecyclerview = (RecyclerView)v.findViewById(R.id.parent_recyclerview);
        myRecyclerview.setLayoutManager(new LinearLayoutManager(v.getContext()));
        adapter = new Parent_recycler_view_adapter(v.getContext(),Data);
        adapter.setListDesProduits(Data);
        myRecyclerview.setAdapter(adapter);
    }
}
