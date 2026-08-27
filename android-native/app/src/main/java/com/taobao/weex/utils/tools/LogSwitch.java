package com.taobao.weex.utils.tools;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class LogSwitch {
    private int low_level = 4;
    private int medium_level = 2;
    private int high_level = 1;
    private int log_switch = 0;
    private boolean showLowLevelLog = false;
    private boolean showMediumLevelLog = false;
    private boolean showHighLevelLog = true;

    public int getLog_switch() {
        return this.log_switch;
    }

    public void setLog_switch() {
        if (this.showLowLevelLog) {
            this.log_switch |= this.low_level;
        }
        if (this.showMediumLevelLog) {
            this.log_switch |= this.medium_level;
        }
        if (this.showHighLevelLog) {
            this.log_switch |= this.high_level;
        }
    }
}
