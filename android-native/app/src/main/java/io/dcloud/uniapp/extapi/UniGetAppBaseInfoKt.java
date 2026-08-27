package io.dcloud.uniapp.extapi;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import uts.sdk.modules.DCloudUniGetAppBaseInfo.GetAppBaseInfoOptions;
import uts.sdk.modules.DCloudUniGetAppBaseInfo.GetAppBaseInfoResult;
import uts.sdk.modules.DCloudUniGetAppBaseInfo.IndexKt;

/* compiled from: uniGetAppBaseInfo.kt */
@Metadata(d1 = {"\u0000\"\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\"2\u0010\u0004\u001a#\u0012\u0015\u0012\u0013\u0018\u00010\u0001¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\b\u0012\u0004\u0012\u00020\u00030\u0005j\u0002`\t¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b*\n\u0010\u0000\"\u00020\u00012\u00020\u0001*\n\u0010\u0002\"\u00020\u00032\u00020\u0003¨\u0006\f"}, d2 = {"GetAppBaseInfoOptions", "Luts/sdk/modules/DCloudUniGetAppBaseInfo/GetAppBaseInfoOptions;", "GetAppBaseInfoResult", "Luts/sdk/modules/DCloudUniGetAppBaseInfo/GetAppBaseInfoResult;", "getAppBaseInfo", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "options", "Luts/sdk/modules/DCloudUniGetAppBaseInfo/GetAppBaseInfo;", "getGetAppBaseInfo", "()Lkotlin/jvm/functions/Function1;", "uni-getAppBaseInfo_release"}, k = 2, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class UniGetAppBaseInfoKt {
    private static final Function1<GetAppBaseInfoOptions, GetAppBaseInfoResult> getAppBaseInfo = IndexKt.getGetAppBaseInfo();

    public static final Function1<GetAppBaseInfoOptions, GetAppBaseInfoResult> getGetAppBaseInfo() {
        return getAppBaseInfo;
    }
}
