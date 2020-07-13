package com.szadst.szoemhost_and;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.szadst.szoemhost_and.DAO.DatabaseHelper;
import com.szadst.szoemhost_lib.SZOEMHost_Lib;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import maes.tech.intentanim.CustomIntent;


public class MainActivity extends AppCompatActivity {
    private TextView heure;
    private TextView date;
    private LinearLayout login;
    private static int timeout = 3000;
    private DatabaseHelper myDb;
    CountDownTimer newtimer;
    private Handler handler = new Handler();


    public static int id;
    int m_nBaudrate = 9600;
    String m_szDevice = "USB";
    boolean fpIsEntered;
    public static SZOEMHost_Lib m_szHost;
    int m_nUserID;
    private TextView tv_empreinte;
    boolean run = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil_);

        //Cacher la barre des menu
        ((ActionBar) getSupportActionBar()).hide();


        // initialisation des variables date et time
        try {
            heure = (TextView) findViewById(R.id.heure);
            date = (TextView) findViewById(R.id.date_jour);
            tv_empreinte = (TextView) findViewById(R.id.connection_comment);
            //heure.setText(new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date()));

            String s = android.text.format.DateFormat.format("yyyy-MM-dd", new Date()).toString();
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE dd MMMM yyyy");
            date.setText(sdf.format(new SimpleDateFormat("yyyy-M-dd").parse(s)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        newtimer = new CountDownTimer(1000000000, 1000) {

            public void onTick(long millisUntilFinished) {
                Calendar c = Calendar.getInstance();
                heure.setText(c.get(Calendar.HOUR) + ":" + c.get(Calendar.MINUTE) + ":" + c.get(Calendar.SECOND));
            }

            public void onFinish() {

            }
        };
        newtimer.start();

        //Gestion du bouton login
        login = (LinearLayout) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent = new Intent(MainActivity.this, Authentification.class);
                finish();
                startActivity(intent);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        handler.removeCallbacks(runnable);
                        m_szHost.Run_CmdCancel();
                    }
                }, 1000);

                handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        m_szHost.CloseDevice();
                    }
                }, 2000);

                handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                    }
                }, 3000);*/
                handler.removeCallbacks(runnable);
                m_szHost.Run_CmdCancel();
                run=false;

                handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        m_szHost.CloseDevice();
                    }
                }, 1000);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(MainActivity.this, Authentification.class);
                        finish();
                        startActivity(intent);
                    }
                }, 2000);

            }
        });

        myDb = new DatabaseHelper(this);

        InitWidget();

        OnOpenDeviceBtn();

        SetInitialState();


    }

    public void InitWidget() {
        if (m_szHost == null) {
            m_szHost = new SZOEMHost_Lib(this, tv_empreinte, runnable);
        } else {
            m_szHost.SZOEMHost_Lib_Init(this, tv_empreinte, runnable);
        }
    }


    public void SetInitialState() {
        tv_empreinte.setText("Bienvenue sur SEATS, veuillez entrer votre empreinte.. ;)");
        handler.post(runnable);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Insertion of emargement
                    
                }
            }, 1000);
            if(run)
                m_szHost.Run_CmdIdentify();
        }
    };

    public void OnOpenDeviceBtn() {
        if (m_szHost.OpenDevice(m_szDevice, m_nBaudrate) == 0)
            return;
        else {
            m_szHost.OpenDevice(m_szDevice, m_nBaudrate);
        }
    }

    @Override
    public void finish() {
        super.finish();
        newtimer.cancel();
        CustomIntent.customType(this, "left-to-right");//or fadein-to-fadeout , bottom-to-up, rotatein-to-rotateout
    }
}
