package com.example.nfcgate.core.di

import android.content.Context
import com.example.nfcgate.core.data.FirestoreManager
import com.example.nfcgate.core.security.KeyStoreManager

class AppContainer(private val context: Context) {

    val firestoreManager: FirestoreManager by lazy {
        FirestoreManager()
    }

    val keyStoreManager: KeyStoreManager by lazy {
        KeyStoreManager()
    }
}
