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
import com.example.skripsiubsi.konsumen.Add;
import com.example.skripsiubsi.konsumen.Cart;
import com.example.skripsiubsi.konsumen.ReadDetail_Konsumen;
import com.example.skripsiubsi.model.Konsumen;
import com.example.skripsiubsi.model.Makanan;

import java.util.ArrayList;

public class AdapterCartRecyclerView extends RecyclerView.Adapter<AdapterCartRecyclerView.ViewHolder> {

    private ArrayList<Konsumen> daftarCartkonsumen;
    private Context context;
    private FirebaseDataListener listener;



    public AdapterCartRecyclerView(ArrayList<Konsumen>konsumens, Context ctx){
        daftarCartkonsumen = konsumens;
        context = ctx;
        listener = (Cart)ctx;
    }
    class ViewHolder extends RecyclerView.ViewHolder{

        TextView totalhasil,itemnama,itemjumlahpesanan,itemhasil;
        CardView card_cart;

        Button pesansekarang;


        ViewHolder(View view){
            super( view);
            totalhasil = (TextView) view.findViewById(R.id.totalcart);
            itemnama = (TextView) view.findViewById(R.id.cart_item_name);
            itemjumlahpesanan = (TextView) view.findViewById(R.id.cart_item_jumlahpesanan);
            itemhasil = (TextView) view.findViewById(R.id.item_hasil);

            card_cart = (CardView) view.findViewById(R.id.cart_card);




        }

    }


    @Override
    public ViewHolder onCreateViewHolder (ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent , false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,final int position) {


        final String name = daftarCartkonsumen.get(position).getNama();
        final String jumlahpesanan = daftarCartkonsumen.get(position).getJumlahpesanan();
        final String hasil = daftarCartkonsumen.get(position).getHasil();

        holder.card_cart.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_view_dapur);
                dialog.setTitle("Silahkan Pilih Aksi");
                dialog.show();

                Button edtbutton = dialog.findViewById(R.id.bt_edit_data);
                Button delbutton = dialog.findViewById(R.id.bt_delete_data);




                delbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Koding Delete Data
                        dialog.dismiss();
                        listener.onDeleteData(daftarCartkonsumen.get(position),position);

                    }
                });

                /**
                 *  Kodingan untuk tutorial Selanjutnya :p Delete dan update data
                 */
                return true;
            }
        });



        holder.itemnama.setText(name);
        holder.itemjumlahpesanan.setText(jumlahpesanan);
        holder.itemhasil.setText(hasil);







    }
    @Override
    public int getItemCount() {
        /**
         * Mengembalikan jumlah item pada barang
         */
        return daftarCartkonsumen.size();
    }
    public interface FirebaseDataListener{
        void onDeleteData(Konsumen konsumen, int position);
    }
}

