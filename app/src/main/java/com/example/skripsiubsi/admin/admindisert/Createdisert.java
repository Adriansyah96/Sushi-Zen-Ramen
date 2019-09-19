package com.example.skripsiubsi.admin.admindisert;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
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

public class Createdisert extends AppCompatActivity {
    public static Activity activity;
    private DatabaseReference databasedisert;
    private Button btnsubmitdisert,btnchosefiledisert;
    private EditText namadisert,keterangandisert,hargadisert;
    private Uri imgPathdisert;
    private DatabaseReference databaseRef;
    public  String gbr="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createdisert);


        namadisert = (EditText) findViewById(R.id.edtnamamakanandisert);
        keterangandisert = (EditText) findViewById(R.id.edtnomejadisert);
        hargadisert =  (EditText) findViewById(R.id.edthargadisert);
        btnchosefiledisert = (Button) findViewById(R.id.btn_choose_filedisert);

        btnsubmitdisert = (Button) findViewById(R.id.btnsubmitdisert);


        databasedisert = FirebaseDatabase.getInstance().getReference();

        btnchosefiledisert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gbr = "kholis";
                Intent iImg = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iImg, 0);



            }
        });

        final Makanan makanan = (Makanan) getIntent().getSerializableExtra("data");

        if (makanan != null) {
            namadisert.setText(makanan.getNama());
            keterangandisert.setText(makanan.getKeterangan());
            hargadisert.setText(makanan.getHarga());

            btnsubmitdisert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    makanan.setNama(namadisert.getText().toString());
                    makanan.setKeterangan(keterangandisert.getText().toString());
                    makanan.setHarga(hargadisert.getText().toString());

                    updateMakanan(makanan);
                }
            });
        }else {


            btnsubmitdisert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (gbr.equalsIgnoreCase("kholis") && !isEmpty(namadisert.getText().toString()) && !isEmpty(keterangandisert.getText().toString()) && !isEmpty(hargadisert.getText().toString()))
                        submitMakanan(new Makanan(namadisert.getText().toString(), keterangandisert.getText().toString(), hargadisert.getText().toString(), "Tersedia"));
                    else
                        Snackbar.make(findViewById(R.id.btnsubmitdisert), "Data Tidak Boleh Kosong", Snackbar.LENGTH_LONG).show();
                    InputMethodManager imm = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(namadisert.getWindowToken(), 0);
                }
            });
        }
    }



    private boolean isEmpty(String s) {
        return TextUtils.isEmpty(s);
    }

    private void  updateMakanan(Makanan makanan){


        databasedisert.child("(Admin) Tambah Data Disert").child(makanan.getKey()).setValue(makanan)
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Snackbar.make(findViewById(R.id.btnsubmitdisert), "Data Berhasil Di Updatekan",
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
        final String key = databasedisert.push().getKey();
        final StorageReference storageRef = FirebaseStorage
                .getInstance()
                .getReference(key);

        storageRef.putFile(imgPathdisert)
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
                            databasedisert.child("(Admin) Tambah Data Disert").child(key).child("image").setValue(downloadUri.toString());


                           // Toast.makeText(Create.this, "Add Image Successfully", Toast.LENGTH_SHORT).show();

                            finish();
                        }
                    }
                });

            databasedisert.child("(Admin) Tambah Data Disert").child(key).setValue(makanan).addOnSuccessListener(this, new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    namadisert.setText("");
                    keterangandisert.setText("");
                    hargadisert.setText("");


                    Snackbar.make(findViewById(R.id.btnsubmitdisert), "Data berhasil ditambahkan", Snackbar.LENGTH_LONG).show();
                }
            });
        }

    public static Intent getActIntent(Activity activity) {
        Createdisert.activity = activity;
        return new Intent(activity, Createdisert.class);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            imgPathdisert = data.getData();
        }
    }


}

