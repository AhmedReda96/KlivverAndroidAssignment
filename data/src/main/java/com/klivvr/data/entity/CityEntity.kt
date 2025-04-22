package com.klivvr.data.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CityEntity(
    @SerializedName("country") val country: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("_id") val id: Long? = null,
    @SerializedName("coord") val coordinates: Coordinates? = null
) : Serializable

data class Coordinates(
    @SerializedName("lon") val longitude: Double? = null,
    @SerializedName("lat") val latitude: Double? = null
) : Serializable
