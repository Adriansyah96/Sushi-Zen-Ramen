package com.example.skripsiubsi.admin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.skripsiubsi.R;
import com.example.skripsiubsi.adapter.AdapterCartRecyclerView;
import com.example.skripsiubsi.adapter.AdapterDapurAdminRecyclerView;
import com.example.skripsiubsi.adapter.AdapterKonsumenRecyclerView;
import com.example.skripsiubsi.model.Konsumen;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ReadOrder extends AppCompatActivity {
    private static Activity activity;
    private DatabaseReference databasekasiradmin;
    private RecyclerView listkasiradmin;
    private RecyclerView.Adapter adapterderkasiraadmin;
    private RecyclerView.LayoutManager lymanagerkasiradmin;
    private ArrayList<Konsumen> daftarkasiradmin;
    private String key;
    private String keystatus;
    public TextView nama, no, bayar, tanggal;
    public Button print;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kasiradmin);


        key = getIntent().getStringExtra("key");
        keystatus = getIntent().getStringExtra("keystatus");




        nama = findViewById(R.id.txtnamakonsumen);
        bayar = findViewById(R.id.txthasil_total);
        no = findViewById(R.id.txtnomermeja);
        print = findViewById(R.id.btnprint);
        tanggal = findViewById(R.id.txttanggalpesan);

        print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(ReadOrder.this, "Maaf, Print Out Belum Tersedia", Toast.LENGTH_SHORT).show();

            }
        });




        listkasiradmin = (RecyclerView) findViewById(R.id.rv_listkasiradmin);
        listkasiradmin.setHasFixedSize(true);
        lymanagerkasiradmin = new LinearLayoutManager(this);
        listkasiradmin.setLayoutManager(lymanagerkasiradmin);





        databasekasiradmin = FirebaseDatabase.getInstance().getReference();


        databasekasiradmin.child("Daftar Konsumen").child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                daftarkasiradmin = new ArrayList<>();

                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    Konsumen konsumen = noteDataSnapshot.getValue(Konsumen.class);
                    konsumen.setKey(noteDataSnapshot.getKey());
                    daftarkasiradmin.add(konsumen);

                    nama.setText(konsumen.getNamakonsumen());
                    no.setText(konsumen.getNomejakonsumen());
                    bayar.setText(konsumen.getTotalbayar());
                    tanggal.setText(konsumen.getTanggal());

                    adapterderkasiraadmin = new AdapterDapurAdminRecyclerView(daftarkasiradmin, ReadOrder.this);
                    listkasiradmin.setAdapter(adapterderkasiraadmin);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                System.out.println(databaseError.getDetails() + " " + databaseError.getMessage());

            }
        });
    }



    public static Intent getActIntent(Activity activity) {
        ReadOrder.activity = activity;
        return new Intent(activity, ReadOrder.class);
    }
}
