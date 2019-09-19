package com.example.skripsiubsi.konsumen.konsumenminuman;

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
import com.example.skripsiubsi.adapter.AdapterKonsumenMinumanRecyclerView;
import com.example.skripsiubsi.adapter.AdapterKonsumenRecyclerView;
import com.example.skripsiubsi.konsumen.ReadKonsumen;
import com.example.skripsiubsi.konsumen.konsumendisert.ReadKonsumendisert;
import com.example.skripsiubsi.model.Makanan;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ReadKonsumenminuman extends Activity {



    private static Activity activity;
    private DatabaseReference databasekonsumenminuman;
    private RecyclerView rv_viewkonsumenminuman;
    private RecyclerView.Adapter adapterkonsumenminuman;
    private RecyclerView.LayoutManager lymanagerkonsumenminuman;
    private ArrayList<Makanan> daftarMakanankonsumenminuman;
    private String nama, meja;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_konsumenminuman);

        rv_viewkonsumenminuman = (RecyclerView) findViewById(R.id.rv_mainkonsumenminuman);
        rv_viewkonsumenminuman.setHasFixedSize(true);
        nama = getIntent().getStringExtra("nama");
        meja = getIntent().getStringExtra("meja");
        lymanagerkonsumenminuman = new LinearLayoutManager(this);
        rv_viewkonsumenminuman.setLayoutManager(lymanagerkonsumenminuman);

        FloatingActionButton fab1 = (FloatingActionButton) findViewById(R.id.floatingmakanan);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ReadKonsumenminuman.this, ReadKonsumen.class);

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
        FloatingActionButton fab3 = (FloatingActionButton) findViewById(R.id.floatingdessert);
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ReadKonsumenminuman.this, ReadKonsumendisert.class);

                intent.putExtra("meja", meja);
                intent.putExtra("nama", nama);
                startActivity(intent);


                finish();
            }
        });




        databasekonsumenminuman = FirebaseDatabase.getInstance().getReference();

        databasekonsumenminuman.child("(Admin) Tambah Data Minuman").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                daftarMakanankonsumenminuman = new ArrayList<>();

                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    Makanan makanan = noteDataSnapshot.getValue(Makanan.class);
                    makanan.setKey(noteDataSnapshot.getKey());
                    daftarMakanankonsumenminuman.add(makanan);

                    adapterkonsumenminuman = new AdapterKonsumenMinumanRecyclerView(daftarMakanankonsumenminuman, ReadKonsumenminuman.this,nama,meja);
                    rv_viewkonsumenminuman.setAdapter(adapterkonsumenminuman);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                System.out.println(databaseError.getDetails() + " " + databaseError.getMessage());

            }
        });
    }

    public static Intent getActIntent(Activity activity) {
        ReadKonsumenminuman.activity = activity;
        return new Intent(activity, ReadKonsumenminuman.class);
    }
}
