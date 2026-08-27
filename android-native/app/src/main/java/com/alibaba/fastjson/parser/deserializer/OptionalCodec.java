package com.alibaba.fastjson.parser.deserializer;

import androidx.webkit.internal.ApiHelperForO$$ExternalSyntheticApiModelOutline2;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.util.TypeUtils;
import dc.squareup.okio.Okio$$ExternalSyntheticApiModelOutline0;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;

/* loaded from: classes.dex */
public class OptionalCodec implements ObjectSerializer, ObjectDeserializer {
    public static OptionalCodec instance = new OptionalCodec();

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 12;
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj) {
        if (type == ApiHelperForO$$ExternalSyntheticApiModelOutline2.m$10()) {
            Integer numCastToInt = TypeUtils.castToInt(defaultJSONParser.parseObject((Class) Integer.class));
            if (numCastToInt == null) {
                return (T) OptionalInt.empty();
            }
            return (T) OptionalInt.of(numCastToInt.intValue());
        }
        if (type == Okio$$ExternalSyntheticApiModelOutline0.m396m()) {
            Long lCastToLong = TypeUtils.castToLong(defaultJSONParser.parseObject((Class) Long.class));
            if (lCastToLong == null) {
                return (T) OptionalLong.empty();
            }
            return (T) OptionalLong.of(lCastToLong.longValue());
        }
        if (type == Okio$$ExternalSyntheticApiModelOutline0.m415m$1()) {
            Double dCastToDouble = TypeUtils.castToDouble(defaultJSONParser.parseObject((Class) Double.class));
            if (dCastToDouble == null) {
                return (T) OptionalDouble.empty();
            }
            return (T) OptionalDouble.of(dCastToDouble.doubleValue());
        }
        Object object = defaultJSONParser.parseObject(TypeUtils.unwrapOptional(type));
        if (object == null) {
            return (T) Optional.empty();
        }
        return (T) Optional.of(object);
    }

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type, int i) throws IOException {
        if (obj == null) {
            jSONSerializer.writeNull();
            return;
        }
        if (Okio$$ExternalSyntheticApiModelOutline0.m$1(obj)) {
            Optional optionalM = Okio$$ExternalSyntheticApiModelOutline0.m(obj);
            jSONSerializer.write(optionalM.isPresent() ? optionalM.get() : null);
            return;
        }
        if (ApiHelperForO$$ExternalSyntheticApiModelOutline2.m$2(obj)) {
            OptionalDouble optionalDoubleM184m = ApiHelperForO$$ExternalSyntheticApiModelOutline2.m184m(obj);
            if (optionalDoubleM184m.isPresent()) {
                jSONSerializer.write(Double.valueOf(optionalDoubleM184m.getAsDouble()));
                return;
            } else {
                jSONSerializer.writeNull();
                return;
            }
        }
        if (Okio$$ExternalSyntheticApiModelOutline0.m411m(obj)) {
            OptionalInt optionalIntM401m = Okio$$ExternalSyntheticApiModelOutline0.m401m(obj);
            if (optionalIntM401m.isPresent()) {
                jSONSerializer.out.writeInt(optionalIntM401m.getAsInt());
                return;
            } else {
                jSONSerializer.writeNull();
                return;
            }
        }
        if (Okio$$ExternalSyntheticApiModelOutline0.m$2(obj)) {
            OptionalLong optionalLongM404m = Okio$$ExternalSyntheticApiModelOutline0.m404m(obj);
            if (optionalLongM404m.isPresent()) {
                jSONSerializer.out.writeLong(optionalLongM404m.getAsLong());
                return;
            } else {
                jSONSerializer.writeNull();
                return;
            }
        }
        throw new JSONException("not support optional : " + obj.getClass());
    }
}
