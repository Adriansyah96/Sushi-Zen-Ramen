package com.example.skripsiubsi.adapter;

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
import com.example.skripsiubsi.admin.Admmin_Key_Kasir;
import com.example.skripsiubsi.admin.ReadOrder;
import com.example.skripsiubsi.dapur.Dapur;
import com.example.skripsiubsi.dapur.DetailDapur;
import com.example.skripsiubsi.model.Konsumen;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdapterKeyAdminrRecyclerView extends RecyclerView.Adapter<AdapterKeyAdminrRecyclerView.ViewHolder> {

    private ArrayList<Konsumen> daftarDapurkey;
    private Context context;
    private FirebaseDataListener listener;
    private DatabaseReference databasedapur;


    public AdapterKeyAdminrRecyclerView(ArrayList<Konsumen>konsumens, Context ctx){
        daftarDapurkey = konsumens;
        context = ctx;
        listener = (Admmin_Key_Kasir)ctx;

    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView itemkey,status,itemjumlahpesanandapur,totalbayar;
        CardView carddapurkey;




        ViewHolder(View view){
            super( view);
            itemkey = (TextView) view.findViewById(R.id.admin_item_key);
            carddapurkey = (CardView) view.findViewById(R.id.cardadmin_key);
            status = (TextView) view.findViewById(R.id.status_item_count);






        }

    }


    @Override
    public ViewHolder onCreateViewHolder (ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_adminkey_kasir, parent , false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        databasedapur = FirebaseDatabase.getInstance().getReference();


        final String namekey = daftarDapurkey.get(position).getKey();
        final String status = daftarDapurkey.get(position).getStatus();

        databasedapur.child("Daftar Konsumen").child(namekey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    String key = ds.getKey();

                    String status = ds.child("status").getValue().toString();
                    holder.status.setText(status);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        holder.carddapurkey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                    Intent intent = new Intent(context.getApplicationContext(), ReadOrder.class);
                    intent.putExtra("key", namekey);




                context.startActivity(intent);
//                context.startActivity(DetailDapur.getActIntent((Activity)context).putExtra("key", daftarDapur.get(position)));
            }
        });
        holder.carddapurkey.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {



                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Apakah anda yakin akan menghapus data?")
                        .setCancelable(false)
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                listener.onDeleteData(daftarDapurkey.get(position),position);

                            }
                        })
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
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
        return daftarDapurkey.size();
    }
    public interface FirebaseDataListener{
        void onDeleteData(Konsumen konsumen, int position);
    }
}

