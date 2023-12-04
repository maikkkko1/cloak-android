# Cloak: Jetpack Compose Biometric Authentication
Cloak is a lightweight Jetpack Compose library designed to simplify the integration of biometric authentication into your Android applications. With Cloak, you can easily enhance the security of your app using fingerprint and face recognition features, providing a seamless and customizable user experience.

# Features
* Jetpack Compose Integration: Cloak seamlessly integrates with Jetpack Compose, allowing you to incorporate biometric authentication into your modern Android UIs effortlessly.
* Fingerprint and Face Recognition: Cloak supports both fingerprint and face recognition, offering flexibility in choosing the biometric authentication method that best suits your application's requirements.
* Intuitive API: Cloak provides an intuitive API that abstracts the complexities of the Android BiometricPrompt API, making it easy to implement secure biometric authentication with just a few lines of code.

# Demo (source)
<img src="https://github.com/maikkkko1/cloak-android/assets/40298292/4bbe7d46-f21e-4013-8ff0-e91385ff3e50" width="400">

# Installation
To get started with Cloak, add the following dependency to your app module's build.gradle file:
```gradle
implementation 'com.example:cloak:1.0.0'
```

# Usage
To use Cloak in your Jetpack Compose project, follow these simple steps:

1. Initialize Cloak in your Composable:
```kotlin
val cloak = rememberCloakState()
```
2. Check if biometric authentication is available on the device.
```kotlin
/**
 * Retrieves a CloakBiometricStatus instance.
 *
 * - `isAvailable`: Indicates whether biometric authentication is both available and enabled on this device.
 * - `message`: Provides a status message, allowing you to understand the reasons behind any unavailability.
 */
val (isAvailable, message) = cloak.getBiometricStatus()

// It's crucial not to proceed with authentication if biometric is unavailable, as it will result in an exception.
if (!isAvailable) {
    return Toast.makeText(
        context,
        message,
        Toast.LENGTH_LONG
    ).show()
}
```
3. Trigger the biometric authentication process by calling the Cloak authentication method. Ensure to handle the callbacks appropriately. Typically, this call is placed within a Button's `onClick` event.
```kotlin
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
```
4. Optionally, customize the contents of the biometric prompt. This allows you to tailor the appearance and messaging of the biometric authentication dialog.
```kotlin
val cloakAuthPrompt = CloakAuthPrompt(
  title = R.string.your_title,
  subTitle = R.string.your_subtitle,
  negativeButtonText = R.string.your_negative_button_texct
)

cloak.authenticateBiometric(
  prompt = cloakAuthPrompt,
  ...
)
```

# Licence

Licensed under Apache License, Version 2.0 here.
