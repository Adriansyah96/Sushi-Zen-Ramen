package com.example.skripsiubsi.admin;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.skripsiubsi.R;
import com.example.skripsiubsi.adapter.AdapterKeyAdminrRecyclerView;
import com.example.skripsiubsi.model.Konsumen;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Admmin_Key_Kasir extends AppCompatActivity implements AdapterKeyAdminrRecyclerView.FirebaseDataListener {
    private static Activity activity;
    private DatabaseReference databasekasirkeyadmin;
    private RecyclerView listkeykasiradmin;
    private RecyclerView.Adapter adapterderkeykasiraadmin;
    private RecyclerView.LayoutManager lymanagerkeykasiradmin;
    private ArrayList<Konsumen> daftarkeykasiradmin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admmin__key__kasir);
        listkeykasiradmin = (RecyclerView) findViewById(R.id.rv_list_key_admin);
        listkeykasiradmin.setHasFixedSize(true);
        lymanagerkeykasiradmin = new LinearLayoutManager(this);
        listkeykasiradmin.setLayoutManager(lymanagerkeykasiradmin);





        databasekasirkeyadmin = FirebaseDatabase.getInstance().getReference();






        databasekasirkeyadmin.child("Daftar Konsumen").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                daftarkeykasiradmin = new ArrayList<>();

                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    Konsumen konsumen = noteDataSnapshot.getValue(Konsumen.class);
                    konsumen.setKey(noteDataSnapshot.getKey());
                    daftarkeykasiradmin.add(konsumen);

                    adapterderkeykasiraadmin = new AdapterKeyAdminrRecyclerView(daftarkeykasiradmin, Admmin_Key_Kasir.this);
                    listkeykasiradmin.setAdapter(adapterderkeykasiraadmin);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                System.out.println(databaseError.getDetails() + " " + databaseError.getMessage());

            }
        });
    }



    public static Intent getActIntent(Activity activity) {
        Admmin_Key_Kasir.activity = activity;
        return new Intent(activity, Admmin_Key_Kasir.class);
    }
    @Override
    public void onDeleteData(Konsumen konsumen, int position) {
        if (databasekasirkeyadmin != null) {
            databasekasirkeyadmin.child("Daftar Konsumen").child(konsumen.getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(Admmin_Key_Kasir.this, "Berhasil Menghapus Data", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}


