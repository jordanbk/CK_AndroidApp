package com.example.course_keeper_capstone.UI.Base;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.course_keeper_capstone.DAO.UserDAO;
import com.example.course_keeper_capstone.Database.DatabaseBuilder;
import com.example.course_keeper_capstone.Database.Repository;
import com.example.course_keeper_capstone.Entity.User;
import com.example.course_keeper_capstone.R;


public class MainActivity extends AppCompatActivity {
    EditText editUsername, editPassword;
    Button buttonLogin;
    TextView textViewSignUp;
    Repository repo;
    DatabaseBuilder database;
    UserDAO db;
    int id;
    int userID;
    public static String username;
    private LoginViewModel loginViewModel;
    public static final String tag = "mainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Repository repo = new Repository(getApplication());
        editUsername = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        textViewSignUp = findViewById(R.id.textRegister);
        buttonLogin = findViewById(R.id.buttonLogin);

        //userID = getIntent().getIntExtra("id", -1);
        //Bundle extras = getIntent().getExtras();

        Log.d(tag, String.valueOf(id));
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);


        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SignUpActivity.class));
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = editUsername.getText().toString().trim();
                String password = editPassword.getText().toString().trim();
                User user = new User();
                user = repo.getUser(username, password);
                if (user != null) {
                        //loginViewModel.authorizeUser(id, username, password);
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    userID = user.getId();
                    intent.putExtra("username", username);
                    intent.putExtra("id", userID);
                    Log.d(tag, String.valueOf(userID));

                    startActivity(intent);
                    finish();
                    }
                    else{
                        Toast.makeText(MainActivity.this,"Username and/or password is incorrect",Toast.LENGTH_SHORT).show();
                    }
                }

        });


    }


}