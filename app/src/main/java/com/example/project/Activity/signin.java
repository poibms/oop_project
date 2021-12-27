package com.example.project.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "userInfo";
    private static final String KEY_ID = "userId";
    private static final String KEY_EMAIL = "LOGIN";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        initViews();
        db = new DBHelper(getApplicationContext()).getReadableDatabase();

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        String email = sharedPreferences.getString(KEY_EMAIL, null);
        if(email != null) {
            startActivity(new Intent(this, MainActivity.class));
        }
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
            SharedPreferences.Editor editor = sharedPreferences.edit();
            bindingValues();
            checkInputs(email,password);
            int check = UserDB.signIn(db, email,password, this);
            if(check !=0){
                editor.putInt(KEY_ID, check);
                editor.putString(KEY_EMAIL, email);
                editor.apply();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Wrong user", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something was wrong", Toast.LENGTH_SHORT).show();
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