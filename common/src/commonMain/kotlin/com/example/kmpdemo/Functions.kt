package com.example.kmpdemo

fun ApiResponse.parseAsString(): String {
    return when (this) {
        is ApiResponse.Idle -> {
            ""
        }

        is ApiResponse.Success -> {
            this.data.joinToString("\n") {
                "Name: ${it.name} - Age: ${it.age}"
            }
        }

        is ApiResponse.Error -> {
            this.errorMessage
        }
    }
}