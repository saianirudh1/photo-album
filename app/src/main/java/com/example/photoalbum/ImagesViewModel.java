package com.example.photoalbum;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ImagesViewModel extends AndroidViewModel {
    private MyImagesRepository imagesRepository;
    private LiveData<List<MyImages>> imageList;

    public ImagesViewModel(@NonNull Application application) {
        super(application);
        imagesRepository = new MyImagesRepository(application);
        imageList = imagesRepository.getAllImages();
    }

    public void insert(MyImages images){
        imagesRepository.insert(images);
    }

    public void update(MyImages images){
        imagesRepository.insert(images);
    }

    public void delete(MyImages images){
        imagesRepository.insert(images);
    }

    public LiveData<List<MyImages>> getAllImages(){
        return imageList;
    }
}
