package com.taobao.weex.appfram.pickers;

import android.R;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Checkable;
import android.widget.TextView;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.appfram.pickers.DatePickerImpl;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;
import com.taobao.weex.common.WXThread;
import com.taobao.weex.utils.WXResourceUtils;
import com.taobao.weex.utils.WXViewUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class WXPickersModule extends WXModule {
    private static final String CANCEL = "cancel";
    private static final String DATA = "data";
    private static final String ERROR = "error";
    private static final String KEY_CANCEL_TITLE = "cancelTitle";
    private static final String KEY_CANCEL_TITLE_COLOR = "cancelTitleColor";
    private static final String KEY_CONFIRM_TITLE = "confirmTitle";
    private static final String KEY_CONFIRM_TITLE_COLOR = "confirmTitleColor";
    private static final String KEY_INDEX = "index";
    private static final String KEY_ITEMS = "items";
    private static final String KEY_MAX = "max";
    private static final String KEY_MIN = "min";
    private static final String KEY_SELECTION_COLOR = "selectionColor";
    private static final String KEY_TEXT_COLOR = "textColor";
    private static final String KEY_TITLE = "title";
    private static final String KEY_TITLE_BACKGROUND_COLOR = "titleBackgroundColor";
    private static final String KEY_TITLE_COLOR = "titleColor";
    private static final String KEY_VALUE = "value";
    private static final String RESULT = "result";
    private static final String SUCCESS = "success";
    private int selected;

    /* JADX INFO: Access modifiers changed from: private */
    public int getColor(Map<String, Object> map, String str, int i) {
        Object option = getOption(map, str, null);
        return option == null ? i : WXResourceUtils.getColor(option.toString(), i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public <T> T getOption(Map<String, Object> map, String str, T t) {
        T t2 = (T) map.get(str);
        return t2 == null ? t : t2;
    }

    private TextView makeTitleView(Context context, Map<String, Object> map) {
        String str = (String) getOption(map, "title", null);
        if (str == null) {
            return null;
        }
        TextView textView = new TextView(context);
        textView.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
        textView.setTextSize(2, 20.0f);
        int iDip2px = WXViewUtils.dip2px(12.0f);
        textView.setPadding(iDip2px, iDip2px, iDip2px, iDip2px);
        textView.getPaint().setFakeBoldText(true);
        textView.setBackgroundColor(getColor(map, KEY_TITLE_BACKGROUND_COLOR, 0));
        textView.setTextColor(getColor(map, KEY_TITLE_COLOR, -16777216));
        textView.setText(str);
        return textView;
    }

    private void performPickDate(Map<String, Object> map, final JSCallback jSCallback) {
        DatePickerImpl.pickDate(this.mWXSDKInstance.getContext(), (String) getOption(map, "value", ""), (String) getOption(map, "max", ""), (String) getOption(map, "min", ""), new DatePickerImpl.OnPickListener() { // from class: com.taobao.weex.appfram.pickers.WXPickersModule.2
            @Override // com.taobao.weex.appfram.pickers.DatePickerImpl.OnPickListener
            public void onPick(boolean z, String str) {
                if (z) {
                    HashMap map2 = new HashMap(2);
                    map2.put("result", "success");
                    map2.put("data", str);
                    jSCallback.invoke(map2);
                    return;
                }
                HashMap map3 = new HashMap(2);
                map3.put("result", "cancel");
                map3.put("data", null);
                jSCallback.invoke(map3);
            }
        }, map);
    }

    private void performPickTime(Map<String, Object> map, final JSCallback jSCallback) {
        DatePickerImpl.pickTime(this.mWXSDKInstance.getContext(), (String) getOption(map, "value", ""), new DatePickerImpl.OnPickListener() { // from class: com.taobao.weex.appfram.pickers.WXPickersModule.1
            @Override // com.taobao.weex.appfram.pickers.DatePickerImpl.OnPickListener
            public void onPick(boolean z, String str) {
                if (z) {
                    HashMap map2 = new HashMap(2);
                    map2.put("result", "success");
                    map2.put("data", str);
                    jSCallback.invoke(map2);
                    return;
                }
                HashMap map3 = new HashMap(2);
                map3.put("result", "cancel");
                map3.put("data", null);
                jSCallback.invoke(map3);
            }
        }, map);
    }

    private void performSinglePick(List<String> list, final Map<String, Object> map, final JSCallback jSCallback) {
        this.selected = ((Integer) getOption(map, "index", 0)).intValue();
        final int color = getColor(map, KEY_TEXT_COLOR, 0);
        final int color2 = getColor(map, KEY_SELECTION_COLOR, 0);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this.mWXSDKInstance.getContext(), R.layout.simple_list_item_single_choice, list) { // from class: com.taobao.weex.appfram.pickers.WXPickersModule.3
            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.widget.ArrayAdapter, android.widget.Adapter
            public View getView(int i, View view, ViewGroup viewGroup) {
                int i2;
                View view2 = super.getView(i, view, viewGroup);
                if (view2 != 0 && (view2 instanceof Checkable)) {
                    boolean z = i == WXPickersModule.this.selected;
                    ((Checkable) view2).setChecked(z);
                    if (z) {
                        view2.setBackgroundColor(color2);
                    } else {
                        view2.setBackgroundColor(0);
                    }
                }
                if ((view2 instanceof TextView) && (i2 = color) != 0) {
                    ((TextView) view2).setTextColor(i2);
                }
                return view2;
            }
        };
        final AlertDialog alertDialogCreate = new AlertDialog.Builder(this.mWXSDKInstance.getContext()).setAdapter(arrayAdapter, null).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() { // from class: com.taobao.weex.appfram.pickers.WXPickersModule.5
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                HashMap map2 = new HashMap(2);
                map2.put("result", "success");
                map2.put("data", Integer.valueOf(WXPickersModule.this.selected));
                jSCallback.invoke(map2);
            }
        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() { // from class: com.taobao.weex.appfram.pickers.WXPickersModule.4
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                HashMap map2 = new HashMap(2);
                map2.put("result", "cancel");
                map2.put("data", -1);
                jSCallback.invoke(map2);
            }
        }).setCustomTitle(makeTitleView(this.mWXSDKInstance.getContext(), map)).create();
        alertDialogCreate.create();
        alertDialogCreate.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.taobao.weex.appfram.pickers.WXPickersModule.6
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                WXPickersModule.this.selected = i;
                arrayAdapter.notifyDataSetChanged();
            }
        });
        alertDialogCreate.getWindow().getDecorView().post(WXThread.secure(new Runnable() { // from class: com.taobao.weex.appfram.pickers.WXPickersModule.7
            @Override // java.lang.Runnable
            public void run() {
                Button button = alertDialogCreate.getButton(-1);
                Button button2 = alertDialogCreate.getButton(-2);
                if (button != null) {
                    String str = (String) WXPickersModule.this.getOption(map, WXPickersModule.KEY_CONFIRM_TITLE, null);
                    int color3 = WXPickersModule.this.getColor(map, WXPickersModule.KEY_CONFIRM_TITLE_COLOR, 0);
                    if (str != null) {
                        button.setText(str);
                        button.setAllCaps(false);
                    }
                    if (color3 != 0) {
                        button.setTextColor(color3);
                        button.setAllCaps(false);
                    }
                }
                if (button2 != null) {
                    String str2 = (String) WXPickersModule.this.getOption(map, "cancelTitle", null);
                    int color4 = WXPickersModule.this.getColor(map, WXPickersModule.KEY_CANCEL_TITLE_COLOR, 0);
                    if (str2 != null) {
                        button2.setText(str2);
                    }
                    if (color4 != 0) {
                        button2.setTextColor(color4);
                    }
                }
            }
        }));
        alertDialogCreate.show();
    }

    private List<String> safeConvert(List list) {
        ArrayList arrayList = new ArrayList(list.size());
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(String.valueOf(it.next()));
        }
        return arrayList;
    }

    @JSMethod
    public void pick(Map<String, Object> map, JSCallback jSCallback) {
        try {
            performSinglePick(safeConvert((List) getOption(map, KEY_ITEMS, new ArrayList())), map, jSCallback);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    @JSMethod
    public void pickDate(Map<String, Object> map, JSCallback jSCallback) {
        performPickDate(map, jSCallback);
    }

    @JSMethod
    public void pickTime(Map<String, Object> map, JSCallback jSCallback) {
        performPickTime(map, jSCallback);
    }
}
