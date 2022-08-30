package com.example.course_keeper_capstone.UI.Base;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.course_keeper_capstone.Entity.User;
import com.example.course_keeper_capstone.R;
import com.example.course_keeper_capstone.UI.Base.HomeActivity;
import com.example.course_keeper_capstone.UI.Base.LoginViewModel;
import com.example.course_keeper_capstone.UI.Base.SignUpActivity;
import com.example.course_keeper_capstone.Util.Util;


public class LoginActivity extends AppCompatActivity {
    private EditText editUsername, editPassword;
    private Button buttonLogin;
    private TextView textViewSignUp;
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editUsername = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        textViewSignUp = findViewById(R.id.textRegister);
        buttonLogin = findViewById(R.id.buttonLogin);

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = editUsername.getText().toString().trim();
                String password = editPassword.getText().toString().trim();

                if (!TextUtils.isEmpty(username) &&
                        !TextUtils.isEmpty(password)) {
                    User user = new User(username, password);
                    loginViewModel.getUser(user);
                } else {
                    Toast.makeText(LoginActivity.this, "Enter a valid username or password", Toast.LENGTH_SHORT).show();
                }
            }

        });

        loginViewModel.userLD.observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (user != null) {
                    if (user.getUsername() != null) {
                        Util.LoginPrefs.setLoggedIn(LoginActivity.this, true, user.getId());
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        intent.putExtra("username", user.getUsername());
                        intent.putExtra("id", user.getId());
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Username and/or password is incorrect", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

}