package com.example.skripsiubsi.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;


@IgnoreExtraProperties

public class Makanan implements Serializable {

    private  String nama;
    private  String keterangan;
    private  String harga;
    private String key;
    private String image, status;





public Makanan(){

}

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() { return image; }

    public void setImage(String image) { this.image = image; }

    public String getNama() { return nama; }

    public void setNama(String nama) { this.nama = nama; }

    public String getKeterangan() { return keterangan; }

    public void setKeterangan(String keterangan) { this.keterangan = keterangan; }

    public String getHarga() { return harga; }

    public void setHarga(String harga) { this.harga = harga; }

    public String getKey() { return key; }

    public void setKey(String key) { this.key = key; }


    @Override
    public String toString() {
        return " "+nama+"\n" +
                " "+keterangan +"\n" +
                " "+harga +"\n" +
                " "+image;
    }


    public Makanan(String nm, String ktr, String hrg, String status) {
        this.nama = nm;
        this.keterangan = ktr;
        this.harga = hrg;
        this.status = status;

    }
}
