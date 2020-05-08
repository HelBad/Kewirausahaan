package com.example.kewirausahaan

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_signin.*

class ActivitySignin : AppCompatActivity(), View.OnClickListener {

    lateinit var alertDialog: AlertDialog.Builder
    lateinit var btnSignin: Button
    lateinit var textEmail: EditText
    lateinit var textPassword:EditText
    lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        toSignup.setOnClickListener {
            val intent = Intent(this@ActivitySignin, ActivitySignup::class.java)
            startActivity(intent)
        }

        alertDialog = AlertDialog.Builder(this)
        textEmail = findViewById(R.id.textEmail)
        textPassword = findViewById(R.id.textPassword)
        btnSignin = findViewById(R.id.btnSignin)
        btnSignin.setOnClickListener(this)
        databaseReference = FirebaseDatabase.getInstance().reference
    }

    override fun onClick(view: View) {
        if (view === btnSignin)
        {
            signinAkun()
        }
    }

    private fun signinAkun() {
        val query = databaseReference.child("Pengguna").orderByChild("email").equalTo(textEmail.text.toString().trim())
        query.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists())
                {
                    for (user in dataSnapshot.children)
                    {
                        val usersBean = user.getValue(Akun::class.java)
                        if (usersBean!!.password == textPassword.text.toString().trim())
                        {
                            val intent = Intent(applicationContext, ActivityMain::class.java)
                            intent.putExtra("email", textEmail.text.toString())
                            startActivity(intent)
                        }
                        else
                        {
                            Toast.makeText(this@ActivitySignin, "Password is wrong", Toast.LENGTH_LONG).show()
                        }
                    }
                }
                else
                {
                    Toast.makeText(this@ActivitySignin, "User not found", Toast.LENGTH_LONG).show()
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    override fun onBackPressed() {
        Toast.makeText(this@ActivitySignin, "Back is Clicked", Toast.LENGTH_SHORT).show()
        alertDialog.setTitle("Close Application")
        alertDialog.setMessage("Do you want to close the application ?")
            .setCancelable(false)
            .setPositiveButton("YES", object: DialogInterface.OnClickListener {
                override fun onClick(dialog:DialogInterface, id:Int) {
                    finishAffinity()
                }
            })
            .setNegativeButton("NO", object: DialogInterface.OnClickListener {
                override fun onClick(dialog:DialogInterface, id:Int) {
                    dialog.cancel()
                }
            }).create().show()
    }
}