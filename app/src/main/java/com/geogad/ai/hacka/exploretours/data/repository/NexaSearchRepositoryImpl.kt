package com.geogad.ai.hacka.exploretours.data.repository

import android.content.Context
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat.getString
import com.geogad.ai.hacka.exploretours.R
import com.geogad.ai.hacka.exploretours.data.model.NexaSearchInput
import com.geogad.ai.hacka.exploretours.data.model.NexaYTVideoSearchOutput
import com.geogad.ai.hacka.exploretours.data.network.OCTOPUS_BASE_URL
import com.geogad.ai.hacka.exploretours.data.network.Resource
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.URLBuilder
import io.ktor.http.URLProtocol

class NexaSearchRepositoryImpl constructor(private val httpClient: HttpClient): NexaSearchRepository {
    private val urlConnn =  URLBuilder(
        protocol = URLProtocol.HTTPS,
        host = OCTOPUS_BASE_URL,
        pathSegments = listOf( "model", "octopus-v2" ),
    ) .build()

    override suspend fun searchYTVideos(searchLocation: String):  Resource<NexaYTVideoSearchOutput> {
        return try {

            Resource.Success(
                httpClient.post(urlConnn) {
                    setBody(NexaSearchInput(inputText = "Walking Tours In $searchLocation", category = "streaming"))
                }
                    .body<NexaYTVideoSearchOutput>()
             )
        } catch (e: Exception) {
            e.printStackTrace()
         //   Resource.Failure(e)
            Resource.Error("Oops! Unable to find a match. Try again later")
        }
    }

}