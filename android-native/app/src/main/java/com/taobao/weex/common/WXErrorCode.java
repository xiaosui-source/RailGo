package com.taobao.weex.common;

import com.taobao.weex.appfram.navigator.WXNavigatorModule;
import com.taobao.weex.performance.WXInstanceApm;
import com.taobao.weex.ui.module.WXDomModule;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'WX_ERR_LOAD_SO' uses external variables
	at jadx.core.dex.visitors.EnumVisitor.createEnumFieldByConstructor(EnumVisitor.java:451)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByField(EnumVisitor.java:372)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByWrappedInsn(EnumVisitor.java:337)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromFilledArray(EnumVisitor.java:322)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:262)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInvoke(EnumVisitor.java:293)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:266)
	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:151)
	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:100)
 */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public final class WXErrorCode {
    private static final /* synthetic */ WXErrorCode[] $VALUES;
    public static final WXErrorCode WHITE_SCREEN_RESPONSE_TIMEOUT;
    public static final WXErrorCode WX_DEGRAD_EAGLE_RENDER_ERROR;
    public static final WXErrorCode WX_DEGRAD_ERR;
    public static final WXErrorCode WX_DEGRAD_ERR_BUNDLE_CONTENTTYPE_ERROR;
    public static final WXErrorCode WX_DEGRAD_ERR_INSTANCE_CREATE_FAILED;
    public static final WXErrorCode WX_DEGRAD_ERR_INSTANCE_CREATE_FAILED_JS;
    public static final WXErrorCode WX_DEGRAD_ERR_NETWORK_BUNDLE_DOWNLOAD_FAILED;
    public static final WXErrorCode WX_DEGRAD_ERR_NETWORK_CHECK_CONTENT_LENGTH_FAILED;
    public static final WXErrorCode WX_DEGRAD_ERR_OTHER_CAUSE_DEGRADTOH5;
    public static final WXErrorCode WX_ERROR_DOM_CREATEFINISH;
    public static final WXErrorCode WX_ERROR_DOM_REFRESHFINISH;
    public static final WXErrorCode WX_ERROR_MOVE_RENDER_OBJECT_OUT_OF_BOUNDS;
    public static final WXErrorCode WX_ERROR_WHITE_SCREEN;
    public static final WXErrorCode WX_ERR_BAD_SO;
    public static final WXErrorCode WX_ERR_COPY_FROM_APK;
    public static final WXErrorCode WX_ERR_DOM_ADDELEMENT;
    public static final WXErrorCode WX_ERR_DOM_ADDEVENT;
    public static final WXErrorCode WX_ERR_DOM_CREATEBODY;
    public static final WXErrorCode WX_ERR_DOM_MOVEELEMENT;
    public static final WXErrorCode WX_ERR_DOM_REMOVEELEMENT;
    public static final WXErrorCode WX_ERR_DOM_REMOVEEVENT;
    public static final WXErrorCode WX_ERR_DOM_SCROLLTO;
    public static final WXErrorCode WX_ERR_DOM_UPDATEATTRS;
    public static final WXErrorCode WX_ERR_DOM_UPDATESTYLE;
    public static final WXErrorCode WX_ERR_FIRST_DOM_ACTION_EXCEPTION;
    public static final WXErrorCode WX_ERR_HASH_MAP_TMP;
    public static final WXErrorCode WX_ERR_INVOKE_NATIVE;
    public static final WXErrorCode WX_ERR_JSBUNDLE_DOWNLOAD;
    public static final WXErrorCode WX_ERR_JSBUNDLE_EMPTY;
    public static final WXErrorCode WX_ERR_JSC_CRASH;
    public static final WXErrorCode WX_ERR_JSDOWNLOAD_END;
    public static final WXErrorCode WX_ERR_JSDOWNLOAD_START;
    public static final WXErrorCode WX_ERR_JSFUNC_PARAM;
    public static final WXErrorCode WX_ERR_JSON_OBJECT;
    public static final WXErrorCode WX_ERR_JS_EXECUTE;
    public static final WXErrorCode WX_ERR_JS_FRAMEWORK;
    public static final WXErrorCode WX_ERR_JS_REINIT_FRAMEWORK;
    public static final WXErrorCode WX_ERR_LOAD_JSLIB;
    public static final WXErrorCode WX_ERR_LOAD_SO;
    public static final WXErrorCode WX_ERR_RELOAD_PAGE;
    public static final WXErrorCode WX_ERR_RELOAD_PAGE_EXCEED_LIMIT;
    public static final WXErrorCode WX_ERR_SINGLE_PROCESS_DLOPEN_FILE_NOT_EXIST;
    public static final WXErrorCode WX_ERR_SINGLE_PROCESS_DLOPEN_FLAIED;
    public static final WXErrorCode WX_ERR_SINGLE_PROCESS_DLSYM_FAILED;
    public static final WXErrorCode WX_ERR_SINGLE_PROCESS_JS_FRAMEWORK;
    public static final WXErrorCode WX_ERR_TEST;
    public static final WXErrorCode WX_JS_FRAMEWORK_INIT_FAILED;
    public static final WXErrorCode WX_JS_FRAMEWORK_INIT_FAILED_FIND_ICU_TIMEOUT;
    public static final WXErrorCode WX_JS_FRAMEWORK_INIT_FAILED_PARAMS_NULL;
    public static final WXErrorCode WX_JS_FRAMEWORK_INIT_MULPROCESS_FAILED;
    public static final WXErrorCode WX_JS_FRAMEWORK_INIT_SINGLE_PROCESS_SUCCESS;
    public static final WXErrorCode WX_JS_FRAMEWORK_INIT_SUCCESS;
    public static final WXErrorCode WX_JS_FRAMEWORK_REINIT_MULPROCESS_FAILED;
    public static final WXErrorCode WX_JS_FRAMEWORK_REINIT_SUCCESS;
    public static final WXErrorCode WX_KEY_EXCEPTION_HERON;
    public static final WXErrorCode WX_KEY_EXCEPTION_HERON_RENDER;
    public static final WXErrorCode WX_KEY_EXCEPTION_INVOKE_BRIDGE;
    public static final WXErrorCode WX_KEY_EXCEPTION_INVOKE_JSSERVICE_EXECUTE;
    public static final WXErrorCode WX_KEY_EXCEPTION_INVOKE_REGISTER_COMPONENT;
    public static final WXErrorCode WX_KEY_EXCEPTION_INVOKE_REGISTER_CONTENT_FAILED;
    public static final WXErrorCode WX_KEY_EXCEPTION_INVOKE_REGISTER_MODULES;
    public static final WXErrorCode WX_KEY_EXCEPTION_JS_DOWNLOAD;
    public static final WXErrorCode WX_KEY_EXCEPTION_JS_DOWNLOAD_FAILED;
    public static final WXErrorCode WX_KEY_EXCEPTION_NO_BUNDLE_TYPE;
    public static final WXErrorCode WX_KEY_EXCEPTION_SDK_INIT;
    public static final WXErrorCode WX_KEY_EXCEPTION_SDK_INIT_CPU_NOT_SUPPORT;
    public static final WXErrorCode WX_KEY_EXCEPTION_SDK_INIT_JSFM_INIT_FAILED;
    public static final WXErrorCode WX_KEY_EXCEPTION_SDK_INIT_TABLE_NOT_SUPPORT;
    public static final WXErrorCode WX_KEY_EXCEPTION_VALIDAPPKEY;
    public static final WXErrorCode WX_KEY_EXCEPTION_WXBRIDGE;
    public static final WXErrorCode WX_KEY_EXCEPTION_WXBRIDGE_EXCEPTION;
    public static final WXErrorCode WX_RENDER_ERR_BRIDGE_ARG_NULL;
    public static final WXErrorCode WX_RENDER_ERR_COMPONENT_ATTR_KEY;
    public static final WXErrorCode WX_RENDER_ERR_COMPONENT_NOT_REGISTER;
    public static final WXErrorCode WX_RENDER_ERR_CONTAINER_TYPE;
    public static final WXErrorCode WX_RENDER_ERR_INSTANCE_ID_NULL;
    public static final WXErrorCode WX_RENDER_ERR_JS_CREATE_INSTANCE;
    public static final WXErrorCode WX_RENDER_ERR_JS_CREATE_INSTANCE_CONTEXT;
    public static final WXErrorCode WX_RENDER_ERR_LAYER_OVERFLOW;
    public static final WXErrorCode WX_RENDER_ERR_LIST_INVALID_COLUMN_COUNT;
    public static final WXErrorCode WX_RENDER_ERR_NATIVE_RUNTIME;
    public static final WXErrorCode WX_RENDER_ERR_NULL_KEY;
    public static final WXErrorCode WX_RENDER_ERR_TEXTURE_SETBACKGROUND;
    public static final WXErrorCode WX_RENDER_ERR_TRANSITION;
    public static final WXErrorCode WX_RENDER_WAR_GPU_LIMIT_LAYOUT;
    public static final WXErrorCode WX_SUCCESS;
    private String appendMsg = "";
    private String args;
    private String errorCode;
    private String errorMsg;
    private ErrorGroup mErrorGroup;
    private ErrorType mErrorType;

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    public enum ErrorGroup {
        JS,
        NATIVE
    }

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    public enum ErrorType {
        JS_ERROR,
        NATIVE_ERROR,
        RENDER_ERROR,
        DEGRAD_ERROR,
        DOWN_LOAD_ERROR
    }

    private static /* synthetic */ WXErrorCode[] $values() {
        return new WXErrorCode[]{WX_ERR_LOAD_SO, WX_ERR_LOAD_JSLIB, WX_ERR_BAD_SO, WX_ERR_COPY_FROM_APK, WX_ERR_JSFUNC_PARAM, WX_ERR_JSON_OBJECT, WX_ERR_INVOKE_NATIVE, WX_ERR_JS_EXECUTE, WX_SUCCESS, WX_ERR_DOM_CREATEBODY, WX_ERR_DOM_UPDATEATTRS, WX_ERR_DOM_UPDATESTYLE, WX_ERR_DOM_ADDELEMENT, WX_ERR_DOM_REMOVEELEMENT, WX_ERR_DOM_MOVEELEMENT, WX_ERR_DOM_ADDEVENT, WX_ERR_DOM_REMOVEEVENT, WX_ERROR_DOM_CREATEFINISH, WX_ERROR_DOM_REFRESHFINISH, WX_ERR_DOM_SCROLLTO, WX_ERR_RELOAD_PAGE, WX_ERR_RELOAD_PAGE_EXCEED_LIMIT, WX_ERROR_WHITE_SCREEN, WHITE_SCREEN_RESPONSE_TIMEOUT, WX_ERR_JSC_CRASH, WX_ERR_FIRST_DOM_ACTION_EXCEPTION, WX_ERR_JSDOWNLOAD_START, WX_ERR_JSBUNDLE_DOWNLOAD, WX_ERR_JSBUNDLE_EMPTY, WX_ERR_JSDOWNLOAD_END, WX_JS_FRAMEWORK_INIT_SUCCESS, WX_JS_FRAMEWORK_REINIT_SUCCESS, WX_ERR_JS_FRAMEWORK, WX_ERR_JS_REINIT_FRAMEWORK, WX_ERR_SINGLE_PROCESS_DLOPEN_FILE_NOT_EXIST, WX_ERR_SINGLE_PROCESS_DLOPEN_FLAIED, WX_ERR_SINGLE_PROCESS_DLSYM_FAILED, WX_ERR_SINGLE_PROCESS_JS_FRAMEWORK, WX_JS_FRAMEWORK_INIT_MULPROCESS_FAILED, WX_JS_FRAMEWORK_REINIT_MULPROCESS_FAILED, WX_JS_FRAMEWORK_INIT_FAILED, WX_JS_FRAMEWORK_INIT_SINGLE_PROCESS_SUCCESS, WX_JS_FRAMEWORK_INIT_FAILED_PARAMS_NULL, WX_JS_FRAMEWORK_INIT_FAILED_FIND_ICU_TIMEOUT, WX_KEY_EXCEPTION_SDK_INIT, WX_KEY_EXCEPTION_SDK_INIT_CPU_NOT_SUPPORT, WX_KEY_EXCEPTION_SDK_INIT_TABLE_NOT_SUPPORT, WX_KEY_EXCEPTION_SDK_INIT_JSFM_INIT_FAILED, WX_KEY_EXCEPTION_INVOKE_BRIDGE, WX_KEY_EXCEPTION_INVOKE_REGISTER_CONTENT_FAILED, WX_KEY_EXCEPTION_INVOKE_JSSERVICE_EXECUTE, WX_KEY_EXCEPTION_INVOKE_REGISTER_MODULES, WX_KEY_EXCEPTION_INVOKE_REGISTER_COMPONENT, WX_KEY_EXCEPTION_JS_DOWNLOAD, WX_KEY_EXCEPTION_JS_DOWNLOAD_FAILED, WX_KEY_EXCEPTION_WXBRIDGE, WX_KEY_EXCEPTION_WXBRIDGE_EXCEPTION, WX_RENDER_ERR_JS_CREATE_INSTANCE, WX_RENDER_ERR_JS_CREATE_INSTANCE_CONTEXT, WX_RENDER_ERR_LAYER_OVERFLOW, WX_RENDER_ERR_NULL_KEY, WX_RENDER_ERR_NATIVE_RUNTIME, WX_RENDER_ERR_COMPONENT_NOT_REGISTER, WX_RENDER_ERR_COMPONENT_ATTR_KEY, WX_RENDER_ERR_BRIDGE_ARG_NULL, WX_RENDER_ERR_CONTAINER_TYPE, WX_RENDER_ERR_TRANSITION, WX_RENDER_ERR_INSTANCE_ID_NULL, WX_RENDER_ERR_LIST_INVALID_COLUMN_COUNT, WX_RENDER_ERR_TEXTURE_SETBACKGROUND, WX_RENDER_WAR_GPU_LIMIT_LAYOUT, WX_KEY_EXCEPTION_HERON, WX_KEY_EXCEPTION_HERON_RENDER, WX_KEY_EXCEPTION_NO_BUNDLE_TYPE, WX_KEY_EXCEPTION_VALIDAPPKEY, WX_DEGRAD_ERR, WX_DEGRAD_ERR_INSTANCE_CREATE_FAILED, WX_DEGRAD_ERR_NETWORK_BUNDLE_DOWNLOAD_FAILED, WX_DEGRAD_ERR_NETWORK_CHECK_CONTENT_LENGTH_FAILED, WX_DEGRAD_ERR_BUNDLE_CONTENTTYPE_ERROR, WX_DEGRAD_ERR_OTHER_CAUSE_DEGRADTOH5, WX_DEGRAD_ERR_INSTANCE_CREATE_FAILED_JS, WX_DEGRAD_EAGLE_RENDER_ERROR, WX_ERR_HASH_MAP_TMP, WX_ERROR_MOVE_RENDER_OBJECT_OUT_OF_BOUNDS, WX_ERR_TEST};
    }

    static {
        ErrorType errorType = ErrorType.NATIVE_ERROR;
        ErrorGroup errorGroup = ErrorGroup.NATIVE;
        WX_ERR_LOAD_SO = new WXErrorCode("WX_ERR_LOAD_SO", 0, "-2001", "load so error", errorType, errorGroup);
        WX_ERR_LOAD_JSLIB = new WXErrorCode("WX_ERR_LOAD_JSLIB", 1, "-2002", "unzip JSLib error!", errorType, errorGroup);
        WX_ERR_BAD_SO = new WXErrorCode("WX_ERR_BAD_SO", 2, "-2003", "so size error, to reload apk so", errorType, errorGroup);
        WX_ERR_COPY_FROM_APK = new WXErrorCode("WX_ERR_COPY_FROM_APK", 3, "-2007", "system load so error，copy from APK also error!", errorType, errorGroup);
        WX_ERR_JSFUNC_PARAM = new WXErrorCode("WX_ERR_JSFUNC_PARAM", 4, "-2009", "JS params error!", errorType, errorGroup);
        WX_ERR_JSON_OBJECT = new WXErrorCode("WX_ERR_JSON_OBJECT", 5, "-2008", "transform JSON->OBJ  error!", errorType, errorGroup);
        WX_ERR_INVOKE_NATIVE = new WXErrorCode("WX_ERR_INVOKE_NATIVE", 6, "-2012", "Native-> Call ->JS  error!", errorType, errorGroup);
        ErrorType errorType2 = ErrorType.JS_ERROR;
        ErrorGroup errorGroup2 = ErrorGroup.JS;
        WX_ERR_JS_EXECUTE = new WXErrorCode("WX_ERR_JS_EXECUTE", 7, "-2013", "JavaScript execute error!", errorType2, errorGroup2);
        WX_SUCCESS = new WXErrorCode(WXNavigatorModule.MSG_SUCCESS, 8, WXInstanceApm.VALUE_ERROR_CODE_DEFAULT, "successful", errorType, errorGroup);
        WX_ERR_DOM_CREATEBODY = new WXErrorCode("WX_ERR_DOM_CREATEBODY", 9, "-2100", "createBody error", errorType, errorGroup);
        WX_ERR_DOM_UPDATEATTRS = new WXErrorCode("WX_ERR_DOM_UPDATEATTRS", 10, "-2101", "updateAttrs error", errorType, errorGroup);
        WX_ERR_DOM_UPDATESTYLE = new WXErrorCode("WX_ERR_DOM_UPDATESTYLE", 11, "-2102", "updateStyle error", errorType, errorGroup);
        WX_ERR_DOM_ADDELEMENT = new WXErrorCode("WX_ERR_DOM_ADDELEMENT", 12, "-2103", "addElement error", errorType, errorGroup);
        WX_ERR_DOM_REMOVEELEMENT = new WXErrorCode("WX_ERR_DOM_REMOVEELEMENT", 13, "-2104", "removeElement error", errorType, errorGroup);
        WX_ERR_DOM_MOVEELEMENT = new WXErrorCode("WX_ERR_DOM_MOVEELEMENT", 14, "-2105", "moveElement error", errorType, errorGroup);
        WX_ERR_DOM_ADDEVENT = new WXErrorCode("WX_ERR_DOM_ADDEVENT", 15, "-2106", "addEvent error", errorType, errorGroup);
        WX_ERR_DOM_REMOVEEVENT = new WXErrorCode("WX_ERR_DOM_REMOVEEVENT", 16, "-2107", "removeEvent error", errorType, errorGroup);
        WX_ERROR_DOM_CREATEFINISH = new WXErrorCode("WX_ERROR_DOM_CREATEFINISH", 17, "-2108", "createFinish error", errorType, errorGroup);
        WX_ERROR_DOM_REFRESHFINISH = new WXErrorCode("WX_ERROR_DOM_REFRESHFINISH", 18, "-2109", "refreshFinish error", errorType, errorGroup);
        WX_ERR_DOM_SCROLLTO = new WXErrorCode("WX_ERR_DOM_SCROLLTO", 19, "-2110", WXDomModule.SCROLL_TO_ELEMENT, errorType, errorGroup);
        WX_ERR_RELOAD_PAGE = new WXErrorCode("WX_ERR_RELOAD_PAGE", 20, "-2111", "reloadPage", errorType, errorGroup);
        WX_ERR_RELOAD_PAGE_EXCEED_LIMIT = new WXErrorCode("WX_ERR_RELOAD_PAGE_EXCEED_LIMIT", 21, "-2114", "RELOAD_PAGE_EXCEED_LIMIT", errorType, errorGroup);
        ErrorType errorType3 = ErrorType.RENDER_ERROR;
        WX_ERROR_WHITE_SCREEN = new WXErrorCode("WX_ERROR_WHITE_SCREEN", 22, "-2116", "WHITE_SCREEN", errorType3, errorGroup2);
        WHITE_SCREEN_RESPONSE_TIMEOUT = new WXErrorCode("WHITE_SCREEN_RESPONSE_TIMEOUT", 23, "-2117", "WHITE_SCREEN_RESPONSE_TIMEOUT", errorType3, errorGroup2);
        WX_ERR_JSC_CRASH = new WXErrorCode("WX_ERR_JSC_CRASH", 24, "-2112", "weexjscCrash", errorType, errorGroup);
        WX_ERR_FIRST_DOM_ACTION_EXCEPTION = new WXErrorCode("WX_ERR_FIRST_DOM_ACTION_EXCEPTION", 25, "-2113", "dom action is invalid ", errorType, errorGroup);
        WX_ERR_JSDOWNLOAD_START = new WXErrorCode("WX_ERR_JSDOWNLOAD_START", 26, "-2201", "js bundle download start", errorType, errorGroup);
        WX_ERR_JSBUNDLE_DOWNLOAD = new WXErrorCode("WX_ERR_JSBUNDLE_DOWNLOAD", 27, "-2299", "js bundle download err", errorType, errorGroup);
        WX_ERR_JSBUNDLE_EMPTY = new WXErrorCode("WX_ERR_JSBUNDLE_EMPTY", 28, "-2203", "js bundle empty", errorType, errorGroup);
        WX_ERR_JSDOWNLOAD_END = new WXErrorCode("WX_ERR_JSDOWNLOAD_END", 29, "-2299", "js bundle download end", errorType, errorGroup);
        WX_JS_FRAMEWORK_INIT_SUCCESS = new WXErrorCode("WX_JS_FRAMEWORK_INIT_SUCCESS", 30, "-1000", "js framework success", errorType, errorGroup);
        WX_JS_FRAMEWORK_REINIT_SUCCESS = new WXErrorCode("WX_JS_FRAMEWORK_REINIT_SUCCESS", 31, "-1001", "js framework reinit success", errorType, errorGroup);
        WX_ERR_JS_FRAMEWORK = new WXErrorCode("WX_ERR_JS_FRAMEWORK", 32, "-1002", "js framework error", errorType, errorGroup);
        WX_ERR_JS_REINIT_FRAMEWORK = new WXErrorCode("WX_ERR_JS_REINIT_FRAMEWORK", 33, "-1003", "js reinit framework error", errorType, errorGroup);
        WX_ERR_SINGLE_PROCESS_DLOPEN_FILE_NOT_EXIST = new WXErrorCode("WX_ERR_SINGLE_PROCESS_DLOPEN_FILE_NOT_EXIST", 34, "-1004", "so file does not exist", errorType, errorGroup);
        WX_ERR_SINGLE_PROCESS_DLOPEN_FLAIED = new WXErrorCode("WX_ERR_SINGLE_PROCESS_DLOPEN_FLAIED", 35, "-1005", "dlopen so file failed", errorType, errorGroup);
        WX_ERR_SINGLE_PROCESS_DLSYM_FAILED = new WXErrorCode("WX_ERR_SINGLE_PROCESS_DLSYM_FAILED", 36, "-1006", "find function from so file failed", errorType, errorGroup);
        WX_ERR_SINGLE_PROCESS_JS_FRAMEWORK = new WXErrorCode("WX_ERR_SINGLE_PROCESS_JS_FRAMEWORK", 37, "-1007", "js framework  init singleProcess failed", errorType, errorGroup);
        WX_JS_FRAMEWORK_INIT_MULPROCESS_FAILED = new WXErrorCode("WX_JS_FRAMEWORK_INIT_MULPROCESS_FAILED", 38, "-1008", "js framework init multiProcess failed", errorType, errorGroup);
        WX_JS_FRAMEWORK_REINIT_MULPROCESS_FAILED = new WXErrorCode("WX_JS_FRAMEWORK_REINIT_MULPROCESS_FAILED", 39, "-1009", "js framework reinit multiProcess failed", errorType, errorGroup);
        WX_JS_FRAMEWORK_INIT_FAILED = new WXErrorCode("WX_JS_FRAMEWORK_INIT_FAILED", 40, "-1010", "js framework init failed", errorType, errorGroup);
        WX_JS_FRAMEWORK_INIT_SINGLE_PROCESS_SUCCESS = new WXErrorCode("WX_JS_FRAMEWORK_INIT_SINGLE_PROCESS_SUCCESS", 41, "-1011", "js framework init success in single process", errorType, errorGroup);
        WX_JS_FRAMEWORK_INIT_FAILED_PARAMS_NULL = new WXErrorCode("WX_JS_FRAMEWORK_INIT_FAILED_PARAMS_NULL", 42, "-1012", "js framework init failed due to params null", errorType, errorGroup);
        WX_JS_FRAMEWORK_INIT_FAILED_FIND_ICU_TIMEOUT = new WXErrorCode("WX_JS_FRAMEWORK_INIT_FAILED_FIND_ICU_TIMEOUT", 43, "-1013", "find icu failed", errorType, errorGroup);
        WX_KEY_EXCEPTION_SDK_INIT = new WXErrorCode("WX_KEY_EXCEPTION_SDK_INIT", 44, "-9000", "[WX_KEY_EXCEPTION_SDK_INIT]", errorType, errorGroup);
        WX_KEY_EXCEPTION_SDK_INIT_CPU_NOT_SUPPORT = new WXErrorCode("WX_KEY_EXCEPTION_SDK_INIT_CPU_NOT_SUPPORT", 45, "-9001", "[WX_KEY_EXCEPTION_SDK_INIT_CPU_NOT_SUPPORT] for android cpu is x86", errorType, errorGroup);
        WX_KEY_EXCEPTION_SDK_INIT_TABLE_NOT_SUPPORT = new WXErrorCode("WX_KEY_EXCEPTION_SDK_INIT_TABLE_NOT_SUPPORT", 46, "-9002", "[WX_KEY_EXCEPTION_SDK_INIT_TABLE_NOT_SUPPORT] for device isTabletDevice", errorType, errorGroup);
        WX_KEY_EXCEPTION_SDK_INIT_JSFM_INIT_FAILED = new WXErrorCode("WX_KEY_EXCEPTION_SDK_INIT_JSFM_INIT_FAILED", 47, "-9003", "[WX_KEY_EXCEPTION_SDK_INIT_JSFM_INIT_FAILED] for jsfm init failed|detail error is:", errorType, errorGroup);
        WX_KEY_EXCEPTION_INVOKE_BRIDGE = new WXErrorCode("WX_KEY_EXCEPTION_INVOKE_BRIDGE", 48, "-9100", "[WX_KEY_EXCEPTION_INVOKE_BRIDGE]", errorType, errorGroup);
        WX_KEY_EXCEPTION_INVOKE_REGISTER_CONTENT_FAILED = new WXErrorCode("WX_KEY_EXCEPTION_INVOKE_REGISTER_CONTENT_FAILED", 49, "-9101", "[WX_KEY_EXCEPTION_INVOKE_REGISTER_CONTENT_FAILED] details", errorType, errorGroup);
        WX_KEY_EXCEPTION_INVOKE_JSSERVICE_EXECUTE = new WXErrorCode("WX_KEY_EXCEPTION_INVOKE_JSSERVICE_EXECUTE", 50, "-9102", "[WX_KEY_EXCEPTION_INVOKE_JSSERVICE_EXECUTE] details", errorType, errorGroup);
        WX_KEY_EXCEPTION_INVOKE_REGISTER_MODULES = new WXErrorCode("WX_KEY_EXCEPTION_INVOKE_REGISTER_MODULES", 51, "-9103", "[WX_KEY_EXCEPTION_INVOKE_REGISTER_MODULES] details", errorType, errorGroup);
        WX_KEY_EXCEPTION_INVOKE_REGISTER_COMPONENT = new WXErrorCode("WX_KEY_EXCEPTION_INVOKE_REGISTER_COMPONENT", 52, "-9104", "[WX_KEY_EXCEPTION_INVOKE_REGISTER_COMPONENT] details", errorType, errorGroup);
        ErrorType errorType4 = ErrorType.DOWN_LOAD_ERROR;
        WX_KEY_EXCEPTION_JS_DOWNLOAD = new WXErrorCode("WX_KEY_EXCEPTION_JS_DOWNLOAD", 53, "-9200", "[WX_KEY_EXCEPTION_JS_DOWNLOAD]|", errorType4, errorGroup);
        WX_KEY_EXCEPTION_JS_DOWNLOAD_FAILED = new WXErrorCode("WX_KEY_EXCEPTION_JS_DOWNLOAD_FAILED", 54, "-9201", "[WX_KEY_EXCEPTION_JS_DOWNLOAD_FAILED] | details", errorType4, errorGroup);
        WX_KEY_EXCEPTION_WXBRIDGE = new WXErrorCode("WX_KEY_EXCEPTION_WXBRIDGE", 55, "-9400", "[js excute runtime error] detail js stack -> ", errorType2, errorGroup2);
        WX_KEY_EXCEPTION_WXBRIDGE_EXCEPTION = new WXErrorCode("WX_KEY_EXCEPTION_WXBRIDGE_EXCEPTION", 56, "-9401", "[js excute runtime error] detail js stack -> ", errorType2, errorGroup2);
        WX_RENDER_ERR_JS_CREATE_INSTANCE = new WXErrorCode("WX_RENDER_ERR_JS_CREATE_INSTANCE", 57, "-9600", "white screen cause create instance failed,check js stack ->", errorType3, errorGroup2);
        WX_RENDER_ERR_JS_CREATE_INSTANCE_CONTEXT = new WXErrorCode("WX_RENDER_ERR_JS_CREATE_INSTANCE_CONTEXT", 58, "-9700", "white screen cause create instanceContext failed,check js stack ->", errorType3, errorGroup2);
        WX_RENDER_ERR_LAYER_OVERFLOW = new WXErrorCode("WX_RENDER_ERR_LAYER_OVERFLOW", 59, "-9602", "WX_RENDER_ERR_LAYER_OVERFLOW", errorType, errorGroup);
        WX_RENDER_ERR_NULL_KEY = new WXErrorCode("WX_RENDER_ERR_NULL_KEY", 60, "-9603", "WX_RENDER_ERR_NULL_KEY", errorType, errorGroup);
        WX_RENDER_ERR_NATIVE_RUNTIME = new WXErrorCode("WX_RENDER_ERR_NATIVE_RUNTIME", 61, "-9604", "WX_RENDER_ERR for js error", errorType3, errorGroup);
        WX_RENDER_ERR_COMPONENT_NOT_REGISTER = new WXErrorCode("WX_RENDER_ERR_COMPONENT_NOT_REGISTER", 62, "-9605", "WX_RENDER_ERR_COMPONENT_NOT_REGISTER", errorType, errorGroup);
        WX_RENDER_ERR_COMPONENT_ATTR_KEY = new WXErrorCode("WX_RENDER_ERR_COMPONENT_ATTR_KEY", 63, "-9606", "The key passed to Component.updateAttr() is not string", errorType, errorGroup2);
        WX_RENDER_ERR_BRIDGE_ARG_NULL = new WXErrorCode("WX_RENDER_ERR_BRIDGE_ARG_NULL", 64, "-9610", "WX_RENDER_ERR_BRIDGE_ARG_NULL", errorType, errorGroup);
        WX_RENDER_ERR_CONTAINER_TYPE = new WXErrorCode("WX_RENDER_ERR_CONTAINER_TYPE", 65, "-9611", "WX_RENDER_ERR_CONTAINER_TYPE", errorType2, errorGroup2);
        WX_RENDER_ERR_TRANSITION = new WXErrorCode("WX_RENDER_ERR_TRANSITION", 66, "-9616", "WX_RENDER_ERR_TRANSITION", errorType2, errorGroup2);
        WX_RENDER_ERR_INSTANCE_ID_NULL = new WXErrorCode("WX_RENDER_ERR_INSTANCE_ID_NULL", 67, "-9618", "WX_RENDER_ERR_INSTANCE_ID_NULL", errorType, errorGroup);
        WX_RENDER_ERR_LIST_INVALID_COLUMN_COUNT = new WXErrorCode("WX_RENDER_ERR_LIST_INVALID_COLUMN_COUNT", 68, "-9619", "WX_RENDER_ERR_LIST_INVALID_COLUMNJ_CONUNT", errorType2, errorGroup2);
        WX_RENDER_ERR_TEXTURE_SETBACKGROUND = new WXErrorCode("WX_RENDER_ERR_TEXTURE_SETBACKGROUND", 69, "-9620", "WX_RENDER_ERR_TEXTURE_SETBACKGROUND", errorType, errorGroup);
        WX_RENDER_WAR_GPU_LIMIT_LAYOUT = new WXErrorCode("WX_RENDER_WAR_GPU_LIMIT_LAYOUT", 70, "-9621", "WX_RENDER_WAR_GPU_LIMIT_LAYOUT", errorType2, errorGroup2);
        WX_KEY_EXCEPTION_HERON = new WXErrorCode("WX_KEY_EXCEPTION_HERON", 71, "Heron_-9900", "Error of Heron engine: ", errorType, errorGroup);
        WX_KEY_EXCEPTION_HERON_RENDER = new WXErrorCode("WX_KEY_EXCEPTION_HERON_RENDER", 72, "Heron_-9901", "Render error of Heron engine: ", errorType3, errorGroup);
        WX_KEY_EXCEPTION_NO_BUNDLE_TYPE = new WXErrorCode("WX_KEY_EXCEPTION_NO_BUNDLE_TYPE", 73, "-9801", "Fatal Error : No bundle type in js bundle head, cause white screen or memory leak!!", errorType2, errorGroup2);
        WX_KEY_EXCEPTION_VALIDAPPKEY = new WXErrorCode("WX_KEY_EXCEPTION_VALIDAPPKEY", 74, "-11001", "", errorType, errorGroup);
        ErrorType errorType5 = ErrorType.DEGRAD_ERROR;
        WX_DEGRAD_ERR = new WXErrorCode("WX_DEGRAD_ERR", 75, "-1000", "degradeToH5|Weex DegradPassivity ->", errorType5, errorGroup2);
        WX_DEGRAD_ERR_INSTANCE_CREATE_FAILED = new WXErrorCode("WX_DEGRAD_ERR_INSTANCE_CREATE_FAILED", 76, "-1001", "degradeToH5|createInstance fail|wx_create_instance_error", errorType5, errorGroup);
        WX_DEGRAD_ERR_NETWORK_BUNDLE_DOWNLOAD_FAILED = new WXErrorCode("WX_DEGRAD_ERR_NETWORK_BUNDLE_DOWNLOAD_FAILED", 77, "-1002", "|wx_network_error|js bundle download failed", errorType4, errorGroup);
        WX_DEGRAD_ERR_NETWORK_CHECK_CONTENT_LENGTH_FAILED = new WXErrorCode("WX_DEGRAD_ERR_NETWORK_CHECK_CONTENT_LENGTH_FAILED", 78, "-1003", "degradeToH5|wx_network_error|js bundle content-length check failed", errorType5, errorGroup);
        WX_DEGRAD_ERR_BUNDLE_CONTENTTYPE_ERROR = new WXErrorCode("WX_DEGRAD_ERR_BUNDLE_CONTENTTYPE_ERROR", 79, "-1004", "degradeToH5|wx_user_intercept_error |Content-Type is not application/javascript, Weex render template must be javascript, please check your request!", errorType5, errorGroup);
        WX_DEGRAD_ERR_OTHER_CAUSE_DEGRADTOH5 = new WXErrorCode("WX_DEGRAD_ERR_OTHER_CAUSE_DEGRADTOH5", 80, "-1005", "degradeToH5|for other reason|", errorType5, errorGroup);
        WX_DEGRAD_ERR_INSTANCE_CREATE_FAILED_JS = new WXErrorCode("WX_DEGRAD_ERR_INSTANCE_CREATE_FAILED_JS", 81, "-1006", "degradeToH5|createInstance fail|wx_create_instance_error", errorType5, errorGroup2);
        WX_DEGRAD_EAGLE_RENDER_ERROR = new WXErrorCode("WX_DEGRAD_EAGLE_RENDER_ERROR", 82, "Eagle_-1007", "degradeToH5|eagleRenderErr", errorType5, errorGroup);
        WX_ERR_HASH_MAP_TMP = new WXErrorCode("WX_ERR_HASH_MAP_TMP", 83, "-10010", "WX_ERR_HASH_MAP_TMP", errorType, errorGroup);
        WX_ERROR_MOVE_RENDER_OBJECT_OUT_OF_BOUNDS = new WXErrorCode("WX_ERROR_MOVE_RENDER_OBJECT_OUT_OF_BOUNDS", 84, "-2120", "Index out of bounds when move element", errorType, errorGroup2);
        WX_ERR_TEST = new WXErrorCode("WX_ERR_TEST", 85, "test", "test", errorType, errorGroup);
        $VALUES = $values();
    }

    private WXErrorCode(String str, int i, String str2, String str3, ErrorType errorType, ErrorGroup errorGroup) {
        this.errorCode = str2;
        this.errorMsg = str3;
        this.mErrorType = errorType;
        this.mErrorGroup = errorGroup;
    }

    public static WXErrorCode valueOf(String str) {
        return (WXErrorCode) Enum.valueOf(WXErrorCode.class, str);
    }

    public static WXErrorCode[] values() {
        return (WXErrorCode[]) $VALUES.clone();
    }

    public void appendErrMsg(String str) {
        this.appendMsg = str;
    }

    public String getArgs() {
        return this.args;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public ErrorGroup getErrorGroup() {
        return this.mErrorGroup;
    }

    public String getErrorMsg() {
        return this.errorMsg + this.appendMsg;
    }

    public ErrorType getErrorType() {
        return this.mErrorType;
    }

    public void setArgs(String str) {
        this.args = str;
    }
}
