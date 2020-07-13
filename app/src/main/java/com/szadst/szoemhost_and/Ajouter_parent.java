package com.szadst.szoemhost_and;

import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.szadst.szoemhost_and.DAO.DatabaseHelper;
import com.szadst.szoemhost_and.Fragments.Fragment_parents;
import com.szadst.szoemhost_and.Spinners.Adapters.CustomAdapter;
import com.szadst.szoemhost_and.Spinners.CustomItem;

import java.util.ArrayList;

public class Ajouter_parent extends AppCompatActivity implements AdapterView.OnItemSelectedListener{


    private Spinner custmSpinnerTypeEcole;
    private ArrayList<CustomItem> customListMP;
    private Button btnEditer;
    private EditText nomParent;
    private EditText prenomParent;
    private EditText emailParent;
    private EditText telephoneParent;
    public static DatabaseHelper myDb;
    public static Cursor result;
    private String typeNotif = "";
    private boolean insert;
    private ImageView ajouterClasse;
    private Dialog dialog;
    private Button valider;
    private EditText editText;
    private TextView edit;
    private TextView add;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_parent);
        myDb = new DatabaseHelper(this);

        //Actualiser les spinners
        actualiserSpinner();

        //managing EditTexts
        nomParent = (EditText)findViewById(R.id.nomParent);
        prenomParent = (EditText)findViewById(R.id.prenomParent);
        emailParent = (EditText)findViewById(R.id.emailParent);
        telephoneParent = (EditText)findViewById(R.id.emailParent);

        //Managing Buttons
        btnEditer = (Button)findViewById(R.id.btnAjouter);
        btnEditer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (typeNotif != "") {
                    insert = myDb.insertParent(nomParent.getText().toString(), prenomParent.getText().toString(), typeNotif, telephoneParent.getText().toString(),emailParent.getText().toString());
                    if(insert){
                        finish();
                        Fragment_parents.actualiserRecyclerView();
                        Toast.makeText(getApplicationContext(), "Insertion réussie!", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "Un problème a été rencontré lors de l'enregistrement!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Veuillez renseigner le type de notification", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    public ArrayList<CustomItem> getcustomListClasse() {
        myDb = new DatabaseHelper(getApplicationContext());
        result = myDb.getAllClasses();
        customListMP = new ArrayList<>();
        customListMP.add(new CustomItem("Journalier", R.drawable.ic_type_notif));
        customListMP.add(new CustomItem("Hebdomadaire", R.drawable.ic_type_notif));
        customListMP.add(new CustomItem("Mensuel", R.drawable.ic_type_notif));
        return customListMP;
    }

    public void actualiserSpinner(){
        //Managing Spinners
        custmSpinnerTypeEcole = (Spinner) findViewById(R.id.customIconSpinnerTypeNotif);
        customListMP = getcustomListClasse();
        CustomAdapter adapterMP = new CustomAdapter(this, customListMP);

        if (custmSpinnerTypeEcole != null) {
            custmSpinnerTypeEcole.setAdapter(adapterMP);
            custmSpinnerTypeEcole.setOnItemSelectedListener(this);
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        CustomItem item = (CustomItem) parent.getSelectedItem();
        typeNotif = item.getSpinnerItemName();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
