package com.klivvr.androidassignment.view.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.klivvr.androidassignment.utils.theme.KlivvrAndroidAssignmentTheme
import com.klivvr.androidassignment.view.baseViews.Routes
import com.klivvr.androidassignment.view.screens.CityListScreen
import com.klivvr.androidassignment.viewModel.CityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : ComponentActivity() {
    private val viewModel: CityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KlivvrAndroidAssignmentTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Routes.CityListScreen.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(Routes.CityListScreen.route) {
                            CityListScreen(viewModel = viewModel).InvokeRouteUI()
                        }
                    }
                }
            }
        }
    }
}