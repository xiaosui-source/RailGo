package uts.sdk.modules.DCloudUniNetwork;

import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;
import okio.Okio;

/* compiled from: index.kt */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\u0019\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0004\b\u0005\u0010\u0006J\b\u0010\u0007\u001a\u00020\bH\u0016J\b\u0010\t\u001a\u00020\nH\u0016J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016R\u0010\u0010\u0002\u001a\u0004\u0018\u00010\u0001X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Luts/sdk/modules/DCloudUniNetwork/ProgressRequestBody;", "Lokhttp3/RequestBody;", "requestBody", "listener", "Luts/sdk/modules/DCloudUniNetwork/UploadProgressListener;", "<init>", "(Lokhttp3/RequestBody;Luts/sdk/modules/DCloudUniNetwork/UploadProgressListener;)V", "contentLength", "", "contentType", "Lokhttp3/MediaType;", "writeTo", "", "sink", "Lokio/BufferedSink;", "uni-network_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public class ProgressRequestBody extends RequestBody {
    private UploadProgressListener listener;
    private RequestBody requestBody;

    public ProgressRequestBody(RequestBody requestBody, UploadProgressListener listener) {
        Intrinsics.checkNotNullParameter(requestBody, "requestBody");
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.requestBody = requestBody;
        this.listener = listener;
    }

    @Override // okhttp3.RequestBody
    public long contentLength() {
        RequestBody requestBody = this.requestBody;
        if (requestBody != null) {
            return requestBody.contentLength();
        }
        return 0L;
    }

    @Override // okhttp3.RequestBody
    public MediaType contentType() {
        RequestBody requestBody = this.requestBody;
        if (requestBody == null) {
            MediaType mediaType = MediaType.parse("application/octet-stream");
            Intrinsics.checkNotNull(mediaType);
            return mediaType;
        }
        MediaType mediaTypeContentType = requestBody.contentType();
        Intrinsics.checkNotNull(mediaTypeContentType);
        return mediaTypeContentType;
    }

    @Override // okhttp3.RequestBody
    public void writeTo(BufferedSink sink) throws IOException {
        Intrinsics.checkNotNullParameter(sink, "sink");
        Long lValueOf = Long.valueOf(contentLength());
        UploadProgressListener uploadProgressListener = this.listener;
        Intrinsics.checkNotNull(uploadProgressListener);
        BufferedSink bufferedSinkBuffer = Okio.buffer(new CountingSink(sink, lValueOf, uploadProgressListener));
        RequestBody requestBody = this.requestBody;
        if (requestBody != null) {
            requestBody.writeTo(bufferedSinkBuffer);
        }
        bufferedSinkBuffer.flush();
    }
}
