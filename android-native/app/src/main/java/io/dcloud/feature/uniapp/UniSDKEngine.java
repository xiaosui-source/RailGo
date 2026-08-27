package io.dcloud.feature.uniapp;

import com.taobao.weex.WXEnvironment;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.bridge.WXModuleManager;
import com.taobao.weex.common.Destroyable;
import com.taobao.weex.ui.ExternalLoaderComponentHolder;
import com.taobao.weex.ui.SimpleComponentHolder;
import com.taobao.weex.ui.WXComponentRegistry;
import io.dcloud.feature.uniapp.bridge.UniModuleFactory;
import io.dcloud.feature.uniapp.common.TypeUniModuleFactory;
import io.dcloud.feature.uniapp.common.UniDestroyableModule;
import io.dcloud.feature.uniapp.common.UniModule;
import io.dcloud.feature.uniapp.ui.IExternalUniComponentGetter;
import io.dcloud.feature.uniapp.ui.IExternalUniModuleGetter;
import io.dcloud.feature.uniapp.ui.IFUniComponentHolder;
import io.dcloud.feature.uniapp.ui.component.UniComponent;
import io.dcloud.feature.uniapp.ui.component.UniVContainer;
import java.util.HashMap;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class UniSDKEngine extends WXSDKEngine {

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    public static abstract class DestroyableUniModule extends UniModule implements Destroyable {
    }

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    public static abstract class DestroyableUniModuleFactory<T extends UniDestroyableModule> extends TypeUniModuleFactory<T> {
        public DestroyableUniModuleFactory(Class<T> cls) {
            super(cls);
        }
    }

    public static <T extends UniModule> boolean registerModuleWithFactory(String str, IExternalUniModuleGetter iExternalUniModuleGetter, boolean z) {
        return WXSDKEngine.registerModule(str, iExternalUniModuleGetter.getExternalModuleClass(str, WXEnvironment.getApplication()), z);
    }

    public static boolean registerUniComponent(String str, Class<? extends UniComponent> cls) {
        return WXComponentRegistry.registerComponent(str, new SimpleComponentHolder(cls), new HashMap());
    }

    public static <T extends UniModule> boolean registerUniModule(String str, Class<T> cls, boolean z) {
        return WXSDKEngine.registerModule(str, cls, z);
    }

    public static <T extends UniModule> boolean registerUniModuleWithFactory(String str, DestroyableUniModuleFactory destroyableUniModuleFactory, boolean z) {
        return WXSDKEngine.registerModule(str, destroyableUniModuleFactory, z);
    }

    public static boolean registerUniVContainer(String str, Class<? extends UniVContainer> cls) {
        return WXComponentRegistry.registerComponent(str, new SimpleComponentHolder(cls), new HashMap());
    }

    public static boolean registerUniComponent(String str, Class<? extends UniComponent> cls, boolean z) {
        return WXSDKEngine.registerComponent(cls, z, str);
    }

    public static <T extends UniModule> boolean registerUniModule(String str, UniModuleFactory uniModuleFactory, boolean z) {
        return WXModuleManager.registerModule(str, uniModuleFactory, z);
    }

    public static boolean registerUniComponent(String str, IExternalUniComponentGetter iExternalUniComponentGetter, boolean z) {
        return WXSDKEngine.registerComponent(new ExternalLoaderComponentHolder(str, iExternalUniComponentGetter), z, str);
    }

    public static boolean registerUniModule(String str, Class<? extends UniModule> cls) {
        return WXSDKEngine.registerModule(str, (Class) cls, false);
    }

    public static boolean registerUniComponent(Class<? extends UniComponent> cls, boolean z, String... strArr) {
        return WXSDKEngine.registerComponent(cls, z, strArr);
    }

    public static boolean registerUniComponent(IFUniComponentHolder iFUniComponentHolder, boolean z, String... strArr) {
        return WXSDKEngine.registerComponent(iFUniComponentHolder, z, strArr);
    }
}
