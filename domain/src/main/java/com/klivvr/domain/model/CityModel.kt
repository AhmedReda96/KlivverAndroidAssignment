package com.klivvr.domain.model

data class CitiesGroupModel(
    val letter: String? = null,
    val cities: List<CityModel>
)

data class CityModel(
    val country: String? = null,
    val name: String? = null,
    val id: Long? = null,
    val longitude: Double? = null,
    val latitude: Double? = null,
    val flagAssetPath: String? = null
)
