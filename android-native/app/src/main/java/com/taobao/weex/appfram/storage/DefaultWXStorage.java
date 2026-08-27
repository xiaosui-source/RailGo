package com.taobao.weex.appfram.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import com.taobao.weex.appfram.storage.IWXStorageAdapter;
import com.taobao.weex.common.WXThread;
import com.taobao.weex.ui.component.WXImage;
import com.taobao.weex.utils.WXLogUtils;
import io.dcloud.common.DHInterface.IApp;
import io.dcloud.common.constant.AbsoluteConst;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class DefaultWXStorage implements IWXStorageAdapter {
    private WXSQLiteOpenHelper mDatabaseSupplier;
    private ExecutorService mExecutorService;

    public DefaultWXStorage(Context context) {
        this.mDatabaseSupplier = new WXSQLiteOpenHelper(context);
    }

    private void execute(Runnable runnable) {
        if (this.mExecutorService == null) {
            this.mExecutorService = Executors.newSingleThreadExecutor();
        }
        if (runnable == null || this.mExecutorService.isShutdown() || this.mExecutorService.isTerminated()) {
            return;
        }
        this.mExecutorService.execute(WXThread.secure(runnable));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<String> performGetAllKeys() {
        SQLiteDatabase database = this.mDatabaseSupplier.getDatabase();
        if (database == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        Cursor cursorQuery = database.query("default_wx_storage", new String[]{IApp.ConfigProperty.CONFIG_KEY}, null, null, null, null, null);
        while (cursorQuery.moveToNext()) {
            try {
                arrayList.add(cursorQuery.getString(cursorQuery.getColumnIndex(IApp.ConfigProperty.CONFIG_KEY)));
            } catch (Exception e) {
                WXLogUtils.e("weex_storage", "DefaultWXStorage occurred an exception when execute getAllKeys:" + e.getMessage());
                return arrayList;
            } finally {
                cursorQuery.close();
            }
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String performGetItem(String str) {
        SQLiteDatabase database = this.mDatabaseSupplier.getDatabase();
        if (database == null) {
            return null;
        }
        Cursor cursorQuery = database.query("default_wx_storage", new String[]{"value"}, "key=?", new String[]{str}, null, null, null);
        try {
            try {
                if (!cursorQuery.moveToNext()) {
                    cursorQuery.close();
                    return null;
                }
                ContentValues contentValues = new ContentValues();
                contentValues.put("timestamp", WXSQLiteOpenHelper.sDateFormatter.format(new Date()));
                int iUpdate = this.mDatabaseSupplier.getDatabase().update("default_wx_storage", contentValues, "key= ?", new String[]{str});
                StringBuilder sb = new StringBuilder("update timestamp ");
                sb.append(iUpdate == 1 ? WXImage.SUCCEED : AbsoluteConst.EVENTS_FAILED);
                sb.append(" for operation [getItem(key = ");
                sb.append(str);
                sb.append(")]");
                WXLogUtils.d("weex_storage", sb.toString());
                String string = cursorQuery.getString(cursorQuery.getColumnIndex("value"));
                cursorQuery.close();
                return string;
            } catch (Exception e) {
                WXLogUtils.e("weex_storage", "DefaultWXStorage occurred an exception when execute getItem:" + e.getMessage());
                cursorQuery.close();
                return null;
            }
        } catch (Throwable th) {
            cursorQuery.close();
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public long performGetLength() {
        SQLiteDatabase database = this.mDatabaseSupplier.getDatabase();
        if (database == null) {
            return 0L;
        }
        SQLiteStatement sQLiteStatementCompileStatement = null;
        try {
            try {
                sQLiteStatementCompileStatement = database.compileStatement("SELECT count(key) FROM default_wx_storage");
                long jSimpleQueryForLong = sQLiteStatementCompileStatement.simpleQueryForLong();
                sQLiteStatementCompileStatement.close();
                return jSimpleQueryForLong;
            } catch (Exception e) {
                WXLogUtils.e("weex_storage", "DefaultWXStorage occurred an exception when execute getLength:" + e.getMessage());
                if (sQLiteStatementCompileStatement != null) {
                    sQLiteStatementCompileStatement.close();
                }
                return 0L;
            }
        } catch (Throwable th) {
            if (sQLiteStatementCompileStatement != null) {
                sQLiteStatementCompileStatement.close();
            }
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean performRemoveItem(String str) {
        SQLiteDatabase database = this.mDatabaseSupplier.getDatabase();
        if (database == null) {
            return false;
        }
        try {
            return database.delete("default_wx_storage", "key=?", new String[]{str}) == 1;
        } catch (Exception e) {
            WXLogUtils.e("weex_storage", "DefaultWXStorage occurred an exception when execute removeItem:" + e.getMessage());
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00d2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean performSetItem(java.lang.String r18, java.lang.String r19, boolean r20, boolean r21) throws java.lang.Throwable {
        /*
            r17 = this;
            r1 = r17
            r2 = r18
            r3 = r19
            r4 = r20
            r5 = r21
            java.lang.String r6 = "retry set k-v to storage(key:"
            java.lang.String r7 = "DefaultWXStorage occurred an exception when execute setItem :"
            com.taobao.weex.appfram.storage.WXSQLiteOpenHelper r0 = r1.mDatabaseSupplier
            android.database.sqlite.SQLiteDatabase r0 = r0.getDatabase()
            r8 = 0
            if (r0 != 0) goto L18
            return r8
        L18:
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            java.lang.String r10 = "set k-v to storage(key:"
            r9.<init>(r10)
            r9.append(r2)
            java.lang.String r10 = ",value:"
            r9.append(r10)
            r9.append(r3)
            java.lang.String r11 = ",isPersistent:"
            r9.append(r11)
            r9.append(r4)
            java.lang.String r11 = ",allowRetry:"
            r9.append(r11)
            r9.append(r5)
            java.lang.String r11 = ")"
            r9.append(r11)
            java.lang.String r9 = r9.toString()
            java.lang.String r12 = "weex_storage"
            com.taobao.weex.utils.WXLogUtils.d(r12, r9)
            java.lang.String r9 = "INSERT OR REPLACE INTO default_wx_storage VALUES (?,?,?,?);"
            java.text.SimpleDateFormat r13 = com.taobao.weex.appfram.storage.WXSQLiteOpenHelper.sDateFormatter
            java.util.Date r14 = new java.util.Date
            r14.<init>()
            java.lang.String r13 = r13.format(r14)
            r14 = 0
            android.database.sqlite.SQLiteStatement r14 = r0.compileStatement(r9)     // Catch: java.lang.Throwable -> L7e java.lang.Exception -> L82
            r14.clearBindings()     // Catch: java.lang.Throwable -> L7e java.lang.Exception -> L82
            r0 = 1
            r14.bindString(r0, r2)     // Catch: java.lang.Throwable -> L7e java.lang.Exception -> L82
            r9 = 2
            r14.bindString(r9, r3)     // Catch: java.lang.Throwable -> L7e java.lang.Exception -> L82
            r9 = 3
            r14.bindString(r9, r13)     // Catch: java.lang.Throwable -> L7e java.lang.Exception -> L82
            if (r4 == 0) goto L6f
            r15 = 1
            goto L71
        L6f:
            r15 = 0
        L71:
            r0 = r15
            r9 = 1
            r13 = 4
            r14.bindLong(r13, r0)     // Catch: java.lang.Throwable -> L7e java.lang.Exception -> L82
            r14.execute()     // Catch: java.lang.Throwable -> L7e java.lang.Exception -> L82
            r14.close()
            return r9
        L7e:
            r0 = move-exception
            r1 = r17
            goto Ld0
        L82:
            r0 = move-exception
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L7e
            r1.<init>(r7)     // Catch: java.lang.Throwable -> L7e
            java.lang.String r7 = r0.getMessage()     // Catch: java.lang.Throwable -> L7e
            r1.append(r7)     // Catch: java.lang.Throwable -> L7e
            java.lang.String r1 = r1.toString()     // Catch: java.lang.Throwable -> L7e
            com.taobao.weex.utils.WXLogUtils.e(r12, r1)     // Catch: java.lang.Throwable -> L7e
            boolean r0 = r0 instanceof android.database.sqlite.SQLiteFullException     // Catch: java.lang.Throwable -> L7e
            if (r0 == 0) goto Lc8
            if (r5 == 0) goto Lc8
            boolean r0 = r17.trimToSize()     // Catch: java.lang.Throwable -> L7e
            if (r0 == 0) goto Lc8
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L7e
            r0.<init>(r6)     // Catch: java.lang.Throwable -> L7e
            r0.append(r2)     // Catch: java.lang.Throwable -> L7e
            r0.append(r10)     // Catch: java.lang.Throwable -> L7e
            r0.append(r3)     // Catch: java.lang.Throwable -> L7e
            r0.append(r11)     // Catch: java.lang.Throwable -> L7e
            java.lang.String r0 = r0.toString()     // Catch: java.lang.Throwable -> L7e
            com.taobao.weex.utils.WXLogUtils.d(r12, r0)     // Catch: java.lang.Throwable -> L7e
            r1 = r17
            boolean r0 = r1.performSetItem(r2, r3, r4, r8)     // Catch: java.lang.Throwable -> Lc6
            if (r14 == 0) goto Lc5
            r14.close()
        Lc5:
            return r0
        Lc6:
            r0 = move-exception
            goto Ld0
        Lc8:
            r1 = r17
            if (r14 == 0) goto Lcf
            r14.close()
        Lcf:
            return r8
        Ld0:
            if (r14 == 0) goto Ld5
            r14.close()
        Ld5:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.weex.appfram.storage.DefaultWXStorage.performSetItem(java.lang.String, java.lang.String, boolean, boolean):boolean");
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x0078 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0079  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private boolean trimToSize() {
        /*
            r13 = this;
            java.lang.String r1 = "weex_storage"
            com.taobao.weex.appfram.storage.WXSQLiteOpenHelper r0 = r13.mDatabaseSupplier
            android.database.sqlite.SQLiteDatabase r2 = r0.getDatabase()
            r10 = 0
            if (r2 != 0) goto Ld
            return r10
        Ld:
            java.util.ArrayList r11 = new java.util.ArrayList
            r11.<init>()
            java.lang.String r0 = "key"
            java.lang.String r12 = "persistent"
            java.lang.String[] r4 = new java.lang.String[]{r0, r12}
            r8 = 0
            java.lang.String r9 = "timestamp ASC"
            java.lang.String r3 = "default_wx_storage"
            r5 = 0
            r6 = 0
            r7 = 0
            android.database.Cursor r2 = r2.query(r3, r4, r5, r6, r7, r8, r9)
            r3 = 1
            int r4 = r2.getCount()     // Catch: java.lang.Throwable -> L57 java.lang.Exception -> L59
            int r4 = r4 / 10
            r5 = 0
        L2f:
            boolean r6 = r2.moveToNext()     // Catch: java.lang.Exception -> L55 java.lang.Throwable -> L57
            if (r6 == 0) goto L51
            int r6 = r2.getColumnIndex(r0)     // Catch: java.lang.Exception -> L55 java.lang.Throwable -> L57
            java.lang.String r6 = r2.getString(r6)     // Catch: java.lang.Exception -> L55 java.lang.Throwable -> L57
            int r7 = r2.getColumnIndex(r12)     // Catch: java.lang.Exception -> L55 java.lang.Throwable -> L57
            int r7 = r2.getInt(r7)     // Catch: java.lang.Exception -> L55 java.lang.Throwable -> L57
            if (r7 != r3) goto L48
            goto L2f
        L48:
            if (r6 == 0) goto L2f
            int r5 = r5 + 1
            r11.add(r6)     // Catch: java.lang.Exception -> L55 java.lang.Throwable -> L57
            if (r5 != r4) goto L2f
        L51:
            r2.close()
            goto L76
        L55:
            r0 = move-exception
            goto L5b
        L57:
            r0 = move-exception
            goto La2
        L59:
            r0 = move-exception
            r5 = 0
        L5b:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L57
            r4.<init>()     // Catch: java.lang.Throwable -> L57
            java.lang.String r6 = "DefaultWXStorage occurred an exception when execute trimToSize:"
            r4.append(r6)     // Catch: java.lang.Throwable -> L57
            java.lang.String r0 = r0.getMessage()     // Catch: java.lang.Throwable -> L57
            r4.append(r0)     // Catch: java.lang.Throwable -> L57
            java.lang.String r0 = r4.toString()     // Catch: java.lang.Throwable -> L57
            com.taobao.weex.utils.WXLogUtils.e(r1, r0)     // Catch: java.lang.Throwable -> L57
            r2.close()
        L76:
            if (r5 > 0) goto L79
            return r10
        L79:
            int r0 = r11.size()
        L7d:
            if (r10 >= r0) goto L8b
            java.lang.Object r2 = r11.get(r10)
            int r10 = r10 + 1
            java.lang.String r2 = (java.lang.String) r2
            r13.performRemoveItem(r2)
            goto L7d
        L8b:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r2 = "remove "
            r0.<init>(r2)
            r0.append(r5)
            java.lang.String r2 = " items by lru"
            r0.append(r2)
            java.lang.String r0 = r0.toString()
            com.taobao.weex.utils.WXLogUtils.e(r1, r0)
            return r3
        La2:
            r2.close()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.weex.appfram.storage.DefaultWXStorage.trimToSize():boolean");
    }

    @Override // com.taobao.weex.appfram.storage.IWXStorageAdapter
    public void close() {
        final ExecutorService executorService = this.mExecutorService;
        execute(new Runnable() { // from class: com.taobao.weex.appfram.storage.DefaultWXStorage.7
            @Override // java.lang.Runnable
            public void run() {
                try {
                    DefaultWXStorage.this.mDatabaseSupplier.closeDatabase();
                    ExecutorService executorService2 = executorService;
                    if (executorService2 != null) {
                        executorService2.shutdown();
                    }
                } catch (Exception e) {
                    WXLogUtils.e("weex_storage", e.getMessage());
                }
            }
        });
        this.mExecutorService = null;
    }

    @Override // com.taobao.weex.appfram.storage.IWXStorageAdapter
    public void getAllKeys(final IWXStorageAdapter.OnResultReceivedListener onResultReceivedListener) {
        execute(new Runnable() { // from class: com.taobao.weex.appfram.storage.DefaultWXStorage.5
            @Override // java.lang.Runnable
            public void run() {
                Map<String, Object> allkeysResult = StorageResultHandler.getAllkeysResult(DefaultWXStorage.this.performGetAllKeys());
                IWXStorageAdapter.OnResultReceivedListener onResultReceivedListener2 = onResultReceivedListener;
                if (onResultReceivedListener2 == null) {
                    return;
                }
                onResultReceivedListener2.onReceived(allkeysResult);
            }
        });
    }

    @Override // com.taobao.weex.appfram.storage.IWXStorageAdapter
    public void getItem(final String str, final IWXStorageAdapter.OnResultReceivedListener onResultReceivedListener) {
        execute(new Runnable() { // from class: com.taobao.weex.appfram.storage.DefaultWXStorage.2
            @Override // java.lang.Runnable
            public void run() {
                Map<String, Object> itemResult = StorageResultHandler.getItemResult(DefaultWXStorage.this.performGetItem(str));
                IWXStorageAdapter.OnResultReceivedListener onResultReceivedListener2 = onResultReceivedListener;
                if (onResultReceivedListener2 == null) {
                    return;
                }
                onResultReceivedListener2.onReceived(itemResult);
            }
        });
    }

    @Override // com.taobao.weex.appfram.storage.IWXStorageAdapter
    public void length(final IWXStorageAdapter.OnResultReceivedListener onResultReceivedListener) {
        execute(new Runnable() { // from class: com.taobao.weex.appfram.storage.DefaultWXStorage.4
            @Override // java.lang.Runnable
            public void run() {
                Map<String, Object> lengthResult = StorageResultHandler.getLengthResult(DefaultWXStorage.this.performGetLength());
                IWXStorageAdapter.OnResultReceivedListener onResultReceivedListener2 = onResultReceivedListener;
                if (onResultReceivedListener2 == null) {
                    return;
                }
                onResultReceivedListener2.onReceived(lengthResult);
            }
        });
    }

    @Override // com.taobao.weex.appfram.storage.IWXStorageAdapter
    public void removeItem(final String str, final IWXStorageAdapter.OnResultReceivedListener onResultReceivedListener) {
        execute(new Runnable() { // from class: com.taobao.weex.appfram.storage.DefaultWXStorage.3
            @Override // java.lang.Runnable
            public void run() {
                Map<String, Object> mapRemoveItemResult = StorageResultHandler.removeItemResult(DefaultWXStorage.this.performRemoveItem(str));
                IWXStorageAdapter.OnResultReceivedListener onResultReceivedListener2 = onResultReceivedListener;
                if (onResultReceivedListener2 == null) {
                    return;
                }
                onResultReceivedListener2.onReceived(mapRemoveItemResult);
            }
        });
    }

    @Override // com.taobao.weex.appfram.storage.IWXStorageAdapter
    public void setItem(final String str, final String str2, final IWXStorageAdapter.OnResultReceivedListener onResultReceivedListener) {
        execute(new Runnable() { // from class: com.taobao.weex.appfram.storage.DefaultWXStorage.1
            @Override // java.lang.Runnable
            public void run() {
                Map<String, Object> itemResult = StorageResultHandler.setItemResult(DefaultWXStorage.this.performSetItem(str, str2, false, true));
                IWXStorageAdapter.OnResultReceivedListener onResultReceivedListener2 = onResultReceivedListener;
                if (onResultReceivedListener2 == null) {
                    return;
                }
                onResultReceivedListener2.onReceived(itemResult);
            }
        });
    }

    @Override // com.taobao.weex.appfram.storage.IWXStorageAdapter
    public void setItemPersistent(final String str, final String str2, final IWXStorageAdapter.OnResultReceivedListener onResultReceivedListener) {
        execute(new Runnable() { // from class: com.taobao.weex.appfram.storage.DefaultWXStorage.6
            @Override // java.lang.Runnable
            public void run() {
                Map<String, Object> itemResult = StorageResultHandler.setItemResult(DefaultWXStorage.this.performSetItem(str, str2, true, true));
                IWXStorageAdapter.OnResultReceivedListener onResultReceivedListener2 = onResultReceivedListener;
                if (onResultReceivedListener2 == null) {
                    return;
                }
                onResultReceivedListener2.onReceived(itemResult);
            }
        });
    }
}
