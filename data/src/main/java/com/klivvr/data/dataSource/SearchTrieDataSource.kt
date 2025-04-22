package com.klivvr.data.dataSource

import com.klivvr.data.entity.CityEntity

class SearchTrieDataSource {
    private class TrieNode {
        val children: MutableMap<Char, TrieNode> = mutableMapOf()
        var isEnd: Boolean = false
        val cities: MutableList<CityEntity> = mutableListOf()
    }

    private val root = TrieNode()

    fun insert(city: CityEntity) {
        var node = root
        val key = city.name?.lowercase()
        for (char in key!!) {
            node = node.children.getOrPut(char) { TrieNode() }
        }
        node.isEnd = true
        node.cities.add(city)
    }

    fun search(prefix: String): List<CityEntity> {
        var node = root
        val key = prefix.lowercase()
        for (char in key) {
            node = node.children[char] ?: return emptyList()
        }
        return collectAllCities(node)
    }

    private fun collectAllCities(node: TrieNode): List<CityEntity> {
        val result = mutableListOf<CityEntity>()
        if (node.isEnd) result.addAll(node.cities)
        for (child in node.children.values) {
            result.addAll(collectAllCities(child))
        }
        return result
    }


}
