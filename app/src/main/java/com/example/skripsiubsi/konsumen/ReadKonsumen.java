package com.example.skripsiubsi.konsumen;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.skripsiubsi.R;
import com.example.skripsiubsi.adapter.AdapterKonsumenRecyclerView;
import com.example.skripsiubsi.konsumen.ReadKonsumen;
import com.example.skripsiubsi.konsumen.konsumendisert.ReadKonsumendisert;
import com.example.skripsiubsi.konsumen.konsumenminuman.ReadKonsumenminuman;
import com.example.skripsiubsi.model.Makanan;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ReadKonsumen extends Activity {



    private static Activity activity;
    private DatabaseReference databasekonsumen;
    private RecyclerView rv_viewkonsumen;
    private RecyclerView.Adapter adapterkonsumen;
    private RecyclerView.LayoutManager lymanagerkonsumen;
    private ArrayList<Makanan> daftarMakanankonsumen;
    private String nama, meja;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_konsumen);
        nama = getIntent().getStringExtra("nama");
        meja = getIntent().getStringExtra("meja");
        rv_viewkonsumen = (RecyclerView) findViewById(R.id.rv_mainkonsumen);
        rv_viewkonsumen.setHasFixedSize(true);
        lymanagerkonsumen = new LinearLayoutManager(this);
        rv_viewkonsumen.setLayoutManager(lymanagerkonsumen);

        FloatingActionButton fab1 = (FloatingActionButton) findViewById(R.id.floatingminuman);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(ReadKonsumen.this, ReadKonsumenminuman.class);

                intent.putExtra("meja", meja);
                intent.putExtra("nama", nama);
                startActivity(intent);


                finish();
            }
        });

        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.floatingdessert);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(ReadKonsumen.this, ReadKonsumendisert.class);

                intent.putExtra("meja", meja);
                intent.putExtra("nama", nama);
                startActivity(intent);


                finish();
            }
        });
        FloatingActionButton fab3 = (FloatingActionButton) findViewById(R.id.floatinghome);
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(), "Masuk Ke Home", Toast.LENGTH_SHORT).show();

                finish();
            }
        });




        databasekonsumen = FirebaseDatabase.getInstance().getReference();

        databasekonsumen.child("(Admin) Tambah Data").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                daftarMakanankonsumen = new ArrayList<>();

                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    Makanan makanan = noteDataSnapshot.getValue(Makanan.class);
                    makanan.setKey(noteDataSnapshot.getKey());
                    daftarMakanankonsumen.add(makanan);

                    adapterkonsumen = new AdapterKonsumenRecyclerView(daftarMakanankonsumen, ReadKonsumen.this, nama, meja);
                    rv_viewkonsumen.setAdapter(adapterkonsumen);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                System.out.println(databaseError.getDetails() + " " + databaseError.getMessage());


            }
        });
    }

    public static Intent getActIntent(Activity activity) {
        ReadKonsumen.activity = activity;
        return new Intent(activity, ReadKonsumen.class);

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
