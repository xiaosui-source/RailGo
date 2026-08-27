package io.dcloud.feature.uniapp.utils;

import kotlinx.coroutines.DebugKt;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public enum UniLogLevel implements AbsLogLevel {
    OFF(DebugKt.DEBUG_PROPERTY_VALUE_OFF, 8, 7),
    WTF("wtf", 7, 7),
    TLOG("tlog", 6, 6),
    ERROR("error", 5, 6),
    WARN("warn", 4, 5),
    INFO("info", 3, 4),
    DEBUG("debug", 2, 3),
    VERBOSE("verbose", 1, 2),
    ALL("all", 0, 2);

    String name;
    int priority;
    int value;

    UniLogLevel(String str, int i, int i2) {
        this.name = str;
        this.value = i;
        this.priority = i2;
    }

    @Override // io.dcloud.feature.uniapp.utils.AbsLogLevel
    public int compare(AbsLogLevel absLogLevel) {
        return this.value - absLogLevel.getValue();
    }

    @Override // io.dcloud.feature.uniapp.utils.AbsLogLevel
    public String getName() {
        return this.name;
    }

    @Override // io.dcloud.feature.uniapp.utils.AbsLogLevel
    public int getPriority() {
        return this.priority;
    }

    @Override // io.dcloud.feature.uniapp.utils.AbsLogLevel
    public int getValue() {
        return this.value;
    }
}
