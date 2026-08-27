package uts.sdk.modules.DCloudUniGetAccessibilityInfo;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.Activity;
import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityManager;
import com.taobao.weex.el.parse.Operators;
import io.dcloud.common.DHInterface.IApp;
import io.dcloud.uts.UTSAndroid;
import io.dcloud.uts.UTSArray;
import io.dcloud.uts.UTSJSONObject;
import io.dcloud.uts.UTSJSONObjectKt;
import java.util.List;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: index.kt */
@Metadata(d1 = {"\u0000(\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u001a\u0016\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f\u001a\u0006\u0010\r\u001a\u00020\u0002\"\u001b\u0010\u0003\u001a\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006*\u0016\u0010\u0000\"\b\u0012\u0004\u0012\u00020\u00020\u00012\b\u0012\u0004\u0012\u00020\u00020\u0001¨\u0006\u000e"}, d2 = {"GetAccessibilityInfo", "Lkotlin/Function0;", "Lio/dcloud/uts/UTSJSONObject;", "getAccessibilityInfo", "Luts/sdk/modules/DCloudUniGetAccessibilityInfo/GetAccessibilityInfo;", "getGetAccessibilityInfo", "()Lkotlin/jvm/functions/Function0;", "isAccessibilitySettingsOn", "", "context", "Landroid/content/Context;", "service", "", "getAccessibilityInfoByJs", "uni-getAccessibilityInfo_release"}, k = 2, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class IndexKt {
    private static final Function0<UTSJSONObject> getAccessibilityInfo = new Function0() { // from class: uts.sdk.modules.DCloudUniGetAccessibilityInfo.IndexKt$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return IndexKt.getAccessibilityInfo$lambda$1();
        }
    };

    public static final Function0<UTSJSONObject> getGetAccessibilityInfo() {
        return getAccessibilityInfo;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final UTSJSONObject getAccessibilityInfo$lambda$1() {
        Activity uniActivity = UTSAndroid.INSTANCE.getUniActivity();
        Intrinsics.checkNotNull(uniActivity);
        Intrinsics.checkNotNull(uniActivity);
        Object systemService = uniActivity.getSystemService("accessibility");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.view.accessibility.AccessibilityManager");
        AccessibilityManager accessibilityManager = (AccessibilityManager) systemService;
        List<AccessibilityServiceInfo> installedAccessibilityServiceList = accessibilityManager.getInstalledAccessibilityServiceList();
        UTSArray uTSArray = new UTSArray();
        for (int i = 0; i < installedAccessibilityServiceList.size(); i++) {
            String id = installedAccessibilityServiceList.get(i).getId();
            Intrinsics.checkNotNullExpressionValue(id, "getId(...)");
            if (isAccessibilitySettingsOn(uniActivity, id)) {
                AccessibilityServiceInfo accessibilityServiceInfo = installedAccessibilityServiceList.get(i);
                Intrinsics.checkNotNull(accessibilityServiceInfo, "null cannot be cast to non-null type android.accessibilityservice.AccessibilityServiceInfo");
                AccessibilityServiceInfo accessibilityServiceInfo2 = accessibilityServiceInfo;
                uTSArray.add(UTSJSONObjectKt._uO(TuplesKt.to("id", accessibilityServiceInfo2.getId()), TuplesKt.to("packageNames", accessibilityServiceInfo2.packageNames)));
            }
        }
        return UTSJSONObjectKt._uO(TuplesKt.to("enabled", Boolean.valueOf(accessibilityManager.isEnabled())), TuplesKt.to(IApp.ConfigProperty.CONFIG_SERVICES, uTSArray));
    }

    public static final boolean isAccessibilitySettingsOn(Context context, String service) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(service, "service");
        TextUtils.SimpleStringSplitter simpleStringSplitter = new TextUtils.SimpleStringSplitter(Operators.CONDITION_IF_MIDDLE);
        String string = Settings.Secure.getString(context.getApplicationContext().getContentResolver(), "enabled_accessibility_services");
        if (string == null) {
            return false;
        }
        simpleStringSplitter.setString(string);
        while (simpleStringSplitter.hasNext()) {
            String next = simpleStringSplitter.next();
            Intrinsics.checkNotNull(next, "null cannot be cast to non-null type kotlin.String");
            if (Intrinsics.areEqual(next, service)) {
                return true;
            }
        }
        return false;
    }

    public static final UTSJSONObject getAccessibilityInfoByJs() {
        return getAccessibilityInfo.invoke();
    }
}
