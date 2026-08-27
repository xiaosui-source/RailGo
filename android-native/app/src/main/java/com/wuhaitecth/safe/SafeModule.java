package com.wuhaitecth.safe;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Debug;
import android.os.Process;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import com.alibaba.fastjson.JSONObject;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.el.parse.Operators;
import com.taobao.weex.performance.WXInstanceApm;
import io.dcloud.feature.uniapp.annotation.UniJSMethod;
import io.dcloud.feature.uniapp.common.UniModule;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Locale;

/* loaded from: classes.dex */
public class SafeModule extends UniModule {
    String LOG_TAG = "SafeModule";

    @UniJSMethod(uiThread = true)
    public void isDisableScreen(int i, JSCallback jSCallback) {
        if (i == 1) {
            ((Activity) this.mUniSDKInstance.getContext()).getWindow().addFlags(8192);
        } else {
            ((Activity) this.mUniSDKInstance.getContext()).getWindow().clearFlags(8192);
        }
        if (jSCallback != null) {
            jSCallback.invoke(true);
        }
    }

    @UniJSMethod(uiThread = true)
    public void getSignature(JSCallback jSCallback) throws PackageManager.NameNotFoundException, CertificateException {
        PackageInfo packageInfo;
        CertificateFactory certificateFactory;
        Context context = this.mUniSDKInstance.getContext();
        X509Certificate x509Certificate = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 64);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            packageInfo = null;
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(packageInfo.signatures[0].toByteArray());
        try {
            certificateFactory = CertificateFactory.getInstance("X509");
        } catch (Exception e2) {
            e2.printStackTrace();
            certificateFactory = null;
        }
        try {
            x509Certificate = (X509Certificate) certificateFactory.generateCertificate(byteArrayInputStream);
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        try {
            String strByte2HexFormatted = byte2HexFormatted(MessageDigest.getInstance("SHA1").digest(x509Certificate.getEncoded()));
            if (jSCallback != null) {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("state", (Object) true);
                jSONObject.put("data", (Object) strByte2HexFormatted);
                jSCallback.invoke(jSONObject);
            }
        } catch (Exception e4) {
            if (jSCallback != null) {
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("state", (Object) false);
                jSONObject2.put("data", (Object) e4.getMessage());
                jSCallback.invoke(jSONObject2);
            }
        }
    }

    @UniJSMethod(uiThread = true)
    public void getApkSHA(JSCallback jSCallback) throws NoSuchAlgorithmException, IOException {
        String packageCodePath = this.mUniSDKInstance.getContext().getPackageCodePath();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            byte[] bArr = new byte[1024];
            FileInputStream fileInputStream = new FileInputStream(new File(packageCodePath));
            while (true) {
                int i = fileInputStream.read(bArr);
                if (i == -1) {
                    break;
                } else {
                    messageDigest.update(bArr, 0, i);
                }
            }
            String string = new BigInteger(1, messageDigest.digest()).toString(16);
            fileInputStream.close();
            if (jSCallback != null) {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("state", (Object) true);
                jSONObject.put("data", (Object) string);
                jSCallback.invoke(jSONObject);
            }
        } catch (Exception e) {
            if (jSCallback != null) {
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("state", (Object) false);
                jSONObject2.put("data", (Object) e.getMessage());
                jSCallback.invoke(jSONObject2);
            }
        }
    }

    @UniJSMethod(uiThread = true)
    public void checkIsRoot(JSCallback jSCallback) {
        if (jSCallback != null) {
            jSCallback.invoke(Boolean.valueOf(isDeviceRooted()));
        }
    }

    @UniJSMethod(uiThread = true)
    public void isDebuggable(JSCallback jSCallback) {
        try {
            ApplicationInfo applicationInfo = this.mUniSDKInstance.getContext().getApplicationInfo();
            if (jSCallback != null) {
                jSCallback.invoke(Boolean.valueOf((applicationInfo.flags & 2) != 0));
            }
        } catch (Exception unused) {
            jSCallback.invoke(false);
        }
    }

    @UniJSMethod(uiThread = true)
    public void isUnderTraced(JSCallback jSCallback) throws IOException {
        boolean z = false;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(String.format(Locale.US, "/proc/%d/status", Integer.valueOf(Process.myPid())))));
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
                if (line.contains("TracerPid")) {
                    String[] strArrSplit = line.split(":");
                    if (strArrSplit.length == 2 && Integer.parseInt(strArrSplit[1].trim()) != 0) {
                        z = true;
                    }
                }
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        jSCallback.invoke(z);
    }

    @UniJSMethod(uiThread = true)
    public void isEmulator(JSCallback jSCallback) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("tel:123456"));
        intent.setAction("android.intent.action.DIAL");
        boolean z = true;
        boolean z2 = intent.resolveActivity(this.mUniSDKInstance.getContext().getPackageManager()) != null;
        if (!Build.FINGERPRINT.startsWith("generic") && !Build.FINGERPRINT.toLowerCase().contains("vbox") && !Build.FINGERPRINT.toLowerCase().contains("test-keys") && !Build.MODEL.contains("google_sdk") && !Build.MODEL.contains("Emulator") && !Build.SERIAL.equalsIgnoreCase("unknown") && !Build.SERIAL.equalsIgnoreCase(WXEnvironment.OS) && !Build.MODEL.contains("Android SDK built for x86") && !Build.MANUFACTURER.contains("Genymotion") && ((!Build.BRAND.startsWith("generic") || !Build.DEVICE.startsWith("generic")) && !"google_sdk".equals(Build.PRODUCT) && !((TelephonyManager) this.mUniSDKInstance.getContext().getSystemService("phone")).getNetworkOperatorName().toLowerCase().equals(WXEnvironment.OS) && z2)) {
            z = false;
        }
        jSCallback.invoke(Boolean.valueOf(z));
    }

    @UniJSMethod(uiThread = true)
    public void isWifiProxy(JSCallback jSCallback) {
        String property = System.getProperty("http.proxyHost");
        String property2 = System.getProperty("http.proxyPort");
        if (property2 == null) {
            property2 = "-1";
        }
        jSCallback.invoke(Boolean.valueOf((TextUtils.isEmpty(property) || Integer.parseInt(property2) == -1) ? false : true));
    }

    @UniJSMethod(uiThread = true)
    public void detectedDynamicDebug() {
        if (Debug.isDebuggerConnected()) {
            Process.killProcess(Process.myPid());
            System.exit(1);
        }
    }

    private boolean isDeviceRooted() {
        if (checkDeviceDebuggable() || checkSuperuserApk() || checkBusybox() || checkAccessRootData()) {
            return true;
        }
        return checkGetRootAuth();
    }

    private synchronized boolean checkAccessRootData() {
        Log.i(this.LOG_TAG, "to write /data");
        if (writeFile("/data/su_test", "test_ok").booleanValue()) {
            Log.i(this.LOG_TAG, "write ok");
        } else {
            Log.i(this.LOG_TAG, "write failed");
        }
        Log.i(this.LOG_TAG, "to read /data");
        String file = readFile("/data/su_test");
        Log.i(this.LOG_TAG, "strRead=" + file);
        return "test_ok".equals(file);
    }

    private Boolean writeFile(String str, String str2) throws IOException {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(str);
            fileOutputStream.write(str2.getBytes());
            fileOutputStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private String readFile(String str) throws IOException {
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(str));
            byte[] bArr = new byte[1024];
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            while (true) {
                int i = fileInputStream.read(bArr);
                if (i > 0) {
                    byteArrayOutputStream.write(bArr, 0, i);
                } else {
                    String str2 = new String(byteArrayOutputStream.toByteArray());
                    Log.i(this.LOG_TAG, str2);
                    return str2;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean checkDeviceDebuggable() {
        String str = Build.TAGS;
        if (str == null || !str.contains("test-keys")) {
            return false;
        }
        Log.i(this.LOG_TAG, "buildTags=" + str);
        return true;
    }

    private boolean checkSuperuserApk() {
        try {
            if (!new File("/system/app/Superuser.apk").exists()) {
                return false;
            }
            Log.i(this.LOG_TAG, "/system/app/Superuser.apk exist");
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    private boolean checkGetRootAuth() throws Throwable {
        Process processExec;
        DataOutputStream dataOutputStream = null;
        try {
            processExec = Runtime.getRuntime().exec("su");
            try {
                DataOutputStream dataOutputStream2 = new DataOutputStream(processExec.getOutputStream());
                try {
                    dataOutputStream2.writeBytes("exit\n");
                    dataOutputStream2.flush();
                    if (processExec.waitFor() == 0) {
                        try {
                            dataOutputStream2.close();
                            if (processExec != null) {
                                processExec.destroy();
                            }
                            return true;
                        } catch (Exception e) {
                            e.printStackTrace();
                            return true;
                        }
                    }
                    try {
                        dataOutputStream2.close();
                        if (processExec != null) {
                            processExec.destroy();
                        }
                        return false;
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        return false;
                    }
                } catch (Exception unused) {
                    dataOutputStream = dataOutputStream2;
                    if (dataOutputStream != null) {
                        try {
                            dataOutputStream.close();
                        } catch (Exception e3) {
                            e3.printStackTrace();
                            return false;
                        }
                    }
                    if (processExec != null) {
                        processExec.destroy();
                    }
                    return false;
                } catch (Throwable th) {
                    th = th;
                    dataOutputStream = dataOutputStream2;
                    if (dataOutputStream != null) {
                        try {
                            dataOutputStream.close();
                        } catch (Exception e4) {
                            e4.printStackTrace();
                            throw th;
                        }
                    }
                    if (processExec != null) {
                        processExec.destroy();
                    }
                    throw th;
                }
            } catch (Exception unused2) {
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception unused3) {
            processExec = null;
        } catch (Throwable th3) {
            th = th3;
            processExec = null;
        }
    }

    private synchronized boolean checkBusybox() {
        Log.i(this.LOG_TAG, "to exec busybox df");
        ArrayList arrayListExecuteCommand = executeCommand(new String[]{"busybox", "df"});
        if (arrayListExecuteCommand != null) {
            Log.i(this.LOG_TAG, "execResult=" + arrayListExecuteCommand.toString());
            return true;
        }
        try {
            Log.i(this.LOG_TAG, "execResult=null");
            return false;
        } catch (Exception e) {
            Log.i(this.LOG_TAG, "Unexpected error - Here is what I know: " + e.getMessage());
            return false;
        }
    }

    private ArrayList executeCommand(String[] strArr) throws IOException {
        ArrayList arrayList = new ArrayList();
        try {
            Log.i(this.LOG_TAG, "to shell exec which for find su :");
            Process processExec = Runtime.getRuntime().exec(strArr);
            new BufferedWriter(new OutputStreamWriter(processExec.getOutputStream()));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(processExec.getInputStream()));
            while (true) {
                try {
                    String line = bufferedReader.readLine();
                    if (line == null) {
                        break;
                    }
                    Log.i(this.LOG_TAG, "Line received: " + line);
                    arrayList.add(line);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Log.i(this.LOG_TAG, "Full response was: " + arrayList);
            return arrayList;
        } catch (Exception unused) {
            return null;
        }
    }

    private String byte2HexFormatted(byte[] bArr) {
        StringBuilder sb = new StringBuilder(bArr.length * 2);
        for (int i = 0; i < bArr.length; i++) {
            String hexString = Integer.toHexString(bArr[i]);
            int length = hexString.length();
            if (length == 1) {
                hexString = WXInstanceApm.VALUE_ERROR_CODE_DEFAULT + hexString;
            }
            if (length > 2) {
                hexString = hexString.substring(length - 2, length);
            }
            sb.append(hexString.toUpperCase());
            if (i < bArr.length - 1) {
                sb.append(Operators.CONDITION_IF_MIDDLE);
            }
        }
        return sb.toString();
    }
}
