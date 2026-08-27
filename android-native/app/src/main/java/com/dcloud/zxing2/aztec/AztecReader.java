package com.dcloud.zxing2.aztec;

import com.dcloud.zxing2.BinaryBitmap;
import com.dcloud.zxing2.FormatException;
import com.dcloud.zxing2.NotFoundException;
import com.dcloud.zxing2.Reader;
import com.dcloud.zxing2.Result;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public final class AztecReader implements Reader {
    @Override // com.dcloud.zxing2.Reader
    public Result decode(BinaryBitmap binaryBitmap) throws FormatException, NotFoundException {
        return decode(binaryBitmap, null);
    }

    @Override // com.dcloud.zxing2.Reader
    public void reset() {
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0031  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0050  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x005d A[LOOP:0: B:32:0x005b->B:33:0x005d, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x007a  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0085  */
    @Override // com.dcloud.zxing2.Reader
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public com.dcloud.zxing2.Result decode(com.dcloud.zxing2.BinaryBitmap r6, java.util.Map<com.dcloud.zxing2.DecodeHintType, ?> r7) throws com.dcloud.zxing2.FormatException, com.dcloud.zxing2.NotFoundException {
        /*
            r5 = this;
            com.dcloud.zxing2.aztec.detector.Detector r0 = new com.dcloud.zxing2.aztec.detector.Detector
            com.dcloud.zxing2.common.BitMatrix r6 = r6.getBlackMatrix()
            r0.<init>(r6)
            r6 = 0
            r1 = 0
            com.dcloud.zxing2.aztec.AztecDetectorResult r2 = r0.detect(r6)     // Catch: com.dcloud.zxing2.FormatException -> L25 com.dcloud.zxing2.NotFoundException -> L2b
            com.dcloud.zxing2.ResultPoint[] r3 = r2.getPoints()     // Catch: com.dcloud.zxing2.FormatException -> L25 com.dcloud.zxing2.NotFoundException -> L2b
            com.dcloud.zxing2.aztec.decoder.Decoder r4 = new com.dcloud.zxing2.aztec.decoder.Decoder     // Catch: com.dcloud.zxing2.FormatException -> L21 com.dcloud.zxing2.NotFoundException -> L23
            r4.<init>()     // Catch: com.dcloud.zxing2.FormatException -> L21 com.dcloud.zxing2.NotFoundException -> L23
            com.dcloud.zxing2.common.DecoderResult r2 = r4.decode(r2)     // Catch: com.dcloud.zxing2.FormatException -> L21 com.dcloud.zxing2.NotFoundException -> L23
            r4 = r3
            r3 = r1
            r1 = r2
            r2 = r3
            goto L2f
        L21:
            r2 = move-exception
            goto L27
        L23:
            r2 = move-exception
            goto L2d
        L25:
            r2 = move-exception
            r3 = r1
        L27:
            r4 = r3
            r3 = r2
            r2 = r1
            goto L2f
        L2b:
            r2 = move-exception
            r3 = r1
        L2d:
            r4 = r3
            r3 = r1
        L2f:
            if (r1 != 0) goto L4e
            r1 = 1
            com.dcloud.zxing2.aztec.AztecDetectorResult r0 = r0.detect(r1)     // Catch: com.dcloud.zxing2.FormatException -> L44 com.dcloud.zxing2.NotFoundException -> L46
            com.dcloud.zxing2.ResultPoint[] r4 = r0.getPoints()     // Catch: com.dcloud.zxing2.FormatException -> L44 com.dcloud.zxing2.NotFoundException -> L46
            com.dcloud.zxing2.aztec.decoder.Decoder r1 = new com.dcloud.zxing2.aztec.decoder.Decoder     // Catch: com.dcloud.zxing2.FormatException -> L44 com.dcloud.zxing2.NotFoundException -> L46
            r1.<init>()     // Catch: com.dcloud.zxing2.FormatException -> L44 com.dcloud.zxing2.NotFoundException -> L46
            com.dcloud.zxing2.common.DecoderResult r1 = r1.decode(r0)     // Catch: com.dcloud.zxing2.FormatException -> L44 com.dcloud.zxing2.NotFoundException -> L46
            goto L4e
        L44:
            r6 = move-exception
            goto L47
        L46:
            r6 = move-exception
        L47:
            if (r2 != 0) goto L4d
            if (r3 == 0) goto L4c
            throw r3
        L4c:
            throw r6
        L4d:
            throw r2
        L4e:
            if (r7 == 0) goto L65
            com.dcloud.zxing2.DecodeHintType r0 = com.dcloud.zxing2.DecodeHintType.NEED_RESULT_POINT_CALLBACK
            java.lang.Object r7 = r7.get(r0)
            com.dcloud.zxing2.ResultPointCallback r7 = (com.dcloud.zxing2.ResultPointCallback) r7
            if (r7 == 0) goto L65
            int r0 = r4.length
        L5b:
            if (r6 >= r0) goto L65
            r2 = r4[r6]
            r7.foundPossibleResultPoint(r2)
            int r6 = r6 + 1
            goto L5b
        L65:
            com.dcloud.zxing2.Result r6 = new com.dcloud.zxing2.Result
            java.lang.String r7 = r1.getText()
            byte[] r0 = r1.getRawBytes()
            com.dcloud.zxing2.BarcodeFormat r2 = com.dcloud.zxing2.BarcodeFormat.AZTEC
            r6.<init>(r7, r0, r4, r2)
            java.util.List r7 = r1.getByteSegments()
            if (r7 == 0) goto L7f
            com.dcloud.zxing2.ResultMetadataType r0 = com.dcloud.zxing2.ResultMetadataType.BYTE_SEGMENTS
            r6.putMetadata(r0, r7)
        L7f:
            java.lang.String r7 = r1.getECLevel()
            if (r7 == 0) goto L8a
            com.dcloud.zxing2.ResultMetadataType r0 = com.dcloud.zxing2.ResultMetadataType.ERROR_CORRECTION_LEVEL
            r6.putMetadata(r0, r7)
        L8a:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.dcloud.zxing2.aztec.AztecReader.decode(com.dcloud.zxing2.BinaryBitmap, java.util.Map):com.dcloud.zxing2.Result");
    }
}
