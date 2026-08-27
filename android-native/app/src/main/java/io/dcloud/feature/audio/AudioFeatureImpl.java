package io.dcloud.feature.audio;

import android.media.AudioManager;
import android.text.TextUtils;
import com.taobao.weex.common.Constants;
import io.dcloud.common.DHInterface.AbsMgr;
import io.dcloud.common.DHInterface.IApp;
import io.dcloud.common.DHInterface.IFeature;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.adapter.util.Logger;
import io.dcloud.common.adapter.util.MessageHandler;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.common.constant.DOMException;
import io.dcloud.common.util.AppRuntime;
import io.dcloud.common.util.BaseInfo;
import io.dcloud.common.util.JSONUtil;
import io.dcloud.common.util.JSUtil;
import io.dcloud.feature.audio.recorder.RecordOption;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class AudioFeatureImpl implements IFeature, MessageHandler.IMessages {
    static final String TAG = "AudioFeatureImpl";
    HashMap<String, ArrayList> mAppsAudioObj = null;

    private Object findAppObj(String str, String str2) {
        ArrayList appObjList = getAppObjList(str);
        if (appObjList.isEmpty()) {
            return null;
        }
        int size = appObjList.size();
        int i = 0;
        while (i < size) {
            Object obj = appObjList.get(i);
            i++;
            if ((obj instanceof AbsAudio) && ((AbsAudio) obj).mUuid.equals(str2)) {
                return obj;
            }
        }
        return null;
    }

    private ArrayList getAppObjList(String str) {
        ArrayList arrayList = this.mAppsAudioObj.get(str);
        if (arrayList != null) {
            return arrayList;
        }
        ArrayList arrayList2 = new ArrayList(2);
        this.mAppsAudioObj.put(str, arrayList2);
        return arrayList2;
    }

    private void putAppObjList(String str, Object obj) {
        getAppObjList(str).add(obj);
    }

    private void removeAppObjFromList(String str, Object obj) {
        ArrayList appObjList = getAppObjList(str);
        if (appObjList != null) {
            appObjList.remove(obj);
        }
    }

    private void setCanPlay(String str, String str2, boolean z) throws IllegalStateException, JSONException, IOException, SecurityException, IllegalArgumentException {
        ArrayList appObjList = getAppObjList(str);
        int size = appObjList.size();
        int i = 0;
        while (i < size) {
            Object obj = appObjList.get(i);
            i++;
            if (obj instanceof AudioPlayer) {
                AudioPlayer audioPlayer = (AudioPlayer) obj;
                if (!audioPlayer.mUuid.equals(str2) && !z) {
                    audioPlayer.pause();
                }
                audioPlayer.setCanMix(z);
            }
        }
    }

    private void setSpeakerphoneOn(AudioManager audioManager, boolean z) {
        if (z) {
            audioManager.setSpeakerphoneOn(true);
            audioManager.setMode(1);
        } else {
            audioManager.setSpeakerphoneOn(false);
            audioManager.setRouting(0, 1, -1);
            audioManager.setMode(3);
        }
    }

    @Override // io.dcloud.common.DHInterface.IFeature
    public void dispose(String str) {
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:6:0x0044  */
    @Override // io.dcloud.common.DHInterface.IFeature
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.lang.String execute(io.dcloud.common.DHInterface.IWebview r9, java.lang.String r10, java.lang.String[] r11) throws org.json.JSONException {
        /*
            Method dump skipped, instructions count: 314
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.feature.audio.AudioFeatureImpl.execute(io.dcloud.common.DHInterface.IWebview, java.lang.String, java.lang.String[]):java.lang.String");
    }

    @Override // io.dcloud.common.DHInterface.IFeature
    public void init(AbsMgr absMgr, String str) {
        this.mAppsAudioObj = new HashMap<>(2);
    }

    @Override // io.dcloud.common.adapter.util.MessageHandler.IMessages
    public void execute(Object obj) throws IllegalStateException, JSONException, IOException, SecurityException, IllegalArgumentException {
        AudioPlayer audioPlayer;
        Object[] objArr = (Object[]) obj;
        IWebview iWebview = (IWebview) objArr[0];
        String strValueOf = String.valueOf(objArr[1]);
        String[] strArr = (String[]) objArr[2];
        IApp iAppObtainApp = iWebview.obtainFrameView().obtainApp();
        String strObtainAppId = iAppObtainApp.obtainAppId();
        String str = strArr[0];
        JSONArray jSONArrayCreateJSONArray = JSONUtil.createJSONArray(strArr[1]);
        String string = JSONUtil.getString(jSONArrayCreateJSONArray, 0);
        if ("RecorderExecMethod".equals(strValueOf)) {
            AppRuntime.checkPrivacyComplianceAndPrompt(iWebview.getContext(), "Audio-" + strValueOf);
            try {
                if ("record".equals(str)) {
                    String string2 = JSONUtil.getString(jSONArrayCreateJSONArray, 1);
                    RecordOption recordOption = new RecordOption(iWebview, JSONUtil.getJSONObject(jSONArrayCreateJSONArray, 2));
                    if (JSUtil.checkOperateDirErrorAndCallback(iWebview, string2, recordOption.mFileName)) {
                        return;
                    }
                    AudioRecorderMgr audioRecorderMgrStartRecorder = AudioRecorderMgr.startRecorder(recordOption, string2);
                    audioRecorderMgrStartRecorder.mUuid = string;
                    putAppObjList(strObtainAppId, audioRecorderMgrStartRecorder);
                    return;
                }
                if ("pause".equals(str)) {
                    ((AudioRecorderMgr) findAppObj(strObtainAppId, string)).pause();
                    return;
                }
                if (Constants.Value.STOP.equals(str)) {
                    AudioRecorderMgr audioRecorderMgr = (AudioRecorderMgr) findAppObj(strObtainAppId, string);
                    audioRecorderMgr.stop();
                    audioRecorderMgr.successCallback();
                    removeAppObjFromList(strObtainAppId, audioRecorderMgr);
                    return;
                }
                if (AbsoluteConst.EVENTS_RESUME.equals(str)) {
                    ((AudioRecorderMgr) findAppObj(strObtainAppId, string)).resume();
                    return;
                }
                return;
            } catch (Exception e) {
                e.printStackTrace();
                Logger.e("RecorderExecMethod _methodName=" + str + "; e =" + e);
                return;
            }
        }
        if ("AudioExecMethod".equals(strValueOf)) {
            AudioPlayer audioPlayer2 = null;
            try {
                audioPlayer = (AudioPlayer) findAppObj(strObtainAppId, string);
            } catch (Exception e2) {
                e = e2;
            }
            try {
                try {
                    switch (str.hashCode()) {
                        case -934426579:
                            if (str.equals(AbsoluteConst.EVENTS_RESUME)) {
                                setCanPlay(strObtainAppId, string, audioPlayer.isCanMix());
                                if (BaseInfo.isUniAppAppid(iAppObtainApp)) {
                                    audioPlayer.mFunId = "";
                                    audioPlayer.play();
                                    return;
                                } else {
                                    audioPlayer.resume();
                                    return;
                                }
                            }
                            return;
                        case -906224877:
                            if (str.equals("seekTo")) {
                                try {
                                    int i = Integer.parseInt(JSONUtil.getString(jSONArrayCreateJSONArray, 1));
                                    if (i >= 0) {
                                        audioPlayer.seekTo(i * 1000);
                                        return;
                                    }
                                    return;
                                } catch (Exception unused) {
                                    int i2 = (int) (Double.parseDouble(JSONUtil.getString(jSONArrayCreateJSONArray, 1)) * 1000.0d);
                                    if (i2 >= 0) {
                                        audioPlayer.seekTo(i2);
                                        return;
                                    }
                                    return;
                                }
                            }
                            return;
                        case -625809843:
                            if (str.equals("addEventListener")) {
                                audioPlayer.addEventListener(jSONArrayCreateJSONArray.optString(1), jSONArrayCreateJSONArray.optString(2));
                                return;
                            }
                            return;
                        case -541487286:
                            if (str.equals("removeEventListener")) {
                                audioPlayer.removeEventListener(jSONArrayCreateJSONArray.optString(1));
                                return;
                            }
                            return;
                        case -388163342:
                            if (str.equals("setSessionCategory")) {
                                audioPlayer.setSessionCategory(jSONArrayCreateJSONArray.length() > 1 ? JSONUtil.getString(jSONArrayCreateJSONArray, 1) : null);
                                return;
                            }
                            return;
                        case 3443508:
                            if (str.equals(Constants.Value.PLAY)) {
                                setCanPlay(strObtainAppId, string, audioPlayer.isCanMix());
                                audioPlayer.mFunId = JSONUtil.getString(jSONArrayCreateJSONArray, 1);
                                audioPlayer.play();
                                return;
                            }
                            return;
                        case 3540994:
                            if (str.equals(Constants.Value.STOP)) {
                                audioPlayer.stop();
                                return;
                            }
                            return;
                        case 94756344:
                            if (str.equals(AbsoluteConst.EVENTS_CLOSE)) {
                                audioPlayer.destory();
                                removeAppObjFromList(strObtainAppId, audioPlayer);
                                return;
                            }
                            return;
                        case 106440182:
                            if (str.equals("pause")) {
                                audioPlayer.pause();
                                return;
                            }
                            return;
                        case 589623268:
                            if (str.equals("setStyles")) {
                                audioPlayer.setStyle(jSONArrayCreateJSONArray.optJSONObject(1));
                                return;
                            }
                            return;
                        case 1355420059:
                            if (!str.equals("playbackRate") || jSONArrayCreateJSONArray.length() <= 1) {
                                return;
                            }
                            String string3 = JSONUtil.getString(jSONArrayCreateJSONArray, 1);
                            if (TextUtils.isEmpty(string3)) {
                                return;
                            }
                            float f = Float.parseFloat(string3);
                            if (f > 0.0f) {
                                audioPlayer.playbackRate(f);
                                return;
                            }
                            return;
                        case 1403417351:
                            if (str.equals("setRoute")) {
                                AudioManager audioManager = (AudioManager) iWebview.getContext().getSystemService("audio");
                                if (Integer.parseInt(jSONArrayCreateJSONArray.optString(1)) == 1) {
                                    setSpeakerphoneOn(audioManager, false);
                                    return;
                                } else {
                                    setSpeakerphoneOn(audioManager, true);
                                    return;
                                }
                            }
                            return;
                        default:
                            return;
                    }
                } catch (Exception unused2) {
                }
            } catch (Exception e3) {
                e = e3;
                audioPlayer2 = audioPlayer;
                e.printStackTrace();
                if (audioPlayer2 != null) {
                    String str2 = DOMException.MSG_PARAMETER_ERROR;
                    audioPlayer2.failCallback(-1, str2);
                    audioPlayer2.execEvents("onError", DOMException.toJSON(-1, str2));
                }
            }
        }
    }
}
