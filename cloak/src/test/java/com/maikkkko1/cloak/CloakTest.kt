package com.maikkkko1.cloak

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.fragment.app.FragmentActivity
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import java.io.InvalidClassException

class CloakTest {
    private val context: Context = Mockito.mock()

    @Test(expected = IllegalArgumentException::class)
    fun authenticateBiometric_biometricIsNotAvailable_throwException() {
        val invalidBiometricStatus = BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE
        val cloak = Cloak(context = context, biometricStatus = invalidBiometricStatus)

        cloak.authenticateBiometric(
            onSuccess = {},
            onFailed = {},
            onError = { _, _ -> }
        )
    }

    @Test
    fun getBiometricStatus_statusIsBiometricSuccess() {
        val invalidBiometricStatus = BiometricManager.BIOMETRIC_SUCCESS
        val cloak = Cloak(context = context, biometricStatus = invalidBiometricStatus)
        val biometricStatus = cloak.getBiometricStatus()

        Assert.assertTrue(biometricStatus.isAvailable)
        Assert.assertEquals(
            biometricStatus.message,
            "BIOMETRIC_SUCCESS: Biometric features are available."
        )
    }

    @Test
    fun getBiometricStatus_statusIsBiometricNoHardware() {
        val invalidBiometricStatus = BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE
        val cloak = Cloak(context = context, biometricStatus = invalidBiometricStatus)
        val biometricStatus = cloak.getBiometricStatus()

        Assert.assertFalse(biometricStatus.isAvailable)
        Assert.assertEquals(
            biometricStatus.message,
            "BIOMETRIC_ERROR_NO_HARDWARE: No biometric features available on this device."
        )
    }

    @Test
    fun getBiometricStatus_statusIsBiometricHwUnavailable() {
        val invalidBiometricStatus = BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE
        val cloak = Cloak(context = context, biometricStatus = invalidBiometricStatus)
        val biometricStatus = cloak.getBiometricStatus()

        Assert.assertFalse(biometricStatus.isAvailable)
        Assert.assertEquals(
            biometricStatus.message,
            "BIOMETRIC_ERROR_HW_UNAVAILABLE: Biometric features are currently unavailable."
        )
    }

    @Test
    fun getBiometricStatus_statusIsBiometricSecurityUpdateRequired() {
        val invalidBiometricStatus = BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED
        val cloak = Cloak(context = context, biometricStatus = invalidBiometricStatus)
        val biometricStatus = cloak.getBiometricStatus()

        Assert.assertFalse(biometricStatus.isAvailable)
        Assert.assertEquals(
            biometricStatus.message,
            "BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED: Biometric features available but a security vulnerability has been discovered."
        )
    }

    @Test
    fun getBiometricStatus_statusIsBiometricUnsupported() {
        val invalidBiometricStatus = BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED
        val cloak = Cloak(context = context, biometricStatus = invalidBiometricStatus)
        val biometricStatus = cloak.getBiometricStatus()

        Assert.assertFalse(biometricStatus.isAvailable)
        Assert.assertEquals(
            biometricStatus.message,
            "BIOMETRIC_ERROR_UNSUPPORTED: Biometric features are currently unavailable because the specified options are incompatible with the current Android version."
        )
    }

    @Test
    fun getBiometricStatus_statusIsBiometricUnknown() {
        val invalidBiometricStatus = BiometricManager.BIOMETRIC_STATUS_UNKNOWN
        val cloak = Cloak(context = context, biometricStatus = invalidBiometricStatus)
        val biometricStatus = cloak.getBiometricStatus()

        Assert.assertFalse(biometricStatus.isAvailable)
        Assert.assertEquals(
            biometricStatus.message,
            "BIOMETRIC_STATUS_UNKNOWN: Unable to determine whether the user can authenticate using biometrics."
        )
    }

    @Test
    fun getBiometricStatus_statusIsBiometricNoneEnrolled() {
        val invalidBiometricStatus = BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED
        val cloak = Cloak(context = context, biometricStatus = invalidBiometricStatus)
        val biometricStatus = cloak.getBiometricStatus()

        Assert.assertFalse(biometricStatus.isAvailable)
        Assert.assertEquals(
            biometricStatus.message,
            "BIOMETRIC_ERROR_NONE_ENROLLED: The user can't authenticate because no biometric or device credential is enrolled."
        )
    }

    @Test
    fun getBiometricStatus_statusIsUnknownStatus() {
        val invalidBiometricStatus = 999
        val cloak = Cloak(context = context, biometricStatus = invalidBiometricStatus)
        val biometricStatus = cloak.getBiometricStatus()

        Assert.assertFalse(biometricStatus.isAvailable)
        Assert.assertEquals(
            biometricStatus.message,
            "Unknown status"
        )
    }

    @Test
    fun mapActivity_activityIsAppComponentActivity_castIsSuccessful() {
        val activityContext: AppCompatActivity = Mockito.mock()

        val cloak = Cloak(context = activityContext, biometricStatus = BiometricManager.BIOMETRIC_SUCCESS)
        val activity: FragmentActivity = cloak.mapActivity()

        Assert.assertTrue(activity is FragmentActivity)
    }

    @Test
    fun mapActivity_activityIsFragmentActivity_castIsSuccessful() {
        val activityContext: FragmentActivity = Mockito.mock()

        val cloak = Cloak(context = activityContext, biometricStatus = BiometricManager.BIOMETRIC_SUCCESS)
        val activity: FragmentActivity = cloak.mapActivity()

        Assert.assertTrue(activity is FragmentActivity)
    }

    @Test(expected = InvalidClassException::class)
    fun mapActivity_activityIsComponentActivity_throwException() {
        val activityContext: ComponentActivity = Mockito.mock()

        val cloak = Cloak(context = activityContext, biometricStatus = BiometricManager.BIOMETRIC_SUCCESS)
        cloak.mapActivity()
    }
}