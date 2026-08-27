package org.mozilla.universalchardet.prober.distributionanalysis;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public abstract class CharDistributionAnalysis {
    public static final int ENOUGH_DATA_THRESHOLD = 1024;
    public static final int MINIMUM_DATA_THRESHOLD = 4;
    public static final float SURE_NO = 0.01f;
    public static final float SURE_YES = 0.99f;
    protected int[] charToFreqOrder;
    private int freqChars;
    private int totalChars;
    protected float typicalDistributionRatio;

    public CharDistributionAnalysis() {
        reset();
    }

    public float getConfidence() {
        int i;
        int i2 = this.totalChars;
        if (i2 <= 0 || (i = this.freqChars) <= 4) {
            return 0.01f;
        }
        if (i2 != i) {
            float f = i / ((i2 - i) * this.typicalDistributionRatio);
            if (f < 0.99f) {
                return f;
            }
        }
        return 0.99f;
    }

    protected abstract int getOrder(byte[] bArr, int i);

    public boolean gotEnoughData() {
        return this.totalChars > 1024;
    }

    public void handleData(byte[] bArr, int i, int i2) {
    }

    public void handleOneChar(byte[] bArr, int i, int i2) {
        int order = i2 == 2 ? getOrder(bArr, i) : -1;
        if (order >= 0) {
            this.totalChars++;
            int[] iArr = this.charToFreqOrder;
            if (order >= iArr.length || 512 <= iArr[order]) {
                return;
            }
            this.freqChars++;
        }
    }

    public void reset() {
        this.totalChars = 0;
        this.freqChars = 0;
    }

    public void setOption() {
    }
}
