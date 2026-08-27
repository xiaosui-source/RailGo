package io.dcloud.common.DHInterface;

import android.view.KeyEvent;
import io.dcloud.common.DHInterface.ISysEventListener;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public interface IKeyHandler {
    boolean onKeyEventExecute(ISysEventListener.SysEventType sysEventType, int i, KeyEvent keyEvent);
}
