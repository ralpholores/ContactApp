package com.contactapp;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by junvir on 8/1/2016.
 */
@IgnoreExtraProperties
public class Contact {


    public String first_name;
    public String contact_number;

    public Contact( String first_name, String contact_number) {

        this.first_name = first_name;
        this.contact_number = contact_number;
    }

    public Contact() {
    }


    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getContact_number() {
        return contact_number;
    }

    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }
}
