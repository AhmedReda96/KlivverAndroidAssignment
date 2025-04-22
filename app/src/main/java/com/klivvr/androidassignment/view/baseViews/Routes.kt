package com.klivvr.androidassignment.view.baseViews

sealed class Routes(val route: String) {
    object CityListScreen : Routes("city_list")
}
