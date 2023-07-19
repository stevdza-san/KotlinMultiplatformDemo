package com.example.kmpdemo.api

import com.example.kmpdemo.ApiResponse
import com.example.kmpdemo.Person
import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.http.setBodyText
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private val people = listOf(
    Person(name = "Stefan", age = 25),
    Person(name = "John", age = 24),
    Person(name = "Mary", age = 23),
    Person(name = "Ana", age = 22),
)

@Api
suspend fun getPeople(context: ApiContext) {
    try {
        val number = context.req.params.getValue("count").toInt()
        context.res.setBodyText(
            Json.encodeToString<ApiResponse>(
                ApiResponse.Success(data = people.take(number))
            )
        )
    } catch (e: Exception) {
        context.res.setBodyText(
            Json.encodeToString<ApiResponse>(
                ApiResponse.Error(errorMessage = e.message.toString())
            )
        )
    }
}