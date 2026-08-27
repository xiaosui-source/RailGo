package io.dcloud;

import android.content.Context;
import io.dcloud.base.R;
import io.dcloud.common.DHInterface.IReflectAble;
import io.dcloud.common.adapter.util.AndroidResources;
import io.dcloud.common.util.BaseInfo;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class PdrR implements IReflectAble {
    public static int ANIM_DCLOUD_SLIDE_IN_FROM_RIGHT;
    public static int ANIM_DCLOUD_SLIDE_OUT_TO_RIGHT;
    public static int ANIM_DIALOG_ANIM_DCLOUD_SLIDE_IN_FROM_TOP;
    public static int ANIM_DIALOG_ANIM_DCLOUD_SLIDE_OUT_TO_TOP;
    public static int DCLOUD_GUIDE_CLOSE;
    public static int DCLOUD_GUIDE_GIFVIEW;
    public static int DCLOUD_GUIDE_GIF_HUAWEI;
    public static int DCLOUD_GUIDE_GIF_MEIZU;
    public static int DCLOUD_GUIDE_GIF_XIAOMI;
    public static int DCLOUD_GUIDE_PLAY;
    public static int DCLOUD_GUIDE_PLAY_LAYOUT;
    public static int DCLOUD_GUIDE_TIP;
    public static int DCLOUD_PACKAGE_NAME_BASE;
    public static int DCLOUD_SHORTCUT_PERMISSION_GUIDE_LAYOUT;
    public static int DCLOUD_SYNC_DEBUD_MESSAGE;
    public static int DRAWABLE_DCLOUD_DIALOG_SHAPE;
    public static int DRAWABLE_DCLOUD_WEBVIEW_DOWNLOAD_PIN;
    public static int DRAWABLE_DCLOUD_WEBVIEW_DOWNLOAD_PIN_AROUND;
    public static int DRAWBLE_PROGRESSBAR_BLACK_CIRCLE;
    public static int DRAWBLE_PROGRESSBAR_BLACK_SNOW;
    public static int DRAWBLE_PROGRESSBAR_WHITE_CIRCLE;
    public static int DRAWBLE_PROGRESSBAR_WHITE_SNOW;
    public static int DRAWEBL_SHADOW_LEFT;
    public static int ID_DCLOUD_DIALOG_BTN1;
    public static int ID_DCLOUD_DIALOG_BTN2;
    public static int ID_DCLOUD_DIALOG_ICON;
    public static int ID_DCLOUD_DIALOG_MSG;
    public static int ID_DCLOUD_DIALOG_ROOTVIEW;
    public static int ID_DCLOUD_DIALOG_TITLE;
    public static int ID_TEXT_NOTIFICATION;
    public static int ID_TIME_NOTIFICATION_DCLOUD;
    public static int ID_TITLE_NOTIFICATION_DCLOUD;
    public static int LAYOUT_DIALOG_LAYOUT_DCLOUD_DIALOG;
    public static int STREAMAPP_CUSTOM_ALERT_DIALOG_CANCEL;
    public static int STREAMAPP_CUSTOM_ALERT_DIALOG_CHECKBOX;
    public static int STREAMAPP_CUSTOM_ALERT_DIALOG_CUSTOM_LAYOUT;
    public static int STREAMAPP_CUSTOM_ALERT_DIALOG_LAYOUT;
    public static int STREAMAPP_CUSTOM_ALERT_DIALOG_SURE;
    public static int STREAMAPP_CUSTOM_ALERT_DIALOG_TITLE;
    public static int STREAMAPP_DELETE_DARK_THEME;
    public static int STREAMAPP_DELETE_THEME;
    public static int STREAMAPP_DRAWABLE_APPDEFULTICON;
    public static int STYLE_DIALOG_DCLOUD_DEFALUT_DIALOG;
    public static int STYLE_DIALOG_STYLE_DCLOUD_ANIM_DIALOG_WINDOW_IN_OUT;
    public static int[] STYLE_GIFVIEW;
    public static int STYLE_GIFVIEW_authPlay;
    public static int STYLE_GIFVIEW_gifSrc;
    public static int STYLE_GIFVIEW_playCount;
    public static int UNI_CUSTOM_PRIVACY_DIALOG_LAYOUT;
    public static int WEBVIEW_ACTIVITY_LAYOUT;
    public static int WEBVIEW_ACTIVITY_LAYOUT_ACTS_STYLE_ActionSheetStyleIOS7;
    public static int WEBVIEW_ACTIVITY_LAYOUT_BACK;
    public static int WEBVIEW_ACTIVITY_LAYOUT_CLOSE;
    public static int WEBVIEW_ACTIVITY_LAYOUT_CONTENT;
    public static int WEBVIEW_ACTIVITY_LAYOUT_MENU;
    public static int WEBVIEW_ACTIVITY_LAYOUT_REFRESH;
    public static int WEBVIEW_ACTIVITY_LAYOUT_TITLE;
    public static int WEBVIEW_ACTIVITY_LAYOUT_WEBVIEW;
    public static int DRAWABLE_ICON = AndroidResources.mApplicationInfo.applicationInfo.icon;
    public static int LAYOUT_SNOW_WHITE_PROGRESS = R.layout.dcloud_snow_white_progress;
    public static int LAYOUT_SNOW_BLACK_PROGRESS = R.layout.dcloud_snow_black_progress;
    public static int ID_PROGRESSBAR = R.id.progressBar;
    public static int FEATURE_LOSS_STYLE = R.style.featureLossDialog;
    public static int LAYOUT_CUSTION_NOTIFICATION_DCLOUD = R.layout.dcloud_custom_notification;
    public static int ID_IMAGE_NOTIFICATION_DCLOUD = R.id.image;

    static {
        int i = R.id.title;
        ID_TITLE_NOTIFICATION_DCLOUD = i;
        ID_TEXT_NOTIFICATION = R.id.text;
        ID_TIME_NOTIFICATION_DCLOUD = R.id.time;
        DRAWABLE_DCLOUD_DIALOG_SHAPE = R.drawable.dcloud_dialog_shape;
        LAYOUT_DIALOG_LAYOUT_DCLOUD_DIALOG = R.layout.dcloud_dialog;
        ID_DCLOUD_DIALOG_ROOTVIEW = R.id.dcloud_dialog_rootview;
        ID_DCLOUD_DIALOG_TITLE = R.id.dcloud_dialog_title;
        ID_DCLOUD_DIALOG_ICON = R.id.dcloud_dialog_icon;
        ID_DCLOUD_DIALOG_MSG = R.id.dcloud_dialog_msg;
        ID_DCLOUD_DIALOG_BTN1 = R.id.dcloud_dialog_btn1;
        ID_DCLOUD_DIALOG_BTN2 = R.id.dcloud_dialog_btn2;
        STYLE_DIALOG_DCLOUD_DEFALUT_DIALOG = R.style.dcloud_defalut_dialog;
        STYLE_DIALOG_STYLE_DCLOUD_ANIM_DIALOG_WINDOW_IN_OUT = R.style.dcloud_anim_dialog_window_in_out;
        ANIM_DIALOG_ANIM_DCLOUD_SLIDE_IN_FROM_TOP = R.anim.dcloud_slide_in_from_top;
        ANIM_DIALOG_ANIM_DCLOUD_SLIDE_OUT_TO_TOP = R.anim.dcloud_slide_out_to_top;
        STREAMAPP_DELETE_THEME = R.style.streamDelete19Dialog;
        STREAMAPP_DELETE_DARK_THEME = R.style.streamDelete19DarkDialog;
        STREAMAPP_DRAWABLE_APPDEFULTICON = R.drawable.dcloud_streamapp_icon_appdefault;
        DRAWBLE_PROGRESSBAR_BLACK_SNOW = R.drawable.dcloud_snow_black_progress_drawable;
        DRAWBLE_PROGRESSBAR_WHITE_SNOW = R.drawable.dcloud_snow_white_progress_drawable;
        DRAWBLE_PROGRESSBAR_BLACK_CIRCLE = R.drawable.dcloud_circle_black_progress;
        DRAWBLE_PROGRESSBAR_WHITE_CIRCLE = R.drawable.dcloud_circle_white_progress;
        DRAWEBL_SHADOW_LEFT = R.drawable.dcloud_shadow_left;
        STREAMAPP_CUSTOM_ALERT_DIALOG_LAYOUT = R.layout.dcloud_custom_alert_dialog_layout;
        UNI_CUSTOM_PRIVACY_DIALOG_LAYOUT = R.layout.dcloud_custom_privacy_dialog_layout;
        STREAMAPP_CUSTOM_ALERT_DIALOG_TITLE = i;
        STREAMAPP_CUSTOM_ALERT_DIALOG_CHECKBOX = R.id.checkbox;
        STREAMAPP_CUSTOM_ALERT_DIALOG_SURE = R.id.sure;
        STREAMAPP_CUSTOM_ALERT_DIALOG_CANCEL = R.id.cancel;
        STREAMAPP_CUSTOM_ALERT_DIALOG_CUSTOM_LAYOUT = R.id.customLayout;
        WEBVIEW_ACTIVITY_LAYOUT = R.layout.webview_layout;
        WEBVIEW_ACTIVITY_LAYOUT_BACK = R.id.back;
        WEBVIEW_ACTIVITY_LAYOUT_CLOSE = R.id.close;
        WEBVIEW_ACTIVITY_LAYOUT_TITLE = i;
        WEBVIEW_ACTIVITY_LAYOUT_MENU = R.id.menu;
        WEBVIEW_ACTIVITY_LAYOUT_REFRESH = R.id.refresh;
        WEBVIEW_ACTIVITY_LAYOUT_CONTENT = R.id.content;
        WEBVIEW_ACTIVITY_LAYOUT_WEBVIEW = R.id.webview;
        ANIM_DCLOUD_SLIDE_IN_FROM_RIGHT = R.anim.dcloud_slide_in_from_right;
        ANIM_DCLOUD_SLIDE_OUT_TO_RIGHT = R.anim.dcloud_slide_out_to_right;
        DRAWABLE_DCLOUD_WEBVIEW_DOWNLOAD_PIN = R.drawable.offline_pin;
        DRAWABLE_DCLOUD_WEBVIEW_DOWNLOAD_PIN_AROUND = R.drawable.offline_pin_round;
        WEBVIEW_ACTIVITY_LAYOUT_ACTS_STYLE_ActionSheetStyleIOS7 = R.style.ActionSheetStyleIOS7;
        DCLOUD_SHORTCUT_PERMISSION_GUIDE_LAYOUT = R.layout.dcloud_shortcut_permission_guide_layout;
        DCLOUD_GUIDE_CLOSE = R.id.dcloud_guide_close;
        DCLOUD_GUIDE_PLAY_LAYOUT = R.id.dcloud_guide_play_layout;
        DCLOUD_GUIDE_GIFVIEW = R.id.dcloud_guide_gifview;
        DCLOUD_GUIDE_PLAY = R.id.dcloud_guide_play;
        DCLOUD_GUIDE_TIP = R.id.dcloud_guide_tip;
        STYLE_GIFVIEW = R.styleable.GIFVIEW;
        STYLE_GIFVIEW_gifSrc = R.styleable.GIFVIEW_gifSrc;
        STYLE_GIFVIEW_authPlay = R.styleable.GIFVIEW_authPlay;
        STYLE_GIFVIEW_playCount = R.styleable.GIFVIEW_playCount;
        DCLOUD_GUIDE_GIF_MEIZU = R.drawable.dcloud_shortcut_guide_meizu;
        DCLOUD_GUIDE_GIF_XIAOMI = R.drawable.dcloud_shortcut_guide_xiaomi;
        DCLOUD_GUIDE_GIF_HUAWEI = R.drawable.dcloud_shortcut_guide_huawei;
        DCLOUD_SYNC_DEBUD_MESSAGE = R.string.dcloud_sync_debug_message;
        DCLOUD_PACKAGE_NAME_BASE = R.string.dcloud_package_name_base_application;
    }

    public static int getInt(Context context, String str, String str2) {
        try {
            return context.getResources().getIdentifier(str2, str, context.getPackageName());
        } catch (Exception e) {
            if (!BaseInfo.SyncDebug) {
                return 0;
            }
            e.printStackTrace();
            return 0;
        }
    }

    public static String getStringValve(Context context, String str) {
        try {
            return context.getString(context.getResources().getIdentifier(str, "string", context.getPackageName()));
        } catch (Exception e) {
            if (!BaseInfo.SyncDebug) {
                return "";
            }
            e.printStackTrace();
            return "";
        }
    }
}
