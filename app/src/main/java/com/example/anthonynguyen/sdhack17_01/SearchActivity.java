package com.example.anthonynguyen.sdhack17_01;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.anthonynguyen.sdhack17_01.models.Item;
import com.example.anthonynguyen.sdhack17_01.models.ItemCollection;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
/*
interface SimpleCallback<T> {
    void callback(T data);
}*/

public class SearchActivity extends AppCompatActivity {

    final ArrayList<Item> itemList = new ArrayList<Item>();

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref = database.getReference();
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageReference = storage.getReference();

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_search);

        Button browseAll = (Button) findViewById(R.id.btn_browseall);
        browseAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrieveItemMap();
            }
        });

    }

    private void addToList() {
        /*retrieveItemMap(new SimpleCallback<Object>() {
            @Override
            public void callback(Object data) {
                if(data instanceof Boolean) {
                    //Do nothing
                }
                else {
                    itemList.add((Item) data);
                    System.out.println(((Item)data).getImgTitle());
                }
            }
        });*/
    }

    private void retrieveItemMap() {
        if(!itemList.isEmpty()) {
            itemList.clear();
        }
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(ref);

        ref.child("items").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Item item = snapshot.getValue(Item.class);
                    itemList.add(item);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



    }
