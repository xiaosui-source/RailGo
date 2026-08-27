package uts.sdk.modules.DCloudUniNetwork;

import io.dcloud.uts.UTSArray;
import io.dcloud.uts.UTSIteratorKt;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.ArrayIteratorKt;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Dns;

/* compiled from: index.kt */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0016\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\t"}, d2 = {"Luts/sdk/modules/DCloudUniNetwork/OKDns;", "Lokhttp3/Dns;", "<init>", "()V", "lookup", "", "Ljava/net/InetAddress;", "hostName", "", "uni-network_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public class OKDns implements Dns {
    @Override // okhttp3.Dns
    public List<InetAddress> lookup(String hostName) throws UnknownHostException {
        Intrinsics.checkNotNullParameter(hostName, "hostName");
        try {
            UTSArray uTSArray = new UTSArray();
            Iterator it = ArrayIteratorKt.iterator((Object[]) UTSIteratorKt.resolveUTSKeyIterator(InetAddress.getAllByName(hostName)));
            while (it.hasNext()) {
                InetAddress inetAddress = (InetAddress) it.next();
                if (inetAddress instanceof Inet4Address) {
                    uTSArray.unshift(inetAddress);
                } else {
                    Intrinsics.checkNotNull(inetAddress);
                    uTSArray.push(inetAddress);
                }
            }
            return uTSArray;
        } catch (Exception e) {
            UnknownHostException unknownHostException = new UnknownHostException("error");
            unknownHostException.initCause(e);
            throw unknownHostException;
        }
    }
}
