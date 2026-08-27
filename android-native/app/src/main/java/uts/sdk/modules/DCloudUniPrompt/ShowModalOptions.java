package uts.sdk.modules.DCloudUniPrompt;

import com.facebook.common.util.UriUtil;
import com.taobao.weex.common.Constants;
import com.taobao.weex.ui.component.WXImage;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.uts.UTSObject;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: index.kt */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\"\b\u0016\u0018\u00002\u00020\u0001B\u008d\u0002\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0003\u0012+\b\u0002\u0010\r\u001a%\u0012\u0013\u0012\u00110\u000f¢\u0006\f\b\u0010\u0012\b\b\u0011\u0012\u0004\b\b(\u0012\u0012\u0004\u0012\u00020\u0013\u0018\u00010\u000ej\u0004\u0018\u0001`\u0014\u0012>\b\u0002\u0010\u0015\u001a8\u0012&\u0012$0\u0016j\u0011`\u0017¢\u0006\f\b\u0010\u0012\b\b\u0011\u0012\u0004\b\b(\u0012¢\u0006\f\b\u0010\u0012\b\b\u0011\u0012\u0004\b\b(\u0012\u0012\u0004\u0012\u00020\u0013\u0018\u00010\u000ej\u0004\u0018\u0001`\u0018\u0012+\b\u0002\u0010\u0019\u001a%\u0012\u0013\u0012\u00110\u001a¢\u0006\f\b\u0010\u0012\b\b\u0011\u0012\u0004\b\b(\u0012\u0012\u0004\u0012\u00020\u0013\u0018\u00010\u000ej\u0004\u0018\u0001`\u001b¢\u0006\u0004\b\u001c\u0010\u001dR\u001c\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u001f\"\u0004\b \u0010!R\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\u001f\"\u0004\b#\u0010!R\u001e\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0096\u000e¢\u0006\u0010\n\u0002\u0010(\u001a\u0004\b$\u0010%\"\u0004\b&\u0010'R\u001c\u0010\u0007\u001a\u0004\u0018\u00010\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b)\u0010\u001f\"\u0004\b*\u0010!R\u001c\u0010\b\u001a\u0004\u0018\u00010\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b+\u0010\u001f\"\u0004\b,\u0010!R\u001c\u0010\t\u001a\u0004\u0018\u00010\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b-\u0010\u001f\"\u0004\b.\u0010!R\u001c\u0010\n\u001a\u0004\u0018\u00010\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b/\u0010\u001f\"\u0004\b0\u0010!R\u001e\u0010\u000b\u001a\u0004\u0018\u00010\u0006X\u0096\u000e¢\u0006\u0010\n\u0002\u0010(\u001a\u0004\b1\u0010%\"\u0004\b2\u0010'R\u001c\u0010\f\u001a\u0004\u0018\u00010\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b3\u0010\u001f\"\u0004\b4\u0010!R=\u0010\r\u001a%\u0012\u0013\u0012\u00110\u000f¢\u0006\f\b\u0010\u0012\b\b\u0011\u0012\u0004\b\b(\u0012\u0012\u0004\u0012\u00020\u0013\u0018\u00010\u000ej\u0004\u0018\u0001`\u0014X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b5\u00106\"\u0004\b7\u00108RP\u0010\u0015\u001a8\u0012&\u0012$0\u0016j\u0011`\u0017¢\u0006\f\b\u0010\u0012\b\b\u0011\u0012\u0004\b\b(\u0012¢\u0006\f\b\u0010\u0012\b\b\u0011\u0012\u0004\b\b(\u0012\u0012\u0004\u0012\u00020\u0013\u0018\u00010\u000ej\u0004\u0018\u0001`\u0018X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b9\u00106\"\u0004\b:\u00108R=\u0010\u0019\u001a%\u0012\u0013\u0012\u00110\u001a¢\u0006\f\b\u0010\u0012\b\b\u0011\u0012\u0004\b\b(\u0012\u0012\u0004\u0012\u00020\u0013\u0018\u00010\u000ej\u0004\u0018\u0001`\u001bX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b;\u00106\"\u0004\b<\u00108¨\u0006="}, d2 = {"Luts/sdk/modules/DCloudUniPrompt/ShowModalOptions;", "Lio/dcloud/uts/UTSObject;", AbsoluteConst.JSON_KEY_TITLE, "", UriUtil.LOCAL_CONTENT_SCHEME, "showCancel", "", "cancelText", "cancelColor", "confirmText", "confirmColor", "editable", "placeholderText", WXImage.SUCCEED, "Lkotlin/Function1;", "Luts/sdk/modules/DCloudUniPrompt/ShowModalSuccess;", "Lkotlin/ParameterName;", "name", UriUtil.LOCAL_RESOURCE_SCHEME, "", "Luts/sdk/modules/DCloudUniPrompt/ShowModalSuccessCallback;", Constants.Event.FAIL, "Luts/sdk/modules/DCloudUniPrompt/IPromptError;", "Luts/sdk/modules/DCloudUniPrompt/ShowModalFail;", "Luts/sdk/modules/DCloudUniPrompt/ShowModalFailCallback;", "complete", "", "Luts/sdk/modules/DCloudUniPrompt/ShowModalCompleteCallback;", "<init>", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Boolean;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Boolean;Ljava/lang/String;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)V", "getTitle", "()Ljava/lang/String;", "setTitle", "(Ljava/lang/String;)V", "getContent", "setContent", "getShowCancel", "()Ljava/lang/Boolean;", "setShowCancel", "(Ljava/lang/Boolean;)V", "Ljava/lang/Boolean;", "getCancelText", "setCancelText", "getCancelColor", "setCancelColor", "getConfirmText", "setConfirmText", "getConfirmColor", "setConfirmColor", "getEditable", "setEditable", "getPlaceholderText", "setPlaceholderText", "getSuccess", "()Lkotlin/jvm/functions/Function1;", "setSuccess", "(Lkotlin/jvm/functions/Function1;)V", "getFail", "setFail", "getComplete", "setComplete", "uni-prompt_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public class ShowModalOptions extends UTSObject {
    private String cancelColor;
    private String cancelText;
    private Function1<Object, Unit> complete;
    private String confirmColor;
    private String confirmText;
    private String content;
    private Boolean editable;
    private Function1<? super IPromptError, Unit> fail;
    private String placeholderText;
    private Boolean showCancel;
    private Function1<? super ShowModalSuccess, Unit> success;
    private String title;

    public ShowModalOptions() {
        this(null, null, null, null, null, null, null, null, null, null, null, null, 4095, null);
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String str) {
        this.content = str;
    }

    public /* synthetic */ ShowModalOptions(String str, String str2, Boolean bool, String str3, String str4, String str5, String str6, Boolean bool2, String str7, Function1 function1, Function1 function12, Function1 function13, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : str, (i & 2) != 0 ? null : str2, (i & 4) != 0 ? true : bool, (i & 8) != 0 ? null : str3, (i & 16) != 0 ? null : str4, (i & 32) != 0 ? null : str5, (i & 64) != 0 ? null : str6, (i & 128) != 0 ? false : bool2, (i & 256) != 0 ? null : str7, (i & 512) != 0 ? null : function1, (i & 1024) != 0 ? null : function12, (i & 2048) != 0 ? null : function13);
    }

    public Boolean getShowCancel() {
        return this.showCancel;
    }

    public void setShowCancel(Boolean bool) {
        this.showCancel = bool;
    }

    public String getCancelText() {
        return this.cancelText;
    }

    public void setCancelText(String str) {
        this.cancelText = str;
    }

    public String getCancelColor() {
        return this.cancelColor;
    }

    public void setCancelColor(String str) {
        this.cancelColor = str;
    }

    public String getConfirmText() {
        return this.confirmText;
    }

    public void setConfirmText(String str) {
        this.confirmText = str;
    }

    public String getConfirmColor() {
        return this.confirmColor;
    }

    public void setConfirmColor(String str) {
        this.confirmColor = str;
    }

    public Boolean getEditable() {
        return this.editable;
    }

    public void setEditable(Boolean bool) {
        this.editable = bool;
    }

    public String getPlaceholderText() {
        return this.placeholderText;
    }

    public void setPlaceholderText(String str) {
        this.placeholderText = str;
    }

    public Function1<ShowModalSuccess, Unit> getSuccess() {
        return this.success;
    }

    public void setSuccess(Function1<? super ShowModalSuccess, Unit> function1) {
        this.success = function1;
    }

    public Function1<IPromptError, Unit> getFail() {
        return this.fail;
    }

    public void setFail(Function1<? super IPromptError, Unit> function1) {
        this.fail = function1;
    }

    public Function1<Object, Unit> getComplete() {
        return this.complete;
    }

    public void setComplete(Function1<Object, Unit> function1) {
        this.complete = function1;
    }

    public ShowModalOptions(String str, String str2, Boolean bool, String str3, String str4, String str5, String str6, Boolean bool2, String str7, Function1<? super ShowModalSuccess, Unit> function1, Function1<? super IPromptError, Unit> function12, Function1<Object, Unit> function13) {
        this.title = str;
        this.content = str2;
        this.showCancel = bool;
        this.cancelText = str3;
        this.cancelColor = str4;
        this.confirmText = str5;
        this.confirmColor = str6;
        this.editable = bool2;
        this.placeholderText = str7;
        this.success = function1;
        this.fail = function12;
        this.complete = function13;
    }
}
