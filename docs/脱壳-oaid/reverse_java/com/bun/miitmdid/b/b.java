package com.bun.miitmdid.b;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: /workspace/39285EFA.decrypted.dex */
public class b {
    private static boolean h;
    private String a = null;
    private InterfaceC0002b b = null;
    private Object c = null;
    private Map<String, String> d = new HashMap();
    private Map<String, String> e = new HashMap();
    private Map<String, String> f = new HashMap();
    private String g = "GET";

    class a extends AsyncTask<Void, Void, c> {
        b a;

        a() {
            this.a = b.this;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public c doInBackground(Void... voidArr) {
            return this.a.g.equalsIgnoreCase("GET") ? b.this.b() : b.this.c();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public void onPostExecute(c cVar) {
            super.onPostExecute(cVar);
            if (b.this.b != null) {
                if (cVar == null) {
                    b.this.b.a(new Exception("Unknown Error"), -1, null);
                    return;
                }
                if (cVar.c != null) {
                    b.this.b.a(cVar.c, -1, null);
                    return;
                }
                try {
                    b.this.b.a(null, cVar.b, cVar.a);
                } catch (Exception e) {
                    b.this.b.a(e, -1, null);
                }
            }
        }
    }

    /* renamed from: com.bun.miitmdid.b.b$b, reason: collision with other inner class name */
    public interface InterfaceC0002b {
        void a(Exception exc, int i, String str);
    }

    private class c {
        private String a;
        private int b;
        private Exception c;

        public c(b bVar, String str, Exception exc, int i) {
            this.a = str;
            this.c = exc;
            this.b = i;
        }
    }

    private b(Context context) {
    }

    public static b a(@NonNull Context context) {
        return new b(context);
    }

    private void a(HttpURLConnection httpURLConnection) {
        if (httpURLConnection == null) {
            return;
        }
        httpURLConnection.setConnectTimeout(20000);
        httpURLConnection.setReadTimeout(10000);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public c b() throws IOException {
        try {
            String strD = d();
            if (h) {
                b("Making Get url call to " + strD);
            }
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(strD).openConnection();
            a(httpURLConnection);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("User-Agent", com.bun.miitmdid.b.a.a());
            for (String str : this.d.keySet()) {
                if (!str.isEmpty()) {
                    httpURLConnection.setRequestProperty(str, this.d.get(str));
                }
            }
            int responseCode = httpURLConnection.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + this.a);
            System.out.println("Response Code : " + responseCode);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    bufferedReader.close();
                    return new c(this, sb.toString(), null, responseCode);
                }
                sb.append(line);
            }
        } catch (Exception e) {
            if (h) {
                e.printStackTrace();
            }
            return new c(this, null, e, -1);
        }
    }

    private static void b(@NonNull String str) {
        com.bun.lib.a.b(b.class.getSimpleName(), str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public c c() throws IOException {
        String string;
        StringBuilder sb;
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(d()).openConnection();
            a(httpURLConnection);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("User-Agent", com.bun.miitmdid.b.a.a());
            httpURLConnection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            StringBuilder sb2 = new StringBuilder("");
            if (!this.e.isEmpty()) {
                for (String str : this.e.keySet()) {
                    sb2.append(str);
                    sb2.append("=");
                    sb2.append(URLEncoder.encode(this.e.get(str), "UTF-8"));
                    sb2.append("&");
                }
            } else if (this.c != null) {
                if ((this.c instanceof JSONObject) || (this.c instanceof JSONArray)) {
                    string = this.c.toString();
                    sb = sb2;
                } else if (this.c instanceof String) {
                    string = (String) this.c;
                    sb = sb2;
                }
                sb.append(string);
            }
            for (String str2 : this.d.keySet()) {
                if (!str2.isEmpty()) {
                    httpURLConnection.setRequestProperty(str2, this.d.get(str2));
                }
            }
            String string2 = sb2.toString();
            httpURLConnection.setDoOutput(true);
            DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
            dataOutputStream.writeBytes(string2);
            dataOutputStream.flush();
            dataOutputStream.close();
            int responseCode = httpURLConnection.getResponseCode();
            if (h) {
                b("\nSending 'POST' request to URL : " + this.a);
                b("Post parameters : " + string2);
                b("Response Code : " + responseCode);
            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            StringBuilder sb3 = new StringBuilder();
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    bufferedReader.close();
                    String string3 = sb3.toString();
                    b("\nPOST RESPONSE : " + string3);
                    return new c(this, string3, null, responseCode);
                }
                sb3.append(line);
            }
        } catch (Exception e) {
            if (h) {
                e.printStackTrace();
            }
            return new c(this, null, e, -1);
        }
    }

    private String d() {
        if (this.a == null) {
            throw new NullPointerException("URL IS NULL");
        }
        StringBuilder sb = new StringBuilder("");
        try {
            if (this.a.trim().endsWith("?")) {
                sb.append("&");
            } else {
                sb.append("?");
            }
            for (String str : this.f.keySet()) {
                if (!str.isEmpty()) {
                    sb.append(str.trim());
                    sb.append("=");
                    sb.append(URLEncoder.encode(this.f.get(str), "UTF-8"));
                    sb.append("&");
                }
            }
        } catch (Exception e) {
        }
        return this.a + (sb.toString().contains("&") ? sb.substring(0, sb.lastIndexOf("&")) : sb.toString());
    }

    public b a() {
        new a().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
        return this;
    }

    public b a(InterfaceC0002b interfaceC0002b) {
        this.b = interfaceC0002b;
        return this;
    }

    public b a(Object obj) {
        this.c = obj;
        return this;
    }

    public b a(@NonNull String str) {
        this.a = str;
        this.g = "POST";
        return this;
    }

    public b a(@NonNull String str, @NonNull String str2) {
        this.f.put(str, str2);
        return this;
    }

    public b a(Map<String, String> map) {
        if (map != null && !map.isEmpty()) {
            this.f.putAll(map);
        }
        return this;
    }
}
