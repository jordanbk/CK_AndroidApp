package com.example.course_keeper_capstone.UI;

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
import com.example.course_keeper_capstone.Database.Repository;
import com.example.course_keeper_capstone.Entity.User;
import com.example.course_keeper_capstone.R;
import com.example.course_keeper_capstone.UI.Login.LoginViewModel;

public class SignUpActivity extends AppCompatActivity {
    EditText editTextUsername, editTextEmail, editTextPassword, editTextCnfPassword;
    Button buttonRegister;
    Repository repo;
    TextView textViewLogin;
    int id;
    private UserDAO userDao;
    public static String username;
    LoginViewModel loginViewModel;
    public static final String tag = "signUpActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Repository repository = new Repository(getApplication());
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextCnfPassword = findViewById(R.id.editTextCnfPassword);
        buttonRegister = findViewById(R.id.buttonRegister);

        textViewLogin = findViewById(R.id.textViewLogin);
        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, MainActivity.class));
            }
        });


        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Repository repository = new Repository(getApplication());

                String email = editTextEmail.getText().toString().trim();
                username = editTextUsername.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                String confirmPw = editTextCnfPassword.getText().toString().trim();

                if (password.equals(confirmPw)) {
                    //loginViewModel.registerUser(id++, email, username, password);
                    User user = new User(id++, email, username, password);
                    repository.insert(user);
                    Intent loginPage = new Intent(SignUpActivity.this, MainActivity.class);
                    username = loginPage.getStringExtra("username");
                    loginPage.putExtra("id", id);
                    //Log.d(tag, String.valueOf(id));

                    startActivity(loginPage);

                } else {
                    Toast.makeText(SignUpActivity.this, "Password does not match", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

}