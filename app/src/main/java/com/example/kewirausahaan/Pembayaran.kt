package com.example.kewirausahaan

class Pembayaran {
    lateinit var tipearmada:String
    lateinit var jenisarmada:String
    lateinit var tahunarmada:String
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
    lateinit var gambarBukti:String

    constructor() {}
    constructor(tipearmada:String, jenisarmada:String, tahunarmada:String, namacostumer:String, emailcostumer:String,
                lokasiberangkat:String, lokasitujuan:String, tanggalberangkat:String, jamberangkat:String, tanggalpulang:String,
                jampulang:String, jumlahunit:String, fasilitas:String, biaya:String, gambarBukti:String) {
        this.tipearmada = tipearmada
        this.jenisarmada = jenisarmada
        this.tahunarmada = tahunarmada
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
        this.gambarBukti = gambarBukti
    }
}