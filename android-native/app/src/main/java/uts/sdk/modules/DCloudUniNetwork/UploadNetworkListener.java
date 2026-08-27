package uts.sdk.modules.DCloudUniNetwork;

import android.os.Looper;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.uniapp.SourceError;
import io.dcloud.uts.UTSJSONObject;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;

/* compiled from: index.kt */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\u0011\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0010\u0010\u0015\u001a\u00020\r2\u0006\u0010\u0016\u001a\u00020\tH\u0016J\u0010\u0010\u0017\u001a\u00020\r2\u0006\u0010\u0018\u001a\u00020\u0019H\u0016R\u0010\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R?\u0010\u0006\u001a'\u0012#\u0012!\u0012\u0013\u0012\u00110\t¢\u0006\f\b\n\u0012\b\b\u000b\u0012\u0004\b\b(\f\u0012\u0004\u0012\u00020\r0\bj\u0002`\u000e0\u0007X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001a"}, d2 = {"Luts/sdk/modules/DCloudUniNetwork/UploadNetworkListener;", "Luts/sdk/modules/DCloudUniNetwork/NetworkUploadFileListener;", "param", "Luts/sdk/modules/DCloudUniNetwork/UploadFileOptions;", "<init>", "(Luts/sdk/modules/DCloudUniNetwork/UploadFileOptions;)V", "progressListeners", "Ljava/util/ArrayList;", "Lkotlin/Function1;", "Luts/sdk/modules/DCloudUniNetwork/OnProgressUpdateResult;", "Lkotlin/ParameterName;", "name", "result", "", "Luts/sdk/modules/DCloudUniNetwork/UploadFileProgressUpdateCallback;", "getProgressListeners", "()Ljava/util/ArrayList;", "setProgressListeners", "(Ljava/util/ArrayList;)V", "looper", "Landroid/os/Looper;", "onProgress", "progressUpdate", "onComplete", AbsoluteConst.JSON_KEY_OPTION, "Lio/dcloud/uts/UTSJSONObject;", "uni-network_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public class UploadNetworkListener implements NetworkUploadFileListener {
    private Looper looper;
    private UploadFileOptions param;
    private ArrayList<Function1<OnProgressUpdateResult, Unit>> progressListeners;

    @Override // uts.sdk.modules.DCloudUniNetwork.NetworkUploadFileListener
    public ArrayList<Function1<OnProgressUpdateResult, Unit>> getProgressListeners() {
        return this.progressListeners;
    }

    @Override // uts.sdk.modules.DCloudUniNetwork.NetworkUploadFileListener
    public void setProgressListeners(ArrayList<Function1<OnProgressUpdateResult, Unit>> arrayList) {
        Intrinsics.checkNotNullParameter(arrayList, "<set-?>");
        this.progressListeners = arrayList;
    }

    public UploadNetworkListener(UploadFileOptions param) {
        Intrinsics.checkNotNullParameter(param, "param");
        this.progressListeners = new ArrayList<>();
        this.param = param;
        this.looper = Looper.myLooper();
    }

    @Override // uts.sdk.modules.DCloudUniNetwork.NetworkUploadFileListener
    public void onProgress(final OnProgressUpdateResult progressUpdate) {
        Intrinsics.checkNotNullParameter(progressUpdate, "progressUpdate");
        if (getProgressListeners().size() != 0) {
            new RunnableTask__1(this.looper, new Function0() { // from class: uts.sdk.modules.DCloudUniNetwork.UploadNetworkListener$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return UploadNetworkListener.onProgress$lambda$1(this.f$0, progressUpdate);
                }
            }).execute();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onProgress$lambda$1(UploadNetworkListener uploadNetworkListener, OnProgressUpdateResult onProgressUpdateResult) {
        for (int i = 0; i < uploadNetworkListener.getProgressListeners().size(); i++) {
            Function1<OnProgressUpdateResult, Unit> function1 = uploadNetworkListener.getProgressListeners().get(i);
            Intrinsics.checkNotNullExpressionValue(function1, "get(...)");
            function1.invoke2(onProgressUpdateResult);
        }
        return Unit.INSTANCE;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0, types: [T, uts.sdk.modules.DCloudUniNetwork.UploadFileOptions] */
    /* JADX WARN: Type inference failed for: r3v0, types: [T, uts.sdk.modules.DCloudUniNetwork.UploadFileSuccess] */
    /* JADX WARN: Type inference failed for: r3v2, types: [T, uts.sdk.modules.DCloudUniNetwork.UploadFileFailImpl] */
    @Override // uts.sdk.modules.DCloudUniNetwork.NetworkUploadFileListener
    public void onComplete(UTSJSONObject option) throws NumberFormatException {
        String str;
        Intrinsics.checkNotNullParameter(option, "option");
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        objectRef.element = this.param;
        if (objectRef.element != 0) {
            if (option.get("errorMsg") != null) {
                Object obj = option.get("errorCode");
                Intrinsics.checkNotNull(obj);
                int i = Integer.parseInt((String) obj);
                final Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
                objectRef2.element = new UploadFileFailImpl(IndexKt.getErrcode(Integer.valueOf(i)));
                Object obj2 = option.get("cause");
                if (obj2 != null) {
                    ((UploadFileFailImpl) objectRef2.element).setCause((SourceError) obj2);
                }
                new RunnableTask__1(this.looper, new Function0() { // from class: uts.sdk.modules.DCloudUniNetwork.UploadNetworkListener$$ExternalSyntheticLambda1
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return UploadNetworkListener.onComplete$lambda$2(objectRef, objectRef2);
                    }
                }).execute();
                return;
            }
            Object obj3 = option.get("data");
            if (obj3 == null) {
                str = "";
            } else {
                str = (String) obj3;
            }
            Object obj4 = option.get("statusCode");
            Intrinsics.checkNotNull(obj4);
            int i2 = Integer.parseInt((String) obj4);
            final Ref.ObjectRef objectRef3 = new Ref.ObjectRef();
            objectRef3.element = new UploadFileSuccess(str, Integer.valueOf(i2));
            new RunnableTask__1(this.looper, new Function0() { // from class: uts.sdk.modules.DCloudUniNetwork.UploadNetworkListener$$ExternalSyntheticLambda2
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return UploadNetworkListener.onComplete$lambda$3(objectRef, objectRef3);
                }
            }).execute();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onComplete$lambda$2(Ref.ObjectRef<UploadFileOptions> objectRef, Ref.ObjectRef<UploadFileFailImpl> objectRef2) {
        if (objectRef.element != null) {
            Function1<UploadFileFail, Unit> fail = objectRef.element.getFail();
            if (fail != null) {
                fail.invoke2(objectRef2.element);
            }
            Function1<Object, Unit> complete = objectRef.element.getComplete();
            if (complete != null) {
                complete.invoke2(objectRef2.element);
            }
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onComplete$lambda$3(Ref.ObjectRef<UploadFileOptions> objectRef, Ref.ObjectRef<UploadFileSuccess> objectRef2) {
        if (objectRef.element != null) {
            Function1<UploadFileSuccess, Unit> success = objectRef.element.getSuccess();
            if (success != null) {
                success.invoke2(objectRef2.element);
            }
            Function1<Object, Unit> complete = objectRef.element.getComplete();
            if (complete != null) {
                complete.invoke2(objectRef2.element);
            }
        }
        return Unit.INSTANCE;
    }
}
