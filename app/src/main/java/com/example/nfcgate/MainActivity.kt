package com.example.nfcgate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.nfcgate.theme.NFCGateTheme
import com.example.nfcgate.core.di.AppContainer

class MainActivity : ComponentActivity() {
    private lateinit var appContainer: AppContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        enableEdgeToEdge()
        appContainer = (application as NFCGateApplication).container

        setContent {
            NFCGateTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    androidx.compose.runtime.CompositionLocalProvider(
                        com.example.nfcgate.core.di.LocalAppContainer provides appContainer
                    ) {
                        MainNavigation()
                    }
                }
            }
        }
    }
}
