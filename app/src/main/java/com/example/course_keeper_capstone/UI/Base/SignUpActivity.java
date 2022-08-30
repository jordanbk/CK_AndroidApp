package com.example.course_keeper_capstone.UI.Base;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.course_keeper_capstone.DAO.UserDAO;
import com.example.course_keeper_capstone.Database.Repository;
import com.example.course_keeper_capstone.Entity.User;
import com.example.course_keeper_capstone.R;
import com.example.course_keeper_capstone.Util.Util;

public class SignUpActivity extends AppCompatActivity {
    private EditText editTextUsername, editTextEmail, editTextPassword, editTextCnfPassword;
    private Button buttonRegister;
    private TextView textViewLogin;
    private int id;
    private SignupViewModel mSignupViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mSignupViewModel = new ViewModelProvider(this).get(SignupViewModel.class);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextCnfPassword = findViewById(R.id.editTextCnfPassword);
        buttonRegister = findViewById(R.id.buttonRegister);

        textViewLogin = findViewById(R.id.textViewLogin);
        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                finish();
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString().trim();
                String username = editTextUsername.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                String confirmPw = editTextCnfPassword.getText().toString().trim();

                if (!TextUtils.isEmpty(email) &&
                        !TextUtils.isEmpty(username) &&
                        !TextUtils.isEmpty(password) &&
                        !TextUtils.isEmpty(confirmPw)
                ) {
                    if (password.equals(confirmPw)) {
                        User user = new User(id++, email, username, password);
                        Util.LoginPrefs.saveUserId(SignUpActivity.this, user.getId());
                        mSignupViewModel.register(user);
                    } else {
                        Toast.makeText(SignUpActivity.this, "Password does not match", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SignUpActivity.this, "Enter a valid input!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mSignupViewModel.isRegisterSuccessLD.observe(this, new Observer<Pair<Boolean, String>>() {
            @Override
            public void onChanged(Pair<Boolean, String> isSuccess) {
                if(isSuccess!=null) {
                    if (isSuccess.first) {
                        Toast.makeText(SignUpActivity.this, isSuccess.second, Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(SignUpActivity.this, isSuccess.second, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

}