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
import android.widget.TextView;
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
import java.util.Iterator;
import java.util.Map;

public class MainActivity extends AppCompatActivity{

    private EditText mFirstName,mLastName,mContactNo;
    private TextView mTxtView;
    private Button mBtnAdd,mBtnDel;
    private ListView mListView;

    ArrayList<Contact> mContacts = new ArrayList<>();

    String firstName,lastName,contactNo;
    Firebase mRef;
    Contact contact = new Contact();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Firebase.setAndroidContext(this);


        mRef = new Firebase("https://contactapp-556c1.firebaseio.com/Contacts");
        Toast.makeText(this,""+mRef,Toast.LENGTH_SHORT).show();
        mTxtView = (TextView) findViewById(R.id.txtView);
        mFirstName = (EditText) findViewById(R.id.firstName);
        mLastName = (EditText) findViewById(R.id.lastName);
        mContactNo = (EditText) findViewById(R.id.contact);
        mBtnAdd = (Button) findViewById(R.id.btnAdd);
        mBtnDel = (Button) findViewById(R.id.btnDel);
        mListView = (ListView) findViewById(R.id.listView);




        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Firebase contactRef = mRef.child("Contacts");
                firstName = mFirstName.getText().toString();
                lastName = mLastName.getText().toString();
                contactNo = mContactNo.getText().toString();
                String key = contactRef.push().getKey();
                Contact contact = new Contact(firstName,lastName,contactNo);
                Toast.makeText(getApplicationContext(),"asdasdsa"+firstName,Toast.LENGTH_SHORT).show();
                contactRef.child(key).setValue(contact);
                mFirstName.getText().clear();
                mLastName.getText().clear();
                mContactNo.getText().clear();
                mFirstName.setFocusable(true);

                mTxtView.setText("OBJECT: " + contact.getFirst_name());

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        Firebase contactRef = mRef.child("Contacts");
        contactRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> snapshotIterator = dataSnapshot.getChildren();
                Iterator<DataSnapshot> iterator = snapshotIterator.iterator();
                while (iterator.hasNext()){
                    Contact value = iterator.next().getValue(Contact.class);
                    mContacts.add(value);
                }
                for(int i = mContacts.size() - 1;i > 0;i--){
                    mTxtView.setText(""+mContacts.get(i).getFirst_name() + mContacts.get(i).getLast_name() + mContacts.get(i).getContact_number());
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}
