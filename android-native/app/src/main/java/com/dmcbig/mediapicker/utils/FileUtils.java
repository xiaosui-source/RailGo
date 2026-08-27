package com.dmcbig.mediapicker.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;
import com.facebook.common.util.UriUtil;
import com.taobao.weex.el.parse.Operators;
import com.taobao.weex.performance.WXInstanceApm;
import com.taobao.weex.utils.tools.TimeCalculator;
import io.dcloud.common.DHInterface.IApp;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Locale;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class FileUtils {
    private static final String EXTERNAL_STORAGE_PERMISSION = "android.permission.WRITE_EXTERNAL_STORAGE";
    private static final String JPEG_FILE_PREFIX = "IMG_";
    private static final String JPEG_FILE_SUFFIX = ".jpg";
    private static final long MB = 1048576;

    public static File createTmpFile(Context context) throws IOException {
        File cacheDirectory;
        if (TextUtils.equals(Environment.getExternalStorageState(), "mounted")) {
            cacheDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
            if (!cacheDirectory.exists()) {
                cacheDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM + "/Camera");
                if (!cacheDirectory.exists()) {
                    cacheDirectory = getCacheDirectory(context, true);
                }
            }
        } else {
            cacheDirectory = getCacheDirectory(context, true);
        }
        return File.createTempFile(JPEG_FILE_PREFIX, JPEG_FILE_SUFFIX, cacheDirectory);
    }

    public static String fileSize(long j) {
        if (j <= 0) {
            return WXInstanceApm.VALUE_ERROR_CODE_DEFAULT;
        }
        double d = j;
        int iLog10 = (int) (Math.log10(d) / Math.log10(1024.0d));
        return new DecimalFormat("#,##0.#").format(d / Math.pow(1024.0d, iLog10)) + Operators.SPACE_STR + new String[]{"B", "kB", "MB", "GB", "TB"}[iLog10];
    }

    public static File getCacheDirectory(Context context) {
        return getCacheDirectory(context, true);
    }

    private static File getExternalCacheDir(Context context) throws IOException {
        File file = new File(new File(new File(new File(Environment.getExternalStorageDirectory(), TimeCalculator.PLATFORM_ANDROID), "data"), context.getPackageName()), IApp.ConfigProperty.CONFIG_CACHE);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                return null;
            }
            try {
                new File(file, ".nomedia").createNewFile();
            } catch (IOException unused) {
            }
        }
        return file;
    }

    public static File getIndividualCacheDirectory(Context context, String str) {
        File cacheDirectory = getCacheDirectory(context);
        File file = new File(cacheDirectory, str);
        return (file.exists() || file.mkdir()) ? file : cacheDirectory;
    }

    public static String getMimeType(Context context, Uri uri) {
        if (UriUtil.LOCAL_CONTENT_SCHEME.equals(uri.getScheme())) {
            String extensionFromMimeType = MimeTypeMap.getSingleton().getExtensionFromMimeType(context.getContentResolver().getType(uri));
            return TextUtils.isEmpty(extensionFromMimeType) ? MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(new File(uri.getPath())).toString()) : extensionFromMimeType;
        }
        String fileExtensionFromUrl = MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(new File(uri.getPath())).toString());
        return TextUtils.isEmpty(fileExtensionFromUrl) ? MimeTypeMap.getSingleton().getExtensionFromMimeType(context.getContentResolver().getType(uri)) : fileExtensionFromUrl;
    }

    public static String getMimeTypeByFileName(String str) {
        return str.substring(str.lastIndexOf(Operators.DOT_STR), str.length());
    }

    public static String getRealPathFromURI(Context context, Uri uri) {
        Cursor cursorQuery = context.getContentResolver().query(uri, null, null, null, null);
        if (cursorQuery == null) {
            return uri.getPath();
        }
        cursorQuery.moveToFirst();
        String string = cursorQuery.getString(cursorQuery.getColumnIndex("_data"));
        cursorQuery.close();
        return string;
    }

    private static boolean hasExternalStoragePermission(Context context) {
        return context.checkCallingOrSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == 0;
    }

    public String getSizeByUnit(double d) {
        if (d == 0.0d) {
            return "0K";
        }
        if (d >= 1048576.0d) {
            return String.format(Locale.US, "%.1f", Double.valueOf(d / 1048576.0d)) + "M";
        }
        return String.format(Locale.US, "%.1f", Double.valueOf(d / 1024.0d)) + "K";
    }

    public static File getCacheDirectory(Context context, boolean z) {
        String externalStorageState = "";
        try {
            externalStorageState = Environment.getExternalStorageState();
        } catch (IncompatibleClassChangeError | NullPointerException unused) {
        }
        File externalCacheDir = (z && "mounted".equals(externalStorageState) && hasExternalStoragePermission(context)) ? getExternalCacheDir(context) : null;
        if (externalCacheDir == null) {
            externalCacheDir = context.getCacheDir();
        }
        if (externalCacheDir != null) {
            return externalCacheDir;
        }
        return new File("/data/data/" + context.getPackageName() + "/cache/");
    }
}
