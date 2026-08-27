package com.dcloud.android.downloader.db;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.dcloud.android.downloader.config.Config;
import com.dcloud.android.downloader.domain.DownloadInfo;
import com.dcloud.android.downloader.domain.DownloadThreadInfo;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.common.util.StringUtil;
import java.util.ArrayList;
import java.util.List;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public final class DefaultDownloadDBController implements DownloadDBController {
    private final Context context;
    private final DefaultDownloadHelper dbHelper;
    private final SQLiteDatabase readableDatabase;
    private final SQLiteDatabase writableDatabase;
    public static final String[] DOWNLOAD_INFO_COLUMNS = {"_id", "supportRanges", "createAt", "uri", "location", AbsoluteConst.XML_PATH, AbsoluteConst.JSON_KEY_SIZE, "progress", "status"};
    public static final String[] DOWNLOAD_THREAD_INFO_COLUMNS = {"_id", "threadId", "downloadInfoId", "uri", "start", "end", "progress"};
    public static final String SQL_UPDATE_DOWNLOAD_THREAD_INFO = StringUtil.format("REPLACE INTO %s (_id,threadId,downloadInfoId,uri,start,end,progress) VALUES(?,?,?,?,?,?,?);", DefaultDownloadHelper.TABLE_NAME_DOWNLOAD_THREAD_INFO);
    public static final String SQL_UPDATE_DOWNLOAD_INFO = StringUtil.format("REPLACE INTO %s (_id,supportRanges,createAt,uri,location,path,size,progress,status) VALUES(?,?,?,?,?,?,?,?,?);", DefaultDownloadHelper.TABLE_NAME_DOWNLOAD_INFO);
    public static final String SQL_UPDATE_DOWNLOADING_INFO_STATUS = StringUtil.format("UPDATE %s SET status=? WHERE status!=?;", DefaultDownloadHelper.TABLE_NAME_DOWNLOAD_INFO);

    public DefaultDownloadDBController(Context context, Config config) {
        this.context = context;
        DefaultDownloadHelper defaultDownloadHelper = new DefaultDownloadHelper(context, config);
        this.dbHelper = defaultDownloadHelper;
        this.writableDatabase = defaultDownloadHelper.getWritableDatabase();
        this.readableDatabase = defaultDownloadHelper.getReadableDatabase();
    }

    private void inflateDownloadInfo(Cursor cursor, DownloadInfo downloadInfo) {
        downloadInfo.setId(cursor.getInt(0));
        downloadInfo.setSupportRanges(cursor.getInt(1));
        downloadInfo.setCreateAt(cursor.getLong(2));
        downloadInfo.setUri(cursor.getString(3));
        downloadInfo.setLocation(cursor.getString(4));
        downloadInfo.setPath(cursor.getString(5));
        downloadInfo.setSize(cursor.getLong(6));
        downloadInfo.setProgress(cursor.getLong(7));
        downloadInfo.setStatus(cursor.getInt(8));
    }

    private void inflateDownloadThreadInfo(Cursor cursor, DownloadThreadInfo downloadThreadInfo) {
        downloadThreadInfo.setId(cursor.getInt(0));
        downloadThreadInfo.setThreadId(cursor.getInt(1));
        downloadThreadInfo.setDownloadInfoId(cursor.getInt(2));
        downloadThreadInfo.setUri(cursor.getString(3));
        downloadThreadInfo.setStart(cursor.getLong(4));
        downloadThreadInfo.setEnd(cursor.getLong(5));
        downloadThreadInfo.setProgress(cursor.getLong(6));
    }

    @Override // com.dcloud.android.downloader.db.DownloadDBController
    public void createOrUpdate(DownloadInfo downloadInfo) throws SQLException {
        this.writableDatabase.execSQL(SQL_UPDATE_DOWNLOAD_INFO, new Object[]{Integer.valueOf(downloadInfo.getId()), Integer.valueOf(downloadInfo.getSupportRanges()), Long.valueOf(downloadInfo.getCreateAt()), downloadInfo.getUri(), downloadInfo.getLocation(), downloadInfo.getPath(), Long.valueOf(downloadInfo.getSize()), Long.valueOf(downloadInfo.getProgress()), Integer.valueOf(downloadInfo.getStatus())});
    }

    @Override // com.dcloud.android.downloader.db.DownloadDBController
    public void delete(DownloadInfo downloadInfo) {
        this.writableDatabase.delete(DefaultDownloadHelper.TABLE_NAME_DOWNLOAD_INFO, "_id=?", new String[]{String.valueOf(downloadInfo.getId())});
        this.writableDatabase.delete(DefaultDownloadHelper.TABLE_NAME_DOWNLOAD_THREAD_INFO, "downloadInfoId=?", new String[]{String.valueOf(downloadInfo.getId())});
    }

    @Override // com.dcloud.android.downloader.db.DownloadDBController
    public List<DownloadInfo> findAllDownloaded() {
        Cursor cursorQuery = this.readableDatabase.query(DefaultDownloadHelper.TABLE_NAME_DOWNLOAD_INFO, DOWNLOAD_INFO_COLUMNS, "status=?", new String[]{String.valueOf(5)}, null, null, "createAt desc");
        ArrayList arrayList = new ArrayList();
        while (cursorQuery.moveToNext()) {
            DownloadInfo downloadInfo = new DownloadInfo(this.context);
            arrayList.add(downloadInfo);
            inflateDownloadInfo(cursorQuery, downloadInfo);
        }
        return arrayList;
    }

    @Override // com.dcloud.android.downloader.db.DownloadDBController
    public List<DownloadInfo> findAllDownloading() {
        Cursor cursorQuery = this.readableDatabase.query(DefaultDownloadHelper.TABLE_NAME_DOWNLOAD_INFO, DOWNLOAD_INFO_COLUMNS, "status!=?", new String[]{String.valueOf(5)}, null, null, "createAt desc");
        ArrayList arrayList = new ArrayList();
        while (cursorQuery.moveToNext()) {
            DownloadInfo downloadInfo = new DownloadInfo(this.context);
            arrayList.add(downloadInfo);
            inflateDownloadInfo(cursorQuery, downloadInfo);
            Cursor cursorQuery2 = this.readableDatabase.query(DefaultDownloadHelper.TABLE_NAME_DOWNLOAD_THREAD_INFO, DOWNLOAD_THREAD_INFO_COLUMNS, "downloadInfoId=?", new String[]{String.valueOf(downloadInfo.getId())}, null, null, null);
            ArrayList arrayList2 = new ArrayList();
            while (cursorQuery2.moveToNext()) {
                DownloadThreadInfo downloadThreadInfo = new DownloadThreadInfo();
                arrayList2.add(downloadThreadInfo);
                inflateDownloadThreadInfo(cursorQuery2, downloadThreadInfo);
            }
            downloadInfo.setDownloadThreadInfos(arrayList2);
        }
        return arrayList;
    }

    @Override // com.dcloud.android.downloader.db.DownloadDBController
    public DownloadInfo findDownloadedInfoById(int i) {
        Cursor cursorQuery = this.readableDatabase.query(DefaultDownloadHelper.TABLE_NAME_DOWNLOAD_INFO, DOWNLOAD_INFO_COLUMNS, "_id=?", new String[]{String.valueOf(i)}, null, null, "createAt desc");
        if (!cursorQuery.moveToNext()) {
            return null;
        }
        DownloadInfo downloadInfo = new DownloadInfo(this.context);
        inflateDownloadInfo(cursorQuery, downloadInfo);
        return downloadInfo;
    }

    @Override // com.dcloud.android.downloader.db.DownloadDBController
    public void pauseAllDownloading() throws SQLException {
        this.writableDatabase.execSQL(SQL_UPDATE_DOWNLOADING_INFO_STATUS, new Object[]{4, 5});
    }

    @Override // com.dcloud.android.downloader.db.DownloadDBController
    public void createOrUpdate(DownloadThreadInfo downloadThreadInfo) throws SQLException {
        this.writableDatabase.execSQL(SQL_UPDATE_DOWNLOAD_THREAD_INFO, new Object[]{Integer.valueOf(downloadThreadInfo.getId()), Integer.valueOf(downloadThreadInfo.getThreadId()), Integer.valueOf(downloadThreadInfo.getDownloadInfoId()), downloadThreadInfo.getUri(), Long.valueOf(downloadThreadInfo.getStart()), Long.valueOf(downloadThreadInfo.getEnd()), Long.valueOf(downloadThreadInfo.getProgress())});
    }

    @Override // com.dcloud.android.downloader.db.DownloadDBController
    public void delete(DownloadThreadInfo downloadThreadInfo) {
        this.writableDatabase.delete(DefaultDownloadHelper.TABLE_NAME_DOWNLOAD_THREAD_INFO, "id=?", new String[]{String.valueOf(downloadThreadInfo.getId())});
    }
}
