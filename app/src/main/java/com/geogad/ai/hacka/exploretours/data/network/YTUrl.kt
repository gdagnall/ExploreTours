package com.geogad.ai.hacka.exploretours.data.network

import android.net.Uri
import android.util.Log
import io.ktor.http.URLBuilder
import io.ktor.http.URLProtocol
import io.ktor.http.parametersOf
import io.ktor.http.toURI
import java.net.URI

object YTUrl {
    private val TAG = "YTUrl"

    fun buildYTUrl(prompt: String): String {
        Log.w(TAG, "In gbuildYTUrl(: prompt = $prompt")
        val urlConnnDetails =  URLBuilder(
            protocol = URLProtocol.HTTPS,
            host = YOUTUBE_BASE_URL,
            pathSegments = listOf( "results"),
            parameters = parametersOf("search_query", prompt)
        ) .build()
        Log.w(TAG, "Out buildYTUrl(: URL = ${urlConnnDetails.toString()}")
        return urlConnnDetails.toURI().toString()
    }
}