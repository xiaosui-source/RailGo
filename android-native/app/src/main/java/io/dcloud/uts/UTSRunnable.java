package io.dcloud.uts;

import com.taobao.weex.common.Constants;
import kotlin.Metadata;

/* compiled from: UTSTimer.kt */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\n\u001a\u00020\u000bH\u0016J\b\u0010\f\u001a\u00020\u000bH&R\u001a\u0010\u0004\u001a\u00020\u0005X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\t¨\u0006\r"}, d2 = {"Lio/dcloud/uts/UTSRunnable;", "Ljava/lang/Runnable;", "<init>", "()V", Constants.Value.STOP, "", "getStop", "()Z", "setStop", "(Z)V", "run", "", "doSth", "utsplugin_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public abstract class UTSRunnable implements Runnable {
    private boolean stop;

    public abstract void doSth();

    public boolean getStop() {
        return this.stop;
    }

    public void setStop(boolean z) {
        this.stop = z;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (getStop()) {
            return;
        }
        doSth();
    }
}
