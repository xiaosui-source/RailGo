package com.bun.miitmdid.c.h;

import android.content.ContentProviderClient;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;

/* loaded from: /workspace/39285EFA.decrypted.dex */
public class a {
    private static Uri a = Uri.parse("content://cn.nubia.identity/identity");

    public static String a(Context context) throws RemoteException {
        String string;
        Exception e;
        Bundle bundleCall;
        try {
            if (Build.VERSION.SDK_INT >= 17) {
                ContentProviderClient contentProviderClientAcquireUnstableContentProviderClient = context.getContentResolver().acquireUnstableContentProviderClient(a);
                bundleCall = contentProviderClientAcquireUnstableContentProviderClient.call("getOAID", null, null);
                if (contentProviderClientAcquireUnstableContentProviderClient != null) {
                    if (Build.VERSION.SDK_INT >= 24) {
                        contentProviderClientAcquireUnstableContentProviderClient.close();
                    } else {
                        contentProviderClientAcquireUnstableContentProviderClient.release();
                    }
                }
            } else {
                bundleCall = context.getContentResolver().call(a, "getOAID", (String) null, (Bundle) null);
            }
            if (bundleCall.getInt("code", -1) == 0) {
                string = bundleCall.getString("id");
                com.bun.lib.a.b("NubiaLog", "succeed:" + string);
            } else {
                string = bundleCall.getString("message");
                try {
                    com.bun.lib.a.b("NubiaLog", "failed:" + string);
                } catch (Exception e2) {
                    e = e2;
                    e.printStackTrace();
                    return string;
                }
            }
        } catch (Exception e3) {
            string = null;
            e = e3;
        }
        return string;
    }

    public static String a(Context context, String str) throws RemoteException {
        String string;
        Exception e;
        Bundle bundleCall;
        try {
            if (Build.VERSION.SDK_INT >= 17) {
                ContentProviderClient contentProviderClientAcquireUnstableContentProviderClient = context.getContentResolver().acquireUnstableContentProviderClient(a);
                bundleCall = contentProviderClientAcquireUnstableContentProviderClient.call("getAAID", str, null);
                if (contentProviderClientAcquireUnstableContentProviderClient != null) {
                    if (Build.VERSION.SDK_INT >= 24) {
                        contentProviderClientAcquireUnstableContentProviderClient.close();
                    } else {
                        contentProviderClientAcquireUnstableContentProviderClient.release();
                    }
                }
            } else {
                bundleCall = context.getContentResolver().call(a, "getAAID", str, (Bundle) null);
            }
            if (bundleCall.getInt("code", -1) == 0) {
                string = bundleCall.getString("id");
                com.bun.lib.a.b("NubiaLog", "succeed:" + string);
            } else {
                string = bundleCall.getString("message");
                try {
                    com.bun.lib.a.b("NubiaLog", "failed:" + string);
                } catch (Exception e2) {
                    e = e2;
                    e.printStackTrace();
                    return string;
                }
            }
        } catch (Exception e3) {
            string = null;
            e = e3;
        }
        return string;
    }

    public static String b(Context context, String str) throws RemoteException {
        String string;
        Exception e;
        Bundle bundleCall;
        try {
            if (Build.VERSION.SDK_INT >= 17) {
                ContentProviderClient contentProviderClientAcquireUnstableContentProviderClient = context.getContentResolver().acquireUnstableContentProviderClient(a);
                bundleCall = contentProviderClientAcquireUnstableContentProviderClient.call("getVAID", str, null);
                if (contentProviderClientAcquireUnstableContentProviderClient != null) {
                    if (Build.VERSION.SDK_INT >= 24) {
                        contentProviderClientAcquireUnstableContentProviderClient.close();
                    } else {
                        contentProviderClientAcquireUnstableContentProviderClient.release();
                    }
                }
            } else {
                context.getContentResolver().call(a, "getVAID", str, (Bundle) null);
                bundleCall = null;
            }
            if (bundleCall.getInt("code", -1) == 0) {
                string = bundleCall.getString("id");
                com.bun.lib.a.b("NubiaLog", "succeed:" + string);
            } else {
                string = bundleCall.getString("message");
                try {
                    com.bun.lib.a.b("NubiaLog", "failed:" + string);
                } catch (Exception e2) {
                    e = e2;
                    e.printStackTrace();
                    return string;
                }
            }
        } catch (Exception e3) {
            string = null;
            e = e3;
        }
        return string;
    }

    public static boolean b(Context context) throws RemoteException {
        Bundle bundleCall;
        try {
            if (Build.VERSION.SDK_INT >= 17) {
                ContentProviderClient contentProviderClientAcquireUnstableContentProviderClient = context.getContentResolver().acquireUnstableContentProviderClient(a);
                bundleCall = contentProviderClientAcquireUnstableContentProviderClient.call("isSupport", null, null);
                if (contentProviderClientAcquireUnstableContentProviderClient != null) {
                    if (Build.VERSION.SDK_INT >= 24) {
                        contentProviderClientAcquireUnstableContentProviderClient.close();
                    } else {
                        contentProviderClientAcquireUnstableContentProviderClient.release();
                    }
                }
            } else {
                bundleCall = context.getContentResolver().call(a, "isSupport", (String) null, (Bundle) null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (bundleCall.getInt("code", -1) == 0) {
            com.bun.lib.a.b("NubiaLog", "succeed");
            return bundleCall.getBoolean("issupport", true);
        }
        com.bun.lib.a.b("NubiaLog", "failed:" + bundleCall.getString("message"));
        return false;
    }
}
