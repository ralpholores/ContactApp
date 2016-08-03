package com.contactapp;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by junvir on 8/1/2016.
 */
@IgnoreExtraProperties
public class Contact {

    public String last_name;
    public String first_name;
    public String contact_number;

    public Contact(String last_name, String first_name, String contact_number) {
        this.last_name = last_name;
        this.first_name = first_name;
        this.contact_number = contact_number;
    }

    public Contact() {
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
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
