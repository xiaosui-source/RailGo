package com.taobao.weex.font;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class FontAdapter {
    private List<FontListener> mFontListener = new CopyOnWriteArrayList();

    public void addFontListener(FontListener fontListener) {
        this.mFontListener.add(fontListener);
    }

    public void onAddFontRule(String str, String str2, String str3) {
        synchronized (this) {
            Iterator<FontListener> it = this.mFontListener.iterator();
            while (it.hasNext()) {
                it.next().onAddFontRule(str, str2, str3);
            }
        }
    }

    public void onFontLoad(String str, String str2, String str3) {
        synchronized (this) {
            Iterator<FontListener> it = this.mFontListener.iterator();
            while (it.hasNext()) {
                it.next().onFontLoad(str, str2, str3);
            }
        }
    }

    public void removeFontListener(FontListener fontListener) {
        this.mFontListener.remove(fontListener);
    }
}
