package com.taobao.weex.bridge;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class ModuleFactoryImpl {
    public boolean hasRigster;
    public ModuleFactory mFactory;

    public ModuleFactoryImpl(ModuleFactory moduleFactory) {
        this.hasRigster = false;
        this.mFactory = moduleFactory;
    }

    public ModuleFactoryImpl(ModuleFactory moduleFactory, boolean z) {
        this.mFactory = moduleFactory;
        this.hasRigster = z;
    }
}
