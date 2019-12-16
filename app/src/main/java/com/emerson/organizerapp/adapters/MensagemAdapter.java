package com.emerson.organizerapp.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.emerson.organizerapp.R;
import com.emerson.organizerapp.beans.Mensagem;
import com.emerson.organizerapp.interfaces.RecyclerViewOnClickListenerHack;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class MensagemAdapter extends  RecyclerView.Adapter<MensagemAdapter.MyViewHolder>  {
    private List<Mensagem> mensagemList;
    private LayoutInflater layoutInflater;
    private RecyclerViewOnClickListenerHack recyclerViewOnClickListenerHack;


    public MensagemAdapter(Context context, List<Mensagem> mensagem){
        this.mensagemList = mensagem;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Log.i("LOG","onCreateViewHolder()");
        View v = layoutInflater.inflate(R.layout.card_chat,parent,false);
        MensagemAdapter.MyViewHolder myViewHolder = new MensagemAdapter.MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.i("LOG","onBindViewHolder()");
        holder.txtMenssage.setText(mensagemList.get(position).getMenssagem());
        holder.txtData.setText(mensagemList.get(position).getData());

        if(mensagemList.get(position).getImagem() != null) {
            try {
                Log.i("Log", "Whats Path : " + mensagemList.get(position).getImagem());
                File f = new File(mensagemList.get(position).getImagem());
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
                holder.imageMenssage.setImageBitmap(b);
                holder.txtMenssage.setText(mensagemList.get(position).getMenssagem());
                holder.imageMenssage.setVisibility(View.VISIBLE);
                holder.txtMenssage.setVisibility(View.GONE);

            } catch (FileNotFoundException e) {
                Log.i("Log", "FileNotFoundException() : " + e.getCause());
                e.printStackTrace();
            }
        }else{
            holder.txtMenssage.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public int getItemCount() {
        return mensagemList.size();
    }

    public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack r){
        recyclerViewOnClickListenerHack = r;
    }

    public void addView(Mensagem messagem, int position){
        mensagemList.add(messagem);
        notifyItemInserted(position);
    }
    public void removeListItem( int position){
        mensagemList.remove(position);
        notifyItemRemoved(position);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public TextView txtMenssage;
        public TextView txtData;
        public ImageView imageMenssage;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtMenssage = itemView.findViewById(R.id.txt_message);
            txtData = itemView.findViewById(R.id.txt_date);
            imageMenssage = itemView.findViewById(R.id.image_message);

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
