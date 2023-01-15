package com.netappssdk;
import static android.util.Base64.encodeToString;
import android.os.Build;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.module.annotations.ReactModule;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Enumeration;
import java.util.Objects;
import java.util.UUID;

import javax.crypto.Cipher;

@ReactModule(name = NetappspaysdkModule.NAME)
public class NetappspaysdkModule extends ReactContextBaseJavaModule {
    public static final String NAME = "Netappspaysdk";

    public NetappspaysdkModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    @NonNull
    public String getName() {
        return NAME;
    }


    // Example method
    // See https://reactnative.dev/docs/native-modules-android
    @ReactMethod
    public void encrypt(String PUBLIC_KEY, String data, Promise promise) {
      String encoded = "";
      byte[] encrypted = null;
      try {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING"); //or try with "RSA"
        cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(PUBLIC_KEY));
        encrypted = cipher.doFinal(data.getBytes());
        encoded = encodeToString(encrypted, Base64.DEFAULT);
      }
      catch (Exception e) {
        promise.reject(e.getMessage());
        e.printStackTrace();
      }
      promise.resolve(encoded);
    }

    Key getPublicKey(String key) throws NoSuchAlgorithmException, InvalidKeySpecException {
      String publicKeyPEM = key;
      publicKeyPEM = publicKeyPEM.replace("-----BEGIN PUBLIC KEY-----", "");
      publicKeyPEM = publicKeyPEM.replace("-----END PUBLIC KEY-----", "");
      publicKeyPEM = publicKeyPEM.replaceAll("\\s+", "");
      byte[] encoded = Base64.decode(publicKeyPEM, Base64.DEFAULT);
      KeyFactory kf = KeyFactory.getInstance("RSA");
      return kf.generatePublic(new X509EncodedKeySpec(encoded));
  }

  @ReactMethod
  public void getDeviceId(Promise promise) {

    String m_szDevIDShort = "35" + (Build.BOARD.length() % 10) + (Build.BRAND.length() % 10) + (Build.CPU_ABI.length() % 10) + (Build.DEVICE.length() % 10) + (Build.MANUFACTURER.length() % 10) + (Build.MODEL.length() % 10) + (Build.PRODUCT.length() % 10);

    String serial = null;
    try {
      serial = Objects.requireNonNull(Build.class.getField("SERIAL").get(null)).toString();

      String deviceId = new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
      promise.resolve(deviceId);
    } catch (Exception exception) {

      serial = "serial";
    }

    String deviceId = new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
    promise.resolve(deviceId);
  }

  @ReactMethod
  public void getDeviceIp(Promise promise) {
    String ip = "";
    try {
      Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
      while (en.hasMoreElements()) {
        NetworkInterface intf = en.nextElement();
        Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses();
        while (enumIpAddr.hasMoreElements()) {
          InetAddress inetAddress = enumIpAddr.nextElement();
          if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
            ip = inetAddress.getHostAddress();
            promise.resolve(ip);
          }
        }
      }
    } catch (SocketException ex) {
      Log.e("IP Address", ex.toString());
    }

  }

}
