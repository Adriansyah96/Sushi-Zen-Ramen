package com.example.skripsiubsi.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
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
import com.example.skripsiubsi.admin.adminmakanan.Create;
import com.example.skripsiubsi.admin.adminmakanan.Read;
import com.example.skripsiubsi.model.Makanan;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdapterMakananRecyclerView extends RecyclerView.Adapter<AdapterMakananRecyclerView.ViewHolder> {

    private ArrayList<Makanan> daftarMakanan;
    private Context context;
    private FirebaseDataListener listener;

    public AdapterMakananRecyclerView(ArrayList<Makanan>makanans,Context ctx){
        daftarMakanan = makanans;
        context = ctx;
        listener = (Read)ctx;
    }
    class ViewHolder extends RecyclerView.ViewHolder{

        TextView object;
        ImageView imageitemmakanan;
        CardView carddata;
        public Button ganti_stat;
        TextView status;



        ViewHolder(View view){
            super( view);
            object = (TextView) view.findViewById(R.id.tv_namaobject);
            imageitemmakanan = (ImageView) view.findViewById(R.id.img_itemadmin);

            carddata = (CardView) view.findViewById(R.id.cardData);
            ganti_stat = view.findViewById(R.id.btn_status_admin_makanan);
            status = view.findViewById(R.id.txtstatus_makanan_admin);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder (ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_makanan, parent , false);
    ViewHolder vh = new ViewHolder(v);
    return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,final int position) {

        final String name = daftarMakanan.get(position).getNama();
        final String n_status = daftarMakanan.get(position).getStatus();
        Glide.with(context).load(daftarMakanan.get(position).getImage()).into(holder.imageitemmakanan);

        holder.carddata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 *  Kodingan untuk tutorial Selanjutnya :p Read detail data
                 */
            }
        });
        holder.carddata.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_view);
                dialog.setTitle("Silahkan Pilih Aksi");
                dialog.show();

                Button edtbutton = dialog.findViewById(R.id.bt_edit_data);
                Button delbutton = dialog.findViewById(R.id.bt_delete_data);


                edtbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Koding Edit data

                        context.startActivity(Create.getActIntent((Activity) context).putExtra("data",daftarMakanan.get(position)));
                    }
                });

                delbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Koding Delete Data
                        dialog.dismiss();
                        listener.onDeleteData(daftarMakanan.get(position),position);

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

                        DatabaseReference dataku = FirebaseDatabase.getInstance().getReference().child("(Admin) Tambah Data").child(daftarMakanan.get(position).getKey());
                        dataku.child("status").setValue("Tersedia");
                        dialog.dismiss();

                    }
                });

                delbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Koding Delete Data
                        DatabaseReference dataku = FirebaseDatabase.getInstance().getReference().child("(Admin) Tambah Data").child(daftarMakanan.get(position).getKey());
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

        holder.object.setText(name);
        holder.status.setText(n_status);

    }
    @Override
    public int getItemCount() {
        /**
         * Mengembalikan jumlah item pada barang
         */
        return daftarMakanan.size();
    }
    public interface FirebaseDataListener{
        void onDeleteData(Makanan makanan, int position);
    }
}

