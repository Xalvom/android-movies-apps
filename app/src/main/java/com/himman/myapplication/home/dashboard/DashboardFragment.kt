package com.himman.myapplication.home.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.database.*

import com.himman.myapplication.R
import com.himman.myapplication.model.Film
import com.himman.myapplication.utils.Preference
import kotlinx.android.synthetic.main.fragment_dashboard.*
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class DashboardFragment : Fragment() {

    private lateinit var preference: Preference
    private lateinit var mDatabase: DatabaseReference
    private var dataList = ArrayList<Film>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        preference = Preference(activity!!.applicationContext)
        mDatabase = FirebaseDatabase.getInstance().getReference("Film")

        tv_nama.setText(preference.getValues("nama"))
        if(!preference.getValues("saldo").equals("")){
            currency(preference.getValues("saldo")!!.toDouble(), tv_saldo)
        }

        Glide.with(this)
            .load(preference.getValues("url"))
            .apply(RequestOptions.circleCropTransform())
            .into(iv_profile)

        rv_nowPlaying.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rv_comingSoon.layoutManager = LinearLayoutManager(context)

        getData()
    }

    private fun getData(){
        mDatabase.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(context, databaseError.message, Toast.LENGTH_LONG).show()
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataList.clear()
                for(getdataSnapshot in dataSnapshot.children){
                    var film = getdataSnapshot.getValue(Film::class.java!!)
                    dataList.add(film!!)
                }

                rv_nowPlaying.adapter = NowPlayingAdapter(dataList){

                }
                rv_comingSoon.adapter = ComingSoonAdapter(dataList){

                }
            }

        })
    }

    private fun currency(saldo: Double, textView: TextView){
        val localID = Locale("in", "ID")
        val format = NumberFormat.getCurrencyInstance(localID)
        textView.setText(format.format(saldo))
    }
}
