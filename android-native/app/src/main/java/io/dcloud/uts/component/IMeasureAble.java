package io.dcloud.uts.component;

import io.dcloud.common.constant.AbsoluteConst;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.ReplaceWith;

/* compiled from: UTSComponent.kt */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u0006H&J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003H'¨\u0006\u0007À\u0006\u0003"}, d2 = {"Lio/dcloud/uts/component/IMeasureAble;", "", "NVMeasure", "Lio/dcloud/uts/component/UTSSize;", AbsoluteConst.JSON_KEY_SIZE, "mode", "Lio/dcloud/uts/component/UTSMeasureMode;", "utsplugin_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public interface IMeasureAble {
    @Deprecated(message = "use NVMeasure(size:UTSSize,mode:UTSMeasureMode) instead", replaceWith = @ReplaceWith(expression = "NVMeasure(size,mode)", imports = {}))
    UTSSize NVMeasure(UTSSize size);

    UTSSize NVMeasure(UTSSize size, UTSMeasureMode mode);
}
