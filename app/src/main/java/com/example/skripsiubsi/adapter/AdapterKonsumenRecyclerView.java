package com.example.skripsiubsi.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
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
import com.example.skripsiubsi.konsumen.Cart;
import com.example.skripsiubsi.konsumen.ReadDetail_Konsumen;
import com.example.skripsiubsi.model.Makanan;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;

public class AdapterKonsumenRecyclerView extends RecyclerView.Adapter<AdapterKonsumenRecyclerView.ViewHolder> {

    private ArrayList<Makanan> daftarMakanankonsumen;
    private Context context;
    private String meja, nama;



    public AdapterKonsumenRecyclerView(ArrayList<Makanan> daftarMakanankonsumen, Context context, String nama, String meja) {
        this.daftarMakanankonsumen = daftarMakanankonsumen;
        this.context = context;
        this.meja = meja;
        this.nama = nama;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView object;
        ImageView imagekonsumen;
        CardView carddatakonsumen;
        Button add;
        TextView status;


        ViewHolder(View view){
            super( view);
            object = (TextView) view.findViewById(R.id.tv_namaobjectkonsumen);
            imagekonsumen = (ImageView) view.findViewById(R.id.img_itemkonsumen);
            carddatakonsumen = (CardView) view.findViewById(R.id.cardDataKonsumen);
            add = (Button) view.findViewById(R.id.add);
            status = view.findViewById(R.id.status_konsumen);



        }

    }

    @Override
    public ViewHolder onCreateViewHolder (ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_konsumen, parent , false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,final int position) {






        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String stat = daftarMakanankonsumen.get(position).getStatus();
                        if (!stat.equalsIgnoreCase("Tersedia")) {

                            Toast.makeText(context, "Maaf, Tidak Tersedia", Toast.LENGTH_SHORT).show();


                        } else {
                            Intent intent = new Intent(context, Add.class );
                            intent.putExtra("data", daftarMakanankonsumen.get(position));
                            intent.putExtra("nama", nama);
                            intent.putExtra("meja", meja);
                            context.startActivity(intent);
                        }



            }
        });

        final String name = daftarMakanankonsumen.get(position).getNama();

        Glide.with(context).load(daftarMakanankonsumen.get(position).getImage()).into(holder.imagekonsumen);

        holder.carddatakonsumen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                context.startActivity(ReadDetail_Konsumen.getActIntent((Activity)context).putExtra("data", daftarMakanankonsumen.get(position)));
                /**
                 *  Kodingan untuk tutorial Selanjutnya :p Read detail data
                 */

            }
        });


        holder.object.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {


                /**
                 *  Kodingan untuk tutorial Selanjutnya :p Delete dan update data
                 */
                return true;
            }
        });



        holder.object.setText(name);
        holder.status.setText(daftarMakanankonsumen.get(position).getStatus());
    }


    @Override
    public int getItemCount() {
        /**
         * Mengembalikan jumlah item pada barang
         */
        return daftarMakanankonsumen.size();
    }

}

