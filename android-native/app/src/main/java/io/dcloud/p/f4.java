package io.dcloud.p;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.PixelCopy;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public abstract class f4 {
    private static WeakReference a = null;
    private static File b = null;
    private static final HandlerThread c;
    private static final Handler d;
    private static final Handler e;
    private static boolean f = false;

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class a implements Runnable {
        final /* synthetic */ o a;

        a(o oVar) {
            this.a = oVar;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.a.a(null, null);
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class b implements Runnable {
        final /* synthetic */ o a;
        final /* synthetic */ Bitmap b;

        b(o oVar, Bitmap bitmap) {
            this.a = oVar;
            this.b = bitmap;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.a.a(null, this.b);
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class c implements Runnable {
        final /* synthetic */ o a;

        c(o oVar) {
            this.a = oVar;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.a.a(null, null);
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class d implements PixelCopy.OnPixelCopyFinishedListener {
        final /* synthetic */ List a;
        final /* synthetic */ Bitmap b;
        final /* synthetic */ List c;
        final /* synthetic */ int d;
        final /* synthetic */ Bitmap e;
        final /* synthetic */ o f;

        d(List list, Bitmap bitmap, List list2, int i, Bitmap bitmap2, o oVar) {
            this.a = list;
            this.b = bitmap;
            this.c = list2;
            this.d = i;
            this.e = bitmap2;
            this.f = oVar;
        }

        @Override // android.view.PixelCopy.OnPixelCopyFinishedListener
        public void onPixelCopyFinished(int i) {
            if (i == 0) {
                this.a.add(this.b);
            } else {
                this.a.add(Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888));
            }
            f4.b(this.c, this.d + 1, this.e, this.a, this.f);
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class e implements Runnable {
        final /* synthetic */ File a;

        e(File file) {
            this.a = file;
        }

        @Override // java.lang.Runnable
        public void run() {
            File[] fileArrListFiles;
            try {
                File file = this.a;
                if (file != null && file.exists() && (fileArrListFiles = this.a.listFiles()) != null) {
                    long jCurrentTimeMillis = System.currentTimeMillis();
                    for (File file2 : fileArrListFiles) {
                        if (jCurrentTimeMillis - file2.lastModified() > 604800000) {
                            f4.c(file2);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class f implements o {
        final /* synthetic */ View a;
        final /* synthetic */ Activity b;
        final /* synthetic */ String c;

        f(View view, Activity activity, String str) {
            this.a = view;
            this.b = activity;
            this.c = str;
        }

        @Override // io.dcloud.p.f4.o
        public void a(Bitmap bitmap, Bitmap bitmap2) {
            f4.b("captureAndSaveAuto", bitmap, bitmap2, this.a, this.b, this.c);
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class g implements o {
        final /* synthetic */ Activity a;
        final /* synthetic */ String b;

        g(Activity activity, String str) {
            this.a = activity;
            this.b = str;
        }

        @Override // io.dcloud.p.f4.o
        public void a(Bitmap bitmap, Bitmap bitmap2) {
            f4.b("captureTopAndSaveAuto", bitmap, bitmap2, null, this.a, this.b);
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class h implements Runnable {
        final /* synthetic */ View a;
        final /* synthetic */ Activity b;
        final /* synthetic */ boolean c;
        final /* synthetic */ o d;

        h(View view, Activity activity, boolean z, o oVar) {
            this.a = view;
            this.b = activity;
            this.c = z;
            this.d = oVar;
        }

        @Override // java.lang.Runnable
        public void run() {
            f4.b(this.a, this.b, this.c, this.d);
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class i implements Application.ActivityLifecycleCallbacks {
        i() {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityCreated(Activity activity, Bundle bundle) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityDestroyed(Activity activity) {
            if (f4.a == null || f4.a.get() != activity) {
                return;
            }
            WeakReference unused = f4.a = null;
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityPaused(Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityResumed(Activity activity) {
            WeakReference unused = f4.a = new WeakReference(activity);
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStarted(Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStopped(Activity activity) {
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class j implements o {
        final /* synthetic */ Bitmap a;
        final /* synthetic */ View b;
        final /* synthetic */ List c;
        final /* synthetic */ int d;
        final /* synthetic */ o e;

        j(Bitmap bitmap, View view, List list, int i, o oVar) {
            this.a = bitmap;
            this.b = view;
            this.c = list;
            this.d = i;
            this.e = oVar;
        }

        @Override // io.dcloud.p.f4.o
        public void a(Bitmap bitmap, Bitmap bitmap2) {
            if (bitmap2 != null) {
                bitmap = bitmap2;
            }
            if (bitmap != null) {
                Canvas canvas = new Canvas(this.a);
                this.b.getLocationOnScreen(new int[2]);
                canvas.drawBitmap(bitmap, r1[0], r1[1], (Paint) null);
            }
            f4.b(this.c, this.d + 1, this.a, this.e);
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class k implements Runnable {
        final /* synthetic */ o a;

        k(o oVar) {
            this.a = oVar;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.a.a(null, null);
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class l implements Runnable {
        final /* synthetic */ o a;
        final /* synthetic */ Bitmap b;

        l(o oVar, Bitmap bitmap) {
            this.a = oVar;
            this.b = bitmap;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.a.a(null, this.b);
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class m implements PixelCopy.OnPixelCopyFinishedListener {
        final /* synthetic */ o a;
        final /* synthetic */ Bitmap b;
        final /* synthetic */ View c;

        m(o oVar, Bitmap bitmap, View view) {
            this.a = oVar;
            this.b = bitmap;
            this.c = view;
        }

        @Override // android.view.PixelCopy.OnPixelCopyFinishedListener
        public void onPixelCopyFinished(int i) {
            if (i == 0) {
                this.a.a(null, this.b);
            } else {
                f4.b(this.c, this.a);
            }
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class n implements PixelCopy.OnPixelCopyFinishedListener {
        final /* synthetic */ o a;
        final /* synthetic */ Bitmap b;
        final /* synthetic */ View c;

        n(o oVar, Bitmap bitmap, View view) {
            this.a = oVar;
            this.b = bitmap;
            this.c = view;
        }

        @Override // android.view.PixelCopy.OnPixelCopyFinishedListener
        public void onPixelCopyFinished(int i) {
            if (i == 0) {
                this.a.a(null, this.b);
            } else {
                f4.b(this.c, this.a);
            }
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    private interface o {
        void a(Bitmap bitmap, Bitmap bitmap2);
    }

    static {
        HandlerThread handlerThread = new HandlerThread("ScreenshotWorker");
        c = handlerThread;
        handlerThread.start();
        d = new Handler(handlerThread.getLooper());
        e = new Handler(Looper.getMainLooper());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(View view, Activity activity, boolean z, o oVar) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            e.post(new h(view, activity, z, oVar));
            return;
        }
        if (activity != null && view != null) {
            a(view, activity.getWindow(), oVar);
        } else if (view != null) {
            a(view, (Window) null, oVar);
        } else {
            a(activity, z, oVar);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void c(File file) {
        File[] fileArrListFiles;
        if (file.isDirectory() && (fileArrListFiles = file.listFiles()) != null) {
            for (File file2 : fileArrListFiles) {
                c(file2);
            }
        }
        file.delete();
    }

    public static void a(View view, Activity activity, String str) {
        b(view, activity, false, (o) new f(view, activity, str));
    }

    public static void a(Activity activity, boolean z, String str) {
        b((View) null, activity, z, new g(activity, str));
    }

    public static void a(Application application) {
        if (f) {
            return;
        }
        f = true;
        File externalFilesDir = application.getExternalFilesDir("feedback");
        if (externalFilesDir != null) {
            b = externalFilesDir;
            b(externalFilesDir);
        }
        application.registerActivityLifecycleCallbacks(new i());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(List list, int i2, Bitmap bitmap, o oVar) {
        if (i2 >= list.size()) {
            a(list, bitmap, oVar);
        } else {
            View view = (View) list.get(i2);
            a(view, a(view), new j(bitmap, view, list, i2, oVar));
        }
    }

    private static void a(Activity activity, boolean z, o oVar) {
        WeakReference weakReference = a;
        Activity activity2 = weakReference != null ? (Activity) weakReference.get() : null;
        if (activity2 != null) {
            activity = activity2;
        }
        ArrayList arrayList = new ArrayList();
        if (!z) {
            arrayList.addAll(b());
            if (arrayList.isEmpty() && activity != null) {
                arrayList.add(activity.getWindow().getDecorView());
            }
        } else if (activity != null) {
            arrayList.add(activity.getWindow().getDecorView());
        }
        if (arrayList.isEmpty()) {
            oVar.a(null, null);
            return;
        }
        View view = (View) arrayList.get(0);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) view.getContext().getSystemService("window");
        if (windowManager != null) {
            windowManager.getDefaultDisplay().getRealMetrics(displayMetrics);
        } else {
            displayMetrics = view.getResources().getDisplayMetrics();
        }
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        if (width <= 0 || height <= 0) {
            width = view.getWidth();
            height = view.getHeight();
        }
        if (width > 0 && height > 0) {
            b(arrayList, 0, Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888), oVar);
        } else {
            oVar.a(null, null);
        }
    }

    private static List b() throws IllegalAccessException, NoSuchFieldException, ClassNotFoundException, SecurityException, IllegalArgumentException, InvocationTargetException {
        ArrayList arrayList = new ArrayList();
        try {
            Class<?> cls = Class.forName("android.view.WindowManagerGlobal");
            Object objInvoke = cls.getMethod("getInstance", null).invoke(null, null);
            Field declaredField = cls.getDeclaredField("mViews");
            declaredField.setAccessible(true);
            List list = (List) declaredField.get(objInvoke);
            Field declaredField2 = cls.getDeclaredField("mParams");
            declaredField2.setAccessible(true);
            List list2 = (List) declaredField2.get(objInvoke);
            if (list != null) {
                for (int i2 = 0; i2 < list.size(); i2++) {
                    View view = (View) list.get(i2);
                    if (view.getVisibility() == 0 && view.isShown() && view.getWidth() > 0 && view.getHeight() > 0 && (list2 == null || i2 >= list2.size() || ((WindowManager.LayoutParams) list2.get(i2)).type != 2005)) {
                        arrayList.add(view);
                    }
                }
            }
            return arrayList;
        } catch (Exception e2) {
            e2.printStackTrace();
            return arrayList;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(View view, o oVar) {
        try {
            if (view.getWidth() > 0 && view.getHeight() > 0) {
                Bitmap bitmapCreateBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmapCreateBitmap);
                Drawable background = view.getBackground();
                if (background != null) {
                    background.draw(canvas);
                }
                view.draw(canvas);
                a(canvas, view, view);
                d.post(new b(oVar, bitmapCreateBitmap));
                return;
            }
            d.post(new a(oVar));
        } catch (Exception e2) {
            e2.printStackTrace();
            d.post(new c(oVar));
        }
    }

    private static void a(View view, Window window, o oVar) {
        View decorView;
        if (view.getWidth() > 0 && view.getHeight() > 0) {
            if (view instanceof TextureView) {
                d.post(new l(oVar, ((TextureView) view).getBitmap()));
                return;
            }
            if (Build.VERSION.SDK_INT >= 24 && (view instanceof SurfaceView)) {
                Bitmap bitmapCreateBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
                try {
                    PixelCopy.request((SurfaceView) view, bitmapCreateBitmap, new m(oVar, bitmapCreateBitmap, view), d);
                    return;
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            if (Build.VERSION.SDK_INT >= 26) {
                if (window == null) {
                    window = a(view);
                }
                if (window != null && (decorView = window.getDecorView()) != null && view.getWindowToken() != decorView.getWindowToken()) {
                    window = null;
                }
                if (window != null && window.getDecorView() != null) {
                    Bitmap bitmapCreateBitmap2 = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
                    int[] iArr = new int[2];
                    view.getLocationInWindow(iArr);
                    int i2 = iArr[0];
                    try {
                        PixelCopy.request(window, new Rect(i2, iArr[1], view.getWidth() + i2, iArr[1] + view.getHeight()), bitmapCreateBitmap2, new n(oVar, bitmapCreateBitmap2, view), d);
                        return;
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                }
            }
            b(view, oVar);
            return;
        }
        d.post(new k(oVar));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(List list, int i2, Bitmap bitmap, List list2, o oVar) {
        Bitmap bitmapCreateBitmap;
        if (i2 >= list.size()) {
            if (list2.isEmpty()) {
                bitmapCreateBitmap = null;
            } else {
                int height = bitmap.getHeight();
                int width = bitmap.getWidth();
                Iterator it = list2.iterator();
                while (it.hasNext()) {
                    Bitmap bitmap2 = (Bitmap) it.next();
                    height += bitmap2.getHeight();
                    if (bitmap2.getWidth() > width) {
                        width = bitmap2.getWidth();
                    }
                }
                try {
                    bitmapCreateBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(bitmapCreateBitmap);
                    canvas.drawColor(-16777216);
                    canvas.drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
                    int height2 = bitmap.getHeight();
                    Iterator it2 = list2.iterator();
                    while (it2.hasNext()) {
                        Bitmap bitmap3 = (Bitmap) it2.next();
                        canvas.drawBitmap(bitmap3, 0.0f, height2, (Paint) null);
                        height2 += bitmap3.getHeight();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    bitmapCreateBitmap = bitmap;
                }
            }
            if (!list2.isEmpty()) {
                try {
                    Bitmap bitmapCopy = bitmap.copy(Bitmap.Config.ARGB_8888, true);
                    Canvas canvas2 = new Canvas(bitmapCopy);
                    Paint paint = new Paint();
                    paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OVER));
                    for (int size = list.size() - 1; size >= 0; size--) {
                        SurfaceView surfaceView = (SurfaceView) list.get(size);
                        Bitmap bitmap4 = (Bitmap) list2.get(size);
                        surfaceView.getLocationOnScreen(new int[2]);
                        canvas2.drawBitmap(bitmap4, r9[0], r9[1], paint);
                    }
                    bitmap = bitmapCopy;
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
            oVar.a(bitmapCreateBitmap, bitmap);
            return;
        }
        SurfaceView surfaceView2 = (SurfaceView) list.get(i2);
        if (surfaceView2.getWidth() > 0 && surfaceView2.getHeight() > 0) {
            Bitmap bitmapCreateBitmap2 = Bitmap.createBitmap(surfaceView2.getWidth(), surfaceView2.getHeight(), Bitmap.Config.ARGB_8888);
            try {
                PixelCopy.request(surfaceView2, bitmapCreateBitmap2, new d(list2, bitmapCreateBitmap2, list, i2, bitmap, oVar), d);
                return;
            } catch (Exception e4) {
                e4.printStackTrace();
                list2.add(Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888));
                b(list, i2 + 1, bitmap, list2, oVar);
                return;
            }
        }
        b(list, i2 + 1, bitmap, list2, oVar);
    }

    private static Window a(View view) {
        for (Context context = view.getContext(); context instanceof ContextWrapper; context = ((ContextWrapper) context).getBaseContext()) {
            if (context instanceof Activity) {
                return ((Activity) context).getWindow();
            }
        }
        return null;
    }

    private static void a(Canvas canvas, View view, View view2) {
        Bitmap bitmap;
        if (view2 instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view2;
            for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
                a(canvas, view, viewGroup.getChildAt(childCount));
            }
            return;
        }
        if ((view2 instanceof TextureView) && view2.getVisibility() == 0 && (bitmap = ((TextureView) view2).getBitmap()) != null) {
            int[] iArr = new int[2];
            view.getLocationOnScreen(iArr);
            int[] iArr2 = new int[2];
            view2.getLocationOnScreen(iArr2);
            float f2 = iArr2[0] - iArr[0];
            float f3 = iArr2[1] - iArr[1];
            Paint paint = new Paint();
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OVER));
            canvas.drawBitmap(bitmap, f2, f3, paint);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static void b(String str, Bitmap bitmap, Bitmap bitmap2, View view, Activity activity, String str2) {
        File fileA;
        File fileA2;
        WeakReference weakReference;
        if (bitmap == null && bitmap2 == null) {
            Log.w("ScreenshotHelper", str + ": capture failed");
            return;
        }
        if (activity == 0 && (weakReference = a) != null) {
            activity = (Context) weakReference.get();
        }
        if (activity == 0 && view != null) {
            activity = view.getContext();
        }
        if (activity == 0 && b == null) {
            Log.w("ScreenshotHelper", str + ": no context available to save");
            return;
        }
        try {
            File externalFilesDir = b;
            if (externalFilesDir == null && activity != 0) {
                externalFilesDir = activity.getExternalFilesDir("feedback");
            }
            if (externalFilesDir == null) {
                return;
            }
            if (!externalFilesDir.exists()) {
                externalFilesDir.mkdirs();
            }
            String str3 = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss-SSS", Locale.getDefault()).format(new Date());
            if (str2 == null || str2.isEmpty()) {
                str2 = "screenshot";
            }
            File file = new File(externalFilesDir, str3 + "_" + str2);
            if (!file.exists()) {
                file.mkdirs();
            }
            if (bitmap != null && (fileA2 = a(bitmap, new File(file, "appended.png"))) != null) {
                Log.i("ScreenshotHelper", str + ": appended saved at " + fileA2.getAbsolutePath());
            }
            if (bitmap2 == null || (fileA = a(bitmap2, new File(file, "layered.png"))) == null) {
                return;
            }
            Log.i("ScreenshotHelper", str + ": layered saved at " + fileA.getAbsolutePath());
        } catch (Exception e2) {
            e2.printStackTrace();
            Log.w("ScreenshotHelper", str + ": save failed " + e2.getMessage());
        }
    }

    private static void a(List list, Bitmap bitmap, o oVar) {
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            a((View) it.next(), arrayList);
        }
        if (arrayList.isEmpty()) {
            oVar.a(null, bitmap);
        } else {
            b(arrayList, 0, bitmap, new ArrayList(), oVar);
        }
    }

    private static void a(View view, List list) {
        if ((view instanceof SurfaceView) && view.getVisibility() == 0) {
            list.add((SurfaceView) view);
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i2 = 0; i2 < viewGroup.getChildCount(); i2++) {
                a(viewGroup.getChildAt(i2), list);
            }
        }
    }

    private static File a(Bitmap bitmap, File file) throws IOException {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            return file;
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    private static void b(File file) {
        d.post(new e(file));
    }
}
