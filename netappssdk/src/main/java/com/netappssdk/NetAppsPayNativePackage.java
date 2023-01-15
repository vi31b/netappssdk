package com.netappssdk;

import androidx.annotation.NonNull;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.webview.RNNetAppsWebViewManager;
import com.webview.RNNetAppsWebViewModule;

public final class NetAppsPayNativePackage implements ReactPackage {

    @NonNull
    @Override
    public List<NativeModule> createNativeModules(@NonNull ReactApplicationContext reactContext) {
        List<NativeModule> modules = new ArrayList<>();
        modules.add(new NetappspaysdkModule(reactContext));
        modules.add(new RNUserAgentModule(reactContext));
        modules.add(new RNNetAppsWebViewModule(reactContext));
        modules.add(new NetAppsPaySheet());
        return modules;
    }

    @NonNull
    @Override
    public List<ViewManager> createViewManagers(@NonNull ReactApplicationContext reactContext) {
        return Arrays.asList(new RNNetAppsWebViewManager());
    }



}

