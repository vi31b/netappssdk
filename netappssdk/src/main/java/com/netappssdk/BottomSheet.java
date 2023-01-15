package com.netappssdk;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.ReactPackage;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;
import com.facebook.react.common.LifecycleState;
import com.facebook.soloader.SoLoader;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

public class BottomSheet extends BottomSheetDialogFragment {
    private ReactRootView mReactRootView;
    private ReactInstanceManager mReactInstanceManager;

    public BottomSheet() {

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SoLoader.init(getActivity(), false);
        mReactRootView = new ReactRootView(getActivity());
        List<ReactPackage> packages = new PackageList(requireActivity().getApplication()).getPackages();
        packages.add(new NetAppsPayNativePackage());
        mReactInstanceManager = ReactInstanceManager.builder()
                .setApplication(requireActivity().getApplication())
                .setCurrentActivity(getActivity())
                .setBundleAssetName("index.android.bundle")
                .setJSMainModulePath("index")
                .addPackages(packages)
                .setUseDeveloperSupport(false)
                .setInitialLifecycleState(LifecycleState.RESUMED)
                .build();

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle initialProperties = new Bundle();
        if (getArguments() != null) {
            initialProperties.putString("payload", getArguments().get("payload").toString());
            initialProperties.putString("publicKey", getArguments().get("publicKey").toString());
        }
        mReactRootView.startReactApplication(mReactInstanceManager, "netapppaysdk", initialProperties);

        return mReactRootView;
    }


}
