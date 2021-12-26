package com.softappsun.imagecapturetextfinderreport;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    Button btnCaptureImage;
    Button btn_save, image_analysis;
    ImageView imageDisplay;
    BitmapDrawable drawable;
    Bitmap bitmap;

    //Text Analysis API's KEY & LINK
    private final String API_KEY = "d1cb628b7b59451c99c62e989ff46e66";
    private final String API_LINK = "https://imagecapturetextfinderreport.cognitiveservices.azure.com/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCaptureImage = (Button) findViewById(R.id.capture_image_btn);
        btn_save = (Button)findViewById(R.id.btn_save);
        image_analysis = (Button)findViewById(R.id.image_analysis);
        imageDisplay = (ImageView) findViewById(R.id.image_view);


        image_analysis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,ImageAnalysis.class);
                startActivity(intent);
            }
        });



        //ON CLICK LISTENER FOR "SAVE" BUTTON//
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                FileOutputStream outputStream = null;
                drawable = (BitmapDrawable)imageDisplay.getDrawable();
                bitmap = drawable.getBitmap();
                File sdCard = Environment.getExternalStorageDirectory();
                File directory = new File(sdCard.getAbsolutePath() + "/Instant Images");
                directory.mkdir();
                String fileName = String.format("%d.jpg", System.currentTimeMillis());
                File outFile = new File(directory,fileName);


                try {
                    outputStream = new FileOutputStream(outFile);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100,outputStream);
                    outputStream.flush();
                    outputStream.close();



                    Toast.makeText(MainActivity.this, "Image saved successfully", Toast.LENGTH_SHORT).show();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });



        //ON CLICK LISTENER FOR "CAPTURE IMAGE" BUTTON//
        btnCaptureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bitmap bitmap = (Bitmap)data.getExtras().get("data");
        imageDisplay.setImageBitmap(bitmap);


    }
}

