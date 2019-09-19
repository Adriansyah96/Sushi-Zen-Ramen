package com.example.skripsiubsi.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.net.sip.SipSession;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.skripsiubsi.R;
import com.example.skripsiubsi.admin.admindisert.Createdisert;
import com.example.skripsiubsi.admin.adminmakanan.Create;
import com.example.skripsiubsi.admin.adminmakanan.Read;
import com.example.skripsiubsi.admin.adminminuman.AdminMinuman;

import com.example.skripsiubsi.admin.adminminuman.Createminuman;
import com.example.skripsiubsi.admin.adminminuman.Readminuman;
import com.example.skripsiubsi.model.Makanan;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdapterMinumanRecyclerView extends RecyclerView.Adapter<AdapterMinumanRecyclerView.ViewHolder> {

    private ArrayList<Makanan> daftarDisert;
    private Context context;
    private FirebaseDataListener listener;

    public AdapterMinumanRecyclerView(ArrayList<Makanan>minumans, Context ctx){
        daftarDisert = minumans;
        context = ctx;
        listener = (Readminuman)ctx;

    }
    class ViewHolder extends RecyclerView.ViewHolder{

        TextView objectminuman;
        ImageView imageiteminuman;
        CardView carddataminuman;
        public Button ganti_stat;
        TextView status;



        ViewHolder(View view){
            super( view);
            objectminuman = (TextView) view.findViewById(R.id.tv_namaobjectminuman);
            imageiteminuman = (ImageView) view.findViewById(R.id.img_itemadminminuman);

            carddataminuman = (CardView) view.findViewById(R.id.cardDataminuman);
            ganti_stat = view.findViewById(R.id.btn_status_admin_minuman);
            status = view.findViewById(R.id.txtstatus_minuman_admin);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder (ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_minuman, parent , false);
    ViewHolder vh = new ViewHolder(v);
    return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,final int position) {

        final String name = daftarDisert.get(position).getNama();
        final String n_status = daftarDisert.get(position).getStatus();
        Glide.with(context).load(daftarDisert.get(position).getImage()).into(holder.imageiteminuman);

        holder.carddataminuman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 *  Kodingan untuk tutorial Selanjutnya :p Read detail data
                 */
            }
        });
        holder.carddataminuman.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_viewminuman);
                dialog.setTitle("Silahkan Pilih Aksi");
                dialog.show();

                Button edtbuttonminuman = dialog.findViewById(R.id.bt_edit_dataminuman);
                Button delbuttonminuman = dialog.findViewById(R.id.bt_delete_dataminuman);


                edtbuttonminuman.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Koding Edit data

                        context.startActivity(Createdisert.getActIntent((Activity) context).putExtra("data",daftarDisert.get(position)));
                    }
                });

                delbuttonminuman.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Koding Delete Data
                        dialog.dismiss();
                        listener.onDeleteData(daftarDisert.get(position),position);

                    }
                });

                /**
                 *  Kodingan untuk tutorial Selanjutnya :p Delete dan update data
                 */
                return true;
            }
        });

        holder.ganti_stat.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_view);
                dialog.setTitle("Silahkan Pilih Aksi");
                dialog.show();

                Button edtbutton = dialog.findViewById(R.id.bt_edit_data);
                Button delbutton = dialog.findViewById(R.id.bt_delete_data);
                edtbutton.setText("Tersedia");
                delbutton.setText("Tidak Tersedia");


                edtbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Koding Edit data

                        DatabaseReference dataku = FirebaseDatabase.getInstance().getReference().child("(Admin) Tambah Data Minuman").child(daftarDisert.get(position).getKey());
                        dataku.child("status").setValue("Tersedia");
                        dialog.dismiss();

                    }
                });

                delbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Koding Delete Data
                        DatabaseReference dataku = FirebaseDatabase.getInstance().getReference().child("(Admin) Tambah Data Minuman").child(daftarDisert.get(position).getKey());
                        dataku.child("status").setValue("Tidak Tersedia");
                        dialog.dismiss();
                    }
                });

                /**
                 *  Kodingan untuk tutorial Selanjutnya :p Delete dan update data
                 */
                return true;
            }
        });

        holder.objectminuman.setText(name);
        holder.status.setText(n_status);


    }
    @Override
    public int getItemCount() {
        /**
         * Mengembalikan jumlah item pada barang
         */
        return daftarDisert.size();
    }
    public interface FirebaseDataListener{
        void onDeleteData(Makanan makanan, int position);
    }
}

