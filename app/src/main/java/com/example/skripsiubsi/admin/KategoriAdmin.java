package com.example.skripsiubsi.admin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.skripsiubsi.R;
import com.example.skripsiubsi.admin.admindisert.AdminDisert;
import com.example.skripsiubsi.admin.adminmakanan.AdminMakanan;
import com.example.skripsiubsi.admin.adminminuman.AdminMinuman;
import com.example.skripsiubsi.konsumen.Kategori;

public class KategoriAdmin extends AppCompatActivity {

    private  Button order,lihatorder,adminmakanan,adminminuman,admindisert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori_admin);

        adminmakanan = (Button) findViewById(R.id.btnadminmakanan);
        adminminuman = (Button) findViewById(R.id.btnadminminuman);
        admindisert = (Button) findViewById(R.id.btnadmindisert);

        order = (Button) findViewById(R.id.btnorderadmin);
        lihatorder = (Button) findViewById(R.id.btnlihatdataorder);



        adminmakanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KategoriAdmin.this, AdminMakanan.class);
                startActivity(intent);
            }
        });
        adminminuman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KategoriAdmin.this, AdminMinuman.class);
                startActivity(intent);
            }
        });
        admindisert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KategoriAdmin.this, AdminDisert.class);
                startActivity(intent);
            }
        });



        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KategoriAdmin.this, Kategori.class);
                startActivity(intent);
                finish();

            }
        });
        lihatorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KategoriAdmin.this, Admmin_Key_Kasir.class);
                startActivity(intent);
            }
        });
    }
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Anda ingin keluar dari admin?")
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(KategoriAdmin.this, LoginAdmin.class);
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
