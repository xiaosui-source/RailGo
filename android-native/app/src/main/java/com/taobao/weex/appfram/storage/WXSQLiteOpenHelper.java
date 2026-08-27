package com.taobao.weex.appfram.storage;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import com.taobao.weex.utils.WXLogUtils;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class WXSQLiteOpenHelper extends SQLiteOpenHelper {
    static final String COLUMN_KEY = "key";
    static final String COLUMN_PERSISTENT = "persistent";
    static final String COLUMN_TIMESTAMP = "timestamp";
    static final String COLUMN_VALUE = "value";
    private static final String DATABASE_NAME = "WXStorage";
    private static final int DATABASE_VERSION = 2;
    private static final int SLEEP_TIME_MS = 30;
    private static final String STATEMENT_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS default_wx_storage (key TEXT PRIMARY KEY,value TEXT NOT NULL,timestamp TEXT NOT NULL,persistent INTEGER DEFAULT 0)";
    static final String TABLE_STORAGE = "default_wx_storage";
    static final String TAG_STORAGE = "weex_storage";
    static SimpleDateFormat sDateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    private Context mContext;
    private SQLiteDatabase mDb;
    private long mMaximumDatabaseSize;

    public WXSQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, 2);
        this.mMaximumDatabaseSize = 52428800L;
        this.mContext = context;
    }

    private void createTableIfNotExists(SQLiteDatabase sQLiteDatabase) {
        Cursor cursorRawQuery = null;
        try {
            try {
                cursorRawQuery = sQLiteDatabase.rawQuery("SELECT DISTINCT tbl_name FROM sqlite_master WHERE tbl_name = 'default_wx_storage'", null);
                if (cursorRawQuery != null && cursorRawQuery.getCount() > 0) {
                    cursorRawQuery.close();
                    return;
                }
                sQLiteDatabase.execSQL(STATEMENT_CREATE_TABLE);
                if (cursorRawQuery != null) {
                    cursorRawQuery.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (cursorRawQuery != null) {
                    cursorRawQuery.close();
                }
            }
        } catch (Throwable th) {
            if (cursorRawQuery != null) {
                cursorRawQuery.close();
            }
            throw th;
        }
    }

    private boolean deleteDB() {
        closeDatabase();
        return this.mContext.deleteDatabase(DATABASE_NAME);
    }

    public synchronized void closeDatabase() {
        SQLiteDatabase sQLiteDatabase = this.mDb;
        if (sQLiteDatabase == null || !sQLiteDatabase.isOpen()) {
            return;
        }
        this.mDb.close();
        this.mDb = null;
    }

    synchronized void ensureDatabase() {
        SQLiteDatabase sQLiteDatabase = this.mDb;
        if (sQLiteDatabase == null || !sQLiteDatabase.isOpen()) {
            for (int i = 0; i < 2; i++) {
                if (i > 0) {
                    try {
                        deleteDB();
                    } catch (SQLiteException e) {
                        try {
                            e.printStackTrace();
                            try {
                                Thread.sleep(30L);
                            } catch (InterruptedException unused) {
                                Thread.currentThread().interrupt();
                            }
                        } catch (Throwable th) {
                            this.mDb = null;
                            WXLogUtils.d(TAG_STORAGE, "ensureDatabase failed, throwable = " + th.getMessage());
                        }
                    }
                }
                this.mDb = getWritableDatabase();
            }
            SQLiteDatabase sQLiteDatabase2 = this.mDb;
            if (sQLiteDatabase2 != null) {
                createTableIfNotExists(sQLiteDatabase2);
                this.mDb.setMaximumSize(this.mMaximumDatabaseSize);
            }
        }
    }

    public SQLiteDatabase getDatabase() {
        ensureDatabase();
        return this.mDb;
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) throws SQLException {
        sQLiteDatabase.execSQL(STATEMENT_CREATE_TABLE);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) throws SQLException {
        if (i != i2) {
            if (i2 != 2 || i != 1) {
                deleteDB();
                onCreate(sQLiteDatabase);
                return;
            }
            WXLogUtils.d(TAG_STORAGE, "storage is updating from version " + i + " to version " + i2);
            try {
                try {
                    long jCurrentTimeMillis = System.currentTimeMillis();
                    sQLiteDatabase.beginTransaction();
                    WXLogUtils.d(TAG_STORAGE, "exec sql : ALTER TABLE default_wx_storage ADD COLUMN timestamp TEXT;");
                    sQLiteDatabase.execSQL("ALTER TABLE default_wx_storage ADD COLUMN timestamp TEXT;");
                    WXLogUtils.d(TAG_STORAGE, "exec sql : ALTER TABLE default_wx_storage ADD COLUMN persistent INTEGER;");
                    sQLiteDatabase.execSQL("ALTER TABLE default_wx_storage ADD COLUMN persistent INTEGER;");
                    String str = "UPDATE default_wx_storage SET timestamp = '" + sDateFormatter.format(new Date()) + "' , persistent = 0";
                    WXLogUtils.d(TAG_STORAGE, "exec sql : " + str);
                    sQLiteDatabase.execSQL(str);
                    sQLiteDatabase.setTransactionSuccessful();
                    WXLogUtils.d(TAG_STORAGE, "storage updated success (" + (System.currentTimeMillis() - jCurrentTimeMillis) + "ms)");
                } catch (Exception e) {
                    WXLogUtils.d(TAG_STORAGE, "storage updated failed from version " + i + " to version " + i2 + "," + e.getMessage());
                    sQLiteDatabase.endTransaction();
                    WXLogUtils.d(TAG_STORAGE, "storage is rollback,all data will be removed");
                    deleteDB();
                    onCreate(sQLiteDatabase);
                }
            } finally {
                sQLiteDatabase.endTransaction();
            }
        }
    }

    public synchronized void setMaximumSize(long j) {
        this.mMaximumDatabaseSize = j;
        SQLiteDatabase sQLiteDatabase = this.mDb;
        if (sQLiteDatabase != null) {
            sQLiteDatabase.setMaximumSize(j);
        }
    }
}
