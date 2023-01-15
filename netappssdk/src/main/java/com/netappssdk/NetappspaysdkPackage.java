package com.netappssdk;

import androidx.annotation.NonNull;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
import com.webview.RNNetAppsWebViewManager;
import com.webview.RNNetAppsWebViewModule;

import java.util.ArrayList;
import java.util.List;

public class NetappspaysdkPackage implements ReactPackage {
  @NonNull
  @Override
  public List<NativeModule> createNativeModules(@NonNull ReactApplicationContext reactContext) {
    List<NativeModule> modules = new ArrayList<>();
    modules.add(new NetappspaysdkModule(reactContext));
    modules.add(new RNUserAgentModule(reactContext));
    modules.add(new RNNetAppsWebViewModule(reactContext));
    return modules;
  }

  @NonNull
  @Override
  public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
    List<ViewManager> modules = new ArrayList<>();
    modules.add(new RNNetAppsWebViewManager());
    return modules;
  }
}
