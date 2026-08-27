package io.dcloud.common.util.db;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteFullException;
import android.database.sqlite.SQLiteStatement;
import android.text.TextUtils;
import io.dcloud.application.DCLoudApplicationImpl;
import io.dcloud.base.R;
import io.dcloud.common.DHInterface.IApp;
import io.dcloud.common.adapter.util.SP;
import io.dcloud.common.util.PdrUtil;
import io.dcloud.feature.internal.sdk.SDK;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class DCStorage {
    public static final int ERROR_DEF = -1;
    public static final int ERROR_FULL = -2;
    public static final int ERROR_NO_KEY = -3;
    public static final int SUCCESS = 1;
    private static DCStorage mInstance;
    private DCSQLiteOpenHelper mDatabaseSupplier;
    private ExecutorService mExecutorService;
    private String TABLET_TAG = "_storage";
    private String DBFILE_NAME = "_dbfile";
    private String DCDBFILE_START = "DCDBFile_";
    private String ERROR_TAG = "__ERROR__";

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public class StorageInfo {
        public int code;
        public String meg;
        public Object v;

        public StorageInfo() {
        }
    }

    private DCStorage(Context context) {
        this.mDatabaseSupplier = DCSQLiteOpenHelper.getSQLiteOpenHelper(context);
    }

    private void clearDBFile(Context context, String str) {
        File file = new File(getBaseDBFilePath(context, str));
        if (file.exists()) {
            file.delete();
        }
    }

    private String getBaseDBFilePath(Context context, String str) {
        return context.getFilesDir().getAbsolutePath() + "/apps/" + str + "/dbfile/";
    }

    private String getCurrentTableName(String str) {
        return "DC_" + Math.abs(str.hashCode()) + this.TABLET_TAG;
    }

    public static DCStorage getDCStorage(Context context) {
        if (mInstance == null && context != null) {
            mInstance = new DCStorage(context);
        }
        return mInstance;
    }

    private String getMsgForCode(Context context, int i) {
        return i != -3 ? i != -2 ? i != 1 ? "" : context.getString(R.string.dcloud_storage_success) : context.getString(R.string.dcloud_storage_ceiling_error) : context.getString(R.string.dcloud_storage_not_find_error);
    }

    private void removeDBFile(Context context, String str, String str2) {
        removeDBFile(getBaseDBFilePath(context, str) + str2 + this.DBFILE_NAME);
    }

    private String saveDBFileValue(Context context, String str, String str2, String str3) throws IOException {
        String baseDBFilePath = getBaseDBFilePath(context, str);
        File file = new File(baseDBFilePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        if (file.isFile()) {
            file.delete();
            file.mkdirs();
        }
        String str4 = baseDBFilePath + str2.hashCode() + this.DBFILE_NAME;
        File file2 = new File(str4);
        if (file2.exists()) {
            file2.delete();
        }
        try {
            file2.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(str4);
            fileOutputStream.write(str3.getBytes());
            fileOutputStream.close();
            return this.DCDBFILE_START + str4;
        } catch (IOException e) {
            e.printStackTrace();
            return this.ERROR_TAG + e.getMessage() + str4;
        }
    }

    public synchronized void checkSPstorageToDB(Context context, String str) {
        SharedPreferences orCreateBundle = SP.getOrCreateBundle(context, str + "_storages");
        SharedPreferences.Editor editorEdit = orCreateBundle.edit();
        Map<String, ?> all = orCreateBundle.getAll();
        if (all != null && all.size() > 0) {
            for (String str2 : all.keySet()) {
                if (performSetItem(context, str, str2, (String) all.get(str2)).code == 1) {
                    editorEdit.remove(str2);
                }
            }
        }
        editorEdit.commit();
    }

    public void close() {
        execute(new Runnable() { // from class: io.dcloud.common.util.db.DCStorage.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    if (DCStorage.this.mDatabaseSupplier != null) {
                        DCStorage.this.mDatabaseSupplier.closeDatabase();
                    }
                    if (DCStorage.this.mExecutorService != null) {
                        DCStorage.this.mExecutorService.shutdown();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                DCStorage.this.mExecutorService = null;
            }
        });
    }

    public void execute(Runnable runnable) {
        if (this.mExecutorService == null) {
            this.mExecutorService = Executors.newSingleThreadExecutor();
        }
        if (runnable == null || this.mExecutorService.isShutdown() || this.mExecutorService.isTerminated()) {
            return;
        }
        this.mExecutorService.execute(runnable);
    }

    public synchronized Long getDBCurrentLength(String str) {
        SQLiteDatabase database = this.mDatabaseSupplier.getDatabase(getCurrentTableName(str));
        if (database == null) {
            return 0L;
        }
        File file = new File(database.getPath());
        if (!file.exists()) {
            return 0L;
        }
        return Long.valueOf(file.length());
    }

    public synchronized Long getDBMaxLength(String str) {
        SQLiteDatabase database = this.mDatabaseSupplier.getDatabase(getCurrentTableName(str));
        if (database == null) {
            return 0L;
        }
        return Long.valueOf(database.getMaximumSize());
    }

    public synchronized StorageInfo performClear(Context context, String str) {
        SQLiteDatabase database = this.mDatabaseSupplier.getDatabase(getCurrentTableName(str));
        StorageInfo storageInfo = new StorageInfo();
        if (database == null) {
            storageInfo.code = -1;
            storageInfo.meg = context.getString(R.string.dcloud_storage_no_db_error);
            return storageInfo;
        }
        storageInfo.code = 1;
        try {
            database.execSQL("delete from " + getCurrentTableName(str));
            if (!SDK.isUniMPSDK()) {
                this.mDatabaseSupplier.ensureDatabase(getCurrentTableName(str));
            }
            clearDBFile(context, str);
            return storageInfo;
        } catch (Exception e) {
            storageInfo.code = -1;
            storageInfo.meg = context.getString(R.string.dcloud_storage_native_error) + e.getMessage();
            return storageInfo;
        }
    }

    public synchronized StorageInfo performGetAllKeys(String str) {
        SQLiteDatabase database = this.mDatabaseSupplier.getDatabase(getCurrentTableName(str));
        StorageInfo storageInfo = new StorageInfo();
        Context context = DCLoudApplicationImpl.self().getContext();
        if (database == null) {
            storageInfo.code = -1;
            storageInfo.meg = context.getString(R.string.dcloud_storage_no_db_error);
            return storageInfo;
        }
        ArrayList arrayList = new ArrayList();
        Cursor cursorQuery = database.query(getCurrentTableName(str), new String[]{IApp.ConfigProperty.CONFIG_KEY}, null, null, null, null, null);
        while (cursorQuery.moveToNext()) {
            try {
                try {
                    arrayList.add(cursorQuery.getString(cursorQuery.getColumnIndex(IApp.ConfigProperty.CONFIG_KEY)));
                } catch (Exception e) {
                    storageInfo.code = -1;
                    storageInfo.meg = context.getString(R.string.dcloud_storage_native_error) + e.getMessage();
                    return storageInfo;
                }
            } finally {
                cursorQuery.close();
            }
        }
        storageInfo.code = 1;
        storageInfo.v = arrayList;
        return storageInfo;
    }

    public synchronized StorageInfo performGetItem(String str, String str2) {
        SQLiteDatabase database = this.mDatabaseSupplier.getDatabase(getCurrentTableName(str));
        StorageInfo storageInfo = new StorageInfo();
        Context context = DCLoudApplicationImpl.self().getContext();
        if (database == null) {
            storageInfo.code = -1;
            storageInfo.meg = context.getString(R.string.dcloud_storage_no_db_error);
            return storageInfo;
        }
        Cursor cursorQuery = database.query(getCurrentTableName(str), new String[]{"value"}, "key=?", new String[]{str2}, null, null, null);
        try {
            try {
                if (!cursorQuery.moveToNext()) {
                    storageInfo.code = -3;
                    storageInfo.meg = getMsgForCode(context, -3);
                    cursorQuery.close();
                    return storageInfo;
                }
                ContentValues contentValues = new ContentValues();
                contentValues.put("timestamp", DCSQLiteOpenHelper.sDateFormatter.format(new Date()));
                this.mDatabaseSupplier.getDatabase(getCurrentTableName(str)).update(getCurrentTableName(str), contentValues, "key= ?", new String[]{str2});
                String string = cursorQuery.getString(cursorQuery.getColumnIndex("value"));
                if (TextUtils.isEmpty(string) || !string.startsWith(this.DCDBFILE_START)) {
                    storageInfo.v = string;
                } else {
                    String dBFileValue = getDBFileValue(string);
                    if (PdrUtil.isEmpty(dBFileValue)) {
                        storageInfo.v = string;
                    } else {
                        storageInfo.v = dBFileValue;
                    }
                }
                storageInfo.code = 1;
                cursorQuery.close();
                return storageInfo;
            } catch (Exception e) {
                storageInfo.code = -1;
                storageInfo.meg = context.getString(R.string.dcloud_storage_native_error) + e.getMessage();
                cursorQuery.close();
                return storageInfo;
            }
        } catch (Throwable th) {
            cursorQuery.close();
            throw th;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0075 A[Catch: all -> 0x007a, PHI: r0
      0x0075: PHI (r0v3 android.database.sqlite.SQLiteStatement) = (r0v2 android.database.sqlite.SQLiteStatement), (r0v4 android.database.sqlite.SQLiteStatement) binds: [B:18:0x0070, B:20:0x0073] A[DONT_GENERATE, DONT_INLINE], TRY_ENTER, TRY_LEAVE, TryCatch #1 {, blocks: (B:4:0x0003, B:6:0x001d, B:9:0x0029, B:12:0x004b, B:21:0x0075), top: B:28:0x0003 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public synchronized io.dcloud.common.util.db.DCStorage.StorageInfo performGetLength(java.lang.String r8) {
        /*
            r7 = this;
            java.lang.String r0 = "SELECT count(key) FROM "
            monitor-enter(r7)
            io.dcloud.common.util.db.DCSQLiteOpenHelper r1 = r7.mDatabaseSupplier     // Catch: java.lang.Throwable -> L7a
            java.lang.String r2 = r7.getCurrentTableName(r8)     // Catch: java.lang.Throwable -> L7a
            android.database.sqlite.SQLiteDatabase r1 = r1.getDatabase(r2)     // Catch: java.lang.Throwable -> L7a
            io.dcloud.common.util.db.DCStorage$StorageInfo r2 = new io.dcloud.common.util.db.DCStorage$StorageInfo     // Catch: java.lang.Throwable -> L7a
            r2.<init>()     // Catch: java.lang.Throwable -> L7a
            io.dcloud.application.DCLoudApplicationImpl r3 = io.dcloud.application.DCLoudApplicationImpl.self()     // Catch: java.lang.Throwable -> L7a
            android.content.Context r3 = r3.getContext()     // Catch: java.lang.Throwable -> L7a
            r4 = -1
            if (r1 != 0) goto L29
            r2.code = r4     // Catch: java.lang.Throwable -> L7a
            int r8 = io.dcloud.base.R.string.dcloud_storage_no_db_error     // Catch: java.lang.Throwable -> L7a
            java.lang.String r8 = r3.getString(r8)     // Catch: java.lang.Throwable -> L7a
            r2.meg = r8     // Catch: java.lang.Throwable -> L7a
            monitor-exit(r7)
            return r2
        L29:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L7a
            r5.<init>(r0)     // Catch: java.lang.Throwable -> L7a
            java.lang.String r8 = r7.getCurrentTableName(r8)     // Catch: java.lang.Throwable -> L7a
            r5.append(r8)     // Catch: java.lang.Throwable -> L7a
            java.lang.String r8 = r5.toString()     // Catch: java.lang.Throwable -> L7a
            r0 = 0
            android.database.sqlite.SQLiteStatement r0 = r1.compileStatement(r8)     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
            long r5 = r0.simpleQueryForLong()     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
            java.lang.Long r8 = java.lang.Long.valueOf(r5)     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
            r2.v = r8     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
            r8 = 1
            r2.code = r8     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
            r0.close()     // Catch: java.lang.Throwable -> L7a
            monitor-exit(r7)
            return r2
        L50:
            goto L73
        L52:
            r8 = move-exception
            r2.code = r4     // Catch: java.lang.Throwable -> L50
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L50
            r1.<init>()     // Catch: java.lang.Throwable -> L50
            int r4 = io.dcloud.base.R.string.dcloud_storage_native_error     // Catch: java.lang.Throwable -> L50
            java.lang.String r3 = r3.getString(r4)     // Catch: java.lang.Throwable -> L50
            r1.append(r3)     // Catch: java.lang.Throwable -> L50
            java.lang.String r8 = r8.getMessage()     // Catch: java.lang.Throwable -> L50
            r1.append(r8)     // Catch: java.lang.Throwable -> L50
            java.lang.String r8 = r1.toString()     // Catch: java.lang.Throwable -> L50
            r2.meg = r8     // Catch: java.lang.Throwable -> L50
            if (r0 == 0) goto L78
            goto L75
        L73:
            if (r0 == 0) goto L78
        L75:
            r0.close()     // Catch: java.lang.Throwable -> L7a
        L78:
            monitor-exit(r7)
            return r2
        L7a:
            r8 = move-exception
            monitor-exit(r7)     // Catch: java.lang.Throwable -> L7a
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.common.util.db.DCStorage.performGetLength(java.lang.String):io.dcloud.common.util.db.DCStorage$StorageInfo");
    }

    public synchronized StorageInfo performRemoveItem(Context context, String str, String str2) {
        SQLiteDatabase database = this.mDatabaseSupplier.getDatabase(getCurrentTableName(str));
        StorageInfo storageInfo = new StorageInfo();
        if (database == null) {
            storageInfo.code = -1;
            storageInfo.meg = context.getString(R.string.dcloud_storage_no_db_error);
            return storageInfo;
        }
        try {
            int iDelete = database.delete(getCurrentTableName(str), "key=?", new String[]{str2});
            if (iDelete > 0) {
                removeDBFile(context, str, str2);
            }
            if (iDelete == 1) {
                storageInfo.code = 1;
                return storageInfo;
            }
            storageInfo.code = -3;
            storageInfo.meg = getMsgForCode(context, -3);
            return storageInfo;
        } catch (Exception e) {
            storageInfo.code = -1;
            storageInfo.meg = context.getString(R.string.dcloud_storage_native_error) + e.getMessage();
            return storageInfo;
        }
    }

    public synchronized StorageInfo performSetItem(Context context, String str, String str2, String str3) {
        SQLiteDatabase database = this.mDatabaseSupplier.getDatabase(getCurrentTableName(str));
        StorageInfo storageInfo = new StorageInfo();
        if (database == null) {
            storageInfo.code = -1;
            storageInfo.meg = context.getString(R.string.dcloud_storage_no_db_error);
            return storageInfo;
        }
        if (PdrUtil.isEmpty(str2)) {
            storageInfo.code = -1;
            storageInfo.meg = context.getString(R.string.dcloud_storage_key_error);
            return storageInfo;
        }
        String str4 = "INSERT OR REPLACE INTO " + getCurrentTableName(str) + " VALUES (?,?,?);";
        String str5 = DCSQLiteOpenHelper.sDateFormatter.format(new Date());
        SQLiteStatement sQLiteStatement = null;
        try {
            try {
            } catch (Exception e) {
                if (e instanceof SQLiteFullException) {
                    storageInfo.code = -2;
                    storageInfo.meg = getMsgForCode(context, -2);
                    if (0 != 0) {
                    }
                } else {
                    storageInfo.code = -1;
                    storageInfo.meg = context.getString(R.string.dcloud_storage_native_error) + e.getMessage();
                }
            }
            if (str3.getBytes().length >= 1800000) {
                str3 = saveDBFileValue(context, str, str2, str3);
                if (TextUtils.isEmpty(str3) || str3.startsWith(this.ERROR_TAG)) {
                    storageInfo.code = -1;
                    storageInfo.meg = context.getString(R.string.dcloud_storage_write_big_error);
                    if (!TextUtils.isEmpty(str3) && str3.startsWith(this.ERROR_TAG)) {
                        storageInfo.meg += " error " + str3.replace(this.ERROR_TAG, "");
                        return storageInfo;
                    }
                    return storageInfo;
                }
            }
            SQLiteStatement sQLiteStatementCompileStatement = database.compileStatement(str4);
            sQLiteStatementCompileStatement.clearBindings();
            sQLiteStatementCompileStatement.bindString(1, str2);
            sQLiteStatementCompileStatement.bindString(2, str3);
            sQLiteStatementCompileStatement.bindString(3, str5);
            sQLiteStatementCompileStatement.execute();
            storageInfo.code = 1;
            sQLiteStatementCompileStatement.close();
            return storageInfo;
        } finally {
            if (0 != 0) {
                sQLiteStatement.close();
            }
        }
    }

    private String getDBFileValue(String str) throws Throwable {
        BufferedReader bufferedReader;
        if (!PdrUtil.isEmpty(str)) {
            if (str.startsWith(this.DCDBFILE_START)) {
                str = str.substring(9);
            }
            if (new File(str).exists()) {
                StringBuffer stringBuffer = new StringBuffer();
                BufferedReader bufferedReader2 = null;
                try {
                    try {
                        try {
                            bufferedReader = new BufferedReader(new FileReader(str));
                            while (true) {
                                try {
                                    String line = bufferedReader.readLine();
                                    if (line == null) {
                                        String string = stringBuffer.toString();
                                        bufferedReader.close();
                                        return string;
                                    }
                                    stringBuffer.append(line);
                                } catch (FileNotFoundException e) {
                                    e = e;
                                    bufferedReader2 = bufferedReader;
                                    e.printStackTrace();
                                    if (bufferedReader2 != null) {
                                        bufferedReader2.close();
                                    }
                                    return "";
                                } catch (IOException e2) {
                                    e = e2;
                                    bufferedReader2 = bufferedReader;
                                    e.printStackTrace();
                                    if (bufferedReader2 != null) {
                                        bufferedReader2.close();
                                    }
                                    return "";
                                } catch (Throwable th) {
                                    th = th;
                                    if (bufferedReader != null) {
                                        try {
                                            bufferedReader.close();
                                        } catch (IOException unused) {
                                        }
                                    }
                                    throw th;
                                }
                            }
                        } catch (IOException unused2) {
                        }
                    } catch (FileNotFoundException e3) {
                        e = e3;
                    } catch (IOException e4) {
                        e = e4;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    bufferedReader = bufferedReader2;
                }
            }
        }
        return "";
    }

    private void removeDBFile(String str) {
        if (PdrUtil.isEmpty(str)) {
            return;
        }
        if (str.startsWith(this.DCDBFILE_START)) {
            str = str.substring(9);
        }
        File file = new File(str);
        if (file.exists()) {
            file.delete();
        }
    }
}
