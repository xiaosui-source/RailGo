package com.alibaba.fastjson.serializer;

import com.taobao.weex.el.parse.Operators;
import java.lang.reflect.Type;

/* loaded from: classes.dex */
public class ArraySerializer implements ObjectSerializer {
    private final ObjectSerializer compObjectSerializer;
    private final Class<?> componentType;

    public ArraySerializer(Class<?> cls, ObjectSerializer objectSerializer) {
        this.componentType = cls;
        this.compObjectSerializer = objectSerializer;
    }

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public final void write(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type, int i) throws Throwable {
        JSONSerializer jSONSerializer2;
        Throwable th;
        SerializeWriter serializeWriter = jSONSerializer.out;
        if (obj == null) {
            serializeWriter.writeNull(SerializerFeature.WriteNullListAsEmpty);
            return;
        }
        Object[] objArr = (Object[]) obj;
        int length = objArr.length;
        SerialContext serialContext = jSONSerializer.context;
        int i2 = 0;
        jSONSerializer.setContext(serialContext, obj, obj2, 0);
        try {
            serializeWriter.append(Operators.ARRAY_START);
            while (i2 < length) {
                if (i2 != 0) {
                    try {
                        serializeWriter.append(Operators.ARRAY_SEPRATOR);
                    } catch (Throwable th2) {
                        th = th2;
                        jSONSerializer2 = jSONSerializer;
                        jSONSerializer2.context = serialContext;
                        throw th;
                    }
                }
                Object obj3 = objArr[i2];
                if (obj3 == null) {
                    if (serializeWriter.isEnabled(SerializerFeature.WriteNullStringAsEmpty) && (obj instanceof String[])) {
                        serializeWriter.writeString("");
                    } else {
                        serializeWriter.append((CharSequence) "null");
                    }
                    jSONSerializer2 = jSONSerializer;
                } else if (obj3.getClass() == this.componentType) {
                    jSONSerializer2 = jSONSerializer;
                    try {
                        this.compObjectSerializer.write(jSONSerializer2, obj3, Integer.valueOf(i2), null, 0);
                    } catch (Throwable th3) {
                        th = th3;
                        th = th;
                        jSONSerializer2.context = serialContext;
                        throw th;
                    }
                } else {
                    jSONSerializer2 = jSONSerializer;
                    jSONSerializer2.getObjectWriter(obj3.getClass()).write(jSONSerializer2, obj3, Integer.valueOf(i2), null, 0);
                }
                i2++;
                jSONSerializer = jSONSerializer2;
            }
            jSONSerializer2 = jSONSerializer;
            serializeWriter.append(Operators.ARRAY_END);
            jSONSerializer2.context = serialContext;
        } catch (Throwable th4) {
            th = th4;
            jSONSerializer2 = jSONSerializer;
        }
    }
}
