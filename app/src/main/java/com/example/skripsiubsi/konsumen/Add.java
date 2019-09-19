package com.example.skripsiubsi.konsumen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.skripsiubsi.R;
import com.example.skripsiubsi.model.Konsumen;
import com.example.skripsiubsi.model.Makanan;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Add extends AppCompatActivity {
    private static Activity activity;
    private EditText edtketeranganadd,edtnamaadd,edthargaadd,edtpesanan;
    private Button add,lihatharga;
    private TextView txtlihatharga,txtnamakonsumen,txtnomejakonsumen;
    private DatabaseReference database;
    String namaksmn, nomejaksmn,namakonsumen,nomejakonsumen;
    public Boolean pct = false;
    public int n_jumlah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        edtketeranganadd = (EditText) findViewById(R.id.edtketeranganadd);
        edtnamaadd = (EditText) findViewById(R.id.edtnamamakananadd);
        edthargaadd = (EditText) findViewById(R.id.edthargaadd);
        edtpesanan = (EditText) findViewById(R.id.edt_addpesanan);
        lihatharga = (Button)findViewById(R.id.btnaddlihatharga);
        add = (Button)findViewById(R.id.btnadd);

        txtnamakonsumen = (TextView) findViewById(R.id.tv_userkonsumen);
        txtnomejakonsumen = (TextView) findViewById(R.id.tv_nomejakonsumen);

        /*SharedPreferences awal = getSharedPreferences("detail",0);*/
        nomejaksmn = getIntent().getStringExtra("meja");
        namaksmn = getIntent().getStringExtra("nama");

        txtnamakonsumen.setText(namaksmn);
        txtnomejakonsumen.setText(nomejaksmn);

        lihatharga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edtpesanan.getText().toString())){
                    Snackbar.make(findViewById(R.id.btnadd), "Data Tidak Boleh Kosong", Snackbar.LENGTH_LONG).show();
                    InputMethodManager imm = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(edtpesanan.getWindowToken(), 0);

                    return;
                } else {
                    pct = true;

                    n_jumlah = Integer.parseInt(edtpesanan.getText().toString().trim());
                    final int harga = Integer.parseInt(edthargaadd.getText().toString().trim());
                    final int hasil = n_jumlah*harga;




                    txtlihatharga = (TextView) findViewById(R.id.txtaddtotal);
                    txtlihatharga.setText(String.valueOf(hasil));
                }

            }
        });





        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 if (!TextUtils.isEmpty(edtpesanan.getText().toString()) && pct == true ) {


                     String nama = edtnamaadd.getText().toString();
                     String harga = edthargaadd.getText().toString();
                     String jumlahpesanan = String.valueOf(n_jumlah);
                     String hasil = txtlihatharga.getText().toString();
                     int n_pesanan = Integer.parseInt(edtpesanan.getText().toString().trim());

                     if (n_jumlah != n_pesanan) {

                         Snackbar.make(findViewById(R.id.btnadd), "Data Berbeda", Snackbar.LENGTH_LONG).show();
                         InputMethodManager imm = (InputMethodManager)
                                 getSystemService(Context.INPUT_METHOD_SERVICE);
                         imm.hideSoftInputFromWindow(edtpesanan.getWindowToken(), 0);

                     } else {


                         submitKonsumen(new Konsumen(namaksmn, nomejaksmn, jumlahpesanan, nama, harga, hasil));

                     }

                 } else {
                    Snackbar.make(findViewById(R.id.btnadd), "Data Tidak Boleh Kosong", Snackbar.LENGTH_LONG).show();
                    InputMethodManager imm = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(edtpesanan.getWindowToken(), 0);

                }


            }
        });

        edtketeranganadd.setEnabled(false);
        edtnamaadd.setEnabled(false);
        edthargaadd.setEnabled(false);

        Makanan makanan = (Makanan) getIntent().getSerializableExtra("data");
        if (makanan != null) {
            edtketeranganadd.setText(makanan.getKeterangan());
            edtnamaadd.setText(makanan.getNama());
            edthargaadd.setText(makanan.getHarga());
        }
        database = FirebaseDatabase.getInstance().getReference().child("Daftar Sementara Cart").child("Nama Konsumen").child(nomejaksmn+namaksmn);


        
    }
    private boolean isEmpty(String s) {
        return TextUtils.isEmpty(s);
    }


    private void submitKonsumen (Konsumen konsumen) {

        database.push().setValue(konsumen).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(Add.this, "Dimasukan Kedalam Cart", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });
    }

    public static Intent getActIntent(Activity activity){
        Add.activity = activity;
        return new Intent(activity, Add.class);
    }
}
