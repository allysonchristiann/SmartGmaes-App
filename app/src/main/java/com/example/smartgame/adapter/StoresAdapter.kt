package com.example.smartgame.adapter

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.smartgame.R
import com.example.smartgame.model.Games
import com.example.smartgame.model.Store
import com.example.smartgame.ui.MapsActivity
import java.util.*

class StoresAdapter(val context:Context): RecyclerView.Adapter<StoresAdapter.Holder>() {

    var listGames = Collections.emptyList<Store>()

    fun updateGame(list: List<Store>) {
        listGames = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoresAdapter.Holder {
        val view = LayoutInflater
            .from(context)
            .inflate(R.layout.holder_stores_list, parent, false)

        return StoresAdapter.Holder(view)
    }

    override fun getItemCount(): Int {
        return listGames.size
    }

    override fun onBindViewHolder(holder: StoresAdapter.Holder, position: Int) {
        val gameInfo = listGames[position]

        holder.storeName.text = gameInfo.place

        holder.cardStore.setOnClickListener {
            val intent = Intent(context, MapsActivity::class.java)
            intent.putExtra("latStore", gameInfo.latitude)
            intent.putExtra("longStore", gameInfo.longitude)
            context.startActivity(intent)

            val prefs: SharedPreferences = context.getSharedPreferences(
                "preferencias",
                Context.MODE_PRIVATE
            )
        }

    }

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val storeName = view.findViewById<TextView>(R.id.text_view_stores)
        val cardStore = view.findViewById<CardView>(R.id.card_stores)
    }
}