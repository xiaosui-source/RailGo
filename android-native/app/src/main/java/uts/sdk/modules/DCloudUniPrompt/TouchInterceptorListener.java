package uts.sdk.modules.DCloudUniPrompt;

import android.view.MotionEvent;
import android.view.View;
import com.alibaba.android.bindingx.core.internal.BindingXConstants;
import io.dcloud.common.util.CreateShortResultReceiver;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.properties.Delegates;
import kotlin.properties.ReadWriteProperty;
import kotlin.reflect.KProperty;

/* compiled from: index.kt */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\u0011\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0018\u0010\f\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016R+\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00038V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\b\n\u0010\u000b\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\u0005¨\u0006\u0011"}, d2 = {"Luts/sdk/modules/DCloudUniPrompt/TouchInterceptorListener;", "Landroid/view/View$OnTouchListener;", BindingXConstants.STATE_INTERCEPTOR, "", "<init>", "(Z)V", "<set-?>", "getInterceptor", "()Z", "setInterceptor", "interceptor$delegate", "Lkotlin/properties/ReadWriteProperty;", "onTouch", CreateShortResultReceiver.KEY_VERSIONNAME, "Landroid/view/View;", "event", "Landroid/view/MotionEvent;", "uni-prompt_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public class TouchInterceptorListener implements View.OnTouchListener {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.mutableProperty1(new MutablePropertyReference1Impl(TouchInterceptorListener.class, BindingXConstants.STATE_INTERCEPTOR, "getInterceptor()Z", 0))};

    /* renamed from: interceptor$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty interceptor = Delegates.INSTANCE.notNull();

    public boolean getInterceptor() {
        return ((Boolean) this.interceptor.getValue(this, $$delegatedProperties[0])).booleanValue();
    }

    public void setInterceptor(boolean z) {
        this.interceptor.setValue(this, $$delegatedProperties[0], Boolean.valueOf(z));
    }

    public TouchInterceptorListener(boolean z) {
        setInterceptor(z);
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View v, MotionEvent event) {
        Intrinsics.checkNotNullParameter(v, "v");
        Intrinsics.checkNotNullParameter(event, "event");
        return getInterceptor();
    }
}
