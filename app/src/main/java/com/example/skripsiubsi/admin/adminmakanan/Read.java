package com.example.skripsiubsi.admin.adminmakanan;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.skripsiubsi.R;
import com.example.skripsiubsi.adapter.AdapterMakananRecyclerView;
import com.example.skripsiubsi.model.Makanan;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

public class Read extends AppCompatActivity implements AdapterMakananRecyclerView.FirebaseDataListener {


    private static Activity activity;
    private DatabaseReference database;
    private RecyclerView rv_view;
    private RecyclerView.Adapter adapter;
    private  RecyclerView.LayoutManager lymanager;
    private ArrayList<Makanan> daftarMakanan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        rv_view = (RecyclerView)findViewById(R.id.rv_main);
        rv_view.setHasFixedSize(true);
        lymanager= new LinearLayoutManager(this);
        rv_view.setLayoutManager(lymanager);

        FloatingActionButton fab = findViewById(R.id.fab);
        rv_view = findViewById(R.id.rv_main);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Create.class);
                startActivity(intent);
            }
        });





        database = FirebaseDatabase.getInstance().getReference();

        database.child("(Admin) Tambah Data").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                daftarMakanan = new ArrayList<>();

                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    Makanan makanan = noteDataSnapshot.getValue(Makanan.class);
                    makanan.setKey(noteDataSnapshot.getKey());
                    daftarMakanan.add(makanan);

                    adapter = new AdapterMakananRecyclerView(daftarMakanan, Read.this);
                    rv_view.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                System.out.println(databaseError.getDetails() + " " + databaseError.getMessage());

            }
        });
    }
    public static Intent getActIntent(Activity activity) {
        Read.activity = activity;
        return new Intent(activity, Read.class);
    }
    @Override
    public void onDeleteData(Makanan makanan, int position) {
        if (database != null) {
            database.child("(Admin) Tambah Data").child(makanan.getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(Read.this, "Berhasil Menghapus Data", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}

