package com.example.photoalbum;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "my_images")
public class MyImages {
    @PrimaryKey(autoGenerate = true)
    public int imageId;
    public String imageTitle;
    public String imageDescription;
    public byte[] image;

    public MyImages(String imageTitle, String imageDescription, byte[] image){
        this.imageTitle = imageTitle;
        this.imageDescription = imageDescription;
        this.image = image;
    }

    public int getImageId() {
        return imageId;
    }

    public String getImageTitle() {
        return imageTitle;
    }

    public String getImageDescription() {
        return imageDescription;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
