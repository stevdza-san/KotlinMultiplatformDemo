package com.example.kmpdemo.pages

import androidx.compose.runtime.*
import com.example.kmpdemo.ApiResponse
import com.example.kmpdemo.parseAsString
import com.varabyte.kobweb.browser.api
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.id
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.text.SpanText
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Br
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Input
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text
import org.w3c.dom.HTMLInputElement

@Page
@Composable
fun HomePage() {
    val scope = rememberCoroutineScope()
    var apiResponseText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().margin(top = 200.px),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SpanText(
            modifier = Modifier
                .margin(bottom = 8.px)
                .fontFamily("Roboto")
                .fontSize(18.px)
                .fontWeight(FontWeight.Medium),
            text = "How many people you want to get?"
        )
        Input(
            type = InputType.Number,
            attrs = Modifier
                .id("countInput")
                .margin(bottom = 16.px)
                .fontFamily("Roboto")
                .fontSize(14.px)
                .toAttrs {
                    attr("placeholder", "Enter the number")
                }
        )
        Button(
            attrs = Modifier
                .margin(bottom = 16.px)
                .fontFamily("Roboto")
                .fontSize(16.px)
                .onClick {
                    scope.launch {
                        val apiResponse = fetchData()
                        apiResponseText = apiResponse.parseAsString()
                    }
                }
                .toAttrs()
        ) {
            SpanText(text = "Fetch")
        }
        P(
            attrs = Modifier
                .fontFamily("Roboto")
                .fontSize(16.px)
                .toAttrs()
        ) {
            apiResponseText.split("\n").forEach {
                Text(value = it)
                Br()
            }
        }
    }
}

private suspend fun fetchData(): ApiResponse {
    val inputText = (document.getElementById("countInput") as HTMLInputElement).value
    val number = if(inputText.isEmpty()) 0 else inputText.toInt()
    val result = window.api.tryGet(apiPath = "getpeople?count=$number")?.decodeToString()
    return Json.decodeFromString(result.toString())
}