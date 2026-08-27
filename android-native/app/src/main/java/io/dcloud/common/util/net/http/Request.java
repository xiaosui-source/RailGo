package io.dcloud.common.util.net.http;

import java.io.IOException;
import java.io.InputStream;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class Request {
    private InputStream input;
    private String mData;
    private String uri;

    public Request(InputStream inputStream) {
        this.input = inputStream;
    }

    private String parseUri(String str) {
        int i;
        int iIndexOf;
        int iIndexOf2 = str.indexOf(32);
        if (iIndexOf2 == -1 || (iIndexOf = str.indexOf(32, (i = iIndexOf2 + 1))) <= iIndexOf2) {
            return null;
        }
        return str.substring(i, iIndexOf);
    }

    public String getData() {
        return this.mData;
    }

    public String getUri() {
        return this.uri;
    }

    public void parse() throws IOException {
        int i;
        StringBuffer stringBuffer = new StringBuffer(2048);
        byte[] bArr = new byte[2048];
        try {
            i = this.input.read(bArr);
        } catch (IOException e) {
            e.printStackTrace();
            i = -1;
        }
        for (int i2 = 0; i2 < i; i2++) {
            stringBuffer.append((char) bArr[i2]);
        }
        String string = stringBuffer.toString();
        this.mData = string;
        String uri = parseUri(string);
        this.uri = uri;
        this.uri = (uri == null || !uri.startsWith("/")) ? this.uri : this.uri.substring(1);
    }
}
