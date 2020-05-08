package com.example.kewirausahaan

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*

class FragmentHome : Fragment() {

    //Image
    lateinit var viewpager : ViewPager
    var path : IntArray = intArrayOf(R.drawable.travel1, R.drawable.travel2, R.drawable.travel3, R.drawable.travel4)
    lateinit var adapter : ViewImg
    var currentPage : Int = 0
    lateinit var timer : Timer
    val DELAY : Long = 3000
    val PERIOD : Long = 3000

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    @SuppressLint("WrongConstant")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        menuHome1.setOnClickListener {
            val intent = Intent(activity, ActivityArmada::class.java)
            intent.putExtra("jenis", jenisArmada1.text.toString())
            startActivity(intent)
        }
        menuHome2.setOnClickListener {
            val intent = Intent(activity, ActivityArmada::class.java)
            intent.putExtra("jenis", jenisArmada2.text.toString())
            startActivity(intent)
        }
        menuHome3.setOnClickListener {
            val intent = Intent(activity, ActivityArmada::class.java)
            intent.putExtra("jenis", jenisArmada3.text.toString())
            startActivity(intent)
        }
        menuHome4.setOnClickListener {
            val intent = Intent(activity, ActivityArmada::class.java)
            intent.putExtra("jenis", jenisArmada4.text.toString())
            startActivity(intent)
        }
        menuHome5.setOnClickListener {
            val intent = Intent(activity, ActivityArmada::class.java)
            intent.putExtra("jenis", jenisArmada5.text.toString())
            startActivity(intent)
        }
        menuHome6.setOnClickListener {
            val intent = Intent(activity, ActivityArmada::class.java)
            intent.putExtra("jenis", jenisArmada6.text.toString())
            startActivity(intent)
        }
        menuHome7.setOnClickListener {
            val intent = Intent(activity, ActivityArmada::class.java)
            intent.putExtra("jenis", jenisArmada7.text.toString())
            startActivity(intent)
        }
        menuHome8.setOnClickListener {
            val intent = Intent(activity, ActivityArmada::class.java)
            intent.putExtra("jenis", jenisArmada8.text.toString())
            startActivity(intent)
        }

        //Image
        viewpager = view!!.findViewById(R.id.viewpagerHome) as ViewPager
        adapter = ViewImg(this.requireContext(), path)
        viewpagerHome.adapter = adapter
        updatePage()
        viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageSelected(position: Int) {
                currentPage = position
            }
        })

        //List Promo
        val listPromo = view!!.findViewById<View>(R.id.listPromo) as RecyclerView
        val image = intArrayOf(R.drawable.promo3, R.drawable.promo2, R.drawable.promo1)
        val manager = LinearLayoutManager(context, LinearLayout.HORIZONTAL, false)
        listPromo.layoutManager = manager
        listPromo.adapter = AdapterPromo(image, this.requireContext())
        val snapHelper : SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(listPromo)
    }

    //Image
    fun updatePage() {
        var handler = Handler()
        val Update : Runnable = Runnable {
            if(currentPage == path.size) {
                currentPage = 0
            }
            viewpager.setCurrentItem(currentPage++, true)
        }
        timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                handler.post(Update)
            }
        }, DELAY, PERIOD)
    }
}