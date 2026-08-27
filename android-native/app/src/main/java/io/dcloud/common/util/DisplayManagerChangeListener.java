package io.dcloud.common.util;

import android.app.Activity;
import android.hardware.display.DisplayManager;
import android.os.Build;
import io.dcloud.EntryProxy;
import io.dcloud.common.adapter.util.AndroidResources;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class DisplayManagerChangeListener implements DisplayManager.DisplayListener {
    private final Activity activity;
    private final DisplayManager displayManager;
    private final EntryProxy mEntryProxy;

    public DisplayManagerChangeListener(DisplayManager displayManager, Activity activity, EntryProxy entryProxy) {
        this.displayManager = displayManager;
        this.activity = activity;
        this.mEntryProxy = entryProxy;
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public void onDisplayAdded(int i) {
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public void onDisplayChanged(int i) {
        EntryProxy entryProxy;
        if (i == 0) {
            int rotation = this.activity.getWindowManager().getDefaultDisplay().getRotation();
            if ((rotation == 3 || rotation == 1) && (entryProxy = this.mEntryProxy) != null) {
                entryProxy.onConfigurationChanged(this.activity, rotation);
            }
        }
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public void onDisplayRemoved(int i) {
    }

    public void register() {
        if (Build.VERSION.SDK_INT < 36 || AndroidResources.sAppTargetSdkVersion < 36) {
            return;
        }
        this.displayManager.registerDisplayListener(this, null);
    }

    public void unregister() {
        if (Build.VERSION.SDK_INT < 36 || AndroidResources.sAppTargetSdkVersion < 36) {
            return;
        }
        this.displayManager.unregisterDisplayListener(this);
    }
}
