package uts.sdk.modules.DCloudUniPrompt;

import com.facebook.common.util.UriUtil;
import com.taobao.weex.common.Constants;
import com.taobao.weex.ui.component.WXImage;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.uts.JsonNotNull;
import io.dcloud.uts.UTSObject;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: index.kt */
@Metadata(d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0004\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u001e\b\u0016\u0018\u00002\u00020\u0001Bñ\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0010\b\u0002\u0010\u0004\u001a\n\u0018\u00010\u0003j\u0004\u0018\u0001`\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n\u0012\u0010\b\u0002\u0010\u000b\u001a\n\u0018\u00010\u0003j\u0004\u0018\u0001`\f\u0012+\b\u0002\u0010\r\u001a%\u0012\u0013\u0012\u00110\u000f¢\u0006\f\b\u0010\u0012\b\b\u0011\u0012\u0004\b\b(\u0012\u0012\u0004\u0012\u00020\u0013\u0018\u00010\u000ej\u0004\u0018\u0001`\u0014\u0012>\b\u0002\u0010\u0015\u001a8\u0012&\u0012$0\u0016j\u0011`\u0017¢\u0006\f\b\u0010\u0012\b\b\u0011\u0012\u0004\b\b(\u0012¢\u0006\f\b\u0010\u0012\b\b\u0011\u0012\u0004\b\b(\u0012\u0012\u0004\u0012\u00020\u0013\u0018\u00010\u000ej\u0004\u0018\u0001`\u0018\u0012+\b\u0002\u0010\u0019\u001a%\u0012\u0013\u0012\u00110\u001a¢\u0006\f\b\u0010\u0012\b\b\u0011\u0012\u0004\b\b(\u0012\u0012\u0004\u0012\u00020\u0013\u0018\u00010\u000ej\u0004\u0018\u0001`\u001b¢\u0006\u0004\b\u001c\u0010\u001dR\u001e\u0010\u0002\u001a\u00020\u00038\u0016@\u0016X\u0097\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u001f\"\u0004\b \u0010!R\"\u0010\u0004\u001a\n\u0018\u00010\u0003j\u0004\u0018\u0001`\u0005X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\u001f\"\u0004\b#\u0010!R\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b$\u0010\u001f\"\u0004\b%\u0010!R\u001e\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0096\u000e¢\u0006\u0010\n\u0002\u0010*\u001a\u0004\b&\u0010'\"\u0004\b(\u0010)R\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b+\u0010,\"\u0004\b-\u0010.R\"\u0010\u000b\u001a\n\u0018\u00010\u0003j\u0004\u0018\u0001`\fX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b/\u0010\u001f\"\u0004\b0\u0010!R=\u0010\r\u001a%\u0012\u0013\u0012\u00110\u000f¢\u0006\f\b\u0010\u0012\b\b\u0011\u0012\u0004\b\b(\u0012\u0012\u0004\u0012\u00020\u0013\u0018\u00010\u000ej\u0004\u0018\u0001`\u0014X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b1\u00102\"\u0004\b3\u00104RP\u0010\u0015\u001a8\u0012&\u0012$0\u0016j\u0011`\u0017¢\u0006\f\b\u0010\u0012\b\b\u0011\u0012\u0004\b\b(\u0012¢\u0006\f\b\u0010\u0012\b\b\u0011\u0012\u0004\b\b(\u0012\u0012\u0004\u0012\u00020\u0013\u0018\u00010\u000ej\u0004\u0018\u0001`\u0018X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b5\u00102\"\u0004\b6\u00104R=\u0010\u0019\u001a%\u0012\u0013\u0012\u00110\u001a¢\u0006\f\b\u0010\u0012\b\b\u0011\u0012\u0004\b\b(\u0012\u0012\u0004\u0012\u00020\u0013\u0018\u00010\u000ej\u0004\u0018\u0001`\u001bX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b7\u00102\"\u0004\b8\u00104¨\u00069"}, d2 = {"Luts/sdk/modules/DCloudUniPrompt/ShowToastOptions;", "Lio/dcloud/uts/UTSObject;", AbsoluteConst.JSON_KEY_TITLE, "", AbsoluteConst.JSON_KEY_ICON, "Luts/sdk/modules/DCloudUniPrompt/Icon;", "image", AbsoluteConst.JSON_KEY_MASK, "", "duration", "", "position", "Luts/sdk/modules/DCloudUniPrompt/ShowToastPosition;", WXImage.SUCCEED, "Lkotlin/Function1;", "Luts/sdk/modules/DCloudUniPrompt/ShowToastSuccess;", "Lkotlin/ParameterName;", "name", UriUtil.LOCAL_RESOURCE_SCHEME, "", "Luts/sdk/modules/DCloudUniPrompt/ShowToastSuccessCallback;", Constants.Event.FAIL, "Luts/sdk/modules/DCloudUniPrompt/IPromptError;", "Luts/sdk/modules/DCloudUniPrompt/ShowToastFail;", "Luts/sdk/modules/DCloudUniPrompt/ShowToastFailCallback;", "complete", "", "Luts/sdk/modules/DCloudUniPrompt/ShowToastCompleteCallback;", "<init>", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Boolean;Ljava/lang/Number;Ljava/lang/String;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)V", "getTitle", "()Ljava/lang/String;", "setTitle", "(Ljava/lang/String;)V", "getIcon", "setIcon", "getImage", "setImage", "getMask", "()Ljava/lang/Boolean;", "setMask", "(Ljava/lang/Boolean;)V", "Ljava/lang/Boolean;", "getDuration", "()Ljava/lang/Number;", "setDuration", "(Ljava/lang/Number;)V", "getPosition", "setPosition", "getSuccess", "()Lkotlin/jvm/functions/Function1;", "setSuccess", "(Lkotlin/jvm/functions/Function1;)V", "getFail", "setFail", "getComplete", "setComplete", "uni-prompt_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public class ShowToastOptions extends UTSObject {
    private Function1<Object, Unit> complete;
    private Number duration;
    private Function1<? super IPromptError, Unit> fail;
    private String icon;
    private String image;
    private Boolean mask;
    private String position;
    private Function1<? super ShowToastSuccess, Unit> success;

    @JsonNotNull
    private String title;

    public /* synthetic */ ShowToastOptions(String str, String str2, String str3, Boolean bool, Number number, String str4, Function1 function1, Function1 function12, Function1 function13, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? null : str2, (i & 4) != 0 ? null : str3, (i & 8) != 0 ? null : bool, (i & 16) != 0 ? null : number, (i & 32) != 0 ? null : str4, (i & 64) != 0 ? null : function1, (i & 128) != 0 ? null : function12, (i & 256) != 0 ? null : function13);
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.title = str;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String str) {
        this.icon = str;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String str) {
        this.image = str;
    }

    public Boolean getMask() {
        return this.mask;
    }

    public void setMask(Boolean bool) {
        this.mask = bool;
    }

    public Number getDuration() {
        return this.duration;
    }

    public void setDuration(Number number) {
        this.duration = number;
    }

    public String getPosition() {
        return this.position;
    }

    public void setPosition(String str) {
        this.position = str;
    }

    public Function1<ShowToastSuccess, Unit> getSuccess() {
        return this.success;
    }

    public void setSuccess(Function1<? super ShowToastSuccess, Unit> function1) {
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

    public ShowToastOptions(String title, String str, String str2, Boolean bool, Number number, String str3, Function1<? super ShowToastSuccess, Unit> function1, Function1<? super IPromptError, Unit> function12, Function1<Object, Unit> function13) {
        Intrinsics.checkNotNullParameter(title, "title");
        this.title = title;
        this.icon = str;
        this.image = str2;
        this.mask = bool;
        this.duration = number;
        this.position = str3;
        this.success = function1;
        this.fail = function12;
        this.complete = function13;
    }
}
