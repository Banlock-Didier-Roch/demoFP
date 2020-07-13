package com.szadst.szoemhost_and;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.szadst.szoemhost_and.Fragments.Fragment_Eleves;
import com.szadst.szoemhost_and.Fragments.Fragment_parents;
import com.szadst.szoemhost_and.Fragments.Fragment_users;
import com.szadst.szoemhost_lib.SZOEMHost_Lib;


public class Drawer_Layout_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer__layout_);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer);
        toggle = new ActionBarDrawerToggle(this, drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //navigationView listener
        navigationView = (NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.f1content,new Fragment_Eleves()).commit();
            navigationView.getMenu().getItem(0).setChecked(true);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.students:
                getSupportFragmentManager().beginTransaction().replace(R.id.f1content,new Fragment_Eleves()).commit();
                menuItem.setChecked(true);
                break;

            case R.id.users:
                getSupportFragmentManager().beginTransaction().replace(R.id.f1content,new Fragment_users()).commit();
                menuItem.setChecked(true);
                break;

            case R.id.parent:
                getSupportFragmentManager().beginTransaction().replace(R.id.f1content,new Fragment_parents()).commit();
                menuItem.setChecked(true);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
