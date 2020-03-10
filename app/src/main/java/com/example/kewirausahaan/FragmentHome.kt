package com.example.kewirausahaan

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_home.*

class FragmentHome : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        menuHome1.setOnClickListener {
            val intent = Intent(activity, ActivityList::class.java)
            startActivity(intent)
        }
        menuHome2.setOnClickListener {
            val intent = Intent(activity, ActivityList::class.java)
            startActivity(intent)
        }
        menuHome3.setOnClickListener {
            val intent = Intent(activity, ActivityList::class.java)
            startActivity(intent)
        }
        menuHome4.setOnClickListener {
            val intent = Intent(activity, ActivityList::class.java)
            startActivity(intent)
        }
        menuHome5.setOnClickListener {
            val intent = Intent(activity, ActivityList::class.java)
            startActivity(intent)
        }
        menuHome6.setOnClickListener {
            val intent = Intent(activity, ActivityList::class.java)
            startActivity(intent)
        }
        menuHome7.setOnClickListener {
            val intent = Intent(activity, ActivityList::class.java)
            startActivity(intent)
        }
        menuHome8.setOnClickListener {
            val intent = Intent(activity, ActivityList::class.java)
            startActivity(intent)
        }

    }
}