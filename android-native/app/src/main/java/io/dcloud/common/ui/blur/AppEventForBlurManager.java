package io.dcloud.common.ui.blur;

import io.dcloud.common.adapter.util.MessageHandler;
import io.dcloud.common.util.ThreadPool;
import java.util.ArrayList;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class AppEventForBlurManager {
    private static final String TAG = "AppScrollManager";
    private static ArrayList<OnAppChangedCallBack> callBacks = new ArrayList<>();
    public static final boolean isBlur = true;
    private static boolean mLoop = false;
    private static long sLastChangedTime = 0;
    private static boolean sScrollStart = false;

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public interface OnAppChangedCallBack {
        void onContentScrollEnd();

        void onContentScrollStart();

        void onSplashclosed();
    }

    public static synchronized void addEventChangedCallBack(OnAppChangedCallBack onAppChangedCallBack) {
        if (callBacks.contains(onAppChangedCallBack)) {
            return;
        }
        callBacks.add(onAppChangedCallBack);
    }

    public static void onScrollChanged(int i, int i2) {
        sScrollStart = true;
        sLastChangedTime = System.currentTimeMillis();
        if (!mLoop) {
            onScrollStart();
        }
        startLoop();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void onScrollEnd() {
        MessageHandler.post(new Runnable() { // from class: io.dcloud.common.ui.blur.AppEventForBlurManager.3
            @Override // java.lang.Runnable
            public void run() {
                ArrayList arrayList = AppEventForBlurManager.callBacks;
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    ((OnAppChangedCallBack) obj).onContentScrollEnd();
                }
            }
        });
    }

    private static void onScrollStart() {
        MessageHandler.post(new Runnable() { // from class: io.dcloud.common.ui.blur.AppEventForBlurManager.2
            @Override // java.lang.Runnable
            public void run() {
                ArrayList arrayList = AppEventForBlurManager.callBacks;
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    ((OnAppChangedCallBack) obj).onContentScrollStart();
                }
            }
        });
    }

    public static void onSplashclosed() {
        MessageHandler.post(new Runnable() { // from class: io.dcloud.common.ui.blur.AppEventForBlurManager.4
            @Override // java.lang.Runnable
            public void run() {
                ArrayList arrayList = AppEventForBlurManager.callBacks;
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    ((OnAppChangedCallBack) obj).onSplashclosed();
                }
            }
        });
    }

    public static synchronized void removeEventChangedCallBack(OnAppChangedCallBack onAppChangedCallBack) {
        if (callBacks.contains(onAppChangedCallBack)) {
            callBacks.remove(onAppChangedCallBack);
        }
    }

    private static void startLoop() {
        if (mLoop) {
            return;
        }
        mLoop = true;
        ThreadPool.self().addThreadTask(new Runnable() { // from class: io.dcloud.common.ui.blur.AppEventForBlurManager.1
            @Override // java.lang.Runnable
            public void run() throws InterruptedException {
                while (AppEventForBlurManager.sScrollStart) {
                    boolean unused = AppEventForBlurManager.mLoop = true;
                    if (System.currentTimeMillis() - AppEventForBlurManager.sLastChangedTime > 500) {
                        boolean unused2 = AppEventForBlurManager.sScrollStart = false;
                        boolean unused3 = AppEventForBlurManager.mLoop = false;
                        long unused4 = AppEventForBlurManager.sLastChangedTime = 0L;
                        AppEventForBlurManager.onScrollEnd();
                    } else {
                        try {
                            Thread.sleep(200L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, true);
    }
}
