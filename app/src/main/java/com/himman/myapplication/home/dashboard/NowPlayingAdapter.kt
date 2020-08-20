package com.himman.myapplication.home.dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.himman.myapplication.R
import com.himman.myapplication.model.Film
import kotlinx.android.synthetic.main.row_item_now_playing.view.*

class NowPlayingAdapter(private var data: ArrayList<Film>,
                        private val listener: (Film) -> Unit) : RecyclerView.Adapter<NowPlayingAdapter.ViewHolder>() {
    lateinit var contextAdapter : Context

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NowPlayingAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        contextAdapter = parent.context
        val inflateView = layoutInflater.inflate(R.layout.row_item_now_playing, parent, false)
        return ViewHolder(inflateView)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: NowPlayingAdapter.ViewHolder, position: Int) {
        holder.bindItem(data[position], listener, contextAdapter)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
    {
        private val tvtitle: TextView = view.findViewById(R.id.tv_title)
        private val tvgenre: TextView = view.findViewById(R.id.tv_genre)
        private val tvrating: TextView = view.findViewById(R.id.tv_rate)

        private val ivImage: ImageView = view.findViewById(R.id.iv_poster_image)

        fun bindItem(data: Film, listener: (Film) -> Unit, context: Context){
            tvtitle.setText(data.judul)
            tvgenre.setText(data.genre)
            tvrating.setText(data.rating)

            Glide.with(context)
                .load(data.poster)
                .into(ivImage)

            itemView.setOnClickListener {
                listener(data)
            }
        }
    }
}
