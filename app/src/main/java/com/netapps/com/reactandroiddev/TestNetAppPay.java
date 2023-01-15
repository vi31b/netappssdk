package com.netapps.com.reactandroiddev;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

import com.netappssdk.NetAppsPaySheet;


public class TestNetAppPay extends AppCompatActivity {
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        Button btnGoRNScreen = this.findViewById(R.id.btnGoRNScreen);
        NetAppsPaySheet Payment = new NetAppsPaySheet("b1108bfb3e2542b287162ef27da838f9", getSupportFragmentManager());
        btnGoRNScreen.setOnClickListener(it -> {
            try {
                JSONObject obj = new JSONObject();
                obj.put("currency", "NGN");
                obj.put("amount", "10");
                obj.put("phone", "08105535178");
                obj.put("email", "nwokolawrence6@gmail.com");
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
    }
}

