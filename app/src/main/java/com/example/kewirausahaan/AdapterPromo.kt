package com.example.kewirausahaan

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class AdapterPromo(private val image : IntArray, private val mContext : Context) :
    RecyclerView.Adapter<ListPromo>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListPromo {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.cardview_promo, parent, false)
        return ListPromo(v, mContext)
    }

    override fun onBindViewHolder(holder: ListPromo, position: Int) {
        holder.index(image[position])
    }

    override fun getItemCount(): Int {
        return image.size
    }
}