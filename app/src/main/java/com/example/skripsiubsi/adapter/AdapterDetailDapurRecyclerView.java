package com.example.skripsiubsi.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.skripsiubsi.R;
import com.example.skripsiubsi.dapur.DetailDapur;
import com.example.skripsiubsi.model.Konsumen;

import java.util.ArrayList;

public class AdapterDetailDapurRecyclerView extends RecyclerView.Adapter<AdapterDetailDapurRecyclerView.ViewHolder> {

    private ArrayList<Konsumen> daftardetailDapur;
    private Context context;



    public AdapterDetailDapurRecyclerView(ArrayList<Konsumen>konsumens, Context ctx){
        daftardetailDapur = konsumens;
        context = ctx;

    }
    class ViewHolder extends RecyclerView.ViewHolder{

        TextView nomejadapur,namamakanan,itemjumlahpesanandapur,namakonsumen;
        CardView carddetaildapur;




        ViewHolder(View view){
            super( view);
            namamakanan = (TextView) view.findViewById(R.id.detaildapur_item_name);
            nomejadapur = (TextView)view.findViewById(R.id.detaildapurnomeja);
            itemjumlahpesanandapur= (TextView) view.findViewById(R.id.detaildapur_item_jumlahpesanan);
            namakonsumen = (TextView)view.findViewById(R.id.detaildapur_item_namakonsumen);

            carddetaildapur = (CardView) view.findViewById(R.id.carddetaildapur);





        }

    }


    @Override
    public ViewHolder onCreateViewHolder (ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detaildapur, parent , false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,final int position) {


        final String namamakanan = daftardetailDapur.get(position).getNama();
        final String nomejadapur = daftardetailDapur.get(position).getNomejakonsumen();
        final String namakonsumen = daftardetailDapur.get(position).getNamakonsumen();
        final String jumlahpesanan = daftardetailDapur.get(position).getJumlahpesanan();









        holder.namamakanan.setText(namamakanan);
        holder.nomejadapur.setText(nomejadapur);
        holder.namakonsumen.setText(namakonsumen);
        holder.itemjumlahpesanandapur.setText(jumlahpesanan);



    }
    @Override
    public int getItemCount() {
        /**
         * Mengembalikan jumlah item pada barang
         */
        return daftardetailDapur.size();
    }

}

