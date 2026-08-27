package uts.sdk.modules.DCloudUniNetwork;

import java.io.ByteArrayOutputStream;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: index.kt */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0004\n\u0002\b\b\b\u0016\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003R\u001a\u0010\u0004\u001a\u00020\u0005X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u000bX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\u0010X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0015\u001a\u00020\u000bX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\f\"\u0004\b\u0017\u0010\u000e¨\u0006\u0018"}, d2 = {"Luts/sdk/modules/DCloudUniNetwork/SharedStreamBuffer;", "", "<init>", "()V", "buffer", "Ljava/io/ByteArrayOutputStream;", "getBuffer", "()Ljava/io/ByteArrayOutputStream;", "setBuffer", "(Ljava/io/ByteArrayOutputStream;)V", "isStreamEnded", "", "()Z", "setStreamEnded", "(Z)V", "totalBytesRead", "", "getTotalBytesRead", "()Ljava/lang/Number;", "setTotalBytesRead", "(Ljava/lang/Number;)V", "hasNewData", "getHasNewData", "setHasNewData", "uni-network_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public class SharedStreamBuffer {
    private boolean hasNewData;
    private boolean isStreamEnded;
    private ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    private Number totalBytesRead = (Number) 0;

    public ByteArrayOutputStream getBuffer() {
        return this.buffer;
    }

    public void setBuffer(ByteArrayOutputStream byteArrayOutputStream) {
        Intrinsics.checkNotNullParameter(byteArrayOutputStream, "<set-?>");
        this.buffer = byteArrayOutputStream;
    }

    /* renamed from: isStreamEnded, reason: from getter */
    public boolean getIsStreamEnded() {
        return this.isStreamEnded;
    }

    public void setStreamEnded(boolean z) {
        this.isStreamEnded = z;
    }

    public Number getTotalBytesRead() {
        return this.totalBytesRead;
    }

    public void setTotalBytesRead(Number number) {
        Intrinsics.checkNotNullParameter(number, "<set-?>");
        this.totalBytesRead = number;
    }

    public boolean getHasNewData() {
        return this.hasNewData;
    }

    public void setHasNewData(boolean z) {
        this.hasNewData = z;
    }
}
