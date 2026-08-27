package com.dcloud.zxing2.client.result;

import com.dcloud.zxing2.Result;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public final class GeoResultParser extends ResultParser {
    private static final Pattern GEO_URL_PATTERN = Pattern.compile("geo:([\\-0-9.]+),([\\-0-9.]+)(?:,([\\-0-9.]+))?(?:\\?(.*))?", 2);

    @Override // com.dcloud.zxing2.client.result.ResultParser
    public GeoParsedResult parse(Result result) throws NumberFormatException {
        Matcher matcher = GEO_URL_PATTERN.matcher(ResultParser.getMassagedText(result));
        if (!matcher.matches()) {
            return null;
        }
        String strGroup = matcher.group(4);
        try {
            double d = Double.parseDouble(matcher.group(1));
            if (d <= 90.0d && d >= -90.0d) {
                double d2 = Double.parseDouble(matcher.group(2));
                if (d2 <= 180.0d && d2 >= -180.0d) {
                    double d3 = 0.0d;
                    if (matcher.group(3) != null) {
                        double d4 = Double.parseDouble(matcher.group(3));
                        if (d4 < 0.0d) {
                            return null;
                        }
                        d3 = d4;
                    }
                    return new GeoParsedResult(d, d2, d3, strGroup);
                }
            }
        } catch (NumberFormatException unused) {
        }
        return null;
    }
}
