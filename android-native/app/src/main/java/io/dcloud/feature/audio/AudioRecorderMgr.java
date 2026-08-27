package io.dcloud.feature.audio;

import android.app.Dialog;
import io.dcloud.base.R;
import io.dcloud.common.adapter.util.PermissionUtil;
import io.dcloud.common.constant.DOMException;
import io.dcloud.common.util.Deprecated_JSUtil;
import io.dcloud.common.util.ErrorDialogUtil;
import io.dcloud.common.util.StringUtil;
import io.dcloud.feature.audio.recorder.AbsRecorder;
import io.dcloud.feature.audio.recorder.AudioRecorder;
import io.dcloud.feature.audio.recorder.HighGradeRecorder;
import io.dcloud.feature.audio.recorder.RecordOption;
import io.dcloud.feature.audio.recorder.RecorderUtil;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class AudioRecorderMgr extends AbsAudio {
    private static AudioRecorderMgr mInstance;
    String mFunId;
    AbsRecorder mNativeRecorder;
    RecordOption mOption;

    private AudioRecorderMgr() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void failCallback(String str) {
        Deprecated_JSUtil.excCallbackError(this.mOption.mWebview, this.mFunId, StringUtil.format(DOMException.JSON_ERROR_INFO, 3, str), true);
    }

    public static boolean isPause(String str) {
        return str.equalsIgnoreCase("mp3") || str.equalsIgnoreCase("aac");
    }

    static AudioRecorderMgr startRecorder(RecordOption recordOption, String str) {
        if (mInstance == null) {
            mInstance = new AudioRecorderMgr();
        }
        AudioRecorderMgr audioRecorderMgr = mInstance;
        audioRecorderMgr.mOption = recordOption;
        audioRecorderMgr.mFunId = str;
        PermissionUtil.usePermission(recordOption.mWebview.getActivity(), "audio", PermissionUtil.PMS_RECORD, 2, new PermissionUtil.StreamPermissionRequest(mInstance.mOption.mWebview.obtainApp()) { // from class: io.dcloud.feature.audio.AudioRecorderMgr.1
            @Override // io.dcloud.common.adapter.util.PermissionUtil.Request
            public void onDenied(String str2) {
                if (AudioRecorderMgr.mInstance != null) {
                    AudioRecorderMgr.mInstance.failCallback(DOMException.MSG_NO_PERMISSION);
                }
            }

            @Override // io.dcloud.common.adapter.util.PermissionUtil.Request
            public void onGranted(String str2) {
                if (AudioRecorderMgr.mInstance == null) {
                    return;
                }
                if (!AudioRecorderMgr.isPause(AudioRecorderMgr.mInstance.mOption.mFormat)) {
                    AudioRecorderMgr.mInstance.mNativeRecorder = new AudioRecorder(AudioRecorderMgr.mInstance.mOption);
                    try {
                        AudioRecorderMgr.mInstance.mNativeRecorder.start();
                        return;
                    } catch (Exception e) {
                        e.printStackTrace();
                        AudioRecorderMgr.mInstance.failCallback(e.getMessage());
                        AudioRecorderMgr.mInstance.stop();
                        return;
                    }
                }
                AudioRecorderMgr.mInstance.mNativeRecorder = new HighGradeRecorder().setRecordOption(AudioRecorderMgr.mInstance.mOption);
                AudioRecorderMgr.mInstance.mOption.mFormat.equalsIgnoreCase("aac");
                if (!AudioRecorderMgr.mInstance.mOption.mFormat.equalsIgnoreCase("mp3") || RecorderUtil.isContainMp3()) {
                    try {
                        AudioRecorderMgr.mInstance.mNativeRecorder.start();
                        return;
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        AudioRecorderMgr.mInstance.failCallback(e2.getMessage());
                        AudioRecorderMgr.mInstance.stop();
                        return;
                    }
                }
                AudioRecorderMgr.mInstance.failCallback(AudioRecorderMgr.mInstance.mOption.mWebview.getContext().getString(R.string.dcloud_audio_not_mp3_recording));
                Dialog lossDialog = ErrorDialogUtil.getLossDialog(AudioRecorderMgr.mInstance.mOption.mWebview, AudioRecorderMgr.mInstance.mOption.mWebview.getContext().getString(R.string.dcloud_audio_no_mp3_module_added) + " https://ask.dcloud.net.cn/article/35058", "https://ask.dcloud.net.cn/article/35058", "audio");
                if (lossDialog != null) {
                    lossDialog.show();
                }
            }
        });
        return mInstance;
    }

    public void pause() {
        RecordOption recordOption;
        AudioRecorderMgr audioRecorderMgr = mInstance;
        if (audioRecorderMgr == null || (recordOption = audioRecorderMgr.mOption) == null || !isPause(recordOption.mFormat)) {
            return;
        }
        mInstance.mNativeRecorder.pause();
    }

    public void resume() {
        RecordOption recordOption;
        AudioRecorderMgr audioRecorderMgr = mInstance;
        if (audioRecorderMgr == null || (recordOption = audioRecorderMgr.mOption) == null || !isPause(recordOption.mFormat)) {
            return;
        }
        mInstance.mNativeRecorder.resume();
    }

    public void stop() {
        AbsRecorder absRecorder;
        AudioRecorderMgr audioRecorderMgr = mInstance;
        if (audioRecorderMgr != null && (absRecorder = audioRecorderMgr.mNativeRecorder) != null) {
            absRecorder.stop();
            mInstance.mNativeRecorder.release();
            mInstance.mNativeRecorder = null;
        }
        mInstance = null;
    }

    void successCallback() {
        RecordOption recordOption = this.mOption;
        Deprecated_JSUtil.excCallbackSuccess(this.mOption.mWebview, this.mFunId, recordOption.mWebview.obtainFrameView().obtainApp().convert2RelPath(recordOption.mFileName));
    }
}
