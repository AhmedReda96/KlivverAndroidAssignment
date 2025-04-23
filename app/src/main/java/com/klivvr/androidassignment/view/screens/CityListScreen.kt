package com.klivvr.androidassignment.view.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.klivvr.androidassignment.utils.extention.openGoogleMapsApp
import com.klivvr.androidassignment.view.baseViews.BaseScreen
import com.klivvr.androidassignment.viewModel.CityViewModel
import com.klivvr.domain.base.ApiResponse
import com.klivvr.domain.model.AllCitiesRequest
import com.klivvr.domain.model.CitiesGroupModel
import com.klivvr.domain.model.CityModel
import com.klivvr.domain.model.QuerySearchRequest
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import coil.compose.rememberAsyncImagePainter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import com.klivvr.androidassignment.utils.extention.hideKeyboard

class CityListScreen(private val viewModel: CityViewModel) : BaseScreen() {
    private var citiesListGrouped = mutableStateListOf<CitiesGroupModel>()
    private var citiesCount = mutableStateOf("")
    private var searchQuery = mutableStateOf("")
    private var hasStartedSearchCollection = mutableStateOf(false)

    @Composable
    fun InvokeRouteUI() {

        InitVars()
        PopulateUI()
    }

    @Composable
    private fun InitVars() =
        with(rememberCoroutineScope()) {
            if (citiesListGrouped.isEmpty()) {
                launch { viewModel.citiesSF.collect { handleUI(it) } }
                viewModel.invokeAllCities(AllCitiesRequest())
            }

            if (searchQuery.value.isNotEmpty() && !hasStartedSearchCollection.value) {
                hasStartedSearchCollection.value = true
                launch { viewModel.searchResultSF.collect { handleUI(it) } }
            }
        }

    @Suppress("UNCHECKED_CAST")
    override fun handleResponse(response: ApiResponse<*>) {
        when (response.request) {
            is AllCitiesRequest, is QuerySearchRequest -> {
                citiesListGrouped.clear()
                citiesListGrouped.addAll(response.data as List<CitiesGroupModel>)
                citiesCount.value = citiesListGrouped.sumOf { it.cities.size }.toString()
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
    @Composable
    private fun PopulateUI() {
        val context = LocalContext.current

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFEEEEEE))
                .pointerInput(Unit) {
                    detectTapGestures(onTap = { context.hideKeyboard() })
                }
        ) {
            Text(
                text = "City Search",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge,
                color = Color(0xFF3D3C3C),
                modifier = Modifier.padding(bottom = 16.dp, top = 8.dp, start = 8.dp)
            )
            Text(
                text = "${citiesCount.value} Cities",
                fontSize = 12.sp,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Black,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .align(Alignment.CenterHorizontally)
            )

            if (isLoading.value) {
                CenteredCircleLoader()
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .imePadding()
                    .consumeWindowInsets(WindowInsets.ime),
            ) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    citiesListGrouped.forEach { group ->
                        stickyHeader {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 10.dp)
                            ) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier
                                        .size(50.dp)
                                        .clip(CircleShape)
                                        .background(Color.White)
                                        .border(0.5.dp, Color.Gray, CircleShape)
                                ) {
                                    Text(
                                        text = group.letter.orEmpty(),
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            color = Color.Black,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                }
                            }
                        }

                        items(group.cities.size) {
                            DrawListItem(group.cities[it])
                        }
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .background(Color.White)
            ) {
                SearchField(
                    query = searchQuery,
                    hasStartedSearchCollection = hasStartedSearchCollection,
                    onQueryChange = { query ->
                        viewModel.invokeQuerySearch(QuerySearchRequest(query = query))
                    }
                )
            }
        }
    }

    @Composable
    fun DrawListItem(city: CityModel) {
        val context = LocalContext.current
        var cardHeight by remember { mutableIntStateOf(0) }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 35.dp, end = 10.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(1.dp)
                    .height(with(LocalDensity.current) { cardHeight.toDp() + 8.dp })
                    .background(Color.LightGray)
                    .align(Alignment.TopStart)
            )

            Card(
                onClick = {
                    if (city.latitude != null && city.longitude != null) {
                        context.openGoogleMapsApp(city.latitude!!, city.longitude!!)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, bottom = 8.dp)
                    .onGloballyPositioned { coordinates ->
                        cardHeight = coordinates.size.height
                    },
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(0.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Row(
                    modifier = Modifier.padding(all = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .clip(CircleShape)
                            .width(70.dp)
                            .height(70.dp)
                            .background(Color(0xFFEEEEEE))
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(model = city.flagAssetPath),
                            contentDescription = "Country Flag",
                            modifier = Modifier
                                .width(40.dp)
                                .height(20.dp),
                        )
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp)
                    ) {
                        Text(
                            text = "${city.name}, ${city.country}",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.Black,
                            modifier = Modifier.padding(bottom = 5.dp)
                        )
                        Text(
                            text = "${city.latitude}, ${city.longitude}",
                            color = Color.DarkGray,
                            fontSize = 12.sp,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun SearchField(
        query: MutableState<String>,
        hasStartedSearchCollection: MutableState<Boolean>,
        onQueryChange: (String) -> Unit
    ) = with(rememberCoroutineScope()) {
        TextField(
            value = query.value,
            onValueChange = {
                query.value = it
                launch {
                    delay(100)
                    if (it.isEmpty()) {
                        viewModel.invokeAllCities(AllCitiesRequest())
                    } else {
                        if (!hasStartedSearchCollection.value) {
                            hasStartedSearchCollection.value = true
                        }
                        onQueryChange(it)
                    }
                }
            },
            placeholder = { Text("Searching...") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 6.dp)
                .background(Color(0xFFEEEEEE), shape = RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp)),
            singleLine = true,
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = "Search")
            },
            trailingIcon = {
                if (query.value.isNotEmpty()) {
                    IconButton(onClick = {
                        query.value = ""
                        viewModel.invokeAllCities(AllCitiesRequest())
                    }) {
                        Icon(Icons.Default.Close, contentDescription = "Clear")
                    }
                }
            },
            textStyle = TextStyle(
                fontSize = 16.sp,
                lineHeight = 16.sp
            )
        )
    }

    @Composable
    fun CenteredCircleLoader() {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .background(Color.White, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = Color.Black,
                    strokeWidth = 2.dp,
                    modifier = Modifier.size(18.dp))
            }
        }
    }
}

