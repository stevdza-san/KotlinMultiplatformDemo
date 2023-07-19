package com.example.kmpdemo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class ApiResponse {
    @Serializable
    @SerialName("idle")
    object Idle: ApiResponse()
    @Serializable
    @SerialName("success")
    data class Success(val data: List<Person>): ApiResponse()
    @Serializable
    @SerialName("error")
    data class Error(val errorMessage: String): ApiResponse()
}
