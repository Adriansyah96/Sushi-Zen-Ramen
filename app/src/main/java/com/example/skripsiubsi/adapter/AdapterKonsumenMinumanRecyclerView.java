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

public class AdapterKonsumenMinumanRecyclerView extends RecyclerView.Adapter<AdapterKonsumenMinumanRecyclerView.ViewHolder> {

    private ArrayList<Makanan> daftarMakanankonsumenminuman;
    private Context context;
    private String nama,meja;

    public AdapterKonsumenMinumanRecyclerView(ArrayList<Makanan>minumans, Context ctx,String nama, String meja){
        daftarMakanankonsumenminuman = minumans;
        context = ctx;
        this.meja = meja;
        this.nama = nama;
    }
    class ViewHolder extends RecyclerView.ViewHolder{

        TextView objectminuman;
        ImageView imagekonsumenminuman;
        CardView carddatakonsumenminuman;
        Button add;
        public TextView status;


        ViewHolder(View view){
            super( view);
            objectminuman = (TextView) view.findViewById(R.id.tv_namaobjectkonsumenminuman);
            imagekonsumenminuman = (ImageView) view.findViewById(R.id.img_itemkonsumenminuman);
            carddatakonsumenminuman = (CardView) view.findViewById(R.id.cardDataKonsumenminuman);
            add = (Button) view.findViewById(R.id.addminuman);
            status = view.findViewById(R.id.status_konsumenminuman);


        }
    }

    @Override
    public ViewHolder onCreateViewHolder (ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_konsumenminuman, parent , false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,final int position) {

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stat = daftarMakanankonsumenminuman.get(position).getStatus();

                if (!stat.equalsIgnoreCase("Tersedia")) {

                    Toast.makeText(context, "Maaf, Tidak Tersedia", Toast.LENGTH_SHORT).show();


                } else {

                    Intent intent = new Intent(context, Add.class);
                    intent.putExtra("data", daftarMakanankonsumenminuman.get(position));
                    intent.putExtra("nama", nama);
                    intent.putExtra("meja", meja);
                    context.startActivity(intent);
                }
            }
        });

        final String name = daftarMakanankonsumenminuman.get(position).getNama();
        Glide.with(context).load(daftarMakanankonsumenminuman.get(position).getImage()).into(holder.imagekonsumenminuman);

        holder.carddatakonsumenminuman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                context.startActivity(ReadDetail_Konsumen.getActIntent((Activity)context).putExtra("data", daftarMakanankonsumenminuman.get(position)));
                /**
                 *  Kodingan untuk tutorial Selanjutnya :p Read detail data
                 */
            }
        });

        holder.objectminuman.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {


                /**
                 *  Kodingan untuk tutorial Selanjutnya :p Delete dan update data
                 */
                return true;
            }
        });



        holder.objectminuman.setText(name);
        holder.status.setText(daftarMakanankonsumenminuman.get(position).getStatus());
    }
    @Override
    public int getItemCount() {
        /**
         * Mengembalikan jumlah item pada barang
         */
        return daftarMakanankonsumenminuman.size();
    }
}

