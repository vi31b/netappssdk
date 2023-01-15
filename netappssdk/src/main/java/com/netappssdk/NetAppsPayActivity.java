package com.netappssdk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactPackage;
import com.facebook.react.ReactRootView;
import com.facebook.react.common.LifecycleState;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.soloader.SoLoader;

import kotlin.jvm.internal.Intrinsics;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public final class NetAppsPayActivity extends Activity implements DefaultHardwareBackBtnHandler {
    private ReactRootView mReactRootView;
    private ReactInstanceManager mReactInstanceManager;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle initialProperties = new Bundle();
        Intent intent = this.getIntent();

        SoLoader.init(this, false);
        mReactRootView = new ReactRootView(this);

        List<ReactPackage> packages = new PackageList(this.getApplication()).getPackages();
        packages.add(new NetAppsPayNativePackage());
        this.mReactInstanceManager = ReactInstanceManager.builder()
                .setApplication(this.getApplication())
                .setCurrentActivity(this)
                .setBundleAssetName("index.android.bundle")
                .setJSMainModulePath("index")
                .addPackages(packages)
                .setUseDeveloperSupport(false)
                .setInitialLifecycleState(LifecycleState.RESUMED)
                .build();
        initialProperties.putString("payload", intent.getExtras().get("payload").toString());
        initialProperties.putString("publicKey", intent.getExtras().get("publicKey").toString());
        mReactRootView.startReactApplication(mReactInstanceManager, "netapppaysdk", initialProperties);
        this.setContentView(this.mReactRootView);
    }

    public void invokeDefaultOnBackPressed() {
        super.onBackPressed();
    }

    protected void onPause() {
        super.onPause();
        ReactInstanceManager var10000 = this.mReactInstanceManager;
        if (var10000 != null) {
            var10000.onHostPause(this);
        }

    }

    protected void onResume() {
        super.onResume();
        ReactInstanceManager var10000 = this.mReactInstanceManager;
        if (var10000 != null) {
            var10000.onHostResume(this, this);
        }

    }

    protected void onDestroy() {
        super.onDestroy();
        ReactInstanceManager var10000 = this.mReactInstanceManager;
        if (var10000 != null) {
            var10000.onHostDestroy(this);
        }

        ReactRootView var1 = this.mReactRootView;
        if (var1 != null) {
            var1.unmountReactApplication();
        }

    }

    public void onBackPressed() {
        if (this.mReactInstanceManager != null) {
            ReactInstanceManager var10000 = this.mReactInstanceManager;
            Intrinsics.checkNotNull(var10000);
            var10000.onBackPressed();
        } else {
            super.onBackPressed();
        }

    }

    public boolean onKeyUp(int keyCode, @NotNull KeyEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (keyCode == 82) {
            ReactInstanceManager var10000 = this.mReactInstanceManager;
            if (var10000 != null) {
                var10000.showDevOptionsDialog();
            }

            return true;
        } else {
            return super.onKeyUp(keyCode, event);
        }
    }
}
