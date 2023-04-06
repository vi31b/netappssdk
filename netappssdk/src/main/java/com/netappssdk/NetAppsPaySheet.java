package com.netappssdk;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONObject;
import java.util.Iterator;
import pl.droidsonroids.gif.GifDrawable;

public class NetAppsPaySheet extends AppCompatActivity {

    private Dialog loadingDialog;
    private static NetAppsPayActionSheetListener mListener;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WebView webView = new WebView(this);
        webView.setBackgroundColor(Color.TRANSPARENT);
        webView.getSettings().setJavaScriptEnabled(true);
        setContentView(webView);
        webView.addJavascriptInterface(new WebAppInterface(), "JSBridge");
        webView.setWebViewClient(new CustomWebViewClient());
        webView.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
        loadingDialog = createLoadingDialog();

        Uri baseUrl = Uri.parse("https://cdn.netapps.ng/pay_m/");
        Uri.Builder uriBuilder = baseUrl.buildUpon();
        for (String key : getIntent().getExtras().keySet()) {
            uriBuilder.appendQueryParameter(key, getIntent().getStringExtra(key));
        }
        webView.loadUrl(uriBuilder.toString());
    }


    public interface NetAppsPayActionSheetListener {
        void onSuccess(String message);
        void onError(String message);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mListener = null;
    }

    private Dialog createLoadingDialog() {
        Dialog dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.setContentView(R.layout.loading_dialog);
        dialog.setCancelable(false);

        ImageView loadingImageView = dialog.findViewById(R.id.loading_image);
        @SuppressLint("UseCompatLoadingForDrawables") Drawable loadingDrawable = getResources().getDrawable(R.drawable.loader);
        if (loadingDrawable instanceof AnimatedVectorDrawable) {
            AnimatedVectorDrawable avd = (AnimatedVectorDrawable) loadingDrawable;
            loadingImageView.setImageDrawable(avd);
            avd.start();
        } else if (loadingDrawable instanceof GifDrawable) {
            GifDrawable gifDrawable = (GifDrawable) loadingDrawable;
            loadingImageView.setImageDrawable(gifDrawable);
            gifDrawable.start();
        } else {
            loadingImageView.setImageDrawable(loadingDrawable);
        }

        return dialog;
    }

    private class CustomWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            String urlString = request.getUrl().toString().toLowerCase();
            if (urlString.contains("https://cipa.unifiedpaymentsnigeria.com/result")) {
                Intent intent = new Intent(Intent.ACTION_VIEW, request.getUrl());
                startActivity(intent);
                return true;
            }
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            loadingDialog.show();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            loadingDialog.dismiss();
        }
    }

    public class WebAppInterface {
        @JavascriptInterface
        public void handleMessage(String message) {
            if (message.contains("SUCCESS")) {
                if (mListener != null) {
                    mListener.onSuccess(message);
                }
                return;
            }
            if (message.contains("FAILED")) {
                if (mListener != null) {
                    mListener.onError(message);
                }
                return;
            }
            if (message.contains("close")) {
                finish();
            }
        }
    }

    public static void start(Context context, JSONObject json, NetAppsPayActionSheetListener listener) {
        Intent intent = new Intent(context, NetAppsPaySheet.class);
        Iterator<String> keys = json.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            intent.putExtra(key, json.optString(key));
        }
        if (context instanceof NetAppsPayActionSheetListener) {
            mListener = (NetAppsPayActionSheetListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement NetAppsPayActionSheetListener");
        }
        context.startActivity(intent);
    }

}
