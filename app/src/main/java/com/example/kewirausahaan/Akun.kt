package com.example.kewirausahaan

class Akun {
    lateinit var nama:String
    lateinit var username:String
    lateinit var password:String
    lateinit var gender:String
    lateinit var alamat:String
    lateinit var telp:String
    lateinit var profesi:String

    constructor() {}
    constructor(nama:String, username:String, password:String, gender:String, alamat:String, telp:String, profesi:String) {
        this.nama = nama
        this.username = username
        this.password = password
        this.gender = gender
        this.alamat = alamat
        this.telp = telp
        this.profesi = profesi
    }
}