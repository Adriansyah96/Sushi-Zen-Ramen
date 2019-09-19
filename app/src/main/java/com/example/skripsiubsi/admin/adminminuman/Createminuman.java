package com.example.skripsiubsi.admin.adminminuman;

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

public class Createminuman extends AppCompatActivity {
    public static Activity activity;
    private DatabaseReference databaseminuman;
    private Button btnsubmitminuman,btnchosefileminuman;
    private EditText namaminuman,keteranganminuman,hargaminuman;
    private Uri imgPathminuman;
    private DatabaseReference databaseRef;
    public  int gbr = 0;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createminuman);



        namaminuman = (EditText) findViewById(R.id.edtnamaminuman);
        keteranganminuman = (EditText) findViewById(R.id.edtnomejaminuman);
        hargaminuman =  (EditText) findViewById(R.id.edthargaminuman);
        btnchosefileminuman = (Button) findViewById(R.id.btn_choose_fileminuman);

        btnsubmitminuman = (Button) findViewById(R.id.btnsubmitminuman);


        databaseminuman = FirebaseDatabase.getInstance().getReference();

        btnchosefileminuman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gbr =1;
                Intent iImg = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iImg, 0);



            }
        });

        final Makanan makanan = (Makanan) getIntent().getSerializableExtra("data");

        if (makanan != null) {
            namaminuman.setText(makanan.getNama());
            keteranganminuman.setText(makanan.getKeterangan());
            hargaminuman.setText(makanan.getHarga());

            btnsubmitminuman.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    makanan.setNama(namaminuman.getText().toString());
                    makanan.setKeterangan(keteranganminuman.getText().toString());
                    makanan.setHarga(hargaminuman.getText().toString());

                    updateMakanan(makanan);
                }
            });
        }else {


            btnsubmitminuman.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (gbr == 1 && !isEmpty(namaminuman.getText().toString()) && !isEmpty(keteranganminuman.getText().toString()) && !isEmpty(hargaminuman.getText().toString()))
                        submitMakanan(new Makanan(namaminuman.getText().toString(), keteranganminuman.getText().toString(), hargaminuman.getText().toString(), "Tersedia"));
                    else
                        Snackbar.make(findViewById(R.id.btnsubmitminuman), "Data Tidak Boleh Kosong", Snackbar.LENGTH_LONG).show();
                    InputMethodManager imm = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(namaminuman.getWindowToken(), 0);
                }
            });
        }
    }



    private boolean isEmpty(String s) {
        return TextUtils.isEmpty(s);
    }

    private void  updateMakanan(Makanan makanan){


        databaseminuman.child("(Admin) Tambah Data Minuman").child(makanan.getKey()).setValue(makanan)
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Snackbar.make(findViewById(R.id.btnsubmitminuman), "Data Berhasil Di Updatekan",
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
        final String key = databaseminuman.push().getKey();
        final StorageReference storageRef = FirebaseStorage
                .getInstance()
                .getReference(key);

        storageRef.putFile(imgPathminuman)
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
                            databaseminuman.child("(Admin) Tambah Data Minuman").child(key).child("image").setValue(downloadUri.toString());


                           // Toast.makeText(Create.this, "Add Image Successfully", Toast.LENGTH_SHORT).show();

                            finish();
                        }
                    }
                });

            databaseminuman.child("(Admin) Tambah Data Minuman").child(key).setValue(makanan).addOnSuccessListener(this, new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    namaminuman.setText("");
                    keteranganminuman.setText("");
                    hargaminuman.setText("");


                    Snackbar.make(findViewById(R.id.btnsubmitminuman), "Data berhasil ditambahkan", Snackbar.LENGTH_LONG).show();
                }
            });
        }

    public static Intent getActIntent(Activity activity) {
        Createminuman.activity = activity;
        return new Intent(activity, Createminuman.class);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            imgPathminuman = data.getData();
        }
    }


}

