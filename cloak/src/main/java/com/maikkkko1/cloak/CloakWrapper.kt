package com.maikkkko1.cloak

import androidx.biometric.BiometricManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

/**
 * A wrapper to be called from a Composable that returns a [Cloak] instance.
 */
@Composable
fun rememberCloakState(): Cloak {
    val context = LocalContext.current
    val biometricManager = remember { BiometricManager.from(context) }

    val biometricStatus = remember {
        biometricManager.canAuthenticate(
            BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL
        )
    }

    return remember { Cloak(context = context, biometricStatus = biometricStatus) }
}