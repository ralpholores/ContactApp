package com.contactapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.api.model.StringList;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity{

    private EditText mFirstName,mLastName,mContactNo;
    private Button mBtnAdd;
    private ListView mListView;

    ArrayList<String> mContacts = new ArrayList<>();

    String firstName,lastName,contactNo;
    Firebase mRef;
    Contact[] contact = new Contact[10];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Firebase.setAndroidContext(this);


        mRef = new Firebase("https://contactapp-556c1.firebaseio.com/");
        Toast.makeText(this,""+mRef,Toast.LENGTH_SHORT).show();
        mFirstName = (EditText) findViewById(R.id.firstName);
        mLastName = (EditText) findViewById(R.id.lastName);
        mContactNo = (EditText) findViewById(R.id.contact);
        mBtnAdd = (Button) findViewById(R.id.btn);
        mListView = (ListView) findViewById(R.id.listView);




        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstName = mFirstName.getText().toString();
                lastName = mLastName.getText().toString();
                contactNo = mContactNo.getText().toString();
                contact[0] = new Contact(firstName,lastName,contactNo);
                Map<String,Contact[]> contacts = new HashMap<String, Contact[]>();
                contacts.put("Contacts",contact);
                Toast.makeText(getApplicationContext(),"asdasdsa"+firstName,Toast.LENGTH_SHORT).show();
                mRef.child("Contacts").setValue(contact);
                mFirstName.getText().clear();
                mLastName.getText().clear();
                mContactNo.getText().clear();
                mFirstName.setFocusable(true);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Firebase contactRef = mRef.child("Contacts");

        contactRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map<String, Object> newContact = (Map<String, Object>)dataSnapshot.getValue();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
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


}
