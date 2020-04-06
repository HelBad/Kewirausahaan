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

        textNama = findViewById(R.id.textNama) as TextView
        textEmail = findViewById(R.id.textEmail) as TextView
        textJk = findViewById(R.id.textJk) as TextView
        textAlamat = findViewById(R.id.textAlamat) as TextView
        textNo = findViewById(R.id.textNo) as TextView
        textProfesi = findViewById(R.id.textProfesi) as TextView
        imgProfil = findViewById(R.id.imgProfil) as ImageView

        databaseReference = FirebaseDatabase.getInstance().getReference()
        val query = databaseReference.child("Pengguna").orderByChild("nama").equalTo(intent.getStringExtra("nama"))
        query.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(datasnapshot: DataSnapshot) {
                if (datasnapshot != null)
                {
                    for (snapshot1 in datasnapshot.getChildren())
                    {
                        val allocation = snapshot1.getValue(Akun::class.java)
                        textNama.setText(allocation!!.nama)
                        textEmail.setText(allocation!!.email)
                        textProfesi.setText(allocation!!.profesi)
                        textJk.setText(allocation!!.gender)
                        textAlamat.setText(allocation!!.alamat)
                        textNo.setText(allocation!!.telp)
                        Picasso.get().load(allocation!!.gambar).into(imgProfil)
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }
}
