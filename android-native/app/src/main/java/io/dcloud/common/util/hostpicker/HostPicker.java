package io.dcloud.common.util.hostpicker;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;
import com.taobao.weex.el.parse.Operators;
import io.dcloud.p.d1;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.List;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class HostPicker {
    private static HostPicker instance = new HostPicker();
    private final String SP_FILE_NAME = "UNIAPP_HostPicker_0817";
    private final String SP_LAST_SUIT_HOST_NAME = "SP_LAST_SUIT_HOST_NAME_0817";

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public static class Host implements Comparable<Host>, Cloneable {
        String hostUrl;
        PriorityEnum priority;

        /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
        public enum PriorityEnum {
            NORMAL(0),
            FIRST(1),
            BACKUP(-1);

            int val;

            PriorityEnum(int i) {
                this.val = i;
            }
        }

        public Host(String str, PriorityEnum priorityEnum) {
            PriorityEnum priorityEnum2 = PriorityEnum.NORMAL;
            this.hostUrl = str;
            this.priority = priorityEnum;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof Host)) {
                return false;
            }
            Host host = (Host) obj;
            if (TextUtils.isEmpty(host.hostUrl)) {
                return false;
            }
            return host.hostUrl.equals(this.hostUrl);
        }

        public String getHostUrl() {
            return this.hostUrl;
        }

        public PriorityEnum getPriority() {
            return this.priority;
        }

        public String getRealHost() {
            String str = "";
            if (TextUtils.isEmpty(this.hostUrl)) {
                return "";
            }
            try {
                str = new String(Base64.decode(this.hostUrl.getBytes("UTF-8"), 2), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return d1.b(str);
        }

        public int hashCode() {
            return this.hostUrl.hashCode();
        }

        public boolean isFormatRightful() {
            return !TextUtils.isEmpty(this.hostUrl);
        }

        public String toString() {
            return "Host{hostUrl='" + this.hostUrl + "', priority=" + this.priority + Operators.BLOCK_END;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: clone, reason: merged with bridge method [inline-methods] */
        public Host m422clone() {
            return new Host(this.hostUrl, this.priority);
        }

        @Override // java.lang.Comparable
        public int compareTo(Host host) {
            if (host == null) {
                return 1;
            }
            return host.priority.val - this.priority.val;
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public interface HostPickCallback {
        boolean doRequest(Host host);

        void onNoOnePicked();

        void onOneSelected(Host host);
    }

    private HostPicker() {
    }

    public static HostPicker getInstance() {
        return instance;
    }

    private void initHostsForRequest(Context context, List<Host> list, String str) {
        String str2 = "SP_LAST_SUIT_HOST_NAME_0817" + str;
        SharedPreferences sharedPreferences = context.getSharedPreferences("UNIAPP_HostPicker_0817", 0);
        String string = sharedPreferences.getString(str2, "");
        for (Host host : list) {
            if (!host.isFormatRightful()) {
                throw new RuntimeException("error format host");
            }
            if (!TextUtils.isEmpty(string) && host.priority != Host.PriorityEnum.BACKUP) {
                if (string.equals(host.hostUrl)) {
                    host.priority = Host.PriorityEnum.FIRST;
                } else {
                    host.priority = Host.PriorityEnum.NORMAL;
                }
            }
        }
        sharedPreferences.edit().remove(str2).apply();
    }

    public void pickSuitHost(Context context, List<Host> list, String str, HostPickCallback hostPickCallback) {
        if (list == null || list.isEmpty()) {
            throw new RuntimeException("call initHosts first");
        }
        initHostsForRequest(context, list, str);
        Collections.sort(list);
        for (Host host : list) {
            if (hostPickCallback.doRequest(host)) {
                if (host.priority != Host.PriorityEnum.BACKUP) {
                    context.getSharedPreferences("UNIAPP_HostPicker_0817", 0).edit().putString("SP_LAST_SUIT_HOST_NAME_0817" + str, host.hostUrl).apply();
                }
                hostPickCallback.onOneSelected(host);
                return;
            }
        }
        hostPickCallback.onNoOnePicked();
    }
}
