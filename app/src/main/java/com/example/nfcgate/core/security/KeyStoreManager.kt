package com.example.nfcgate.core.security

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.security.KeyPairGenerator
import java.security.KeyStore

class KeyStoreManager {
    private val keyStore = KeyStore.getInstance("AndroidKeyStore").apply { load(null) }
    private val ALIAS = "nfcgate_device_key"

    fun generateOrGetKeyPair(): String {
        if (!keyStore.containsAlias(ALIAS)) {
            val keyPairGenerator = KeyPairGenerator.getInstance(
                KeyProperties.KEY_ALGORITHM_EC,
                "AndroidKeyStore"
            )
            val parameterSpec = KeyGenParameterSpec.Builder(
                ALIAS,
                KeyProperties.PURPOSE_SIGN or KeyProperties.PURPOSE_VERIFY
            ).setDigests(KeyProperties.DIGEST_SHA256)
                .build()

            keyPairGenerator.initialize(parameterSpec)
            keyPairGenerator.generateKeyPair()
        }

        val publicKey = keyStore.getCertificate(ALIAS).publicKey
        return Base64.encodeToString(publicKey.encoded, Base64.NO_WRAP)
    }
}
