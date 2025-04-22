package com.klivvr.data.dataSource

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.klivvr.data.entity.CityEntity

class CityDataSource(
    private val trie: SearchTrieDataSource
) {
    private var allEntities: List<CityEntity>? = null

    fun loadAllData(context: Context): List<CityEntity> {
        allEntities?.let { return it }

        return try {
            val jsonFile = context.assets.open("cities.json").bufferedReader().use { it.readText() }
            val type = object : TypeToken<List<CityEntity>>() {}.type
            val entities: List<CityEntity> = Gson().fromJson(jsonFile, type)

            allEntities = entities.sortedBy { it.name }.also { sortedEntities ->
                sortedEntities.forEach { entity ->
                    trie.insert(entity)
                }
            }
            allEntities!!
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    fun search(prefix: String): List<CityEntity> =
        if (prefix.isBlank()) allEntities.orEmpty() else trie.search(prefix)

}
