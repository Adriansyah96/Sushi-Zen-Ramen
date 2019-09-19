package com.example.skripsiubsi.admin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.skripsiubsi.MainActivity;
import com.example.skripsiubsi.R;
import com.example.skripsiubsi.admin.adminmakanan.Read;
import com.example.skripsiubsi.konsumen.Kategori;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import info.hoang8f.widget.FButton;

public class LoginAdmin extends AppCompatActivity {
    private EditText Email, Password;
    private Button btnlogin;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);


        Email = (EditText) findViewById(R.id.email);
        Password = (EditText) findViewById(R.id.password);
        btnlogin = (Button) findViewById(R.id.btnlogin);

        mAuth = FirebaseAuth.getInstance();

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               validasi();
            }
        });

    }

    void validasi() {
        String email = Email.getText().toString();
        String password = Password.getText().toString();



        if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password)) {
            showToast("Harap isi email dan password");
        } else if (TextUtils.isEmpty(email)) {
            Email.setError("isi Email");

        } else if (TextUtils.isEmpty(password)) {
            Password.setError("Password Salah");
        } else {



            login(email,password);
        }


    }




    void login(final String email, String password){
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    if (email.equals("adriansyahpratamasofyan@gmail.com")){
                        startActivity(new Intent(LoginAdmin.this, KategoriAdmin.class));
                        finish();
                    }else {
                        showToast("GAGAL LOGIN ");
                    }

                }else
                    showToast("GAGAL LOGIN ");
            }
        });

    }
    void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }




    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Anda ingin keluar dari admin?")
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(LoginAdmin.this, Kategori.class);
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