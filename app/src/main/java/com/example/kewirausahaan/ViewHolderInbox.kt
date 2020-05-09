package com.example.kewirausahaan

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ViewHolderInbox(itemView:View): RecyclerView.ViewHolder(itemView) {
    internal var mView:View
    private var mClickListener: ClickListener? = null

    init{
        mView = itemView
        itemView.setOnClickListener(object: View.OnClickListener {
            override fun onClick(view:View) {
                mClickListener!!.onItemClick(view, adapterPosition)
            }
        })
        itemView.setOnLongClickListener(object: View.OnLongClickListener {
            override fun onLongClick(view:View):Boolean {
                mClickListener!!.onItemLongClick(view, adapterPosition)
                return true
            }
        })
    }

    fun setDetails(ctx: Context, judul:String, deskripsi:String) {
        val judulI = mView.findViewById(R.id.judulInbox) as TextView
        val deskripsiI = mView.findViewById(R.id.deskipsiInbox) as TextView
        judulI.text = judul
        deskripsiI.text = deskripsi
    }

    interface ClickListener {
        fun onItemClick(view:View, position:Int)
        fun onItemLongClick(view:View, position:Int)
    }

    fun setOnClickListener(clickListener:ViewHolderInbox.ClickListener) {
        mClickListener = clickListener
    }
}