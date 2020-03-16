package com.example.kewirausahaan

import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_armada.*
import java.io.ByteArrayOutputStream

class ActivityArmada : AppCompatActivity() {

    lateinit var mLayoutManager:LinearLayoutManager //for sorting
    lateinit var mSharedPref: SharedPreferences //for saving sort settings
    lateinit var mRecyclerView:RecyclerView
    lateinit var databaseReference: DatabaseReference
    lateinit var textbarArmada: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_armada)

        setSupportActionBar(toolbarArmada)
        textbarArmada = findViewById(R.id.textbarArmada) as TextView
        databaseReference = FirebaseDatabase.getInstance().getReference("Armada")
        val query = databaseReference.child(intent.getStringExtra("jenis"))
        query.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(datasnapshot: DataSnapshot) {
                if (datasnapshot != null)
                {
                    for (snapshot1 in datasnapshot.getChildren())
                    {
                        val allocation = snapshot1.getValue(ListArmada::class.java)
                        textbarArmada.setText(allocation!!.jenis)
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })

        mSharedPref = getSharedPreferences("SortSettings", MODE_PRIVATE)
        val mSorting = mSharedPref.getString("Sort", "a-z")
        if (mSorting == "a-z")
        {
            mLayoutManager = LinearLayoutManager(this)
            mLayoutManager.setReverseLayout(true)
            mLayoutManager.setStackFromEnd(true)
        }
        else if (mSorting == "z-a")
        {
            mLayoutManager = LinearLayoutManager(this)
            mLayoutManager.setReverseLayout(false)
            mLayoutManager.setStackFromEnd(false)
        }

        mRecyclerView = findViewById(R.id.recyclerArmada)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.setLayoutManager(mLayoutManager)
    }

    private fun firebaseSearch(searchText:String) {

        val firebaseSearchQuery = FirebaseDatabase.getInstance().getReference("Armada").child(intent.getStringExtra("jenis")).orderByChild("tipe").startAt(searchText).endAt(searchText + "\uf8ff")
        val firebaseRecyclerAdapter = object:FirebaseRecyclerAdapter<ListArmada, ViewHolder>(
            ListArmada::class.java,
            R.layout.card_armada,
            ViewHolder::class.java,
            firebaseSearchQuery
        ) {
            override fun populateViewHolder(viewHolder:ViewHolder, model:ListArmada, position:Int) {
                viewHolder.setDetails(getApplicationContext(), model.tipe, model.tahun, model.cc, model.deskripsi, model.gambar1, model.gambar2)
            }
            override fun onCreateViewHolder(parent: ViewGroup, viewType:Int):ViewHolder {
                val viewHolder = super.onCreateViewHolder(parent, viewType)
                viewHolder.setOnClickListener(object: ViewHolder.ClickListener {
                    override fun onItemClick(view:View, position:Int) {

                        val tipeArmada = view.findViewById(R.id.tipeArmada) as TextView
                        val tahunArmada = view.findViewById(R.id.tahunArmada) as TextView
                        val ccArmada = view.findViewById(R.id.ccArmada) as TextView
                        val deskripsiArmada = view.findViewById(R.id.deskripsiArmada) as TextView
                        val gambar1Armada = view.findViewById(R.id.gambar1Armada) as ImageView
                        val gambar2Armada = view.findViewById(R.id.gambar2Armada) as ImageView

                        val tipeA = tipeArmada.getText().toString()
                        val tahunA = tahunArmada.getText().toString()
                        val ccA = ccArmada.getText().toString()
                        val deskripsiA = deskripsiArmada.getText().toString()
                        val gambar1A = gambar1Armada.getDrawable()
                        val bitmap1A = (gambar1A as BitmapDrawable).getBitmap()
                        val gambar2A = gambar2Armada.getDrawable()
                        val bitmap2A = (gambar2A as BitmapDrawable).getBitmap()

                        val intent = Intent(view.getContext(), ActivityDetail::class.java)
                        val stream = ByteArrayOutputStream()
                        bitmap1A.compress(Bitmap.CompressFormat.PNG, 100, stream)
                        bitmap2A.compress(Bitmap.CompressFormat.PNG, 100, stream)
                        val bytes = stream.toByteArray()
                        intent.putExtra("gambar1", bytes)
                        intent.putExtra("gambar2", bytes)
                        intent.putExtra("tipe", tipeA)
                        intent.putExtra("tahun", tahunA)
                        intent.putExtra("cc", ccA)
                        intent.putExtra("deskripsi", deskripsiA)
                        startActivity(intent)
                    }
                    override fun onItemLongClick(view:View, position:Int) {

                    }
                })
                return viewHolder
            }
        }
        //set adapter to recyclerview
        mRecyclerView.setAdapter(firebaseRecyclerAdapter)
    }

    override fun onStart() {
        super.onStart()
        val query = FirebaseDatabase.getInstance().getReference("Armada").child(intent.getStringExtra("jenis"))
        val firebaseRecyclerAdapter = object:FirebaseRecyclerAdapter<ListArmada, ViewHolder>(
            ListArmada::class.java,
            R.layout.card_armada,
            ViewHolder::class.java,
            query
        ) {
            override fun populateViewHolder(viewHolder:ViewHolder, model:ListArmada, position:Int) {
                viewHolder.setDetails(getApplicationContext(), model.tipe, model.tahun, model.cc, model.deskripsi, model.gambar1, model.gambar2)
            }
            override fun onCreateViewHolder(parent:ViewGroup, viewType:Int):ViewHolder {
                val viewHolder = super.onCreateViewHolder(parent, viewType)
                viewHolder.setOnClickListener(object: ViewHolder.ClickListener {
                    override fun onItemClick(view:View, position:Int) {

                        val tipeArmada = view.findViewById(R.id.tipeArmada) as TextView
                        val tahunArmada = view.findViewById(R.id.tahunArmada) as TextView
                        val ccArmada = view.findViewById(R.id.ccArmada) as TextView
                        val deskripsiArmada = view.findViewById(R.id.deskripsiArmada) as TextView
                        val gambar1Armada = view.findViewById(R.id.gambar1Armada) as ImageView
                        val gambar2Armada = view.findViewById(R.id.gambar2Armada) as ImageView

                        val tipeA = tipeArmada.getText().toString()
                        val tahunA = tahunArmada.getText().toString()
                        val ccA = ccArmada.getText().toString()
                        val deskripsiA = deskripsiArmada.getText().toString()
                        val gambar1A = gambar1Armada.getDrawable()
                        val bitmap1A = (gambar1A as BitmapDrawable).getBitmap()
                        val gambar2A = gambar2Armada.getDrawable()
                        val bitmap2A = (gambar2A as BitmapDrawable).getBitmap()

                        val intent = Intent(view.getContext(), ActivityDetail::class.java)
                        val stream = ByteArrayOutputStream()
                        bitmap1A.compress(Bitmap.CompressFormat.PNG, 100, stream)
                        bitmap2A.compress(Bitmap.CompressFormat.PNG, 100, stream)
                        val bytes = stream.toByteArray()
                        intent.putExtra("gambar1", bytes)
                        intent.putExtra("gambar2", bytes)
                        intent.putExtra("tipe", tipeA)
                        intent.putExtra("tahun", tahunA)
                        intent.putExtra("cc", ccA)
                        intent.putExtra("deskripsi", deskripsiA)
                        startActivity(intent)
                    }
                    override fun onItemLongClick(view:View, position:Int) {

                    }
                })
                return viewHolder
            }
        }
        //set adapter to recyclerview
        mRecyclerView.setAdapter(firebaseRecyclerAdapter)
    }
    override fun onCreateOptionsMenu(menu: Menu):Boolean {
        getMenuInflater().inflate(R.menu.menu, menu)
        val item = menu.findItem(R.id.action_search)
        val searchView = item.getActionView() as SearchView
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query:String):Boolean {
                firebaseSearch(query)
                return false
            }
            override fun onQueryTextChange(newText:String):Boolean {
                firebaseSearch(newText)
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem):Boolean {
        val id = item.getItemId()
        if (id == R.id.action_sort)
        {
            showSortDialog()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
    private fun showSortDialog() {
        val sortOptions = arrayOf<String>(" A-Z", " Z-A")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Sort by")
            .setIcon(R.drawable.icon_sort)
            .setItems(sortOptions, object: DialogInterface.OnClickListener {
                override fun onClick(dialog:DialogInterface, which:Int) {
                    if (which == 0)
                    {
                        val editor = mSharedPref.edit()
                        editor.putString("Sort", "a-z")
                        editor.apply()
                        recreate() //restart activity to take effect
                    }
                    else if (which == 1)
                    {
                        run {
                            val editor = mSharedPref.edit()
                            editor.putString("Sort", "z-a")
                            editor.apply()
                            recreate() //restart activity to take effect
                        }
                    }
                }
            })
        builder.show()
    }
}