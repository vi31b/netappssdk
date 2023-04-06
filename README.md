# README

## TestNetAppPay

This Android application demonstrates the integration of the `NetAppsPaySheet` library for handling payments. The application includes a button that triggers the payment process using the `NetAppsPaySheet` library with predefined payment parameters.

### Table of Contents

- [Overview](#overview)
- [Usage](#usage)
- [Methods](#methods)

### Overview

The `TestNetAppPay` class is an `AppCompatActivity` that implements the `NetAppsPaySheet.NetAppsPayActionSheetListener` interface. The class is responsible for initializing the payment process using the `NetAppsPaySheet` library, and handling the success and error callbacks.

### Usage

Go to gradle.properties and add the line below:

 ```gradle
android.enableJetifier=true
```

Add  `https://jitpack.io` to your repository example below:
 ```java
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
```

To get started with the Payment SDK, you will need to:




1. Obtain an API key from NetAppsPay. This can be done by signing up for a developer account at NetAppsPay Developer Portal.
2. Install the Payment SDK in your Android project. This can be done by adding the following dependency to your build.gradle file:
3. Import the necessary packages and classes.
4. Extend the `AppCompatActivity` and implement the `NetAppsPaySheet.NetAppsPayActionSheetListener` interface.
5. Override the `onCreate` method to initialize the view and set up the button click listener.
6. Create a new `JSONObject` with the required payment parameters.
7. Call `NetAppsPaySheet.start()` with the required parameters.
8. Override the `onSuccess` and `onError` methods to handle the success and error callbacks, respectively.

### Methods

- `onCreate(@Nullable Bundle savedInstanceState)`: Initializes the view, sets up the button click listener, and triggers the payment process using the `NetAppsPaySheet` library.
- `onSuccess(String message)`: Handles the success callback, logs the message.
- `onError(String message)`: Handles the error callback, logs the message.

- [Dependencies](#dependencies)
- [Integration Steps](#integration-steps)

### Dependencies

In order to use the `NetAppsPaySheet` library in your project, ensure that the required dependencies are added to your project's `build.gradle` file.
 
```gradle
implementation 'com.github.vi31b:netappssdk:0.0.7'
```

### Integration Steps

1. Add the required dependencies to your project's `build.gradle` file.
2. Create a new Android Activity class (e.g., `TestNetAppPay`) that extends `AppCompatActivity` and implements the `NetAppsPaySheet.NetAppsPayActionSheetListener` interface.
3. In the `onCreate` method, initialize the view and set up the button click listener.
4. In the button click listener, create a new `JSONObject` with the required payment parameters.
5. Call `NetAppsPaySheet.start()` with the context, payment parameters, and the listener.
6. Override the `onSuccess` and `onError` methods to handle the success and error callbacks, respectively.



Example:

```java
public class TestNetAppPay extends AppCompatActivity implements NetAppsPaySheet.NetAppsPayActionSheetListener {
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // ...
    }

    @Override
    public void onSuccess(String message) {
        // Handle success
    }

    @Override
    public void onError(String message) {
        // Handle error
    }
}
```

Complete Code: 
```java
package com.netapps.com;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

import com.netappssdk.NetAppsPaySheet;


public class TestNetAppPay extends AppCompatActivity implements NetAppsPaySheet.NetAppsPayActionSheetListener {
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        Button btnGoRNScreen = this.findViewById(R.id.btnGoRNScreen);
        btnGoRNScreen.setOnClickListener(it -> {
            try {
                JSONObject obj = new JSONObject();
                obj.put("public_key","b1108bfb3e2542b287162ef27da838f9");
                obj.put("currency", "NGN");
                obj.put("amount", "1000");
                obj.put("phone", "***080");
                obj.put("email", "example@gmail.com");
                obj.put("fullname", "Nwoko Ndubueze");
                obj.put("narration", "Testing");
                obj.put("tx_ref", "12o9876eertyuiolkjkvghjkjhjjhklhgf344sdsd");
                obj.put("paymentChannels", "card,ussd,transfer,payatitude");

                NetAppsPaySheet.start(this, obj, this);


            } catch (Exception e) {
                Log.d("Law", e.getMessage());
            }
        });
    }

    @Override
    public void onSuccess(String message) {
        Log.d("Develop", "onSuccess: ");
    }

    @Override
    public void onError(String message) {
        Log.d("develop", "onError: ");
    }
}
```