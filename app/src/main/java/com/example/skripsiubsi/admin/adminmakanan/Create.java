package com.example.skripsiubsi.admin.adminmakanan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.skripsiubsi.R;
import com.example.skripsiubsi.model.Makanan;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Create extends AppCompatActivity {
    public static Activity activity;
    private DatabaseReference database;
    private Button btnsubmit,btnchosefile;
    private EditText nama,keterangan,harga;
    private Uri imgPath;
    private DatabaseReference databaseRef;
    public int gbr = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);


        nama = (EditText) findViewById(R.id.edtnamamakanan);
        keterangan = (EditText) findViewById(R.id.edtnomeja);
        harga =  (EditText) findViewById(R.id.edtharga);
        btnchosefile = (Button) findViewById(R.id.btn_choose_file);

        btnsubmit = (Button) findViewById(R.id.btnsubmit);


        database = FirebaseDatabase.getInstance().getReference();

        btnchosefile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gbr = 1;
                Intent iImg = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iImg, 0);




            }
        });

        final Makanan makanan = (Makanan) getIntent().getSerializableExtra("data");

        // update makanan

        if (makanan != null) {
            nama.setText(makanan.getNama());
            keterangan.setText(makanan.getKeterangan());
            harga.setText(makanan.getHarga());

            btnsubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    makanan.setNama(nama.getText().toString());
                    makanan.setKeterangan(keterangan.getText().toString());
                    makanan.setHarga(harga.getText().toString());

                    updateMakanan(makanan);
                }
            });
        }else {


            btnsubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (gbr==1 && !isEmpty(nama.getText().toString()) && !isEmpty(keterangan.getText().toString()) && !isEmpty(harga.getText().toString())) {
                        submitMakanan(new Makanan(nama.getText().toString(), keterangan.getText().toString(), harga.getText().toString(), "Tersedia"));
                } else {
                        Snackbar.make(findViewById(R.id.btnsubmit), "Data Tidak Boleh Kosong", Snackbar.LENGTH_LONG).show();
                        InputMethodManager imm = (InputMethodManager)
                                getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(nama.getWindowToken(), 0);
                    }
                }
            });
        }
    }



    private boolean isEmpty(String s) {
        return TextUtils.isEmpty(s);
    }

    private void  updateMakanan(Makanan makanan){


        database.child("(Admin) Tambah Data").child(makanan.getKey()).setValue(makanan)
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Snackbar.make(findViewById(R.id.btnsubmit), "Data Berhasil Di Updatekan",
                                Snackbar.LENGTH_LONG).setAction("Oke", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish();
                            }
                        }).show();
                    }
                });

    }

    private void submitMakanan (final Makanan makanan) {
        final String key = database.push().getKey();
        final StorageReference storageRef = FirebaseStorage
                .getInstance()
                .getReference(key);

        storageRef.putFile(imgPath)
                .continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        return storageRef.getDownloadUrl();

                    }
                })
                .addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();
                            database.child("(Admin) Tambah Data").child(key).child("image").setValue(downloadUri.toString());


                           // Toast.makeText(Create.this, "Add Image Successfully", Toast.LENGTH_SHORT).show();

                            finish();
                        }
                    }
                });

            database.child("(Admin) Tambah Data").child(key).setValue(makanan).addOnSuccessListener(this, new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    nama.setText("");
                    keterangan.setText("");
                    harga.setText("");


                    Snackbar.make(findViewById(R.id.btnsubmit), "Data berhasil ditambahkan", Snackbar.LENGTH_LONG).show();
                }
            });
        }

    public static Intent getActIntent(Activity activity) {
        Create.activity = activity;
        return new Intent(activity, Create.class);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            imgPath = data.getData();

        }
    }


}

