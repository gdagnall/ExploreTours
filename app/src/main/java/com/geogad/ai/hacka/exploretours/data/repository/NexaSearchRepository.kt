package com.geogad.ai.hacka.exploretours.data.repository

import com.geogad.ai.hacka.exploretours.data.model.NexaYTVideoSearchOutput
import com.geogad.ai.hacka.exploretours.data.network.Resource

interface NexaSearchRepository {
    suspend fun searchYTVideos(searchLocation: String): Resource<NexaYTVideoSearchOutput>
}