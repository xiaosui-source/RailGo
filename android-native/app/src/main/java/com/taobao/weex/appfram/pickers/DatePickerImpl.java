package com.taobao.weex.appfram.pickers;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import com.taobao.weex.common.WXThread;
import com.taobao.weex.el.parse.Operators;
import com.taobao.weex.performance.WXInstanceApm;
import com.taobao.weex.ui.module.WXModalUIModule;
import com.taobao.weex.utils.WXLogUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class DatePickerImpl {
    private static final int DEFAULT_END_YEAR = 2100;
    private static final int DEFAULT_START_YEAR = 1900;
    private static SimpleDateFormat dateFormatter;
    private static SimpleDateFormat timeFormatter;

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    public interface OnPickListener {
        void onPick(boolean z, String str);
    }

    private static Date parseDate(String str) {
        if (dateFormatter == null) {
            dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        }
        try {
            return dateFormatter.parse(str);
        } catch (ParseException e) {
            WXLogUtils.w("[DatePickerImpl] " + e.toString());
            return new Date();
        }
    }

    private static Date parseTime(String str) {
        if (timeFormatter == null) {
            timeFormatter = new SimpleDateFormat("HH:mm", Locale.getDefault());
        }
        try {
            return timeFormatter.parse(str);
        } catch (ParseException e) {
            WXLogUtils.w("[DatePickerImpl] " + e.toString());
            return new Date();
        }
    }

    public static void pickDate(Context context, String str, String str2, String str3, final OnPickListener onPickListener, Map<String, Object> map) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(parseDate(str));
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() { // from class: com.taobao.weex.appfram.pickers.DatePickerImpl.1
            @Override // android.app.DatePickerDialog.OnDateSetListener
            public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
                String strValueOf;
                String strValueOf2;
                int i4 = i2 + 1;
                if (i4 < 10) {
                    strValueOf = WXInstanceApm.VALUE_ERROR_CODE_DEFAULT + i4;
                } else {
                    strValueOf = String.valueOf(i4);
                }
                if (i3 < 10) {
                    strValueOf2 = WXInstanceApm.VALUE_ERROR_CODE_DEFAULT + i3;
                } else {
                    strValueOf2 = String.valueOf(i3);
                }
                onPickListener.onPick(true, i + Operators.SUB + strValueOf + Operators.SUB + strValueOf2);
            }
        }, calendar.get(1), calendar.get(2), calendar.get(5));
        DatePicker datePicker = datePickerDialog.getDatePicker();
        Calendar calendar2 = Calendar.getInstance(Locale.getDefault());
        Calendar calendar3 = Calendar.getInstance(Locale.getDefault());
        calendar2.set(DEFAULT_START_YEAR, 0, 1);
        calendar3.set(DEFAULT_END_YEAR, 11, 31);
        if (!TextUtils.isEmpty(str3)) {
            if (datePicker.getMaxDate() >= parseDate(str3).getTime()) {
                datePicker.setMinDate(parseDate(str3).getTime());
            } else {
                datePicker.setMinDate(calendar2.getTimeInMillis());
                datePicker.setMaxDate(calendar3.getTimeInMillis());
            }
        }
        if (!TextUtils.isEmpty(str2)) {
            if (datePicker.getMinDate() <= parseDate(str2).getTime()) {
                datePicker.setMaxDate(parseDate(str2).getTime());
            } else {
                datePicker.setMinDate(calendar2.getTimeInMillis());
                datePicker.setMaxDate(calendar3.getTimeInMillis());
            }
        }
        datePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.taobao.weex.appfram.pickers.DatePickerImpl.2
            @Override // android.content.DialogInterface.OnCancelListener
            public void onCancel(DialogInterface dialogInterface) {
                onPickListener.onPick(false, null);
            }
        });
        setButtonText(datePickerDialog, -2, String.valueOf(map != null ? map.get(WXModalUIModule.CANCEL_TITLE) : null));
        setButtonText(datePickerDialog, -1, String.valueOf(map != null ? map.get("confirmTitle") : null));
        datePickerDialog.show();
    }

    public static void pickTime(Context context, String str, final OnPickListener onPickListener, Map<String, Object> map) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(parseTime(str));
        TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() { // from class: com.taobao.weex.appfram.pickers.DatePickerImpl.3
            @Override // android.app.TimePickerDialog.OnTimeSetListener
            public void onTimeSet(TimePicker timePicker, int i, int i2) {
                String strValueOf;
                String strValueOf2;
                if (i < 10) {
                    strValueOf = WXInstanceApm.VALUE_ERROR_CODE_DEFAULT + i;
                } else {
                    strValueOf = String.valueOf(i);
                }
                if (i2 < 10) {
                    strValueOf2 = WXInstanceApm.VALUE_ERROR_CODE_DEFAULT + i2;
                } else {
                    strValueOf2 = String.valueOf(i2);
                }
                onPickListener.onPick(true, strValueOf + ":" + strValueOf2);
            }
        }, calendar.get(11), calendar.get(12), false);
        timePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.taobao.weex.appfram.pickers.DatePickerImpl.4
            @Override // android.content.DialogInterface.OnCancelListener
            public void onCancel(DialogInterface dialogInterface) {
                onPickListener.onPick(false, null);
            }
        });
        setButtonText(timePickerDialog, -2, String.valueOf(map != null ? map.get(WXModalUIModule.CANCEL_TITLE) : null));
        setButtonText(timePickerDialog, -1, String.valueOf(map != null ? map.get("confirmTitle") : null));
        timePickerDialog.show();
    }

    private static void setButtonText(final AlertDialog alertDialog, final int i, final CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence) || "null".equals(charSequence)) {
            return;
        }
        try {
            alertDialog.getWindow().getDecorView().post(WXThread.secure(new Runnable() { // from class: com.taobao.weex.appfram.pickers.DatePickerImpl.5
                @Override // java.lang.Runnable
                public void run() {
                    Button button = alertDialog.getButton(i);
                    if (button != null) {
                        button.setAllCaps(false);
                        button.setText(charSequence);
                    }
                }
            }));
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }
}
