package com.example.anthonynguyen.sdhack17_01;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;

import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anthonynguyen.sdhack17_01.models.Item;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by anthonynguyen on 10/21/17.
 */

public class CameraActivity extends AppCompatActivity {
    static final int REQUEST_TAKE_PHOTO = 123;
    ImageView img;
    String mCurrentPhotoPath;
    private DatabaseReference mDatabaseRef;
    private StorageReference mStorageRef;
    private EditText txtImgName;
    private Uri imgUri;
    private String imgName;
    private TextView expDate;
    private EditText quantity;

    //Calendar
    private int year;
    private int month;
    private int day;
    private boolean dateCheck = false;

    public static final String FB_STORAGE_PATH = "image/";
    public static final String FB_DATABASE_PATH = "image";

    @Override
    protected void onCreate(Bundle saveInstancedState) {
        super.onCreate(saveInstancedState);
        setContentView(R.layout.activity_camera);
        Button takePictureBtn = (Button) findViewById(R.id.takePicture);
        takePictureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
        expDate = (TextView) findViewById(R.id.expirationdate);
        Button setDate = (Button) findViewById(R.id.btn_setDate);
        setDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate();
            }
        });
        quantity = (EditText) findViewById(R.id.txt_quantity);
        Button sendPictureBtn = (Button) findViewById(R.id.sendImg);
        sendPictureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendButton();
            }
        });

        //Storage
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();

        txtImgName = (EditText) findViewById(R.id.imgName);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File...
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(CameraActivity.this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_TAKE_PHOTO && resultCode == Activity.RESULT_OK) {
            //Bitmap image = (Bitmap) data.getExtras().get("data");
            //updateImageView(image);
            //saveImage(image);
            Bitmap fullres = fileToBitmap();
            saveImage(fullres);
            updateImageView(fullres);
        }

    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

/*
    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }
    */

    private Bitmap fileToBitmap() {
        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
        Bitmap rotatedBitmap = null;
        try {
            ExifInterface ei = new ExifInterface(mCurrentPhotoPath);
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotatedBitmap = rotateImage(bitmap, 90);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotatedBitmap = rotateImage(bitmap, 180);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotatedBitmap = rotateImage(bitmap, 270);
                    break;
                case ExifInterface.ORIENTATION_NORMAL:
                default:
                    rotatedBitmap = bitmap;

            }
        } catch (Exception e) {
        }
        if(rotatedBitmap == null) {
            rotatedBitmap = bitmap;
        }
        return rotatedBitmap;
    }

    public static Bitmap rotateImage(Bitmap bmap, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(bmap, 0, 0, bmap.getWidth(), bmap.getHeight(),
                matrix, true);
    }

    private void saveImage(Bitmap bmap) {
        String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString() + "/Camera/SDHACK01";
        File myDir = new File(root);
        myDir.mkdirs();
        String fname = "Image-" + getTime() + ".png";
        imgName = fname;
        File file = new File(myDir, fname);
        setUri(); // Setting uri for future send
        if(file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            bmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        MediaScannerConnection.scanFile(CameraActivity.this, new String[]{file.getPath()}, new String[]{"image/jpeg"}, null);
    }

    public String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String currentDateandTime = sdf.format(new Date());
        return currentDateandTime;
    }

    public void updateImageView(Bitmap bmap) {
        img = (ImageView) findViewById(R.id.imgView);
        img.setImageBitmap(bmap);
    }

    public void setUri() {
        imgUri = Uri.fromFile(new File(mCurrentPhotoPath));
    }

    public void sendButton() {
        System.out.println(imgUri.toString());System.out.println(imgUri.toString());
        System.out.println(imgUri.toString());
        System.out.println(imgUri.toString());
        System.out.println(imgUri.toString());
        // Check for any breaking in item information
        if(!databaseItemCheck()) return;

        System.out.println(imgUri.toString());
        // Refernce to storage
        StorageReference ref = mStorageRef.child(FB_STORAGE_PATH + imgName);

        // Adding file to ref
        ref.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Toast.makeText(CameraActivity.this, "Upload successful", Toast.LENGTH_SHORT).show();
                setUpDatabaseItem();
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(CameraActivity.this, "Upload unsuccessful. Please try again.", Toast.LENGTH_SHORT
                        ).show();
                    }
                })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                            }
                        });
    }

    private void setUpDatabaseItem() {
        Item item = new Item(txtImgName.getText().toString(), imgName, expDate.getText().toString(), 5555555, "1234 Gay Ave", Integer.parseInt(quantity.getText().toString()));
        mDatabaseRef.child("items").child(txtImgName.getText().toString()).setValue(item);
    }

    private boolean databaseItemCheck() {
        if(txtImgName.getText().toString().isEmpty()) return false;
        if(imgName == null) return false;
        if(!dateCheck) return false;
        int quant = Integer.parseInt(quantity.getText().toString());
        if(quant > 0) return true; else return false;
    }

    private void setDate() {
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthofyear, int dayofmonth) {
                expDate.setText("Set to expire: " + dayofmonth + "-" + monthofyear + "-" + year);
                dateCheck = true;
            }
        }, year, month, day);
        dpd.show();
    }

}
