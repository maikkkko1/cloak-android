package com.maikkkko1.cloak

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import java.io.InvalidClassException

/**
 * The wrapper class responsible for handling the biometric request and validation.
 *
 * @param context The calling composable current local context.
 * @param biometricStatus The [BiometricManager] biometric status.
 */
class Cloak(private val context: Context, private val biometricStatus: Int) {
    fun authenticateBiometric(
        prompt: CloakAuthPrompt = CloakAuthPrompt(),
        onError: (errorCode: Int, errorMessage: CharSequence) -> Unit,
        onFailed: () -> Unit,
        onSuccess: (result: BiometricPrompt.AuthenticationResult) -> Unit
    ) {
        require(value = getBiometricStatus().isAvailable) { mapBiometricStatus() }

        val activity: FragmentActivity = mapActivity()
        val executor = ContextCompat.getMainExecutor(context)
        val biometricPrompt = BiometricPrompt(
            activity,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    onError(errorCode, errString)
                }

                @RequiresApi(Build.VERSION_CODES.R)
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    onSuccess(result)
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    onFailed()
                }
            }
        )

        biometricPrompt.authenticate(prompt.build(context = context))
    }

    fun getBiometricStatus() = CloakBiometricStatus(
        isAvailable = biometricStatus == BiometricManager.BIOMETRIC_SUCCESS,
        message = mapBiometricStatus()
    )

    private fun mapBiometricStatus() = when (biometricStatus) {
        BiometricManager.BIOMETRIC_SUCCESS -> {
            "BIOMETRIC_SUCCESS: Biometric features are available."
        }

        BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
            "BIOMETRIC_ERROR_NO_HARDWARE: No biometric features available on this device."
        }

        BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
            "BIOMETRIC_ERROR_HW_UNAVAILABLE: Biometric features are currently unavailable."
        }

        BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED -> {
            "BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED: Biometric features available but a security vulnerability has been discovered."
        }

        BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED -> {
            "BIOMETRIC_ERROR_UNSUPPORTED: Biometric features are currently unavailable because the specified options are incompatible with the current Android version."
        }

        BiometricManager.BIOMETRIC_STATUS_UNKNOWN -> {
            "BIOMETRIC_STATUS_UNKNOWN: Unable to determine whether the user can authenticate using biometrics."
        }

        BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
            "BIOMETRIC_ERROR_NONE_ENROLLED: The user can't authenticate because no biometric or device credential is enrolled."
        }

        else -> "Unknown status"
    }

    @VisibleForTesting
    fun mapActivity(): FragmentActivity {
        if (context is AppCompatActivity || context is FragmentActivity) {
            return context as FragmentActivity
        }

        throw InvalidClassException(
            "Invalid Activity type. Please use AppCompatActivity or FragmentActivity " +
                    "instead of ComponentActivity. ComponentActivity is not supported in this context."
        )
    }
}