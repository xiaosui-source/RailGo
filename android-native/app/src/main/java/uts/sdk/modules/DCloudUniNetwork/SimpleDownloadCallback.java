package uts.sdk.modules.DCloudUniNetwork;

import android.content.Context;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;
import androidx.core.app.NotificationCompat;
import com.taobao.weex.el.parse.Operators;
import com.taobao.weex.performance.WXInstanceApm;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.uniapp.SourceError;
import io.dcloud.uts.NumberKt;
import io.dcloud.uts.StringKt;
import io.dcloud.uts.UTSAndroid;
import io.dcloud.uts.UTSJSONObject;
import io.dcloud.uts.UTSJSONObjectKt;
import io.dcloud.uts.UTSRegExp;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.StringTokenizer;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/* compiled from: index.kt */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0011\n\u0002\b\u0004\b\u0016\u0018\u00002\u00020\u0001B\u0019\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010\u000f\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\b\u0010\u0012\u001a\u00020\u0013H\u0016J\b\u0010\u0014\u001a\u00020\u0005H\u0016J\u0010\u0010\u0015\u001a\u00020\u00132\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u0010\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0005H\u0016J\u0018\u0010\u0019\u001a\u00020\u00172\u0006\u0010\u001a\u001a\u00020\u00132\u0006\u0010\u001b\u001a\u00020\u0013H\u0016J+\u0010\u001c\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0005\u0018\u00010\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u00052\b\u0010\u001f\u001a\u0004\u0018\u00010\u0005H\u0016¢\u0006\u0002\u0010 R\u000e\u0010\b\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006!"}, d2 = {"Luts/sdk/modules/DCloudUniNetwork/SimpleDownloadCallback;", "Lokhttp3/Callback;", "listener", "Luts/sdk/modules/DCloudUniNetwork/NetworkDownloadFileListener;", "specifyPath", "", "<init>", "(Luts/sdk/modules/DCloudUniNetwork/NetworkDownloadFileListener;Ljava/lang/String;)V", "downloadFilePath", "onFailure", "", NotificationCompat.CATEGORY_CALL, "Lokhttp3/Call;", "exception", "Ljava/io/IOException;", "onResponse", "response", "Lokhttp3/Response;", "getTempFile", "Ljava/io/File;", "getRealPath", "getFile", "isAbsolute", "", AbsoluteConst.XML_PATH, "isDescendant", "parent", "child", "stringSplit", "", "str", "delim", "(Ljava/lang/String;Ljava/lang/String;)[Ljava/lang/String;", "uni-network_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public class SimpleDownloadCallback implements Callback {
    private String downloadFilePath;
    private NetworkDownloadFileListener listener;
    private String specifyPath;

    public SimpleDownloadCallback(NetworkDownloadFileListener listener, String specifyPath) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        Intrinsics.checkNotNullParameter(specifyPath, "specifyPath");
        this.downloadFilePath = "/uni-download/";
        this.specifyPath = "";
        this.listener = listener;
        if (StringsKt.startsWith$default(specifyPath, "unifile://", false, 2, (Object) null)) {
            this.specifyPath = UTSAndroid.INSTANCE.convert2AbsFullPath(specifyPath);
        } else {
            this.specifyPath = specifyPath;
        }
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
        NetworkDownloadFileListener networkDownloadFileListener = this.listener;
        if (networkDownloadFileListener != null) {
            networkDownloadFileListener.onComplete(uTSJSONObject_uO);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:65:0x0144  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0145  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0150 A[Catch: all -> 0x0169, TRY_LEAVE, TryCatch #4 {all -> 0x0169, blocks: (B:63:0x012b, B:67:0x0146, B:69:0x0150), top: B:98:0x012b }] */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0157  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x015c  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0161  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x016c  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x0171  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x0176  */
    @Override // okhttp3.Callback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void onResponse(okhttp3.Call r21, okhttp3.Response r22) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 459
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: uts.sdk.modules.DCloudUniNetwork.SimpleDownloadCallback.onResponse(okhttp3.Call, okhttp3.Response):void");
    }

    public File getTempFile() {
        Context appContext = UTSAndroid.INSTANCE.getAppContext();
        Intrinsics.checkNotNull(appContext);
        return new File(appContext.getExternalCacheDir(), "temp_" + System.currentTimeMillis());
    }

    public String getRealPath() {
        String appTempPath = UTSAndroid.INSTANCE.getAppTempPath();
        if (appTempPath == null) {
            appTempPath = "";
        }
        return appTempPath + this.downloadFilePath;
    }

    public File getFile(Response response) throws IOException {
        String realPath;
        Integer num;
        String str;
        String str2;
        String str3;
        String strValueOf;
        String strSubstring;
        String[] strArrStringSplit;
        Integer num2;
        String[] strArr;
        String str4;
        String str5;
        int i;
        String[] strArrStringSplit2;
        String string;
        Number number;
        String strSubstring$default;
        String strSubstring2;
        Intrinsics.checkNotNullParameter(response, "response");
        String str6 = "";
        Integer num3 = 10;
        String str7 = "cause";
        String str8 = "errorMsg";
        if (!Intrinsics.areEqual(this.specifyPath, "")) {
            if (isDescendant(new File(UTSAndroid.INSTANCE.convert2AbsFullPath("/")), new File(this.specifyPath))) {
                UTSJSONObject uTSJSONObject_uO = UTSJSONObjectKt._uO(new Pair[0]);
                uTSJSONObject_uO.set("statusCode", "-1");
                uTSJSONObject_uO.set("errorCode", "602001");
                uTSJSONObject_uO.set("errorMsg", "This path is not supported");
                uTSJSONObject_uO.set("cause", null);
                NetworkDownloadFileListener networkDownloadFileListener = this.listener;
                if (networkDownloadFileListener != null) {
                    networkDownloadFileListener.onComplete(uTSJSONObject_uO);
                }
                return new File("");
            }
            if (NumberKt.numberEquals(StringKt.lastIndexOf$default(this.specifyPath, "/", null, 2, null), Integer.valueOf(this.specifyPath.length() - 1))) {
                if (isAbsolute(this.specifyPath)) {
                    realPath = this.specifyPath;
                } else {
                    StringBuilder sb = new StringBuilder();
                    String appTempPath = UTSAndroid.INSTANCE.getAppTempPath();
                    Intrinsics.checkNotNull(appTempPath);
                    Intrinsics.checkNotNull(appTempPath);
                    sb.append(appTempPath);
                    sb.append('/');
                    sb.append(this.specifyPath);
                    realPath = sb.toString();
                }
            } else {
                if (isAbsolute(this.specifyPath)) {
                    string = this.specifyPath;
                } else {
                    StringBuilder sb2 = new StringBuilder();
                    String appTempPath2 = UTSAndroid.INSTANCE.getAppTempPath();
                    Intrinsics.checkNotNull(appTempPath2);
                    Intrinsics.checkNotNull(appTempPath2);
                    sb2.append(appTempPath2);
                    sb2.append('/');
                    sb2.append(this.specifyPath);
                    string = sb2.toString();
                }
                File file = new File(string);
                File parentFile = file.getParentFile();
                if (parentFile != null && !parentFile.exists()) {
                    parentFile.mkdirs();
                }
                if (file.exists() && file.isDirectory()) {
                    UTSJSONObject uTSJSONObject_uO2 = UTSJSONObjectKt._uO(new Pair[0]);
                    uTSJSONObject_uO2.set("statusCode", "-1");
                    uTSJSONObject_uO2.set("errorCode", "602001");
                    uTSJSONObject_uO2.set("errorMsg", "The target file path is already a directory file, and file creation failed.");
                    number = null;
                    uTSJSONObject_uO2.set("cause", null);
                    NetworkDownloadFileListener networkDownloadFileListener2 = this.listener;
                    if (networkDownloadFileListener2 != null) {
                        networkDownloadFileListener2.onComplete(uTSJSONObject_uO2);
                    }
                } else {
                    number = null;
                }
                if (file.exists()) {
                    Number numberLastIndexOf$default = StringKt.lastIndexOf$default(string, Operators.DOT_STR, number, 2, number);
                    if (NumberKt.compareTo(numberLastIndexOf$default, (Number) 0) >= 0) {
                        strSubstring2 = StringKt.substring(string, (Number) 0, numberLastIndexOf$default);
                        strSubstring$default = StringKt.substring$default(string, numberLastIndexOf$default, number, 2, number);
                    } else {
                        strSubstring$default = "";
                        strSubstring2 = string;
                    }
                    Number numberInc = (Number) 1;
                    while (new File(string).exists()) {
                        string = strSubstring2 + Operators.BRACKET_START + NumberKt.toString(numberInc, (Number) num3) + Operators.BRACKET_END + strSubstring$default;
                        numberInc = NumberKt.inc(numberInc);
                    }
                    file = new File(string);
                }
                if (!file.exists()) {
                    try {
                        file.createNewFile();
                        return file;
                    } catch (Exception e) {
                        UTSJSONObject uTSJSONObject_uO3 = UTSJSONObjectKt._uO(new Pair[0]);
                        uTSJSONObject_uO3.set("statusCode", "-1");
                        uTSJSONObject_uO3.set("errorCode", "602001");
                        uTSJSONObject_uO3.set("errorMsg", e.getMessage());
                        uTSJSONObject_uO3.set("cause", new SourceError(NumberKt.toString_number_nullable$default(e.getCause(), (Number) null, 1, (Object) null)));
                        NetworkDownloadFileListener networkDownloadFileListener3 = this.listener;
                        if (networkDownloadFileListener3 != null) {
                            networkDownloadFileListener3.onComplete(uTSJSONObject_uO3);
                        }
                    }
                }
                return file;
            }
        } else {
            realPath = getRealPath();
        }
        String strHeader = response.header("content-disposition");
        if (TextUtils.isEmpty(strHeader) || (strArrStringSplit = stringSplit(strHeader, ";")) == null) {
            num = num3;
            str = "cause";
            str2 = "errorMsg";
            str3 = "";
        } else {
            str3 = "";
            int i2 = 0;
            while (i2 < strArrStringSplit.length) {
                String str9 = strArrStringSplit[i2];
                if (str9 != null) {
                    String str10 = str9;
                    num2 = num3;
                    strArr = strArrStringSplit;
                    str4 = str7;
                    str5 = str8;
                    i = i2;
                    if (StringsKt.contains$default((CharSequence) str10, (CharSequence) AbsoluteConst.JSON_KEY_FILENAME, false, 2, (Object) null) && (strArrStringSplit2 = stringSplit(StringsKt.trim((CharSequence) str10).toString(), "=")) != null && strArrStringSplit2.length > 1) {
                        String strReplace = strArrStringSplit2[0];
                        String strReplace2 = strArrStringSplit2[1];
                        UTSRegExp uTSRegExp = new UTSRegExp("^\"|\"$", "g");
                        if (strReplace != null) {
                            strReplace = StringKt.replace(strReplace, uTSRegExp, "");
                        }
                        if (strReplace2 != null) {
                            strReplace2 = StringKt.replace(strReplace2, uTSRegExp, "");
                        }
                        if (!TextUtils.isEmpty(strReplace) && !TextUtils.isEmpty(strReplace2)) {
                            Intrinsics.checkNotNull(strReplace);
                            if (StringsKt.equals(strReplace, AbsoluteConst.JSON_KEY_FILENAME, true) && strReplace2 != null) {
                                str3 = strReplace2;
                            }
                        }
                    }
                } else {
                    num2 = num3;
                    strArr = strArrStringSplit;
                    str4 = str7;
                    str5 = str8;
                    i = i2;
                }
                i2 = i + 1;
                num3 = num2;
                strArrStringSplit = strArr;
                str7 = str4;
                str8 = str5;
            }
            num = num3;
            str = str7;
            str2 = str8;
        }
        if (TextUtils.isEmpty(str3)) {
            String strEncodedPath = response.request().url().encodedPath();
            Intrinsics.checkNotNull(strEncodedPath);
            Number numberLastIndexOf$default2 = StringKt.lastIndexOf$default(strEncodedPath, "/", null, 2, null);
            if (NumberKt.compareTo(numberLastIndexOf$default2, (Number) 0) >= 0) {
                Intrinsics.checkNotNull(strEncodedPath);
                String strSubstring$default2 = StringKt.substring$default(strEncodedPath, NumberKt.plus(numberLastIndexOf$default2, (Number) 1), null, 2, null);
                if (NumberKt.compareTo(StringKt.indexOf$default(strSubstring$default2, Operators.DOT_STR, null, 2, null), (Number) 0) >= 0 || strSubstring$default2.length() > 0) {
                    if (StringsKt.contains$default((CharSequence) strSubstring$default2, (CharSequence) Operators.CONDITION_IF_STRING, false, 2, (Object) null)) {
                        strSubstring$default2 = StringKt.substring(strSubstring$default2, (Number) 0, StringKt.indexOf$default(strSubstring$default2, Operators.CONDITION_IF_STRING, null, 2, null));
                    }
                    str3 = strSubstring$default2;
                }
            }
        }
        if (TextUtils.isEmpty(str3)) {
            strValueOf = String.valueOf(System.currentTimeMillis());
            String extensionFromMimeType = MimeTypeMap.getSingleton().getExtensionFromMimeType(response.header("content-type"));
            if (extensionFromMimeType != null) {
                str3 = strValueOf + Operators.DOT + extensionFromMimeType;
                strValueOf = str3;
            }
        } else {
            strValueOf = str3;
        }
        String strDecode = URLDecoder.decode(strValueOf, "UTF-8");
        Intrinsics.checkNotNullExpressionValue(strDecode, "decode(...)");
        String separator = File.separator;
        Intrinsics.checkNotNullExpressionValue(separator, "separator");
        String strReplace3 = new Regex(separator).replace(strDecode, "");
        String str11 = strReplace3;
        if (StringsKt.contains$default((CharSequence) str11, (CharSequence) Operators.CONDITION_IF_STRING, false, 2, (Object) null)) {
            strReplace3 = new Regex("\\?").replace(str11, WXInstanceApm.VALUE_ERROR_CODE_DEFAULT);
        }
        if (strReplace3.length() > 80) {
            strReplace3 = StringKt.substring(strReplace3, (Number) 0, (Number) 80) + System.currentTimeMillis();
        }
        if (new File(realPath + strReplace3).exists()) {
            Number numberLastIndexOf$default3 = StringKt.lastIndexOf$default(strReplace3, Operators.DOT_STR, null, 2, null);
            if (NumberKt.compareTo(numberLastIndexOf$default3, (Number) 0) >= 0) {
                strSubstring = StringKt.substring(strReplace3, (Number) 0, numberLastIndexOf$default3);
                String strSubstring$default3 = StringKt.substring$default(strReplace3, numberLastIndexOf$default3, null, 2, null);
                if (Intrinsics.areEqual(strSubstring, "")) {
                    strSubstring = strSubstring$default3;
                } else {
                    str6 = strSubstring$default3;
                }
            } else {
                strSubstring = strReplace3;
            }
            Number numberInc2 = (Number) 1;
            while (true) {
                if (!new File(realPath + strReplace3).exists()) {
                    break;
                }
                strReplace3 = strSubstring + Operators.BRACKET_START + NumberKt.toString(numberInc2, (Number) num) + Operators.BRACKET_END + str6;
                numberInc2 = NumberKt.inc(numberInc2);
            }
        }
        File file2 = new File(realPath + strReplace3);
        File parentFile2 = file2.getParentFile();
        if (parentFile2 != null && !parentFile2.exists()) {
            parentFile2.mkdirs();
        }
        if (!file2.exists()) {
            try {
                file2.createNewFile();
                return file2;
            } catch (Exception e2) {
                UTSJSONObject uTSJSONObject_uO4 = UTSJSONObjectKt._uO(new Pair[0]);
                uTSJSONObject_uO4.set("statusCode", "-1");
                uTSJSONObject_uO4.set("errorCode", "602001");
                uTSJSONObject_uO4.set(str2, e2.getMessage());
                uTSJSONObject_uO4.set(str, new SourceError(NumberKt.toString_number_nullable$default(e2.getCause(), (Number) null, 1, (Object) null)));
                NetworkDownloadFileListener networkDownloadFileListener4 = this.listener;
                if (networkDownloadFileListener4 != null) {
                    networkDownloadFileListener4.onComplete(uTSJSONObject_uO4);
                }
            }
        }
        return file2;
    }

    public boolean isAbsolute(String path) {
        Intrinsics.checkNotNullParameter(path, "path");
        Context appContext = UTSAndroid.INSTANCE.getAppContext();
        Intrinsics.checkNotNull(appContext);
        Intrinsics.checkNotNull(appContext);
        String parent = appContext.getFilesDir().getParent();
        Intrinsics.checkNotNullExpressionValue(parent, "getParent(...)");
        if (StringsKt.startsWith$default(path, parent, false, 2, (Object) null)) {
            return true;
        }
        File externalFilesDir = appContext.getExternalFilesDir(null);
        String parent2 = externalFilesDir != null ? externalFilesDir.getParent() : null;
        return parent2 != null && StringsKt.startsWith$default(path, parent2, false, 2, (Object) null);
    }

    public boolean isDescendant(File parent, File child) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        Intrinsics.checkNotNullParameter(child, "child");
        if (Intrinsics.areEqual(child.getCanonicalPath(), parent.getCanonicalPath())) {
            return true;
        }
        File parentFile = child.getParentFile();
        if (parentFile == null) {
            return false;
        }
        return isDescendant(parent, parentFile);
    }

    public String[] stringSplit(String str, String delim) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(delim)) {
            return null;
        }
        int i = 0;
        StringTokenizer stringTokenizer = new StringTokenizer(str, delim, false);
        String[] strArr = new String[stringTokenizer.countTokens()];
        while (stringTokenizer.hasMoreElements()) {
            String strNextToken = stringTokenizer.nextToken();
            Intrinsics.checkNotNullExpressionValue(strNextToken, "nextToken(...)");
            strArr[i] = StringsKt.trim((CharSequence) strNextToken).toString();
            i++;
        }
        return strArr;
    }
}
