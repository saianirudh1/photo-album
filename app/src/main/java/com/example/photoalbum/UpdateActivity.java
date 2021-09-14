package com.example.photoalbum;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class UpdateActivity extends AppCompatActivity {

    private ImageView imageViewUpdate;
    private EditText updateTitle, updateDescription;
    private Button buttonUpdate;

    private int id;
    private String title, description;
    private byte[] image;

    private Bitmap selectedImage;
    private Bitmap scaledImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        imageViewUpdate = findViewById(R.id.updateImage);
        updateTitle = findViewById(R.id.updateTitle);
        updateDescription = findViewById(R.id.updateDescription);
        buttonUpdate = findViewById(R.id.buttonUpdate);

        id = getIntent().getIntExtra("id", -1);
        title = getIntent().getStringExtra("title");
        description = getIntent().getStringExtra("description");
        image = getIntent().getByteArrayExtra("image");

        imageViewUpdate.setImageBitmap(BitmapFactory.decodeByteArray(image, 0, image.length));
        updateTitle.setText(title);
        updateDescription.setText(description);

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 2);
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            try {
                if (Build.VERSION.SDK_INT >= 28) {
                    ImageDecoder.Source source = ImageDecoder.createSource(this.getContentResolver(), data.getData());
                    selectedImage = ImageDecoder.decodeBitmap(source);
                } else {
                    selectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                }

                imageViewUpdate.setImageBitmap(selectedImage);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public Bitmap makeSmall(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float ratio = (float) width / (float) height;

        if (ratio > 1) {
            width = maxSize;
            height = (int) (width / ratio);
        } else {
            height = maxSize;
            width = (int) (height * ratio);
        }

        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    public void updateData() {

        if (id == -1) {
            Toast.makeText(UpdateActivity.this, "There is a problem", Toast.LENGTH_SHORT).show();
            return;
        }

        String title = updateTitle.getText().toString();
        String description = updateDescription.getText().toString();
        Intent intent = new Intent();
        intent.putExtra("id", id);
        intent.putExtra("title", title);
        intent.putExtra("description", description);

        if (selectedImage == null) {
            intent.putExtra("image", image);
        } else {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            scaledImage = makeSmall(selectedImage, 300);
            selectedImage.compress(Bitmap.CompressFormat.PNG, 50, outputStream);
            byte[] image = outputStream.toByteArray();
            intent.putExtra("image", image);
        }

        setResult(RESULT_OK, intent);
        finish();
    }
}