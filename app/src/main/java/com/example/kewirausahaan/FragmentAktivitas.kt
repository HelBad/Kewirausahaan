package com.example.kewirausahaan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.*

class FragmentAktivitas : Fragment() {
    lateinit var pemesanAktivitas: TextView
    lateinit var mRecyclerView: RecyclerView
    lateinit var mLayoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_aktivitas, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        pemesanAktivitas = view!!.findViewById<View>(R.id.pemesanAktivitas) as TextView
        val query = FirebaseDatabase.getInstance().getReference("Pesanan").orderByChild("emailcustomer").equalTo(activity!!.intent.getStringExtra("email"))
        query.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(datasnapshot: DataSnapshot) {
                if (datasnapshot != null)
                {
                    for (snapshot1 in datasnapshot.children)
                    {
                        val allocation = snapshot1.getValue(Pesanan::class.java)
                        pemesanAktivitas.text = allocation!!.namacostumer
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })

        mLayoutManager = LinearLayoutManager(activity!!)
        mRecyclerView = view!!.findViewById(R.id.recyclerAktivitas)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = mLayoutManager
    }

    override fun onStart() {
        super.onStart()
        val query = FirebaseDatabase.getInstance().getReference("Pesanan").orderByChild("emailcustomer").equalTo(activity!!.intent.getStringExtra("email"))
        val firebaseRecyclerAdapter = object: FirebaseRecyclerAdapter<Pesanan, ViewHolderAktivitas>(
            Pesanan::class.java,
            R.layout.card_aktivitas,
            ViewHolderAktivitas::class.java,
            query
        ) {
            override fun populateViewHolder(viewHolder:ViewHolderAktivitas, model:Pesanan, position:Int) {
                viewHolder.setDetails(activity!!.applicationContext, model.gambararmada, model.tipearmada, model.tahunarmada,
                    model.lokasitujuan, model.tanggalberangkat, model.tanggalpulang, model.biaya)
            }
            override fun onCreateViewHolder(parent:ViewGroup, viewType:Int):ViewHolderAktivitas {
                val viewHolder = super.onCreateViewHolder(parent, viewType)
                viewHolder.setOnClickListener(object: ViewHolderAktivitas.ClickListener {
                    override fun onItemClick(view:View, position:Int) {
                    }
                    override fun onItemLongClick(view:View, position:Int) {
                    }
                })
                return viewHolder
            }
        }
        mRecyclerView.setAdapter(firebaseRecyclerAdapter)
    }
}