package com.example.kewirausahaan

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.view.View
import android.widget.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_signup.*

class ActivitySignup : AppCompatActivity() {

    lateinit var btnSignup: Button
    lateinit var textNama: EditText
    lateinit var textEmail: EditText
    lateinit var textPassword: EditText
    lateinit var textProfesi: EditText
    lateinit var textAlamat: EditText
    lateinit var textTelp: EditText
    lateinit var textGender1: TextView
    lateinit var textGender: Spinner
    lateinit var databaseReference: DatabaseReference
    lateinit var uri:Uri
    var url:Uri? = null
    lateinit var storageReference: StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        textNama = findViewById(R.id.textNama)
        textEmail = findViewById(R.id.textEmail)
        textPassword = findViewById(R.id.textPassword)
        textProfesi = findViewById(R.id.textProfesi)
        textAlamat = findViewById(R.id.textAlamat)
        textTelp = findViewById(R.id.textTelp)
        btnSignup = findViewById(R.id.btnSignup)
        textGender1 = findViewById(R.id.textGender1)
        textGender = findViewById(R.id.textGender)
        val gender = arrayOf("Laki-laki", "Perempuan")
        textGender.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, gender)
        textGender.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                textGender1.text = "Pilih Jenis Kelamin"
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                textGender1.text = gender[position]
            }
        }

        toSignin.setOnClickListener {
            val intent = Intent(this@ActivitySignup, ActivitySignin::class.java)
            startActivity(intent)
        }

        databaseReference = FirebaseDatabase.getInstance().getReference("Pengguna")
        btnSignup.setOnClickListener(object: View.OnClickListener {
            override fun onClick(view:View) {
                addData()
            }
        })

        storageReference = FirebaseStorage.getInstance().getReference("Pengguna")
        imgSignup.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
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
                        val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
                        val bitmapDrawable = BitmapDrawable(bitmap)
                        imgSignup.setBackgroundDrawable(bitmapDrawable)
                        Toast.makeText(this, "Successfully Uploaded", Toast.LENGTH_SHORT).show()
                    }}
                } catch(ex:Exception) {
                    Toast.makeText(this, ex.toString(), Toast.LENGTH_SHORT).show()
                }
            }}
    }

    private fun addData() {
        val nama = textNama.text.toString().trim()
        val email = textEmail.text.toString().trim()
        val password = textPassword.text.toString().trim()
        val profesi = textProfesi.text.toString().trim()
        val alamat = textAlamat.text.toString().trim()
        val telp = textTelp.text.toString().trim()
        val gender = textGender1.text.toString().trim()
        val gambar = url.toString()

        if (!TextUtils.isEmpty(nama) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(profesi)
            && !TextUtils.isEmpty(alamat) && !TextUtils.isEmpty(telp) && !TextUtils.isEmpty(gender) && !TextUtils.isEmpty(gambar))
        {
            val add = Akun(nama, email, password, profesi, alamat, telp, gender, gambar)
            databaseReference.child(nama).setValue(add)
            Toast.makeText(this@ActivitySignup, "Data Terkirim", Toast.LENGTH_LONG).show()
        }
        else
        {
            Toast.makeText(this@ActivitySignup, "Data Masih Kosong", Toast.LENGTH_LONG).show()
        }
    }
}