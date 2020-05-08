package com.example.kewirausahaan

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_akun.*

class FragmentAkun : Fragment() {

    lateinit var alertDialog: AlertDialog.Builder
    lateinit var databaseReference: DatabaseReference
    lateinit var textNama: TextView
    lateinit var imgAkun: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_akun, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        alertDialog = AlertDialog.Builder(activity!!)
        lihatAkun.setOnClickListener {
            val intent = Intent(activity, ActivityProfil::class.java)
            intent.putExtra("nama", textNama.text.toString())
            startActivity(intent)
        }
        akun1.setOnClickListener {
            val intent = Intent(activity, ActivityTentang::class.java)
            startActivity(intent)
        }
        akun2.setOnClickListener {
            val intent = Intent(activity, ActivityKontak::class.java)
            startActivity(intent)
        }
        akun3.setOnClickListener {
            val intent = Intent(activity, ActivityUlasan::class.java)
            intent.putExtra("nama", textNama.text.toString())
            startActivity(intent)
        }
        akun5.setOnClickListener {
            alertDialog.setTitle("Keluar Akun")
            alertDialog.setMessage("Apakah Kamu Ingin Keluar dari Akun Ini ?")
                .setCancelable(false)
                .setPositiveButton("YA", object: DialogInterface.OnClickListener {
                    override fun onClick(dialog:DialogInterface, id:Int) {
                        val intent = Intent(activity, ActivitySignin::class.java)
                        startActivity(intent)
                    }
                })
                .setNegativeButton("TIDAK", object: DialogInterface.OnClickListener {
                    override fun onClick(dialog:DialogInterface, id:Int) {
                        dialog.cancel()
                    }
                }).create().show()
        }

        textNama = view!!.findViewById<View>(R.id.namaAkun) as TextView
        imgAkun = view!!.findViewById<View>(R.id.imgAkun) as ImageView
        databaseReference = FirebaseDatabase.getInstance().reference
        val query = databaseReference.child("Pengguna").orderByChild("email").equalTo(activity!!.intent.getStringExtra("email"))
        query.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(datasnapshot: DataSnapshot) {
                if (datasnapshot != null)
                {
                    for (snapshot1 in datasnapshot.getChildren())
                    {
                        val allocation = snapshot1.getValue(Akun::class.java)
                        textNama.setText(allocation!!.nama)
                        Picasso.get().load(allocation!!.gambar).into(imgAkun)
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }
}