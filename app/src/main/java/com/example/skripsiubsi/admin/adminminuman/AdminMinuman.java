package com.example.skripsiubsi.admin.adminminuman;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.skripsiubsi.R;
import com.example.skripsiubsi.admin.adminmakanan.Create;
import com.example.skripsiubsi.admin.adminmakanan.Read;

public class AdminMinuman extends AppCompatActivity {

    Button tambahdataminuman,lihatdataminuman;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_minuman);

        tambahdataminuman = (Button) findViewById(R.id.btntambahdataminuman);

        lihatdataminuman = (Button) findViewById(R.id.btnlihatdataminuman);

        tambahdataminuman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMinuman.this, Createminuman.class);
                startActivity(intent);
            }
        });

        lihatdataminuman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMinuman.this, Readminuman.class);
                startActivity(intent);
            }
        });

    }
}
