package com.dcloud.android.graphics;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class Region extends android.graphics.Region {
    private int HOLD_SCREEN_COUNT;
    int fillScreenCounter;

    public Region() {
        this(1);
    }

    public void count() {
        this.fillScreenCounter++;
    }

    public boolean fillWholeScreen() {
        return this.fillScreenCounter >= this.HOLD_SCREEN_COUNT;
    }

    public int getFillScreenCounter() {
        return this.fillScreenCounter;
    }

    public Region(int i) {
        this.fillScreenCounter = 1;
        this.HOLD_SCREEN_COUNT = i;
    }
}
