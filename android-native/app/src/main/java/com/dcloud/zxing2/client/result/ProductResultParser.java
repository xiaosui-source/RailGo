package com.dcloud.zxing2.client.result;

import com.dcloud.zxing2.BarcodeFormat;
import com.dcloud.zxing2.Result;
import com.dcloud.zxing2.oned.UPCEReader;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public final class ProductResultParser extends ResultParser {
    @Override // com.dcloud.zxing2.client.result.ResultParser
    public ProductParsedResult parse(Result result) {
        BarcodeFormat barcodeFormat = result.getBarcodeFormat();
        if (barcodeFormat != BarcodeFormat.UPC_A && barcodeFormat != BarcodeFormat.UPC_E && barcodeFormat != BarcodeFormat.EAN_8 && barcodeFormat != BarcodeFormat.EAN_13) {
            return null;
        }
        String massagedText = ResultParser.getMassagedText(result);
        if (ResultParser.isStringOfDigits(massagedText, massagedText.length())) {
            return new ProductParsedResult(massagedText, (barcodeFormat == BarcodeFormat.UPC_E && massagedText.length() == 8) ? UPCEReader.convertUPCEtoUPCA(massagedText) : massagedText);
        }
        return null;
    }
}
