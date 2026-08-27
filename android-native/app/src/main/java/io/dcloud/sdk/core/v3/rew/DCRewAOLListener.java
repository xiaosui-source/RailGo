package io.dcloud.sdk.core.v3.rew;

import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public interface DCRewAOLListener {
    void onClick();

    void onClose();

    void onReward(JSONObject jSONObject);

    void onShow();

    void onShowError(int i, String str);

    void onSkip();

    void onVideoPlayEnd();
}
