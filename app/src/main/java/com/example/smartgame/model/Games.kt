package com.example.smartgame.model

import java.io.Serializable
import java.util.ArrayList

data class Games (
    var id: Int = 0,
    var name: String = "",
    var description: String = "",
    var price: Double = 0.0,
    var image: String = "",
    var discount: Double = 0.0,
    var Platforms: ArrayList<Platform>,
    var Stores: ArrayList<Store>,
): Serializable