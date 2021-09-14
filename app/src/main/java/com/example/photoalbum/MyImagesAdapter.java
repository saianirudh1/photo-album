package com.example.photoalbum;

import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyImagesAdapter extends RecyclerView.Adapter<MyImagesAdapter.MyImageHolder> {

    List<MyImages> images = new ArrayList<>();
    private onImageClickListener onImageClickListener;

    public void setImages(List<MyImages> images) {
        this.images = images;
        notifyDataSetChanged();
    }

    public void setOnImageClickListener(MyImagesAdapter.onImageClickListener onImageClickListener) {
        this.onImageClickListener = onImageClickListener;
    }

    public interface onImageClickListener {
        void onImageClick(MyImages myImages);
    }

    @NonNull
    @Override
    public MyImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_card, parent, false);
        return new MyImageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyImageHolder holder, int position) {
        MyImages myImages = images.get(position);
        holder.title.setText(myImages.getImageTitle());
        holder.description.setText(myImages.getImageDescription());
        holder.image.setImageBitmap(BitmapFactory.decodeByteArray(myImages.getImage(), 0, myImages.getImage().length));
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public MyImages getPosition(int position) {
        return images.get(position);
    }


    public class MyImageHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView title, description;

        public MyImageHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageView);
            title = itemView.findViewById(R.id.textViewTitle);
            description = itemView.findViewById(R.id.textViewDescription);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (onImageClickListener != null && position != RecyclerView.NO_POSITION) {
                        onImageClickListener.onImageClick(images.get(position));
                    }
                }
            });
        }
    }
}
