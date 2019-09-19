package com.example.skripsiubsi.admin.admindisert;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.skripsiubsi.R;
import com.example.skripsiubsi.admin.adminmakanan.Create;
import com.example.skripsiubsi.admin.adminmakanan.Read;

public class AdminDisert extends AppCompatActivity {

    Button tambahdatadisert,lihatdatadisert,order,lihatorder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_disert);

        tambahdatadisert = (Button) findViewById(R.id.btntambahdatadisert);

        lihatdatadisert = (Button) findViewById(R.id.btnlihatdatadisert);

        tambahdatadisert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminDisert.this, Createdisert.class);
                startActivity(intent);
            }
        });

        lihatdatadisert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminDisert.this, Readdisert.class);
                startActivity(intent);
            }
        });

    }

}
