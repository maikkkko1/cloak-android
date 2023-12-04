package com.maikkkko1.cloak

import androidx.biometric.BiometricManager
import org.junit.Assert
import org.junit.Test

class CloakAuthPromptTest {
    @Test
    fun `build PromptInfo with default params`() {
        val cloakAuthPrompt = CloakAuthPrompt()

        Assert.assertEquals(cloakAuthPrompt.allowedAuthenticators, BiometricManager.Authenticators.BIOMETRIC_STRONG)
        Assert.assertEquals(cloakAuthPrompt.title, R.string.auth_prompt_title)
        Assert.assertEquals(cloakAuthPrompt.subTitle, R.string.auth_prompt_subtitle)
        Assert.assertEquals(cloakAuthPrompt.negativeButtonText, R.string.auth_prompt_negative_button_title)
    }
}