package com.example.anthonynguyen.sdhack17_01;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by anthonynguyen on 10/22/17.
 */

public class GridViewActivity extends AppCompatActivity {

    private ImageView selectedImage;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);

        setContentView(R.layout.activity_gridview);
        //selectedImage = (ImageView) findViewById(R.id.selectedImage);
        int position = getIntent().getIntExtra("int", 0);

        //selectedImage.setImageBitmap(SearchActivity.searchList_Bitmap.get(position));

        TextView title = (TextView) findViewById(R.id.info_title);
        TextView address = (TextView) findViewById(R.id.info_address);
        TextView phone = (TextView) findViewById(R.id.info_phone);
        TextView quantity = (TextView) findViewById(R.id.info_quantity);
        TextView expire = (TextView) findViewById(R.id.info_expire);
        title.setText(SearchActivity.searchList.get(position).getImgTitle());
        address.setText(SearchActivity.searchList.get(position).getAddress());
        phone.setText(SearchActivity.searchList.get(position).getPhone());
        quantity.setText(SearchActivity.searchList.get(position).getQuantity());
        expire.setText(SearchActivity.searchList.get(position).getExpirationDate());

        System.out.println(title.getText().toString() + address.getText().toString());
        System.out.println(title.getText().toString() + address.getText().toString());
        System.out.println(title.getText().toString() + address.getText().toString());
        System.out.println(title.getText().toString() + address.getText().toString());
        System.out.println(title.getText().toString() + address.getText().toString());
        System.out.println(title.getText().toString() + address.getText().toString());

    }
}
