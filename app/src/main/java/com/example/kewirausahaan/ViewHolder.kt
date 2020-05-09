package com.example.kewirausahaan

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class ViewHolder(itemView:View): RecyclerView.ViewHolder(itemView) {
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

    fun setDetails(ctx: Context, tipe:String, tahun:String, jumlah:String, deskripsi:String, gambar1:String, gambar2:String) {
        val tipeArmada = mView.findViewById(R.id.tipeArmada) as TextView
        val tahunArmada = mView.findViewById(R.id.tahunArmada) as TextView
        val jumlahArmada = mView.findViewById(R.id.jumlahArmada) as TextView
        val deskripsiArmada = mView.findViewById(R.id.deskripsiArmada) as TextView
        val gambar1Armada = mView.findViewById(R.id.gambar1Armada) as ImageView
        val gambar2Armada = mView.findViewById(R.id.gambar2Armada) as ImageView

        tipeArmada.text = tipe
        tahunArmada.text = tahun
        jumlahArmada.text = jumlah
        deskripsiArmada.text = deskripsi
        Picasso.get().load(gambar1).into(gambar1Armada)
        Picasso.get().load(gambar2).into(gambar2Armada)
    }

    interface ClickListener {
        fun onItemClick(view:View, position:Int)
        fun onItemLongClick(view:View, position:Int)
    }

    fun setOnClickListener(clickListener:ViewHolder.ClickListener) {
        mClickListener = clickListener
    }
}