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
    private Button btn_activity_1, btn_activity_2, btn_send, btn_signout;

    private DatabaseReference mDatabase, root;
    private FirebaseAuth auth;


    EditText phone_number;

    String token = FirebaseAuth.getInstance().getCurrentUser().getUid();


    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_home);

        auth = FirebaseAuth.getInstance();


        mDatabase = FirebaseDatabase.getInstance().getReference();


        phone_number = (EditText) findViewById(R.id.phone_number);
        //ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.Sports, android.R.layout.simple_spinner_item); Don't need that!
        //spinner_sports.setAdapter(adapter);
        //spinner_sports.setOnItemSelectedListener(this);


        btn_activity_1 = (Button) findViewById(R.id.btn_start_google_map);
        btn_activity_2 = (Button) findViewById(R.id.btn_start_main);
        btn_send = (Button) findViewById(R.id.btn_send);
        btn_signout = (Button) findViewById(R.id.btn_signout);

        btn_activity_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, ButtonActivity1.class));
            }
        });

        btn_activity_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, ButtonActivity2.class));
            }
        });

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myPhone;
                myPhone = phone_number.getText().toString();
                mDatabase.child("users").child(token).child("Phone number").setValue(myPhone);
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



















