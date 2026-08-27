package uts.sdk.modules.DCloudUniNetwork;

import java.io.IOException;
import java.io.InputStream;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.internal.Util;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

/* compiled from: index.kt */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B!\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0004\b\b\u0010\tJ\b\u0010\n\u001a\u00020\u0005H\u0016J\b\u0010\u000b\u001a\u00020\u0003H\u0016J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016R\u0010\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Luts/sdk/modules/DCloudUniNetwork/InputStreamRequestBody;", "Lokhttp3/RequestBody;", "mediaType", "Lokhttp3/MediaType;", "length", "", "inputStream", "Ljava/io/InputStream;", "<init>", "(Lokhttp3/MediaType;JLjava/io/InputStream;)V", "contentLength", "contentType", "writeTo", "", "sink", "Lokio/BufferedSink;", "uni-network_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public class InputStreamRequestBody extends RequestBody {
    private InputStream inputStream;
    private long length;
    private MediaType mediaType;

    public InputStreamRequestBody(MediaType mediaType, long j, InputStream inputStream) {
        Intrinsics.checkNotNullParameter(mediaType, "mediaType");
        Intrinsics.checkNotNullParameter(inputStream, "inputStream");
        this.mediaType = mediaType;
        this.length = j;
        this.inputStream = inputStream;
    }

    @Override // okhttp3.RequestBody
    /* renamed from: contentLength, reason: from getter */
    public long getLength() {
        return this.length;
    }

    @Override // okhttp3.RequestBody
    public MediaType contentType() {
        MediaType mediaType = this.mediaType;
        if (mediaType != null) {
            return mediaType;
        }
        MediaType mediaType2 = MediaType.parse("application/octet-stream");
        Intrinsics.checkNotNull(mediaType2);
        return mediaType2;
    }

    @Override // okhttp3.RequestBody
    public void writeTo(BufferedSink sink) throws IOException {
        Intrinsics.checkNotNullParameter(sink, "sink");
        Source source = null;
        try {
            source = Okio.source(this.inputStream);
            sink.writeAll(source);
        } catch (Throwable unused) {
        }
        Util.closeQuietly(source);
    }
}
