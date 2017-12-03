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
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class MainActivity extends AppCompatActivity implements View.OnKeyListener, View.OnClickListener {

    EditText usernameField;
    EditText passwordField;

    Boolean signUpModeActive;

    ImageView logo;
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // app starts on sign up mode
        signUpModeActive = true;

        // set usernameField and passwordField to view fields
        usernameField = (EditText) findViewById(R.id.edtUsername);
        passwordField = (EditText) findViewById(R.id.edtPassword);

        // attribute on key listener, so that it attempts to log in
        // or sign up when user clicks on enter
        passwordField.setOnKeyListener(this);

        //
        logo = (ImageView) findViewById(R.id.imageViewLogo);
        logo.setOnClickListener(this);
        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        relativeLayout.setOnClickListener(this);

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

    /**
     * Function used for switching the values inside the button and textview
     * between log in and sign up according to the situation
     */
    public void switchButtons(View view) {

        // check if we're currently in sign up mode
        if (signUpModeActive) {

            // change texts of button and textview
            ((Button) findViewById(R.id.btnOk)).setText(R.string.log_in);
            ((TextView) findViewById(R.id.txtViewSwitch)).setText(R.string.sign_up);
            // set sign up mode to false (now we're on log in mode)
            signUpModeActive = false;

        } else {

            // change texts of button and textview
            ((Button) findViewById(R.id.btnOk)).setText(R.string.sign_up);
            ((TextView) findViewById(R.id.txtViewSwitch)).setText(R.string.log_in);
            // set sign up mode to true
            signUpModeActive = true;

        }
    }

    /**
     * Handler function: checks current mode and calls function to execute
     * the correct action
     */
    public void signUpLogInHandler(View view) {

        // check if we're currently on sign up or log in mode
        if (signUpModeActive) {
            signUp();
        } else {
            logIn();
        }
    }

    /**
     * Log in function: gets username and password data and attempts to log in the app.
     * Shows a Toast message to the user informing if it was successful or not
     */
    public void logIn() {

        ParseUser.logInInBackground(String.valueOf(usernameField.getText()),
                String.valueOf(passwordField.getText()), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {

                        if (e == null) {

                            Log.i("AppInfo", "Log in Successful");
                            Toast.makeText(getApplicationContext(), "Log in successful", Toast.LENGTH_LONG).show();

                        } else {

                            Log.i("AppInfo", "Log in failed");
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();

                        }

                    }
                });


    }


    /**
     * Sign up function: gets username and password data and attempts to sign up.
     * Shows a Toast message to the user informing if it was successful or not
     */
    public void signUp() {

        ParseUser user = new ParseUser();

        user.setUsername(String.valueOf(usernameField.getText()));
        user.setPassword(String.valueOf(passwordField.getText()));

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {

                if (e == null) {

                    Log.i("AppInfo", "Sign up Successful");
                    Toast.makeText(getApplicationContext(), "Sign up successful", Toast.LENGTH_LONG).show();

                } else {

                    Log.i("AppInfo", "Sign up failed");
                    e.printStackTrace();
                    Log.i("AppInfo", "Cause of failure: " + e.getCause());
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();

                }
            }
        });

    }

    /**
     * onKey method: when user clicks on the enter button, app attempts to log in or sign up,
     * depending on the current app mode
     */
    @Override
    public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {

        if (keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
            signUpLogInHandler(view);
        }

        return false;
    }

    @Override
    public void onClick(View view) {
        // checking if click was from imageView or relativeLayout
        if (view.getId() == R.id.imageViewLogo || view.getId() == R.id.relativeLayout) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
}
