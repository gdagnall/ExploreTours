package com.geogad.ai.hacka.exploretours.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material.icons.outlined.MyLocation
import androidx.compose.material.icons.outlined.VideoLibrary
import androidx.compose.material3.Icon
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.geogad.ai.hacka.exploretours.SearchLocationViewModel
import com.geogad.ai.hacka.exploretours.data.model.NexaYTVideoSearchOutput
import com.geogad.ai.hacka.exploretours.data.network.Resource

enum class TabContent {
    LOCATION, VIDEOS
}

//Int to Enum
inline fun <reified T : Enum<T>> Int.toEnum(): T? {
    return enumValues<T>().firstOrNull { it.ordinal == this }
}

data class TabItem(
    val id: TabContent,
    val title: String,
    val description: String,
    val unSelectedIcon: ImageVector,
    val selectedIcon: ImageVector
)


@ExperimentalFoundationApi
@Composable
fun TabScreens(viewModel: SearchLocationViewModel) {
    val selectedTabIndex = viewModel.selectedTabIndex.collectAsState()
    val searchYTDetails = viewModel.streamingPrompt.collectAsState()

    val tabItem = listOf(
        TabItem(
            id = TabContent.LOCATION,
            title = "Location",
            description = "Location To Explore",
            unSelectedIcon = Icons.Outlined.MyLocation,
            selectedIcon = Icons.Filled.MyLocation
        ), TabItem(
            id = TabContent.VIDEOS,
            title = "Tours",
            description = "Search For Self-Guided Tours",
            unSelectedIcon = Icons.Outlined.VideoLibrary,
            selectedIcon = Icons.Filled.VideoLibrary
        )
    )

    val pagerState = rememberPagerState { tabItem.size }

    val networkCalledState = remember { mutableStateOf(true) }

    LaunchedEffect(key1 = selectedTabIndex.value) {
        pagerState.animateScrollToPage(selectedTabIndex.value)
    }

    LaunchedEffect(key1 = pagerState.currentPage, pagerState.isScrollInProgress) {
        if (!pagerState.isScrollInProgress) {
            pagerState.currentPage.toEnum<TabContent>()?.let { viewModel.updateTab(it) }
        }
    }

    LaunchedEffect(key1 = searchYTDetails.value) {
        if (searchYTDetails.value != null) {
            val responsePrompt: Resource<NexaYTVideoSearchOutput> = searchYTDetails.value!!
            when (responsePrompt) {
                is Resource.Success -> {
                    viewModel.updateTab(TabContent.VIDEOS)
                    networkCalledState.value = false
                }
                is Resource.Error -> networkCalledState.value = false
                //Log.w(TAG, "Error getting YT prompt. Error = $(responsePrompt.exceptionMessage)")
                is Resource.Failure -> networkCalledState.value = false
                //  Log.w(TAG, "Failure getting YT prompt. Error = $(responsePrompt.exception)")
                is Resource.Loading -> networkCalledState.value = false
                //Log.w(TAG, "Sending YT prompt. Waiting....")
                null -> null
                //Log.w(TAG, "Sending YT prompt. null")
            }
        }
    }

    Column(modifier = Modifier.fillMaxWidth()) {

        TabRow(selectedTabIndex = selectedTabIndex.value) {
            tabItem.forEachIndexed { index, tabItem ->

                androidx.compose.material3.Tab(
                    selected = index == selectedTabIndex.value,
                    onClick = {
                        index.toEnum<TabContent>()?.let { viewModel.updateTab(it) }
                    },
                    text = { Text(text = tabItem.title, color = Color.White) },
                    icon = {
                        Icon(
                            imageVector = if (index == selectedTabIndex.value) {
                                tabItem.selectedIcon
                            } else tabItem.unSelectedIcon,
                            contentDescription = tabItem.title
                        )
                    }

                )
            }
        }

        HorizontalPager(
            state = pagerState, modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) { index ->
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                if (networkCalledState.value) {
                    Text(text = tabItem[index].description)
                }
                when (index) {
                    0 -> tabLocation(viewModel)
                    1 -> tabVideos(searchYTDetails)
                    else -> {
                       // print("x is neither 1 nor 2")
                    }
                }
            }
        }
    }
}