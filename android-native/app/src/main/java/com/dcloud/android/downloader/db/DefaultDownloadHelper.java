package com.dcloud.android.downloader.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.dcloud.android.downloader.config.Config;
import io.dcloud.common.util.StringUtil;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class DefaultDownloadHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    public static final String TABLE_NAME_DOWNLOAD_INFO = "download_info";
    private static final String SQL_CREATE_DOWNLOAD_TABLE = StringUtil.format("CREATE TABLE %s (_id integer PRIMARY KEY NOT NULL,supportRanges integer NOT NULL,createAt long NOT NULL,uri varchar(255) NOT NULL,location varchar(255),path varchar(255) NOT NULL,size long NOT NULL, progress long NOT NULL,status integer NOT NULL, value1 varchar(255), value2 varchar(255));", TABLE_NAME_DOWNLOAD_INFO);
    public static final String TABLE_NAME_DOWNLOAD_THREAD_INFO = "download_thread_info";
    private static final String SQL_CREATE_DOWNLOAD_THREAD_TABLE = StringUtil.format("CREATE TABLE %s (_id integer PRIMARY KEY NOT NULL,threadId integer NOT NULL,downloadInfoId integer NOT NULL,uri varchar(255) NOT NULL,start long NOT NULL,end long NOT NULL,progress long NOT NULL);", TABLE_NAME_DOWNLOAD_THREAD_INFO);

    public DefaultDownloadHelper(Context context, Config config) {
        super(context, config.getDatabaseName(), (SQLiteDatabase.CursorFactory) null, config.getDatabaseVersion());
    }

    private void createTable(SQLiteDatabase sQLiteDatabase) throws SQLException {
        sQLiteDatabase.execSQL(SQL_CREATE_DOWNLOAD_TABLE);
        sQLiteDatabase.execSQL(SQL_CREATE_DOWNLOAD_THREAD_TABLE);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) throws SQLException {
        createTable(sQLiteDatabase);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }
}
