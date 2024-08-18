package com.geogad.ai.hacka.exploretours.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NexaSearchInput(
    @SerialName("input_text")
    val inputText: String,
    @SerialName("category")
    val category: String
)

@Serializable
data class NexaYTVideoSearchOutput(
    @SerialName("result")
    val result: String,
    @SerialName("latency")
    val latency: Double,
    @SerialName("function_name")
    val functionName: String,
    @SerialName("function_arguments")
    val functionArguments: Array<String>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as NexaYTVideoSearchOutput

        if (result != other.result) return false
        if (latency != other.latency) return false
        if (functionName != other.functionName) return false
        if (!functionArguments.contentEquals(other.functionArguments)) return false

        return true
    }

    override fun hashCode(): Int {
        var result1 = result.hashCode()
        result1 = 31 * result1 + latency.hashCode()
        result1 = 31 * result1 + functionName.hashCode()
        result1 = 31 * result1 + functionArguments.contentHashCode()
        return result1
    }
}
