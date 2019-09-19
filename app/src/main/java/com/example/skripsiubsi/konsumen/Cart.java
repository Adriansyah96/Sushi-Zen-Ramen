package com.example.skripsiubsi.konsumen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.skripsiubsi.MainActivity;
import com.example.skripsiubsi.R;
import com.example.skripsiubsi.adapter.AdapterCartRecyclerView;
import com.example.skripsiubsi.adapter.AdapterKonsumenRecyclerView;
import com.example.skripsiubsi.admin.LoginAdmin;
import com.example.skripsiubsi.model.Konsumen;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import info.hoang8f.widget.FButton;

public class Cart extends AppCompatActivity implements AdapterCartRecyclerView.FirebaseDataListener {
    private static Activity activity;
    private DatabaseReference databasecart;
    private RecyclerView listcart;
    private RecyclerView.Adapter adaptercart;
    private RecyclerView.LayoutManager lymanagercart;
    private ArrayList<Konsumen> daftarCartkonsumen;
    private Button pesansekarang;
    private TextView totalpesanan;
    private Integer totalBayar = 0;
    private String key;
    private SwipeRefreshLayout swipe;
    private RelativeLayout swipelayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        key = getIntent().getStringExtra("key");

        listcart = (RecyclerView) findViewById(R.id.rv_listCart);
        listcart.setHasFixedSize(true);
        lymanagercart = new LinearLayoutManager(this);
        listcart.setLayoutManager(lymanagercart);
        totalpesanan = (TextView) findViewById(R.id.totalcart);

        pesansekarang = (Button) findViewById(R.id.btnpesansekarang);

        databasecart = FirebaseDatabase.getInstance().getReference();
        swipelayout = (RelativeLayout) findViewById(R.id.relativswipe);

        swipe = (SwipeRefreshLayout) findViewById(R.id.swiprefreshmenu);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {

                        // Berhenti berputar/refreshing
                        swipe.setRefreshing(false);
                        Toast.makeText(getApplicationContext(), "Berhasil Merefresh", Toast.LENGTH_SHORT).show();
                    }
                }, 5000);
            }
        });


        pesansekarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (daftarCartkonsumen.size() != 0) {
                    setDataToPost();
                    finish();
                    Intent intent = new Intent(getApplicationContext(), Kategori.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {

                    Toast.makeText(Cart.this, "Anda Belum Memesan Apapun, Silahkan Pesan Terlebih Dahulu", Toast.LENGTH_SHORT).show();

                    finish();
                }
            }

        });


        databasecart.child("Daftar Sementara Cart").child("Nama Konsumen").child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                daftarCartkonsumen = new ArrayList<>();

                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    Konsumen konsumen = noteDataSnapshot.getValue(Konsumen.class);
                    konsumen.setKey(noteDataSnapshot.getKey());
                    daftarCartkonsumen.add(konsumen);

                    adaptercart = new AdapterCartRecyclerView(daftarCartkonsumen, Cart.this);
                    listcart.setAdapter(adaptercart);
                    getData(daftarCartkonsumen, daftarCartkonsumen.size() - 1);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                System.out.println(databaseError.getDetails() + " " + databaseError.getMessage());

            }
        });
    }

    private void setDataToPost() {
        Log.d("MASUK", "Masuk ke fungsi " + daftarCartkonsumen.size());

        for (int i = 0; i < daftarCartkonsumen.size(); i++) {

            submitPesanan(new Konsumen(daftarCartkonsumen.get(i).getNamakonsumen(), daftarCartkonsumen.get(i).getNomejakonsumen(),
                    daftarCartkonsumen.get(i).getJumlahpesanan(), daftarCartkonsumen.get(i).getNama(), daftarCartkonsumen.get(i).getHarga(),
                    daftarCartkonsumen.get(i).getHasil(), totalBayar.toString(),"Belum Selesai", "Belum Selesai"));

            if (i == daftarCartkonsumen.size() - 1) {
                /*databasecart.child("Nama Konsumen").child("Pesanan").child(key).removeValue()*/
                Intent intent = new Intent(Cart.this, Kategori.class);
                startActivity(intent);
                Toast.makeText(Cart.this, "TERIMAKASIH TELAH MEMEASAN", Toast.LENGTH_LONG).show();



            }

        }
        finish();

    }


    private void submitPesanan(Konsumen konsumen) {
        Log.d("MASUK", "Masuk ke fungsi submit ");
        databasecart.child("Daftar Konsumen").child(key).push().setValue(konsumen);


    }


    private void getData(ArrayList<Konsumen> data, int index) {
        int convert = Integer.parseInt(data.get(index).getHasil());
        totalBayar += convert;
        totalpesanan.setText("" + totalBayar);
    }

    public static Intent getActIntent(Activity activity) {
        Cart.activity = activity;
        return new Intent(activity, Cart.class);

    }


    @Override
    public void onDeleteData(Konsumen konsumen, int position) {
        if (databasecart != null) {
            databasecart.child("Daftar Sementara Cart").child("Nama Konsumen").child(key).child(konsumen.getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(Cart.this, "Berhasil Menghapus Data", Toast.LENGTH_LONG).show();
                   finish();
                   overridePendingTransition(0,0);
                   Intent restart = getIntent();
                   startActivity(restart);
                   overridePendingTransition(0,0);

                }
            });
        }
    }
}
