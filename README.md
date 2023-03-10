## Payment SDK
### Introduction

The Payment SDK allows you to easily integrate payment functionality into your Android app. With just a few lines of code, you can offer your users a variety of payment options, including card payments, USSD payments, bank transfers, and more.
## Getting Started

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

1) Obtain an API key from NetAppsPay. This can be done by signing up for a developer account at NetAppsPay Developer Portal.

2) Install the Payment SDK in your Android project. This can be done by adding the following dependency to your build.gradle file:

```gradle
implementation 'com.github.vi31b:netappssdk:0.0.6'
```

Add the the following to your AndroidManifest.xml file:

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

Initialize the Payment SDK in your Activity . In the example below, we initialize the Payment SDK and set up button click listeners to initiate a payment and handle success or failure callbacks:

```java
NetAppsPaySheet Payment = new NetAppsPaySheet("YOUR_API_KEY", getSupportFragmentManager());

        btnGoRNScreen.setOnClickListener(it -> {
        try {
            JSONObject obj = new JSONObject();
            obj.put("currency", "NGN");
            obj.put("amount", "10");
            obj.put("phone", "080****");
            obj.put("email", "email@example.com");
            obj.put("fullname", "Nwoko Ndubueze");
            obj.put("narration", "Testing");
            obj.put("tx_ref", "12o9876eertyuiolkjkvghjkjhjjhklhgf344sdsd");
            obj.put("paymentChannels", "card,ussd,transfer,payatitude");

        Payment.setPaymentFailedCallback(res -> {
            Log.d("JAVAres", res.toString());
        });

        Payment.setPaymentSuccessCallback(res -> {
             Log.d("JAVAres", res.toString());
        });
        
        Payment.InitPayment(obj);
        
        } catch (Exception e) {
             Log.d("Law", e.getMessage());
        }
        });

```

## Payment Parameters

The Payment SDK requires a JSONObject to be passed as a parameter when initiating a payment. This JSONObject should contain the following parameters:

* currency (string): The currency of the payment (e.g. "NGN").
* amount (string): The amount of the payment (e.g. "10").
*  phone (string): The phone number of the user making the payment.
* email (string): The email address of the user making the payment.
* fullname (string): The full name of the user making the payment
* narration (string): A description of the payment.
* tx_ref (string): A unique reference for the payment. This should be generated by your app and can be used to identify the payment later on.
* paymentChannels (string): A comma-separated list of payment channels to enable for the payment (e.g. "card,ussd,transfer,payatitude").

Handling Payment Results

After a payment has been initiated, the Payment SDK will either return a success or failure result. You can handle these results by setting up success and failure callbacks using the setPaymentSuccessCallback and setPaymentFailedCallback methods:

```java
Payment.setPaymentFailedCallback(res -> {
    Log.d("JAVAres", res.toString());
});

Payment.setPaymentSuccessCallback(res -> {
    Log.d("JAVAres", res.toString());
});
```

The success and failure callbacks will be passed a JSONObject containing the result of the payment. The structure of this JSONObject will depend on the payment channel used and the result of the payment.

## Additional Resources

For more information on using the Payment SDK, please see the NetAppsPay Developer Portal.
Support

If you have any questions or need assistance using the Payment SDK, please contact us at support@NetAppsPay.com.
