package com.klivvr.data.mapper

import com.klivvr.data.entity.CityEntity
import com.klivvr.domain.model.CitiesGroupModel
import com.klivvr.domain.model.CityModel


fun List<CityEntity>.toGroupedCities(): List<CitiesGroupModel> {
    return this.map { entity ->
        CityModel(
            country = entity.country,
            name = entity.name,
            id = entity.id,
            longitude = entity.coordinates?.longitude,
            latitude = entity.coordinates?.latitude,
            flagAssetPath = entity.country?.lowercase()?.let { "file:///android_asset/$it.png" }
        )
    }
        .groupBy { it.name?.firstOrNull()?.uppercaseChar() ?: '#' }
        .toSortedMap()
        .map { (letter, cities) ->
            CitiesGroupModel(letter = letter.toString(), cities = cities.sortedBy { it.name })
        }
}

