package com.example.smartgame.ui

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.smartgame.R
import com.example.smartgame.adapter.PlatformsAdapter
import com.example.smartgame.adapter.StoresAdapter
import com.example.smartgame.api.CallGames
import com.example.smartgame.model.Games
import com.example.smartgameapp.api.RetrofitApi
import retrofit2.Call
import retrofit2.Response

class GameInformation : AppCompatActivity() {

    lateinit var rvStoresList : RecyclerView
    lateinit var rvPlatformsList : RecyclerView
    lateinit var adapterPlatforms: PlatformsAdapter
    lateinit var adapterStores: StoresAdapter
    lateinit var buy: Button

    lateinit var ivgameImage : ImageView
    lateinit var ivgameImageUrl : String
    lateinit var tvgamePrice : TextView
    lateinit var tvgameName : TextView
    lateinit var tvgameDecription : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_information)

        adapterPlatforms = PlatformsAdapter(this)
        adapterStores = StoresAdapter(this)

        rvPlatformsList= findViewById(R.id.chip_platform)
        rvStoresList= findViewById(R.id.recycler_view_stores)
        rvPlatformsList.adapter = adapterPlatforms
        rvStoresList.adapter = adapterStores

        rvStoresList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvPlatformsList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        ivgameImage = findViewById(R.id.game_image)
        tvgameName = findViewById(R.id.game_name)
        tvgamePrice = findViewById(R.id.game_value)
        tvgameDecription = findViewById(R.id.game_description)
        buy = findViewById(R.id.buy)

        buy.setOnClickListener {
            Toast.makeText(this, "Compra Efetuada!", Toast.LENGTH_SHORT).show()
        }

        carregarDados()

        val prefs: SharedPreferences = this@GameInformation.getSharedPreferences("preferencias", Context.MODE_PRIVATE)
        val retrivedUrl: String = prefs.getString("URL", "Nada foi recebido").toString()

        Glide.with(this).load(retrivedUrl).into(ivgameImage)

    }

    private fun carregarDados() {

        val retrievedId: Int = intent.getIntExtra("gameId", 0)

        var game: Games
        val retrofit = RetrofitApi.getRetrofit()
        val gamesCall = retrofit.create(CallGames::class.java)
        val call = gamesCall.getOneGame(retrievedId)

        call.enqueue(object : retrofit2.Callback<Games>{

            override fun onFailure(call: Call<Games>, t: Throwable) {
                Toast.makeText(this@GameInformation, "Ops! Acho que ocorreu um problema.", Toast.LENGTH_SHORT).show()
                Log.e("Erro_CONEXÃO", t.message.toString())
            }
            override fun onResponse(call: Call<Games>, response: Response<Games>) {
                game = response.body()!!

                tvgameName.text = game.name
                tvgameDecription.text = game.description
                ivgameImageUrl = game.image
                tvgamePrice.text = "R$ ${String.format("%.2f", game.price)}"
                adapterPlatforms.updateGame(game.Platforms)
                adapterStores.updateGame(game.Stores)

            }
        })
    }
}