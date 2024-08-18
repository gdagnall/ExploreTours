package com.geogad.ai.hacka.exploretours.ui.screen


import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.getString
import com.geogad.ai.hacka.exploretours.R
import com.geogad.ai.hacka.exploretours.common.FullScreenProgressbar
import com.geogad.ai.hacka.exploretours.common.FullScreenTextView
import com.geogad.ai.hacka.exploretours.data.model.NexaYTVideoSearchOutput
import com.geogad.ai.hacka.exploretours.data.network.Resource
import com.geogad.ai.hacka.exploretours.data.network.YTUrl
import com.geogad.ai.hacka.exploretours.ui.component.ShowWebView


@SuppressLint("StateFlowValueCalledInComposition")
@ExperimentalFoundationApi
@Composable
fun tabVideos(videos: State<Resource<NexaYTVideoSearchOutput>?>) {

    videos.value?.let {
        when (it) {
            is Resource.Failure -> {
                FullScreenTextView(getString(LocalContext.current, R.string.error_message))
            }
            is Resource.Error -> {
                FullScreenTextView(getString(LocalContext.current, R.string.error_message))
            }
            Resource.Loading -> {
                FullScreenProgressbar()
            }
            is Resource.Success -> {
                val ytUrlParameter = YTUrl.buildYTUrl(it.result.functionArguments[0])
                if (!ytUrlParameter.isNullOrEmpty()) {
                    ShowWebView(url = ytUrlParameter)
                } else {
                    FullScreenTextView(getString(LocalContext.current, R.string.error_message))
                }
            }
        }
    }
}