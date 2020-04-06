package com.example.kewirausahaan

class Akun {
    lateinit var nama:String
    lateinit var email:String
    lateinit var password:String
    lateinit var profesi:String
    lateinit var alamat:String
    lateinit var telp:String
    lateinit var gender:String
    lateinit var gambar:String

    constructor() {}
    constructor(nama:String, email:String, password:String, profesi:String, alamat:String, telp:String, gender:String, gambar:String) {
        this.nama = nama
        this.email = email
        this.password = password
        this.profesi = profesi
        this.alamat = alamat
        this.telp = telp
        this.gender = gender
        this.gambar = gambar
    }
}