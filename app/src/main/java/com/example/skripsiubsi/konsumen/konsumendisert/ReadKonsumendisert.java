package com.example.skripsiubsi.konsumen.konsumendisert;

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
import com.example.skripsiubsi.adapter.AdapterKonsumenDisertRecyclerView;
import com.example.skripsiubsi.adapter.AdapterKonsumenMinumanRecyclerView;
import com.example.skripsiubsi.konsumen.ReadKonsumen;
import com.example.skripsiubsi.konsumen.konsumenminuman.ReadKonsumenminuman;
import com.example.skripsiubsi.model.Makanan;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ReadKonsumendisert extends Activity {



    private static Activity activity;
    private DatabaseReference databasekonsumendisert;
    private RecyclerView rv_viewkonsumendisert;
    private RecyclerView.Adapter adapterkonsumendisert;
    private RecyclerView.LayoutManager lymanagerkonsumendisert;
    private ArrayList<Makanan> daftarMakanankonsumendisert;
    private String nama, meja;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_konsumendisert);

        rv_viewkonsumendisert = (RecyclerView) findViewById(R.id.rv_mainkonsumendisert);
        rv_viewkonsumendisert.setHasFixedSize(true);
        nama = getIntent().getStringExtra("nama");
        meja = getIntent().getStringExtra("meja");
        lymanagerkonsumendisert = new LinearLayoutManager(this);
        rv_viewkonsumendisert.setLayoutManager(lymanagerkonsumendisert);

        FloatingActionButton fab1 = (FloatingActionButton) findViewById(R.id.floatingminuman);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(ReadKonsumendisert.this, ReadKonsumenminuman.class);

                intent.putExtra("meja", meja);
                intent.putExtra("nama", nama);
                startActivity(intent);


                finish();
            }
        });


        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.floatinghome);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(), "Masuk Ke Home", Toast.LENGTH_SHORT).show();

                finish();
            }
        });
        FloatingActionButton fab3 = (FloatingActionButton) findViewById(R.id.floatingmakanan);
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(ReadKonsumendisert.this, ReadKonsumen.class);

                intent.putExtra("meja", meja);
                intent.putExtra("nama", nama);
                startActivity(intent);


                finish();
            }
        });

        databasekonsumendisert = FirebaseDatabase.getInstance().getReference();

        databasekonsumendisert.child("(Admin) Tambah Data Disert").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                daftarMakanankonsumendisert = new ArrayList<>();

                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    Makanan makanan = noteDataSnapshot.getValue(Makanan.class);
                    makanan.setKey(noteDataSnapshot.getKey());
                    daftarMakanankonsumendisert.add(makanan);

                    adapterkonsumendisert = new AdapterKonsumenDisertRecyclerView(daftarMakanankonsumendisert, ReadKonsumendisert.this,nama,meja);
                    rv_viewkonsumendisert.setAdapter(adapterkonsumendisert);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                System.out.println(databaseError.getDetails() + " " + databaseError.getMessage());

            }
        });
    }

    public static Intent getActIntent(Activity activity) {
        ReadKonsumendisert.activity = activity;
        return new Intent(activity, ReadKonsumendisert.class);
    }
}
