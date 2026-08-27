package com.taobao.weex.ui.component;

import android.R;
import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import com.taobao.weex.ui.view.WXEditText;
import io.dcloud.common.DHInterface.IApp;
import io.dcloud.common.DHInterface.ISysEventListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class ConfirmBar {
    private static ConfirmBar instance;
    private int height;
    private ViewGroup rootView;
    private RelativeLayout rtl;
    private List<WXComponent> editText = new ArrayList();
    private AtomicReference<ISysEventListener> listener = null;

    static ConfirmBar getInstance() {
        if (instance == null) {
            synchronized (ConfirmBar.class) {
                if (instance == null) {
                    instance = new ConfirmBar();
                }
            }
        }
        return instance;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showConfirm(boolean z, int i) {
        try {
            if (this.rtl == null) {
                return;
            }
            ViewGroup viewGroup = this.rootView;
            View viewFindViewWithTag = viewGroup != null ? viewGroup.findViewWithTag("AppRootView") : null;
            if (!z) {
                this.rtl.setVisibility(4);
                if (viewFindViewWithTag != null) {
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) viewFindViewWithTag.getLayoutParams();
                    layoutParams.bottomMargin = 0;
                    viewFindViewWithTag.setLayoutParams(layoutParams);
                    return;
                }
                return;
            }
            if (this.rootView != null) {
                FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) viewFindViewWithTag.getLayoutParams();
                layoutParams2.bottomMargin = this.height;
                viewFindViewWithTag.setLayoutParams(layoutParams2);
            }
            FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) this.rtl.getLayoutParams();
            layoutParams3.topMargin = i;
            this.rtl.setLayoutParams(layoutParams3);
            this.rtl.setVisibility(0);
            this.rtl.bringToFront();
        } catch (Exception unused) {
        }
    }

    void addComponent(WXComponent wXComponent) {
        try {
            this.editText.add(wXComponent);
        } catch (Exception unused) {
        }
    }

    void createConfirmBar(final Context context, IApp iApp) {
        try {
            if (this.listener == null) {
                AtomicReference<ISysEventListener> atomicReference = new AtomicReference<>(new ISysEventListener() { // from class: com.taobao.weex.ui.component.ConfirmBar.1
                    @Override // io.dcloud.common.DHInterface.ISysEventListener
                    public boolean onExecute(ISysEventListener.SysEventType sysEventType, Object obj) {
                        if (sysEventType == ISysEventListener.SysEventType.onSizeChanged) {
                            int[] iArr = (int[]) obj;
                            int i = context.getResources().getDisplayMetrics().heightPixels;
                            int i2 = context.getResources().getDisplayMetrics().heightPixels / 4;
                            if (Math.abs(iArr[1] - i) > i2 || Math.abs(iArr[1] - iArr[3]) > i2) {
                                int i3 = iArr[1];
                                int i4 = iArr[3];
                                if (i3 <= i4 || Math.abs(i3 - i4) <= i2) {
                                    Iterator it = ConfirmBar.this.editText.iterator();
                                    while (true) {
                                        if (!it.hasNext()) {
                                            break;
                                        }
                                        WXComponent wXComponent = (WXComponent) it.next();
                                        if (wXComponent.getHostView().hasFocus() && (wXComponent instanceof DCTextArea) && ((DCTextArea) wXComponent).isShowConfirm) {
                                            ConfirmBar.getInstance().showConfirm(true, iArr[1]);
                                            break;
                                        }
                                        ConfirmBar.getInstance().showConfirm(false, iArr[1]);
                                    }
                                } else {
                                    ConfirmBar.getInstance().showConfirm(false, iArr[1]);
                                }
                            }
                        } else if (sysEventType == ISysEventListener.SysEventType.onWebAppStop) {
                            ConfirmBar.this.listener = null;
                            ConfirmBar.this.rtl = null;
                            ConfirmBar.this.rootView = null;
                        }
                        return false;
                    }
                });
                this.listener = atomicReference;
                iApp.registerSysEventListener(atomicReference.get(), ISysEventListener.SysEventType.onSizeChanged);
                iApp.registerSysEventListener(this.listener.get(), ISysEventListener.SysEventType.onWebAppStop);
            }
            this.height = (int) TypedValue.applyDimension(1, 44.0f, context.getResources().getDisplayMetrics());
            if (this.rtl == null) {
                this.rtl = new RelativeLayout(context);
                ViewGroup viewGroup = (ViewGroup) iApp.obtainWebAppRootView().obtainMainView().getParent();
                this.rootView = viewGroup;
                if (viewGroup != null) {
                    this.rootView.addView(this.rtl, new FrameLayout.LayoutParams(-1, this.height));
                }
                Button button = new Button(context);
                button.setText(R.string.ok);
                button.setGravity(17);
                button.setTextColor(Color.argb(255, 50, 205, 50));
                button.setTextSize(TypedValue.applyDimension(2, 6.0f, context.getResources().getDisplayMetrics()));
                button.setBackground(null);
                button.setOnClickListener(new View.OnClickListener() { // from class: com.taobao.weex.ui.component.ConfirmBar.2
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        WXComponent wXComponent;
                        if (ConfirmBar.this.editText.size() > 0) {
                            Iterator it = ConfirmBar.this.editText.iterator();
                            while (true) {
                                if (!it.hasNext()) {
                                    wXComponent = null;
                                    break;
                                }
                                wXComponent = (WXComponent) it.next();
                                if ((wXComponent.getHostView() instanceof WXEditText) && wXComponent.getHostView().hasFocus()) {
                                    break;
                                }
                            }
                            if (wXComponent != null) {
                                HashMap map = new HashMap(1);
                                HashMap map2 = new HashMap(1);
                                map2.put("value", ((WXEditText) wXComponent.getHostView()).getText().toString());
                                map.put("detail", map2);
                                wXComponent.fireEvent("confirm", map);
                                if (wXComponent.getParent() != null) {
                                    wXComponent.getParent().interceptFocus();
                                }
                                wXComponent.getHostView().clearFocus();
                                ((DCTextArea) wXComponent).hideSoftKeyboard();
                            }
                        }
                    }
                });
                this.rtl.addView(button, new RelativeLayout.LayoutParams(-2, -1));
                this.rtl.setBackgroundColor(Color.argb(255, 220, 220, 220));
                this.rtl.setTag("ConfirmBar");
                this.rtl.setVisibility(4);
            }
        } catch (Exception unused) {
        }
    }

    void removeComponent(WXComponent wXComponent) {
        try {
            this.editText.remove(wXComponent);
        } catch (Exception unused) {
        }
    }
}
