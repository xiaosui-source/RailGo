package io.dcloud.common.core.ui;

import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.widget.AbsoluteLayout;
import android.widget.FrameLayout;
import com.alibaba.android.bindingx.core.internal.BindingXConstants;
import com.taobao.weex.performance.WXInstanceApm;
import com.taobao.weex.ui.view.gesture.WXGesture;
import io.dcloud.common.DHInterface.IFrameView;
import io.dcloud.common.DHInterface.INativeView;
import io.dcloud.common.adapter.util.DragBean;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.common.util.BaseInfo;
import io.dcloud.common.util.JSONUtil;
import io.dcloud.common.util.PdrUtil;
import io.dcloud.nineoldandroids.animation.Animator;
import io.dcloud.nineoldandroids.animation.ValueAnimator;
import io.dcloud.nineoldandroids.view.ViewHelper;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.regex.Pattern;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class g {
    private static boolean j0 = false;
    private String A;
    private String C;
    private String D;
    private IFrameView J;
    private String K;
    private String L;
    private String M;
    private int N;
    private boolean O;
    private float P;
    private int Q;
    private int R;
    private int W;
    private DisplayMetrics X;
    private float Z;
    private float a0;
    private float b0;
    private float c0;
    private float d0;
    private float e0;
    private VelocityTracker f;
    boolean g0;
    private int h;
    private float i;
    private Pattern i0;
    private float j;
    private float k;
    private int l;
    private io.dcloud.common.core.ui.b q;
    private io.dcloud.common.core.ui.b r;
    private View t;
    private String w;
    private String y;
    private boolean a = false;
    private boolean b = false;
    private boolean c = false;
    private boolean d = false;
    private boolean e = false;
    private boolean g = true;
    private int m = 0;
    private int n = 0;
    private int o = 0;
    private int p = 0;
    private View s = null;
    private boolean u = false;
    private int v = Integer.MAX_VALUE;
    private int x = Integer.MAX_VALUE;
    private int z = Integer.MAX_VALUE;
    private int B = Integer.MAX_VALUE;
    private boolean E = false;
    private boolean F = true;
    private boolean G = true;
    private boolean H = true;
    private int I = -1;
    private boolean S = false;
    private boolean T = false;
    private boolean U = false;
    private int V = Integer.MAX_VALUE;
    private float Y = 20.0f;
    boolean f0 = true;
    private String h0 = "^([1-9]|[1-9]\\d|100)$";

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class a implements ViewTreeObserver.OnGlobalLayoutListener {
        a() {
        }

        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public void onGlobalLayout() {
            int i;
            g.this.t.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            g gVar = g.this;
            boolean z = true;
            if (gVar.d(gVar.L)) {
                i = g.this.p;
            } else {
                g gVar2 = g.this;
                int iB = gVar2.b(gVar2.t) / 2;
                Rect rect = new Rect();
                g.this.t.getGlobalVisibleRect(rect);
                if (rect.right - rect.left >= iB) {
                    i = g.this.p;
                } else {
                    i = g.this.o;
                    z = false;
                }
            }
            g gVar3 = g.this;
            int iA = gVar3.a(gVar3.t);
            g gVar4 = g.this;
            ValueAnimator valueAnimatorA = gVar4.a(gVar4.t, iA, i, z);
            if (valueAnimatorA != null) {
                valueAnimatorA.start();
            }
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class b implements ViewTreeObserver.OnGlobalLayoutListener {
        b() {
        }

        /* JADX WARN: Removed duplicated region for block: B:32:0x00e0  */
        /* JADX WARN: Removed duplicated region for block: B:53:0x0166  */
        /* JADX WARN: Removed duplicated region for block: B:63:0x019c  */
        /* JADX WARN: Removed duplicated region for block: B:66:0x01ab  */
        /* JADX WARN: Removed duplicated region for block: B:67:0x01ba  */
        /* JADX WARN: Removed duplicated region for block: B:70:0x01d3  */
        /* JADX WARN: Removed duplicated region for block: B:72:? A[RETURN, SYNTHETIC] */
        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public void onGlobalLayout() {
            /*
                Method dump skipped, instructions count: 471
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: io.dcloud.common.core.ui.g.b.onGlobalLayout():void");
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class c implements ViewTreeObserver.OnGlobalLayoutListener {
        c() {
        }

        /* JADX WARN: Removed duplicated region for block: B:54:0x0201  */
        /* JADX WARN: Removed duplicated region for block: B:57:0x0216  */
        /* JADX WARN: Removed duplicated region for block: B:69:? A[RETURN, SYNTHETIC] */
        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public void onGlobalLayout() throws org.json.JSONException {
            /*
                Method dump skipped, instructions count: 679
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: io.dcloud.common.core.ui.g.c.onGlobalLayout():void");
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class d implements Animator.AnimatorListener {
        final /* synthetic */ boolean a;
        final /* synthetic */ boolean b;
        final /* synthetic */ View c;
        final /* synthetic */ int d;

        d(boolean z, boolean z2, View view, int i) {
            this.a = z;
            this.b = z2;
            this.c = view;
            this.d = i;
        }

        @Override // io.dcloud.nineoldandroids.animation.Animator.AnimatorListener
        public void onAnimationCancel(Animator animator) {
        }

        /* JADX WARN: Removed duplicated region for block: B:57:0x0137  */
        /* JADX WARN: Removed duplicated region for block: B:62:0x0149  */
        /* JADX WARN: Removed duplicated region for block: B:64:0x0152  */
        /* JADX WARN: Removed duplicated region for block: B:68:0x01ae  */
        @Override // io.dcloud.nineoldandroids.animation.Animator.AnimatorListener
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public void onAnimationEnd(io.dcloud.nineoldandroids.animation.Animator r14) throws org.json.JSONException {
            /*
                Method dump skipped, instructions count: 529
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: io.dcloud.common.core.ui.g.d.onAnimationEnd(io.dcloud.nineoldandroids.animation.Animator):void");
        }

        @Override // io.dcloud.nineoldandroids.animation.Animator.AnimatorListener
        public void onAnimationRepeat(Animator animator) {
        }

        @Override // io.dcloud.nineoldandroids.animation.Animator.AnimatorListener
        public void onAnimationStart(Animator animator) {
            g.g(g.this);
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class e implements ValueAnimator.AnimatorUpdateListener {
        final /* synthetic */ View a;

        e(View view) {
            this.a = view;
        }

        @Override // io.dcloud.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            ViewGroup.LayoutParams layoutParams = this.a.getLayoutParams();
            View view = this.a;
            if (view instanceof INativeView) {
                g.this.b(view, ((Integer) valueAnimator.getAnimatedValue()).intValue());
                this.a.requestLayout();
                this.a.invalidate();
            } else {
                if (layoutParams instanceof FrameLayout.LayoutParams) {
                    ViewHelper.setX(view, ((Float) valueAnimator.getAnimatedValue()).floatValue());
                    return;
                }
                if (layoutParams instanceof AbsoluteLayout.LayoutParams) {
                    try {
                        ViewHelper.setX(this.a, ((Integer) valueAnimator.getAnimatedValue()).intValue());
                    } catch (Exception unused) {
                        ViewHelper.setX(this.a, ((Float) valueAnimator.getAnimatedValue()).floatValue());
                    }
                    this.a.requestLayout();
                }
            }
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class f implements ViewTreeObserver.OnGlobalLayoutListener {
        f() {
        }

        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public void onGlobalLayout() throws JSONException {
            int iB;
            int i;
            boolean z;
            int iB2;
            g.this.s.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            g gVar = g.this;
            int iB3 = 0;
            if (!gVar.d(gVar.L)) {
                View view = (View) g.this.s.getParent();
                int[] iArr = new int[2];
                int[] iArr2 = new int[2];
                view.getLocationOnScreen(iArr);
                g.this.s.getLocationOnScreen(new int[2]);
                g.this.t.getLocationOnScreen(iArr2);
                int width = iArr[0] + view.getWidth();
                g.this.s.getWidth();
                int i2 = iArr2[0];
                g gVar2 = g.this;
                int iB4 = i2 + gVar2.b(gVar2.t);
                int i3 = iArr2[0];
                int i4 = iArr[0];
                if (i3 <= i4 && i4 < iB4) {
                    int i5 = iB4 - i4;
                    if (i5 > 0) {
                        g gVar3 = g.this;
                        if (i5 < gVar3.b(gVar3.t)) {
                            g gVar4 = g.this;
                            if (i5 <= gVar4.b(gVar4.t) / 2) {
                                g gVar5 = g.this;
                                iB2 = gVar5.b(gVar5.t);
                                i = -iB2;
                                iB3 = i;
                                iB = 0;
                                z = true;
                            } else {
                                g gVar6 = g.this;
                                iB = gVar6.b(gVar6.t);
                            }
                        }
                    }
                    g gVar7 = g.this;
                    if (i5 == gVar7.b(gVar7.t)) {
                        g.this.a(false, true);
                        g gVar8 = g.this;
                        View view2 = gVar8.s;
                        g gVar9 = g.this;
                        gVar8.a(view2, gVar9.b(gVar9.t));
                        g gVar10 = g.this;
                        gVar10.a(gVar10.t, 0);
                        boolean unused = g.j0 = false;
                        return;
                    }
                    g.this.a(true, true);
                    g gVar11 = g.this;
                    gVar11.a(gVar11.s, 0);
                    g gVar12 = g.this;
                    View view3 = gVar12.t;
                    g gVar13 = g.this;
                    gVar12.a(view3, -gVar13.b(gVar13.t));
                    boolean unused2 = g.j0 = false;
                    return;
                }
                if (i3 >= width || width > iB4) {
                    g.this.a(true, true);
                    g gVar14 = g.this;
                    gVar14.a(gVar14.s, 0);
                    g gVar15 = g.this;
                    gVar15.a(gVar15.t, g.this.Q);
                    boolean unused3 = g.j0 = false;
                    return;
                }
                int i6 = width - i3;
                if (i6 > 0) {
                    g gVar16 = g.this;
                    if (i6 < gVar16.b(gVar16.t)) {
                        g gVar17 = g.this;
                        if (i6 <= gVar17.b(gVar17.t) / 2) {
                            i = g.this.Q;
                            iB3 = i;
                            iB = 0;
                            z = true;
                        } else {
                            g gVar18 = g.this;
                            int i7 = -gVar18.b(gVar18.t);
                            int i8 = g.this.Q;
                            g gVar19 = g.this;
                            iB = i7;
                            iB3 = i8 - gVar19.b(gVar19.t);
                        }
                    }
                }
                g gVar20 = g.this;
                if (i6 != gVar20.b(gVar20.t)) {
                    g.this.a(true, true);
                    g gVar21 = g.this;
                    gVar21.a(gVar21.s, 0);
                    g gVar22 = g.this;
                    gVar22.a(gVar22.t, g.this.Q);
                    boolean unused4 = g.j0 = false;
                    return;
                }
                g.this.a(false, true);
                g gVar23 = g.this;
                View view4 = gVar23.s;
                g gVar24 = g.this;
                gVar23.a(view4, -gVar24.b(gVar24.t));
                g gVar25 = g.this;
                View view5 = gVar25.t;
                int i9 = g.this.Q;
                g gVar26 = g.this;
                gVar25.a(view5, i9 - gVar26.b(gVar26.t));
                boolean unused5 = g.j0 = false;
                return;
                z = false;
            } else if ("right".equals(g.this.L)) {
                i = g.this.Q;
                iB3 = i;
                iB = 0;
                z = true;
            } else {
                if ("left".equals(g.this.L)) {
                    g gVar27 = g.this;
                    iB2 = gVar27.b(gVar27.t);
                    i = -iB2;
                    iB3 = i;
                }
                iB = 0;
                z = true;
            }
            g gVar28 = g.this;
            int iA = gVar28.a(gVar28.s);
            if (iA != 0) {
                g gVar29 = g.this;
                ValueAnimator valueAnimatorA = gVar29.a(gVar29.s, iA, iB, z, true);
                if (valueAnimatorA != null) {
                    valueAnimatorA.start();
                }
            }
            g gVar30 = g.this;
            int iA2 = gVar30.a(gVar30.t);
            g gVar31 = g.this;
            ValueAnimator valueAnimatorA2 = gVar31.a(gVar31.t, iA2, iB3, z, true);
            if (valueAnimatorA2 != null) {
                valueAnimatorA2.start();
            }
        }
    }

    public g(IFrameView iFrameView, Context context) {
        j0 = false;
        this.W = 0;
        if (iFrameView instanceof io.dcloud.common.core.ui.b) {
            this.q = (io.dcloud.common.core.ui.b) iFrameView;
            if (this.X == null) {
                DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
                this.X = displayMetrics;
                int i = displayMetrics.widthPixels;
                this.Q = i;
                this.R = i;
            }
        }
        this.h = ViewConfiguration.get(context).getScaledTouchSlop();
        this.i0 = Pattern.compile(this.h0);
    }

    static /* synthetic */ int g(g gVar) {
        int i = gVar.W;
        gVar.W = i + 1;
        return i;
    }

    static /* synthetic */ int h(g gVar) {
        int i = gVar.W;
        gVar.W = i - 1;
        return i;
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x000f, code lost:
    
        if (r5 != 3) goto L38;
     */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0048 A[PHI: r2
      0x0048: PHI (r2v16 int) = (r2v15 int), (r2v19 int) binds: [B:12:0x003e, B:15:0x0046] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0051  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0072 A[PHI: r2
      0x0072: PHI (r2v9 int) = (r2v8 int), (r2v12 int) binds: [B:26:0x0068, B:29:0x0070] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x007b  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0082 A[PHI: r0 r2
      0x0082: PHI (r0v8 float) = (r0v7 float), (r0v7 float), (r0v13 float), (r0v13 float) binds: [B:32:0x0078, B:35:0x0080, B:18:0x004e, B:21:0x0056] A[DONT_GENERATE, DONT_INLINE]
      0x0082: PHI (r2v13 int) = (r2v10 int), (r2v11 int), (r2v17 int), (r2v18 int) binds: [B:32:0x0078, B:35:0x0080, B:18:0x004e, B:21:0x0056] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private boolean d(android.view.MotionEvent r5) {
        /*
            r4 = this;
            float r0 = r5.getRawX()
            int r5 = r5.getAction()
            r1 = 1
            if (r5 == r1) goto L90
            r2 = 2
            if (r5 == r2) goto L13
            r0 = 3
            if (r5 == r0) goto L90
            goto L8f
        L13:
            float r5 = r4.i
            float r5 = r0 - r5
            int r5 = (int) r5
            float r5 = (float) r5
            r4.i = r0
            float r5 = r4.a(r5)
            android.view.View r0 = r4.s
            int r0 = r4.a(r0)
            float r0 = (float) r0
            float r0 = r0 + r5
            android.view.View r2 = r4.t
            int r2 = r4.a(r2)
            float r2 = (float) r2
            float r5 = r5 + r2
            java.lang.String r2 = r4.L
            java.lang.String r3 = "right"
            boolean r2 = r3.equals(r2)
            if (r2 == 0) goto L59
            int r2 = r4.n
            float r3 = (float) r2
            int r3 = (r0 > r3 ? 1 : (r0 == r3 ? 0 : -1))
            if (r3 < 0) goto L41
            goto L48
        L41:
            int r2 = r4.m
            float r3 = (float) r2
            int r3 = (r0 > r3 ? 1 : (r0 == r3 ? 0 : -1))
            if (r3 > 0) goto L49
        L48:
            float r0 = (float) r2
        L49:
            int r2 = r4.p
            float r3 = (float) r2
            int r3 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            if (r3 < 0) goto L51
            goto L82
        L51:
            int r2 = r4.o
            float r3 = (float) r2
            int r3 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            if (r3 > 0) goto L83
            goto L82
        L59:
            java.lang.String r2 = r4.L
            java.lang.String r3 = "left"
            boolean r2 = r3.equals(r2)
            if (r2 == 0) goto L83
            int r2 = r4.m
            float r3 = (float) r2
            int r3 = (r0 > r3 ? 1 : (r0 == r3 ? 0 : -1))
            if (r3 < 0) goto L6b
            goto L72
        L6b:
            int r2 = r4.n
            float r3 = (float) r2
            int r3 = (r0 > r3 ? 1 : (r0 == r3 ? 0 : -1))
            if (r3 > 0) goto L73
        L72:
            float r0 = (float) r2
        L73:
            int r2 = r4.o
            float r3 = (float) r2
            int r3 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            if (r3 < 0) goto L7b
            goto L82
        L7b:
            int r2 = r4.p
            float r3 = (float) r2
            int r3 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            if (r3 > 0) goto L83
        L82:
            float r5 = (float) r2
        L83:
            android.view.View r2 = r4.s
            int r0 = (int) r0
            r4.b(r2, r0)
            android.view.View r0 = r4.t
            int r5 = (int) r5
            r4.b(r0, r5)
        L8f:
            return r1
        L90:
            r5 = 0
            r4.g = r5
            boolean r0 = r4.H
            if (r0 == 0) goto L9a
            r4.a()
        L9a:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.common.core.ui.g.d(android.view.MotionEvent):boolean");
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x000f, code lost:
    
        if (r4 != 3) goto L26;
     */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0053 A[PHI: r4
      0x0053: PHI (r4v11 int) = (r4v9 int), (r4v10 int), (r4v13 int), (r4v14 int) binds: [B:20:0x0049, B:23:0x0051, B:12:0x002f, B:15:0x0037] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private boolean f(android.view.MotionEvent r4) {
        /*
            r3 = this;
            float r0 = r4.getRawX()
            int r4 = r4.getAction()
            r1 = 1
            if (r4 == r1) goto L5b
            r2 = 2
            if (r4 == r2) goto L12
            r0 = 3
            if (r4 == r0) goto L5b
            goto L5a
        L12:
            float r4 = r3.i
            float r4 = r0 - r4
            r3.i = r0
            android.view.View r0 = r3.t
            int r0 = r3.a(r0)
            float r0 = (float) r0
            float r0 = r0 + r4
            java.lang.String r4 = r3.L
            java.lang.String r2 = "right"
            boolean r4 = r2.equals(r4)
            if (r4 == 0) goto L3a
            int r4 = r3.p
            float r2 = (float) r4
            int r2 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r2 <= 0) goto L32
            goto L53
        L32:
            int r4 = r3.o
            float r2 = (float) r4
            int r2 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r2 >= 0) goto L54
            goto L53
        L3a:
            java.lang.String r4 = r3.L
            java.lang.String r2 = "left"
            boolean r4 = r2.equals(r4)
            if (r4 == 0) goto L54
            int r4 = r3.o
            float r2 = (float) r4
            int r2 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r2 <= 0) goto L4c
            goto L53
        L4c:
            int r4 = r3.p
            float r2 = (float) r4
            int r2 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r2 >= 0) goto L54
        L53:
            float r0 = (float) r4
        L54:
            android.view.View r4 = r3.t
            int r0 = (int) r0
            r3.b(r4, r0)
        L5a:
            return r1
        L5b:
            r4 = 0
            r3.g = r4
            boolean r0 = r3.H
            if (r0 == 0) goto L65
            r3.c()
        L65:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.common.core.ui.g.f(android.view.MotionEvent):boolean");
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x000f, code lost:
    
        if (r4 != 3) goto L26;
     */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0057 A[PHI: r4
      0x0057: PHI (r4v12 int) = (r4v10 int), (r4v11 int), (r4v14 int), (r4v15 int) binds: [B:20:0x004d, B:23:0x0055, B:12:0x0033, B:15:0x003b] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private boolean g(android.view.MotionEvent r4) {
        /*
            r3 = this;
            float r0 = r4.getRawX()
            int r4 = r4.getAction()
            r1 = 1
            if (r4 == r1) goto L5f
            r2 = 2
            if (r4 == r2) goto L12
            r0 = 3
            if (r4 == r0) goto L5f
            goto L5e
        L12:
            float r4 = r3.i
            float r4 = r0 - r4
            r3.i = r0
            float r4 = r3.a(r4)
            android.view.View r0 = r3.s
            int r0 = r3.a(r0)
            float r0 = (float) r0
            float r0 = r0 + r4
            java.lang.String r4 = r3.L
            java.lang.String r2 = "right"
            boolean r4 = r2.equals(r4)
            if (r4 == 0) goto L3e
            int r4 = r3.n
            float r2 = (float) r4
            int r2 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r2 <= 0) goto L36
            goto L57
        L36:
            int r4 = r3.m
            float r2 = (float) r4
            int r2 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r2 >= 0) goto L58
            goto L57
        L3e:
            java.lang.String r4 = r3.L
            java.lang.String r2 = "left"
            boolean r4 = r2.equals(r4)
            if (r4 == 0) goto L58
            int r4 = r3.m
            float r2 = (float) r4
            int r2 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r2 <= 0) goto L50
            goto L57
        L50:
            int r4 = r3.n
            float r2 = (float) r4
            int r2 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r2 >= 0) goto L58
        L57:
            float r0 = (float) r4
        L58:
            android.view.View r4 = r3.s
            int r0 = (int) r0
            r3.b(r4, r0)
        L5e:
            return r1
        L5f:
            r4 = 0
            r3.g = r4
            boolean r0 = r3.H
            if (r0 == 0) goto L69
            r3.d()
        L69:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.common.core.ui.g.g(android.view.MotionEvent):boolean");
    }

    private boolean h() {
        this.m = a(this.s);
        if ("right".equals(this.L)) {
            int i = this.v;
            if (Integer.MAX_VALUE != i) {
                this.n = i;
                return this.m != i;
            }
            this.n = this.Q;
            View view = this.t;
            if (view == null) {
                if (view != null || this.m >= 0) {
                    return true;
                }
                this.n = 0;
                return true;
            }
            if ("bounce".equalsIgnoreCase(this.D)) {
                this.n = this.m + (b(this.s) / 2);
                return true;
            }
            if (b(this.t) >= this.Q) {
                return true;
            }
            int iA = a(this.s);
            int iA2 = a(this.t);
            int iA3 = a(this.t) + b(this.t);
            if (iA2 == 0 && iA3 == iA) {
                return false;
            }
            boolean z = this.G;
            if (!z && iA2 == 0) {
                this.n = b(this.t);
                return true;
            }
            if (z && iA3 == 0) {
                this.n = b(this.t);
                return true;
            }
            if (this.Q != iA3) {
                return true;
            }
            this.n = 0;
            return true;
        }
        if (!"left".equals(this.L)) {
            return true;
        }
        int i2 = this.v;
        if (Integer.MAX_VALUE != i2) {
            this.n = i2;
            return this.m != i2;
        }
        this.n = -b(this.s);
        View view2 = this.t;
        if (view2 == null) {
            if (view2 != null || this.m <= 0) {
                return true;
            }
            this.n = 0;
            return true;
        }
        if ("bounce".equalsIgnoreCase(this.D)) {
            this.n = this.m - (b(this.s) / 2);
            return true;
        }
        if (b(this.t) >= this.Q) {
            return true;
        }
        int iA4 = a(this.s) + b(this.s);
        int iA5 = a(this.t);
        int iA6 = a(this.t) + b(this.t);
        int i3 = this.Q;
        if (i3 == iA6 && iA5 == iA4) {
            return false;
        }
        boolean z2 = this.G;
        if (!z2 && i3 == iA6) {
            this.n = -b(this.t);
            return true;
        }
        if (z2 && i3 == iA5) {
            this.n = -b(this.t);
            return true;
        }
        if (iA5 != 0) {
            return true;
        }
        this.n = 0;
        return true;
    }

    private void i() {
        float f2 = this.m;
        this.Z = f2;
        float f3 = this.n;
        this.a0 = f3;
        this.c0 = f2;
        this.b0 = f2;
        float fAbs = Math.abs(f3 - f2);
        this.d0 = fAbs;
        this.e0 = (fAbs * this.Y) / 100.0f;
        this.f0 = true;
        this.g0 = false;
    }

    private boolean j() {
        return "right".equals(this.L) ? a(this.t) != 0 : ("left".equals(this.L) && a(this.t) + b(this.t) == this.Q) ? false : true;
    }

    private void k() {
        float fA = a(this.s);
        this.c0 = fA;
        if (this.f0) {
            float f2 = this.Z;
            if (fA == f2) {
                this.g0 = true;
                this.f0 = false;
                this.b0 = f2;
                a(WXGesture.MOVE, false, WXInstanceApm.VALUE_ERROR_CODE_DEFAULT);
                return;
            }
        }
        if (Math.abs(fA - this.b0) >= this.e0) {
            this.f0 = true;
            this.g0 = true;
            a(WXGesture.MOVE, false, "" + ((int) ((Math.abs(this.c0 - this.Z) / this.d0) * 100.0f)));
            this.b0 = this.c0;
            return;
        }
        if (this.g0) {
            float f3 = this.c0;
            float f4 = this.a0;
            if (f3 == f4) {
                this.g0 = false;
                this.f0 = true;
                this.b0 = f4;
                a(WXGesture.MOVE, false, "100");
            }
        }
    }

    public HashMap e() {
        io.dcloud.common.core.ui.b bVar = this.q;
        if (bVar == null || bVar.obtainFrameOptions() == null) {
            return null;
        }
        return this.q.obtainFrameOptions().dragData;
    }

    public void c(boolean z) {
        this.a = z;
    }

    public boolean c(MotionEvent motionEvent) {
        View view;
        View view2;
        if (!this.g) {
            return false;
        }
        if (motionEvent.getPointerCount() > 1) {
            return true;
        }
        if (BaseInfo.sDoingAnimation) {
            if (this.U) {
                return false;
            }
            this.U = true;
            motionEvent.setAction(3);
        }
        if (this.a && !this.g) {
            return true;
        }
        if (this.f == null) {
            this.f = VelocityTracker.obtain();
        }
        if (this.I == -1 || j0) {
            return false;
        }
        if (2 == motionEvent.getAction()) {
            k();
            if ("left".equals(this.L)) {
                if (motionEvent.getRawX() < this.i) {
                    this.f.addMovement(motionEvent);
                }
            } else if ("right".equals(this.L) && motionEvent.getRawX() > this.i) {
                this.f.addMovement(motionEvent);
            }
        }
        if (1 == motionEvent.getAction() || 3 == motionEvent.getAction()) {
            this.f.addMovement(motionEvent);
        }
        if (this.T) {
            return e(motionEvent);
        }
        boolean z = this.E;
        if (z && this.F && this.G) {
            if ((!a(this.q) && !a(this.r)) || ((view2 = this.t) != null && (view2 instanceof INativeView))) {
                return d(motionEvent);
            }
        } else if (z && !this.F && this.G) {
            if ((!a(this.q) && !a(this.r)) || ((view = this.t) != null && (view instanceof INativeView))) {
                return f(motionEvent);
            }
        } else if (z && !this.G && this.F) {
            if (!a(this.q) && !a(this.r)) {
                return g(motionEvent);
            }
        } else if (!z && this.F && !a(this.q)) {
            return g(motionEvent);
        }
        return true;
    }

    private boolean e(String str) throws JSONException {
        JSONObject jSONObject;
        JSONObject jSONObject2;
        this.s = this.q.obtainMainView();
        this.v = Integer.MAX_VALUE;
        this.x = Integer.MAX_VALUE;
        this.w = null;
        this.y = null;
        HashMap mapE = e();
        if (mapE == null || !mapE.containsKey(str)) {
            return false;
        }
        DragBean dragBean = (DragBean) mapE.get(str);
        if (dragBean != null) {
            this.K = dragBean.dragCbId;
            this.J = dragBean.dragCallBackWebView;
            JSONObject jSONObject3 = dragBean.dragCurrentViewOp;
            try {
                this.M = JSONUtil.getString(jSONObject3, "direction");
                if (jSONObject3.has("moveMode")) {
                    String string = JSONUtil.getString(jSONObject3, "moveMode");
                    this.D = string;
                    this.F = "followFinger".equalsIgnoreCase(string) || "follow".equalsIgnoreCase(this.D) || "bounce".equalsIgnoreCase(this.D);
                }
                this.Y = 20.0f;
                if (jSONObject3.has("callbackStep")) {
                    try {
                        if (this.i0.matcher(jSONObject3.getString("callbackStep")).find()) {
                            this.Y = Integer.valueOf(r2).intValue();
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
                if (jSONObject3.has("over") && (jSONObject2 = JSONUtil.getJSONObject(jSONObject3, "over")) != null) {
                    if (jSONObject2.has("left")) {
                        this.v = PdrUtil.parseInt(JSONUtil.getString(jSONObject2, "left"), this.Q, Integer.MAX_VALUE);
                    }
                    if (jSONObject2.has("action")) {
                        this.w = JSONUtil.getString(jSONObject2, "action");
                    }
                }
                if (jSONObject3.has(BindingXConstants.STATE_CANCEL) && (jSONObject = JSONUtil.getJSONObject(jSONObject3, BindingXConstants.STATE_CANCEL)) != null) {
                    if (jSONObject.has("left")) {
                        this.x = PdrUtil.parseInt(JSONUtil.getString(jSONObject, "left"), this.Q, Integer.MAX_VALUE);
                    }
                    if (jSONObject.has("action")) {
                        this.y = JSONUtil.getString(jSONObject, "action");
                    }
                }
            } catch (Exception e3) {
                e3.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public void b(MotionEvent motionEvent) {
        DisplayMetrics displayMetrics = this.X;
        if (displayMetrics != null) {
            this.Q = displayMetrics.widthPixels;
        }
        this.P = motionEvent.getRawX();
        float rawX = motionEvent.getRawX();
        this.i = rawX;
        this.k = rawX;
        this.j = motionEvent.getRawY();
        this.g = false;
        this.E = false;
        this.u = false;
        this.U = false;
        this.I = -1;
        this.q.obtainWebView().loadUrl("javascript:window.__needNotifyNative__=true;");
        this.q.obtainWebView().setWebviewProperty("needTouchEvent", AbsoluteConst.FALSE);
        this.O = false;
        this.N = this.q.obtainWebView().obtainWindowView().getScrollY();
        if (this.f == null) {
            this.f = VelocityTracker.obtain();
        }
        this.f.addMovement(motionEvent);
    }

    public boolean a(MotionEvent motionEvent) {
        View view;
        View view2;
        String str;
        if (this.a) {
            return true;
        }
        if (e() != null && !BaseInfo.sDoingAnimation && !j0) {
            int actionMasked = motionEvent.getActionMasked();
            float rawX = motionEvent.getRawX();
            float rawY = motionEvent.getRawY();
            if (actionMasked == 0 && motionEvent.getEdgeFlags() != 0) {
                return false;
            }
            if (actionMasked == 0) {
                b(motionEvent);
            } else if (actionMasked == 1) {
                this.u = false;
                this.I = -1;
            } else if (actionMasked == 2) {
                if (this.N != this.q.obtainWebView().obtainWindowView().getScrollY()) {
                    this.O = true;
                }
                if (this.O) {
                    return false;
                }
                float f2 = rawX - this.i;
                if (Math.abs(f2) >= Math.abs(rawY - this.j)) {
                    if (this.h * 3 <= Math.abs(motionEvent.getRawX() - this.P) && AbsoluteConst.FALSE.equals(this.q.obtainWebView().getWebviewProperty("needTouchEvent"))) {
                        if (f2 >= 0.0f) {
                            str = "right";
                            if (!this.u && e("right")) {
                                this.L = "right";
                                this.g = true;
                                this.u = true;
                            }
                        } else {
                            str = "left";
                            if (!this.u && e("left")) {
                                this.L = "left";
                                this.g = true;
                                this.u = true;
                            }
                        }
                        if (-1 == this.I) {
                            this.i = rawX;
                            View viewA = a(str);
                            if (viewA != null) {
                                HashMap mapE = e();
                                String strC = c(str);
                                if (mapE != null && mapE.containsKey(strC)) {
                                    a(((DragBean) mapE.get(strC)).dragBindViewOp);
                                }
                                this.L = str;
                                this.g = true;
                                this.t = viewA;
                                this.T = true;
                                this.I = 1;
                            } else {
                                b(this.L);
                            }
                        }
                    }
                }
            }
            if (this.g) {
                if (this.T) {
                    this.g = j();
                } else {
                    boolean z = this.E;
                    if (z && this.F && this.G) {
                        if ((!a(this.q) && !a(this.r)) || ((view2 = this.t) != null && (view2 instanceof INativeView))) {
                            this.g = g();
                        }
                    } else if (z && !this.F && this.G) {
                        if ((!a(this.q) && !a(this.r)) || ((view = this.t) != null && (view instanceof INativeView))) {
                            this.g = f();
                        }
                    } else if (!z && this.F) {
                        if (!a(this.q)) {
                            h();
                        }
                    } else if (z && !this.G && this.F) {
                        if (!a(this.q) && !a(this.r)) {
                            this.g = h();
                        }
                    } else {
                        this.g = false;
                    }
                }
            }
            if (this.g) {
                if (!a(this.q)) {
                    this.b = this.q.obtainWebView().obtainWindowView().isVerticalScrollBarEnabled();
                    this.c = this.q.obtainWebView().obtainWindowView().isHorizontalScrollBarEnabled();
                    this.q.obtainWebView().obtainWindowView().setVerticalScrollBarEnabled(false);
                    this.q.obtainWebView().obtainWindowView().setHorizontalScrollBarEnabled(false);
                }
                View view3 = this.t;
                if (view3 != null && (view3 instanceof com.dcloud.android.widget.AbsoluteLayout)) {
                    ((com.dcloud.android.widget.AbsoluteLayout) view3).getDrag().c(true);
                    if (!a(this.r)) {
                        this.d = this.r.obtainWebView().obtainWindowView().isVerticalScrollBarEnabled();
                        this.e = this.r.obtainWebView().obtainWindowView().isHorizontalScrollBarEnabled();
                        this.r.obtainWebView().obtainWindowView().setVerticalScrollBarEnabled(false);
                        this.r.obtainWebView().obtainWindowView().setHorizontalScrollBarEnabled(false);
                    }
                }
                a("start", false, WXInstanceApm.VALUE_ERROR_CODE_DEFAULT);
                i();
            }
            return this.g;
        }
        this.g = false;
        return false;
    }

    private int b(String str) {
        View view;
        DragBean dragBean;
        int i = this.I;
        if (i != -1) {
            return i;
        }
        this.r = null;
        this.t = null;
        HashMap mapE = e();
        if (mapE != null && mapE.containsKey(str) && (dragBean = (DragBean) mapE.get(str)) != null) {
            a(dragBean.dragBindViewOp);
            IFrameView iFrameView = dragBean.dragBindWebView;
            if (iFrameView != null && (iFrameView instanceof io.dcloud.common.core.ui.b)) {
                this.E = true;
                io.dcloud.common.core.ui.b bVar = (io.dcloud.common.core.ui.b) iFrameView;
                this.r = bVar;
                this.t = bVar.obtainMainView();
            } else {
                View view2 = dragBean.nativeView;
                this.t = view2;
                if (view2 != null) {
                    this.E = true;
                }
            }
        }
        if (a(this.r) && ((view = this.t) == null || !(view instanceof INativeView))) {
            this.I = 0;
        } else {
            if (this.t.getVisibility() != 0) {
                this.r = null;
                this.t = null;
                return 0;
            }
            if (this.t.getParent() == null && (this.s.getParent() instanceof FrameLayout) && !(this.t instanceof INativeView)) {
                this.r.pushToViewStack();
            }
            if (!a(this.s, this.t) && !(this.t instanceof INativeView)) {
                this.r = null;
                this.t = null;
                return 0;
            }
            if (!(this.t instanceof INativeView) && this.G && this.s.getParent() != this.t.getParent()) {
                this.I = 0;
                return 0;
            }
            View view3 = this.t;
            if (view3 instanceof INativeView) {
                view3.bringToFront();
            }
            int iA = a(this.t);
            int iA2 = a(this.s);
            if (iA2 == 0) {
                int width = this.s.getWidth();
                int i2 = this.Q;
                if (width == i2 && (iA >= i2 || iA <= (-b(this.t)))) {
                    this.V = iA;
                    boolean z = this.F;
                    if (z && this.G) {
                        if ("right".equals(str)) {
                            this.l = iA2 - b(this.t);
                        } else if ("left".equals(str)) {
                            this.l = iA2 + this.s.getWidth();
                        }
                        b(this.t, this.l);
                    } else if (!z && this.G) {
                        if ("right".equals(str)) {
                            this.l = -b(this.t);
                        } else if ("left".equals(str)) {
                            this.l = this.s.getWidth();
                        }
                        b(this.t, this.l);
                    }
                }
            }
            this.I = 1;
        }
        return this.I;
    }

    private boolean f() {
        this.o = a(this.t);
        if ("right".equals(this.L)) {
            int i = this.z;
            if (Integer.MAX_VALUE != i) {
                this.p = i;
                return this.o != i;
            }
            this.p = b(this.t);
            int i2 = this.o;
            if (i2 == 0 || i2 == this.Q) {
                return false;
            }
            if ("bounce".equalsIgnoreCase(this.D)) {
                this.p = this.o + (b(this.s) / 2);
                return true;
            }
            if (this.o >= 0) {
                return true;
            }
            this.p = 0;
            return true;
        }
        if (!"left".equals(this.L)) {
            return true;
        }
        int i3 = this.z;
        if (Integer.MAX_VALUE != i3) {
            this.p = i3;
            return this.o != i3;
        }
        int iB = b(this.t);
        this.p = -iB;
        if ("bounce".equalsIgnoreCase(this.D)) {
            this.p = this.o - (b(this.s) / 2);
            return true;
        }
        int i4 = this.Q;
        if (iB < i4) {
            int iA = a(this.t) + b(this.t);
            if (iA == this.Q || iA == 0) {
                return false;
            }
            Rect rect = new Rect();
            this.t.getGlobalVisibleRect(rect);
            int i5 = this.Q;
            if (i5 == rect.left) {
                this.p = i5 - iB;
                return true;
            }
            if (rect.right != 0) {
                return true;
            }
            this.p = 0;
            return true;
        }
        if (iB != i4 || this.o <= 0) {
            return true;
        }
        this.p = 0;
        return true;
    }

    private boolean g() {
        return h() && f();
    }

    private void d() {
        this.S = true;
        j0 = true;
        this.s.requestLayout();
        this.s.getViewTreeObserver().addOnGlobalLayoutListener(new b());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean d(String str) {
        VelocityTracker velocityTracker = this.f;
        if (velocityTracker == null) {
            return false;
        }
        velocityTracker.computeCurrentVelocity(1000, 1000.0f);
        float xVelocity = velocityTracker.getXVelocity();
        this.f.clear();
        this.f.recycle();
        this.f = null;
        return Math.abs(xVelocity) >= 200.0f;
    }

    /* JADX WARN: Code restructure failed: missing block: B:8:0x0015, code lost:
    
        if (r0 != 3) goto L84;
     */
    /* JADX WARN: Removed duplicated region for block: B:32:0x006d  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x009d  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00b1 A[PHI: r10 r12
      0x00b1: PHI (r10v12 int) = (r10v11 int), (r10v19 int) binds: [B:46:0x00af, B:38:0x008c] A[DONT_GENERATE, DONT_INLINE]
      0x00b1: PHI (r12v1 int) = (r12v0 int), (r12v3 int) binds: [B:46:0x00af, B:38:0x008c] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00bb  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00dc  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x00ff  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0123  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0133  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private boolean e(android.view.MotionEvent r15) {
        /*
            Method dump skipped, instructions count: 354
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.common.core.ui.g.e(android.view.MotionEvent):boolean");
    }

    private void c() {
        this.S = true;
        j0 = true;
        View view = this.t;
        if (view != null) {
            view.requestLayout();
            this.t.getViewTreeObserver().addOnGlobalLayoutListener(new a());
        }
    }

    private String c(String str) {
        if ("left".equals(str)) {
            return "right";
        }
        if ("right".equals(str)) {
            return "left";
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(boolean z) {
        if (this.S) {
            this.S = false;
            a("end", z, z ? "100" : WXInstanceApm.VALUE_ERROR_CODE_DEFAULT);
        }
    }

    private void b() {
        this.S = true;
        j0 = true;
        if (this.t != null) {
            this.s.requestLayout();
            this.s.getViewTreeObserver().addOnGlobalLayoutListener(new f());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public int b(View view) {
        if (view == 0) {
            return 0;
        }
        if (view instanceof INativeView) {
            return ((INativeView) view).getStyleWidth();
        }
        return view.getWidth();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public void b(View view, int i) {
        if (view != 0) {
            if (view instanceof INativeView) {
                ((INativeView) view).setStyleLeft(i);
                return;
            }
            if (view.getLayoutParams() instanceof AbsoluteLayout.LayoutParams) {
                AbsoluteLayout.LayoutParams layoutParams = (AbsoluteLayout.LayoutParams) view.getLayoutParams();
                layoutParams.height = view.getHeight();
                layoutParams.width = view.getWidth();
                ViewHelper.setX(view, i);
                view.requestLayout();
                return;
            }
            if (view.getLayoutParams() instanceof FrameLayout.LayoutParams) {
                ViewHelper.setX(view, i);
            }
        }
    }

    private void a(JSONObject jSONObject) {
        JSONObject jSONObject2;
        JSONObject jSONObject3;
        if (jSONObject != null) {
            this.z = Integer.MAX_VALUE;
            this.B = Integer.MAX_VALUE;
            this.A = null;
            this.C = null;
            this.G = "follow".equalsIgnoreCase(JSONUtil.getString(jSONObject, "moveMode"));
            if (jSONObject.has("over") && (jSONObject3 = JSONUtil.getJSONObject(jSONObject, "over")) != null) {
                if (jSONObject3.has("left")) {
                    this.z = PdrUtil.parseInt(JSONUtil.getString(jSONObject3, "left"), this.Q, Integer.MAX_VALUE);
                }
                if (jSONObject3.has("action")) {
                    this.A = JSONUtil.getString(jSONObject3, "action");
                }
            }
            if (!jSONObject.has(BindingXConstants.STATE_CANCEL) || (jSONObject2 = JSONUtil.getJSONObject(jSONObject, BindingXConstants.STATE_CANCEL)) == null) {
                return;
            }
            if (jSONObject2.has("left")) {
                this.B = PdrUtil.parseInt(JSONUtil.getString(jSONObject2, "left"), this.Q, Integer.MAX_VALUE);
            }
            if (jSONObject2.has("action")) {
                this.C = JSONUtil.getString(jSONObject2, "action");
            }
        }
    }

    private boolean a(View view, View view2) {
        if (view != null && view2 != null) {
            ViewParent parent = view2.getParent();
            for (ViewParent parent2 = view.getParent(); parent2 != null && parent2 != view2; parent2 = parent2.getParent()) {
                while (parent != null) {
                    if (parent == view) {
                        return false;
                    }
                    if (parent2 == parent) {
                        return true;
                    }
                    parent = parent.getParent();
                }
                parent = view2.getParent();
            }
            return false;
        }
        return false;
    }

    private void a() {
        this.S = true;
        j0 = true;
        this.s.requestLayout();
        this.s.getViewTreeObserver().addOnGlobalLayoutListener(new c());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(boolean z, boolean z2) {
        if (this.S) {
            this.S = false;
            a("end", z, z2, z ? "100" : WXInstanceApm.VALUE_ERROR_CODE_DEFAULT);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(View view, int i) throws JSONException {
        io.dcloud.common.core.ui.b bVar;
        io.dcloud.common.core.ui.b bVar2;
        if (view != null) {
            if (view == this.s && (bVar2 = this.q) != null) {
                bVar2.obtainFrameOptions().left = i;
                this.q.obtainFrameOptions().checkValueIsPercentage("left", i, this.Q, true, true);
            } else {
                if (view != this.t || (bVar = this.r) == null) {
                    return;
                }
                bVar.obtainFrameOptions().left = i;
                this.r.obtainFrameOptions().checkValueIsPercentage("left", i, this.Q, true, true);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ValueAnimator a(View view, int i, int i2, boolean z) {
        return a(view, i, i2, z, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ValueAnimator a(View view, int i, int i2, boolean z, boolean z2) {
        ValueAnimator valueAnimatorOfFloat = null;
        if (view == null) {
            return null;
        }
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if ((view instanceof INativeView) || (layoutParams instanceof AbsoluteLayout.LayoutParams)) {
            valueAnimatorOfFloat = ValueAnimator.ofInt(i, i2);
        } else if (layoutParams instanceof FrameLayout.LayoutParams) {
            valueAnimatorOfFloat = ValueAnimator.ofFloat(i, i2);
        }
        valueAnimatorOfFloat.setDuration(Math.min(Math.max(new BigDecimal(450).multiply(new BigDecimal(Math.abs(i2 - i)).divide(new BigDecimal(this.Q), 4, 4)).longValue(), 200L), 250L));
        valueAnimatorOfFloat.addListener(new d(z, z2, view, i2));
        valueAnimatorOfFloat.addUpdateListener(new e(view));
        return valueAnimatorOfFloat;
    }

    private void a(String str, boolean z, String str2) {
        a(str, z, false, str2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:32:0x009f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void a(java.lang.String r12, boolean r13, boolean r14, java.lang.String r15) {
        /*
            Method dump skipped, instructions count: 394
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.common.core.ui.g.a(java.lang.String, boolean, boolean, java.lang.String):void");
    }

    public View a(String str) {
        DragBean dragBean;
        IFrameView iFrameView;
        View viewObtainMainView;
        String strC = c(str);
        HashMap<String, DragBean> map = this.q.obtainFrameOptions().dragData;
        if (map == null || !map.containsKey(strC) || (dragBean = map.get(strC)) == null || (iFrameView = dragBean.dragBindWebView) == null || !"follow".equalsIgnoreCase(JSONUtil.getString(dragBean.dragBindViewOp, "moveMode")) || (viewObtainMainView = iFrameView.obtainMainView()) == null || viewObtainMainView.getVisibility() != 0 || viewObtainMainView.getWidth() >= this.Q) {
            return null;
        }
        int iA = a(viewObtainMainView);
        int width = viewObtainMainView.getWidth() + iA;
        if ((iA < 0 || iA >= this.Q) && (width <= 0 || width > this.Q)) {
            return null;
        }
        return viewObtainMainView;
    }

    private float a(float f2) {
        if (!"bounce".equalsIgnoreCase(this.D) || 0.0f == f2) {
            return f2;
        }
        boolean z = f2 < 0.0f;
        float fFloatValue = new BigDecimal(f2).multiply(new BigDecimal(Math.abs(this.n - a(this.s))).divide(new BigDecimal(this.n - this.m), 4, 4)).floatValue();
        if (z) {
            fFloatValue = -fFloatValue;
        }
        if (z) {
            return Math.min(fFloatValue, -2.0f);
        }
        return Math.max(fFloatValue, 2.0f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public int a(View view) {
        if (view == 0) {
            return 0;
        }
        if (view instanceof INativeView) {
            return ((INativeView) view).getStyleLeft();
        }
        if (view.getLayoutParams() instanceof AbsoluteLayout.LayoutParams) {
            return (int) ViewHelper.getX(view);
        }
        if (view.getLayoutParams() instanceof FrameLayout.LayoutParams) {
            return (int) ViewHelper.getX(view);
        }
        return 0;
    }

    private boolean a(io.dcloud.common.core.ui.b bVar) {
        if (bVar == null) {
            return true;
        }
        if (bVar != null && bVar.obtainWebView() == null) {
            return true;
        }
        if (bVar == null || bVar.obtainMainView() != null) {
            return bVar != null && bVar.obtainWebView() == null && bVar.obtainMainView() == null;
        }
        return true;
    }
}
