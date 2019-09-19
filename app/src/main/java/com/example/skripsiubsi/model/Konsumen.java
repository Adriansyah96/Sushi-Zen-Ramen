package com.example.skripsiubsi.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
@IgnoreExtraProperties


public class Konsumen implements Serializable {

    private String namakonsumen;
    private String nomejakonsumen;
    private String jumlahpesanan;
    private String nama;
    private String harga;
    private String hasil;
    private String totalbayar;
    private String key;
    private String status;
    private String tanggal;















    public Konsumen() {


    }


    public String getTotalbayar() {
        return totalbayar;
    }

    public void setTotalbayar(String totalbayar) {
        this.totalbayar = totalbayar;
    }

    public String getNamakonsumen() {
        return namakonsumen;
    }

    public void setNamakonsumen(String namakonsumen) {
        this.namakonsumen = namakonsumen;
    }

    public String getNomejakonsumen() {
        return nomejakonsumen;
    }

    public void setNomejakonsumen(String nomejakonsumen) {
        this.nomejakonsumen = nomejakonsumen;
    }

    public String getJumlahpesanan() {
        return jumlahpesanan;
    }

    public void setJumlahpesanan(String jumlahpesanan) {
        this.jumlahpesanan = jumlahpesanan;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getHasil() {
        return hasil;
    }

    public void setHasil(String hasil) {
        this.hasil = hasil;
    }

    public String getKey() { return key; }

    public void setKey(String key) { this.key = key; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public String getTanggal() { return tanggal; }

    public void setTanggal(String tanggal) { this.tanggal = tanggal; }

    public Konsumen(String namakonsumen, String nomejakonsumen, String jumlahpesanan, String nama, String harga, String hasil) {
        this.namakonsumen = namakonsumen;
        this.nomejakonsumen = nomejakonsumen;
        this.jumlahpesanan = jumlahpesanan;
        this.nama = nama;
        this.harga = harga;
        this.hasil = hasil;

    }

    public Konsumen(String namakonsumen, String nomejakonsumen, String jumlahpesanan, String nama, String harga, String hasil, String totalbayar, String status, String tanggal) {
        this.namakonsumen = namakonsumen;
        this.nomejakonsumen = nomejakonsumen;
        this.jumlahpesanan = jumlahpesanan;
        this.nama = nama;
        this.harga = harga;
        this.hasil = hasil;
        this.totalbayar = totalbayar;
        this.status = status;
        this.status = tanggal;


    }
}

