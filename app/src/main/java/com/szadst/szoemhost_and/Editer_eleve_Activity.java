package com.szadst.szoemhost_and;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;


import com.szadst.szoemhost_and.DAO.DatabaseHelper;
import com.szadst.szoemhost_and.Domaine.Eleves;
import com.szadst.szoemhost_and.Domaine.Parent;
import com.szadst.szoemhost_and.Fragments.Fragment_Eleves;
import com.szadst.szoemhost_and.Spinners.Adapters.CustomAdapter;
import com.szadst.szoemhost_and.Spinners.CustomItem;

import java.util.ArrayList;

public class Editer_eleve_Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    private Spinner custmSpinnerTypeEcole;
    private Spinner custmSpinnerParent;
    private ArrayList<CustomItem> customListMP;
    private ArrayList<CustomItem> customListMP2;
    private Button btnEditer;
    private EditText nomEleve;
    private EditText prenomEleve;
    private EditText matriculeEleve;
    public static DatabaseHelper myDb;
    public static Cursor result;
    private String ecole = "";
    private String typeNotif = "";
    private boolean insert;
    private ImageView ajouterClasse;
    private Dialog dialog;
    private Button valider;
    private EditText editText;
    private Eleves eleves;
    private int positionParentSelectionne = 0;
    private int positionClasseSelectionne = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editer_eleve_);
        myDb = new DatabaseHelper(this);
        Intent intent = getIntent();
        eleves = ((Eleves) intent.getSerializableExtra("produitSelectione"));

        //Actualiser les spinners
        actualiserSpinner();

        //managing EditTexts
        nomEleve = (EditText) findViewById(R.id.nomEleve);
        prenomEleve = (EditText) findViewById(R.id.prenomEleve);
        matriculeEleve = (EditText) findViewById(R.id.matriculeEleve);

        //Setting fields values
        nomEleve.setText(eleves.getNom());
        prenomEleve.setText(eleves.getPrenom());
        matriculeEleve.setText(eleves.getMatricule());

        //-->Setting spinner value
        //custmSpinnerTypeEcole.setSelection(customListMP.indexOf(new CustomItem(eleves.getClasse(), R.drawable.ic_school_black_24dp)));


        //Managing Buttons
        btnEditer = (Button) findViewById(R.id.btnEditer);
        btnEditer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ecole !=""&&typeNotif!="") {
                    insert = myDb.updateEleve(eleves.getId(), nomEleve.getText().toString(), prenomEleve.getText().toString(), ecole, matriculeEleve.getText().toString());
                    if (insert) {
                        finish();
                        Fragment_Eleves.actualiserRecyclerView();
                        Toast.makeText(getApplicationContext(), "Insertion réussie!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Un problème a été rencontré lors de l'enregistrement!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Tous les champs doivent être renseignés", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Managing parent addition

        //Manage Onclick
        ajouterClasse = (ImageView) findViewById(R.id.ajouterClasse);
        ajouterClasse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Dialog ini
                dialog = new Dialog(Editer_eleve_Activity.this);
                dialog.setContentView(R.layout.dialog_ecole);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                dialog.show();
                valider = dialog.findViewById(R.id.btnAjouterEcole);
                valider.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editText = (EditText) dialog.findViewById(R.id.nomEcole);
                        if (!editText.getText().toString().equalsIgnoreCase("")) {
                            myDb.insertClasse(editText.getText().toString());
                            dialog.dismiss();
                            actualiserSpinner();
                        } else {
                            Toast.makeText(Editer_eleve_Activity.this, "Le nom de la classe doit être renseigné", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }


    public ArrayList<CustomItem> getcustomListClasse() {
        myDb = new DatabaseHelper(getApplicationContext());
        result = myDb.getAllClasses();
        customListMP = new ArrayList<>();
        customListMP.add(new CustomItem("", R.drawable.ic_school_black_24dp));
        while (result.moveToNext()) {
            customListMP.add(new CustomItem(result.getString(0), R.drawable.ic_school_black_24dp));
            if(result.getString(0).equalsIgnoreCase(eleves.getClasse())){
                positionClasseSelectionne = (customListMP.size()-1);
            }
        }
        return customListMP;
    }


    public ArrayList<CustomItem> getcustomListParent() {
        Parent parent = myDb.getParent(eleves);
        myDb = new DatabaseHelper(getApplicationContext());
        result = myDb.getAllParents();
        customListMP2 = new ArrayList<>();
        customListMP2.add(new CustomItem("", R.drawable.ic_person_outline_black_24dp));
        while (result.moveToNext()) {
            customListMP2.add(new CustomItem(result.getString(1)+" "+result.getString(2), R.drawable.ic_person_outline_black_24dp));

            if(parent!=null && ((result.getString(1)+" "+result.getString(2)).equalsIgnoreCase(parent.getNom()+" "+parent.getPrenom()))){
                positionParentSelectionne = (customListMP2.size()-1);
            }
        }
        return customListMP2;
    }

    public void actualiserSpinner() {
        //Managing Spinners
        //school
        custmSpinnerTypeEcole = (Spinner) findViewById(R.id.customIconSpinnerClasse);
        customListMP = getcustomListClasse();
        CustomAdapter adapterMP = new CustomAdapter(this, customListMP);

        //Parent
        custmSpinnerParent = (Spinner) findViewById(R.id.customIconSpinnerParent);
        customListMP2 = getcustomListParent();
        CustomAdapter adapterMP2 = new CustomAdapter(this, customListMP2);

        if (custmSpinnerTypeEcole != null) {
            custmSpinnerTypeEcole.setAdapter(adapterMP);
            custmSpinnerTypeEcole.setOnItemSelectedListener(this);
        }

        if (custmSpinnerParent != null) {
            custmSpinnerParent.setAdapter(adapterMP2);
            custmSpinnerParent.setOnItemSelectedListener(this);
        }

        //Initialize spinners on corresponding values
        custmSpinnerTypeEcole.setSelection(positionClasseSelectionne);
        custmSpinnerParent.setSelection(positionParentSelectionne);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.equals(custmSpinnerParent)){
            CustomItem item = (CustomItem) parent.getSelectedItem();
            typeNotif = item.getSpinnerItemName();

        }else{
            CustomItem item = (CustomItem) parent.getSelectedItem();
            ecole = item.getSpinnerItemName();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
