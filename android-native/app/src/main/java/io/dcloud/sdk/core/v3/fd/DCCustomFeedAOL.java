package io.dcloud.sdk.core.v3.fd;

import android.app.Activity;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import io.dcloud.WebAppActivity;
import io.dcloud.base.R;
import io.dcloud.sdk.activity.WebViewActivity;
import io.dcloud.sdk.core.interfaces.AOLLoader;
import io.dcloud.sdk.core.v3.fd.DCCustomFeedAOL;
import java.util.ArrayList;
import java.util.Locale;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class DCCustomFeedAOL {
    private final DCFeedAOL a;
    private final Activity b;
    private CountDownTimer c;
    private Runnable g;
    private TextView i;
    private FrameLayout j;
    private long d = WebAppActivity.SPLASH_SECOND;
    private boolean e = false;
    private boolean h = false;
    private final Handler f = new Handler();

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    /* renamed from: io.dcloud.sdk.core.v3.fd.DCCustomFeedAOL$8, reason: invalid class name */
    class AnonymousClass8 extends CountDownTimer {
        final /* synthetic */ TextView a;
        final /* synthetic */ FrameLayout b;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass8(long j, long j2, TextView textView, FrameLayout frameLayout) {
            super(j, j2);
            this.a = textView;
            this.b = frameLayout;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void a(TextView textView, int i) {
            if (textView != null) {
                textView.setText(i + "s");
            }
        }

        @Override // android.os.CountDownTimer
        public void onFinish() {
            DCCustomFeedAOL.this.e = false;
            DCCustomFeedAOL.this.d = 0L;
            if (DCCustomFeedAOL.this.h) {
                return;
            }
            DCCustomFeedAOL.this.c(this.a, this.b);
        }

        @Override // android.os.CountDownTimer
        public void onTick(long j) {
            DCCustomFeedAOL.this.d = j;
            final int iMax = Math.max(0, Math.round(j / 1000.0f));
            Activity activity = DCCustomFeedAOL.this.b;
            final TextView textView = this.a;
            activity.runOnUiThread(new Runnable() { // from class: io.dcloud.sdk.core.v3.fd.DCCustomFeedAOL$8$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    DCCustomFeedAOL.AnonymousClass8.a(textView, iMax);
                }
            });
        }
    }

    public DCCustomFeedAOL(DCFeedAOL dCFeedAOL, Activity activity) {
        this.a = dCFeedAOL;
        this.b = activity;
    }

    public View getFeedAOLView() {
        DCFeedAOL dCFeedAOL = this.a;
        if (dCFeedAOL != null) {
            if (dCFeedAOL.isExpressAd()) {
                return this.a.getFeedAOLView(this.b);
            }
            final FrameLayout frameLayout = (FrameLayout) LayoutInflater.from(this.b).inflate(R.layout.dcloud_ad_custom_feed_ad, (ViewGroup) null);
            if (this.a.getMaterialType() == 1) {
                long videoDuration = (long) (this.a.getVideoDuration() * 1000.0d);
                this.d = videoDuration;
                ViewGroup viewGroup = (ViewGroup) frameLayout.findViewById(R.id.videoContainer);
                View videoView = this.a.getVideoView(this.b);
                if (videoView != null) {
                    viewGroup.addView(videoView, new ViewGroup.LayoutParams(-1, -1));
                    TextView textView = (TextView) frameLayout.findViewById(R.id.privacyInfo);
                    if (this.a.getInteractionType() == 1) {
                        final AOLLoader.AppInfo appInfo = this.a.getAppInfo();
                        SpannableString spannableString = new SpannableString(String.format(Locale.ENGLISH, "应用名称：%s|开发者：%s|应用版本：%s|隐私协议|权限详情|简介", appInfo.getAppName(), appInfo.getDeveloperName(), appInfo.getAppVersion()));
                        ClickableSpan clickableSpan = new ClickableSpan() { // from class: io.dcloud.sdk.core.v3.fd.DCCustomFeedAOL.1
                            @Override // android.text.style.ClickableSpan
                            public void onClick(View view) {
                                Intent intent = new Intent(DCCustomFeedAOL.this.b, (Class<?>) WebViewActivity.class);
                                intent.putExtra("url", appInfo.getPrivacyUrl());
                                DCCustomFeedAOL.this.b.startActivity(intent);
                            }
                        };
                        ClickableSpan clickableSpan2 = new ClickableSpan() { // from class: io.dcloud.sdk.core.v3.fd.DCCustomFeedAOL.2
                            @Override // android.text.style.ClickableSpan
                            public void onClick(View view) {
                                Intent intent = new Intent(DCCustomFeedAOL.this.b, (Class<?>) WebViewActivity.class);
                                intent.putExtra("url", appInfo.getPermissionInfoUrl());
                                DCCustomFeedAOL.this.b.startActivity(intent);
                            }
                        };
                        ClickableSpan clickableSpan3 = new ClickableSpan() { // from class: io.dcloud.sdk.core.v3.fd.DCCustomFeedAOL.3
                            @Override // android.text.style.ClickableSpan
                            public void onClick(View view) {
                                Intent intent = new Intent(DCCustomFeedAOL.this.b, (Class<?>) WebViewActivity.class);
                                intent.putExtra("url", appInfo.getFunctionDescUrl());
                                DCCustomFeedAOL.this.b.startActivity(intent);
                            }
                        };
                        spannableString.setSpan(clickableSpan, spannableString.toString().indexOf("隐私协议"), spannableString.toString().indexOf("隐私协议") + 4, 33);
                        spannableString.setSpan(clickableSpan2, spannableString.toString().indexOf("权限详情"), spannableString.toString().indexOf("权限详情") + 4, 33);
                        spannableString.setSpan(clickableSpan3, spannableString.toString().indexOf("简介"), spannableString.toString().indexOf("简介") + 2, 33);
                        textView.setText(spannableString);
                        textView.setVisibility(0);
                        textView.setMovementMethod(LinkMovementMethod.getInstance());
                    } else {
                        textView.setVisibility(8);
                    }
                    final TextView textView2 = (TextView) frameLayout.findViewById(R.id.skipView);
                    textView2.setText(Math.max(0, Math.round(videoDuration / 1000.0f)) + "s");
                    this.i = textView2;
                    this.j = frameLayout;
                    textView2.setOnClickListener(new View.OnClickListener() { // from class: io.dcloud.sdk.core.v3.fd.DCCustomFeedAOL.4
                        @Override // android.view.View.OnClickListener
                        public void onClick(View view) {
                            if (DCCustomFeedAOL.this.e) {
                                return;
                            }
                            if (DCCustomFeedAOL.this.g != null) {
                                DCCustomFeedAOL.this.f.removeCallbacks(DCCustomFeedAOL.this.g);
                                DCCustomFeedAOL.this.h = false;
                            }
                            if (frameLayout.getParent() instanceof ViewGroup) {
                                ((ViewGroup) frameLayout.getParent()).removeView(frameLayout);
                            }
                            DCCustomFeedAOL.this.a.onClosed("");
                        }
                    });
                    this.a.setVideoAdListener(new AOLLoader.VideoAdListener() { // from class: io.dcloud.sdk.core.v3.fd.DCCustomFeedAOL.5
                        @Override // io.dcloud.sdk.core.interfaces.AOLLoader.VideoAdListener
                        public void onProgressUpdate(long j, long j2) {
                        }

                        @Override // io.dcloud.sdk.core.interfaces.AOLLoader.VideoAdListener
                        public void onVideoAdComplete() {
                            if (DCCustomFeedAOL.this.c != null) {
                                DCCustomFeedAOL.this.c.cancel();
                                DCCustomFeedAOL.this.c = null;
                            }
                            DCCustomFeedAOL.this.e = false;
                            DCCustomFeedAOL.this.d = 0L;
                            if (DCCustomFeedAOL.this.h) {
                                return;
                            }
                            DCCustomFeedAOL.this.c(textView2, frameLayout);
                        }

                        @Override // io.dcloud.sdk.core.interfaces.AOLLoader.VideoAdListener
                        public void onVideoAdPaused() {
                            DCCustomFeedAOL.this.a();
                        }

                        @Override // io.dcloud.sdk.core.interfaces.AOLLoader.VideoAdListener
                        public void onVideoAdResume() {
                            DCCustomFeedAOL.this.b();
                        }

                        @Override // io.dcloud.sdk.core.interfaces.AOLLoader.VideoAdListener
                        public void onVideoAdStartPlay() {
                            DCCustomFeedAOL.this.c();
                        }

                        @Override // io.dcloud.sdk.core.interfaces.AOLLoader.VideoAdListener
                        public void onVideoError(int i, String str) {
                            if (DCCustomFeedAOL.this.c != null) {
                                DCCustomFeedAOL.this.c.cancel();
                                DCCustomFeedAOL.this.c = null;
                            }
                            DCCustomFeedAOL.this.e = false;
                            DCCustomFeedAOL.this.c(textView2, frameLayout);
                        }

                        @Override // io.dcloud.sdk.core.interfaces.AOLLoader.VideoAdListener
                        public void onVideoLoad() {
                            DCCustomFeedAOL.this.b(textView2, frameLayout);
                        }
                    });
                    this.a.registerViewForInteraction(this.b, frameLayout, new ArrayList<View>(videoView) { // from class: io.dcloud.sdk.core.v3.fd.DCCustomFeedAOL.6
                        final /* synthetic */ View a;

                        {
                            this.a = videoView;
                            add(videoView);
                        }
                    }, null, null);
                    return frameLayout;
                }
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(final TextView textView, final FrameLayout frameLayout) {
        if (this.h) {
            return;
        }
        CountDownTimer countDownTimer = this.c;
        if (countDownTimer != null) {
            countDownTimer.cancel();
            this.c = null;
        }
        this.e = false;
        this.d = 0L;
        this.b.runOnUiThread(new Runnable() { // from class: io.dcloud.sdk.core.v3.fd.DCCustomFeedAOL$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.a(textView, frameLayout);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(TextView textView, FrameLayout frameLayout) {
        CountDownTimer countDownTimer = this.c;
        if (countDownTimer != null) {
            countDownTimer.cancel();
            this.c = null;
        }
        if (this.d <= 0) {
            this.d = 1000L;
        }
        this.c = new AnonymousClass8(this.d, 1000L, textView, frameLayout);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(TextView textView, final FrameLayout frameLayout) {
        if (textView != null) {
            textView.setText("关闭");
        }
        Runnable runnable = this.g;
        if (runnable != null) {
            this.f.removeCallbacks(runnable);
        }
        this.g = new Runnable() { // from class: io.dcloud.sdk.core.v3.fd.DCCustomFeedAOL.7
            @Override // java.lang.Runnable
            public void run() {
                FrameLayout frameLayout2 = frameLayout;
                if (frameLayout2 != null && (frameLayout2.getParent() instanceof ViewGroup)) {
                    ((ViewGroup) frameLayout.getParent()).removeView(frameLayout);
                }
                DCCustomFeedAOL.this.h = false;
                DCCustomFeedAOL.this.a.onClosed("");
            }
        };
        this.h = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        TextView textView;
        FrameLayout frameLayout;
        if (this.e || this.d <= 0 || (textView = this.i) == null || (frameLayout = this.j) == null) {
            return;
        }
        b(textView, frameLayout);
        c();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c() {
        CountDownTimer countDownTimer = this.c;
        if (countDownTimer == null || this.e) {
            return;
        }
        this.e = true;
        countDownTimer.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a() {
        CountDownTimer countDownTimer = this.c;
        if (countDownTimer == null || !this.e) {
            return;
        }
        countDownTimer.cancel();
        this.c = null;
        this.e = false;
    }
}
