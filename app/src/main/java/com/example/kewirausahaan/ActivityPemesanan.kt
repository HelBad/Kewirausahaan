package com.example.kewirausahaan

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.database.*
import com.squareup.picasso.Picasso

class ActivityPemesanan : AppCompatActivity() {

    lateinit var tipeArmada: TextView
    lateinit var tahunArmada:TextView
    lateinit var jumlahArmada:TextView
    lateinit var deskripsiArmada:TextView
    lateinit var gambarArmada: ImageView
    lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pemesanan)

        tipeArmada = findViewById(R.id.tipeAPemesanan)
        tahunArmada = findViewById(R.id.tahunAPemesanan)
        jumlahArmada = findViewById(R.id.tahunAPemesanan)
        deskripsiArmada = findViewById(R.id.deskripsiAPemesanan)
        gambarArmada = findViewById(R.id.gambarAPemesanan)
        databaseReference = FirebaseDatabase.getInstance().getReference()
        val query = databaseReference.child("Armada").child(intent.getStringExtra("jenis")).orderByChild("tipe").equalTo(intent.getStringExtra("tipe"))
        query.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(datasnapshot: DataSnapshot) {
                if (datasnapshot != null)
                {
                    for (snapshot1 in datasnapshot.getChildren())
                    {
                        val allocation = snapshot1.getValue(ListArmada::class.java)
                        tipeArmada.text = allocation!!.tipe
                        tahunArmada.text = allocation.tahun
                        jumlahArmada.text = allocation.jumlah
                        deskripsiArmada.text = allocation.deskripsi
                        Picasso.get().load(allocation.gambar1).into(gambarArmada)
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }
}
