package com.example.smartgame.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.smartgame.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.map_stores)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {

        val retrievedLat: Double = intent.getDoubleExtra("latStore", 0.0)
        val retrievedLong: Double = intent.getDoubleExtra("longStore", 0.0)

        mMap = googleMap

        val zoomLevel = 16.0f
        val loja = LatLng(retrievedLat, retrievedLong)
        mMap.addMarker(MarkerOptions().position(loja).title("Localização da Loja"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loja, zoomLevel))
    }

}