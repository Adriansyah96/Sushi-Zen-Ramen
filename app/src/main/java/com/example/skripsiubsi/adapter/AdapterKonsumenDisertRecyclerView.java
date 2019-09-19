package com.example.skripsiubsi.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.skripsiubsi.R;
import com.example.skripsiubsi.konsumen.Add;
import com.example.skripsiubsi.konsumen.ReadDetail_Konsumen;
import com.example.skripsiubsi.model.Makanan;

import java.util.ArrayList;

public class AdapterKonsumenDisertRecyclerView extends RecyclerView.Adapter<AdapterKonsumenDisertRecyclerView.ViewHolder> {

    private ArrayList<Makanan> daftarMakanankonsumendisert;
    private Context context;
    private String nama,meja;

    public AdapterKonsumenDisertRecyclerView(ArrayList<Makanan>diserts, Context ctx,String nama, String meja){
        daftarMakanankonsumendisert = diserts;
        context = ctx;
        this.nama=nama;
        this.meja=meja;
    }
    class ViewHolder extends RecyclerView.ViewHolder{

        TextView objectdisert;
        ImageView imagekonsumendisert;
        CardView carddatakonsumendisert;
        Button adddisert;
        public TextView status;


        ViewHolder(View view){
            super( view);
            objectdisert = (TextView) view.findViewById(R.id.tv_namaobjectkonsumendisert);
            imagekonsumendisert = (ImageView) view.findViewById(R.id.img_itemkonsumendisert);
            carddatakonsumendisert = (CardView) view.findViewById(R.id.cardDataKonsumendisert);
            adddisert = (Button) view.findViewById(R.id.adddisert);
            status = view.findViewById(R.id.status_konsumendessert);


        }
    }

    @Override
    public ViewHolder onCreateViewHolder (ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_konsumendisert, parent , false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,final int position) {

        holder.adddisert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String stat = daftarMakanankonsumendisert.get(position).getStatus();

                if (!stat.equalsIgnoreCase("Tersedia")) {

                    Toast.makeText(context, "Maaf, Tidak Tersedia", Toast.LENGTH_SHORT).show();


                } else {

                    Intent intent = new Intent(context, Add.class);
                    intent.putExtra("data", daftarMakanankonsumendisert.get(position));
                    intent.putExtra("nama", nama);
                    intent.putExtra("meja", meja);
                    context.startActivity(intent);
                }
            }
        });

        final String name = daftarMakanankonsumendisert.get(position).getNama();
        Glide.with(context).load(daftarMakanankonsumendisert.get(position).getImage()).into(holder.imagekonsumendisert);

        holder.carddatakonsumendisert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                context.startActivity(ReadDetail_Konsumen.getActIntent((Activity)context).putExtra("data", daftarMakanankonsumendisert.get(position)));
                /**
                 *  Kodingan untuk tutorial Selanjutnya :p Read detail data
                 */
            }
        });

        holder.objectdisert.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {


                /**
                 *  Kodingan untuk tutorial Selanjutnya :p Delete dan update data
                 */
                return true;
            }
        });



        holder.objectdisert.setText(name);
        holder.status.setText(daftarMakanankonsumendisert.get(position).getStatus());
    }
    @Override
    public int getItemCount() {
        /**
         * Mengembalikan jumlah item pada barang
         */
        return daftarMakanankonsumendisert.size();
    }
}

