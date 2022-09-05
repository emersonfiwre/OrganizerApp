package com.emerson.organizerapp.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.emerson.organizerapp.R;
import com.emerson.organizerapp.interfaces.RecyclerViewOnClickListenerHack;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class ImagemAdapter extends RecyclerView.Adapter<ImagemAdapter.MyViewHolder> {
    private List<String> imagelist;
    private LayoutInflater layoutInflater;
    private RecyclerViewOnClickListenerHack recyclerViewOnClickListenerHack;
    private Context context;
    public int selectedPosition= 0;

    public ImagemAdapter(Context context, List<String> images) {
        this.imagelist = images;
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public ImagemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i("LOG", "onCreateViewHolder()");
        View v = layoutInflater.inflate(R.layout.card_image, parent, false);
        ImagemAdapter.MyViewHolder myViewHolder = new ImagemAdapter.MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImagemAdapter.MyViewHolder holder, int position) {
        Log.i("LOG", "onBindViewHolder()");

        try {
            Log.i("onBindViewHolder()", "Whats Path: " + imagelist.get(position));
            File f = new File(imagelist.get(position));
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            holder.image.setImageBitmap(b);
            if(selectedPosition==position)
                holder.itemView.setBackground(context.getDrawable(R.drawable.shape_border_selected));
            else
                holder.itemView.setBackground(null);
            /*if(position == 0){

            }*/


        } catch (FileNotFoundException e) {
            Log.i("Log", "FileNotFoundException() : " + e.getCause());
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return imagelist.size();
    }

    public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack r) {
        recyclerViewOnClickListenerHack = r;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public ImageView image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (recyclerViewOnClickListenerHack != null) {
                recyclerViewOnClickListenerHack.onClickListener(view, getAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View view) {
            if (recyclerViewOnClickListenerHack != null) {
                recyclerViewOnClickListenerHack.onLongPressClickListener(view, getAdapterPosition());
            }
            return false;
        }
    }
}
