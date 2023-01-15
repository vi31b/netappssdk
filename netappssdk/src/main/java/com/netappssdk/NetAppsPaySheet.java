package com.netappssdk;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import org.json.JSONException;
import org.json.JSONObject;

public class NetAppsPaySheet extends ReactContextBaseJavaModule {
    public static final String NAME = "NetAppsPayCross";
    public static BottomSheet bottomSheet;
    public static String NetAppsPublicKey;
    FragmentManager supportFragmentM;
    public static PaymentSuccessCallback paymentSuccessCallback;
    public static PaymentFailedCallback paymentFailedCallback;

    public NetAppsPaySheet(String netAppsPublicKey, FragmentManager supportFragmentManager) {
        supportFragmentM = supportFragmentManager;
        NetAppsPublicKey = netAppsPublicKey;
        bottomSheet = new BottomSheet();
    }

    public NetAppsPaySheet() {
    }

    public void InitPayment(JSONObject payload) {
        Bundle args = new Bundle();
        bottomSheet.setCancelable(false);
//        bottomSheet.
        args.putString("publicKey", NetAppsPublicKey);
        args.putString("payload", payload.toString());

        bottomSheet.setArguments(args);
        bottomSheet.show(supportFragmentM, "react_root_view");
    }

    @ReactMethod
    public void close() {
        bottomSheet.dismiss();
    }

    public void setPaymentSuccessCallback(PaymentSuccessCallback callback) {
        paymentSuccessCallback = callback;
    }


    public interface PaymentSuccessCallback {
        void onPaymentSuccess(JSONObject obj);
    }

    public void setPaymentFailedCallback(PaymentFailedCallback callback) {
        paymentFailedCallback = callback;
    }

    public interface PaymentFailedCallback {
        void onPaymentFailed(JSONObject res);
    }

    @ReactMethod
    public void onSuccessful(String res) throws JSONException {
        if (paymentSuccessCallback != null) {
            JSONObject obj = new JSONObject(res);
            paymentSuccessCallback.onPaymentSuccess(obj);
        }
    }

    @ReactMethod
    public void onFailed(String res) throws JSONException {
        if (paymentFailedCallback != null) {
            JSONObject obj = new JSONObject(res);
            paymentFailedCallback.onPaymentFailed(obj);
        }
    }

    @NonNull
    @Override
    public String getName() {
        return NAME;
    }

}
