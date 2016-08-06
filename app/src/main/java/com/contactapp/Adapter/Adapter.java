package com.contactapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.contactapp.Contact;
import com.contactapp.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by cicct on 8/5/2016.
 */
public class Adapter extends ArrayAdapter<Contact>{

    private Context context;
    private int resource;
    private ArrayList<Contact> objects;

    public Adapter(Context context, int resource, ArrayList<Contact> objects) {
        super(context, resource,objects);
        this.context = context;
        this.objects = objects;
        this.resource = resource;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Contact contact = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(resource,parent,false);
            TextView mContactName = (TextView) convertView.findViewById(R.id.txtName);
            TextView mContactNumber = (TextView) convertView.findViewById(R.id.txtContact);

            mContactName.setText("Contact Name: "+contact.getFirst_name());
            mContactNumber.setText("Contact Number: "+contact.getContact_number());
        }
        return convertView;
    }
}
