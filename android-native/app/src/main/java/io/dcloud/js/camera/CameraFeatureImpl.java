package io.dcloud.js.camera;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.StrictMode;
import android.text.TextUtils;
import androidtranscoder.MediaTranscoder;
import androidtranscoder.format.MediaFormatStrategyPresets;
import androidx.core.content.FileProvider;
import com.dmcbig.mediapicker.entity.Media;
import com.hjq.permissions.Permission;
import io.dcloud.common.DHInterface.AbsMgr;
import io.dcloud.common.DHInterface.IApp;
import io.dcloud.common.DHInterface.IFeature;
import io.dcloud.common.DHInterface.ISysEventListener;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.adapter.io.DHFile;
import io.dcloud.common.adapter.util.ContentUriUtil;
import io.dcloud.common.adapter.util.DeviceInfo;
import io.dcloud.common.adapter.util.PermissionUtil;
import io.dcloud.common.constant.DOMException;
import io.dcloud.common.util.AppRuntime;
import io.dcloud.common.util.CompressUtil;
import io.dcloud.common.util.DCFileUriData;
import io.dcloud.common.util.Deprecated_JSUtil;
import io.dcloud.common.util.FileUtil;
import io.dcloud.common.util.JSUtil;
import io.dcloud.common.util.PdrUtil;
import io.dcloud.common.util.ThreadPool;
import io.dcloud.feature.gallery.imageedit.IMGEditActivity;
import io.dcloud.js.camera.CameraFeatureImpl;
import io.dcloud.js.camera.a;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class CameraFeatureImpl implements IFeature {
    AbsMgr a = null;
    private boolean b = false;
    private final List c = new ArrayList();

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class b extends PermissionUtil.StreamPermissionRequest {
        final /* synthetic */ String[] a;
        final /* synthetic */ IApp b;
        final /* synthetic */ IWebview c;
        final /* synthetic */ String d;

        /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
        class a implements ISysEventListener {
            final /* synthetic */ String a;
            final /* synthetic */ a.C0062a b;
            final /* synthetic */ String c;

            /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
            /* renamed from: io.dcloud.js.camera.CameraFeatureImpl$b$a$a, reason: collision with other inner class name */
            class C0061a implements MediaTranscoder.Listener {
                final /* synthetic */ Dialog a;

                C0061a(Dialog dialog) {
                    this.a = dialog;
                }

                @Override // androidtranscoder.MediaTranscoder.Listener
                public void onTranscodeCanceled() {
                    this.a.dismiss();
                    a aVar = a.this;
                    CameraFeatureImpl.this.a(aVar.b.h, aVar.a);
                    String json = DOMException.toJSON(-2, DOMException.MSG_USER_CANCEL);
                    b bVar = b.this;
                    Deprecated_JSUtil.execCallback(bVar.c, bVar.d, json, JSUtil.ERROR, true, false);
                }

                @Override // androidtranscoder.MediaTranscoder.Listener
                public void onTranscodeCompleted() {
                    this.a.dismiss();
                    a aVar = a.this;
                    CameraFeatureImpl.this.a(aVar.b.h, aVar.a);
                    a aVar2 = a.this;
                    String strConvert2RelPath = b.this.b.convert2RelPath(aVar2.c);
                    b bVar = b.this;
                    Deprecated_JSUtil.execCallback(bVar.c, bVar.d, strConvert2RelPath, JSUtil.OK, false, false);
                }

                @Override // androidtranscoder.MediaTranscoder.Listener
                public void onTranscodeFailed(Exception exc) {
                    this.a.dismiss();
                    a aVar = a.this;
                    CameraFeatureImpl.this.a(aVar.b.h, aVar.a);
                    String json = DOMException.toJSON(-99, exc.getMessage());
                    b bVar = b.this;
                    Deprecated_JSUtil.execCallback(bVar.c, bVar.d, json, JSUtil.ERROR, true, false);
                }

                @Override // androidtranscoder.MediaTranscoder.Listener
                public void onTranscodeProgress(double d) {
                }
            }

            a(String str, a.C0062a c0062a, String str2) {
                this.a = str;
                this.b = c0062a;
                this.c = str2;
            }

            @Override // io.dcloud.common.DHInterface.ISysEventListener
            public boolean onExecute(ISysEventListener.SysEventType sysEventType, Object obj) {
                Object[] objArr = (Object[]) obj;
                int iIntValue = ((Integer) objArr[0]).intValue();
                int iIntValue2 = ((Integer) objArr[1]).intValue();
                if (sysEventType == ISysEventListener.SysEventType.onActivityResult && iIntValue == io.dcloud.js.camera.a.f) {
                    if (iIntValue2 != -1) {
                        b bVar = b.this;
                        Deprecated_JSUtil.execCallback(bVar.c, bVar.d, null, JSUtil.ERROR, false, false);
                    } else {
                        if (!new File(this.a).exists() && DHFile.copyFile(ContentUriUtil.getImageAbsolutePath(b.this.b.getActivity(), ((Intent) objArr[2]).getData()), this.a) != 1) {
                            b bVar2 = b.this;
                            Deprecated_JSUtil.execCallback(bVar2.c, bVar2.d, null, JSUtil.ERROR, false, false);
                            b.this.b.unregisterSysEventListener(this, sysEventType);
                            return false;
                        }
                        Dialog dialogA = null;
                        try {
                            if (this.b.h) {
                                dialogA = io.dcloud.js.camera.b.a(b.this.c.getContext());
                                dialogA.show();
                                MediaTranscoder.getInstance().transcodeVideo(this.a, this.c, MediaFormatStrategyPresets.createAndroid720pStrategy(2, 1.0d), new C0061a(dialogA));
                            } else {
                                String strConvert2RelPath = b.this.b.convert2RelPath(this.c);
                                b bVar3 = b.this;
                                Deprecated_JSUtil.execCallback(bVar3.c, bVar3.d, strConvert2RelPath, JSUtil.OK, false, false);
                            }
                        } catch (IOException e) {
                            if (dialogA != null) {
                                dialogA.dismiss();
                            }
                            CameraFeatureImpl.this.a(this.b.h, this.a);
                            String json = DOMException.toJSON(-99, e.getMessage());
                            b bVar4 = b.this;
                            Deprecated_JSUtil.execCallback(bVar4.c, bVar4.d, json, JSUtil.ERROR, true, false);
                        }
                    }
                    b.this.b.unregisterSysEventListener(this, sysEventType);
                }
                return false;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        b(IApp iApp, String[] strArr, IApp iApp2, IWebview iWebview, String str) {
            super(iApp);
            this.a = strArr;
            this.b = iApp2;
            this.c = iWebview;
            this.d = str;
        }

        @Override // io.dcloud.common.adapter.util.PermissionUtil.Request
        public void onDenied(String str) {
            Deprecated_JSUtil.execCallback(this.c, this.d, DOMException.toJSON(11, DOMException.MSG_NO_PERMISSION), JSUtil.ERROR, true, false);
        }

        @Override // io.dcloud.common.adapter.util.PermissionUtil.Request
        public void onGranted(String str) {
            String str2;
            try {
                a.C0062a c0062aA = io.dcloud.js.camera.a.a(this.a[1], false);
                String strConvert2AbsFullPath = this.b.convert2AbsFullPath(this.c.obtainFullUrl(), c0062aA.a());
                if (JSUtil.checkOperateDirErrorAndCallback(this.c, this.d, strConvert2AbsFullPath)) {
                    Deprecated_JSUtil.execCallback(this.c, this.d, DOMException.toJSON(-5, DOMException.MSG_IO_ERROR), JSUtil.ERROR, true, false);
                    return;
                }
                if (c0062aA.h) {
                    str2 = strConvert2AbsFullPath + ".temp";
                } else {
                    str2 = strConvert2AbsFullPath;
                }
                File file = new File(str2);
                File parentFile = file.getParentFile();
                if (!parentFile.exists()) {
                    parentFile.mkdirs();
                }
                this.b.registerSysEventListener(new a(str2, c0062aA, strConvert2AbsFullPath), ISysEventListener.SysEventType.onActivityResult);
                Intent intent = new Intent("android.media.action.VIDEO_CAPTURE");
                if (c0062aA.b() != 0) {
                    intent.putExtra("android.intent.extra.durationLimit", c0062aA.b());
                }
                if (Build.VERSION.SDK_INT >= 29) {
                    intent.putExtra("output", FileProvider.getUriForFile(this.c.getContext(), this.c.getContext().getPackageName() + ".dc.fileprovider", file));
                }
                this.c.getActivity().startActivityForResult(intent, io.dcloud.js.camera.a.f);
            } catch (Exception e) {
                Deprecated_JSUtil.execCallback(this.c, this.d, DOMException.toJSON(11, e.getMessage()), JSUtil.ERROR, true, false);
            }
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class c extends PermissionUtil.StreamPermissionRequest {
        final /* synthetic */ io.dcloud.js.camera.a a;
        final /* synthetic */ String[] b;
        final /* synthetic */ IWebview c;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        c(IApp iApp, io.dcloud.js.camera.a aVar, String[] strArr, IWebview iWebview) {
            super(iApp);
            this.a = aVar;
            this.b = strArr;
            this.c = iWebview;
        }

        @Override // io.dcloud.common.adapter.util.PermissionUtil.Request
        public void onDenied(String str) {
            CameraFeatureImpl.this.b = false;
            Iterator it = CameraFeatureImpl.this.c.iterator();
            while (it.hasNext()) {
                ((PermissionUtil.Request) it.next()).onDenied(str);
            }
            CameraFeatureImpl.this.c.clear();
        }

        @Override // io.dcloud.common.adapter.util.PermissionUtil.Request
        public void onGranted(String str) {
            this.a.b();
            CameraFeatureImpl.this.b = false;
            Iterator it = CameraFeatureImpl.this.c.iterator();
            while (it.hasNext()) {
                ((PermissionUtil.Request) it.next()).onGranted(str);
            }
            CameraFeatureImpl.this.c.clear();
            String[] strArr = this.b;
            if (strArr.length >= 3) {
                Deprecated_JSUtil.execCallback(this.c, strArr[2], this.a.a(), JSUtil.OK, true, false);
            }
        }
    }

    @Override // io.dcloud.common.DHInterface.IFeature
    public void dispose(String str) {
    }

    @Override // io.dcloud.common.DHInterface.IFeature
    public String execute(IWebview iWebview, String str, String[] strArr) {
        IApp iAppObtainApp = iWebview.obtainFrameView().obtainApp();
        AppRuntime.checkPrivacyComplianceAndPrompt(iWebview.getContext(), "Camera-" + str);
        String str2 = strArr[0];
        if (Build.VERSION.SDK_INT >= 24) {
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().build());
        }
        if (str.equals("captureImage")) {
            a.C0062a c0062aA = io.dcloud.js.camera.a.a(strArr[1], true);
            String strConvert2AbsFullPath = iAppObtainApp.convert2AbsFullPath(iWebview.obtainFullUrl(), c0062aA.a());
            if (!FileUtil.checkPrivatePath(iWebview.getContext(), strConvert2AbsFullPath) && FileUtil.getPathForPublicType(strConvert2AbsFullPath) == null) {
                Deprecated_JSUtil.execCallback(iWebview, str2, DOMException.toJSON(-5, DOMException.MSG_PATH_NOT_PRIVATE_ERROR), JSUtil.ERROR, true, false);
                return null;
            }
            a aVar = new a(iAppObtainApp, iWebview, str2, strConvert2AbsFullPath, c0062aA, iAppObtainApp);
            if (this.b) {
                this.c.add(aVar);
                return null;
            }
            if (PermissionUtil.checkSelfPermission(iAppObtainApp.getActivity(), Permission.CAMERA) == 0) {
                aVar.onGranted(PermissionUtil.PMS_CAMERA);
                return null;
            }
            PermissionUtil.usePermission(iAppObtainApp.getActivity(), "camera", PermissionUtil.PMS_CAMERA, 2, aVar);
            return null;
        }
        if (str.equals("startVideoCapture")) {
            b bVar = new b(iAppObtainApp, strArr, iAppObtainApp, iWebview, str2);
            if (this.b) {
                this.c.add(bVar);
                return null;
            }
            if (PermissionUtil.checkSelfPermission(iAppObtainApp.getActivity(), Permission.CAMERA) == 0) {
                bVar.onGranted(PermissionUtil.PMS_CAMERA);
                return null;
            }
            PermissionUtil.usePermission(iAppObtainApp.getActivity(), "camera", PermissionUtil.PMS_CAMERA, 2, bVar);
            return null;
        }
        if (str.equals("getCamera")) {
            io.dcloud.js.camera.a aVar2 = new io.dcloud.js.camera.a(PdrUtil.parseInt(strArr[1], 1));
            if (PermissionUtil.checkSelfPermission(iAppObtainApp.getActivity(), Permission.CAMERA) == 0) {
                aVar2.b();
                return aVar2.a();
            }
            if (!this.b) {
                this.b = true;
                PermissionUtil.usePermission(iAppObtainApp.getActivity(), "camera", PermissionUtil.PMS_CAMERA, 2, new c(iAppObtainApp, aVar2, strArr, iWebview));
            }
        }
        return null;
    }

    @Override // io.dcloud.common.DHInterface.IFeature
    public void init(AbsMgr absMgr, String str) {
        this.a = absMgr;
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class a extends PermissionUtil.StreamPermissionRequest {
        final /* synthetic */ IWebview a;
        final /* synthetic */ String b;
        final /* synthetic */ String c;
        final /* synthetic */ a.C0062a d;
        final /* synthetic */ IApp e;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        a(IApp iApp, IWebview iWebview, String str, String str2, a.C0062a c0062a, IApp iApp2) {
            super(iApp);
            this.a = iWebview;
            this.b = str;
            this.c = str2;
            this.d = c0062a;
            this.e = iApp2;
        }

        @Override // io.dcloud.common.adapter.util.PermissionUtil.Request
        public void onDenied(String str) {
            Deprecated_JSUtil.execCallback(this.a, this.b, DOMException.toJSON(11, DOMException.MSG_NO_PERMISSION), JSUtil.ERROR, true, false);
        }

        @Override // io.dcloud.common.adapter.util.PermissionUtil.Request
        public void onGranted(String str) {
            try {
                if (JSUtil.checkOperateDirErrorAndCallback(this.a, this.b, this.c)) {
                    Deprecated_JSUtil.execCallback(this.a, this.b, DOMException.toJSON(-5, DOMException.MSG_IO_ERROR), JSUtil.ERROR, true, false);
                    return;
                }
                File file = new File(this.c);
                File parentFile = file.getParentFile();
                if (!parentFile.exists()) {
                    parentFile.mkdirs();
                }
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                DCFileUriData shareImageUri = FileUtil.getShareImageUri(this.a.getContext(), file, this.d.a(), intent);
                this.e.registerSysEventListener(new C0056a(shareImageUri), ISysEventListener.SysEventType.onActivityResult);
                intent.putExtra("output", shareImageUri.fileUri);
                this.a.getActivity().startActivityForResult(intent, io.dcloud.js.camera.a.e);
            } catch (Exception e) {
                Deprecated_JSUtil.execCallback(this.a, this.b, DOMException.toJSON(11, e.getMessage()), JSUtil.ERROR, true, false);
            }
        }

        /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
        /* renamed from: io.dcloud.js.camera.CameraFeatureImpl$a$a, reason: collision with other inner class name */
        class C0056a implements ISysEventListener {
            final /* synthetic */ DCFileUriData a;

            C0056a(DCFileUriData dCFileUriData) {
                this.a = dCFileUriData;
            }

            @Override // io.dcloud.common.DHInterface.ISysEventListener
            public boolean onExecute(ISysEventListener.SysEventType sysEventType, Object obj) {
                Object[] objArr = (Object[]) obj;
                int iIntValue = ((Integer) objArr[0]).intValue();
                int iIntValue2 = ((Integer) objArr[1]).intValue();
                if (sysEventType == ISysEventListener.SysEventType.onActivityResult && iIntValue == io.dcloud.js.camera.a.e) {
                    if (iIntValue2 == -1) {
                        ThreadPool.self().addThreadTask(new RunnableC0057a());
                    } else {
                        String json = DOMException.toJSON(11, "resultCode is wrong");
                        a aVar = a.this;
                        Deprecated_JSUtil.execCallback(aVar.a, aVar.b, json, JSUtil.ERROR, true, false);
                    }
                    a.this.e.unregisterSysEventListener(this, sysEventType);
                }
                return false;
            }

            /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
            /* renamed from: io.dcloud.js.camera.CameraFeatureImpl$a$a$a, reason: collision with other inner class name */
            class RunnableC0057a implements Runnable {

                /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
                /* renamed from: io.dcloud.js.camera.CameraFeatureImpl$a$a$a$a, reason: collision with other inner class name */
                class RunnableC0058a implements Runnable {
                    RunnableC0058a() {
                    }

                    @Override // java.lang.Runnable
                    public void run() {
                        String json = DOMException.toJSON(-5, DOMException.MSG_IO_ERROR);
                        a aVar = a.this;
                        Deprecated_JSUtil.execCallback(aVar.a, aVar.b, json, JSUtil.ERROR, true, false);
                    }
                }

                /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
                /* renamed from: io.dcloud.js.camera.CameraFeatureImpl$a$a$a$b */
                class b implements Runnable {
                    b() {
                    }

                    @Override // java.lang.Runnable
                    public void run() {
                        String json = DOMException.toJSON(-5, DOMException.MSG_IO_ERROR);
                        a aVar = a.this;
                        Deprecated_JSUtil.execCallback(aVar.a, aVar.b, json, JSUtil.ERROR, true, false);
                    }
                }

                /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
                /* renamed from: io.dcloud.js.camera.CameraFeatureImpl$a$a$a$c */
                class c implements ISysEventListener {
                    final /* synthetic */ String a;

                    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
                    /* renamed from: io.dcloud.js.camera.CameraFeatureImpl$a$a$a$c$a, reason: collision with other inner class name */
                    class RunnableC0059a implements Runnable {

                        /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
                        /* renamed from: io.dcloud.js.camera.CameraFeatureImpl$a$a$a$c$a$a, reason: collision with other inner class name */
                        class RunnableC0060a implements Runnable {
                            RunnableC0060a() {
                            }

                            @Override // java.lang.Runnable
                            public void run() {
                                c cVar = c.this;
                                a aVar = a.this;
                                Deprecated_JSUtil.execCallback(aVar.a, aVar.b, cVar.a, JSUtil.OK, false, false);
                            }
                        }

                        RunnableC0059a() {
                        }

                        @Override // java.lang.Runnable
                        public void run() {
                            a.this.e.getActivity().runOnUiThread(new RunnableC0060a());
                        }
                    }

                    c(String str) {
                        this.a = str;
                    }

                    @Override // io.dcloud.common.DHInterface.ISysEventListener
                    public boolean onExecute(ISysEventListener.SysEventType sysEventType, Object obj) {
                        Object[] objArr = (Object[]) obj;
                        int iIntValue = ((Integer) objArr[0]).intValue();
                        int iIntValue2 = ((Integer) objArr[1]).intValue();
                        if (sysEventType == ISysEventListener.SysEventType.onActivityResult && iIntValue == io.dcloud.js.camera.a.g) {
                            if (iIntValue2 == -1) {
                                ThreadPool.self().addThreadTask(new RunnableC0059a());
                            } else {
                                String json = DOMException.toJSON(11, "resultCode is wrong");
                                a aVar = a.this;
                                Deprecated_JSUtil.execCallback(aVar.a, aVar.b, json, JSUtil.ERROR, true, false);
                            }
                            a.this.e.unregisterSysEventListener(this, sysEventType);
                        }
                        return false;
                    }
                }

                RunnableC0057a() {
                }

                /* JADX INFO: Access modifiers changed from: private */
                public static /* synthetic */ void a(a.C0062a c0062a, String str, IApp iApp, final IWebview iWebview, final String str2, final String str3) throws IOException {
                    if (c0062a.h) {
                        CompressUtil.compressImage(str, str, false, iApp.getActivity());
                    }
                    iApp.getActivity().runOnUiThread(new Runnable() { // from class: io.dcloud.js.camera.CameraFeatureImpl$a$a$a$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            CameraFeatureImpl.a.C0056a.RunnableC0057a.a(iWebview, str2, str3);
                        }
                    });
                }

                @Override // java.lang.Runnable
                public void run() {
                    String strConvert2RelPath;
                    String strA;
                    DCFileUriData dCFileUriData = C0056a.this.a;
                    if (dCFileUriData.isReplace) {
                        if (DHFile.copyFile(dCFileUriData.fileReplacePath, dCFileUriData.filePath, true, false) != 1) {
                            a.this.e.getActivity().runOnUiThread(new RunnableC0058a());
                            return;
                        } else {
                            try {
                                DHFile.deleteFile(C0056a.this.a.fileReplacePath);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    C0056a c0056a = C0056a.this;
                    a aVar = a.this;
                    if (aVar.d.e) {
                        strA = io.dcloud.js.camera.b.a(c0056a.a.filePath);
                        if (TextUtils.isEmpty(strA)) {
                            a.this.e.getActivity().runOnUiThread(new b());
                            return;
                        }
                        strConvert2RelPath = a.this.e.convert2RelPath(strA);
                    } else {
                        strConvert2RelPath = aVar.e.convert2RelPath(c0056a.a.filePath);
                        strA = C0056a.this.a.filePath;
                    }
                    final String str = strA;
                    final String str2 = strConvert2RelPath;
                    JSONObject jSONObject = a.this.d.g;
                    if (jSONObject == null || !jSONObject.has("width") || !a.this.d.g.has("height")) {
                        C0056a.this.a.clear();
                        ThreadPool threadPoolSelf = ThreadPool.self();
                        a aVar2 = a.this;
                        final a.C0062a c0062a = aVar2.d;
                        final IApp iApp = aVar2.e;
                        final IWebview iWebview = aVar2.a;
                        final String str3 = aVar2.b;
                        threadPoolSelf.addThreadTask(new Runnable() { // from class: io.dcloud.js.camera.CameraFeatureImpl$a$a$a$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() throws IOException {
                                CameraFeatureImpl.a.C0056a.RunnableC0057a.a(c0062a, str, iApp, iWebview, str3, str2);
                            }
                        });
                        return;
                    }
                    Media media = new Media(C0056a.this.a.filePath, "", System.currentTimeMillis(), 1, 1L, -1001, new File(C0056a.this.a.filePath).getParent());
                    Intent intent = new Intent(a.this.e.getActivity(), (Class<?>) IMGEditActivity.class);
                    intent.putExtra("IMAGE_URI", Uri.parse(DeviceInfo.FILE_PROTOCOL + media.path));
                    intent.putExtra("IMAGE_MEDIA_ID", media.id);
                    intent.putExtra("IMAGE_INDEX", 0);
                    intent.putExtra("IMAGE_CROP", a.this.d.g.toString());
                    intent.putExtra("IMAGE_SAVE_PATH", media.path);
                    a.this.e.registerSysEventListener(new c(str2), ISysEventListener.SysEventType.onActivityResult);
                    C0056a.this.a.clear();
                    a.this.e.getActivity().startActivityForResult(intent, io.dcloud.js.camera.a.g);
                    a.this.e.getActivity().overridePendingTransition(0, 0);
                }

                /* JADX INFO: Access modifiers changed from: private */
                public static /* synthetic */ void a(IWebview iWebview, String str, String str2) {
                    Deprecated_JSUtil.execCallback(iWebview, str, str2, JSUtil.OK, false, false);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(boolean z, String str) {
        if (z) {
            try {
                if (str.endsWith(".temp")) {
                    new File(str).delete();
                }
            } catch (Exception unused) {
            }
        }
    }
}
