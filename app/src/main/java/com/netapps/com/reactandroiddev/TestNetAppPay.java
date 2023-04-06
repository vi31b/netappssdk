package com.netapps.com.reactandroiddev;
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
        Log.d("Develop law", "onSuccess: ");
    }

    @Override
    public void onError(String message) {
        Log.d("develop law", "onError: ");
    }
}

