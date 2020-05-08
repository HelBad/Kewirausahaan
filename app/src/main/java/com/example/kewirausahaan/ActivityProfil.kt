package com.example.kewirausahaan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profil.*

class ActivityProfil : AppCompatActivity() {

    lateinit var databaseReference: DatabaseReference
    lateinit var textNama:TextView
    lateinit var textEmail:TextView
    lateinit var textJk:TextView
    lateinit var textAlamat:TextView
    lateinit var textNo:TextView
    lateinit var textProfesi:TextView
    lateinit var imgProfil:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profil)

        textNama = findViewById(R.id.textNama)
        textEmail = findViewById(R.id.textEmail)
        textJk = findViewById(R.id.textJk)
        textAlamat = findViewById(R.id.textAlamat)
        textNo = findViewById(R.id.textNo)
        textProfesi = findViewById(R.id.textProfesi)
        imgProfil = findViewById(R.id.imgProfil)

        databaseReference = FirebaseDatabase.getInstance().reference
        val query = databaseReference.child("Pengguna").orderByChild("nama").equalTo(intent.getStringExtra("nama"))
        query.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(datasnapshot: DataSnapshot) {
                if (datasnapshot != null)
                {
                    for (snapshot1 in datasnapshot.children)
                    {
                        val allocation = snapshot1.getValue(Akun::class.java)
                        textNama.text = allocation!!.nama
                        textEmail.text = allocation.email
                        textProfesi.text = allocation.profesi
                        textJk.text = allocation.gender
                        textAlamat.text = allocation.alamat
                        textNo.text = allocation.telp
                        Picasso.get().load(allocation.gambar).into(imgProfil)
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }
}
