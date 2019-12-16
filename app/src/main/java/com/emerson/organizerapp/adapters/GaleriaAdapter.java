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

public class GaleriaAdapter extends  RecyclerView.Adapter<GaleriaAdapter.MyViewHolder> {
    private List<String> galeriaList;
    private LayoutInflater layoutInflater;
    private RecyclerViewOnClickListenerHack recyclerViewOnClickListenerHack;

    public GaleriaAdapter(Context context, List<String> galeria){
        this.galeriaList = galeria;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    @NonNull
    @Override
    public GaleriaAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i("LOG","onCreateViewHolder()");
        View v = layoutInflater.inflate(R.layout.card_gallery,parent,false);
        GaleriaAdapter.MyViewHolder myViewHolder = new GaleriaAdapter.MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GaleriaAdapter.MyViewHolder holder, int position) {
        Log.i("LOG","onBindViewHolder()");

            try {
                Log.i("Log", "Whats Path : " + galeriaList.get(position));
            File f = new File(galeriaList.get(position));
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            holder.image.setImageBitmap(b);

        }catch (FileNotFoundException e){
            Log.i("Log", "FileNotFoundException() : " + e.getCause());
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return galeriaList.size();
    }
    public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack r){
        recyclerViewOnClickListenerHack = r;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public ImageView image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image_gallery);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if( recyclerViewOnClickListenerHack != null ) {
                recyclerViewOnClickListenerHack.onClickListener(view,getAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View view) {
            if( recyclerViewOnClickListenerHack != null ) {
                recyclerViewOnClickListenerHack.onLongPressClickListener(view,getAdapterPosition());
            }
            return false;
        }
    }
}
