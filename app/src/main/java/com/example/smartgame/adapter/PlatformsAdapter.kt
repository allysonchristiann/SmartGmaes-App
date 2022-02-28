package com.example.smartgame.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.smartgame.R
import com.example.smartgame.model.Platform
import java.util.*

class PlatformsAdapter(val context:Context): RecyclerView.Adapter<PlatformsAdapter.Holder>() {

    var listGames = Collections.emptyList<Platform>()

    fun updateGame(list: List<Platform>) {
        listGames = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlatformsAdapter.Holder {
        val view = LayoutInflater
            .from(context)
            .inflate(R.layout.holder_platforms_list, parent, false)

        return PlatformsAdapter.Holder(view)
    }

    override fun getItemCount(): Int {
        return listGames.size
    }

    override fun onBindViewHolder(holder: PlatformsAdapter.Holder, position: Int) {
        val gameInfo = listGames[position]

        holder.gameName.text = gameInfo.name

    }

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val gameName = view.findViewById<TextView>(R.id.text_view_platform)
    }
}