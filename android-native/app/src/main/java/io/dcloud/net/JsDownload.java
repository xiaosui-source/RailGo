package io.dcloud.net;

import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.CookieManager;
import com.facebook.common.statfs.StatFsHelper;
import com.taobao.weex.el.parse.Operators;
import io.dcloud.common.DHInterface.IApp;
import io.dcloud.common.DHInterface.IReqListener;
import io.dcloud.common.DHInterface.IResponseListener;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.adapter.util.DeviceInfo;
import io.dcloud.common.adapter.util.Logger;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.common.constant.StringConst;
import io.dcloud.common.util.BaseInfo;
import io.dcloud.common.util.Deprecated_JSUtil;
import io.dcloud.common.util.IOUtil;
import io.dcloud.common.util.JSONUtil;
import io.dcloud.common.util.PdrUtil;
import io.dcloud.common.util.StringUtil;
import io.dcloud.common.util.ThreadPool;
import io.dcloud.common.util.net.DownloadMgr;
import io.dcloud.common.util.net.NetWork;
import io.dcloud.common.util.net.RequestData;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class JsDownload implements IReqListener, IResponseListener {
    public static final String DOWNLOAD_NAME = "_download_dcloud";
    static final int STATE_COMPLETED = 4;
    static final int STATE_CONNECTED = 2;
    static final int STATE_CONNECTING = 1;
    static final int STATE_INIT = 0;
    static final int STATE_PAUSE = 5;
    static final int STATE_RECEIVING = 3;
    static final int STATE_UNDEFINED = -1000;
    static final int STATE_UNKOWN = -1;
    public boolean mAbort;
    String mData;
    private boolean mDownloadComplete;
    private DownloadJSMgr mDownloadMgr;
    DownloadNetWork mDownloadNetWork;
    String mMethod;
    public boolean mPause;
    int mPriority;
    private ArrayList<IWebview> mRelWebviews;
    RequestData mRequestData;
    private long mRetryIntervalTime;
    public IWebview mWebview;
    private String sAppid;
    private String sharedPreferenceName;
    int mState = -1000;
    String mUUID = null;
    String mUrl = null;
    String mRealURL = null;
    boolean append = false;
    private long responseOffset = 0;
    JSONObject mOptions = null;
    String mFileName = "";
    private String mParentPath = null;
    private File mFile = null;
    RandomAccessFile mFileOs = null;
    long mFileSize = 0;
    long mTotalSize = 0;
    int mRetry = 3;
    String mConfigFilePath = null;
    private long preTime = 0;

    JsDownload(DownloadJSMgr downloadJSMgr, IWebview iWebview, JSONObject jSONObject) {
        this.mDownloadNetWork = null;
        this.mRequestData = null;
        this.mRelWebviews = null;
        this.mDownloadMgr = downloadJSMgr;
        this.mWebview = iWebview;
        ArrayList<IWebview> arrayList = new ArrayList<>();
        this.mRelWebviews = arrayList;
        arrayList.add(iWebview);
        parseJson(jSONObject);
        RequestData requestData = new RequestData(this.mUrl, this.mMethod);
        this.mRequestData = requestData;
        requestData.unTrustedCAType = iWebview.obtainApp().obtainConfigProperty(IApp.ConfigProperty.CONFIG_UNTRUSTEDCA);
        if (!jSONObject.isNull("timeout")) {
            this.mRequestData.mTimeout = jSONObject.optInt("timeout") * 1000;
        }
        this.mRequestData.addHeader(IWebview.USER_AGENT, iWebview.getWebviewProperty(IWebview.USER_AGENT));
        DownloadNetWork downloadNetWork = new DownloadNetWork(2, this.mRequestData, this, this);
        this.mDownloadNetWork = downloadNetWork;
        downloadNetWork.MAX_TIMES = this.mRetry;
        downloadNetWork.mPriority = this.mPriority;
        downloadNetWork.setRetryIntervalTime(this.mRetryIntervalTime);
        this.sAppid = this.mWebview.obtainFrameView().obtainApp().obtainAppId();
        this.sharedPreferenceName = this.sAppid + DOWNLOAD_NAME;
    }

    private void checkSpecialFile(String str) throws JSONException {
        if (TextUtils.isEmpty(str) || !BaseInfo.ISAMU) {
            return;
        }
        int length = str.length();
        String str2 = StringConst.POINT_APP_EN;
        if (((length - str.indexOf(str2)) - str2.length() == 0 || (length - str.indexOf(".wgt")) - 4 == 0 || (length - str.indexOf(".wgtu")) - 5 == 0) && this.mWebview != null) {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("type", AbsoluteConst.SPNAME_DOWNLOAD);
                jSONObject.put("file", this.mParentPath + this.mFileName);
                jSONObject.put("url", this.mUrl);
                jSONObject.put("appid", this.mWebview.obtainApp().obtainOriginalAppId());
                jSONObject.put("version", this.mWebview.obtainApp().obtainAppVersionName());
                Log.i(AbsoluteConst.HBUILDER_TAG, jSONObject.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private File createDownloadFile(boolean z) throws IOException {
        try {
            String realPath = getRealPath(this.mFileName);
            if (realPath == null) {
                return null;
            }
            File file = new File(realPath);
            if (z && file.exists()) {
                return file;
            }
            String str = this.mFileName;
            int iLastIndexOf = str.lastIndexOf(Operators.DOT_STR);
            String strSubstring = iLastIndexOf < 0 ? str : str.substring(0, iLastIndexOf);
            String strSubstring2 = iLastIndexOf < 0 ? "" : str.substring(iLastIndexOf);
            int i = 1;
            while (file.exists()) {
                str = strSubstring + Operators.BRACKET_START_STR + i + Operators.BRACKET_END_STR + strSubstring2;
                i++;
                file = new File(getRealPath(str));
            }
            this.mFileName = str;
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            file.createNewFile();
            return file;
        } catch (Exception e) {
            String str2 = this.mFileName;
            if (str2 == null || !str2.toLowerCase(Locale.ENGLISH).startsWith(DeviceInfo.FILE_PROTOCOL)) {
                e.printStackTrace();
                return null;
            }
            this.mFileName = this.mFileName.substring(7);
            File fileCreateDownloadFile = createDownloadFile(this.append);
            if (fileCreateDownloadFile != null) {
                this.mFileName = fileCreateDownloadFile.getAbsolutePath();
            }
            return fileCreateDownloadFile;
        }
    }

    private void initPath(String str) {
        IApp iAppObtainApp = this.mWebview.obtainFrameView().obtainApp();
        if (PdrUtil.isEmpty(str)) {
            this.mParentPath = new File(BaseInfo.sDownloadFullPath).getParent() + "/";
            this.mFileName = "_downloads/";
            return;
        }
        this.mFileName = str;
        if (startsWith(str, BaseInfo.REL_PRIVATE_DOC_DIR, true)) {
            this.mParentPath = new File(iAppObtainApp.obtainAppDocPath()).getParent() + "/";
            return;
        }
        if (startsWith(str, BaseInfo.REL_PUBLIC_DOCUMENTS_DIR, true)) {
            this.mParentPath = new File(BaseInfo.sDocumentFullPath).getParent() + "/";
            return;
        }
        if (startsWith(str, BaseInfo.REL_PUBLIC_DOWNLOADS_DIR, true)) {
            this.mParentPath = new File(BaseInfo.sDownloadFullPath).getParent() + "/";
            return;
        }
        this.mParentPath = new File(BaseInfo.sDownloadFullPath).getParent() + "/";
    }

    private boolean justDirPath() {
        return TextUtils.isEmpty(this.mFileName) || this.mFileName.endsWith("/");
    }

    private void onStateChanged(int i) throws IOException {
        if (i == 3 || i == 5) {
            this.mState = i;
        }
        boolean z = i == 3 && SystemClock.elapsedRealtime() - this.preTime >= 10;
        if (z || i != 3 || this.mTotalSize - this.mFileSize <= 5120 || this.mAbort) {
            if (z) {
                this.preTime = SystemClock.elapsedRealtime();
            }
            String json = toJSON();
            int size = this.mRelWebviews.size();
            for (int i2 = 0; i2 < size; i2++) {
                Deprecated_JSUtil.excDownloadCallBack(this.mRelWebviews.get(i2), json, this.mUUID);
            }
        }
        if (this.mAbort) {
            return;
        }
        saveDownloadState();
    }

    private void parseJson(JSONObject jSONObject) {
        this.mOptions = jSONObject.optJSONObject("options");
        this.mUrl = JSONUtil.getString(jSONObject, "url");
        String string = JSONUtil.getString(jSONObject, AbsoluteConst.JSON_KEY_REALURL);
        if (string != null && !string.equalsIgnoreCase("null") && !string.equalsIgnoreCase(this.mUrl)) {
            this.mUrl = string;
        }
        String string2 = JSONUtil.getString(jSONObject, "id");
        this.mUUID = string2;
        if (TextUtils.isEmpty(string2)) {
            this.mUUID = JSONUtil.getString(jSONObject, "uuid");
        }
        this.mMethod = JSONUtil.getString(jSONObject, "method");
        this.mPriority = JSONUtil.getInt(jSONObject, "priority");
        this.mRetry = JSONUtil.getInt(jSONObject, AbsoluteConst.JSON_KEY_RETRY);
        this.mFileSize = JSONUtil.getInt(jSONObject, AbsoluteConst.JSON_KEY_DOWNLOADEDSIZE);
        this.mTotalSize = JSONUtil.getInt(jSONObject, AbsoluteConst.JSON_KEY_TOTALSIZE);
        String string3 = JSONUtil.getString(jSONObject, "state");
        if (!PdrUtil.isEmpty(string3)) {
            try {
                this.mState = Integer.parseInt(string3);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        this.mConfigFilePath = BaseInfo.sDownloadFullPath + this.mUUID + ".download";
        File file = new File(this.mConfigFilePath);
        try {
            if (file.exists()) {
                String string4 = IOUtil.toString(new FileInputStream(file));
                string4.replace("\n", "");
                String[] strArrSplit = string4.split(Operators.SUB);
                this.mTotalSize = Long.parseLong(strArrSplit[0]);
                this.mState = Integer.parseInt(strArrSplit[2]);
                File file2 = new File(strArrSplit[3].replace("\n", ""));
                this.mFile = file2;
                if (file2.exists()) {
                    this.mFileSize = this.mFile.length();
                } else {
                    this.mFileSize = 0L;
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        String string5 = JSONUtil.getString(jSONObject, AbsoluteConst.JSON_KEY_FILENAME);
        if (TextUtils.isEmpty(string5)) {
            string5 = JSONUtil.getString(this.mOptions, AbsoluteConst.JSON_KEY_FILENAME);
        }
        initPath(string5);
        this.mData = JSONUtil.getString(jSONObject, "data");
        this.mRetryIntervalTime = JSONUtil.getLong(jSONObject, AbsoluteConst.JSON_KEY_RETRY_INTERVAL_TIME) * 1000;
    }

    private void saveDownloadState() throws IOException {
        if (this.mFileSize < this.mTotalSize) {
            try {
                File file = new File(this.mConfigFilePath);
                if (file.exists()) {
                    FileOutputStream fileOutputStream = new FileOutputStream(file, false);
                    fileOutputStream.write((this.mTotalSize + Operators.SUB + this.mFileSize + Operators.SUB + this.mState + Operators.SUB + this.mFile.getAbsolutePath()).getBytes());
                    fileOutputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean startsWith(String str, String str2, boolean z) {
        if (str == null || !str.startsWith(str2)) {
            return false;
        }
        if (z) {
            String strSubstring = str.substring(str2.length());
            if (strSubstring.length() != 0 && (strSubstring.length() <= 1 || strSubstring.charAt(0) != '/')) {
                return false;
            }
        }
        return true;
    }

    public void abort() {
        try {
            this.mAbort = true;
            ThreadPool.self().addThreadTask(new Runnable() { // from class: io.dcloud.net.JsDownload.1
                @Override // java.lang.Runnable
                public void run() throws IOException {
                    JsDownload.this.mDownloadNetWork.cancelWork();
                    DownloadMgr.getDownloadMgr().removeTask(JsDownload.this.mDownloadNetWork);
                    JsDownload.this.deleteDownloadData();
                    if (!PdrUtil.isEmpty(JsDownload.this.mFileOs)) {
                        try {
                            JsDownload.this.mFileOs.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (JsDownload.this.mFile != null) {
                        JsDownload.this.mFile.delete();
                    }
                    JsDownload jsDownload = JsDownload.this;
                    jsDownload.mWebview = null;
                    jsDownload.mRelWebviews.clear();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addRelWebview(IWebview iWebview) {
        if (this.mRelWebviews.contains(iWebview)) {
            return;
        }
        this.mRelWebviews.add(iWebview);
    }

    public void deleteDownloadData() {
        this.mDownloadMgr.deleteDownloadTaskInfo(this.sAppid, this.mUUID);
        new File(this.mConfigFilePath).delete();
    }

    public String getRealPath(String str) {
        if (str != null && str.startsWith("/") && !str.toLowerCase(Locale.ENGLISH).startsWith("/sdcard")) {
            String strConvert2AbsFullPath = this.mWebview.obtainApp().convert2AbsFullPath(str.substring(1));
            if (strConvert2AbsFullPath == null || !strConvert2AbsFullPath.contains("/www/")) {
                return strConvert2AbsFullPath;
            }
            return strConvert2AbsFullPath.replace("/www/", "/" + BaseInfo.REAL_PUBLIC_DOWNLOADS_DIR);
        }
        if (str != null && str.toLowerCase(Locale.ENGLISH).startsWith("/sdcard")) {
            return str;
        }
        String strConvert2AbsFullPath2 = this.mWebview.obtainApp().convert2AbsFullPath(str);
        if (strConvert2AbsFullPath2 == null || !strConvert2AbsFullPath2.contains("/www/")) {
            return strConvert2AbsFullPath2;
        }
        return strConvert2AbsFullPath2.replace("/www/", "/" + BaseInfo.REAL_PUBLIC_DOWNLOADS_DIR);
    }

    @Override // io.dcloud.common.DHInterface.IReqListener
    public void onNetStateChanged(IReqListener.NetState netState, boolean z) throws JSONException, IOException {
        String string;
        int iLastIndexOf;
        if (this.mPause) {
            return;
        }
        if (netState == IReqListener.NetState.NET_INIT) {
            this.mState = 0;
            this.mDownloadComplete = false;
        } else if (netState == IReqListener.NetState.NET_CONNECTED) {
            this.mState = 2;
        } else if (netState == IReqListener.NetState.NET_HANDLE_END) {
            this.mState = 4;
            Logger.d("----NetState.NET_HANDLE_END-----");
            DownloadMgr.getDownloadMgr().removeTask(this.mDownloadNetWork);
            deleteDownloadData();
            saveInDatabase();
            this.mDownloadComplete = true;
            checkSpecialFile(this.mFileName);
        } else if (netState == IReqListener.NetState.NET_ERROR) {
            this.mState = 4;
            this.mDownloadNetWork.mStatus = StatFsHelper.DEFAULT_DISK_YELLOW_LEVEL_IN_MB;
            Logger.d("----NetState.NET_ERROR-----");
            DownloadMgr.getDownloadMgr().removeTask(this.mDownloadNetWork);
            if (this.mDownloadComplete) {
                return;
            }
        } else if (netState == IReqListener.NetState.NET_TIMEOUT) {
            this.mState = 4;
            this.mDownloadNetWork.mStatus = 0;
            Logger.d("----NetState.NET_TIMEOUT-----");
            DownloadMgr.getDownloadMgr().removeTask(this.mDownloadNetWork);
            if (this.mDownloadComplete) {
                return;
            }
        } else if (netState == IReqListener.NetState.NET_REQUEST_BEGIN) {
            try {
                if (this.mFileSize > 0) {
                    this.mDownloadNetWork.mUrlConn.setRequestProperty("Range", "bytes=" + String.valueOf(this.mFileSize) + Operators.SUB);
                }
                this.mDownloadNetWork.mUrlConn.setRequestMethod(this.mMethod);
                if (this.mMethod.equals("POST")) {
                    this.mDownloadNetWork.mUrlConn.setDoOutput(true);
                    this.mDownloadNetWork.mUrlConn.getOutputStream().write(this.mData.getBytes("utf8"));
                    this.mDownloadNetWork.mUrlConn.getOutputStream().flush();
                    this.mDownloadNetWork.mUrlConn.getOutputStream().close();
                    this.mDownloadNetWork.mUrlConn.setChunkedStreamingMode(0);
                }
                this.mDownloadNetWork.mUrlConn.setConnectTimeout(this.mRequestData.mTimeout);
                this.mDownloadNetWork.mUrlConn.setReadTimeout(this.mRequestData.mTimeout);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (netState == IReqListener.NetState.NET_HANDLE_BEGIN) {
            String headerField = this.mDownloadNetWork.mUrlConn.getHeaderField("Content-Length");
            String headerField2 = this.mDownloadNetWork.mUrlConn.getHeaderField("Content-Range");
            String headerField3 = this.mDownloadNetWork.mUrlConn.getHeaderField(NetWork.CONTENT_TYPE);
            String headerField4 = this.mDownloadNetWork.mUrlConn.getHeaderField(IWebview.SET_COOKIE);
            if (!PdrUtil.isEmpty(headerField4)) {
                CookieManager.getInstance().setCookie(this.mRequestData.getUrl(), headerField4);
            }
            if (headerField2 == null) {
                this.mTotalSize = PdrUtil.parseLong(headerField, 0L);
                this.mFileSize = 0L;
                this.responseOffset = 0L;
                File file = this.mFile;
                if (file != null && file.exists()) {
                    this.mFile.delete();
                    new File(this.mConfigFilePath).delete();
                }
            } else {
                this.append = true;
                try {
                    MessageFormat messageFormat = new MessageFormat("bytes {0,number}-{1,number}");
                    messageFormat.setLocale(Locale.US);
                    long jLongValue = ((Number) messageFormat.parse(headerField2)[0]).longValue();
                    this.responseOffset = jLongValue;
                    if (jLongValue < 0) {
                        this.responseOffset = 0L;
                    }
                } catch (Exception unused) {
                    this.responseOffset = 0L;
                }
            }
            if (justDirPath()) {
                String headerField5 = this.mDownloadNetWork.mUrlConn.getHeaderField("content-disposition");
                try {
                    if (PdrUtil.isEmpty(headerField5) && (iLastIndexOf = (string = this.mDownloadNetWork.mUrlConn.getURL().getFile().toString()).lastIndexOf(47)) >= 0) {
                        String strSubstring = string.substring(iLastIndexOf + 1);
                        if (strSubstring.indexOf(46) >= 0) {
                            if (strSubstring.contains(Operators.CONDITION_IF_STRING)) {
                                strSubstring = strSubstring.substring(0, strSubstring.indexOf(Operators.CONDITION_IF_STRING));
                            }
                            this.mFileName += strSubstring;
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                if (justDirPath()) {
                    this.mFileName += PdrUtil.getDownloadFilename(headerField5, headerField3, this.mUrl);
                }
            }
            if (this.mDownloadNetWork.isStop) {
                return;
            }
            try {
                this.mFile = createDownloadFile(this.append);
                RandomAccessFile randomAccessFile = new RandomAccessFile(this.mFile, "rw");
                this.mFileOs = randomAccessFile;
                randomAccessFile.seek(this.responseOffset);
                return;
            } catch (IOException e3) {
                e3.printStackTrace();
                saveInDatabase();
                return;
            }
        }
        onStateChanged(this.mState);
    }

    @Override // io.dcloud.common.DHInterface.IReqListener
    public int onReceiving(InputStream inputStream) throws Exception {
        byte[] bArr = new byte[10240];
        if (inputStream != null) {
            this.mDownloadNetWork.mTimes = 1;
            boolean z = false;
            while (true) {
                int i = inputStream.read(bArr);
                if (i == -1) {
                    this.mFileOs.close();
                    break;
                }
                if (this.mPause) {
                    onStateChanged(5);
                    return -1;
                }
                this.mFileOs.write(bArr, 0, i);
                this.mFileSize += i;
                onStateChanged(3);
                if (!z) {
                    this.mRealURL = this.mDownloadNetWork.mUrlConn.getURL().toString();
                    saveInDatabase();
                    z = true;
                }
            }
        }
        if (this.mFileSize < this.mTotalSize) {
            throw new RuntimeException();
        }
        new File(this.mConfigFilePath).delete();
        return -1;
    }

    @Override // io.dcloud.common.DHInterface.IResponseListener
    public void onResponseState(int i, String str) {
    }

    @Override // io.dcloud.common.DHInterface.IReqListener
    public void onResponsing(InputStream inputStream) {
        String json = toJSON();
        this.mState = 1;
        int size = this.mRelWebviews.size();
        for (int i = 0; i < size; i++) {
            Deprecated_JSUtil.excDownloadCallBack(this.mRelWebviews.get(i), json, this.mUUID);
        }
    }

    public void saveInDatabase() {
        this.mDownloadMgr.saveDownloadTaskInfo(this.sAppid, this.mUUID, toSaveJSON());
    }

    public void setRequestHeader(String str, String str2) {
        this.mRequestData.addHeader(str, str2);
    }

    public void start() {
        this.mDownloadNetWork.mTimes = 1;
        this.mPause = false;
        DownloadMgr.getDownloadMgr().addQuestTask(this.mDownloadNetWork);
        saveInDatabase();
    }

    String toSaveJSON() {
        return StringUtil.format("{url: '%s',uuid: '%s',method: '%s',priority: %d,timeout:%d,retry:%d,filename:'%s',downloadedSize:%d,totalSize:%d,state: %d,options:%s,RealURL:'%s'}", this.mUrl, this.mUUID, this.mMethod, Integer.valueOf(this.mPriority), Integer.valueOf(this.mRequestData.mTimeout), Integer.valueOf(this.mRetry), this.mFileName, Long.valueOf(this.mFileSize), Long.valueOf(this.mTotalSize), Integer.valueOf(this.mState), this.mOptions.toString(), this.mRealURL);
    }

    public String toJSON() {
        return this.mState == -1000 ? StringUtil.format("{status: %d,filename: '%s',uuid: '%s',downloadedSize:%d,totalSize:%d,headers:%s}", Integer.valueOf(this.mDownloadNetWork.mStatus), this.mFileName, this.mUUID, Long.valueOf(this.mFileSize), Long.valueOf(this.mTotalSize), this.mDownloadNetWork.getResponseHeaders()) : StringUtil.format("{status: %d,state: %d,filename: '%s',uuid: '%s',downloadedSize:%d,totalSize:%d,headers:%s}", Integer.valueOf(this.mDownloadNetWork.mStatus), Integer.valueOf(this.mState), this.mFileName, this.mUUID, Long.valueOf(this.mFileSize), Long.valueOf(this.mTotalSize), this.mDownloadNetWork.getResponseHeaders());
    }
}
