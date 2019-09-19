package com.example.skripsiubsi.konsumen;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.skripsiubsi.MainActivity;
import com.example.skripsiubsi.R;
import com.example.skripsiubsi.admin.LoginAdmin;
import com.example.skripsiubsi.dapur.LoginDapur;
import com.example.skripsiubsi.konsumen.konsumendisert.ReadKonsumendisert;
import com.example.skripsiubsi.konsumen.konsumenminuman.ReadKonsumenminuman;
import com.example.skripsiubsi.model.Konsumen;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.text.TextUtils.isEmpty;

public class Kategori extends AppCompatActivity {
    Animation plat;
    LinearLayout linier;
    RelativeLayout Dalam;
    private Button btnsimpanvisibility;
    private ImageView btnkategorymakanan,btnkategoryminuman,btnkategorydisert,help,option;
    private EditText Nomejakategori,Namakonsumen;
    private TextView key;
    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = mDatabase.getReference();
    ViewFlipper v_flipper;
    String jumlahpesanan,nama,harga,hasil,meja,namakonsumen;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori);

        key = findViewById(R.id.txtKey);

        Nomejakategori = (EditText)findViewById(R.id.edtnomejavisibility);
        Namakonsumen = (EditText)findViewById(R.id.edtnamakonsumenvisibility);

        btnsimpanvisibility = (Button) findViewById(R.id.btnsimpanvisibility);
        btnkategorymakanan = (ImageView) findViewById(R.id.btnkategorymakanan);
        btnkategoryminuman = (ImageView) findViewById(R.id.btnkategoryminuman);
        btnkategorydisert = (ImageView) findViewById(R.id.btnkategorydisert);
        help = (ImageView) findViewById(R.id.help);
        option = (ImageView) findViewById(R.id.option);
        linier = findViewById(R.id.linier);
        Dalam = findViewById(R.id.dalam);

        plat = AnimationUtils.loadAnimation(this, R.anim.plat);

        help.startAnimation(plat);




        //  Glide.with(Kategori.this)
                // LOAD URL DARI LOKAL DRAWABLE
      //          .load(R.drawable.helpgif).asGif()
                //PENGATURAN CACHE
       //         .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
       //         .into(help);

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Kategori.this,Help.class);
                startActivity(intent);
            }
        });

        option.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final Dialog dialog = new Dialog(Kategori.this);
                dialog.setContentView(R.layout.dialog_view_option);
                dialog.setTitle("Silahkan Pilih Aksi");
                dialog.show();

                Button admin = dialog.findViewById(R.id.bt_admin);
                Button admindapur = dialog.findViewById(R.id.bt_admindapur);




                admin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Kategori.this, LoginAdmin.class);
                        startActivity(intent);
                        finish();
                    }
                });

                admindapur.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Kategori.this, LoginDapur.class);
                        startActivity(intent);
                        finish();
                    }
                });

                return true;
            }
        });


        btnsimpanvisibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnkategorymakanan.setVisibility(View.VISIBLE);
                btnkategoryminuman.setVisibility(View.VISIBLE);
                btnkategorydisert.setVisibility(View.VISIBLE);
                linier.setVisibility(View.INVISIBLE);


               meja = Nomejakategori.getText().toString();
               namakonsumen = Namakonsumen.getText().toString();

               key.setText(meja+namakonsumen);

                SharedPreferences awal = getSharedPreferences("detail",0);
                SharedPreferences.Editor editor = awal.edit();
                editor.putString("meja",meja);
                editor.putString("nama",namakonsumen);
                editor.commit();

                if (isEmpty(Nomejakategori.getText().toString()) && isEmpty(Namakonsumen.getText().toString())) {
                        Intent intent = new Intent(Kategori.this, Kategori.class);
                        startActivity(intent);
                        Toast.makeText(Kategori.this, "Harap Isi Dengan Lengkap", Toast.LENGTH_SHORT).show();
                        finish();
                    }else if (isEmpty(Nomejakategori.getText().toString())) {
                    Intent intent = new Intent(Kategori.this, Kategori.class);
                    startActivity(intent);
                    Toast.makeText(Kategori.this, "Harap Isi No Meja ", Toast.LENGTH_SHORT).show();
                    finish();
                    }else if (isEmpty(Namakonsumen.getText().toString())) {
                    Intent intent = new Intent(Kategori.this, Kategori.class);
                    startActivity(intent);
                    Toast.makeText(Kategori.this, "Harap Isi Nama Pemesan", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    Dalam.setVisibility(View.VISIBLE);
                    linier.setVisibility(View.INVISIBLE);


                    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
                    fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Intent carIntent = new Intent(Kategori.this,Cart.class);
                            carIntent.putExtra("key", key.getText().toString());
                            startActivity(carIntent);
                        }
                    });



                    Snackbar.make(findViewById(R.id.btnsimpanvisibility), "Berhasil Mengisi Data, Terimakasih", Snackbar.LENGTH_LONG).show();


                }

            }
        });





        int images[] = {R.drawable.logosushizen,R.drawable.pelayanslide, R.drawable.sushidisplay7, R.drawable.sushidisplay8,R.drawable.sushislide9,
                R.drawable.sushidisplay4, R.drawable.sushidisplay6,R.drawable.sushidisplay1,R.drawable.lemonteaslide,R.drawable.jusbuahslide,R.drawable.milk, R.drawable.nanas,R.drawable.jusjerukslide,
                R.drawable.eskrimslide,R.drawable.koppiiislide, R.drawable.milkshakedisplay,R.drawable.pudingslide,R.drawable.dessert1,R.drawable.dessert2,R.drawable.letsorderslide,R.drawable.thankyouslide};
        v_flipper = findViewById(R.id.v_flipper);


        for (int i = 0; i < images.length; i++) {
            fliverImages(images[i]);
        }

        for (int image : images)
            fliverImages(image);


        v_flipper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Kategori.this, "Silahkan Memilih Kategori Pesanan :)", Toast.LENGTH_LONG).show();
            }
        });



        btnkategorymakanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                meja = Nomejakategori.getText().toString();
                namakonsumen = Namakonsumen.getText().toString();
                Intent intent = new Intent(Kategori.this, ReadKonsumen.class );
                intent.putExtra("meja", meja);
                intent.putExtra("nama", namakonsumen);
                startActivity(intent);

            }
        });

        btnkategoryminuman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                meja = Nomejakategori.getText().toString();
                namakonsumen = Namakonsumen.getText().toString();
                Intent intent = new Intent(Kategori.this, ReadKonsumenminuman.class );
                intent.putExtra("meja", meja);
                intent.putExtra("nama", namakonsumen);
                startActivity(intent);

            }
        });

        btnkategorydisert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                meja = Nomejakategori.getText().toString();
                namakonsumen = Namakonsumen.getText().toString();
                Intent intent = new Intent(Kategori.this, ReadKonsumendisert.class );
                intent.putExtra("meja", meja);
                intent.putExtra("nama", namakonsumen);
                startActivity(intent);

            }
        });

    }

    private void submitKonsumen (Konsumen konsumen) {

        databaseReference.setValue(konsumen).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(Kategori.this, "Dimasukan Kedalam Cart", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void fliverImages(int images) {
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(images);

        v_flipper.addView(imageView);
        v_flipper.setFlipInterval(4200);
        v_flipper.setAutoStart(true);

        v_flipper.setInAnimation(this, android.R.anim.slide_in_left);
        v_flipper.setOutAnimation(this, android.R.anim.slide_out_right);


    }


    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (!Dalam.isShown() && linier.isShown()){
                            finish();
                        }else {
                            Dalam.setVisibility(View.INVISIBLE);
                            linier.setVisibility(View.VISIBLE);
                        }
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




