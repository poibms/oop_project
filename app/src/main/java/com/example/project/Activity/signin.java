package com.example.project.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project.Database.DBHelper;
import com.example.project.Database.UserDB;
import com.example.project.R;

public class signin extends AppCompatActivity {
    EditText emailEdit, passwordEdit;
    String email, password;
    private SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        initViews();
        db = new DBHelper(getApplicationContext()).getReadableDatabase();
    }

    public void initViews() {
        emailEdit = findViewById(R.id.emailEdit);
        passwordEdit = findViewById(R.id.passwordEdit);
    }

    public void bindingValues() {
        email = emailEdit.getText().toString();
        password = passwordEdit.getText().toString();
    }

    public void signUpActivity_btn(View view) {
        Intent intent = new Intent(this, Signup.class);
        startActivity(intent);
    }

    public void signin_btn(View view) {
        try {
            bindingValues();
            checkInputs(email,password);
            UserDB.signIn(db, email,password, this);
        } catch (Exception e) {

        }


    }

    private boolean checkInputs(String email, String password){
        if( email.equals("") || password.equals("")){
            Toast.makeText(this, "All fields must be filled out.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}