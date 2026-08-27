package io.dcloud.common.DHInterface;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public interface ISysEventListener {

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public enum SysEventType {
        AllSystemEvent,
        onActivityResult,
        onCreateOptionMenu,
        onKeyDown,
        onKeyUp,
        onSaveInstanceState,
        onKeyLongPress,
        onStart,
        onResume,
        onWebAppStop,
        onWebAppReStart,
        onWebAppSaveState,
        onWebAppTrimMemory,
        onWebAppBackground,
        onWebAppPause,
        onWebAppForeground,
        onWebAppSrcUpZip,
        onStop,
        onPause,
        onDeviceNetChanged,
        onSimStateChanged,
        onNewIntent,
        onConfigurationChanged,
        onKeyboardShow,
        onKeyboardHide,
        onRequestPermissionsResult,
        onSplashclosed,
        onSizeChanged
    }

    boolean onExecute(SysEventType sysEventType, Object obj);
}
