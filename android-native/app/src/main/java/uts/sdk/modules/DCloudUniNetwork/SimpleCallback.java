package uts.sdk.modules.DCloudUniNetwork;

import android.os.Looper;
import androidx.core.app.NotificationCompat;
import io.dcloud.uts.ArrayBuffer;
import io.dcloud.uts.Math;
import io.dcloud.uts.NumberKt;
import io.dcloud.uts.StringKt;
import io.dcloud.uts.UTSArray;
import io.dcloud.uts.UTSArrayKt;
import io.dcloud.uts.UTSJSONObject;
import io.dcloud.uts.UTSJSONObjectKt;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.properties.Delegates;
import kotlin.properties.ReadWriteProperty;
import kotlin.reflect.KProperty;
import kotlin.text.StringsKt;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSource;
import okio.Okio;
import uts.sdk.modules.DCloudUniNetwork.NetworkUtil;

/* compiled from: index.kt */
@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u0001B-\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\u0004\b\n\u0010\u000bJ\u0018\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"H\u0016J\u0018\u0010#\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010$\u001a\u00020%H\u0016J\u001e\u0010&\u001a\u0004\u0018\u00010'2\b\u0010(\u001a\u0004\u0018\u00010)2\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003H\u0002J\u001e\u0010*\u001a\u0004\u0018\u00010+2\b\u0010(\u001a\u0004\u0018\u00010)2\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003H\u0002J&\u0010,\u001a\u0004\u0018\u00010'2\u0006\u0010\u0004\u001a\u00020\u00052\b\u0010-\u001a\u0004\u0018\u00010)2\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003H\u0002J&\u0010.\u001a\u0004\u0018\u00010'2\u0006\u0010\u0004\u001a\u00020\u00052\b\u0010-\u001a\u0004\u0018\u00010)2\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003H\u0002J\u0018\u0010/\u001a\u00020\u00072\u0006\u00100\u001a\u00020'2\u0006\u00101\u001a\u00020'H\u0002J\u0010\u00102\u001a\u0002032\u0006\u00104\u001a\u00020'H\u0002J\u0010\u00105\u001a\u00020\u00072\u0006\u00104\u001a\u00020'H\u0002J\u0010\u00106\u001a\u00020\u00072\u0006\u00104\u001a\u00020'H\u0002J\u0010\u00107\u001a\u00020\u00072\u0006\u00104\u001a\u00020'H\u0002J\u0018\u00108\u001a\u00020\u001e2\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u00109\u001a\u00020'H\u0002J \u0010:\u001a\u00020\u001e2\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010;\u001a\u00020'2\u0006\u0010<\u001a\u00020'H\u0002J\u001e\u0010=\u001a\u00020\u001e2\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010>\u001a\b\u0012\u0004\u0012\u00020'0?H\u0002J\u001e\u0010@\u001a\u00020\u001e2\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010>\u001a\b\u0012\u0004\u0012\u00020'0?H\u0002R+\u0010\u0002\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\u00038B@BX\u0082\u008e\u0002¢\u0006\u0012\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R+\u0010\u0004\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\u00058B@BX\u0082\u008e\u0002¢\u0006\u0012\n\u0004\b\u0017\u0010\u0012\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R+\u0010\u0006\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\u00078B@BX\u0082\u008e\u0002¢\u0006\u0012\n\u0004\b\u001c\u0010\u0012\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006A"}, d2 = {"Luts/sdk/modules/DCloudUniNetwork/SimpleCallback;", "Lokhttp3/Callback;", "listener", "Luts/sdk/modules/DCloudUniNetwork/NetworkRequestListener;", "requestTask", "Luts/sdk/modules/DCloudUniNetwork/NetworkRequestTaskImpl;", "enableChunked", "", "looper", "Landroid/os/Looper;", "<init>", "(Luts/sdk/modules/DCloudUniNetwork/NetworkRequestListener;Luts/sdk/modules/DCloudUniNetwork/NetworkRequestTaskImpl;ZLandroid/os/Looper;)V", "<set-?>", "getListener", "()Luts/sdk/modules/DCloudUniNetwork/NetworkRequestListener;", "setListener", "(Luts/sdk/modules/DCloudUniNetwork/NetworkRequestListener;)V", "listener$delegate", "Lkotlin/properties/ReadWriteProperty;", "getRequestTask", "()Luts/sdk/modules/DCloudUniNetwork/NetworkRequestTaskImpl;", "setRequestTask", "(Luts/sdk/modules/DCloudUniNetwork/NetworkRequestTaskImpl;)V", "requestTask$delegate", "getEnableChunked", "()Z", "setEnableChunked", "(Z)V", "enableChunked$delegate", "onResponse", "", NotificationCompat.CATEGORY_CALL, "Lokhttp3/Call;", "response", "Lokhttp3/Response;", "onFailure", "e", "Ljava/io/IOException;", "readInputStreamAsBytes", "", "inputSteam", "Ljava/io/InputStream;", "readInputStream", "", "processStreamWithOkio", "inputStream", "processChunkedResponse", "isValidChunk", "sizeLine", "dataLine", "parseHexSize", "", "lineData", "isHexadecimalLine", "isZeroChunk", "isEmptyLine", "sendData", "data", "sendCombinedData", "data1", "data2", "processPendingLines", "pendingLines", "Lio/dcloud/uts/UTSArray;", "processLinePair", "uni-network_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public class SimpleCallback implements Callback {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.mutableProperty1(new MutablePropertyReference1Impl(SimpleCallback.class, "listener", "getListener()Luts/sdk/modules/DCloudUniNetwork/NetworkRequestListener;", 0)), Reflection.mutableProperty1(new MutablePropertyReference1Impl(SimpleCallback.class, "requestTask", "getRequestTask()Luts/sdk/modules/DCloudUniNetwork/NetworkRequestTaskImpl;", 0)), Reflection.mutableProperty1(new MutablePropertyReference1Impl(SimpleCallback.class, "enableChunked", "getEnableChunked()Z", 0))};

    /* renamed from: enableChunked$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty enableChunked;

    /* renamed from: listener$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty listener;
    private Looper looper;

    /* renamed from: requestTask$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty requestTask;

    private final NetworkRequestListener getListener() {
        return (NetworkRequestListener) this.listener.getValue(this, $$delegatedProperties[0]);
    }

    private final void setListener(NetworkRequestListener networkRequestListener) {
        this.listener.setValue(this, $$delegatedProperties[0], networkRequestListener);
    }

    private final NetworkRequestTaskImpl getRequestTask() {
        return (NetworkRequestTaskImpl) this.requestTask.getValue(this, $$delegatedProperties[1]);
    }

    private final void setRequestTask(NetworkRequestTaskImpl networkRequestTaskImpl) {
        this.requestTask.setValue(this, $$delegatedProperties[1], networkRequestTaskImpl);
    }

    private final boolean getEnableChunked() {
        return ((Boolean) this.enableChunked.getValue(this, $$delegatedProperties[2])).booleanValue();
    }

    private final void setEnableChunked(boolean z) {
        this.enableChunked.setValue(this, $$delegatedProperties[2], Boolean.valueOf(z));
    }

    public SimpleCallback(NetworkRequestListener listener, NetworkRequestTaskImpl requestTask, boolean z, Looper looper) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        Intrinsics.checkNotNullParameter(requestTask, "requestTask");
        this.listener = Delegates.INSTANCE.notNull();
        this.requestTask = Delegates.INSTANCE.notNull();
        this.enableChunked = Delegates.INSTANCE.notNull();
        setListener(listener);
        setRequestTask(requestTask);
        setEnableChunked(z);
        this.looper = looper;
    }

    public /* synthetic */ SimpleCallback(NetworkRequestListener networkRequestListener, NetworkRequestTaskImpl networkRequestTaskImpl, boolean z, Looper looper, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(networkRequestListener, networkRequestTaskImpl, z, (i & 8) != 0 ? null : looper);
    }

    @Override // okhttp3.Callback
    public void onResponse(Call call, Response response) throws IOException {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(response, "response");
        Headers headers = response.headers();
        Intrinsics.checkNotNullExpressionValue(headers, "headers(...)");
        Map<String, List<String>> multimap = headers.toMultimap();
        final Ref.IntRef intRef = new Ref.IntRef();
        intRef.element = response.code();
        boolean z = false;
        UTSJSONObject uTSJSONObject_uO = UTSJSONObjectKt._uO(new Pair[0]);
        uTSJSONObject_uO.set("statusCode", NumberKt.plus(Integer.valueOf(intRef.element), ""));
        NetworkRequestListener listener = getListener();
        if (listener != null) {
            Integer numValueOf = Integer.valueOf(intRef.element);
            Intrinsics.checkNotNull(multimap);
            listener.onHeadersReceived(numValueOf, multimap);
        }
        io.dcloud.uts.Map<Number, Function1<RequestTaskOnHeadersReceivedListenerResult, Unit>> headersReceivedListeners = getRequestTask().getHeadersReceivedListeners();
        if (headersReceivedListeners.size() > 0) {
            NetworkUtil.Companion companion = NetworkUtil.INSTANCE;
            Intrinsics.checkNotNull(multimap);
            final UTSJSONObject uTSJSONObjectConvertHeaders = companion.convertHeaders(multimap);
            headersReceivedListeners.forEach(new Function2() { // from class: uts.sdk.modules.DCloudUniNetwork.SimpleCallback$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return SimpleCallback.onResponse$lambda$0(uTSJSONObjectConvertHeaders, intRef, (Function1) obj, (Number) obj2);
                }
            });
        }
        ResponseBody responseBodyBody = response.body();
        Intrinsics.checkNotNull(responseBodyBody);
        InputStream inputStreamByteStream = responseBodyBody.byteStream();
        Intrinsics.checkNotNull(inputStreamByteStream);
        if (getEnableChunked() && Intrinsics.areEqual(response.header("Transfer-Encoding"), "chunked")) {
            z = true;
        }
        try {
            if (!response.isSuccessful()) {
                uTSJSONObject_uO.set("errorMsg", readInputStream(inputStreamByteStream, getListener()));
            } else if (z) {
                uTSJSONObject_uO.set("originalData", processStreamWithOkio(getRequestTask(), inputStreamByteStream, getListener()));
            } else {
                uTSJSONObject_uO.set("originalData", readInputStreamAsBytes(inputStreamByteStream, getListener()));
            }
            if (listener != null) {
                listener.onComplete(uTSJSONObject_uO);
            }
        } finally {
            try {
                inputStreamByteStream.close();
            } catch (Exception unused) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onResponse$lambda$0(UTSJSONObject uTSJSONObject, Ref.IntRef intRef, Function1<? super RequestTaskOnHeadersReceivedListenerResult, Unit> function1, Number number) {
        function1.invoke2(new RequestTaskOnHeadersReceivedListenerResult(NetworkUtil.INSTANCE.parseCookie(uTSJSONObject), uTSJSONObject, Integer.valueOf(intRef.element)));
        return Unit.INSTANCE;
    }

    @Override // okhttp3.Callback
    public void onFailure(Call call, IOException e) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(e, "e");
        NetworkRequestListener listener = getListener();
        UTSJSONObject uTSJSONObject_uO = UTSJSONObjectKt._uO(new Pair[0]);
        uTSJSONObject_uO.set("statusCode", "-1");
        uTSJSONObject_uO.set("errorCode", "602001");
        uTSJSONObject_uO.set("errorMsg", e.getMessage());
        uTSJSONObject_uO.set("cause", e);
        if (listener != null) {
            listener.onComplete(uTSJSONObject_uO);
        }
    }

    private final byte[] readInputStreamAsBytes(InputStream inputSteam, NetworkRequestListener listener) throws IOException {
        if (inputSteam == null) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Number numberPlus = (Number) 0;
        byte[] bArr = new byte[2048];
        while (true) {
            int i = inputSteam.read(bArr, 0, 2048);
            if (i != -1) {
                byteArrayOutputStream.write(bArr, 0, i);
                numberPlus = NumberKt.plus(numberPlus, Integer.valueOf(i));
                if (listener != null) {
                    listener.onProgress(numberPlus);
                }
            } else {
                byteArrayOutputStream.flush();
                return byteArrayOutputStream.toByteArray();
            }
        }
    }

    private final String readInputStream(InputStream inputSteam, NetworkRequestListener listener) throws IOException {
        if (inputSteam == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputSteam));
        char[] cArr = new char[2048];
        while (true) {
            int i = bufferedReader.read(cArr);
            if (i != -1) {
                sb.append(cArr, 0, i);
                if (listener != null) {
                    listener.onProgress(Integer.valueOf(sb.length()));
                }
            } else {
                bufferedReader.close();
                return sb.toString();
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0, types: [T, uts.sdk.modules.DCloudUniNetwork.SharedStreamBuffer] */
    /* JADX WARN: Type inference failed for: r4v0, types: [T, java.util.concurrent.locks.ReentrantLock] */
    /* JADX WARN: Type inference failed for: r7v7, types: [T, io.dcloud.uts.ArrayBuffer] */
    private final byte[] processStreamWithOkio(final NetworkRequestTaskImpl requestTask, final InputStream inputStream, NetworkRequestListener listener) throws Throwable {
        Thread thread;
        Number totalBytesRead;
        byte[] byteArray;
        boolean z;
        Thread thread2 = null;
        if (inputStream == null) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        objectRef.element = new SharedStreamBuffer();
        final Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
        objectRef2.element = new ReentrantLock();
        try {
            thread = new Thread(new Runnable() { // from class: uts.sdk.modules.DCloudUniNetwork.SimpleCallback$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    SimpleCallback.processStreamWithOkio$lambda$1(inputStream, objectRef2, objectRef);
                }
            });
            try {
                thread.start();
                do {
                    Thread.sleep(100L);
                    Integer num = (Number) 0;
                    ((ReentrantLock) objectRef2.element).lock();
                    try {
                        if (((SharedStreamBuffer) objectRef.element).getHasNewData()) {
                            byteArray = ((SharedStreamBuffer) objectRef.element).getBuffer().toByteArray();
                            totalBytesRead = ((SharedStreamBuffer) objectRef.element).getTotalBytesRead();
                            ((SharedStreamBuffer) objectRef.element).getBuffer().reset();
                            ((SharedStreamBuffer) objectRef.element).setHasNewData(false);
                        } else {
                            totalBytesRead = num;
                            byteArray = null;
                        }
                        z = ((SharedStreamBuffer) objectRef.element).getIsStreamEnded() && !((SharedStreamBuffer) objectRef.element).getHasNewData();
                        if (byteArray != null && byteArray.length > 0) {
                            byteArrayOutputStream.write(byteArray, 0, byteArray.length);
                            if (requestTask.getChunkReceivedListeners().size() > 0) {
                                final Ref.ObjectRef objectRef3 = new Ref.ObjectRef();
                                ArrayBuffer.Companion companion = ArrayBuffer.INSTANCE;
                                ByteBuffer byteBufferWrap = ByteBuffer.wrap(byteArray);
                                Intrinsics.checkNotNullExpressionValue(byteBufferWrap, "wrap(...)");
                                objectRef3.element = companion.fromByteBuffer(byteBufferWrap);
                                new RunnableTask(this.looper, new Function0() { // from class: uts.sdk.modules.DCloudUniNetwork.SimpleCallback$$ExternalSyntheticLambda4
                                    @Override // kotlin.jvm.functions.Function0
                                    public final Object invoke() {
                                        return SimpleCallback.processStreamWithOkio$lambda$3(requestTask, objectRef3);
                                    }
                                }).execute();
                            }
                            if (listener != null) {
                                listener.onProgress(totalBytesRead);
                            }
                        }
                    } finally {
                        ((ReentrantLock) objectRef2.element).unlock();
                    }
                } while (!z);
                byteArrayOutputStream.flush();
                byte[] byteArray2 = byteArrayOutputStream.toByteArray();
                thread.interrupt();
                try {
                    thread.join(1000L);
                } catch (InterruptedException unused) {
                }
                try {
                    if (objectRef.element != 0 && ((SharedStreamBuffer) objectRef.element).getBuffer() != null) {
                        ((SharedStreamBuffer) objectRef.element).getBuffer().close();
                    }
                    byteArrayOutputStream.close();
                } catch (Exception unused2) {
                }
                return byteArray2;
            } catch (Exception unused3) {
                if (thread != null) {
                    thread.interrupt();
                    try {
                        thread.join(1000L);
                    } catch (InterruptedException unused4) {
                    }
                }
                try {
                    if (objectRef.element != 0 && ((SharedStreamBuffer) objectRef.element).getBuffer() != null) {
                        ((SharedStreamBuffer) objectRef.element).getBuffer().close();
                    }
                    byteArrayOutputStream.close();
                } catch (Exception unused5) {
                }
                return null;
            } catch (Throwable th) {
                th = th;
                thread2 = thread;
                if (thread2 != null) {
                    thread2.interrupt();
                    try {
                        thread2.join(1000L);
                    } catch (InterruptedException unused6) {
                    }
                }
                try {
                    if (objectRef.element != 0 && ((SharedStreamBuffer) objectRef.element).getBuffer() != null) {
                        ((SharedStreamBuffer) objectRef.element).getBuffer().close();
                    }
                    byteArrayOutputStream.close();
                    throw th;
                } catch (Exception unused7) {
                    throw th;
                }
            }
        } catch (Exception unused8) {
            thread = null;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void processStreamWithOkio$lambda$1(InputStream inputStream, Ref.ObjectRef<ReentrantLock> objectRef, Ref.ObjectRef<SharedStreamBuffer> objectRef2) {
        BufferedSource bufferedSourceBuffer;
        try {
            bufferedSourceBuffer = Okio.buffer(Okio.source(inputStream));
        } catch (Exception unused) {
            objectRef.element.lock();
            try {
                objectRef2.element.setStreamEnded(true);
                return;
            } finally {
            }
        }
        while (!Thread.interrupted()) {
            if (bufferedSourceBuffer.exhausted()) {
                objectRef.element.lock();
                try {
                    objectRef2.element.setStreamEnded(true);
                    return;
                } finally {
                }
            }
            byte[] bArr = new byte[8192];
            int i = bufferedSourceBuffer.read(bArr);
            if (i <= 0) {
                objectRef.element.lock();
                try {
                    objectRef2.element.setStreamEnded(true);
                    return;
                } finally {
                }
            } else {
                objectRef.element.lock();
                try {
                    objectRef2.element.getBuffer().write(bArr, 0, i);
                    SharedStreamBuffer sharedStreamBuffer = objectRef2.element;
                    sharedStreamBuffer.setTotalBytesRead(NumberKt.plus(sharedStreamBuffer.getTotalBytesRead(), Integer.valueOf(i)));
                    objectRef2.element.setHasNewData(true);
                    objectRef.element.unlock();
                } finally {
                }
            }
            objectRef.element.lock();
            objectRef2.element.setStreamEnded(true);
            return;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit processStreamWithOkio$lambda$3(NetworkRequestTaskImpl networkRequestTaskImpl, final Ref.ObjectRef<ArrayBuffer> objectRef) {
        networkRequestTaskImpl.getChunkReceivedListeners().forEach(new Function2() { // from class: uts.sdk.modules.DCloudUniNetwork.SimpleCallback$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return SimpleCallback.processStreamWithOkio$lambda$3$lambda$2(objectRef, (Function1) obj, (Number) obj2);
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit processStreamWithOkio$lambda$3$lambda$2(Ref.ObjectRef<ArrayBuffer> objectRef, Function1<? super RequestTaskOnChunkReceivedListenerResult, Unit> function1, Number number) {
        function1.invoke2(new RequestTaskOnChunkReceivedListenerResult(objectRef.element));
        return Unit.INSTANCE;
    }

    private final byte[] processChunkedResponse(NetworkRequestTaskImpl requestTask, InputStream inputStream, NetworkRequestListener listener) throws IOException {
        byte[] bArr;
        InputStream inputStream2 = inputStream;
        NetworkRequestListener networkRequestListener = listener;
        byte[] bArr2 = null;
        if (inputStream2 == null) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
        byte[] bArr3 = new byte[10];
        int i = 0;
        Number numberPlus = (Number) 0;
        UTSArray<byte[]> uTSArray = new UTSArray<>();
        Integer.valueOf(0);
        byte[] bArr4 = null;
        boolean z = false;
        int i2 = -1;
        while (true) {
            try {
                try {
                    bArr = bArr2;
                    try {
                        Integer numValueOf = Integer.valueOf(inputStream2.read(bArr3));
                        if (NumberKt.numberEquals(numValueOf, -1)) {
                            break;
                        }
                        byteArrayOutputStream.write(bArr3, i, numValueOf.intValue());
                        numberPlus = NumberKt.plus(numberPlus, numValueOf);
                        if (networkRequestListener != null) {
                            networkRequestListener.onProgress(numberPlus);
                        }
                        if (requestTask.getChunkReceivedListeners().size() > 0) {
                            SimpleCallback simpleCallback = this;
                            Integer numValueOf2 = Integer.valueOf(i);
                            while (NumberKt.compareTo(numValueOf2, numValueOf) < 0) {
                                int iIntValue = NumberKt.and(Byte.valueOf(bArr3[numValueOf2.intValue()]), (Number) 255).intValue();
                                byteArrayOutputStream2.write(iIntValue);
                                if (i2 == 13 && iIntValue == 10) {
                                    byte[] byteArray = byteArrayOutputStream2.toByteArray();
                                    Intrinsics.checkNotNull(byteArray);
                                    if (isZeroChunk(byteArray) && !z) {
                                        bArr4 = new byte[byteArray.length];
                                        System.arraycopy(byteArray, 0, bArr4, 0, byteArray.length);
                                        z = true;
                                    } else if (z) {
                                        processPendingLines(requestTask, uTSArray);
                                        uTSArray = new UTSArray<>();
                                        Intrinsics.checkNotNull(byteArray);
                                        if (isEmptyLine(byteArray)) {
                                            Intrinsics.checkNotNull(bArr4);
                                            Intrinsics.checkNotNull(byteArray);
                                            sendCombinedData(requestTask, bArr4, byteArray);
                                        } else {
                                            byte[] bArr5 = new byte[byteArray.length];
                                            System.arraycopy(byteArray, 0, bArr5, 0, byteArray.length);
                                            Intrinsics.checkNotNull(bArr4);
                                            processLinePair(requestTask, UTSArrayKt._uA(bArr4, bArr5));
                                        }
                                        bArr4 = bArr;
                                        z = false;
                                    } else {
                                        byte[] bArr6 = new byte[byteArray.length];
                                        System.arraycopy(byteArray, 0, bArr6, 0, byteArray.length);
                                        uTSArray.push(bArr6);
                                        if (NumberKt.numberEquals(uTSArray.getLength(), 2)) {
                                            processLinePair(requestTask, uTSArray);
                                            uTSArray = new UTSArray<>();
                                        }
                                    }
                                    byteArrayOutputStream2.reset();
                                }
                                numValueOf2 = NumberKt.inc(numValueOf2);
                                i2 = iIntValue;
                            }
                        }
                        if (NumberKt.numberEquals(numValueOf, -1)) {
                            break;
                        }
                        inputStream2 = inputStream;
                        networkRequestListener = listener;
                        bArr2 = bArr;
                        i = 0;
                    } catch (Exception unused) {
                        return bArr;
                    }
                } finally {
                    try {
                        byteArrayOutputStream.close();
                        byteArrayOutputStream2.close();
                    } catch (Exception unused2) {
                    }
                }
            } catch (Exception unused3) {
                bArr = bArr2;
            }
        }
        processPendingLines(requestTask, uTSArray);
        if (z && bArr4 != null) {
            sendData(requestTask, bArr4);
        }
        if (requestTask.getChunkReceivedListeners().size() > 0 && byteArrayOutputStream2.size() > 0) {
            byte[] byteArray2 = byteArrayOutputStream2.toByteArray();
            Intrinsics.checkNotNullExpressionValue(byteArray2, "toByteArray(...)");
            sendData(requestTask, byteArray2);
        }
        byteArrayOutputStream.flush();
        byte[] byteArray3 = byteArrayOutputStream.toByteArray();
        try {
            byteArrayOutputStream.close();
            byteArrayOutputStream2.close();
        } catch (Exception unused4) {
        }
        return byteArray3;
    }

    private final boolean isValidChunk(byte[] sizeLine, byte[] dataLine) {
        int hexSize;
        return isHexadecimalLine(sizeLine) && (hexSize = parseHexSize(sizeLine)) >= 0 && NumberKt.compareTo(Math.abs(Integer.valueOf((dataLine.length - 2) - hexSize)), (Number) 2) <= 0;
    }

    private final int parseHexSize(byte[] lineData) {
        if (lineData.length <= 2) {
            return -1;
        }
        int length = lineData.length - 2;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length && lineData[i] != 59; i++) {
            sb.append(StringKt.fromCharCode(StringCompanionObject.INSTANCE, Integer.valueOf(lineData[i])));
        }
        if (sb.length() == 0) {
            return -1;
        }
        try {
            String string = sb.toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            return Integer.parseInt(StringsKt.trim((CharSequence) string).toString(), 16);
        } catch (Exception unused) {
            return -1;
        }
    }

    private final boolean isHexadecimalLine(byte[] lineData) {
        int length;
        if (lineData.length <= 2 || (length = lineData.length - 2) <= 0) {
            return false;
        }
        int i = 0;
        boolean z = false;
        while (i < length) {
            byte b = lineData[i];
            if (b == 32 || b == 59) {
                break;
            }
            if ((b < 48 || b > 57) && ((b < 65 || b > 70) && (b < 97 || b > 102))) {
                return false;
            }
            i++;
            z = true;
        }
        return z;
    }

    private final boolean isZeroChunk(byte[] lineData) {
        if (lineData.length < 3 || lineData[0] != 48) {
            return false;
        }
        byte b = lineData[1];
        if (b != 59 && b != 13) {
            return false;
        }
        if (b == 13 && lineData.length >= 3) {
            return lineData[2] == 10;
        }
        if (b == 59) {
            for (int i = 2; i < lineData.length - 1; i++) {
                if (lineData[i] == 13 && lineData[i + 1] == 10) {
                    return true;
                }
            }
        }
        return false;
    }

    private final boolean isEmptyLine(byte[] lineData) {
        return lineData.length == 2 && lineData[0] == 13 && lineData[1] == 10;
    }

    /* JADX WARN: Type inference failed for: r5v2, types: [T, io.dcloud.uts.ArrayBuffer] */
    private final void sendData(NetworkRequestTaskImpl requestTask, byte[] data) {
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        ArrayBuffer.Companion companion = ArrayBuffer.INSTANCE;
        ByteBuffer byteBufferWrap = ByteBuffer.wrap(data);
        Intrinsics.checkNotNullExpressionValue(byteBufferWrap, "wrap(...)");
        objectRef.element = companion.fromByteBuffer(byteBufferWrap);
        requestTask.getChunkReceivedListeners().forEach(new Function2() { // from class: uts.sdk.modules.DCloudUniNetwork.SimpleCallback$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return SimpleCallback.sendData$lambda$8(objectRef, (Function1) obj, (Number) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit sendData$lambda$8(Ref.ObjectRef<ArrayBuffer> objectRef, Function1<? super RequestTaskOnChunkReceivedListenerResult, Unit> function1, Number number) {
        function1.invoke2(new RequestTaskOnChunkReceivedListenerResult(objectRef.element));
        return Unit.INSTANCE;
    }

    private final void sendCombinedData(NetworkRequestTaskImpl requestTask, byte[] data1, byte[] data2) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            byteArrayOutputStream.write(data1, 0, data1.length);
            byteArrayOutputStream.write(data2, 0, data2.length);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            Intrinsics.checkNotNull(byteArray);
            sendData(requestTask, byteArray);
        } finally {
            try {
                byteArrayOutputStream.close();
            } catch (Exception unused) {
            }
        }
    }

    private final void processPendingLines(NetworkRequestTaskImpl requestTask, UTSArray<byte[]> pendingLines) {
        if (NumberKt.numberEquals(pendingLines.getLength(), 0)) {
            return;
        }
        for (Number numberInc = (Number) 0; NumberKt.compareTo(numberInc, pendingLines.getLength()) < 0; numberInc = NumberKt.inc(numberInc)) {
            sendData(requestTask, pendingLines.get(numberInc));
        }
    }

    private final void processLinePair(NetworkRequestTaskImpl requestTask, UTSArray<byte[]> pendingLines) throws IOException {
        if (NumberKt.numberEquals(pendingLines.getLength(), 2)) {
            byte[] bArr = pendingLines.get(0);
            Intrinsics.checkNotNullExpressionValue(bArr, "get(...)");
            byte[] bArr2 = pendingLines.get(1);
            Intrinsics.checkNotNullExpressionValue(bArr2, "get(...)");
            if (isValidChunk(bArr, bArr2)) {
                byte[] bArr3 = pendingLines.get(0);
                Intrinsics.checkNotNullExpressionValue(bArr3, "get(...)");
                byte[] bArr4 = pendingLines.get(1);
                Intrinsics.checkNotNullExpressionValue(bArr4, "get(...)");
                sendCombinedData(requestTask, bArr3, bArr4);
                return;
            }
            processPendingLines(requestTask, pendingLines);
        }
    }
}
