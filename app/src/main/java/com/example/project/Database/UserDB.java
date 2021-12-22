package com.example.project.Database;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.project.Activity.MainActivity;
import com.example.project.Model.User;

import java.security.SecureRandom;

public class UserDB {
    private static final String USER_TABLE = "USER";

    public static long signUp(SQLiteDatabase db, User user) {

//        db.execSQL("drop trigger if exists trgr_SIGNUP_USR");
//
//        String createTrigger =
//                "create trigger if not exists trgr_SIGNUP_USR before insert on " + USER_TABLE
//                        + " begin"
//                        + " select case"
//                        + " (select EMAIL"
//                        + " from " + USER_TABLE
//                        + " where EMAIL = " + user.getEmail() + ")"
//                        + " then"
//                        + " raise(abort, 'there must be less than 6 students in the group')"
//                        + " end;"
//                        + " end;";
//
//        db.execSQL(createTrigger);

        ContentValues values = new ContentValues();
        values.put("LOGIN", user.getLogin());
        values.put("EMAIL", user.getEmail());
        values.put("PASSWORD", user.getPassword());

        return db.insertOrThrow(USER_TABLE, null, values);
    }

    private byte[] getSalt() {
        SecureRandom random = new SecureRandom();
        byte[]salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    public static void signIn(SQLiteDatabase db, String login,String password, Context ctx ) {

        Cursor query = db.rawQuery("select * from " + USER_TABLE + " where " + " LOGIN " + " LIKE \'" + login + "\';", null);
        if( query.moveToFirst() && query.getCount() != 0) {
            int id = query.getInt(0);
            String pass = query.getString(3);
            if(pass.equals(password)) {
                Intent intent = new Intent(ctx, MainActivity.class);
                intent.putExtra("id_user", id);
                ctx.startActivity(intent);
            } else {
                Toast.makeText(ctx, "Wrong Password", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(ctx, "Wrong user", Toast.LENGTH_LONG).show();
        }

    }

    
}
