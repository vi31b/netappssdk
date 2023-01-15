package com.webview

import com.facebook.react.ReactPackage
import com.facebook.react.bridge.ReactApplicationContext


class RNNetAppsWebViewPackage : ReactPackage {
  override fun createNativeModules(reactContext: ReactApplicationContext) = listOf(
    RNNetAppsWebViewModule(reactContext)
  )

  override fun createViewManagers(reactContext: ReactApplicationContext) = listOf(
    RNNetAppsWebViewManager()
  )
}
