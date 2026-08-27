package io.dcloud.common.util;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import androidx.core.content.FileProvider;
import com.facebook.common.util.UriUtil;
import com.taobao.weex.el.parse.Operators;
import io.dcloud.common.adapter.io.DHFile;
import io.dcloud.common.adapter.util.DeviceInfo;
import io.dcloud.common.constant.AbsoluteConst;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class FileUtil {
    public static boolean checkFilePathLegalization(Context context, String str) {
        if (checkPrivatePath(context, str)) {
            return true;
        }
        return needMediaStoreOpenFile(context) && !TextUtils.isEmpty(getPathForPublicType(str));
    }

    public static boolean checkPathAccord(Context context, String... strArr) {
        boolean z = true;
        if (needMediaStoreOpenFile(context)) {
            if (Build.VERSION.SDK_INT > 29 && Environment.isExternalStorageManager()) {
                return true;
            }
            for (String str : strArr) {
                if (!checkPrivatePath(context, str)) {
                    z = false;
                }
            }
        }
        return z;
    }

    public static boolean checkPrivatePath(Context context, String str) {
        if (TextUtils.isEmpty(DeviceInfo.sPrivateDir)) {
            DeviceInfo.initAppDir(context);
        }
        int i = Build.VERSION.SDK_INT;
        if (i > 29 && Environment.isExternalStorageManager()) {
            return true;
        }
        if (str.startsWith(DeviceInfo.FILE_PROTOCOL)) {
            str = str.replace(DeviceInfo.FILE_PROTOCOL, "");
        }
        if (DeviceInfo.sPrivateExternalDir.startsWith("/") && !str.startsWith("/")) {
            str = "/" + str;
        }
        return str.contains(DeviceInfo.sPrivateDir) || str.contains(DeviceInfo.sPrivateExternalDir) || isAssetFile(str) || i < 29;
    }

    private static Uri copyMediaFile(Context context, InputStream inputStream, String str, String str2, String str3, String str4) throws Exception {
        String fileTypeForSuffix = getFileTypeForSuffix(str2);
        if (TextUtils.isEmpty(fileTypeForSuffix)) {
            return null;
        }
        Uri contentUriForSuffix = getContentUriForSuffix(str2);
        File file = new File(DeviceInfo.sPublicDCIMDir + "/" + str4 + str);
        StringBuilder sb = new StringBuilder(Operators.DOT_STR);
        sb.append(str2);
        String string = sb.toString();
        String strReplace = str.replace(string, "");
        ContentResolver contentResolver = context.getContentResolver();
        int i = 1;
        while (file.exists()) {
            str = strReplace + Operators.BRACKET_START_STR + i + Operators.BRACKET_END_STR + string;
            i++;
            file = new File(DeviceInfo.sPublicDCIMDir + "/" + str4 + str);
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("_display_name", str);
        contentValues.put("mime_type", fileTypeForSuffix);
        contentValues.put(AbsoluteConst.JSON_KEY_TITLE, str);
        contentValues.put("relative_path", str3 + str4);
        Uri uriInsert = contentResolver.insert(contentUriForSuffix, contentValues);
        if (uriInsert == null) {
            return null;
        }
        OutputStream outputStreamOpenOutputStream = context.getContentResolver().openOutputStream(uriInsert);
        byte[] bArr = new byte[DHFile.BUF_SIZE];
        if (outputStreamOpenOutputStream != null) {
            while (true) {
                int i2 = inputStream.read(bArr);
                if (i2 <= 0) {
                    break;
                }
                outputStreamOpenOutputStream.write(bArr, 0, i2);
                outputStreamOpenOutputStream.flush();
            }
            outputStreamOpenOutputStream.close();
        }
        inputStream.close();
        return uriInsert;
    }

    public static Uri copyMediaFileToDCIM(Context context, String str) throws IOException {
        String str2;
        if (needMediaStoreOpenFile(context)) {
            try {
                File file = new File(str);
                if (!file.exists()) {
                    return null;
                }
                InputStream fileInputStream = checkPrivatePath(context, str) ? new FileInputStream(file) : getFileInputStream(context, file);
                if (fileInputStream == null) {
                    return null;
                }
                String fileNameForPath = getFileNameForPath(str);
                String fileNameWithSuffix = getFileNameWithSuffix(fileNameForPath);
                if (TextUtils.isEmpty(fileNameWithSuffix)) {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    if (checkPrivatePath(context, str)) {
                        BitmapFactory.decodeFile(str, options);
                    } else {
                        InputStream fileInputStream2 = getFileInputStream(context, file);
                        BitmapFactory.decodeStream(fileInputStream2, null, options);
                        fileInputStream2.close();
                    }
                    String str3 = options.outMimeType;
                    if (PdrUtil.isEmpty(str3) || !str3.contains("/")) {
                        str2 = "jpg";
                    } else {
                        String[] strArrSplit = str3.split("/");
                        str2 = strArrSplit[strArrSplit.length - 1];
                    }
                    fileNameWithSuffix = str2;
                }
                return copyMediaFile(context, fileInputStream, fileNameForPath, fileNameWithSuffix, "DCIM/", "Camera/");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Uri createPublicDocumentsFile(Context context, String str, String str2) {
        if (needMediaStoreOpenFile(context)) {
            Uri contentUri = MediaStore.Files.getContentUri("external");
            File file = new File(DeviceInfo.sPublicDocumentsDir + "/" + str);
            if (file.exists()) {
                return getFileUri(context, file, contentUri);
            }
            ContentResolver contentResolver = context.getContentResolver();
            ContentValues contentValues = new ContentValues();
            contentValues.put("_display_name", str);
            contentValues.put("mime_type", str2);
            contentValues.put(AbsoluteConst.JSON_KEY_TITLE, str);
            contentValues.put("relative_path", "Documents/");
            try {
                return contentResolver.insert(contentUri, contentValues);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void deleteFile(Context context, String str) {
        if (context != null) {
            try {
                if (TextUtils.isEmpty(str)) {
                    return;
                }
                File fileStreamPath = context.getFileStreamPath(str);
                if (fileStreamPath.exists()) {
                    fileStreamPath.delete();
                }
            } catch (Exception unused) {
            }
        }
    }

    public static Uri getContentUriForSuffix(String str) {
        String upperCase = str.toUpperCase();
        upperCase.getClass();
        upperCase.hashCode();
        switch (upperCase) {
            case "AAC":
            case "AMR":
            case "M4A":
            case "MKA":
            case "MP3":
            case "OGG":
            case "WAV":
            case "WMA":
            case "FLAC":
                return MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            case "ASF":
            case "AVI":
            case "M4V":
            case "MKV":
            case "MP4":
            case "WMV":
            case "3GPP":
            case "WEBM":
            case "3GPP2":
            case "MP2TS":
                return MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
            case "BMP":
            case "GIF":
            case "JPG":
            case "PNG":
            case "JPEG":
            case "WBMP":
            case "WEBP":
                return MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            default:
                return MediaStore.Files.getContentUri("external");
        }
    }

    public static InputStream getFileInputStream(Context context, String str) {
        if (str.startsWith(DeviceInfo.FILE_PROTOCOL)) {
            str = str.replace(DeviceInfo.FILE_PROTOCOL, "");
        }
        return getFileInputStream(context, new File(str));
    }

    public static String getFileNameForPath(String str) {
        int iLastIndexOf;
        if (TextUtils.isEmpty(str) || (iLastIndexOf = str.lastIndexOf("/")) == -1) {
            return null;
        }
        return str.substring(iLastIndexOf + 1);
    }

    public static String getFileNameWithSuffix(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        int iLastIndexOf = str.lastIndexOf(Operators.DOT_STR);
        if (iLastIndexOf == -1) {
            return null;
        }
        return str.substring(iLastIndexOf + 1);
    }

    public static String getFileProviderUriToPath(Context context, Uri uri) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        try {
            Method declaredMethod = FileProvider.class.getDeclaredMethod("getPathStrategy", Context.class, String.class);
            declaredMethod.setAccessible(true);
            Object objInvoke = declaredMethod.invoke(null, context, uri.getAuthority());
            if (objInvoke != null) {
                Method declaredMethod2 = Class.forName(FileProvider.class.getName() + "$PathStrategy").getDeclaredMethod("getFileForUri", Uri.class);
                declaredMethod2.setAccessible(true);
                Object objInvoke2 = declaredMethod2.invoke(objInvoke, uri);
                if (objInvoke2 instanceof File) {
                    return ((File) objInvoke2).getAbsolutePath();
                }
            }
        } catch (Exception unused) {
        }
        return null;
    }

    public static String getFileTypeForSuffix(String str) {
        String upperCase = str.toUpperCase();
        upperCase.getClass();
        upperCase.hashCode();
        switch (upperCase) {
            case "AAC":
            case "AMR":
            case "M4A":
            case "MKA":
            case "MP3":
            case "OGG":
            case "WAV":
            case "WMA":
            case "FLAC":
                return "audio/*";
            case "ASF":
            case "AVI":
            case "M4V":
            case "MKV":
            case "MP4":
            case "WMV":
            case "3GPP":
            case "WEBM":
            case "3GPP2":
            case "MP2TS":
                return "video/*";
            case "BMP":
            case "GIF":
            case "JPG":
            case "PNG":
            case "JPEG":
            case "WBMP":
            case "WEBP":
                return "image/*";
            default:
                return null;
        }
    }

    public static Uri getFileUri(Context context, File file, Uri uri) {
        Cursor cursorQuery = context.getContentResolver().query(uri, new String[]{"_id"}, "_data=? ", new String[]{file.getAbsolutePath()}, null);
        if (cursorQuery == null || !cursorQuery.moveToFirst()) {
            if (cursorQuery == null) {
                return null;
            }
            cursorQuery.close();
            return null;
        }
        int i = cursorQuery.getInt(cursorQuery.getColumnIndex("_id"));
        cursorQuery.close();
        return Uri.withAppendedPath(uri, "" + i);
    }

    public static InputStream getImageFileStream(Context context, File file) {
        Uri imageFileUri = getImageFileUri(context, file.getPath());
        if (imageFileUri == null) {
            return null;
        }
        try {
            return context.getContentResolver().openInputStream(imageFileUri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Uri getImageFileUri(Context context, String str) {
        return getImageFileUri(context, new File(str));
    }

    public static String getPathForPublicType(String str) {
        if (str.contains(DeviceInfo.sPublicDCIMDir)) {
            return Environment.DIRECTORY_DCIM;
        }
        if (str.contains(DeviceInfo.sPublicDownloadDir)) {
            return Environment.DIRECTORY_DOWNLOADS;
        }
        if (str.contains(DeviceInfo.sPublicMoviesDir)) {
            return Environment.DIRECTORY_MOVIES;
        }
        if (str.contains(DeviceInfo.sPublicMusicDir)) {
            return Environment.DIRECTORY_MUSIC;
        }
        if (str.contains(DeviceInfo.sPublicPicturesDir)) {
            return Environment.DIRECTORY_PICTURES;
        }
        if (str.contains(DeviceInfo.sPublicDocumentsDir)) {
            return Environment.DIRECTORY_DOCUMENTS;
        }
        if (str.contains(DeviceInfo.sPublicRingtonesDir)) {
            return Environment.DIRECTORY_RINGTONES;
        }
        return null;
    }

    public static DCFileUriData getShareImageUri(Context context, File file, String str, Intent intent) {
        DCFileUriData dCFileUriData = new DCFileUriData();
        String path = file.getPath();
        dCFileUriData.filePath = path;
        if (isExternalPublicDir(path) || Build.VERSION.SDK_INT < 29 || getPathForPublicType(path) != null) {
            if (!isExternalPublicDir(path)) {
                dCFileUriData.fileUri = Uri.fromFile(file);
                return dCFileUriData;
            }
            File parentFile = file.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            dCFileUriData.fileUri = FileProvider.getUriForFile(context, context.getPackageName() + ".dc.fileprovider", file);
            return dCFileUriData;
        }
        String str2 = context.getExternalFilesDir(Environment.DIRECTORY_DCIM).getPath() + "/" + str.hashCode() + Operators.DOT_STR + getFileNameWithSuffix(str);
        File file2 = new File(str2);
        File parentFile2 = file2.getParentFile();
        if (!parentFile2.exists()) {
            parentFile2.mkdirs();
        }
        dCFileUriData.fileUri = FileProvider.getUriForFile(context, context.getPackageName() + ".dc.fileprovider", file2);
        dCFileUriData.isReplace = true;
        dCFileUriData.fileReplacePath = str2;
        if (intent != null) {
            intent.addFlags(1);
        }
        return dCFileUriData;
    }

    public static Uri getVideoFileUri(Context context, String str) {
        File file = new File(str);
        return checkPrivatePath(context, str) ? Uri.fromFile(file) : getFileUri(context, file, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
    }

    public static boolean isAssetFile(String str) {
        return str.startsWith("apps/") || str.startsWith("/android_asset/") || str.startsWith("android_asset/");
    }

    private static boolean isExternalPublicDir(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(DeviceInfo.sPrivateExternalDir);
        sb.append("/file/");
        return str.contains(sb.toString()) || str.contains(DeviceInfo.sBaseFsRootPath);
    }

    public static boolean isFilePathForPublic(Context context, String str) {
        if (PdrUtil.isEmpty(str)) {
            return false;
        }
        if (str.startsWith(DeviceInfo.FILE_PROTOCOL)) {
            str = str.replace(DeviceInfo.FILE_PROTOCOL, "");
        }
        File file = new File(str);
        if (!file.exists()) {
            return false;
        }
        if (!needMediaStoreOpenFile(context)) {
            return true;
        }
        String fileNameWithSuffix = getFileNameWithSuffix(str);
        return (PdrUtil.isEmpty(fileNameWithSuffix) || getFileUri(context, file, getContentUriForSuffix(fileNameWithSuffix)) == null) ? false : true;
    }

    public static boolean needMediaStoreOpenFile(Context context) {
        int i = Build.VERSION.SDK_INT;
        return (i >= 29 && context.getApplicationInfo().targetSdkVersion >= 29) || i >= 30;
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x003b A[PHI: r8
      0x003b: PHI (r8v4 android.database.Cursor) = (r8v3 android.database.Cursor), (r8v5 android.database.Cursor) binds: [B:19:0x0039, B:12:0x002d] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0044  */
    /* JADX WARN: Removed duplicated region for block: B:32:? A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static java.lang.String queryAbsolutePath(android.content.Context r8, android.net.Uri r9) throws java.lang.Throwable {
        /*
            java.lang.String r0 = "_data"
            java.lang.String[] r3 = new java.lang.String[]{r0}
            r7 = 0
            android.content.ContentResolver r1 = r8.getContentResolver()     // Catch: java.lang.Throwable -> L30 java.lang.Exception -> L33
            r5 = 0
            r6 = 0
            r4 = 0
            r2 = r9
            android.database.Cursor r8 = r1.query(r2, r3, r4, r5, r6)     // Catch: java.lang.Throwable -> L30 java.lang.Exception -> L33
            if (r8 == 0) goto L2d
            boolean r9 = r8.moveToFirst()     // Catch: java.lang.Exception -> L2a java.lang.Throwable -> L3f
            if (r9 == 0) goto L2d
            int r9 = r8.getColumnIndexOrThrow(r0)     // Catch: java.lang.Exception -> L2a java.lang.Throwable -> L3f
            java.lang.String r9 = r8.getString(r9)     // Catch: java.lang.Exception -> L2a java.lang.Throwable -> L3f
            r8.close()     // Catch: java.lang.Exception -> L2a java.lang.Throwable -> L3f
            r8.close()
            return r9
        L2a:
            r0 = move-exception
            r9 = r0
            goto L36
        L2d:
            if (r8 == 0) goto L3e
            goto L3b
        L30:
            r0 = move-exception
            r9 = r0
            goto L42
        L33:
            r0 = move-exception
            r9 = r0
            r8 = r7
        L36:
            r9.printStackTrace()     // Catch: java.lang.Throwable -> L3f
            if (r8 == 0) goto L3e
        L3b:
            r8.close()
        L3e:
            return r7
        L3f:
            r0 = move-exception
            r9 = r0
            r7 = r8
        L42:
            if (r7 == 0) goto L47
            r7.close()
        L47:
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.common.util.FileUtil.queryAbsolutePath(android.content.Context, android.net.Uri):java.lang.String");
    }

    public static Object readData4Disk(Context context, String str) throws Throwable {
        ObjectInputStream objectInputStream;
        ObjectInputStream objectInputStream2 = null;
        if (context != null && !TextUtils.isEmpty(str)) {
            try {
                objectInputStream = new ObjectInputStream(new FileInputStream(context.getFileStreamPath(str)));
                try {
                    Object object = objectInputStream.readObject();
                    objectInputStream.close();
                    IOUtil.close(objectInputStream);
                    return object;
                } catch (Exception unused) {
                    IOUtil.close(objectInputStream);
                    return null;
                } catch (Throwable th) {
                    th = th;
                    objectInputStream2 = objectInputStream;
                    IOUtil.close(objectInputStream2);
                    throw th;
                }
            } catch (Exception unused2) {
                objectInputStream = null;
            } catch (Throwable th2) {
                th = th2;
            }
        }
        return null;
    }

    public static void saveData2Disk(Context context, Object obj, String str) throws Throwable {
        if (context == null || obj == null || !(obj instanceof Serializable) || TextUtils.isEmpty(str)) {
            return;
        }
        ObjectOutputStream objectOutputStream = null;
        try {
            File fileStreamPath = context.getFileStreamPath(str);
            if (!fileStreamPath.exists()) {
                fileStreamPath.createNewFile();
            }
            ObjectOutputStream objectOutputStream2 = new ObjectOutputStream(new FileOutputStream(fileStreamPath));
            try {
                objectOutputStream2.writeObject(obj);
                objectOutputStream2.close();
                IOUtil.close(objectOutputStream2);
            } catch (IOException unused) {
                objectOutputStream = objectOutputStream2;
                IOUtil.close(objectOutputStream);
            } catch (Throwable th) {
                th = th;
                objectOutputStream = objectOutputStream2;
                IOUtil.close(objectOutputStream);
                throw th;
            }
        } catch (IOException unused2) {
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static void writeStream2File(InputStream inputStream, File file) throws Throwable {
        FileOutputStream fileOutputStream;
        FileOutputStream fileOutputStream2 = null;
        try {
            if (inputStream != null) {
                try {
                    fileOutputStream = new FileOutputStream(file);
                } catch (Exception e) {
                    e = e;
                }
                try {
                    byte[] bArr = new byte[1024];
                    while (true) {
                        int i = inputStream.read(bArr);
                        if (i == -1) {
                            break;
                        } else {
                            fileOutputStream.write(bArr, 0, i);
                        }
                    }
                    fileOutputStream.close();
                    inputStream.close();
                    fileOutputStream2 = fileOutputStream;
                } catch (Exception e2) {
                    e = e2;
                    fileOutputStream2 = fileOutputStream;
                    e.printStackTrace();
                    IOUtil.close(inputStream);
                    IOUtil.close(fileOutputStream2);
                    return;
                } catch (Throwable th) {
                    th = th;
                    IOUtil.close(inputStream);
                    IOUtil.close(fileOutputStream);
                    throw th;
                }
            }
            IOUtil.close(inputStream);
            IOUtil.close(fileOutputStream2);
        } catch (Throwable th2) {
            th = th2;
            fileOutputStream = fileOutputStream2;
        }
    }

    public static String getPathFromUri(Context context, Uri uri) {
        Uri uri2;
        if (uri == null) {
            return null;
        }
        if (!DocumentsContract.isDocumentUri(context, uri)) {
            String scheme = uri.getScheme();
            if (UriUtil.LOCAL_CONTENT_SCHEME.equals(scheme)) {
                return queryAbsolutePath(context, uri);
            }
            if ("file".equals(scheme)) {
                return uri.getPath();
            }
            return null;
        }
        String authority = uri.getAuthority();
        if ("com.android.externalstorage.documents".equals(authority)) {
            String[] strArrSplit = DocumentsContract.getDocumentId(uri).split(":");
            String str = strArrSplit[0];
            return "primary".equals(str) ? Environment.getExternalStorageDirectory().getAbsolutePath().concat("/").concat(strArrSplit[1]) : "/storage/".concat(str).concat("/").concat(strArrSplit[1]);
        }
        if ("com.android.providers.downloads.documents".equals(authority)) {
            String documentId = DocumentsContract.getDocumentId(uri);
            return documentId.startsWith("raw:") ? documentId.replaceFirst("raw:", "") : queryAbsolutePath(context, ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.parseLong(documentId)));
        }
        if ("com.android.providers.media.documents".equals(authority)) {
            String[] strArrSplit2 = DocumentsContract.getDocumentId(uri).split(":");
            String str2 = strArrSplit2[0];
            if ("image".equals(str2)) {
                uri2 = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            } else if ("video".equals(str2)) {
                uri2 = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
            } else if ("audio".equals(str2)) {
                uri2 = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            }
            return queryAbsolutePath(context, ContentUris.withAppendedId(uri2, Long.parseLong(strArrSplit2[1])));
        }
        return null;
    }

    public static Uri getImageFileUri(Context context, File file) {
        if (checkPrivatePath(context, file.getPath())) {
            return Uri.fromFile(file);
        }
        return getFileUri(context, file, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0110 A[Catch: FileNotFoundException -> 0x0129, TryCatch #0 {FileNotFoundException -> 0x0129, blocks: (B:7:0x000f, B:9:0x0019, B:11:0x001f, B:13:0x0029, B:15:0x002f, B:17:0x0039, B:19:0x0042, B:21:0x004c, B:25:0x0057, B:27:0x005f, B:29:0x0065, B:31:0x006f, B:33:0x0078, B:35:0x0080, B:37:0x0088, B:39:0x0091, B:41:0x0099, B:43:0x00a1, B:45:0x00aa, B:47:0x00b2, B:49:0x00ba, B:51:0x00c3, B:53:0x00cb, B:55:0x00d5, B:57:0x00de, B:59:0x00e6, B:61:0x00ee, B:63:0x00f7, B:65:0x00ff, B:67:0x0107, B:69:0x0110, B:71:0x011a, B:73:0x0123), top: B:78:0x000f }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.io.InputStream getFileInputStream(android.content.Context r4, java.io.File r5) {
        /*
            Method dump skipped, instructions count: 332
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.common.util.FileUtil.getFileInputStream(android.content.Context, java.io.File):java.io.InputStream");
    }

    public static Uri copyMediaFileToDCIM(Context context, InputStream inputStream, String str) {
        if (!needMediaStoreOpenFile(context) || inputStream == null) {
            return null;
        }
        try {
            String fileNameWithSuffix = getFileNameWithSuffix(str);
            if (TextUtils.isEmpty(getFileTypeForSuffix(fileNameWithSuffix))) {
                return null;
            }
            return copyMediaFile(context, inputStream, str, fileNameWithSuffix, "DCIM/", "Camera/");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
