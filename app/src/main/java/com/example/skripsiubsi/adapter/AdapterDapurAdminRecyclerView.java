package com.example.skripsiubsi.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.skripsiubsi.R;
import com.example.skripsiubsi.model.Konsumen;

import java.util.ArrayList;

public class AdapterDapurAdminRecyclerView extends RecyclerView.Adapter<AdapterDapurAdminRecyclerView.ViewHolder> {

    private ArrayList<Konsumen> daftarDapurAdmin;
    private Context context;



    public AdapterDapurAdminRecyclerView(ArrayList<Konsumen>konsumens, Context ctx){
        daftarDapurAdmin = konsumens;
        context = ctx;

    }
    class ViewHolder extends RecyclerView.ViewHolder{

        TextView hasilkasir,namaitem,jumlahpesanan,tanggal;
        CardView cardkasiradmin;




        ViewHolder(View view){
            super( view);

            namaitem = (TextView)view.findViewById(R.id.kasir_namaitem);
            jumlahpesanan= (TextView) view.findViewById(R.id.kasir_jumlahpesanan);
            hasilkasir = (TextView)view.findViewById(R.id.kasir_hasil);
          //  tanggal = (TextView)view.findViewById(R.id.kasir_tanggal);

            cardkasiradmin = (CardView) view.findViewById(R.id.cardkasiradmin);





        }

    }


    @Override
    public ViewHolder onCreateViewHolder (ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_read_order, parent , false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,final int position) {



        final String namaitemjakasir = daftarDapurAdmin.get(position).getNama();
        final String jumlahpesanankasir = daftarDapurAdmin.get(position).getJumlahpesanan();
        final String hasil = daftarDapurAdmin.get(position).getHasil();
        final String tanggal = daftarDapurAdmin.get(position).getTanggal();





        holder.namaitem.setText(namaitemjakasir);
        holder.jumlahpesanan.setText(jumlahpesanankasir);
        holder.hasilkasir.setText(hasil);
     //   holder.tanggal.setText(tanggal);



    }
    @Override
    public int getItemCount() {
        /**
         * Mengembalikan jumlah item pada barang
         */
        return daftarDapurAdmin.size();
    }

}

