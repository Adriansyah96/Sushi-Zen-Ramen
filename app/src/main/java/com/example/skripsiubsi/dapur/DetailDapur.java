package com.example.skripsiubsi.dapur;

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
import com.example.skripsiubsi.adapter.AdapterDetailDapurRecyclerView;
import com.example.skripsiubsi.adapter.AdapterKonsumenRecyclerView;
import com.example.skripsiubsi.model.Konsumen;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import info.hoang8f.widget.FButton;

public class DetailDapur extends AppCompatActivity {
    private static Activity activity;
    private DatabaseReference databasedetaildapur;
    private RecyclerView listdetaildapur;
    private RecyclerView.Adapter adapterdetaildapur;
    private RecyclerView.LayoutManager lymanagerdetaildapur;
    private ArrayList<Konsumen> daftardetaildapur;
    private String key;
    private String namaksmn;
    private String nomejaksmn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_dapur);

        key = getIntent().getStringExtra("key");




        listdetaildapur = (RecyclerView) findViewById(R.id.rv_listdetaildapur);
        listdetaildapur.setHasFixedSize(true);
        lymanagerdetaildapur = new LinearLayoutManager(this);
        listdetaildapur.setLayoutManager(lymanagerdetaildapur);





        databasedetaildapur = FirebaseDatabase.getInstance().getReference();






        databasedetaildapur.child("Daftar Sementara Cart").child("Nama Konsumen").child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                daftardetaildapur = new ArrayList<>();

                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    Konsumen konsumen = noteDataSnapshot.getValue(Konsumen.class);
                    konsumen.setKey(noteDataSnapshot.getKey());
                    daftardetaildapur.add(konsumen);

                    adapterdetaildapur = new AdapterDetailDapurRecyclerView(daftardetaildapur, DetailDapur.this);
                    listdetaildapur.setAdapter(adapterdetaildapur);

                     }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                System.out.println(databaseError.getDetails() + " " + databaseError.getMessage());

            }
        });
    }



    public static Intent getActIntent(Activity activity) {
        DetailDapur.activity = activity;
        return new Intent(activity, DetailDapur.class);
    }


}
