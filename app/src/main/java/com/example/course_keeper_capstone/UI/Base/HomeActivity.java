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
import com.example.course_keeper_capstone.UI.Assessments.AssessmentActivity;
import com.example.course_keeper_capstone.UI.Courses.CourseActivity;
import com.example.course_keeper_capstone.UI.Instructors.InstructorActivity;
import com.example.course_keeper_capstone.UI.Terms.TermActivity;
import com.example.course_keeper_capstone.UI.reports.ReportActivity;
import com.example.course_keeper_capstone.Util.Util;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity {
    /*    @BindView(R.id.menu_username)
        TextView tvUser;*/
    public static String usernameText;
    private User user;
    public DrawerLayout drawer;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar = null;
    int userID;
    protected FrameLayout frameLayout;
    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        userID = getIntent().getIntExtra("id", -1);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setTitle("Terms");
        // Inflate a menu to be displayed in the toolbar
        toolbar.inflateMenu(R.menu.menu_logout);
        // Set an OnMenuItemClickListener to handle menu item clicks
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.action_report:
                        startActivity(new Intent(HomeActivity.this, ReportActivity.class));
                        return true;
                    case R.id.action_logout:
                        Util.LoginPrefs.setLoggedOut(HomeActivity.this);
                        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                        return true;
                    default:
                        //default intent
                        return false;
                }
            }
        });

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

        ImageButton coursesButton = (ImageButton) findViewById(R.id.button_courses);
        coursesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentTerms = new Intent(HomeActivity.this, CourseActivity.class);
                intentTerms.putExtra("id", userID);
                startActivity(intentTerms);
            }
        });


        findViewById(R.id.button_assessments).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentTerms = new Intent(HomeActivity.this, AssessmentActivity.class);
                intentTerms.putExtra("id", userID);
                startActivity(intentTerms);
            }
        });

        findViewById(R.id.button_instructors).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentTerms = new Intent(HomeActivity.this, InstructorActivity.class);
                intentTerms.putExtra("id", userID);
                startActivity(intentTerms);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_logout, menu);
        return true;
    }


}