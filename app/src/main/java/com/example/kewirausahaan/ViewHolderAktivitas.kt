package com.example.kewirausahaan

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class ViewHolderAktivitas(itemView:View): RecyclerView.ViewHolder(itemView) {
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

    fun setDetails(ctx: Context, gambararmada:String, tipearmada:String, tahunarmada:String, lokasitujuan:String, tanggalberangkat:String, tanggalpulang:String, biaya:String) {
        val gambarAr = mView.findViewById(R.id.gambarAAktivitas) as ImageView
        val tipeAr = mView.findViewById(R.id.tipeAAktivitas) as TextView
        val tahunAr = mView.findViewById(R.id.tahunAAktivitas) as TextView
        val tujuanAkt = mView.findViewById(R.id.tujuanAktivitas) as TextView
        val berangkatAkt = mView.findViewById(R.id.berangkatAktivitas) as TextView
        val pulangAkt = mView.findViewById(R.id.pulangAktivitas) as TextView
        val biayaAkt = mView.findViewById(R.id.biayaAktivitas) as TextView

        Picasso.get().load(gambararmada).into(gambarAr)
        tipeAr.text = tipearmada
        tahunAr.text = tahunarmada
        tujuanAkt.text = lokasitujuan
        berangkatAkt.text = tanggalberangkat
        pulangAkt.text = tanggalpulang
        biayaAkt.text = biaya
    }

    interface ClickListener {
        fun onItemClick(view:View, position:Int)
        fun onItemLongClick(view:View, position:Int)
    }

    fun setOnClickListener(clickListener:ViewHolderAktivitas.ClickListener) {
        mClickListener = clickListener
    }
}