package org.mozilla.universalchardet.prober.sequence;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public abstract class SequenceModel {
    protected short[] charToOrderMap;
    protected String charsetName;
    protected boolean keepEnglishLetter;
    protected byte[] precedenceMatrix;
    protected float typicalPositiveRatio;

    public SequenceModel(short[] sArr, byte[] bArr, float f, boolean z, String str) {
        this.charToOrderMap = (short[]) sArr.clone();
        this.precedenceMatrix = (byte[]) bArr.clone();
        this.typicalPositiveRatio = f;
        this.keepEnglishLetter = z;
        this.charsetName = str;
    }

    public String getCharsetName() {
        return this.charsetName;
    }

    public boolean getKeepEnglishLetter() {
        return this.keepEnglishLetter;
    }

    public short getOrder(byte b) {
        return this.charToOrderMap[b & 255];
    }

    public byte getPrecedence(int i) {
        return this.precedenceMatrix[i];
    }

    public float getTypicalPositiveRatio() {
        return this.typicalPositiveRatio;
    }
}
