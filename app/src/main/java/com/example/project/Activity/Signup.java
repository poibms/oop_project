package com.example.project.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project.Database.DBHelper;
import com.example.project.Database.UserDB;
import com.example.project.Model.User;
import com.example.project.R;

public class Signup extends AppCompatActivity {

    EditText loginEdit, emailEdit, passwordEdit;
    String login, email, password;
    Button signUpBtn, signInActBtn;

    private SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initViews();
        setButtonListeners();
        db = new DBHelper(getApplicationContext()).getWritableDatabase();

    }

    public void initViews() {
        loginEdit = findViewById(R.id.loginEdit);
        emailEdit = findViewById(R.id.emailEdit);
        passwordEdit = findViewById(R.id.passwordEdit);
        signUpBtn = findViewById(R.id.signup_button);
        signInActBtn = findViewById(R.id.loginActivity_button);
    }

    public void bindingValues() {
        login = loginEdit.getText().toString();
        email = emailEdit.getText().toString();
        password = passwordEdit.getText().toString();
    }

    public void setButtonListeners() {
        signUpBtn.setOnClickListener(view -> {
            try {
                bindingValues();

                if(login.length() >=4 && login.length()<=15) {
                    if (password.length() >= 4 && password.length() <= 15) {
                        User user = new User(login, email, password);
                        if(UserDB.signUp(db, user) != -1) {
//                            startActivity(new Intent(this, MainActivity.class));
                            clearFields();
                            finish();
                            Toast.makeText(this, "User was successfully sign up ", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "password length must be between 4 and 15 characters",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "login length must be between 4 and 15 characters",
                            Toast.LENGTH_SHORT).show();
                }
            } catch (SQLiteConstraintException e) {
                Toast.makeText(this, "sql error",
                        Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, "Check your input values",
                        Toast.LENGTH_SHORT).show();
            }
        });
        signInActBtn.setOnClickListener(view -> {
//            startActivity(new Intent(this, signin.class));
            finish();
        });
    }

    public void clearFields() {
        loginEdit.setText("");
        emailEdit.setText("");
        passwordEdit.setText("");
    }
}