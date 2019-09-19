package com.example.skripsiubsi.dapur;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.skripsiubsi.MainActivity;
import com.example.skripsiubsi.R;
import com.example.skripsiubsi.admin.KategoriAdmin;
import com.example.skripsiubsi.admin.LoginAdmin;
import com.example.skripsiubsi.konsumen.Kategori;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginDapur extends AppCompatActivity {
    private EditText emaildapur, passworddapur;
    private Button logindapur;

    private FirebaseAuth mAuthdapur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_dapur);
        emaildapur = (EditText) findViewById(R.id.emaildapur);
        passworddapur = (EditText) findViewById(R.id.passworddapur);
        logindapur = (Button) findViewById(R.id.btnlogindapur);

        mAuthdapur = FirebaseAuth.getInstance();

        logindapur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validasi();
            }
        });
    }

    void validasi() {
        String email = emaildapur.getText().toString();
        String password = passworddapur.getText().toString();



        if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password)) {
            showToast("Harap isi email dan password");
        } else if (TextUtils.isEmpty(email)) {
            emaildapur.setError("isi Email");

        } else if (TextUtils.isEmpty(password)) {
            passworddapur.setError("Password Salah");
        } else {



            login(email,password);
        }


    }




    void login(final String email, String password){
        mAuthdapur.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    if (email.equals("admindapur@gmail.com")){
                        startActivity(new Intent(LoginDapur.this, Dapur.class));
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
        builder.setMessage("Anda ingin keluar dari admin dapur?")
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(LoginDapur.this, Kategori.class);
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