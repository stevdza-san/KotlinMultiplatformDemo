package com.stevdza.san.androidapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.stevdza.san.androidapp.ui.theme.KMPDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KMPDemoTheme {
                MainScreen()
            }
        }
    }
}