package com.example.course_keeper_capstone.UI.Base;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import androidx.appcompat.widget.Toolbar;

import com.example.course_keeper_capstone.Database.Repository;
import com.example.course_keeper_capstone.Entity.User;
import com.example.course_keeper_capstone.R;
import com.example.course_keeper_capstone.UI.Terms.TermActivity;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
/*    @BindView(R.id.menu_username)
    TextView tvUser;*/
    public static String usernameText;
    private User user;
    public DrawerLayout drawer;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar = null;
    Repository repo;
    int userID;
    protected FrameLayout frameLayout;
    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        userID = getIntent().getIntExtra("id", -1);
        repo = new Repository(getApplication());


        // Inflate a menu to be displayed in the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // drawer layout instance to toggle the menu icon to open
        // drawer and back button to close drawer
/*        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,
                drawer,
                toolbar,
                R.string.nav_open,
                R.string.nav_close);
        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);*/


        // to make the Navigation drawer icon always appear on the action bar
        ImageButton termsButton = (ImageButton) findViewById(R.id.button_terms);
        termsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentTerms = new Intent(HomeActivity.this, TermActivity.class);
                intentTerms.putExtra("id", userID);
                startActivity(intentTerms);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.nav_home){
            Intent home = new Intent(HomeActivity.this, HomeActivity.class);
            startActivity(home);
        } else if (id == R.id.nav_terms){
            Intent terms = new Intent(HomeActivity.this, TermActivity.class);
            startActivity(terms);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed(){
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


}