package com.example.myapplication

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme

var mediaPlayer: MediaPlayer? = null
var mediaPlayerPrepared by mutableStateOf(false)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val url = "https://stream.hardcoreradio.nl:9000/hcr.ogg"
        mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            setDataSource(url)
            setOnPreparedListener {
                mediaPlayerPrepared = true
                it.start()
            }
            setOnErrorListener { _, _, _ ->
                mediaPlayerPrepared = false
                Toast.makeText(this@MainActivity, "Stream error", Toast.LENGTH_LONG).show()
                true
            }
            prepareAsync()
        }

        enableEdgeToEdge()
        setContent {
            val pagerState = rememberPagerState(pageCount = { 2 })
            val scope = rememberCoroutineScope()

            MyApplicationTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { MyTopBar() },
                    bottomBar = {
                        MyNavigationBar(
                            index = pagerState.currentPage,
                            handler = { page ->
                                scope.launch {
                                    pagerState.animateScrollToPage(page)
                                }
                            }
                        )
                    }
                ) { innerPadding ->
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier.padding(innerPadding)
                    ) { page ->
                        when (page) {
                            0 -> MyHomePage()
                            1 -> MySettingsPage()
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        mediaPlayer?.release()
        mediaPlayer = null
        super.onDestroy()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopBar() {
    TopAppBar(
        navigationIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "Launcher Foreground",
                tint = Color.Unspecified,
                modifier = Modifier
                    .padding(start = 4.dp)
            )
        },
        title = {
            Text(
                stringResource(R.string.app_name),
                fontWeight = FontWeight.Bold
            )
        }
    )
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

@Composable
fun MyHomePage() {
    if (mediaPlayerPrepared) {
        Text("Playing stream")
    } else {
        Text("Preparing stream")
    }
}

@Composable
fun MySettingsPage() {
    Text("Settings page")
}