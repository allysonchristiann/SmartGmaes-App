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
import com.bumptech.glide.Glide
import com.example.smartgame.R
import com.example.smartgame.model.Games
import com.example.smartgame.ui.GameInformation
import java.util.*

class GameListAdapter(val context: Context) : RecyclerView.Adapter<GameListAdapter.Holder>() {

    var listGames = Collections.emptyList<Games>()

    fun updateCategoryList(list: List<Games>) {
        listGames = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater
            .from(context)
            .inflate(R.layout.holder_games, parent, false)

        return Holder(view)
    }

    override fun getItemCount(): Int {
        return listGames.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val allGames = listGames[position]

        holder.tvGameName.text = allGames.name
        holder.tvGamePrice.text = "R$ ${String.format("%.2f", allGames.price)}"

        Glide.with(holder.itemView.getContext()).load(allGames.image).into(holder.ivGameImage)

        holder.cardButton.setOnClickListener {
            val intent = Intent(context, GameInformation::class.java)
            intent.putExtra("gameId", allGames.id)
            context.startActivity(intent)

            val prefs: SharedPreferences = context.getSharedPreferences(
                "preferencias",
                Context.MODE_PRIVATE
            )

            prefs.edit().putString("URL", allGames.image).apply()
        }
    }

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val ivGameImage = view.findViewById<ImageView>(R.id.image_game)
        val tvGameName = view.findViewById<TextView>(R.id.text_view_game_name)
        val tvGamePrice = view.findViewById<TextView>(R.id.text_view_price)
        val cardButton = view.findViewById<CardView>(R.id.card_view_game_list)
    }
}
