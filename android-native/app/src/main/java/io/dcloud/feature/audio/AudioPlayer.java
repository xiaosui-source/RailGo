package io.dcloud.feature.audio;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.PlaybackParams;
import android.os.Build;
import com.taobao.weex.common.Constants;
import io.dcloud.base.R;
import io.dcloud.common.DHInterface.IApp;
import io.dcloud.common.DHInterface.IEventCallback;
import io.dcloud.common.DHInterface.ISysEventListener;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.common.constant.DOMException;
import io.dcloud.common.util.Deprecated_JSUtil;
import io.dcloud.common.util.JSONUtil;
import io.dcloud.common.util.JSUtil;
import io.dcloud.common.util.PdrUtil;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
class AudioPlayer extends AbsAudio implements ISysEventListener, IEventCallback {
    private IApp _app;
    private AudioManager mAudioMgr;
    String mFunId;
    private IWebview mWebview;
    private JSONObject params;
    private int bufferPercent = 0;
    private int startTime = Integer.MIN_VALUE;
    private String mSrcPath = "";
    private float volume = 1.0f;
    private boolean autoplay = false;
    private boolean isCanMix = false;
    private boolean needPause = false;
    private AudioManager.OnAudioFocusChangeListener mAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() { // from class: io.dcloud.feature.audio.AudioPlayer.1
        @Override // android.media.AudioManager.OnAudioFocusChangeListener
        public void onAudioFocusChange(int i) throws IllegalStateException, JSONException, IOException, SecurityException, IllegalArgumentException {
            if ((i == -1 || i == -2 || i == -3) && !AudioPlayer.this.needPause) {
                AudioPlayer.this.pause();
            }
        }
    };
    private boolean isPrepared = false;
    private boolean isPlay = false;
    private boolean isCanplay = false;
    private boolean isStoped = false;
    private MediaPlayer mMediaPlayer = new MediaPlayer();
    private Map<String, String> events = new HashMap();

    private AudioPlayer(JSONObject jSONObject, IWebview iWebview) throws IllegalStateException, JSONException, IOException, SecurityException, IllegalArgumentException {
        this.params = jSONObject;
        this.mWebview = iWebview;
        addListener();
        this._app = iWebview.obtainFrameView().obtainApp();
        iWebview.obtainFrameView().addFrameViewListener(this);
        this._app.registerSysEventListener(this, ISysEventListener.SysEventType.onStop);
        setStyle(this.params);
    }

    private void addListener() {
        this.mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() { // from class: io.dcloud.feature.audio.AudioPlayer.2
            @Override // android.media.MediaPlayer.OnPreparedListener
            public void onPrepared(MediaPlayer mediaPlayer) throws IllegalStateException, JSONException, IOException, SecurityException, IllegalArgumentException {
                AudioPlayer.this.execEvents("canplay", "");
            }
        });
        this.mMediaPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() { // from class: io.dcloud.feature.audio.AudioPlayer.3
            @Override // android.media.MediaPlayer.OnSeekCompleteListener
            public void onSeekComplete(MediaPlayer mediaPlayer) throws IllegalStateException, JSONException, IOException, SecurityException, IllegalArgumentException {
                AudioPlayer.this.execEvents("seeked", "");
            }
        });
        this.mMediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() { // from class: io.dcloud.feature.audio.AudioPlayer.4
            @Override // android.media.MediaPlayer.OnBufferingUpdateListener
            public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
                AudioPlayer.this.bufferPercent = i;
            }
        });
        this.mMediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() { // from class: io.dcloud.feature.audio.AudioPlayer.5
            @Override // android.media.MediaPlayer.OnInfoListener
            public boolean onInfo(MediaPlayer mediaPlayer, int i, int i2) throws IllegalStateException, JSONException, IOException, SecurityException, IllegalArgumentException {
                if (i == 701) {
                    AudioPlayer.this.execEvents(IApp.ConfigProperty.CONFIG_WAITING, "");
                    return false;
                }
                if (i != 702 || !mediaPlayer.isPlaying()) {
                    return false;
                }
                AudioPlayer.this.execEvents(Constants.Value.PLAY, "");
                return false;
            }
        });
        this.mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() { // from class: io.dcloud.feature.audio.AudioPlayer.6
            @Override // android.media.MediaPlayer.OnErrorListener
            public boolean onError(MediaPlayer mediaPlayer, int i, int i2) throws IllegalStateException, JSONException, IOException, SecurityException, IllegalArgumentException {
                String string;
                int i3;
                if (i == 1) {
                    string = DOMException.MSG_UNKNOWN_ERROR;
                    i3 = -99;
                } else if (i != 100) {
                    i3 = 0;
                    string = null;
                } else {
                    string = AudioPlayer.this.mWebview.getContext().getString(R.string.dcloud_audio_abnormal_rebuild);
                    i3 = 1303;
                }
                if (i2 == -1010) {
                    string = DOMException.MSG_NOT_SUPPORT;
                    i3 = -3;
                } else if (i2 == -1007) {
                    string = DOMException.MSG_AUDIO_ERROR_MALFORMED;
                    i3 = DOMException.CODE_AUDIO_ERROR_MALFORMED;
                } else if (i2 == -1004) {
                    i3 = -5;
                    string = DOMException.MSG_IO_ERROR;
                } else if (i2 == -110) {
                    string = DOMException.MSG_AUDIO_ERROR_TIMED_OUT;
                    i3 = DOMException.CODE_AUDIO_ERROR_TIMED_OUT;
                }
                if (i3 != 0) {
                    AudioPlayer.this.failCallback(i3, string);
                    AudioPlayer.this.execEvents("error", DOMException.toJSON(i3, string));
                }
                return true;
            }
        });
        this.mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() { // from class: io.dcloud.feature.audio.AudioPlayer.7
            @Override // android.media.MediaPlayer.OnCompletionListener
            public void onCompletion(MediaPlayer mediaPlayer) throws IllegalStateException, JSONException, IOException, SecurityException, IllegalArgumentException {
                AudioPlayer.this.execEvents("ended", "");
            }
        });
    }

    static AudioPlayer createAudioPlayer(JSONObject jSONObject, IWebview iWebview) {
        return new AudioPlayer(jSONObject, iWebview);
    }

    private void requestAudioFocus() {
        if (this.mAudioMgr == null) {
            this.mAudioMgr = (AudioManager) this.mWebview.getActivity().getSystemService("audio");
        }
        AudioManager audioManager = this.mAudioMgr;
        if (audioManager != null) {
            audioManager.requestAudioFocus(this.mAudioFocusChangeListener, 3, 1);
        }
    }

    private void setSpeed() throws NumberFormatException {
        MediaPlayer mediaPlayer;
        JSONObject jSONObject = this.params;
        if (jSONObject == null) {
            return;
        }
        try {
            float f = Float.parseFloat(jSONObject.optString("playbackRate"));
            if (f <= 0.0f || (mediaPlayer = this.mMediaPlayer) == null || Build.VERSION.SDK_INT < 23) {
                return;
            }
            PlaybackParams playbackParams = mediaPlayer.getPlaybackParams();
            playbackParams.setSpeed(f);
            this.mMediaPlayer.setPlaybackParams(playbackParams);
        } catch (Exception unused) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x006a  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x006d A[Catch: Exception -> 0x00cd, TRY_LEAVE, TryCatch #0 {Exception -> 0x00cd, blocks: (B:3:0x0004, B:5:0x000d, B:33:0x00c5, B:6:0x001e, B:8:0x0024, B:10:0x0044, B:12:0x004a, B:14:0x0055, B:16:0x005d, B:19:0x006d, B:22:0x0075, B:26:0x0084, B:28:0x008a, B:23:0x007a, B:25:0x0080, B:32:0x00bb, B:30:0x00b1), top: B:38:0x0004 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void setSrc(java.lang.String r11) throws java.lang.IllegalStateException, java.io.IOException, java.lang.SecurityException, java.lang.IllegalArgumentException {
        /*
            r10 = this;
            java.lang.String r0 = "android_asset/"
            java.lang.String r1 = "/android_asset/"
            java.lang.String r2 = "content://"
            boolean r2 = r11.startsWith(r2)     // Catch: java.lang.Exception -> Lcd
            r3 = 0
            if (r2 == 0) goto L1e
            android.net.Uri r11 = android.net.Uri.parse(r11)     // Catch: java.lang.Exception -> Lcd
            android.media.MediaPlayer r0 = r10.mMediaPlayer     // Catch: java.lang.Exception -> Lcd
            io.dcloud.common.DHInterface.IWebview r1 = r10.mWebview     // Catch: java.lang.Exception -> Lcd
            android.app.Activity r1 = r1.getActivity()     // Catch: java.lang.Exception -> Lcd
            r0.setDataSource(r1, r11)     // Catch: java.lang.Exception -> Lcd
            goto Lc5
        L1e:
            boolean r2 = io.dcloud.common.util.PdrUtil.isNetPath(r11)     // Catch: java.lang.Exception -> Lcd
            if (r2 != 0) goto Lb1
            io.dcloud.common.DHInterface.IApp r2 = r10._app     // Catch: java.lang.Exception -> Lcd
            java.lang.String r11 = r2.checkPrivateDirAndCopy2Temp(r11)     // Catch: java.lang.Exception -> Lcd
            io.dcloud.common.DHInterface.IApp r2 = r10._app     // Catch: java.lang.Exception -> Lcd
            io.dcloud.common.DHInterface.IWebview r4 = r10.mWebview     // Catch: java.lang.Exception -> Lcd
            java.lang.String r4 = r4.obtainFullUrl()     // Catch: java.lang.Exception -> Lcd
            java.lang.String r11 = r2.convert2AbsFullPath(r4, r11)     // Catch: java.lang.Exception -> Lcd
            io.dcloud.application.DCLoudApplicationImpl r2 = io.dcloud.application.DCLoudApplicationImpl.self()     // Catch: java.lang.Exception -> Lcd
            android.content.Context r2 = r2.getContext()     // Catch: java.lang.Exception -> Lcd
            boolean r4 = io.dcloud.common.util.FileUtil.needMediaStoreOpenFile(r2)     // Catch: java.lang.Exception -> Lcd
            if (r4 == 0) goto L6a
            boolean r4 = io.dcloud.common.util.FileUtil.checkPrivatePath(r2, r11)     // Catch: java.lang.Exception -> Lcd
            if (r4 != 0) goto L6a
            java.io.File r4 = new java.io.File     // Catch: java.lang.Exception -> Lcd
            r4.<init>(r11)     // Catch: java.lang.Exception -> Lcd
            boolean r5 = r4.exists()     // Catch: java.lang.Exception -> Lcd
            if (r5 == 0) goto L6a
            android.net.Uri r5 = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI     // Catch: java.lang.Exception -> Lcd
            android.net.Uri r2 = io.dcloud.common.util.FileUtil.getFileUri(r2, r4, r5)     // Catch: java.lang.Exception -> Lcd
            if (r2 == 0) goto L6a
            android.media.MediaPlayer r4 = r10.mMediaPlayer     // Catch: java.lang.Exception -> Lcd
            io.dcloud.common.DHInterface.IWebview r5 = r10.mWebview     // Catch: java.lang.Exception -> Lcd
            android.app.Activity r5 = r5.getActivity()     // Catch: java.lang.Exception -> Lcd
            r4.setDataSource(r5, r2)     // Catch: java.lang.Exception -> Lcd
            r2 = 1
            goto L6b
        L6a:
            r2 = 0
        L6b:
            if (r2 != 0) goto Lb9
            boolean r4 = r11.startsWith(r1)     // Catch: java.lang.Exception -> Lcd
            java.lang.String r5 = ""
            if (r4 == 0) goto L7a
            java.lang.String r11 = r11.replace(r1, r5)     // Catch: java.lang.Exception -> Lcd
            goto L84
        L7a:
            boolean r1 = r11.startsWith(r0)     // Catch: java.lang.Exception -> Lcd
            if (r1 == 0) goto L84
            java.lang.String r11 = r11.replace(r0, r5)     // Catch: java.lang.Exception -> Lcd
        L84:
            boolean r0 = io.dcloud.common.util.PdrUtil.isDeviceRootDir(r11)     // Catch: java.lang.Exception -> Lcd
            if (r0 != 0) goto Lb9
            io.dcloud.common.DHInterface.IWebview r0 = r10.mWebview     // Catch: java.lang.Exception -> Lcd
            android.app.Activity r0 = r0.getActivity()     // Catch: java.lang.Exception -> Lcd
            android.content.res.AssetManager r0 = r0.getAssets()     // Catch: java.lang.Exception -> Lcd
            android.content.res.AssetFileDescriptor r11 = r0.openFd(r11)     // Catch: java.lang.Exception -> Lcd
            android.media.MediaPlayer r4 = r10.mMediaPlayer     // Catch: java.lang.Exception -> Lcd
            java.io.FileDescriptor r5 = r11.getFileDescriptor()     // Catch: java.lang.Exception -> Lcd
            long r6 = r11.getStartOffset()     // Catch: java.lang.Exception -> Lcd
            long r8 = r11.getLength()     // Catch: java.lang.Exception -> Lcd
            r4.setDataSource(r5, r6, r8)     // Catch: java.lang.Exception -> Lcd
            r10.isCanplay = r3     // Catch: java.lang.Exception -> Lcd
            android.media.MediaPlayer r11 = r10.mMediaPlayer     // Catch: java.lang.Exception -> Lcd
            r11.prepareAsync()     // Catch: java.lang.Exception -> Lcd
            return
        Lb1:
            java.lang.String r0 = "utf-8"
            java.lang.String r11 = java.net.URLDecoder.decode(r11, r0)     // Catch: java.lang.Exception -> Lcd
            r2 = 0
        Lb9:
            if (r2 != 0) goto Lc5
            android.media.MediaPlayer r0 = r10.mMediaPlayer     // Catch: java.lang.Exception -> Lcd
            r0.reset()     // Catch: java.lang.Exception -> Lcd
            android.media.MediaPlayer r0 = r10.mMediaPlayer     // Catch: java.lang.Exception -> Lcd
            r0.setDataSource(r11)     // Catch: java.lang.Exception -> Lcd
        Lc5:
            r10.isCanplay = r3     // Catch: java.lang.Exception -> Lcd
            android.media.MediaPlayer r11 = r10.mMediaPlayer     // Catch: java.lang.Exception -> Lcd
            r11.prepareAsync()     // Catch: java.lang.Exception -> Lcd
            return
        Lcd:
            r0 = move-exception
            r11 = r0
            r10.stop()
            java.lang.String r0 = r11.getMessage()
            r1 = -5
            r10.failCallback(r1, r0)
            java.lang.String r11 = r11.getMessage()
            java.lang.String r11 = io.dcloud.common.constant.DOMException.toJSON(r1, r11)
            java.lang.String r0 = "error"
            r10.execEvents(r0, r11)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.feature.audio.AudioPlayer.setSrc(java.lang.String):void");
    }

    private void startPlay() throws IllegalStateException, NumberFormatException {
        requestAudioFocus();
        this.isPrepared = true;
        this.mMediaPlayer.start();
        setSpeed();
        execEvents(Constants.Value.PLAY, "");
        this.isPlay = false;
    }

    private void successCallback() {
        Deprecated_JSUtil.excCallbackSuccess(this.mWebview, this.mFunId, "");
    }

    void addEventListener(String str, String str2) {
        this.events.put(str, str2);
    }

    void destory() throws IllegalStateException {
        MediaPlayer mediaPlayer = this.mMediaPlayer;
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            this.mMediaPlayer.release();
            this.mWebview.obtainFrameView().removeFrameViewListener(this);
            this.mWebview.obtainFrameView().obtainApp().unregisterSysEventListener(this, ISysEventListener.SysEventType.onStop);
            this.mMediaPlayer = null;
            AudioManager audioManager = this.mAudioMgr;
            if (audioManager != null) {
                audioManager.abandonAudioFocus(this.mAudioFocusChangeListener);
            }
            this.mAudioMgr = null;
        }
    }

    void execEvents(String str, String str2) throws IllegalStateException, JSONException, IOException, SecurityException, IllegalArgumentException {
        String str3 = this.events.get(str);
        if (!PdrUtil.isEmpty(str3)) {
            Deprecated_JSUtil.execCallback(this.mWebview, str3, str2, JSUtil.OK, !PdrUtil.isEmpty(str2), true);
        }
        str.getClass();
        if (str.equals("ended")) {
            pause();
            successCallback();
            return;
        }
        if (str.equals("canplay")) {
            this.isCanplay = true;
            if (this.autoplay) {
                play();
            }
            if (this.isPlay) {
                startPlay();
            }
            int i = this.startTime;
            if (i != Integer.MIN_VALUE) {
                this.mMediaPlayer.seekTo(i);
            }
        }
    }

    void failCallback(int i, String str) {
        Deprecated_JSUtil.excCallbackError(this.mWebview, this.mFunId, DOMException.toJSON(i, str), true);
    }

    String getBuffer() {
        MediaPlayer mediaPlayer = this.mMediaPlayer;
        return Deprecated_JSUtil.wrapJsVar(PdrUtil.int2DecimalStr(mediaPlayer != null ? (this.bufferPercent * mediaPlayer.getDuration()) / 100 : -1, 1000), false);
    }

    String getDuration() {
        int duration;
        MediaPlayer mediaPlayer = this.mMediaPlayer;
        if (mediaPlayer != null && (duration = mediaPlayer.getDuration()) >= 0) {
            return Deprecated_JSUtil.wrapJsVar(PdrUtil.int2DecimalStr(duration, 1000), false);
        }
        return Deprecated_JSUtil.wrapJsVar(Constants.Name.UNDEFINED, false);
    }

    String getPosition() {
        return Deprecated_JSUtil.wrapJsVar(PdrUtil.int2DecimalStr(this.mMediaPlayer.getCurrentPosition(), 1000), false);
    }

    String getStyles(String str) {
        Object objValueOf;
        if (PdrUtil.isEmpty(str)) {
            return JSUtil.wrapJsVar(this.params);
        }
        str.getClass();
        str.hashCode();
        switch (str) {
            case "startTime":
                int i = this.startTime;
                objValueOf = Integer.valueOf(i < 0 ? this.params.has("startTime") ? this.params.optInt("startTime") : 0 : i / 1000);
                break;
            case "volume":
                objValueOf = Float.valueOf(this.volume);
                break;
            case "src":
                objValueOf = this.mSrcPath;
                break;
            case "loop":
                objValueOf = Boolean.valueOf(this.mMediaPlayer.isLooping());
                break;
            case "playbackRate":
                if (Build.VERSION.SDK_INT < 23) {
                    objValueOf = 1;
                    break;
                } else {
                    objValueOf = Float.valueOf(this.mMediaPlayer.getPlaybackParams().getSpeed());
                    break;
                }
            case "autoplay":
                objValueOf = Boolean.valueOf(this.params.optBoolean(Constants.Name.AUTOPLAY, false));
                break;
            default:
                return this.params.has(str) ? JSUtil.wrapJsVar(this.params.optString(str)) : Deprecated_JSUtil.wrapJsVar(Constants.Name.UNDEFINED, false);
        }
        return objValueOf != null ? JSUtil.wrapJsVar(objValueOf.toString()) : Deprecated_JSUtil.wrapJsVar(Constants.Name.UNDEFINED, false);
    }

    String getVolume() {
        return JSUtil.wrapJsVar(this.volume);
    }

    public boolean isCanMix() {
        return this.isCanMix;
    }

    String isPause() {
        MediaPlayer mediaPlayer = this.mMediaPlayer;
        return JSUtil.wrapJsVar(mediaPlayer != null ? true ^ mediaPlayer.isPlaying() : true);
    }

    @Override // io.dcloud.common.DHInterface.IEventCallback
    public Object onCallBack(String str, Object obj) throws IllegalStateException {
        if ((!PdrUtil.isEquals(str, AbsoluteConst.EVENTS_WINDOW_CLOSE) && !PdrUtil.isEquals(str, AbsoluteConst.EVENTS_CLOSE)) || !(obj instanceof IWebview)) {
            return null;
        }
        destory();
        return null;
    }

    @Override // io.dcloud.common.DHInterface.ISysEventListener
    public boolean onExecute(ISysEventListener.SysEventType sysEventType, Object obj) throws IllegalStateException {
        if (sysEventType != ISysEventListener.SysEventType.onStop) {
            return false;
        }
        destory();
        return false;
    }

    void pause() throws IllegalStateException, JSONException, IOException, SecurityException, IllegalArgumentException {
        this.autoplay = false;
        try {
            this.mMediaPlayer.pause();
        } catch (Exception unused) {
        }
        execEvents("pause", "");
    }

    void play() throws IllegalStateException, JSONException, IOException, SecurityException, IllegalArgumentException {
        if (this.isStoped && !this.mMediaPlayer.isPlaying()) {
            try {
                this.mMediaPlayer.prepareAsync();
                this.isStoped = false;
            } catch (Exception unused) {
                this.mSrcPath = "";
                setStyle(this.params);
                this.isStoped = false;
            }
        }
        try {
            this.isPrepared = false;
            this.isPlay = true;
            if (this.isCanplay) {
                startPlay();
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
            destory();
            failCallback(-1, e.toString());
            execEvents("error", DOMException.toJSON(-1, e.getMessage()));
        } catch (NumberFormatException unused2) {
        }
    }

    public void playbackRate(float f) throws JSONException, NumberFormatException {
        if (this.params == null) {
            this.params = new JSONObject();
        }
        try {
            this.params.put("playbackRate", f);
        } catch (JSONException unused) {
        }
        MediaPlayer mediaPlayer = this.mMediaPlayer;
        if (mediaPlayer == null || !mediaPlayer.isPlaying()) {
            return;
        }
        setSpeed();
    }

    void removeEventListener(String str) {
        this.events.remove(str);
    }

    void resume() throws IllegalStateException, NumberFormatException {
        requestAudioFocus();
        this.mMediaPlayer.start();
        setSpeed();
    }

    void seekTo(int i) throws IllegalStateException, JSONException, IOException, SecurityException, IllegalArgumentException {
        this.mMediaPlayer.seekTo(i);
        execEvents("seeking", "");
    }

    public void setCanMix(boolean z) {
        this.needPause = z;
    }

    public void setParams(JSONObject jSONObject) {
        this.params = jSONObject;
    }

    void setSessionCategory(String str) {
        MediaPlayer mediaPlayer;
        if (PdrUtil.isEmpty(str) || (mediaPlayer = this.mMediaPlayer) == null || mediaPlayer.isPlaying()) {
            return;
        }
        this.isCanMix = str.equals("ambient");
    }

    void setStyle(JSONObject jSONObject) throws IllegalStateException, JSONException, IOException, SecurityException, IllegalArgumentException {
        String strOptString = jSONObject.optString("src");
        if (!PdrUtil.isEmpty(strOptString) && (PdrUtil.isEmpty(this.mSrcPath) || !strOptString.equals(this.mSrcPath))) {
            this.mMediaPlayer.reset();
            setSrc(strOptString);
        }
        JSONUtil.combinJSONObject(this.params, jSONObject);
        this.mSrcPath = jSONObject.optString("src");
        this.mMediaPlayer.setLooping(this.params.optBoolean("loop"));
        try {
            float f = Float.parseFloat(this.params.optString("volume", "1"));
            this.volume = f;
            if (f < 0.0f) {
                this.volume = 0.0f;
            } else if (f > 1.0f) {
                this.volume = 1.0f;
            }
            MediaPlayer mediaPlayer = this.mMediaPlayer;
            float f2 = this.volume;
            mediaPlayer.setVolume(f2, f2);
            if (this.params.has("startTime")) {
                this.startTime = this.params.optInt("startTime") * 1000;
            }
            this.autoplay = this.params.optBoolean(Constants.Name.AUTOPLAY, false);
        } catch (Exception unused) {
        }
        try {
            float f3 = Float.parseFloat(this.params.optString("playbackRate"));
            if (f3 > 0.0f) {
                playbackRate(f3);
            }
        } catch (Exception unused2) {
        }
    }

    void stop() throws IllegalStateException {
        MediaPlayer mediaPlayer = this.mMediaPlayer;
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            this.isStoped = true;
            this.isCanplay = false;
            execEvents(Constants.Value.STOP, "");
        }
    }
}
