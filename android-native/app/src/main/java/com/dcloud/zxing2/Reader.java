package com.dcloud.zxing2;

import java.util.Map;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public interface Reader {
    Result decode(BinaryBitmap binaryBitmap) throws ChecksumException, FormatException, NotFoundException;

    Result decode(BinaryBitmap binaryBitmap, Map<DecodeHintType, ?> map) throws ChecksumException, FormatException, NotFoundException;

    void reset();
}
