package com.example.kewirausahaan

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter

class ViewImg : PagerAdapter {

    lateinit var con : Context
    lateinit var path : IntArray
    lateinit var inflater: LayoutInflater

    constructor(con: Context, path: IntArray) : super() {
        this.con = con
        this.path = path
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object` as ConstraintLayout
    }

    override fun getCount(): Int {
        return path.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var img : ImageView
        inflater = con.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var rootView : View = inflater.inflate(R.layout.img_slider, container, false)
        img = rootView.findViewById(R.id.imgHome) as ImageView
        img.setImageResource(path[position])
        container!!.addView(rootView)
        return rootView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container!!.removeView(`object` as ConstraintLayout)
    }
}