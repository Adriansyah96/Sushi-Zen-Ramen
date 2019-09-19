package com.example.skripsiubsi.admin.admindisert;

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
import com.example.skripsiubsi.adapter.AdapterDisertRecyclerView;
import com.example.skripsiubsi.adapter.AdapterMinumanRecyclerView;
import com.example.skripsiubsi.admin.adminminuman.Createminuman;
import com.example.skripsiubsi.model.Makanan;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Readdisert extends AppCompatActivity implements AdapterDisertRecyclerView.FirebaseDataListener {



    private static Activity activity;
    private DatabaseReference databasedisert;
    private RecyclerView rv_viewdisert;
    private RecyclerView.Adapter adapterdisert;
    private RecyclerView.LayoutManager lymanagerdisert;
    private ArrayList<Makanan> daftarMakanandisert;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_readdisert);

        rv_viewdisert = (RecyclerView) findViewById(R.id.rv_maindisert);
        rv_viewdisert.setHasFixedSize(true);
        lymanagerdisert = new LinearLayoutManager(this);
        rv_viewdisert.setLayoutManager(lymanagerdisert);

        FloatingActionButton fabdisert = findViewById(R.id.fabdisert);
        rv_viewdisert = findViewById(R.id.rv_maindisert);
        fabdisert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Createdisert.class);
                startActivity(intent);
            }
        });



        databasedisert = FirebaseDatabase.getInstance().getReference();

        databasedisert.child("(Admin) Tambah Data Disert").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                daftarMakanandisert = new ArrayList<>();

                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    Makanan makanan = noteDataSnapshot.getValue(Makanan.class);
                    makanan.setKey(noteDataSnapshot.getKey());
                    daftarMakanandisert.add(makanan);

                    adapterdisert = new AdapterDisertRecyclerView(daftarMakanandisert, Readdisert.this);
                    rv_viewdisert.setAdapter(adapterdisert);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                System.out.println(databaseError.getDetails() + " " + databaseError.getMessage());

            }
        });
    }

    public static Intent getActIntent(Activity activity) {
        Readdisert.activity = activity;
        return new Intent(activity, Readdisert.class);
    }

    @Override
    public void onDeleteData(Makanan makanan, int position) {
        if (databasedisert != null) {
            databasedisert.child("(Admin) Tambah Data Disert").child(makanan.getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(Readdisert.this, "Berhasil Menghapus Data", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
