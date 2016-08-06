package com.contactapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.contactapp.Adapter.Adapter;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.api.model.StringList;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity{

    private EditText mContactName,mContactNo,mDelName;
    private TextView mTxtView;
    private Button mBtnAdd,mBtnDel,mBtnUpdate;
    private ListView mListView;

    private ArrayList<Contact> mContacts = new ArrayList<>();
    private ListViewCompat listViewCompat;
    String contactName,contactNo,nameDelete;
    private Firebase mRef;
    private FirebaseDatabase dbRef;
    private DatabaseReference dbFirebase;

    ListAdapter adapter;
    Contact contact = new Contact();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Firebase.setAndroidContext(this);




        dbRef = FirebaseDatabase.getInstance();
        dbRef.setPersistenceEnabled(true);
        dbFirebase = dbRef.getReference("Contacts");

        mTxtView = (TextView) findViewById(R.id.txtView);
        mContactName = (EditText) findViewById(R.id.contactName);
        mContactNo = (EditText) findViewById(R.id.contact);
        mDelName = (EditText) findViewById(R.id.deleteName);
        mBtnAdd = (Button) findViewById(R.id.btnAdd);
        mBtnDel = (Button) findViewById(R.id.btnDel);
        mBtnUpdate = (Button) findViewById(R.id.update);
        listViewCompat = (ListViewCompat) findViewById(R.id.listContacts);

        dbFirebase.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                mContacts = new ArrayList<>();
                Iterable<com.google.firebase.database.DataSnapshot> snapshotIterable = dataSnapshot.getChildren();
                Iterator<com.google.firebase.database.DataSnapshot> iterator = snapshotIterable.iterator();


                while (iterator.hasNext()){
                    Contact contact = iterator.next().getValue(Contact.class);
                    mContacts.add(contact);
                }
                adapter = new Adapter(getApplicationContext(),R.layout.listview_item,mContacts);
                listViewCompat.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mBtnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameDelete = mDelName.getText().toString();
                Query dQuery = dbFirebase.orderByChild("first_name").equalTo(nameDelete);
                dQuery.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                    @Override
                    public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                        String key = dataSnapshot.getKey();
                        dataSnapshot.getRef().removeValue().equals(key);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                contactName = mContactName.getText().toString();
                contactNo = mContactNo.getText().toString();
                contact.setFirst_name(contactName);
                contact.setContact_number(contactNo);
                String key = dbFirebase.push().getKey();
                Contact contact = new Contact(contactName,contactNo);
                dbFirebase.child(key).setValue(contact);
                mContactName.getText().clear();
                mContactNo.getText().clear();

            }
        });
//
//        mBtnUpdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                contactName = mContactName.getText().toString();
//                contactNo = mContactNo.getText().toString();
//                String key = dbFirebase.push().getKey();
//                contact.setFirst_name(contactName);
//                contact.setContact_number(contactNo);
//                Contact contact = new Contact(contactName,contactNo);
//                dbFirebase.child(key).setValue(contact);
//            }
//        });
        listViewCompat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contact con = (Contact) parent.getItemAtPosition(position);
                Toast.makeText(MainActivity.this,"Contact Number: "+con.getContact_number(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+con.getContact_number()));
                startActivity(intent);
            }
        });

    }



}
