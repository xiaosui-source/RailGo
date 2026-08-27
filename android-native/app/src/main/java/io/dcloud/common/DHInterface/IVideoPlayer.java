package io.dcloud.common.DHInterface;

import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public interface IVideoPlayer {
    void addEventListener(String str, String str2, String str3);

    void close();

    void exitFullScreen();

    boolean isFullScreen();

    boolean isPointInRect(float f, float f2);

    boolean isVideoHandleTouch();

    void pause();

    void play();

    void playbackRate(String str);

    void release();

    void requestFullScreen(String str);

    void resume();

    void seek(String str);

    void sendDanmu(JSONObject jSONObject);

    void setOptions(JSONObject jSONObject);

    void stop();
}
