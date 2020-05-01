package com.example.kewirausahaan

class ListArmada {
    lateinit var jenis:String
    lateinit var tipe:String
    lateinit var tahun:String
    lateinit var jumlah:String
    lateinit var deskripsi:String
    lateinit var gambar1:String
    lateinit var gambar2:String

    constructor(){}
    constructor(jenis:String, tipe:String, tahun:String, jumlah:String, deskripsi:String, gambar1:String, gambar2:String){
        this.jenis = jenis
        this.tipe = tipe
        this.tahun = tahun
        this.jumlah = jumlah
        this.deskripsi = deskripsi
        this.gambar1 = gambar1
        this.gambar2 = gambar2
    }
}