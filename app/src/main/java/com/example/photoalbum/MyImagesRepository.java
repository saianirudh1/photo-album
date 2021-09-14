package com.example.photoalbum;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class MyImagesRepository {

    private MyImagesDAO myImagesDAO;
    private LiveData<List<MyImages>> imageList;

    public MyImagesRepository(Application application) {
        MyImagesDatabase database = MyImagesDatabase.getInstance(application);
        myImagesDAO = database.myImagesDAO();
        imageList = myImagesDAO.getMyImages();
    }

    public void insert(MyImages images) {
        new InsertImageAsyncTask(myImagesDAO).execute(images);
    }

    public void update(MyImages images) {
        new UpdateImageAsyncTask(myImagesDAO).execute(images);
    }

    public void delete(MyImages images) {
        new DeleteImageAsyncTask(myImagesDAO).execute(images);
    }

    public LiveData<List<MyImages>> getAllImages() {
        return imageList;
    }

    public static class InsertImageAsyncTask extends AsyncTask<MyImages, Void, Void> {
        private MyImagesDAO imagesDAO;

        public InsertImageAsyncTask(MyImagesDAO imagesDAO) {
            this.imagesDAO = imagesDAO;
        }

        @Override
        protected Void doInBackground(MyImages... myImages) {
            imagesDAO.insert(myImages[0]);
            return null;
        }
    }

    public static class UpdateImageAsyncTask extends AsyncTask<MyImages, Void, Void> {
        private MyImagesDAO imagesDAO;

        public UpdateImageAsyncTask(MyImagesDAO imagesDAO) {
            this.imagesDAO = imagesDAO;
        }

        @Override
        protected Void doInBackground(MyImages... myImages) {
            imagesDAO.update(myImages[0]);
            return null;
        }
    }

    public static class DeleteImageAsyncTask extends AsyncTask<MyImages, Void, Void> {
        private MyImagesDAO imagesDAO;

        public DeleteImageAsyncTask(MyImagesDAO imagesDAO) {
            this.imagesDAO = imagesDAO;
        }

        @Override
        protected Void doInBackground(MyImages... myImages) {
            imagesDAO.delete(myImages[0]);
            return null;
        }
    }

}
