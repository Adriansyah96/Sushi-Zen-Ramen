package com.example.skripsiubsi.admin.adminmakanan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.skripsiubsi.R;
import com.example.skripsiubsi.admin.ReadOrder;

public class AdminMakanan extends AppCompatActivity {

    Button tambahdata,lihatdata,order,lihatorder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_makanan);

        tambahdata = (Button) findViewById(R.id.btntambahdata);

        lihatdata = (Button) findViewById(R.id.btnlihatdata);

        tambahdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMakanan.this, Create.class);
                startActivity(intent);
            }
        });

        lihatdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMakanan.this, Read.class);
                startActivity(intent);
            }
        });

    }
}
