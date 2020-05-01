package com.example.kewirausahaan

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FragmentInbox : Fragment() {

    lateinit var mLayoutManager: LinearLayoutManager //for sorting
    lateinit var mRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_inbox, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mLayoutManager = LinearLayoutManager(activity)
        mRecyclerView = view!!.findViewById(R.id.recyclerInbox)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.setLayoutManager(mLayoutManager)
    }

    override fun onStart() {
        super.onStart()
        val query = FirebaseDatabase.getInstance().getReference("Inbox")
        val firebaseRecyclerAdapter = object: FirebaseRecyclerAdapter<ListInbox, ViewHolderInbox>(
            ListInbox::class.java,
            R.layout.card_inbox,
            ViewHolderInbox::class.java,
            query
        ) {
            override fun populateViewHolder(viewHolder:ViewHolderInbox, model:ListInbox, position:Int) {
                viewHolder.setDetails(activity!!.getApplicationContext(), model.judul, model.deskripsi)
            }
            override fun onCreateViewHolder(parent:ViewGroup, viewType:Int):ViewHolderInbox {
                val viewHolder = super.onCreateViewHolder(parent, viewType)
                viewHolder.setOnClickListener(object: ViewHolder.ClickListener {
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