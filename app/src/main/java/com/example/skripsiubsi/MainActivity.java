package com.example.skripsiubsi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;


import com.example.skripsiubsi.admin.KategoriAdmin;
import com.example.skripsiubsi.admin.LoginAdmin;
import com.example.skripsiubsi.admin.adminmakanan.AdminMakanan;
import com.example.skripsiubsi.dapur.Dapur;
import com.example.skripsiubsi.dapur.LoginDapur;
import com.example.skripsiubsi.konsumen.Kategori;


public class MainActivity extends Activity {


    ImageView admin,dapur,konsumen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        admin = (ImageView) findViewById(R.id.btnadmin);
        konsumen= (ImageView) findViewById(R.id.btnkonsumen);
        dapur= (ImageView) findViewById(R.id.btndapur);


        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginAdmin.class);
                startActivity(intent);
                finish();
            }
        });

        konsumen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Kategori.class);
                startActivity(intent);
                finish();
            }
        });
        dapur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginDapur.class);
                startActivity(intent);
                finish();

            }
        });

    }
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
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
