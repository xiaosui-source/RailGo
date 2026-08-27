package com.dcloud.zxing2.aztec.encoder;

import com.dcloud.zxing2.common.BitArray;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
abstract class Token {
    static final Token EMPTY = new SimpleToken(null, 0, 0);
    private final Token previous;

    Token(Token token) {
        this.previous = token;
    }

    final Token add(int i, int i2) {
        return new SimpleToken(this, i, i2);
    }

    final Token addBinaryShift(int i, int i2) {
        return new BinaryShiftToken(this, i, i2);
    }

    abstract void appendTo(BitArray bitArray, byte[] bArr);

    final Token getPrevious() {
        return this.previous;
    }
}
