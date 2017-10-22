package com.example.anthonynguyen.sdhack17_01.models;

import com.example.anthonynguyen.sdhack17_01.SearchActivity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.anthonynguyen.sdhack17_01.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

/**
 * Created by anthonynguyen on 10/22/17.
 */

public class ImageAdapter extends BaseAdapter {
    private Context mContext;



    // references to our images
    private List<Item> mThumbIds = SearchActivity.searchList;
    private List<Bitmap> mThumbIdsBitmap = SearchActivity.searchList_Bitmap;

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    StorageReference pathRef = storageRef.child("image");


    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(500, 500));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageBitmap(mThumbIdsBitmap.get(position));
        return imageView;
    }
/*
    public Bitmap downloadToMemory(int position) {
        Item item = mThumbIds.get(position);
        String imgFilename = item.getImgUrl();
        System.out.println("ALLAH" + imgFilename);
        System.out.println("ALLAH" + imgFilename);
        System.out.println("ALLAH" + imgFilename);
        System.out.println("ALLAH" + imgFilename);
        System.out.println("ALLAH" + imgFilename);
        System.out.println("ALLAH" + imgFilename);
        Bitmap bmap = null;
        pathRef.child(imgFilename+".jpg").getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                //Data for image
                bmap_lol = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        return bmap_lol;
    }
*/

}
