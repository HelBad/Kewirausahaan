package com.example.kewirausahaan

class Pesanan {
    lateinit var gambararmada:String
    lateinit var tipearmada:String
    lateinit var jenisarmada:String
    lateinit var tahunarmada:String
    lateinit var deskripsiarmada: String
    lateinit var namacostumer:String
    lateinit var emailcostumer:String
    lateinit var lokasiberangkat:String
    lateinit var lokasitujuan:String
    lateinit var tanggalberangkat:String
    lateinit var jamberangkat:String
    lateinit var tanggalpulang:String
    lateinit var jampulang:String
    lateinit var jumlahunit:String
    lateinit var fasilitas:String
    lateinit var biaya:String

    constructor() {}
    constructor(gambararmada:String, tipearmada:String, jenisarmada:String, tahunarmada:String, deskripsiarmada:String,
                namacostumer:String, emailcostumer:String, lokasiberangkat:String, lokasitujuan:String, tanggalberangkat:String,
                jamberangkat:String, tanggalpulang:String, jampulang:String, jumlahunit:String, fasilitas:String, biaya:String) {
        this.gambararmada = gambararmada
        this.tipearmada = tipearmada
        this.jenisarmada = jenisarmada
        this.tahunarmada = tahunarmada
        this.deskripsiarmada = deskripsiarmada
        this.namacostumer = namacostumer
        this.emailcostumer = emailcostumer
        this.lokasiberangkat = lokasiberangkat
        this.lokasitujuan = lokasitujuan
        this.tanggalberangkat = tanggalberangkat
        this.jamberangkat = jamberangkat
        this.tanggalpulang = tanggalpulang
        this.jampulang = jampulang
        this.jumlahunit = jumlahunit
        this.fasilitas = fasilitas
        this.biaya = biaya
    }
}