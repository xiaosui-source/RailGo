package uts.sdk.modules.DCloudUniNetwork;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;
import com.taobao.weex.el.parse.Operators;
import io.dcloud.common.adapter.util.DeviceInfo;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.uts.NumberKt;
import io.dcloud.uts.StringKt;
import io.dcloud.uts.UTSAndroid;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import okhttp3.Call;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/* compiled from: index.kt */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\b\u0016\u0018\u0000 !2\u00020\u0001:\u0001!B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0018\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\tH\u0002J\u001a\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002J\u0012\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\u001a\u0010\u0015\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u0014H\u0002J\u0010\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u0014H\u0002J\u0010\u0010\u001d\u001a\u00020\u001b2\u0006\u0010\u001e\u001a\u00020\u0014H\u0002J\u0010\u0010\u001f\u001a\u00020\u00142\u0006\u0010 \u001a\u00020\u0014H\u0002R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\""}, d2 = {"Luts/sdk/modules/DCloudUniNetwork/UploadController;", "", "<init>", "()V", "uploadExecutorService", "Ljava/util/concurrent/ExecutorService;", "uploadFile", "Luts/sdk/modules/DCloudUniNetwork/UploadTask;", "options", "Luts/sdk/modules/DCloudUniNetwork/UploadFileOptions;", "listener", "Luts/sdk/modules/DCloudUniNetwork/NetworkUploadFileListener;", "createUploadClient", "Lokhttp3/OkHttpClient;", AbsoluteConst.JSON_KEY_OPTION, "createUploadRequest", "Lokhttp3/Request;", "getFileInformation", "Luts/sdk/modules/DCloudUniNetwork/FileInformation;", "reassignedUri", "", "copyAssetFileToPrivateDir", "Ljava/io/File;", "context", "Landroid/content/Context;", "fileName", "checkPrivatePath", "", "reassignedPath", "isAssetFile", "filePath", "getMimeType", AbsoluteConst.JSON_KEY_FILENAME, "Companion", "uni-network_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public class UploadController {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static UploadController instance;
    private ExecutorService uploadExecutorService;

    public UploadTask uploadFile(UploadFileOptions options, NetworkUploadFileListener listener) throws IOException {
        Intrinsics.checkNotNullParameter(options, "options");
        Intrinsics.checkNotNullParameter(listener, "listener");
        OkHttpClient okHttpClientCreateUploadClient = createUploadClient(options);
        Request requestCreateUploadRequest = createUploadRequest(options, listener);
        if (requestCreateUploadRequest == null) {
            return new NetworkUploadTaskImpl(null, listener);
        }
        Call callNewCall = okHttpClientCreateUploadClient.newCall(requestCreateUploadRequest);
        Intrinsics.checkNotNullExpressionValue(callNewCall, "newCall(...)");
        callNewCall.enqueue(new SimpleUploadCallback(listener));
        return new NetworkUploadTaskImpl(callNewCall, listener);
    }

    private final OkHttpClient createUploadClient(UploadFileOptions option) {
        long jLongValue;
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (option.getTimeout() != null) {
            Number timeout = option.getTimeout();
            Intrinsics.checkNotNull(timeout);
            jLongValue = timeout.longValue();
        } else {
            jLongValue = 120000;
        }
        builder.connectTimeout(jLongValue, TimeUnit.MILLISECONDS);
        builder.readTimeout(jLongValue, TimeUnit.MILLISECONDS);
        builder.writeTimeout(jLongValue, TimeUnit.MILLISECONDS);
        builder.addInterceptor(new CookieInterceptor());
        if (this.uploadExecutorService == null) {
            this.uploadExecutorService = Executors.newFixedThreadPool(10);
        }
        builder.dispatcher(new Dispatcher(this.uploadExecutorService));
        OkHttpClient okHttpClientBuild = builder.build();
        Intrinsics.checkNotNullExpressionValue(okHttpClientBuild, "build(...)");
        return okHttpClientBuild;
    }

    /* JADX WARN: Code restructure failed: missing block: B:65:0x01c3, code lost:
    
        if (io.dcloud.uts.NumberKt.numberEquals(r21.getLength(), 0) != false) goto L68;
     */
    /* JADX WARN: Removed duplicated region for block: B:117:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:64:0x01b6  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x01c6  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x01e6  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x01f2  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x01f7  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x01fc  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x024e  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x0253  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final okhttp3.Request createUploadRequest(uts.sdk.modules.DCloudUniNetwork.UploadFileOptions r29, uts.sdk.modules.DCloudUniNetwork.NetworkUploadFileListener r30) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 703
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: uts.sdk.modules.DCloudUniNetwork.UploadController.createUploadRequest(uts.sdk.modules.DCloudUniNetwork.UploadFileOptions, uts.sdk.modules.DCloudUniNetwork.NetworkUploadFileListener):okhttp3.Request");
    }

    private final FileInformation getFileInformation(String reassignedUri) throws IOException {
        String strConvert2AbsFullPath;
        if (StringsKt.startsWith$default(reassignedUri, "content://", false, 2, (Object) null)) {
            Uri uri = Uri.parse(reassignedUri);
            Context appContext = UTSAndroid.INSTANCE.getAppContext();
            Intrinsics.checkNotNull(appContext);
            Cursor cursorQuery = appContext.getContentResolver().query(uri, null, null, null, null);
            if (cursorQuery == null) {
                return null;
            }
            cursorQuery.moveToFirst();
            FileInformation fileInformation = new FileInformation();
            fileInformation.setInputStream(appContext.getContentResolver().openInputStream(uri));
            fileInformation.setSize(cursorQuery.getInt(cursorQuery.getColumnIndex("_size")));
            fileInformation.setName(cursorQuery.getString(cursorQuery.getColumnIndex("_display_name")));
            fileInformation.setMime(cursorQuery.getString(cursorQuery.getColumnIndex("mime_type")));
            cursorQuery.close();
            return fileInformation;
        }
        if (StringsKt.startsWith$default(reassignedUri, DeviceInfo.FILE_PROTOCOL, false, 2, (Object) null)) {
            strConvert2AbsFullPath = StringKt.substring$default(reassignedUri, (Number) 7, null, 2, null);
        } else if (StringsKt.startsWith$default(reassignedUri, "unifile://", false, 2, (Object) null)) {
            strConvert2AbsFullPath = UTSAndroid.INSTANCE.convert2AbsFullPath(reassignedUri);
        } else {
            strConvert2AbsFullPath = UTSAndroid.INSTANCE.convert2AbsFullPath(reassignedUri);
            if (StringsKt.startsWith$default(strConvert2AbsFullPath, "/android_asset/", false, 2, (Object) null)) {
                String strReplace = StringKt.replace(strConvert2AbsFullPath, "/android_asset/", "");
                Context appContext2 = UTSAndroid.INSTANCE.getAppContext();
                Intrinsics.checkNotNull(appContext2);
                Intrinsics.checkNotNull(appContext2);
                File fileCopyAssetFileToPrivateDir = copyAssetFileToPrivateDir(appContext2, strReplace);
                if (fileCopyAssetFileToPrivateDir != null) {
                    strConvert2AbsFullPath = fileCopyAssetFileToPrivateDir.getPath();
                    Intrinsics.checkNotNullExpressionValue(strConvert2AbsFullPath, "getPath(...)");
                }
            }
        }
        File file = new File(strConvert2AbsFullPath);
        FileInputStream fileInputStream = new FileInputStream(file);
        long length = file.length();
        String name = file.getName();
        Intrinsics.checkNotNull(name);
        String mimeType = getMimeType(name);
        FileInformation fileInformation2 = new FileInformation();
        fileInformation2.setInputStream(fileInputStream);
        fileInformation2.setSize(length);
        fileInformation2.setName(name);
        fileInformation2.setMime(mimeType);
        return fileInformation2;
    }

    private final File copyAssetFileToPrivateDir(Context context, String fileName) throws IOException {
        try {
            File file = new File(context.getCacheDir().getPath() + "/uploadFiles/" + fileName);
            File parentFile = file.getParentFile();
            if (parentFile != null && !parentFile.exists()) {
                parentFile.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            InputStream inputStreamOpen = context.getAssets().open(fileName);
            Intrinsics.checkNotNullExpressionValue(inputStreamOpen, "open(...)");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            byte[] bArr = new byte[1024];
            while (true) {
                int i = inputStreamOpen.read(bArr);
                if (i != -1) {
                    fileOutputStream.write(bArr, 0, i);
                } else {
                    inputStreamOpen.close();
                    fileOutputStream.close();
                    return file;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private final boolean checkPrivatePath(String reassignedPath) {
        String parent;
        if (Build.VERSION.SDK_INT > 29 && Environment.isExternalStorageManager()) {
            return true;
        }
        if (StringsKt.startsWith$default(reassignedPath, DeviceInfo.FILE_PROTOCOL, false, 2, (Object) null)) {
            reassignedPath = StringKt.replace(reassignedPath, DeviceInfo.FILE_PROTOCOL, "");
        }
        Context appContext = UTSAndroid.INSTANCE.getAppContext();
        Intrinsics.checkNotNull(appContext);
        File externalCacheDir = appContext.getExternalCacheDir();
        if (externalCacheDir == null) {
            parent = Environment.getExternalStorageDirectory().getPath() + "/Android/data/" + appContext.getPackageName();
        } else {
            parent = externalCacheDir.getParent();
            Intrinsics.checkNotNullExpressionValue(parent, "getParent(...)");
        }
        String parent2 = appContext.getFilesDir().getParent();
        if (StringsKt.startsWith$default(parent, "/", false, 2, (Object) null) && !StringsKt.startsWith$default(reassignedPath, "/", false, 2, (Object) null)) {
            reassignedPath = "/" + reassignedPath;
        }
        String str = reassignedPath;
        Intrinsics.checkNotNull(parent2);
        return StringsKt.contains$default((CharSequence) str, (CharSequence) parent2, false, 2, (Object) null) || StringsKt.contains$default((CharSequence) str, (CharSequence) parent, false, 2, (Object) null) || isAssetFile(reassignedPath) || Build.VERSION.SDK_INT < 29;
    }

    private final boolean isAssetFile(String filePath) {
        return StringsKt.startsWith$default(filePath, "apps/", false, 2, (Object) null) || StringsKt.startsWith$default(filePath, "/android_asset/", false, 2, (Object) null) || StringsKt.startsWith$default(filePath, "android_asset/", false, 2, (Object) null);
    }

    private final String getMimeType(String filename) {
        MimeTypeMap singleton = MimeTypeMap.getSingleton();
        String fileExtensionFromUrl = MimeTypeMap.getFileExtensionFromUrl(filename);
        if (TextUtils.isEmpty(fileExtensionFromUrl) && NumberKt.compareTo(StringKt.lastIndexOf$default(filename, Operators.DOT_STR, null, 2, null), (Number) 0) >= 0) {
            fileExtensionFromUrl = StringKt.substring$default(filename, NumberKt.plus(StringKt.lastIndexOf$default(filename, Operators.DOT_STR, null, 2, null), (Number) 1), null, 2, null);
        }
        if (fileExtensionFromUrl != null) {
            fileExtensionFromUrl = StringKt.toLowerCase(fileExtensionFromUrl);
        }
        String mimeTypeFromExtension = singleton.getMimeTypeFromExtension(fileExtensionFromUrl);
        if (TextUtils.isEmpty(mimeTypeFromExtension)) {
            if (TextUtils.isEmpty(fileExtensionFromUrl)) {
                mimeTypeFromExtension = "application/octet-stream";
            } else {
                mimeTypeFromExtension = "application/" + fileExtensionFromUrl;
            }
        }
        Intrinsics.checkNotNull(mimeTypeFromExtension);
        return mimeTypeFromExtension;
    }

    /* compiled from: index.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0006\u0010\u0006\u001a\u00020\u0005R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0007"}, d2 = {"Luts/sdk/modules/DCloudUniNetwork/UploadController$Companion;", "", "<init>", "()V", "instance", "Luts/sdk/modules/DCloudUniNetwork/UploadController;", "getInstance", "uni-network_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final UploadController getInstance() {
            if (UploadController.instance == null) {
                UploadController.instance = new UploadController();
            }
            UploadController uploadController = UploadController.instance;
            Intrinsics.checkNotNull(uploadController);
            return uploadController;
        }
    }
}
