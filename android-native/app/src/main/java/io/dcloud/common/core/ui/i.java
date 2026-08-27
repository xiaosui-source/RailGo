package io.dcloud.common.core.ui;

import android.content.res.Resources;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import io.dcloud.base.R;
import io.dcloud.common.DHInterface.IMgr;
import io.dcloud.common.DHInterface.ITypeofAble;
import io.dcloud.common.adapter.ui.DHImageView;
import io.dcloud.common.adapter.util.AnimOptions;
import io.dcloud.common.adapter.util.Logger;
import io.dcloud.common.adapter.util.ViewOptions;
import io.dcloud.common.util.BaseInfo;
import io.dcloud.common.util.PdrUtil;
import io.dcloud.nineoldandroids.view.ViewHelper;
import java.util.Iterator;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public abstract class i {
    public static View a = null;
    public static DHImageView b = null;
    private static boolean c = false;

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class a implements Animation.AnimationListener {
        final /* synthetic */ io.dcloud.common.core.ui.b a;

        a(io.dcloud.common.core.ui.b bVar) {
            this.a = bVar;
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationEnd(Animation animation) {
            BaseInfo.sDoingAnimation = false;
            DHImageView dHImageView = i.b;
            if (dHImageView == null || dHImageView.isNativeView()) {
                DHImageView dHImageView2 = i.b;
                if (dHImageView2 == null || !dHImageView2.isNativeView()) {
                    return;
                }
                this.a.handleNativeViewByAction(i.b, 0);
                i.b = null;
                return;
            }
            DHImageView dHImageView3 = i.b;
            if (dHImageView3 != null) {
                dHImageView3.setIntercept(false);
                i.b.clearAnimation();
                i.b.setVisibility(4);
                i.b.setImageBitmap(null);
                i.b = null;
            }
            View view = i.a;
            if (view != null) {
                view.clearAnimation();
                i.a = null;
            }
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationRepeat(Animation animation) {
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationStart(Animation animation) {
            DHImageView dHImageView = i.b;
            if (dHImageView != null) {
                dHImageView.setIntercept(true);
            }
            BaseInfo.sDoingAnimation = true;
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class b implements Animation.AnimationListener {
        final /* synthetic */ io.dcloud.common.core.ui.b a;

        /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
        class a implements Runnable {
            a() {
            }

            @Override // java.lang.Runnable
            public void run() {
                DHImageView dHImageView = i.b;
                if (dHImageView != null) {
                    dHImageView.clearAnimation();
                    i.b.setVisibility(4);
                    if (i.b.isNativeView()) {
                        b.this.a.handleNativeViewByAction(i.b, 1);
                    }
                    i.b.removeNativeView();
                    i.b.setImageBitmap(null);
                    i.b.setTag(0);
                    i.b = null;
                }
                View view = i.a;
                if (view != null) {
                    view.clearAnimation();
                    i.a = null;
                }
            }
        }

        b(io.dcloud.common.core.ui.b bVar) {
            this.a = bVar;
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationEnd(Animation animation) {
            DHImageView dHImageView = i.b;
            if (dHImageView != null) {
                dHImageView.setIntercept(false);
                i.b.setNativeAnimationRuning(false);
            }
            BaseInfo.sDoingAnimation = false;
            DHImageView dHImageView2 = i.b;
            int i = (dHImageView2 == null || !dHImageView2.isNativeView()) ? 320 : 0;
            View viewObtainMainView = i.a;
            if (viewObtainMainView == null) {
                viewObtainMainView = this.a.obtainMainView();
            }
            viewObtainMainView.postDelayed(new a(), i);
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationRepeat(Animation animation) {
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationStart(Animation animation) {
            DHImageView dHImageView = i.b;
            if (dHImageView != null) {
                dHImageView.setIntercept(true);
                i.b.setNativeAnimationRuning(true);
            }
            BaseInfo.sDoingAnimation = true;
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class c implements Animation.AnimationListener {
        final /* synthetic */ io.dcloud.common.core.ui.b a;

        c(io.dcloud.common.core.ui.b bVar) {
            this.a = bVar;
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationEnd(Animation animation) {
            i.c(this.a);
            View view = i.a;
            if (view != null) {
                ViewHelper.setX(view, this.a.obtainFrameOptions().left);
                ViewHelper.setY(i.a, this.a.obtainFrameOptions().top);
                i.a.clearAnimation();
                i.a = null;
            }
            BaseInfo.sDoingAnimation = false;
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationRepeat(Animation animation) {
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationStart(Animation animation) {
            BaseInfo.sDoingAnimation = true;
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class d implements Animation.AnimationListener {
        final /* synthetic */ io.dcloud.common.core.ui.b a;

        d(io.dcloud.common.core.ui.b bVar) {
            this.a = bVar;
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationEnd(Animation animation) {
            i.c(this.a);
            View view = i.a;
            if (view != null) {
                ViewHelper.setX(view, this.a.obtainFrameOptions().left);
                ViewHelper.setY(i.a, this.a.obtainFrameOptions().top);
                i.a.clearAnimation();
                i.a = null;
            }
            BaseInfo.sDoingAnimation = false;
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationRepeat(Animation animation) {
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationStart(Animation animation) {
            BaseInfo.sDoingAnimation = true;
        }
    }

    public static boolean b(io.dcloud.common.core.ui.b bVar) {
        ViewGroup viewGroup = (ViewGroup) bVar.obtainMainView();
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (viewGroup.getChildAt(i) instanceof ITypeofAble) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void c(io.dcloud.common.core.ui.b bVar) {
        if (bVar == null || bVar.k == null) {
            return;
        }
        bVar.mWindowMgr.processEvent(IMgr.MgrType.WindowMgr, 70, null);
        bVar.k.obtainMainView().setBackgroundColor(-1);
        Iterator it = bVar.k.d().iterator();
        while (it.hasNext()) {
            io.dcloud.common.core.ui.b bVar2 = (io.dcloud.common.core.ui.b) it.next();
            if (bVar2.f()) {
                bVar2.d(false);
                bVar2.obtainMainView().setVisibility(0);
            }
        }
    }

    public static void a(io.dcloud.common.core.ui.b bVar, int i) {
        String str = bVar.getAnimOptions().mAnimType;
        String str2 = bVar.getAnimOptions().mAnimType_close;
        io.dcloud.common.core.ui.b bVar2 = (io.dcloud.common.core.ui.b) bVar.k.findFrameViewB(bVar);
        if (bVar2 == null) {
            return;
        }
        if (i == 1) {
            String closeAnimType = AnimOptions.getCloseAnimType(str2);
            if (bVar.mAccelerationType.equals("auto") && PdrUtil.isContains(closeAnimType, "slide")) {
                return;
            }
            if (bVar.mAccelerationType.equals("auto") && !PdrUtil.isEquals(closeAnimType, AnimOptions.ANIM_POP_OUT) && !BaseInfo.isDefaultAim && bVar2.mSnapshot == null) {
                return;
            }
            if (bVar.mAccelerationType.equals("none") && !PdrUtil.isEquals(closeAnimType, AnimOptions.ANIM_POP_OUT) && bVar2.mSnapshot == null) {
                return;
            }
            if (!bVar.mAccelerationType.equals("none") && bVar2.mSnapshot == null) {
                BaseInfo.sOpenedCount--;
            }
        } else {
            if (bVar.mAccelerationType.equals("auto") && PdrUtil.isContains(str2, "slide")) {
                return;
            }
            if (bVar.mAccelerationType.equals("auto") && !PdrUtil.isEquals(str, AnimOptions.ANIM_POP_IN) && !BaseInfo.isDefaultAim && bVar2.mSnapshot == null) {
                return;
            }
            if (bVar.mAccelerationType.equals("none") && !PdrUtil.isEquals(str2, AnimOptions.ANIM_POP_IN) && bVar2.mSnapshot == null) {
                return;
            }
            if (!bVar.mAccelerationType.equals("none") && bVar2.mSnapshot == null) {
                int i2 = BaseInfo.sOpenedCount + 1;
                BaseInfo.sOpenedCount = i2;
                c = i2 > 1;
            }
        }
        a = bVar2.obtainMainView();
        bVar2.k.a(bVar2, bVar);
        bVar2.chkUseCaptureAnimation(true, bVar2.hashCode(), bVar2.mSnapshot != null);
        if (bVar2.mAnimationCapture && BaseInfo.sAnimationCaptureB && !b(bVar2)) {
            Logger.e("mabo", "B页面是否启用截图动画方案:true | " + bVar2.getAnimOptions().mAnimType);
            a(i, bVar2, bVar);
        } else {
            Logger.e("mabo", "B页面是否启用截图动画方案:false | " + bVar2.getAnimOptions().mAnimType);
            b(i, bVar2, bVar);
        }
        if (BaseInfo.sOpenedCount == 0) {
            c = false;
        }
    }

    private static void b(int i, io.dcloud.common.core.ui.b bVar, io.dcloud.common.core.ui.b bVar2) throws Resources.NotFoundException {
        Animation translateAnimation;
        int i2 = bVar2.obtainApp().getInt(0);
        if (i == 0) {
            if (PdrUtil.isEquals(bVar2.getAnimOptions().mAnimType, AnimOptions.ANIM_POP_IN)) {
                if (Build.VERSION.SDK_INT >= 23) {
                    translateAnimation = AnimationUtils.loadAnimation(bVar.getContext(), R.anim.dcloud_page_open_exit);
                    a(bVar, bVar2);
                } else {
                    translateAnimation = new TranslateAnimation(bVar.obtainFrameOptions().left, (-i2) / 4, 0.0f, 0.0f);
                    translateAnimation.setDuration(bVar2.getAnimOptions().duration_show);
                    translateAnimation.setInterpolator(new DecelerateInterpolator());
                }
            } else {
                translateAnimation = new TranslateAnimation(0.0f, 0.0f, 0.0f, 0.0f);
                translateAnimation.setDuration(bVar2.getAnimOptions().duration_show);
            }
            translateAnimation.setAnimationListener(new c(bVar));
        } else {
            if (PdrUtil.isEquals(AnimOptions.getCloseAnimType(bVar2.getAnimOptions().mAnimType_close), AnimOptions.ANIM_POP_OUT)) {
                if (Build.VERSION.SDK_INT >= 23) {
                    translateAnimation = AnimationUtils.loadAnimation(bVar.getContext(), R.anim.dcloud_page_close_enter);
                    a(bVar, bVar2);
                } else {
                    translateAnimation = new TranslateAnimation((-i2) / 4, bVar2.obtainFrameOptions().left, 0.0f, 0.0f);
                    translateAnimation.setDuration(bVar2.getAnimOptions().duration_close);
                    translateAnimation.setInterpolator(new DecelerateInterpolator());
                }
            } else {
                translateAnimation = new TranslateAnimation(0.0f, 0.0f, 0.0f, 0.0f);
                translateAnimation.setDuration(bVar2.getAnimOptions().duration_close);
            }
            translateAnimation.setAnimationListener(new d(bVar));
        }
        View view = b;
        if (view == null) {
            view = a;
        }
        view.startAnimation(translateAnimation);
        bVar.k.f(bVar);
    }

    private static void a(int i, io.dcloud.common.core.ui.b bVar, io.dcloud.common.core.ui.b bVar2) throws Resources.NotFoundException {
        TranslateAnimation translateAnimation;
        int i2 = bVar2.obtainApp().getInt(0);
        DHImageView dHImageViewA = bVar2.k.a(bVar, i, c);
        b = dHImageViewA;
        if (dHImageViewA == null) {
            b(i, bVar, bVar2);
            return;
        }
        if (i == 0) {
            dHImageViewA.setTag(Integer.valueOf(bVar.hashCode()));
            if (PdrUtil.isEquals(bVar2.getAnimOptions().mAnimType, AnimOptions.ANIM_POP_IN)) {
                translateAnimation = new TranslateAnimation(bVar.obtainFrameOptions().left, (-i2) / 4, 0.0f, 0.0f);
                translateAnimation.setFillAfter(true);
                translateAnimation.setDuration(300L);
            } else {
                translateAnimation = new TranslateAnimation(0.0f, 0.0f, 0.0f, 0.0f);
                translateAnimation.setDuration(bVar2.getAnimOptions().duration_show);
            }
            translateAnimation.setInterpolator(new DecelerateInterpolator());
            translateAnimation.setAnimationListener(new a(bVar));
        } else {
            if (PdrUtil.isEquals(AnimOptions.getCloseAnimType(bVar2.getAnimOptions().mAnimType_close), AnimOptions.ANIM_POP_OUT)) {
                translateAnimation = new TranslateAnimation((-i2) / 4, bVar.obtainFrameOptions().left, 0.0f, 0.0f);
                translateAnimation.setFillAfter(true);
                translateAnimation.setDuration(360L);
            } else {
                translateAnimation = new TranslateAnimation(0.0f, 0.0f, 0.0f, 0.0f);
                translateAnimation.setDuration(bVar2.getAnimOptions().duration_close);
            }
            translateAnimation.setInterpolator(new DecelerateInterpolator());
            translateAnimation.setAnimationListener(new b(bVar));
        }
        View view = b;
        if (view == null) {
            view = a;
        }
        view.startAnimation(translateAnimation);
        bVar.k.f(bVar);
    }

    private static void a(io.dcloud.common.core.ui.b bVar, io.dcloud.common.core.ui.b bVar2) {
        ViewOptions viewOptionsObtainFrameOptions;
        if (bVar == null || bVar.k == null) {
            return;
        }
        if (bVar instanceof io.dcloud.common.core.ui.c) {
            io.dcloud.common.core.ui.c cVar = (io.dcloud.common.core.ui.c) bVar;
            if (cVar.u() != null && (viewOptionsObtainFrameOptions = cVar.u().obtainFrameOptions()) != null && !PdrUtil.isEmpty(viewOptionsObtainFrameOptions.animationAlphaBackground)) {
                bVar.k.obtainMainView().setBackgroundColor(PdrUtil.stringToColor(viewOptionsObtainFrameOptions.animationAlphaBackground));
            }
        } else if (bVar.obtainFrameOptions() != null && !PdrUtil.isEmpty(bVar.obtainFrameOptions().animationAlphaBackground)) {
            bVar.k.obtainMainView().setBackgroundColor(PdrUtil.stringToColor(bVar.obtainFrameOptions().animationAlphaBackground));
        }
        Iterator it = bVar.k.d().iterator();
        while (it.hasNext()) {
            io.dcloud.common.core.ui.b bVar3 = (io.dcloud.common.core.ui.b) it.next();
            if (bVar3 != bVar && bVar2 != bVar3 && bVar3.obtainMainView().getVisibility() == 0) {
                bVar3.d(true);
                bVar3.obtainMainView().setVisibility(4);
            }
        }
    }
}
