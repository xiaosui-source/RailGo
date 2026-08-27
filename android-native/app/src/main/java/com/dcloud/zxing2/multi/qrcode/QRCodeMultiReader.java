package com.dcloud.zxing2.multi.qrcode;

import com.dcloud.zxing2.BarcodeFormat;
import com.dcloud.zxing2.BinaryBitmap;
import com.dcloud.zxing2.DecodeHintType;
import com.dcloud.zxing2.NotFoundException;
import com.dcloud.zxing2.ReaderException;
import com.dcloud.zxing2.Result;
import com.dcloud.zxing2.ResultMetadataType;
import com.dcloud.zxing2.ResultPoint;
import com.dcloud.zxing2.common.DecoderResult;
import com.dcloud.zxing2.common.DetectorResult;
import com.dcloud.zxing2.multi.MultipleBarcodeReader;
import com.dcloud.zxing2.multi.qrcode.detector.MultiDetector;
import com.dcloud.zxing2.qrcode.QRCodeReader;
import com.dcloud.zxing2.qrcode.decoder.QRCodeDecoderMetaData;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public final class QRCodeMultiReader extends QRCodeReader implements MultipleBarcodeReader {
    private static final Result[] EMPTY_RESULT_ARRAY = new Result[0];
    private static final ResultPoint[] NO_POINTS = new ResultPoint[0];

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    private static final class SAComparator implements Comparator<Result>, Serializable {
        private SAComparator() {
        }

        @Override // java.util.Comparator
        public int compare(Result result, Result result2) {
            Map<ResultMetadataType, Object> resultMetadata = result.getResultMetadata();
            ResultMetadataType resultMetadataType = ResultMetadataType.STRUCTURED_APPEND_SEQUENCE;
            int iIntValue = ((Integer) resultMetadata.get(resultMetadataType)).intValue();
            int iIntValue2 = ((Integer) result2.getResultMetadata().get(resultMetadataType)).intValue();
            if (iIntValue < iIntValue2) {
                return -1;
            }
            return iIntValue > iIntValue2 ? 1 : 0;
        }
    }

    private static List<Result> processStructuredAppend(List<Result> list) {
        Iterator<Result> it = list.iterator();
        while (it.hasNext()) {
            if (it.next().getResultMetadata().containsKey(ResultMetadataType.STRUCTURED_APPEND_SEQUENCE)) {
                ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = new ArrayList();
                for (Result result : list) {
                    arrayList.add(result);
                    if (result.getResultMetadata().containsKey(ResultMetadataType.STRUCTURED_APPEND_SEQUENCE)) {
                        arrayList2.add(result);
                    }
                }
                Collections.sort(arrayList2, new SAComparator());
                StringBuilder sb = new StringBuilder();
                int size = arrayList2.size();
                int i = 0;
                int length = 0;
                int length2 = 0;
                while (i < size) {
                    Object obj = arrayList2.get(i);
                    i++;
                    Result result2 = (Result) obj;
                    sb.append(result2.getText());
                    length2 += result2.getRawBytes().length;
                    Map<ResultMetadataType, Object> resultMetadata = result2.getResultMetadata();
                    ResultMetadataType resultMetadataType = ResultMetadataType.BYTE_SEGMENTS;
                    if (resultMetadata.containsKey(resultMetadataType)) {
                        Iterator it2 = ((Iterable) result2.getResultMetadata().get(resultMetadataType)).iterator();
                        while (it2.hasNext()) {
                            length += ((byte[]) it2.next()).length;
                        }
                    }
                }
                byte[] bArr = new byte[length2];
                byte[] bArr2 = new byte[length];
                int size2 = arrayList2.size();
                int i2 = 0;
                int length3 = 0;
                int length4 = 0;
                while (i2 < size2) {
                    Object obj2 = arrayList2.get(i2);
                    i2++;
                    Result result3 = (Result) obj2;
                    System.arraycopy(result3.getRawBytes(), 0, bArr, length3, result3.getRawBytes().length);
                    length3 += result3.getRawBytes().length;
                    Map<ResultMetadataType, Object> resultMetadata2 = result3.getResultMetadata();
                    ResultMetadataType resultMetadataType2 = ResultMetadataType.BYTE_SEGMENTS;
                    if (resultMetadata2.containsKey(resultMetadataType2)) {
                        for (byte[] bArr3 : (Iterable) result3.getResultMetadata().get(resultMetadataType2)) {
                            System.arraycopy(bArr3, 0, bArr2, length4, bArr3.length);
                            length4 += bArr3.length;
                        }
                    }
                }
                Result result4 = new Result(sb.toString(), bArr, NO_POINTS, BarcodeFormat.QR_CODE);
                if (length > 0) {
                    ArrayList arrayList3 = new ArrayList();
                    arrayList3.add(bArr2);
                    result4.putMetadata(ResultMetadataType.BYTE_SEGMENTS, arrayList3);
                }
                arrayList.add(result4);
                return arrayList;
            }
        }
        return list;
    }

    @Override // com.dcloud.zxing2.multi.MultipleBarcodeReader
    public Result[] decodeMultiple(BinaryBitmap binaryBitmap) throws NotFoundException {
        return decodeMultiple(binaryBitmap, null);
    }

    @Override // com.dcloud.zxing2.multi.MultipleBarcodeReader
    public Result[] decodeMultiple(BinaryBitmap binaryBitmap, Map<DecodeHintType, ?> map) throws NotFoundException {
        ArrayList arrayList = new ArrayList();
        for (DetectorResult detectorResult : new MultiDetector(binaryBitmap.getBlackMatrix()).detectMulti(map)) {
            try {
                DecoderResult decoderResultDecode = getDecoder().decode(detectorResult.getBits(), map);
                ResultPoint[] points = detectorResult.getPoints();
                if (decoderResultDecode.getOther() instanceof QRCodeDecoderMetaData) {
                    ((QRCodeDecoderMetaData) decoderResultDecode.getOther()).applyMirroredCorrection(points);
                }
                Result result = new Result(decoderResultDecode.getText(), decoderResultDecode.getRawBytes(), points, BarcodeFormat.QR_CODE);
                List<byte[]> byteSegments = decoderResultDecode.getByteSegments();
                if (byteSegments != null) {
                    result.putMetadata(ResultMetadataType.BYTE_SEGMENTS, byteSegments);
                }
                String eCLevel = decoderResultDecode.getECLevel();
                if (eCLevel != null) {
                    result.putMetadata(ResultMetadataType.ERROR_CORRECTION_LEVEL, eCLevel);
                }
                if (decoderResultDecode.hasStructuredAppend()) {
                    result.putMetadata(ResultMetadataType.STRUCTURED_APPEND_SEQUENCE, Integer.valueOf(decoderResultDecode.getStructuredAppendSequenceNumber()));
                    result.putMetadata(ResultMetadataType.STRUCTURED_APPEND_PARITY, Integer.valueOf(decoderResultDecode.getStructuredAppendParity()));
                }
                arrayList.add(result);
            } catch (ReaderException unused) {
            }
        }
        if (arrayList.isEmpty()) {
            return EMPTY_RESULT_ARRAY;
        }
        List<Result> listProcessStructuredAppend = processStructuredAppend(arrayList);
        return (Result[]) listProcessStructuredAppend.toArray(new Result[listProcessStructuredAppend.size()]);
    }
}
