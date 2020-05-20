package com.example.kewirausahaan

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_pembayaran.*

class ActivityPembayaran : AppCompatActivity() {

    lateinit var gambarAPembayaran: ImageView
    lateinit var tipeAPembayaran: TextView
    lateinit var jenisAPembayaran: TextView
    lateinit var tahunAPembayaran: TextView
    lateinit var deskripsiAPembayaran: TextView
    lateinit var namaPembayar: TextView
    lateinit var emailPembayar: TextView
    lateinit var textLokasiKeberangkatan: TextView
    lateinit var textLokasiTujuan: TextView
    lateinit var textTglBerangkat: TextView
    lateinit var textJamBerangkat: TextView
    lateinit var textTglPulang: TextView
    lateinit var textJamPulang: TextView
    lateinit var textUnit: TextView
    lateinit var textPaketBayar: TextView
    lateinit var textBayar: TextView
    lateinit var textKonfirm: TextView

    lateinit var databaseReference: DatabaseReference
    lateinit var uri: Uri
    var url:Uri? = null
    lateinit var storageReference: StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pembayaran)

        gambarAPembayaran = findViewById(R.id.gambarAPembayaran)
        tipeAPembayaran = findViewById(R.id.tipeAPembayaran)
        jenisAPembayaran = findViewById(R.id.jenisAPembayaran)
        tahunAPembayaran = findViewById(R.id.tahunAPembayaran)
        deskripsiAPembayaran = findViewById(R.id.deskripsiAPembayaran)
        namaPembayar = findViewById(R.id.namaPembayar)
        emailPembayar = findViewById(R.id.emailPembayar)
        textLokasiKeberangkatan = findViewById(R.id.textLokasiKeberangkatan)
        textLokasiTujuan = findViewById(R.id.textLokasiTujuan)
        textTglBerangkat = findViewById(R.id.textTglBerangkat)
        textJamBerangkat = findViewById(R.id.textJamBerangkat)
        textTglPulang = findViewById(R.id.textTglPulang)
        textJamPulang = findViewById(R.id.textJamPulang)
        textUnit = findViewById(R.id.textUnit)
        textPaketBayar = findViewById(R.id.textPaketBayar)
        textBayar = findViewById(R.id.textBayar)
        textKonfirm = findViewById(R.id.textKonfirm)

        val query = FirebaseDatabase.getInstance().getReference("Pesanan").child("tanggal" + " (" + "nama" + ")").orderByChild("tipearmada").equalTo(intent.getStringExtra("tipe"))
        query.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(datasnapshot: DataSnapshot) {
                if (datasnapshot != null)
                {
                    for (snapshot1 in datasnapshot.children)
                    {
                        val allocation = snapshot1.getValue(Pesanan::class.java)
                        Picasso.get().load(allocation!!.gambararmada).into(gambarAPembayaran)
                        tipeAPembayaran.text = allocation.tipearmada
                        jenisAPembayaran.text = allocation.jenisarmada
                        tahunAPembayaran.text = allocation.tahunarmada
                        deskripsiAPembayaran.text = allocation.deskripsiarmada
                        namaPembayar.text = allocation.namacostumer
                        emailPembayar.text = allocation.emailcostumer
                        textLokasiKeberangkatan.text = allocation.lokasiberangkat
                        textLokasiTujuan.text = allocation.lokasitujuan
                        textTglBerangkat.text = allocation.tanggalberangkat
                        textJamBerangkat.text = allocation.jamberangkat
                        textTglPulang.text = allocation.tanggalpulang
                        textJamPulang.text = allocation.jampulang
                        textUnit.text = allocation.jumlahunit
                        textPaketBayar.text = allocation.fasilitas
                        textBayar.text = allocation.biaya
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })

        databaseReference = FirebaseDatabase.getInstance().getReference("Pembayaran")
        btnKonfirm.setOnClickListener(object: View.OnClickListener {
            override fun onClick(view:View) {
                addData()
                btnKonfirm.isEnabled = false
            }
        })

        storageReference = FirebaseStorage.getInstance().getReference("Pembayaran")
        btnUpload.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == RESULT_OK) {
            if(requestCode == 0) {
                uri = data!!.data!!
                var mStorage = storageReference.child(uri.lastPathSegment!!)
                try {
                    mStorage.putFile(uri).addOnFailureListener {}.addOnSuccessListener () {
                            taskSnapshot -> mStorage.downloadUrl.addOnCompleteListener() { taskSnapshot ->
                        url = taskSnapshot.result
                        btnUpload.isEnabled = false
                        Toast.makeText(this, "Successfully Uploaded", Toast.LENGTH_SHORT).show()
                    }}
                } catch(ex:Exception) {
                    Toast.makeText(this, ex.toString(), Toast.LENGTH_SHORT).show()
                }
            }}
    }

    private fun addData() {
        val tipearmada = tipeAPembayaran.text.toString().trim()
        val jenisarmada = jenisAPembayaran.text.toString().trim()
        val tahunarmada = tahunAPembayaran.text.toString().trim()
        val namacostumer = namaPembayar.text.toString().trim()
        val emailcostumer = emailPembayar.text.toString().trim()
        val lokasiberangkat = textLokasiKeberangkatan.text.toString().trim()
        val lokasitujuan = textLokasiTujuan.text.toString().trim()
        val tanggalberangkat = textTglBerangkat.text.toString().trim()
        val jamberangkat = textJamBerangkat.text.toString().trim()
        val tanggalpulang = textTglPulang.text.toString().trim()
        val jampulang = textJamPulang.text.toString().trim()
        val jumlahunit = textUnit.text.toString().trim()
        val fasilitas = textPaketBayar.text.toString().trim()
        val biaya = textBayar.text.toString().trim()
        val gambarBukti = url.toString()

        if (!TextUtils.isEmpty(gambarBukti))
        {
            val bayar = Pembayaran(tipearmada, jenisarmada, tahunarmada, namacostumer, emailcostumer, lokasiberangkat,
                lokasitujuan, tanggalberangkat, jamberangkat, tanggalpulang, jampulang, jumlahunit, fasilitas, biaya, gambarBukti)
            databaseReference.child(tanggalberangkat + " (" + namacostumer + ")").setValue(bayar)
            Toast.makeText(this, "Terima kasih telah Memesan Travel Kami", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Lengkapi semua data", Toast.LENGTH_SHORT).show()
        }
    }
}
