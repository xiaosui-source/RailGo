package org.mozilla.universalchardet.prober.statemachine;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public abstract class SMModel {
    public static final int ERROR = 1;
    public static final int ITSME = 2;
    public static final int START = 0;
    protected int[] charLenTable;
    protected int classFactor;
    protected PkgInt classTable;
    protected String name;
    protected PkgInt stateTable;

    public SMModel(PkgInt pkgInt, int i, PkgInt pkgInt2, int[] iArr, String str) {
        this.classTable = pkgInt;
        this.classFactor = i;
        this.stateTable = pkgInt2;
        this.charLenTable = (int[]) iArr.clone();
        this.name = str;
    }

    public int getCharLen(int i) {
        return this.charLenTable[i];
    }

    public int getClass(byte b) {
        return this.classTable.unpack(b & 255);
    }

    public String getName() {
        return this.name;
    }

    public int getNextState(int i, int i2) {
        return this.stateTable.unpack((i2 * this.classFactor) + i);
    }
}
