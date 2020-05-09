package com.example.kewirausahaan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.FirebaseDatabase

class FragmentInbox : Fragment() {

    lateinit var mRecyclerView: RecyclerView
    lateinit var mLayoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_inbox, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mLayoutManager = LinearLayoutManager(activity!!)
        mRecyclerView = view!!.findViewById(R.id.recyclerInbox)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = mLayoutManager
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
                viewHolder.setDetails(activity!!.applicationContext, model.judul, model.deskripsi)
            }
            override fun onCreateViewHolder(parent:ViewGroup, viewType:Int):ViewHolderInbox {
                val viewHolder = super.onCreateViewHolder(parent, viewType)
                viewHolder.setOnClickListener(object: ViewHolderInbox.ClickListener {
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