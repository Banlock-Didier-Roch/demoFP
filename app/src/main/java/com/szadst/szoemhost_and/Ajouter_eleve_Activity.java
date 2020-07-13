package com.szadst.szoemhost_and;

import android.app.Dialog;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.szadst.szoemhost_and.DAO.DatabaseHelper;
import com.szadst.szoemhost_and.Domaine.Parent;
import com.szadst.szoemhost_and.Fragments.Fragment_Eleves;
import com.szadst.szoemhost_and.Spinners.Adapters.CustomAdapter;
import com.szadst.szoemhost_and.Spinners.CustomItem;
import com.szadst.szoemhost_lib.DevComm;
import com.szadst.szoemhost_lib.SZOEMHost_Lib;

import java.util.ArrayList;
import java.util.List;

public class Ajouter_eleve_Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


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
    private Button btnEmpreinte;
    private Dialog dialog;
    private Button valider;
    private EditText editText;
    private List<Parent> parents;
    private int empreinte = 0;
    public static View gifImageView;
    private TextView tv_empreinte;

    //Fingerprint variables
    public static int id;
    int m_nBaudrate = 9600;
    String m_szDevice = "USB";
    public static boolean fpIsEntered;
    private static SZOEMHost_Lib m_szHost;
    int m_nUserID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        empreinte = 0;
        setContentView(R.layout.activity_ajouter_eleves);
        myDb = new DatabaseHelper(this);
        parents = new ArrayList<Parent>();
        fpIsEntered = false;

        //Actualiser les spinners
        actualiserSpinner();

        //managing EditTexts && ImageViews
        nomEleve = (EditText) findViewById(R.id.nomEleve);
        prenomEleve = (EditText) findViewById(R.id.prenomEleve);
        matriculeEleve = (EditText) findViewById(R.id.matriculeEleve);
        gifImageView = (View) findViewById(R.id.image_spinner);
        gifImageView.setVisibility(View.GONE);
        tv_empreinte = (TextView) findViewById(R.id.tv_empreinte);

        //Managing Buttons
        btnEditer = (Button) findViewById(R.id.btnEditer);
        btnEditer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fpIsEntered) {
                    if (ecole != "" && typeNotif != "" && !nomEleve.getText().toString().equalsIgnoreCase("") && !prenomEleve.getText().toString().equalsIgnoreCase("") && !matriculeEleve.getText().toString().equalsIgnoreCase("")) {
                        insert = myDb.insertEleve(nomEleve.getText().toString(), prenomEleve.getText().toString(), ecole, matriculeEleve.getText().toString(), parents.get(custmSpinnerParent.getSelectedItemPosition() - 1), id);
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
                } else {
                    Toast.makeText(getApplicationContext(), "Veuillez enregistrer l'empreinte!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Managing class addition

        //Manage Onclick
        ajouterClasse = (ImageView) findViewById(R.id.ajouterClasse);
        ajouterClasse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Dialog ini
                dialog = new Dialog(Ajouter_eleve_Activity.this);
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
                            Toast.makeText(Ajouter_eleve_Activity.this, "Le nom de la classe doit être renseigné", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        //Managing fingerprint addition

        //Manage Onclick
        btnEmpreinte = (Button) findViewById(R.id.btnEmpreinte);
        btnEmpreinte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gifImageView.setVisibility(View.VISIBLE);
                OnEnrollBtn();
                fpIsEntered = true;
            }
        });


        SetInitialState();

        InitWidget();

        OnOpenDeviceBtn();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Getting max Template number
                OnGetEmptyID();
            }
        }, 1000);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Getting max Template number
                tv_empreinte.setText("Veuillez entrer l'empreinte.");
            }
        }, 1000);

    }


    public ArrayList<CustomItem> getcustomListClasse() {
        myDb = new DatabaseHelper(getApplicationContext());
        result = myDb.getAllClasses();
        customListMP = new ArrayList<>();
        customListMP.add(new CustomItem("", R.drawable.ic_school_black_24dp));
        while (result.moveToNext()) {
            customListMP.add(new CustomItem(result.getString(0), R.drawable.ic_school_black_24dp));
        }
        return customListMP;
    }

    public ArrayList<CustomItem> getcustomListParent() {
        myDb = new DatabaseHelper(getApplicationContext());
        result = myDb.getAllParents();
        customListMP2 = new ArrayList<>();
        customListMP2.add(new CustomItem("", R.drawable.ic_person_outline_black_24dp));
        while (result.moveToNext()) {
            customListMP2.add(new CustomItem(result.getString(1) + " " + result.getString(2), R.drawable.ic_person_outline_black_24dp));
            parents.add(new Parent(result.getInt(0), result.getString(1), result.getString(2), result.getString(5), result.getString(3), result.getString(4)));
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
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.equals(custmSpinnerParent)) {
            CustomItem item = (CustomItem) parent.getSelectedItem();
            typeNotif = item.getSpinnerItemName();

        } else {
            CustomItem item = (CustomItem) parent.getSelectedItem();
            ecole = item.getSpinnerItemName();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void InitWidget() {
        if (m_szHost == null) {
            m_szHost = new SZOEMHost_Lib(this, tv_empreinte, runEnableCtrl);
        } else {
            m_szHost.SZOEMHost_Lib_Init(this, tv_empreinte, runEnableCtrl);
        }
    }

    public void OnEnrollBtn() {
        int w_nTemplateNo;

        w_nTemplateNo = GetInputTemplateNo();
        Toast.makeText(this, "" + w_nTemplateNo, Toast.LENGTH_SHORT).show();
        if (w_nTemplateNo < 0)
            return;

        m_szHost.Run_CmdEnroll(w_nTemplateNo);
    }

    public int GetInputTemplateNo() {
        String str;

        str = id + "";

        if (str.isEmpty()) {
            Toast.makeText(this, "Please input user id", Toast.LENGTH_SHORT).show();
            return -1;
        }

        try {
            m_nUserID = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please input correct user id(1~%d)" + (short) DevComm.GD_MAX_RECORD_COUNT, Toast.LENGTH_SHORT).show();
            return -1;
        }

        return m_nUserID;
    }

    public void OnOpenDeviceBtn() {
        if (m_szHost.OpenDevice(m_szDevice, m_nBaudrate) == 0) {
            btnEmpreinte.setEnabled(true);
        }
    }

    public void OnGetEmptyID() {
        m_szHost.Run_CmdGetEmptyID();
    }

    public void OnDeleteIDBtn() {
        int w_nTemplateNo;

        w_nTemplateNo = GetInputTemplateNo();
        if (w_nTemplateNo < 0)
            return;

        m_szHost.Run_CmdDeleteID(w_nTemplateNo);
    }

    public void SetInitialState() {
        tv_empreinte.setText("Veuillez entrer l'empreinte.");
        btnEmpreinte.setEnabled(false);
    }

    Runnable runEnableCtrl = new Runnable() {
        public void run() {
            btnEmpreinte.setEnabled(true);
        }
    };

    @Override
    public boolean onKeyDown(int KeyCode, KeyEvent event) {
        switch (KeyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (event.getRepeatCount() == 0) {
                    if (fpIsEntered) {
                        m_szHost.Run_CmdCancel();
                        OnDeleteIDBtn();

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //Remove Template
                                m_szHost.CloseDevice();
                            }
                        }, 1000);
                    }
                }
                break;
        }

        return super.onKeyDown(KeyCode, event);
    }
}
