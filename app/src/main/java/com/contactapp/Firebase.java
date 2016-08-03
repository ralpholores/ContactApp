package com.contactapp;

/**
 * Created by RAPSK on 8/2/2016.
 */
public class Firebase extends android.app.Application{

    @Override
    public void onCreate() {
        super.onCreate();
        com.firebase.client.Firebase.setAndroidContext(this);
    }
}
