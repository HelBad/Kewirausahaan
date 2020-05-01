package com.example.kewirausahaan

class ListInbox {
    lateinit var judul:String
    lateinit var deskripsi:String

    constructor(){}
    constructor(judul:String, deskripsi:String){
        this.judul = judul
        this.deskripsi = deskripsi
    }
}