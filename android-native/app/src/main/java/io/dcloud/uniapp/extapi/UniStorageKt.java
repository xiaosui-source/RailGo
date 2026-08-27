package io.dcloud.uniapp.extapi;

import io.dcloud.common.DHInterface.IApp;
import io.dcloud.common.constant.AbsoluteConst;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import uts.sdk.modules.DCloudUniStorage.ClearStorageOptions;
import uts.sdk.modules.DCloudUniStorage.GetStorageInfoOptions;
import uts.sdk.modules.DCloudUniStorage.GetStorageInfoSuccess;
import uts.sdk.modules.DCloudUniStorage.GetStorageOptions;
import uts.sdk.modules.DCloudUniStorage.IndexKt;
import uts.sdk.modules.DCloudUniStorage.RemoveStorageOptions;
import uts.sdk.modules.DCloudUniStorage.SetStorageOptions;

/* compiled from: uniStorage.kt */
@Metadata(d1 = {"\u0000²\u0001\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\"0\u0010\u0014\u001a!\u0012\u0013\u0012\u00110\u0003¢\u0006\f\b\u0016\u0012\b\b\u0017\u0012\u0004\b\b(\u0018\u0012\u0004\u0012\u00020\u00190\u0015j\u0002`\u001a¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001c\"E\u0010\u001d\u001a6\u0012\u0013\u0012\u00110\u001f¢\u0006\f\b\u0016\u0012\b\b\u0017\u0012\u0004\b\b( \u0012\u0013\u0012\u00110!¢\u0006\f\b\u0016\u0012\b\b\u0017\u0012\u0004\b\b(\"\u0012\u0004\u0012\u00020\u00190\u001ej\u0002`#¢\u0006\b\n\u0000\u001a\u0004\b$\u0010%\"0\u0010&\u001a!\u0012\u0013\u0012\u00110\u0007¢\u0006\f\b\u0016\u0012\b\b\u0017\u0012\u0004\b\b(\u0018\u0012\u0004\u0012\u00020\u00190\u0015j\u0002`'¢\u0006\b\n\u0000\u001a\u0004\b(\u0010\u001c\"2\u0010)\u001a#\u0012\u0013\u0012\u00110\u001f¢\u0006\f\b\u0016\u0012\b\b\u0017\u0012\u0004\b\b( \u0012\u0006\u0012\u0004\u0018\u00010!0\u0015j\u0002`*¢\u0006\b\n\u0000\u001a\u0004\b+\u0010\u001c\"0\u0010,\u001a!\u0012\u0013\u0012\u00110\u000b¢\u0006\f\b\u0016\u0012\b\b\u0017\u0012\u0004\b\b(\u0018\u0012\u0004\u0012\u00020\u00190\u0015j\u0002`-¢\u0006\b\n\u0000\u001a\u0004\b.\u0010\u001c\"\u001b\u0010/\u001a\f\u0012\u0004\u0012\u00020\t00j\u0002`1¢\u0006\b\n\u0000\u001a\u0004\b2\u00103\"0\u00104\u001a!\u0012\u0013\u0012\u00110\u000f¢\u0006\f\b\u0016\u0012\b\b\u0017\u0012\u0004\b\b(\u0018\u0012\u0004\u0012\u00020\u00190\u0015j\u0002`5¢\u0006\b\n\u0000\u001a\u0004\b6\u0010\u001c\"0\u00107\u001a!\u0012\u0013\u0012\u00110\u001f¢\u0006\f\b\u0016\u0012\b\b\u0017\u0012\u0004\b\b( \u0012\u0004\u0012\u00020\u00190\u0015j\u0002`8¢\u0006\b\n\u0000\u001a\u0004\b9\u0010\u001c\"2\u0010:\u001a#\u0012\u0015\u0012\u0013\u0018\u00010\u0013¢\u0006\f\b\u0016\u0012\b\b\u0017\u0012\u0004\b\b(;\u0012\u0004\u0012\u00020\u00190\u0015j\u0002`<¢\u0006\b\n\u0000\u001a\u0004\b=\u0010\u001c\"\u001b\u0010>\u001a\f\u0012\u0004\u0012\u00020\u001900j\u0002`?¢\u0006\b\n\u0000\u001a\u0004\b@\u00103*\n\u0010\u0000\"\u00020\u00012\u00020\u0001*\n\u0010\u0002\"\u00020\u00032\u00020\u0003*\n\u0010\u0004\"\u00020\u00052\u00020\u0005*\n\u0010\u0006\"\u00020\u00072\u00020\u0007*\n\u0010\b\"\u00020\t2\u00020\t*\n\u0010\n\"\u00020\u000b2\u00020\u000b*\n\u0010\f\"\u00020\r2\u00020\r*\n\u0010\u000e\"\u00020\u000f2\u00020\u000f*\n\u0010\u0010\"\u00020\u00112\u00020\u0011*\n\u0010\u0012\"\u00020\u00132\u00020\u0013¨\u0006A"}, d2 = {"SetStorageSuccess", "Luts/sdk/modules/DCloudUniStorage/SetStorageSuccess;", "SetStorageOptions", "Luts/sdk/modules/DCloudUniStorage/SetStorageOptions;", "GetStorageSuccess", "Luts/sdk/modules/DCloudUniStorage/GetStorageSuccess;", "GetStorageOptions", "Luts/sdk/modules/DCloudUniStorage/GetStorageOptions;", "GetStorageInfoSuccess", "Luts/sdk/modules/DCloudUniStorage/GetStorageInfoSuccess;", "GetStorageInfoOptions", "Luts/sdk/modules/DCloudUniStorage/GetStorageInfoOptions;", "RemoveStorageSuccess", "Luts/sdk/modules/DCloudUniStorage/RemoveStorageSuccess;", "RemoveStorageOptions", "Luts/sdk/modules/DCloudUniStorage/RemoveStorageOptions;", "ClearStorageSuccess", "Luts/sdk/modules/DCloudUniStorage/ClearStorageSuccess;", "ClearStorageOptions", "Luts/sdk/modules/DCloudUniStorage/ClearStorageOptions;", "setStorage", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "options", "", "Luts/sdk/modules/DCloudUniStorage/SetStorage;", "getSetStorage", "()Lkotlin/jvm/functions/Function1;", "setStorageSync", "Lkotlin/Function2;", "", IApp.ConfigProperty.CONFIG_KEY, "", "data", "Luts/sdk/modules/DCloudUniStorage/SetStorageSync;", "getSetStorageSync", "()Lkotlin/jvm/functions/Function2;", "getStorage", "Luts/sdk/modules/DCloudUniStorage/GetStorage;", "getGetStorage", "getStorageSync", "Luts/sdk/modules/DCloudUniStorage/GetStorageSync;", "getGetStorageSync", "getStorageInfo", "Luts/sdk/modules/DCloudUniStorage/GetStorageInfo;", "getGetStorageInfo", "getStorageInfoSync", "Lkotlin/Function0;", "Luts/sdk/modules/DCloudUniStorage/GetStorageInfoSync;", "getGetStorageInfoSync", "()Lkotlin/jvm/functions/Function0;", "removeStorage", "Luts/sdk/modules/DCloudUniStorage/RemoveStorage;", "getRemoveStorage", "removeStorageSync", "Luts/sdk/modules/DCloudUniStorage/RemoveStorageSync;", "getRemoveStorageSync", "clearStorage", AbsoluteConst.JSON_KEY_OPTION, "Luts/sdk/modules/DCloudUniStorage/ClearStorage;", "getClearStorage", "clearStorageSync", "Luts/sdk/modules/DCloudUniStorage/ClearStorageSync;", "getClearStorageSync", "uni-storage_release"}, k = 2, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class UniStorageKt {
    private static final Function1<SetStorageOptions, Unit> setStorage = IndexKt.getSetStorage();
    private static final Function2<String, Object, Unit> setStorageSync = IndexKt.getSetStorageSync();
    private static final Function1<GetStorageOptions, Unit> getStorage = IndexKt.getGetStorage();
    private static final Function1<String, Object> getStorageSync = IndexKt.getGetStorageSync();
    private static final Function1<GetStorageInfoOptions, Unit> getStorageInfo = IndexKt.getGetStorageInfo();
    private static final Function0<GetStorageInfoSuccess> getStorageInfoSync = IndexKt.getGetStorageInfoSync();
    private static final Function1<RemoveStorageOptions, Unit> removeStorage = IndexKt.getRemoveStorage();
    private static final Function1<String, Unit> removeStorageSync = IndexKt.getRemoveStorageSync();
    private static final Function1<ClearStorageOptions, Unit> clearStorage = IndexKt.getClearStorage();
    private static final Function0<Unit> clearStorageSync = IndexKt.getClearStorageSync();

    public static final Function1<SetStorageOptions, Unit> getSetStorage() {
        return setStorage;
    }

    public static final Function2<String, Object, Unit> getSetStorageSync() {
        return setStorageSync;
    }

    public static final Function1<GetStorageOptions, Unit> getGetStorage() {
        return getStorage;
    }

    public static final Function1<String, Object> getGetStorageSync() {
        return getStorageSync;
    }

    public static final Function1<GetStorageInfoOptions, Unit> getGetStorageInfo() {
        return getStorageInfo;
    }

    public static final Function0<GetStorageInfoSuccess> getGetStorageInfoSync() {
        return getStorageInfoSync;
    }

    public static final Function1<RemoveStorageOptions, Unit> getRemoveStorage() {
        return removeStorage;
    }

    public static final Function1<String, Unit> getRemoveStorageSync() {
        return removeStorageSync;
    }

    public static final Function1<ClearStorageOptions, Unit> getClearStorage() {
        return clearStorage;
    }

    public static final Function0<Unit> getClearStorageSync() {
        return clearStorageSync;
    }
}
