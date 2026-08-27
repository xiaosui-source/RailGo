package io.dcloud.feature.audio.recorder;

import android.media.MediaRecorder;
import io.dcloud.common.adapter.io.DHFile;
import io.dcloud.common.adapter.util.DeviceInfo;
import io.dcloud.common.adapter.util.Logger;
import io.dcloud.common.util.PdrUtil;
import java.io.IOException;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class AudioRecorder extends AbsRecorder {
    private MediaRecorder mRecorder;

    public AudioRecorder(RecordOption recordOption) throws IllegalStateException, IOException {
        MediaRecorder mediaRecorder = new MediaRecorder();
        this.mRecorder = mediaRecorder;
        try {
            mediaRecorder.reset();
            this.mRecorder.setAudioSource(0);
            String str = recordOption.mFileName;
            if (!DHFile.isExist(str)) {
                DHFile.createNewFile(DHFile.createFileHandler(str));
            }
            this.mRecorder.setOutputFile(str);
            try {
                this.mRecorder.setAudioSamplingRate(recordOption.mSamplingRate);
                int i = recordOption.mSamplingRate;
                if (i == 44100) {
                    this.mRecorder.setOutputFormat(1);
                    this.mRecorder.setAudioEncoder(3);
                } else if (i == 16000) {
                    this.mRecorder.setOutputFormat(4);
                    this.mRecorder.setAudioEncoder(2);
                } else if (i == 8000) {
                    this.mRecorder.setOutputFormat(3);
                    this.mRecorder.setAudioEncoder(1);
                } else {
                    this.mRecorder.setOutputFormat(0);
                    this.mRecorder.setAudioEncoder(0);
                }
            } catch (Exception e) {
                this.mRecorder.reset();
                this.mRecorder.setAudioSource(0);
                this.mRecorder.setOutputFile(str);
                Logger.w("HighGradeRecorder.getRecorderInstence", e);
                if (PdrUtil.isEquals(recordOption.mFormat, "3gp")) {
                    this.mRecorder.setOutputFormat(1);
                } else if (DeviceInfo.sDeviceSdkVer >= 10) {
                    this.mRecorder.setOutputFormat(3);
                } else {
                    this.mRecorder.setOutputFormat(0);
                }
                this.mRecorder.setAudioEncoder(1);
            }
            this.mRecorder.prepare();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    @Override // io.dcloud.feature.audio.recorder.AbsRecorder
    public void pause() {
    }

    @Override // io.dcloud.feature.audio.recorder.AbsRecorder
    public void release() {
        this.mRecorder.release();
    }

    @Override // io.dcloud.feature.audio.recorder.AbsRecorder
    public void resume() {
    }

    @Override // io.dcloud.feature.audio.recorder.AbsRecorder
    public void start() throws IllegalStateException {
        this.mRecorder.start();
    }

    @Override // io.dcloud.feature.audio.recorder.AbsRecorder
    public void stop() throws IllegalStateException {
        this.mRecorder.stop();
    }
}
