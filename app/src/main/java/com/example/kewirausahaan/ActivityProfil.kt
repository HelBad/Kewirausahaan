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
    lateinit var textNim:TextView
    lateinit var textNama:TextView
    lateinit var textEmail:TextView
    lateinit var textJk:TextView
    lateinit var textProdi:TextView
    lateinit var textIpk:TextView
    lateinit var textAlamat:TextView
    lateinit var textNo:TextView
    lateinit var imgProfil:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profil)

        btnBack.setOnClickListener {
            finish()
        }

        textNim = findViewById(R.id.textNim) as TextView
        textNama = findViewById(R.id.textNama) as TextView
        textEmail = findViewById(R.id.textEmail) as TextView
        textJk = findViewById(R.id.textJk) as TextView
        textProdi = findViewById(R.id.textProdi) as TextView
        textIpk = findViewById(R.id.textIpk) as TextView
        textAlamat = findViewById(R.id.textAlamat) as TextView
        textNo = findViewById(R.id.textNo) as TextView
        imgProfil = findViewById(R.id.imgProfil) as ImageView

        databaseReference = FirebaseDatabase.getInstance().getReference()
        val query = databaseReference.child("mhs").orderByChild("nama").equalTo(intent.getStringExtra("nama"))
        query.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(datasnapshot: DataSnapshot) {
                if (datasnapshot != null)
                {
                    for (snapshot1 in datasnapshot.getChildren())
                    {
                        val allocation = snapshot1.getValue(Akun::class.java)
                        textNim.setText(allocation!!.nim)
                        textNama.setText(allocation!!.nama)
                        textEmail.setText(allocation!!.email)
                        textJk.setText(allocation!!.jk)
                        textProdi.setText(allocation!!.prodi)
                        textIpk.setText(allocation!!.ipk)
                        textAlamat.setText(allocation!!.alamat)
                        textNo.setText(allocation!!.telp)
                        Picasso.get().load(allocation!!.img).into(imgProfil)
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }
}
