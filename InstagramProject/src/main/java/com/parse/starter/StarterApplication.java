/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;

import android.app.Application;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.List;


public class StarterApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        // Add your initialization code here
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId("instagram63537UJDFHEYhdfkefhe")
                //.clientKey("uefjfhy7%$RHJHu7163jie")
                .server("https://instagram1223.herokuapp.com/parse/")
                .build()
        );

        /*
        ParseObject gameScore = new ParseObject("GameScore");
        gameScore.put("score", 1337);
        gameScore.put("playerName", "Mariana");
        gameScore.put("cheatMode", false);
        gameScore.saveInBackground(new SaveCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    Log.i("Parse", "Save Succeeded");
                } else {
                    Log.i("Parse", "Save Failed oi " + e.toString());
                    //e.getMessage();
                }
            }
        });
        */

        /*ParseQuery<ParseObject> parse = ParseQuery.getQuery("GameScore");
        Log.i("Parse", "to string: " + parse.toString());
        parse.getInBackground("2RHQ4eEQIh", new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    object.put("playerName", "Evan Scott");
                    object.put("score", 1743);
                    object.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Log.i("Parse", "Change on DB succeded");
                            } else {
                                Log.i("Parse", "Save failed");
                            }
                        }
                    });
                } else {
                    Log.i("Parse", "An error occured on retrieving information");
                }
            }
        });


        ParseQuery<ParseObject> query = ParseQuery.getQuery("GameScore");
        //query.whereEndsWith("playerName", "Evan Scott");
        query.whereEqualTo("playerName", "Evan Scott");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    Log.i("findCallback", objects.size() + " results");

                    if (objects.size() != 1) {
                        Log.i("findCallback", "Error: there should be only one result for the find query");
                    } else {
                        ParseObject object = objects.get(0);

                        object.put("score", 1399);
                        object.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    Log.i("findCallback", "Update succeded");
                                } else {
                                    Log.i("findCallback", "Update failed");
                                }
                            }
                        });
                    }

                } else {
                    Log.i("findCallback", "Error on retrieving data");
                }
            }
        });

        ParseUser.enableAutomaticUser();
*/



        ParseUser user = new ParseUser();

        user.setUsername("mfantini");
        user.setPassword("senha");

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.i("signUp", "Successful");
                } else {
                    Log.i("signUp", "Unsuccessful");
                }
            }
        });


        ParseACL defaultACL = new ParseACL();
        // Optionally enable public read access.
        // defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
    }
}
