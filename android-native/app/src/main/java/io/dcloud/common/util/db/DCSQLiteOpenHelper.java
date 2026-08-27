package io.dcloud.common.util.db;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import io.dcloud.common.adapter.util.Logger;
import java.text.SimpleDateFormat;
import java.util.Locale;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class DCSQLiteOpenHelper extends SQLiteOpenHelper {
    static final String COLUMN_KEY = "key";
    static final String COLUMN_TIMESTAMP = "timestamp";
    static final String COLUMN_VALUE = "value";
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS default_wx_storage (key TEXT PRIMARY KEY,value TEXT NOT NULL,timestamp TEXT NOT NULL)";
    private static final String DATABASE_NAME = "DCStorage";
    private static final int DATABASE_VERSION = 1;
    private static final int SLEEP_TIME_MS = 30;
    static final String TABLE_STORAGE = "default_wx_storage";
    static final String TAG_STORAGE = "dc_storage";
    private static DCSQLiteOpenHelper mInstance;
    static SimpleDateFormat sDateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    private Context mContext;
    private SQLiteDatabase mDb;

    private DCSQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, 1);
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
                sQLiteDatabase.execSQL(CREATE_TABLE);
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

    public static DCSQLiteOpenHelper getSQLiteOpenHelper(Context context) {
        if (mInstance == null) {
            mInstance = new DCSQLiteOpenHelper(context);
        }
        return mInstance;
    }

    public synchronized void closeDatabase() {
        SQLiteDatabase sQLiteDatabase = this.mDb;
        if (sQLiteDatabase != null && sQLiteDatabase.isOpen()) {
            this.mDb.close();
            this.mDb = null;
        }
        mInstance = null;
    }

    synchronized void ensureDatabase(String str) {
        SQLiteDatabase sQLiteDatabase = this.mDb;
        if (sQLiteDatabase != null && sQLiteDatabase.isOpen()) {
            createTableIfNotExists(this.mDb, str);
            return;
        }
        for (int i = 0; i < 2; i++) {
            if (i <= 0) {
                this.mDb = getWritableDatabase();
                break;
                break;
            }
            try {
                try {
                    deleteDB();
                    this.mDb = getWritableDatabase();
                    break;
                } catch (SQLiteException e) {
                    e.printStackTrace();
                    try {
                        Thread.sleep(30L);
                    } catch (InterruptedException unused) {
                        Thread.currentThread().interrupt();
                    }
                }
            } catch (Throwable th) {
                this.mDb = null;
                Logger.d(TAG_STORAGE, "ensureDatabase failed, throwable = " + th.getMessage());
            }
        }
        if (this.mDb != null) {
            if (TextUtils.isEmpty(str)) {
                createTableIfNotExists(this.mDb);
            } else {
                createTableIfNotExists(this.mDb, str);
            }
        }
    }

    public SQLiteDatabase getDatabase() {
        ensureDatabase(null);
        return this.mDb;
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) throws SQLException {
        sQLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }

    public SQLiteDatabase getDatabase(String str) {
        ensureDatabase(str);
        return this.mDb;
    }

    private void createTableIfNotExists(SQLiteDatabase sQLiteDatabase, String str) {
        Cursor cursorRawQuery = null;
        try {
            try {
                cursorRawQuery = sQLiteDatabase.rawQuery("SELECT DISTINCT tbl_name FROM sqlite_master WHERE tbl_name = ?", new String[]{str});
                if (cursorRawQuery != null && cursorRawQuery.getCount() > 0) {
                    cursorRawQuery.close();
                    return;
                }
                sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + str + " (key TEXT PRIMARY KEY,value TEXT NOT NULL,timestamp TEXT NOT NULL)");
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
}
