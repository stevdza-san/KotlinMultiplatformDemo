package com.stevdza.san.androidapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.kmpdemo.ApiResponse
import com.example.kmpdemo.parseAsString
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val scope = rememberCoroutineScope()
    var value by remember { mutableStateOf("") }
    var apiResponseText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            modifier = Modifier.padding(bottom = 8.dp),
            value = value,
            placeholder = {
                Text(text = "Enter the number")
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = { value = it }
        )
        Button(
            modifier = Modifier.padding(bottom = 12.dp),
            onClick = {
                scope.launch {
                    val apiResponse = fetchData(
                        count = if(value.isNumeric()) value.toInt() else 0
                    )
                    apiResponseText = apiResponse.parseAsString()
                }
            }) {
            Text(text = "Fetch")
        }
        Text(text = apiResponseText)
    }
}

fun String.isNumeric(): Boolean {
    return this.matches(Regex("-?\\d+"))
}

private suspend fun fetchData(count: Int): ApiResponse {
    val apiService = RetrofitClient.apiService
    return apiService.getResponse(count = count)
}