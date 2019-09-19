package com.example.skripsiubsi.dapur;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.example.skripsiubsi.MainActivity;
import com.example.skripsiubsi.R;
import com.example.skripsiubsi.adapter.AdapterCartRecyclerView;
import com.example.skripsiubsi.adapter.AdapterDapurRecyclerView;
import com.example.skripsiubsi.adapter.AdapterKonsumenRecyclerView;
import com.example.skripsiubsi.model.Konsumen;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import info.hoang8f.widget.FButton;

public class Dapur extends AppCompatActivity implements AdapterDapurRecyclerView.FirebaseDataListener {
    private static Activity activity;
    private DatabaseReference databasedapur;
    private RecyclerView listdapur;
    private RecyclerView.Adapter adapterdapur;
    private RecyclerView.LayoutManager lymanagerdapur;
    private ArrayList<Konsumen> daftardapur;

    private String key;
    public String tgl, weekDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dapur);

        key = getIntent().getStringExtra("key");

        listdapur = (RecyclerView) findViewById(R.id.rv_listdapur);
        listdapur.setHasFixedSize(true);
        lymanagerdapur = new LinearLayoutManager(this);
        listdapur.setLayoutManager(lymanagerdapur);

        // hari dan tanggal
        Calendar c = Calendar.getInstance();

        Date c_time = c.getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        tgl = df.format(c_time);

        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

        weekDay = "";
        if (Calendar.MONDAY == dayOfWeek) weekDay = "Senin";
        else if (Calendar.TUESDAY == dayOfWeek) weekDay = "Selasa";
        else if (Calendar.WEDNESDAY == dayOfWeek) weekDay = "Rabu";
        else if (Calendar.THURSDAY == dayOfWeek) weekDay = "Kamis";
        else if (Calendar.FRIDAY == dayOfWeek) weekDay = "Jumat";
        else if (Calendar.SATURDAY == dayOfWeek) weekDay = "Sabtu";
        else if (Calendar.SUNDAY == dayOfWeek) weekDay = "Minggu";




        databasedapur = FirebaseDatabase.getInstance().getReference();




        databasedapur.child("Daftar Sementara Cart").child("Nama Konsumen").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                daftardapur = new ArrayList<>();

                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    Konsumen konsumen = noteDataSnapshot.getValue(Konsumen.class);
                    konsumen.setKey(noteDataSnapshot.getKey());
                    daftardapur.add(konsumen);

                    adapterdapur = new AdapterDapurRecyclerView(daftardapur, Dapur.this);
                    listdapur.setAdapter(adapterdapur);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                System.out.println(databaseError.getDetails() + " " + databaseError.getMessage());

            }
        });
    }


    public static Intent getActIntent(Activity activity) {
        Dapur.activity = activity;
        return new Intent(activity, Dapur.class);
    }


    @Override
    public void onDeleteData(final Konsumen konsumen, int position) {

        if (databasedapur != null) {
            databasedapur.child("Daftar Sementara Cart").child("Nama Konsumen").child(konsumen.getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(Dapur.this, "Pesanan Terselesaikan", Toast.LENGTH_LONG).show();
                }
            });
            databasedapur.child("Daftar Konsumen").child(konsumen.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()){
                        String key = ds.getKey();
                        databasedapur.child("Daftar Konsumen").child(konsumen.getKey()).child(key).child("tanggal").setValue(weekDay+", "+tgl);
                        databasedapur.child("Daftar Konsumen").child(konsumen.getKey()).child(key).child("status").setValue("selesai");


                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

    }

    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Anda ingin keluar dari admin dapur?")
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(Dapur.this, LoginDapur.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


}

