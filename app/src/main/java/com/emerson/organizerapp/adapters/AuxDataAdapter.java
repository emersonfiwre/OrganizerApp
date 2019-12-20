package com.emerson.organizerapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.emerson.organizerapp.R;
import com.emerson.organizerapp.beans.AuxData;
import com.emerson.organizerapp.presenter.MensagemPresenter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AuxDataAdapter extends RecyclerView.Adapter<AuxDataAdapter.MyViewHolder> {
    private List<AuxData> dataList;
    private LayoutInflater layoutInflater;
    private Context context;

    public AuxDataAdapter(Context context,List<AuxData> dataList){
        this.context = context;
        this.dataList = dataList;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i("AuxDataAdapter","onCreateViewHolder()");
        View v = layoutInflater.inflate(R.layout.card_data_envio,parent,false);
        AuxDataAdapter.MyViewHolder myViewHolder = new AuxDataAdapter.MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.i("AuxDataAdapter","onBindViewHolder()");
        try{
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            Date result =  df.parse(dataList.get(position).getDataEnvio());

            SimpleDateFormat formataData = new SimpleDateFormat("EEE, d MMM yyyy");

            holder.txtData.setText(formataData.format(result));

        }catch (ParseException e){
            Log.e("onBindViewHolder", "ParseException: " + e.getMessage());
        }
        setupRecycler(holder,position);

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void addView(AuxData data){
        dataList.add(data);
        notifyItemInserted(dataList.size());

    }
    public void removeListItem( int position){
        dataList.remove(position);
        notifyItemRemoved(position);
    }

    private void setupRecycler(MyViewHolder holder, int position){
        holder.rvMensagem.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(context);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        holder.rvMensagem.setLayoutManager(llm);
        MensagemPresenter presenter = new MensagemPresenter(context);
        List<AuxData> datas = presenter.buscarTodos(dataList);
        MensagemAdapter adapter = new MensagemAdapter(context, datas.get(position).getMensagemList());
        holder.rvMensagem.setAdapter(adapter);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CardView card;
        public TextView txtData;
        public  RecyclerView rvMensagem;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            card = itemView.findViewById(R.id.card_data_envio);
            txtData = itemView.findViewById(R.id.txt_data);
            rvMensagem = itemView.findViewById(R.id.rv_mensagem);

        }
    }
}
