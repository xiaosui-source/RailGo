package io.dcloud.uts.component;

import android.view.View;
import com.taobao.weex.ui.component.WXComponent;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: IUTSComWrap.kt */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002J\u001c\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\bH\u0016¨\u0006\tÀ\u0006\u0003"}, d2 = {"Lio/dcloud/uts/component/IUTSComWrap;", "T", "", "wrapComponentHostView", "", "rootView", "Landroid/view/View;", "hostCom", "Lcom/taobao/weex/ui/component/WXComponent;", "utsplugin_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public interface IUTSComWrap<T> {
    void wrapComponentHostView(View rootView, WXComponent<?> hostCom);

    /* compiled from: IUTSComWrap.kt */
    /* renamed from: io.dcloud.uts.component.IUTSComWrap$-CC, reason: invalid class name */
    public final /* synthetic */ class CC {
        public static void $default$wrapComponentHostView(IUTSComWrap _this, View rootView, WXComponent hostCom) {
            Intrinsics.checkNotNullParameter(rootView, "rootView");
            Intrinsics.checkNotNullParameter(hostCom, "hostCom");
        }
    }

    /* compiled from: IUTSComWrap.kt */
    @Metadata(k = 3, mv = {2, 2, 0}, xi = 48)
    public static final class DefaultImpls {
        @Deprecated
        public static <T> void wrapComponentHostView(IUTSComWrap<T> iUTSComWrap, View rootView, WXComponent<?> hostCom) {
            Intrinsics.checkNotNullParameter(rootView, "rootView");
            Intrinsics.checkNotNullParameter(hostCom, "hostCom");
            CC.$default$wrapComponentHostView(iUTSComWrap, rootView, hostCom);
        }
    }
}
