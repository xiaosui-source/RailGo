package io.dcloud.feature.weex.extend.result;

import android.util.Pair;
import com.alibaba.fastjson.JSONObject;
import com.taobao.weex.adapter.IWXUserTrackAdapter;
import com.taobao.weex.common.Constants;
import com.taobao.weex.el.parse.Operators;
import com.taobao.weex.ui.component.WXImage;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes2.dex */
public abstract class Result {
    public IError cause;
    public JSONObject data;
    public int errCode;
    public String errMsg;
    public String errSubject;

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    public static class AggregateError implements IError {
        public String[] errors;
        public String message;
        public String name;

        public AggregateError(String str, String[] strArr, String str2) {
            this.name = str;
            this.errors = strArr;
            this.message = str2;
        }

        @Override // io.dcloud.feature.weex.extend.result.Result.IError
        public JSONObject toJsonObject() {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("name", (Object) this.name);
            if (this.errors != null) {
                StringBuilder sb = new StringBuilder(Operators.ARRAY_START_STR);
                for (String str : this.errors) {
                    sb.append(str);
                }
                sb.append(Operators.ARRAY_END_STR);
                jSONObject.put("errors", (Object) sb);
            }
            String str2 = this.message;
            if (str2 != null) {
                jSONObject.put("message", (Object) str2);
            }
            return jSONObject;
        }
    }

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    public interface IError {
        JSONObject toJsonObject();
    }

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    public static class SourceError implements IError {
        public static final int EMPTY_CODE = -999999;
        public SourceError cause;
        public int code;
        public String message;
        public String subject;

        public SourceError(String str, int i, String str2, SourceError sourceError) {
            this.subject = str;
            this.code = i;
            this.message = str2;
            this.cause = sourceError;
        }

        @Override // io.dcloud.feature.weex.extend.result.Result.IError
        public JSONObject toJsonObject() {
            JSONObject jSONObject = new JSONObject();
            String str = this.subject;
            if (str != null) {
                jSONObject.put("subject", (Object) str);
            }
            int i = this.code;
            if (i != -999999) {
                jSONObject.put("code", (Object) Integer.valueOf(i));
            }
            String str2 = this.message;
            if (str2 != null) {
                jSONObject.put("message", (Object) str2);
            }
            SourceError sourceError = this.cause;
            if (sourceError != null) {
                jSONObject.put("cause", (Object) sourceError.toJsonObject());
            }
            return jSONObject;
        }
    }

    public Result(String str, int i, String str2) {
        this.errSubject = str;
        this.errCode = i;
        this.errMsg = str2;
    }

    @SafeVarargs
    public static JSONObject boxCallBackResult(boolean z, Pair<String, Object>... pairArr) {
        JSONObject jSONObject = new JSONObject();
        if (z) {
            jSONObject.put("type", (Object) WXImage.SUCCEED);
        } else {
            jSONObject.put("type", (Object) Constants.Event.FAIL);
        }
        for (Pair<String, Object> pair : pairArr) {
            if (pair != null) {
                jSONObject.put((String) pair.first, pair.second);
            }
        }
        return jSONObject;
    }

    public static JSONObject boxFailResult(Result result) {
        return boxCallBackResult(false, new Pair("errSubject", result.errSubject), new Pair(IWXUserTrackAdapter.MONITOR_ERROR_CODE, Integer.valueOf(result.errCode)), new Pair(IWXUserTrackAdapter.MONITOR_ERROR_MSG, result.errMsg), result.cause != null ? new Pair("cause", result.cause.toJsonObject()) : null, result.data != null ? new Pair("data", result.data) : null);
    }

    public static JSONObject boxSuccessResult(Object obj) {
        return boxCallBackResult(true, new Pair("data", obj));
    }
}
