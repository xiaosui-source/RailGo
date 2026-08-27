package uts.sdk.modules.DCloudUniGetDeviceInfo;

import kotlin.Metadata;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.properties.Delegates;
import kotlin.properties.ReadWriteProperty;
import kotlin.reflect.KProperty;

/* compiled from: index.kt */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000e\b\u0016\u0018\u00002\u00020\u0001B\u001b\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0004\b\u0006\u0010\u0007R+\u0010\u0002\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\u00038V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\b\r\u0010\u000e\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012¨\u0006\u0013"}, d2 = {"Luts/sdk/modules/DCloudUniGetDeviceInfo/CheckResult;", "", "result", "", "value", "", "<init>", "(ILjava/lang/String;)V", "<set-?>", "getResult", "()I", "setResult", "(I)V", "result$delegate", "Lkotlin/properties/ReadWriteProperty;", "getValue", "()Ljava/lang/String;", "setValue", "(Ljava/lang/String;)V", "uni-getDeviceInfo_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public class CheckResult {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.mutableProperty1(new MutablePropertyReference1Impl(CheckResult.class, "result", "getResult()I", 0))};

    /* renamed from: result$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty result = Delegates.INSTANCE.notNull();
    private String value;

    public int getResult() {
        return ((Number) this.result.getValue(this, $$delegatedProperties[0])).intValue();
    }

    public void setResult(int i) {
        this.result.setValue(this, $$delegatedProperties[0], Integer.valueOf(i));
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String str) {
        this.value = str;
    }

    public CheckResult(int i, String str) {
        setResult(i);
        setValue(str);
    }
}
