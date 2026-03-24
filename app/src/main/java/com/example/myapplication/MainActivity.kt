package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableIntStateOf
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var index by remember { mutableIntStateOf(0) }

            MyApplicationTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        MyNavigationBar(
                            index,
                            handler = { index = it }
                        )
                    }
                ) { innerPadding ->
                    Text(
                        text = "Page index $index",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun MyNavigationBar(index: Int, handler: (Int) -> Unit) {
    NavigationBar {
        NavigationBarItem(
            selected = index == 0,
            onClick = { handler(0) },
            icon = { MyHomeIcon(index == 0) },
            label = { Text("Home") }
        )
        NavigationBarItem(
            selected = index == 1,
            onClick = { handler(1) },
            icon = { MySettingsIcon(index == 1) },
            label = { Text("Settings") }
        )
    }
}

@Composable
fun MyHomeIcon(filled: Boolean) {
    Icon(
        imageVector = if (filled) Icons.Filled.Home else Icons.Outlined.Home,
        contentDescription = "Home"
    )
}

@Composable
fun MySettingsIcon(filled: Boolean) {
    Icon(
        imageVector = if (filled) Icons.Filled.Settings else Icons.Outlined.Settings,
        contentDescription = "Settings"
    )
}