package com.example.kewirausahaan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //Bottom Navigation
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId) {
            R.id.home -> {
                replaceFragment(FragmentHome())
                return@OnNavigationItemSelectedListener true
            }
            R.id.aktivitas -> {
                replaceFragment(FragmentAktivitas())
                return@OnNavigationItemSelectedListener true
            }
            R.id.inbox -> {
                replaceFragment(FragmentInbox())
                return@OnNavigationItemSelectedListener true
            }
            R.id.akun -> {
                replaceFragment(FragmentAkun())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Bottom Navigation
        bottomNav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        replaceFragment(FragmentHome())
    }

    //Bottom Navigation
    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(R.id.fragmentContainer, fragment)
        fragmentTransition.commit()
    }
}
