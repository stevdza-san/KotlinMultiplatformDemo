package com.example.kmpdemo

import kotlinx.serialization.Serializable

@Serializable
data class Person(
    val name: String,
    val age: Int
)