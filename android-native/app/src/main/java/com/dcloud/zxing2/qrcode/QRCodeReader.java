package com.dcloud.zxing2.qrcode;

import android.os.Handler;
import android.os.Message;
import com.dcloud.zxing2.BarcodeFormat;
import com.dcloud.zxing2.BinaryBitmap;
import com.dcloud.zxing2.ChecksumException;
import com.dcloud.zxing2.DecodeHintType;
import com.dcloud.zxing2.FormatException;
import com.dcloud.zxing2.NotFoundException;
import com.dcloud.zxing2.Reader;
import com.dcloud.zxing2.Result;
import com.dcloud.zxing2.ResultMetadataType;
import com.dcloud.zxing2.ResultPoint;
import com.dcloud.zxing2.common.BitMatrix;
import com.dcloud.zxing2.common.DecoderResult;
import com.dcloud.zxing2.common.DetectorResult;
import com.dcloud.zxing2.qrcode.decoder.Decoder;
import com.dcloud.zxing2.qrcode.decoder.QRCodeDecoderMetaData;
import com.dcloud.zxing2.qrcode.detector.Detector;
import java.util.List;
import java.util.Map;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class QRCodeReader implements Reader {
    private static final ResultPoint[] NO_POINTS = new ResultPoint[0];
    private final Decoder decoder = new Decoder();
    private Handler hostHandler;

    private static BitMatrix extractPureBits(BitMatrix bitMatrix) throws NotFoundException {
        int[] topLeftOnBit = bitMatrix.getTopLeftOnBit();
        int[] bottomRightOnBit = bitMatrix.getBottomRightOnBit();
        if (topLeftOnBit == null || bottomRightOnBit == null) {
            throw NotFoundException.getNotFoundInstance();
        }
        float fModuleSize = moduleSize(topLeftOnBit, bitMatrix);
        int i = topLeftOnBit[1];
        int i2 = bottomRightOnBit[1];
        int i3 = topLeftOnBit[0];
        int i4 = bottomRightOnBit[0];
        if (i3 >= i4 || i >= i2) {
            throw NotFoundException.getNotFoundInstance();
        }
        int i5 = i2 - i;
        if (i5 != i4 - i3) {
            i4 = i3 + i5;
        }
        int iRound = Math.round(((i4 - i3) + 1) / fModuleSize);
        int iRound2 = Math.round((i5 + 1) / fModuleSize);
        if (iRound <= 0 || iRound2 <= 0) {
            throw NotFoundException.getNotFoundInstance();
        }
        if (iRound2 != iRound) {
            throw NotFoundException.getNotFoundInstance();
        }
        int i6 = (int) (fModuleSize / 2.0f);
        int i7 = i + i6;
        int i8 = i3 + i6;
        int i9 = (((int) ((iRound - 1) * fModuleSize)) + i8) - i4;
        if (i9 > 0) {
            if (i9 > i6) {
                throw NotFoundException.getNotFoundInstance();
            }
            i8 -= i9;
        }
        int i10 = (((int) ((iRound2 - 1) * fModuleSize)) + i7) - i2;
        if (i10 > 0) {
            if (i10 > i6) {
                throw NotFoundException.getNotFoundInstance();
            }
            i7 -= i10;
        }
        BitMatrix bitMatrix2 = new BitMatrix(iRound, iRound2);
        for (int i11 = 0; i11 < iRound2; i11++) {
            int i12 = ((int) (i11 * fModuleSize)) + i7;
            for (int i13 = 0; i13 < iRound; i13++) {
                if (bitMatrix.get(((int) (i13 * fModuleSize)) + i8, i12)) {
                    bitMatrix2.set(i13, i11);
                }
            }
        }
        return bitMatrix2;
    }

    private static float moduleSize(int[] iArr, BitMatrix bitMatrix) throws NotFoundException {
        int height = bitMatrix.getHeight();
        int width = bitMatrix.getWidth();
        int i = iArr[0];
        boolean z = true;
        int i2 = iArr[1];
        int i3 = 0;
        while (i < width && i2 < height) {
            if (z != bitMatrix.get(i, i2)) {
                i3++;
                if (i3 == 5) {
                    break;
                }
                z = !z;
            }
            i++;
            i2++;
        }
        if (i == width || i2 == height) {
            throw NotFoundException.getNotFoundInstance();
        }
        return (i - iArr[0]) / 7.0f;
    }

    @Override // com.dcloud.zxing2.Reader
    public Result decode(BinaryBitmap binaryBitmap) throws ChecksumException, FormatException, NotFoundException {
        return decode(binaryBitmap, null);
    }

    protected final Decoder getDecoder() {
        return this.decoder;
    }

    @Override // com.dcloud.zxing2.Reader
    public void reset() {
    }

    public void updateHandler(Handler handler) {
        this.hostHandler = handler;
    }

    @Override // com.dcloud.zxing2.Reader
    public final Result decode(BinaryBitmap binaryBitmap, Map<DecodeHintType, ?> map) throws ChecksumException, FormatException, NotFoundException {
        DecoderResult decoderResultDecode;
        ResultPoint[] points;
        DecoderResult decoderResultDecode2;
        if (map == null || !map.containsKey(DecodeHintType.PURE_BARCODE)) {
            DetectorResult detectorResultDetect = new Detector(binaryBitmap.getBlackMatrix()).detect(map);
            try {
                decoderResultDecode = this.decoder.decode(detectorResultDetect.getBits(), map);
            } catch (Exception unused) {
                if (this.hostHandler != null && detectorResultDetect != null) {
                    Message message = new Message();
                    message.what = 1010;
                    message.obj = Float.valueOf(detectorResultDetect.moduleSize);
                    this.hostHandler.sendMessage(message);
                }
                decoderResultDecode = null;
            }
            if (decoderResultDecode == null) {
                throw NotFoundException.getNotFoundInstance();
            }
            DecoderResult decoderResult = decoderResultDecode;
            points = detectorResultDetect.getPoints();
            decoderResultDecode2 = decoderResult;
        } else {
            decoderResultDecode2 = this.decoder.decode(extractPureBits(binaryBitmap.getBlackMatrix()), map);
            points = NO_POINTS;
        }
        if (decoderResultDecode2.getOther() instanceof QRCodeDecoderMetaData) {
            ((QRCodeDecoderMetaData) decoderResultDecode2.getOther()).applyMirroredCorrection(points);
        }
        Result result = new Result(decoderResultDecode2.getText(), decoderResultDecode2.getRawBytes(), points, BarcodeFormat.QR_CODE);
        result.textCharset = decoderResultDecode2.textCharset;
        List<byte[]> byteSegments = decoderResultDecode2.getByteSegments();
        if (byteSegments != null) {
            result.putMetadata(ResultMetadataType.BYTE_SEGMENTS, byteSegments);
        }
        String eCLevel = decoderResultDecode2.getECLevel();
        if (eCLevel != null) {
            result.putMetadata(ResultMetadataType.ERROR_CORRECTION_LEVEL, eCLevel);
        }
        if (decoderResultDecode2.hasStructuredAppend()) {
            result.putMetadata(ResultMetadataType.STRUCTURED_APPEND_SEQUENCE, Integer.valueOf(decoderResultDecode2.getStructuredAppendSequenceNumber()));
            result.putMetadata(ResultMetadataType.STRUCTURED_APPEND_PARITY, Integer.valueOf(decoderResultDecode2.getStructuredAppendParity()));
        }
        return result;
    }
}
