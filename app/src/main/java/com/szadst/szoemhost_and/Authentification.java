package com.szadst.szoemhost_and;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.szadst.szoemhost_and.Domaine.Parent;
import com.szadst.szoemhost_lib.SZOEMHost_Lib;

import java.io.Serializable;

public class Authentification extends AppCompatActivity {
private Button login_btn;
public static SZOEMHost_Lib m_szHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentification);

        //Initialisation des variables
        login_btn = (Button)findViewById(R.id.login_btn);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Authentification.this,Drawer_Layout_Activity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
