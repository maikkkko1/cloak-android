package com.maikkkko1.cloak

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.FragmentActivity
import com.maikkkko1.cloak.ui.theme.CloakTheme

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CloakTheme {
                val context = LocalContext.current
                val cloak = rememberCloakState()

                fun authenticateBiometric() {
                    val biometricStatus = cloak.getBiometricStatus()

                    if (!biometricStatus.isAvailable) {
                        return Toast.makeText(
                            context,
                            biometricStatus.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    cloak.authenticateBiometric(
                        onError = { errorCode, errorMessage ->
                            Toast.makeText(
                                context,
                                "Biometric error. Code: [$errorCode], message: [$errorMessage]",
                                Toast.LENGTH_LONG
                            ).show()
                        },
                        onFailed = {
                            Toast.makeText(
                                context,
                                "Biometric failed.",
                                Toast.LENGTH_LONG
                            ).show()
                        },
                        onSuccess = {
                            Toast.makeText(
                                context,
                                "Biometric success. Result: [$it]",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    )
                }

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = {
                            authenticateBiometric()
                        }
                    ) {
                        Text(text = "Authenticate biometric")
                    }
                }
            }
        }
    }
}