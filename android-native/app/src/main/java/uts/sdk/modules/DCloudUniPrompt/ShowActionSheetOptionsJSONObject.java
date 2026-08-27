package uts.sdk.modules.DCloudUniPrompt;

import com.taobao.weex.common.Constants;
import com.taobao.weex.ui.component.WXImage;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.uts.UTSArray;
import io.dcloud.uts.UTSCallback;
import io.dcloud.uts.UTSJSONObject;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: index.kt */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000b\b\u0016\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003R\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001c\u0010\n\u001a\u0004\u0018\u00010\u0005X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\u0007\"\u0004\b\f\u0010\tR \u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00050\u000eX\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u001c\u0010\u0013\u001a\u0004\u0018\u00010\u0005X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0007\"\u0004\b\u0015\u0010\tR\u001c\u0010\u0016\u001a\u0004\u0018\u00010\u0017X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u001c\u0010\u001c\u001a\u0004\u0018\u00010\u001dX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u001f\"\u0004\b \u0010!R\u001c\u0010\"\u001a\u0004\u0018\u00010\u001dX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010\u001f\"\u0004\b$\u0010!R\u001c\u0010%\u001a\u0004\u0018\u00010\u001dX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b&\u0010\u001f\"\u0004\b'\u0010!¨\u0006("}, d2 = {"Luts/sdk/modules/DCloudUniPrompt/ShowActionSheetOptionsJSONObject;", "Lio/dcloud/uts/UTSJSONObject;", "<init>", "()V", AbsoluteConst.JSON_KEY_TITLE, "", "getTitle", "()Ljava/lang/String;", "setTitle", "(Ljava/lang/String;)V", "alertText", "getAlertText", "setAlertText", "itemList", "Lio/dcloud/uts/UTSArray;", "getItemList", "()Lio/dcloud/uts/UTSArray;", "setItemList", "(Lio/dcloud/uts/UTSArray;)V", Constants.Name.ITEM_COLOR, "getItemColor", "setItemColor", AbsoluteConst.JSON_KEY_POPOVER, "Luts/sdk/modules/DCloudUniPrompt/Popover;", "getPopover", "()Luts/sdk/modules/DCloudUniPrompt/Popover;", "setPopover", "(Luts/sdk/modules/DCloudUniPrompt/Popover;)V", WXImage.SUCCEED, "Lio/dcloud/uts/UTSCallback;", "getSuccess", "()Lio/dcloud/uts/UTSCallback;", "setSuccess", "(Lio/dcloud/uts/UTSCallback;)V", Constants.Event.FAIL, "getFail", "setFail", "complete", "getComplete", "setComplete", "uni-prompt_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public class ShowActionSheetOptionsJSONObject extends UTSJSONObject {
    private String alertText;
    private UTSCallback complete;
    private UTSCallback fail;
    private String itemColor;
    public UTSArray<String> itemList;
    private Popover popover;
    private UTSCallback success;
    private String title;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String getAlertText() {
        return this.alertText;
    }

    public void setAlertText(String str) {
        this.alertText = str;
    }

    public UTSArray<String> getItemList() {
        UTSArray<String> uTSArray = this.itemList;
        if (uTSArray != null) {
            return uTSArray;
        }
        Intrinsics.throwUninitializedPropertyAccessException("itemList");
        return null;
    }

    public void setItemList(UTSArray<String> uTSArray) {
        Intrinsics.checkNotNullParameter(uTSArray, "<set-?>");
        this.itemList = uTSArray;
    }

    public String getItemColor() {
        return this.itemColor;
    }

    public void setItemColor(String str) {
        this.itemColor = str;
    }

    public Popover getPopover() {
        return this.popover;
    }

    public void setPopover(Popover popover) {
        this.popover = popover;
    }

    public UTSCallback getSuccess() {
        return this.success;
    }

    public void setSuccess(UTSCallback uTSCallback) {
        this.success = uTSCallback;
    }

    public UTSCallback getFail() {
        return this.fail;
    }

    public void setFail(UTSCallback uTSCallback) {
        this.fail = uTSCallback;
    }

    public UTSCallback getComplete() {
        return this.complete;
    }

    public void setComplete(UTSCallback uTSCallback) {
        this.complete = uTSCallback;
    }
}
