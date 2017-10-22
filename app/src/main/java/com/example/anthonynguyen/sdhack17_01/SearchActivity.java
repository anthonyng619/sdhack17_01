package com.example.anthonynguyen.sdhack17_01;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anthonynguyen.sdhack17_01.models.ImageAdapter;
import com.example.anthonynguyen.sdhack17_01.models.Item;
import com.example.anthonynguyen.sdhack17_01.models.ItemCollection;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/*
interface SimpleCallback<T> {
    void callback(T data);
}*/

public class SearchActivity extends AppCompatActivity {

    public static List<Item> itemList = new ArrayList<Item>();
    public static List<Item> searchList = new ArrayList<Item>();
    public static List<Bitmap> searchList_Bitmap = new ArrayList<Bitmap>();
    //public static HashMap<String, Bitmap> searchlist_Bitmap = new HashMap<String, Bitmap>;
    //public static HashMap<Integer, HashMap<String ,Bitmap>>searchList_Bitmap_Int = new HashMap<Integer, HashMap<String, Bitmap>>();

    private EditText searchText;
    private GridView gridView;
    public Bitmap bmap_lol;

    public int index = 0;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref = database.getReference();
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageReference = storage.getReference();
    StorageReference pathRef = storageReference.child("image");

    final long ONE_MEGABYTE = 5 * 1024 * 1024;

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

        searchText = (EditText) findViewById(R.id.search_item_name);

        Button search = (Button) findViewById(R.id.btn_search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchItemMap(searchText.getText().toString());
            }
        });

        gridView = (GridView) findViewById(R.id.gridview);
        gridView.setAdapter(new ImageAdapter(this));


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
        ref.child("items").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Item item = snapshot.getValue(Item.class);
                    itemList.add(item);
                    System.out.println("Adding " + item.getImgTitle());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void searchItemMap(String name){

        searchList.clear();
        searchList_Bitmap.clear();

    /*
        for (Item item : itemList) {
            System.out.println("SHoulda " + item.getImgTitle());
            if (item.getImgTitle().equals(name)){
                System.out.println("We have it here ");
            }
        }
        */


        System.out.println("Searching for " +name);
        System.out.println("Size of itemlist " + itemList.size());
        //Refresh itemmap
        System.out.println("Contain " + itemList);


        //retrieveItemMap();
        for(Item currItem : itemList) {
            //Break spaces from currItem

            System.out.println("Looking at "+currItem.getImgTitle());
            String[] parts = explodeString(currItem.getImgTitle());
            for(String part : parts) {
                System.out.println("Comparing " + part +" with " + name);
                if (part.toLowerCase().equals(name.toLowerCase())) {
                    searchList.add(currItem);
                    System.out.println(currItem.getImgTitle());
                    System.out.println("Should download " +currItem.getImgUrl());
                    System.out.println("Size of searchlist " + searchList.size());
                    break;
                }
            }

        }
        downloadToMemory();
    }

    public String[] explodeString(String text) {
        String[] parts = text.split(" ");
        return parts;
    }

    public void downloadToMemory() {
        for(Item item : searchList) {
            bmap_lol = null;
            String imgFilename = item.getImgUrl();
            System.out.println(pathRef.child(imgFilename).getName());
            pathRef.child(imgFilename).getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    //Data for image
                    bmap_lol = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    searchList_Bitmap.add(bmap_lol);
                    Toast.makeText(SearchActivity.this, "Success download", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(SearchActivity.this, "Failed download", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    /*private void downloadToMemory() {
        index = 0;
        for(Item item : searchList) {
            final String imgFilename = item.getImgUrl();
            pathRef.child(imgFilename).getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    //Data for image
                    Bitmap bmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                    searchList_Bitmap_Int.put(index, HashMap.Entry<imgFilename, bmap>);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
            index++;
        }
    }*/

    }
