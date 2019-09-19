package com.example.skripsiubsi.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.example.skripsiubsi.dapur.Dapur;
import com.example.skripsiubsi.dapur.DetailDapur;
import com.example.skripsiubsi.konsumen.Cart;
import com.example.skripsiubsi.konsumen.ReadDetail_Konsumen;
import com.example.skripsiubsi.model.Konsumen;

import java.util.ArrayList;

public class AdapterDapurRecyclerView extends RecyclerView.Adapter<AdapterDapurRecyclerView.ViewHolder> {

    private ArrayList<Konsumen> daftarDapur;
    private Context context;
    private FirebaseDataListener listener;


    public AdapterDapurRecyclerView(ArrayList<Konsumen>konsumens, Context ctx){
        daftarDapur = konsumens;
        context = ctx;
        listener = (Dapur)ctx;

    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView itemkey,itemjumlahpesanandapur,totalbayar;
        CardView carddapur;




        ViewHolder(View view){
            super( view);
            itemkey = (TextView) view.findViewById(R.id.dapur_item_key);
            carddapur = (CardView) view.findViewById(R.id.carditemdapur);






        }

    }


    @Override
    public ViewHolder onCreateViewHolder (ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dapur, parent , false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,final int position) {


        final String namekey = daftarDapur.get(position).getKey();

        holder.carddapur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), DetailDapur.class);
                intent.putExtra("key", namekey);
                context.startActivity(intent);
//                context.startActivity(DetailDapur.getActIntent((Activity)context).putExtra("key", daftarDapur.get(position)));
            }
        });
        holder.carddapur.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Pesanan ini telah selesai?")
                        .setCancelable(false)
                        .setPositiveButton("Selesai", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                listener.onDeleteData(daftarDapur.get(position),position);

                            }
                        })
                        .setNegativeButton("Belum", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

                /**
                 *  Kodingan untuk tutorial Selanjutnya :p Delete dan update data
                 */
                return true;
            }
        });

        holder.itemkey.setText(namekey);



    }
    @Override
    public int getItemCount() {
        /**
         * Mengembalikan jumlah item pada barang
         */
        return daftarDapur.size();
    }
    public interface FirebaseDataListener{
        void onDeleteData(Konsumen konsumen, int position);
    }
}

