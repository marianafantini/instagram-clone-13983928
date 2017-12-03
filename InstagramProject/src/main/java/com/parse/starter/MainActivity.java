/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class MainActivity extends AppCompatActivity {

    EditText usernameField;
    EditText passwordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
    Functions used for activity_main.xml actions
     */
    public void switchButtons(View view) {
        Button buttonOK = (Button) findViewById(R.id.btnOk);

        // check if the button says "log in"
        if (buttonOK.getText().toString().contains("Log in")) {

            ((Button) findViewById(R.id.btnOk)).setText("Sign up");
            //((Button) findViewById(R.id.btnOk)).setOnClickListener(logIn());
            ((TextView) findViewById(R.id.txtViewSwitch)).setText("Log in");

        }
        // or, if buttons says "sign up"
        else if (buttonOK.getText().toString().contains("Sign up")) {

            ((Button) findViewById(R.id.btnOk)).setText("Log in");
            //((Button) findViewById(R.id.btnOk)).setOnClickListener(logIn());
            ((TextView) findViewById(R.id.txtViewSwitch)).setText("Sign up");

        }
    }

    public void signUpLogInHandler(View view) {
        Button buttonOK = (Button) findViewById(R.id.btnOk);

        usernameField = (EditText) findViewById(R.id.edtUsername);
        passwordField = (EditText) findViewById(R.id.edtPassword);

        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();

        // check if the button says "log in"
        if (buttonOK.getText().toString().contains("Log in")) {
            logIn(username, password);
        }
        // or, if buttons says "sign up"
        else if (buttonOK.getText().toString().contains("Sign up")) {
            signUp(username, password);
        }
    }

    public void logIn(String username, String password) {


    }

    public void signUp(String username, String password) {

        Parse.enableLocalDatastore(this);

        // Add your initialization code here
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId("instagram63537UJDFHEYhdfkefhe")
                //.clientKey("uefjfhy7%$RHJHu7163jie")
                .server("https://instagram1223.herokuapp.com/parse/")
                .build()
        );

        ParseUser user = new ParseUser();

        user.setUsername(username);
        user.setPassword(password);

        Log.i("signUp", "Username: " + username + " - Password: " + password);

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {

                Log.i("signUp", "Exception: " + e.toString() + " - " + e.fillInStackTrace());

                if (e == null) {
                    Toast toast = Toast.makeText(MainActivity.this, "Error on log in! " + e.getMessage(), Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    Toast toast = Toast.makeText(MainActivity.this, "Log in successful!", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        ParseACL defaultACL = new ParseACL();
        // Optionally enable public read access.
        // defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);

    }

}
