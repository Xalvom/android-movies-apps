package com.himman.myapplication.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.himman.myapplication.R
import com.himman.myapplication.home.dashboard.DashboardFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val fragmentHome =
            DashboardFragment()
        val fragmentTiket = TiketFragment()
        val fragmentAccount = AccountFragment()
        setFragment(fragmentHome)
        iv_home.setOnClickListener {
            setFragment(fragmentHome)

            changeIcon(iv_home, R.drawable.ic_home_active)
            changeIcon(iv_tiket, R.drawable.icon_tiket)
            changeIcon(iv_account, R.drawable.ic_icon_account)
        }
        iv_tiket.setOnClickListener {
            setFragment(fragmentTiket)

            changeIcon(iv_home, R.drawable.ic_icon_home)
            changeIcon(iv_tiket, R.drawable.ic_tiket_active)
            changeIcon(iv_account, R.drawable.ic_icon_account)
        }
        iv_account.setOnClickListener {
            setFragment(fragmentAccount)

            changeIcon(iv_home, R.drawable.ic_icon_home)
            changeIcon(iv_tiket, R.drawable.icon_tiket)
            changeIcon(iv_account, R.drawable.ic_profile_active)
        }
    }

    private fun setFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.layout_fragment, fragment)
        fragmentTransaction.commit()
    }

    private fun changeIcon(imageView: ImageView, int: Int){
        imageView.setImageResource(int)
    }
}
