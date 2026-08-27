package uts.sdk.modules.DCloudUniNetwork;

import androidx.core.app.NotificationCompat;
import io.dcloud.uniapp.SourceError;
import io.dcloud.uts.NumberKt;
import io.dcloud.uts.UTSJSONObject;
import io.dcloud.uts.UTSJSONObjectKt;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

/* compiled from: index.kt */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\u0011\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0018\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0018\u0010\f\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u000eH\u0016R\u0010\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Luts/sdk/modules/DCloudUniNetwork/SimpleUploadCallback;", "Lokhttp3/Callback;", "listener", "Luts/sdk/modules/DCloudUniNetwork/NetworkUploadFileListener;", "<init>", "(Luts/sdk/modules/DCloudUniNetwork/NetworkUploadFileListener;)V", "onFailure", "", NotificationCompat.CATEGORY_CALL, "Lokhttp3/Call;", "exception", "Ljava/io/IOException;", "onResponse", "response", "Lokhttp3/Response;", "uni-network_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public class SimpleUploadCallback implements Callback {
    private NetworkUploadFileListener listener;

    public SimpleUploadCallback(NetworkUploadFileListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.listener = listener;
    }

    @Override // okhttp3.Callback
    public void onFailure(Call call, IOException exception) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(exception, "exception");
        UTSJSONObject uTSJSONObject_uO = UTSJSONObjectKt._uO(new Pair[0]);
        uTSJSONObject_uO.set("statusCode", "-1");
        uTSJSONObject_uO.set("errorCode", "-1");
        uTSJSONObject_uO.set("errorMsg", exception.getMessage());
        uTSJSONObject_uO.set("cause", new SourceError(NumberKt.toString_number_nullable$default(exception.getCause(), (Number) null, 1, (Object) null)));
        NetworkUploadFileListener networkUploadFileListener = this.listener;
        if (networkUploadFileListener != null) {
            networkUploadFileListener.onComplete(uTSJSONObject_uO);
        }
    }

    @Override // okhttp3.Callback
    public void onResponse(Call call, Response response) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(response, "response");
        UTSJSONObject uTSJSONObject_uO = UTSJSONObjectKt._uO(new Pair[0]);
        uTSJSONObject_uO.set("statusCode", NumberKt.plus(Integer.valueOf(response.code()), ""));
        ResponseBody responseBodyBody = response.body();
        uTSJSONObject_uO.set("data", responseBodyBody != null ? responseBodyBody.string() : null);
        NetworkUploadFileListener networkUploadFileListener = this.listener;
        if (networkUploadFileListener != null) {
            networkUploadFileListener.onComplete(uTSJSONObject_uO);
        }
    }
}
