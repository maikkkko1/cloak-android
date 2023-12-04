package com.maikkkko1.cloak

import android.content.Context
import androidx.annotation.StringRes
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt

/**
 * The biometric prompt dialog configuration.
 *
 * @param allowedAuthenticators The [BiometricManager.Authenticators] to be used.
 * @param title The prompt title.
 * @param subTitle The prompt sub-title.
 * @param negativeButtonText The prompt negative button text.
 */
data class CloakAuthPrompt(
    val allowedAuthenticators: Int = BiometricManager.Authenticators.BIOMETRIC_STRONG,
    @StringRes val title: Int = R.string.auth_prompt_title,
    @StringRes val subTitle: Int = R.string.auth_prompt_subtitle,
    @StringRes val negativeButtonText: Int = R.string.auth_prompt_negative_button_title
) {
    fun build(context: Context): BiometricPrompt.PromptInfo = BiometricPrompt.PromptInfo.Builder()
        .setAllowedAuthenticators(allowedAuthenticators)
        .setTitle(context.getString(title))
        .setSubtitle(context.getString(subTitle))
        .setNegativeButtonText(context.getText(negativeButtonText))
        .build()
}
