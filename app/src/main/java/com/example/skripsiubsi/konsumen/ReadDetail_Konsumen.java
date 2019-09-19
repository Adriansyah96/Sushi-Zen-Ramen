package com.example.skripsiubsi.konsumen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.skripsiubsi.R;
import com.example.skripsiubsi.model.Makanan;

public class ReadDetail_Konsumen extends AppCompatActivity {

    private static Activity activity;
    private Button btSubmit;
    private Button chose;
    private EditText edtnomeja;
    private EditText edtnama;
    private EditText edtHarga;
    private TextView txtadm;
    private ImageView imgadm;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        txtadm = (TextView) findViewById(R.id.txtadm);
        imgadm = (ImageView) findViewById(R.id.img_adm);
        edtnomeja = (EditText) findViewById(R.id.edtnomeja);
        edtnama = (EditText) findViewById(R.id.edtnamamakanan);
        edtHarga = (EditText) findViewById(R.id.edtharga);
        btSubmit = (Button) findViewById(R.id.btnsubmit);
        chose = (Button) findViewById(R.id.btn_choose_file);



        edtnomeja.setEnabled(false);
        edtnama.setEnabled(false);
        edtHarga.setEnabled(false);

        txtadm.setVisibility(View.GONE);
        imgadm.setVisibility(View.VISIBLE);
        btSubmit.setVisibility(View.GONE);
        chose.setVisibility(View.GONE);

        Makanan makanan = (Makanan) getIntent().getSerializableExtra("data");
        if (makanan != null) {
            edtnomeja.setText(makanan.getKeterangan());
            edtnama.setText(makanan.getNama());
            edtHarga.setText(makanan.getHarga());

        }
    }

    public static Intent getActIntent(Activity activity){
        ReadDetail_Konsumen.activity = activity;
        return new Intent(activity, ReadDetail_Konsumen.class);
    }
}
