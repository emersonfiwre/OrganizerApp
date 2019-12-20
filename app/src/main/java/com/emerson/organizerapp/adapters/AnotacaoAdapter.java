package com.emerson.organizerapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.emerson.organizerapp.R;
import com.emerson.organizerapp.beans.Anotacao;
import com.emerson.organizerapp.interfaces.RecyclerViewOnClickListenerHack;
import java.util.List;

public class AnotacaoAdapter extends  RecyclerView.Adapter<AnotacaoAdapter.MyViewHolder> {
    private List<Anotacao> anotacaoList;
    private LayoutInflater layoutInflater;
    private RecyclerViewOnClickListenerHack recyclerViewOnClickListenerHack;

    public AnotacaoAdapter(Context context, List<Anotacao> anotacaoList){
            this.anotacaoList = anotacaoList;
            this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i("AnotacaoAdapter","onCreateViewHolder()");
        View v = layoutInflater.inflate(R.layout.card_annotation,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull  MyViewHolder holder, final int position) {
        Log.i("AnotacaoAdapter","onBindViewHolder()");
        holder.txtMateria.setText(anotacaoList.get(position).getNome());
        if(position == anotacaoList.size() -1){
            holder.divider.setVisibility(View.INVISIBLE);
        }
    }

    public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack r){
        recyclerViewOnClickListenerHack = r;
    }

    public void addView(Anotacao anotacao){
        anotacaoList.add(anotacao);
        notifyItemInserted(anotacaoList.size());

    }
    public void removeListItem( int position){
        anotacaoList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return anotacaoList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener , View.OnLongClickListener {
        public CardView card;
        public ImageView imgMateria;
        public TextView txtMateria;
        public View divider;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            card = itemView.findViewById(R.id.card_materias);
            imgMateria = itemView.findViewById(R.id.img_materia);
            txtMateria = itemView.findViewById(R.id.txt_materia);
            divider = itemView.findViewById(R.id.divider);

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
