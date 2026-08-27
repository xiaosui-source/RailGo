package com.taobao.weex.ui.component.list;

import androidx.recyclerview.widget.RecyclerView;
import com.taobao.weex.ui.view.listview.adapter.TransformItemDecoration;
import com.taobao.weex.utils.WXLogUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class RecyclerTransform {
    private static final String TAG = "RecyclerTransform";
    public static final String TRANSFORM = "transform";
    private static final Pattern transformPattern = Pattern.compile("([a-z]+)\\(([0-9\\.]+),?([0-9\\.]+)?\\)");

    public static RecyclerView.ItemDecoration parseTransforms(int i, String str) throws NumberFormatException {
        if (str == null) {
            return null;
        }
        Matcher matcher = transformPattern.matcher(str);
        float f = 0.0f;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        float f2 = 0.0f;
        float f3 = 0.0f;
        while (matcher.find()) {
            String strGroup = matcher.group();
            String strGroup2 = matcher.group(1);
            try {
                int iHashCode = strGroup2.hashCode();
                if (iHashCode != -1267206133) {
                    if (iHashCode != -925180581) {
                        if (iHashCode != 109250890) {
                            if (iHashCode == 1052832078 && strGroup2.equals("translate")) {
                                i2 = Integer.parseInt(matcher.group(2));
                                i3 = Integer.parseInt(matcher.group(3));
                            } else {
                                WXLogUtils.e(TAG, "Invaild transform expression:" + strGroup);
                            }
                        } else if (strGroup2.equals("scale")) {
                            f2 = Float.parseFloat(matcher.group(2));
                            f3 = Float.parseFloat(matcher.group(3));
                        } else {
                            WXLogUtils.e(TAG, "Invaild transform expression:" + strGroup);
                        }
                    } else if (strGroup2.equals("rotate")) {
                        i4 = Integer.parseInt(matcher.group(2));
                    } else {
                        WXLogUtils.e(TAG, "Invaild transform expression:" + strGroup);
                    }
                } else if (strGroup2.equals("opacity")) {
                    f = Float.parseFloat(matcher.group(2));
                } else {
                    WXLogUtils.e(TAG, "Invaild transform expression:" + strGroup);
                }
            } catch (NumberFormatException e) {
                WXLogUtils.e("", e);
                WXLogUtils.e(TAG, "Invaild transform expression:" + strGroup);
            }
        }
        return new TransformItemDecoration(i == 1, f, i2, i3, i4, f2, f3);
    }
}
