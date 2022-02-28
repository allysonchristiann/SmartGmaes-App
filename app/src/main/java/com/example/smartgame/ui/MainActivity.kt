package com.example.smartgame.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smartgame.R
import com.example.smartgame.adapter.GameListAdapter
import com.example.smartgame.model.Games
import com.example.smartgame.api.CallGames
import com.example.smartgameapp.api.RetrofitApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var rvGames: RecyclerView
    lateinit var adapterGameList: GameListAdapter
    lateinit var qrCodeButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvGames= findViewById(R.id.recycler_view_games)
        rvGames.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        adapterGameList = GameListAdapter(this)

        rvGames.adapter = adapterGameList

        qrCodeButton = findViewById(R.id.button_qrcode)
        qrCodeButton.setOnClickListener{
            val scannerIntent = Intent(this, ScannerActivity::class.java)
            startActivity(scannerIntent)
        }
        scanQrCode()
        loadGameList()
    }

    private fun scanQrCode() {
        val scannedCode: String = intent.getStringExtra("qrCode").toString()

        if (scannedCode !== "null"){
            var game: Games
            val retrofit = RetrofitApi.getRetrofit()
            val gamesCall = retrofit.create(CallGames::class.java)
            val call = gamesCall.getDiscount(scannedCode.toInt())

            call.enqueue(object : retrofit2.Callback<Games> {

                override fun onFailure(call: Call<Games>, t: Throwable) {
                    Toast.makeText(this@MainActivity,"Ops! Acho que ocorreu um problema.",Toast.LENGTH_SHORT).show()
                    Log.e("Erro_CONEXÃO", t.message.toString())
                }

                override fun onResponse(call: Call<Games>, response: Response<Games>) {
                    game = response.body()!!

                    Toast.makeText(this@MainActivity, "20% de Desconto Aplicado em ${game.name}!", Toast.LENGTH_LONG).show()

                }
            })
        }
    }

    private fun loadGameList() {
        var listaItens: List<Games>
        val retrofit = RetrofitApi.getRetrofit()
        val gamesCall = retrofit.create(CallGames::class.java)
        val call = gamesCall.getGame()

        call.enqueue(object : Callback<List<Games>>{
            override fun onFailure(call: Call<List<Games>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Ops! Acho que ocorreu um problema.", Toast.LENGTH_SHORT).show()
                Log.e("Erro_CONEXÃO", t.message.toString())
            }
            override fun onResponse(call: Call<List<Games>>, response: Response<List<Games>>) {
                listaItens = response.body()!!

                adapterGameList.updateCategoryList(listaItens)
            }
        })
    }
}