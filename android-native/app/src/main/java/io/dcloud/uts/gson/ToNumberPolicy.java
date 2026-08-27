package io.dcloud.uts.gson;

import io.dcloud.uts.UTSNumber;
import io.dcloud.uts.gson.stream.JsonReader;
import io.dcloud.uts.gson.stream.MalformedJsonException;
import java.io.IOException;
import java.math.BigDecimal;

/* loaded from: classes2.dex */
public enum ToNumberPolicy implements ToNumberStrategy {
    DOUBLE { // from class: io.dcloud.uts.gson.ToNumberPolicy.1
        @Override // io.dcloud.uts.gson.ToNumberStrategy
        public Double readNumber(JsonReader jsonReader) throws IOException {
            return Double.valueOf(jsonReader.nextDouble());
        }
    },
    LAZILY_PARSED_NUMBER { // from class: io.dcloud.uts.gson.ToNumberPolicy.2
        @Override // io.dcloud.uts.gson.ToNumberStrategy
        public Number readNumber(JsonReader jsonReader) throws IOException {
            return UTSNumber.INSTANCE.from(jsonReader.nextString());
        }
    },
    LONG_OR_DOUBLE { // from class: io.dcloud.uts.gson.ToNumberPolicy.3
        @Override // io.dcloud.uts.gson.ToNumberStrategy
        public Number readNumber(JsonReader jsonReader) throws JsonParseException, IOException, NumberFormatException {
            String strNextString = jsonReader.nextString();
            try {
                try {
                    return Long.valueOf(Long.parseLong(strNextString));
                } catch (NumberFormatException unused) {
                    Double dValueOf = Double.valueOf(strNextString);
                    if ((!dValueOf.isInfinite() && !dValueOf.isNaN()) || jsonReader.isLenient()) {
                        return dValueOf;
                    }
                    throw new MalformedJsonException("JSON forbids NaN and infinities: " + dValueOf + "; at path " + jsonReader.getPath());
                }
            } catch (NumberFormatException e) {
                throw new JsonParseException("Cannot parse " + strNextString + "; at path " + jsonReader.getPath(), e);
            }
        }
    },
    UTS_Number { // from class: io.dcloud.uts.gson.ToNumberPolicy.4
        @Override // io.dcloud.uts.gson.ToNumberStrategy
        public Number readNumber(JsonReader jsonReader) throws JsonParseException, IOException {
            String strNextString = jsonReader.nextString();
            Double dValueOf = Double.valueOf(UTSNumber.INSTANCE.from(strNextString).doubleValue());
            if (jsonReader.isLenient() || (!UTSNumber.INSTANCE.isNaN(dValueOf) && UTSNumber.INSTANCE.isFinite(dValueOf))) {
                return dValueOf;
            }
            throw new MalformedJsonException("JSON forbids NaN and infinities: " + strNextString + "; at path " + jsonReader.getPath());
        }
    },
    BIG_DECIMAL { // from class: io.dcloud.uts.gson.ToNumberPolicy.5
        @Override // io.dcloud.uts.gson.ToNumberStrategy
        public BigDecimal readNumber(JsonReader jsonReader) throws IOException {
            String strNextString = jsonReader.nextString();
            try {
                return new BigDecimal(strNextString);
            } catch (NumberFormatException e) {
                throw new JsonParseException("Cannot parse " + strNextString + "; at path " + jsonReader.getPath(), e);
            }
        }
    }
}
