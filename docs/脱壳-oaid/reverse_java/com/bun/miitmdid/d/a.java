package com.bun.miitmdid.d;

import android.content.Context;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: /workspace/39285EFA.decrypted.dex */
public class a {
    public static String a(Context context, String str) throws IOException {
        try {
            InputStream inputStreamOpen = context.getAssets().open(str);
            int iAvailable = inputStreamOpen.available();
            byte[] bArr = new byte[iAvailable];
            inputStreamOpen.read(bArr);
            inputStreamOpen.close();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byteArrayOutputStream.write(bArr, 0, iAvailable);
            return byteArrayOutputStream.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
