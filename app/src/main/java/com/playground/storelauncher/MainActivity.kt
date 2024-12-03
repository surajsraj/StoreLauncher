package com.playground.storelauncher

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.playground.storelauncher.ui.theme.StoreLauncherTheme

class MainActivity : ComponentActivity() {

    private val packageName = "com.google.android.apps.youtube.music"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val launcher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                // Handle the result if needed
            }

        setContent {
            StoreLauncherTheme {
                val modifier = Modifier
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        modifier = modifier,
                        onClick = {
                            AppStoreHelper.launchSupportedAppStore(
                                packageName,
                                this@MainActivity,
                                launcher
                            )
                        }) {
                        Text(text = "Go to App Store")
                    }
                }
            }
        }
    }
}


