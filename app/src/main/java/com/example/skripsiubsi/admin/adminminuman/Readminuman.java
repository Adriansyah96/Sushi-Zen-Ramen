package com.example.skripsiubsi.admin.adminminuman;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.skripsiubsi.R;
import com.example.skripsiubsi.adapter.AdapterMakananRecyclerView;
import com.example.skripsiubsi.adapter.AdapterMinumanRecyclerView;
import com.example.skripsiubsi.admin.adminmakanan.Create;
import com.example.skripsiubsi.admin.adminmakanan.Read;
import com.example.skripsiubsi.model.Makanan;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Readminuman extends AppCompatActivity implements AdapterMinumanRecyclerView.FirebaseDataListener {



    private static Activity activity;
    private DatabaseReference databaseminuman;
    private RecyclerView rv_viewminuman;
    private RecyclerView.Adapter adapterminuman;
    private RecyclerView.LayoutManager lymanagerminuman;
    private ArrayList<Makanan> daftarMakananminuman;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_readminuman);

        rv_viewminuman = (RecyclerView) findViewById(R.id.rv_mainminuman);
        rv_viewminuman.setHasFixedSize(true);
        lymanagerminuman = new LinearLayoutManager(this);
        rv_viewminuman.setLayoutManager(lymanagerminuman);

        FloatingActionButton fabminuman = findViewById(R.id.fabminuman);
        rv_viewminuman = findViewById(R.id.rv_mainminuman);
        fabminuman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Createminuman.class);
                startActivity(intent);
            }
        });



        databaseminuman = FirebaseDatabase.getInstance().getReference();

        databaseminuman.child("(Admin) Tambah Data Minuman").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                daftarMakananminuman = new ArrayList<>();

                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    Makanan makanan = noteDataSnapshot.getValue(Makanan.class);
                    makanan.setKey(noteDataSnapshot.getKey());
                    daftarMakananminuman.add(makanan);

                    adapterminuman = new AdapterMinumanRecyclerView(daftarMakananminuman, Readminuman.this);
                    rv_viewminuman.setAdapter(adapterminuman);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                System.out.println(databaseError.getDetails() + " " + databaseError.getMessage());

            }
        });
    }

    public static Intent getActIntent(Activity activity) {
        Readminuman.activity = activity;
        return new Intent(activity, Readminuman.class);
    }

    @Override
    public void onDeleteData(Makanan makanan, int position) {
        if (databaseminuman != null) {
            databaseminuman.child("(Admin) Tambah Data Minuman").child(makanan.getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(Readminuman.this, "Berhasil Menghapus Data", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
