package com.switchcodeur.hardm3.pages

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import me.zhanghai.compose.preference.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsPage() {
    ProvidePreferenceLocals {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            textFieldPreference(
                key = "stream_url",
                defaultValue = "https://stream.hardcoreradio.nl:9000/hcr.ogg",
                title = { Text(text = "Stream URL") },
                textToValue = { it },
                summary = { Text(text = it) },
            )
        }
    }
}