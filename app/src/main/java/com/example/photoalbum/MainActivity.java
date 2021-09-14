package com.example.photoalbum;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ImagesViewModel imagesViewModel;
    RecyclerView recyclerView;
    FloatingActionButton addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        addButton = findViewById(R.id.addButton);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        MyImagesAdapter adapter = new MyImagesAdapter();
        recyclerView.setAdapter(adapter);

        imagesViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication())
                .create(ImagesViewModel.class);
        imagesViewModel.getAllImages().observe(MainActivity.this, new Observer<List<MyImages>>() {
            @Override
            public void onChanged(List<MyImages> myImages) {
                adapter.setImages(myImages);
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddImageActivity.class);
                startActivityForResult(intent,3);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                imagesViewModel.delete(adapter.getPosition(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnImageClickListener(new MyImagesAdapter.onImageClickListener() {
            @Override
            public void onImageClick(MyImages myImages) {
                Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
                intent.putExtra("id",myImages.getImageId());
                intent.putExtra("title",myImages.getImageTitle());
                intent.putExtra("description",myImages.getImageDescription());
                intent.putExtra("image",myImages.getImage());
                startActivityForResult(intent, 4);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 3 && resultCode == RESULT_OK && data != null){
            String title = data.getStringExtra("title");
            String description = data.getStringExtra("description");
            byte image[] = data.getByteArrayExtra("image");

            MyImages myImages = new MyImages(title,description,image);
            imagesViewModel.insert(myImages);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}