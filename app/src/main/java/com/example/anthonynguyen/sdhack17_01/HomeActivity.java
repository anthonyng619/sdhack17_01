package com.example.anthonynguyen.sdhack17_01;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class HomeActivity extends Activity {
    private Button btn_Extra, btn_Search, btn_send, btn_signout;

    private DatabaseReference mDatabase, root;
    private FirebaseAuth auth;


    EditText phone_number, address;

    String token = FirebaseAuth.getInstance().getCurrentUser().getUid();


    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_home);

        auth = FirebaseAuth.getInstance();


        mDatabase = FirebaseDatabase.getInstance().getReference();


        phone_number = (EditText) findViewById(R.id.phone_number);
        address = (EditText) findViewById(R.id.address);
        //ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.Sports, android.R.layout.simple_spinner_item); Don't need that!
        //spinner_sports.setAdapter(adapter);
        //spinner_sports.setOnItemSelectedListener(this);


        btn_Search = (Button) findViewById(R.id.btn_Search);
        btn_Extra = (Button) findViewById(R.id.btn_Extra);
        btn_send = (Button) findViewById(R.id.btn_send);
        btn_signout = (Button) findViewById(R.id.btn_signout);


        btn_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, SearchActivity.class));
            }
        });

        btn_Extra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, ExtraActivity.class));
            }
        });

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myPhone, myAddress;
                myPhone = phone_number.getText().toString();
                myAddress = address.getText().toString();
                mDatabase.child("users").child(token).child("Phone number").setValue(myPhone);
                mDatabase.child("users").child(token).child("Address").setValue(myAddress);

            }
        });


        btn_signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signout();
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            }
        });



    }

        //sign out method
        public void signout() {
        auth.signOut();
    }


    }



















