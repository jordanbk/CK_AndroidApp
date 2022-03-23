package com.example.course_keeper_capstone.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.course_keeper_capstone.Database.Repository;
import com.example.course_keeper_capstone.Entity.Term;
import com.example.course_keeper_capstone.Entity.User;
import com.example.course_keeper_capstone.R;
import com.example.course_keeper_capstone.UI.Terms.TermActivity;
import com.google.android.material.navigation.NavigationView;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    User currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //userID = getIntent().getIntExtra("id", -1);
        repo = new Repository(getApplication());

/*        List<User> getAllUsers = repo.getAllUsers();
        for(User user : getAllUsers){
            if (user.getId() == userID){
                currentUser = user;
                userID = currentUser.getId();
                usernameText = currentUser.getUsername();
            }
        }*/

        //tvUser.setText(usernameText);


        // Inflate a menu to be displayed in the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // drawer layout instance to toggle the menu icon to open
        // drawer and back button to close drawer
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
        navigationView.setNavigationItemSelectedListener(this);

        Term termT = new Term(1, "name", null, null, 1);
        repo.insert(termT);
        ButterKnife.bind(this);


        // to make the Navigation drawer icon always appear on the action bar

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

    @OnClick(R.id.button_terms)
    public void buttonClick(){
        Intent terms2 = new Intent(HomeActivity.this, TermActivity.class);
        terms2.putExtra("id", userID);
        startActivity(terms2);
    }


}