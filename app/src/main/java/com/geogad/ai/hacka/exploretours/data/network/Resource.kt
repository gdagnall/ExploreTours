package com.geogad.ai.hacka.exploretours.data.network

/*
import kotlinx.coroutines.flow.MutableStateFlow
import java.lang.Exception

sealed class Resource<out R> : MutableStateFlow<Resource<String>?> {
    data class Success<out R>(val result: R): Resource<R>()
    data class Error(val exceptionMessage: String): Resource<Nothing>()
    data class Failure(val exception: Exception): Resource<Nothing>()
    object Loading: Resource<Nothing>()
}
*/

import java.lang.Exception

sealed class Resource<out R> {
    data class Success<out R>(val result: R): Resource<R>()
    data class Error(val exceptionMessage: String): Resource<Nothing>()
    data class Failure(val exception: Exception): Resource<Nothing>()
    object Loading: Resource<Nothing>()
}