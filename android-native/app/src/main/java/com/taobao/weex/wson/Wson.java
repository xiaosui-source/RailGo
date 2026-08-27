package com.taobao.weex.wson;

import androidx.collection.LruCache;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.dmcbig.mediapicker.PickerConfig;
import com.taobao.weex.el.parse.Operators;
import com.taobao.weex.utils.WXLogUtils;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.jvm.internal.ByteCompanionObject;
import kotlinx.coroutines.scheduling.WorkQueueKt;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class Wson {
    private static final byte ARRAY_TYPE = 91;
    private static final byte BOOLEAN_TYPE_FALSE = 102;
    private static final byte BOOLEAN_TYPE_TRUE = 116;
    private static final int GLOBAL_STRING_CACHE_SIZE = 2048;
    private static final boolean IS_NATIVE_LITTLE_ENDIAN;
    private static final byte MAP_TYPE = 123;
    private static final String METHOD_PREFIX_GET = "get";
    private static final String METHOD_PREFIX_IS = "is";
    private static final byte NULL_TYPE = 48;
    private static final byte NUMBER_BIG_DECIMAL_TYPE = 101;
    private static final byte NUMBER_BIG_INTEGER_TYPE = 103;
    private static final byte NUMBER_DOUBLE_TYPE = 100;
    private static final byte NUMBER_FLOAT_TYPE = 70;
    private static final byte NUMBER_INT_TYPE = 105;
    private static final byte NUMBER_LONG_TYPE = 108;
    private static final byte STRING_TYPE = 115;
    public static final boolean WriteMapNullValue = false;
    private static LruCache<String, List<Field>> fieldsCache;
    private static final String[] globalStringBytesCache;
    private static final ThreadLocal<char[]> localCharsBufferCache;
    private static LruCache<String, List<Method>> methodsCache;
    private static LruCache<String, Boolean> specialClass;

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    private static final class Builder {
        private static final ThreadLocal<byte[]> bufLocal = new ThreadLocal<>();
        private static final ThreadLocal<ArrayList> refsLocal = new ThreadLocal<>();
        private byte[] buffer;
        private int position;
        private ArrayList refs;

        /* JADX INFO: Access modifiers changed from: private */
        public final void close() {
            byte[] bArr = this.buffer;
            if (bArr.length <= 16384) {
                bufLocal.set(bArr);
            }
            if (this.refs.isEmpty()) {
                refsLocal.set(this.refs);
            } else {
                this.refs.clear();
            }
            this.refs = null;
            this.buffer = null;
            this.position = 0;
        }

        private final void ensureCapacity(int i) {
            int i2 = i + this.position;
            byte[] bArr = this.buffer;
            if (i2 - bArr.length > 0) {
                int length = bArr.length << 1;
                if (length < 16384) {
                    length = 16384;
                }
                if (length - i2 >= 0) {
                    i2 = length;
                }
                this.buffer = Arrays.copyOf(bArr, i2);
            }
        }

        private final Map toMap(Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            Object obj2;
            Object objInvoke;
            JSONObject jSONObject = new JSONObject();
            try {
                Class<?> cls = obj.getClass();
                String name = cls.getName();
                for (Method method : Wson.getBeanMethod(name, cls)) {
                    String name2 = method.getName();
                    if (name2.startsWith(Wson.METHOD_PREFIX_GET)) {
                        Object objInvoke2 = method.invoke(obj, null);
                        if (objInvoke2 != null) {
                            StringBuilder sb = new StringBuilder(method.getName().substring(3));
                            sb.setCharAt(0, Character.toLowerCase(sb.charAt(0)));
                            jSONObject.put((JSONObject) sb.toString(), (String) objInvoke2);
                        }
                    } else if (name2.startsWith(Wson.METHOD_PREFIX_IS) && (objInvoke = method.invoke(obj, null)) != null) {
                        StringBuilder sb2 = new StringBuilder(method.getName().substring(2));
                        sb2.setCharAt(0, Character.toLowerCase(sb2.charAt(0)));
                        jSONObject.put((JSONObject) sb2.toString(), (String) objInvoke);
                    }
                }
                for (Field field : Wson.getBeanFields(name, cls)) {
                    String name3 = field.getName();
                    if (!jSONObject.containsKey(name3) && (obj2 = field.get(obj)) != null) {
                        jSONObject.put((JSONObject) name3, (String) obj2);
                    }
                }
                return jSONObject;
            } catch (Exception e) {
                if (e instanceof RuntimeException) {
                    throw ((RuntimeException) e);
                }
                throw new RuntimeException(e);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final byte[] toWson(Object obj) {
            writeObject(obj);
            int i = this.position;
            byte[] bArr = new byte[i];
            System.arraycopy(this.buffer, 0, bArr, 0, i);
            return bArr;
        }

        private final void writeAdapterObject(Object obj) {
            if (Wson.specialClass.get(obj.getClass().getName()) != null) {
                writeObject(JSON.toJSON(obj));
                return;
            }
            try {
                writeMap(toMap(obj));
            } catch (Exception unused) {
                Wson.specialClass.put(obj.getClass().getName(), Boolean.TRUE);
                writeObject(JSON.toJSON(obj));
            }
        }

        private final void writeByte(byte b) {
            byte[] bArr = this.buffer;
            int i = this.position;
            bArr[i] = b;
            this.position = i + 1;
        }

        private final void writeDouble(double d) {
            writeLong(Double.doubleToLongBits(d));
        }

        private final void writeFloat(float f) {
            int iFloatToIntBits = Float.floatToIntBits(f);
            byte[] bArr = this.buffer;
            int i = this.position;
            bArr[i + 3] = (byte) iFloatToIntBits;
            bArr[i + 2] = (byte) (iFloatToIntBits >>> 8);
            bArr[i + 1] = (byte) (iFloatToIntBits >>> 16);
            bArr[i] = (byte) (iFloatToIntBits >>> 24);
            this.position = i + 4;
        }

        private final void writeLong(long j) {
            byte[] bArr = this.buffer;
            int i = this.position;
            bArr[i + 7] = (byte) j;
            bArr[i + 6] = (byte) (j >>> 8);
            bArr[i + 5] = (byte) (j >>> 16);
            bArr[i + 4] = (byte) (j >>> 24);
            bArr[i + 3] = (byte) (j >>> 32);
            bArr[i + 2] = (byte) (j >>> 40);
            bArr[i + 1] = (byte) (j >>> 48);
            bArr[i] = (byte) (j >>> 56);
            this.position = i + 8;
        }

        private final void writeMap(Map map) {
            Set<Map.Entry> setEntrySet = map.entrySet();
            Iterator it = setEntrySet.iterator();
            int i = 0;
            while (it.hasNext()) {
                if (((Map.Entry) it.next()).getValue() == null) {
                    i++;
                }
            }
            ensureCapacity(8);
            writeByte(Wson.MAP_TYPE);
            writeUInt(map.size() - i);
            for (Map.Entry entry : setEntrySet) {
                if (entry.getValue() != null) {
                    writeMapKeyUTF16(entry.getKey().toString());
                    writeObject(entry.getValue());
                }
            }
        }

        private final void writeMapKeyUTF16(String str) {
            writeUTF16String(str);
        }

        private final void writeNumber(Number number) {
            ensureCapacity(12);
            if (number instanceof Integer) {
                writeByte(Wson.NUMBER_INT_TYPE);
                writeVarInt(number.intValue());
                return;
            }
            if (number instanceof Float) {
                writeByte(Wson.NUMBER_FLOAT_TYPE);
                writeFloat(number.floatValue());
                return;
            }
            if (number instanceof Double) {
                writeByte(Wson.NUMBER_DOUBLE_TYPE);
                writeDouble(number.doubleValue());
                return;
            }
            if (number instanceof Long) {
                writeByte(Wson.NUMBER_LONG_TYPE);
                writeLong(number.longValue());
                return;
            }
            if ((number instanceof Short) || (number instanceof Byte)) {
                writeByte(Wson.NUMBER_INT_TYPE);
                writeVarInt(number.intValue());
                return;
            }
            if (number instanceof BigInteger) {
                writeByte(Wson.NUMBER_BIG_INTEGER_TYPE);
                writeUTF16String(number.toString());
                return;
            }
            if (!(number instanceof BigDecimal)) {
                writeByte(Wson.STRING_TYPE);
                writeUTF16String(number.toString());
                return;
            }
            String string = number.toString();
            double dDoubleValue = number.doubleValue();
            if (string.equals(Double.toString(dDoubleValue))) {
                writeByte(Wson.NUMBER_DOUBLE_TYPE);
                writeDouble(dDoubleValue);
            } else {
                writeByte(Wson.NUMBER_BIG_DECIMAL_TYPE);
                writeUTF16String(string);
            }
        }

        private final void writeObject(Object obj) {
            if (obj instanceof CharSequence) {
                ensureCapacity(2);
                writeByte(Wson.STRING_TYPE);
                writeUTF16String((CharSequence) obj);
                return;
            }
            if (obj instanceof Map) {
                if (this.refs.contains(obj)) {
                    ensureCapacity(2);
                    writeByte(Wson.NULL_TYPE);
                    return;
                } else {
                    this.refs.add(obj);
                    writeMap((Map) obj);
                    this.refs.remove(r8.size() - 1);
                    return;
                }
            }
            if (obj instanceof List) {
                if (this.refs.contains(obj)) {
                    ensureCapacity(2);
                    writeByte(Wson.NULL_TYPE);
                    return;
                }
                this.refs.add(obj);
                ensureCapacity(8);
                List list = (List) obj;
                writeByte(Wson.ARRAY_TYPE);
                writeUInt(list.size());
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    writeObject(it.next());
                }
                this.refs.remove(r8.size() - 1);
                return;
            }
            if (obj instanceof Number) {
                writeNumber((Number) obj);
                return;
            }
            if (obj instanceof Boolean) {
                ensureCapacity(2);
                if (((Boolean) obj).booleanValue()) {
                    writeByte(Wson.BOOLEAN_TYPE_TRUE);
                    return;
                } else {
                    writeByte(Wson.BOOLEAN_TYPE_FALSE);
                    return;
                }
            }
            if (obj == null) {
                ensureCapacity(2);
                writeByte(Wson.NULL_TYPE);
                return;
            }
            if (obj.getClass().isArray()) {
                if (this.refs.contains(obj)) {
                    ensureCapacity(2);
                    writeByte(Wson.NULL_TYPE);
                    return;
                }
                this.refs.add(obj);
                ensureCapacity(8);
                int length = Array.getLength(obj);
                writeByte(Wson.ARRAY_TYPE);
                writeUInt(length);
                for (int i = 0; i < length; i++) {
                    writeObject(Array.get(obj, i));
                }
                this.refs.remove(r8.size() - 1);
                return;
            }
            if (obj instanceof Date) {
                ensureCapacity(10);
                double time = ((Date) obj).getTime();
                writeByte(Wson.NUMBER_DOUBLE_TYPE);
                writeDouble(time);
                return;
            }
            if (obj instanceof Calendar) {
                ensureCapacity(10);
                double time2 = ((Calendar) obj).getTime().getTime();
                writeByte(Wson.NUMBER_DOUBLE_TYPE);
                writeDouble(time2);
                return;
            }
            if (!(obj instanceof Collection)) {
                if (this.refs.contains(obj)) {
                    ensureCapacity(2);
                    writeByte(Wson.NULL_TYPE);
                    return;
                }
                this.refs.add(obj);
                if (obj.getClass().isEnum()) {
                    writeObject(JSON.toJSONString(obj));
                } else {
                    writeAdapterObject(obj);
                }
                this.refs.remove(r8.size() - 1);
                return;
            }
            if (this.refs.contains(obj)) {
                ensureCapacity(2);
                writeByte(Wson.NULL_TYPE);
                return;
            }
            this.refs.add(obj);
            ensureCapacity(8);
            Collection collection = (Collection) obj;
            writeByte(Wson.ARRAY_TYPE);
            writeUInt(collection.size());
            Iterator it2 = collection.iterator();
            while (it2.hasNext()) {
                writeObject(it2.next());
            }
            this.refs.remove(r8.size() - 1);
        }

        private final void writeUInt(int i) {
            while ((i & (-128)) != 0) {
                byte[] bArr = this.buffer;
                int i2 = this.position;
                bArr[i2] = (byte) ((i & WorkQueueKt.MASK) | 128);
                this.position = i2 + 1;
                i >>>= 7;
            }
            byte[] bArr2 = this.buffer;
            int i3 = this.position;
            bArr2[i3] = (byte) (i & WorkQueueKt.MASK);
            this.position = i3 + 1;
        }

        private final void writeUTF16String(CharSequence charSequence) {
            int length = charSequence.length();
            int i = length * 2;
            ensureCapacity(i + 8);
            writeUInt(i);
            int i2 = 0;
            if (Wson.IS_NATIVE_LITTLE_ENDIAN) {
                while (i2 < length) {
                    char cCharAt = charSequence.charAt(i2);
                    byte[] bArr = this.buffer;
                    int i3 = this.position;
                    bArr[i3] = (byte) cCharAt;
                    bArr[i3 + 1] = (byte) (cCharAt >>> '\b');
                    this.position = i3 + 2;
                    i2++;
                }
                return;
            }
            while (i2 < length) {
                char cCharAt2 = charSequence.charAt(i2);
                byte[] bArr2 = this.buffer;
                int i4 = this.position;
                bArr2[i4 + 1] = (byte) cCharAt2;
                bArr2[i4] = (byte) (cCharAt2 >>> '\b');
                this.position = i4 + 2;
                i2++;
            }
        }

        private final void writeVarInt(int i) {
            writeUInt((i >> 31) ^ (i << 1));
        }

        private Builder() {
            ThreadLocal<byte[]> threadLocal = bufLocal;
            byte[] bArr = threadLocal.get();
            this.buffer = bArr;
            if (bArr != null) {
                threadLocal.set(null);
            } else {
                this.buffer = new byte[1024];
            }
            ThreadLocal<ArrayList> threadLocal2 = refsLocal;
            ArrayList arrayList = threadLocal2.get();
            this.refs = arrayList;
            if (arrayList != null) {
                threadLocal2.set(null);
            } else {
                this.refs = new ArrayList(16);
            }
        }
    }

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    private static final class Parser {
        private byte[] buffer;
        private char[] charsBuffer;
        private int position;

        /* JADX INFO: Access modifiers changed from: private */
        public final void close() {
            this.position = 0;
            this.buffer = null;
            if (this.charsBuffer != null) {
                Wson.localCharsBufferCache.set(this.charsBuffer);
            }
            this.charsBuffer = null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final Object parse() {
            return readObject();
        }

        private final Object readArray() {
            int uInt = readUInt();
            JSONArray jSONArray = new JSONArray(uInt);
            for (int i = 0; i < uInt; i++) {
                jSONArray.add(readObject());
            }
            return jSONArray;
        }

        private final Object readDouble() {
            double dLongBitsToDouble = Double.longBitsToDouble(readLong());
            if (dLongBitsToDouble > 2.147483647E9d) {
                long j = (long) dLongBitsToDouble;
                if (dLongBitsToDouble - j < Double.MIN_NORMAL) {
                    return Long.valueOf(j);
                }
            }
            return Double.valueOf(dLongBitsToDouble);
        }

        private Object readFloat() {
            byte[] bArr = this.buffer;
            int i = this.position;
            int i2 = (bArr[i + 3] & 255) + ((bArr[i + 2] & 255) << 8) + ((bArr[i + 1] & 255) << 16) + ((bArr[i] & 255) << 24);
            this.position = i + 4;
            return Float.valueOf(Float.intBitsToFloat(i2));
        }

        private final long readLong() {
            byte[] bArr = this.buffer;
            long j = (bArr[r1 + 7] & 255) + ((bArr[r1 + 6] & 255) << 8) + ((bArr[r1 + 5] & 255) << 16) + ((bArr[r1 + 4] & 255) << 24) + ((bArr[r1 + 3] & 255) << 32) + ((bArr[r1 + 2] & 255) << 40) + ((255 & bArr[r1 + 1]) << 48) + (bArr[r1] << 56);
            this.position = this.position + 8;
            return j;
        }

        private final Object readMap() {
            int uInt = readUInt();
            JSONObject jSONObject = new JSONObject();
            for (int i = 0; i < uInt; i++) {
                jSONObject.put((JSONObject) readMapKeyUTF16(), (String) readObject());
            }
            return jSONObject;
        }

        private final String readMapKeyUTF16() {
            int uInt = readUInt() / 2;
            if (this.charsBuffer.length < uInt) {
                this.charsBuffer = new char[uInt];
            }
            int i = 5381;
            if (Wson.IS_NATIVE_LITTLE_ENDIAN) {
                for (int i2 = 0; i2 < uInt; i2++) {
                    byte[] bArr = this.buffer;
                    int i3 = this.position;
                    char c = (char) ((bArr[i3] & 255) + (bArr[i3 + 1] << 8));
                    this.charsBuffer[i2] = c;
                    i = (i << 5) + i + c;
                    this.position = i3 + 2;
                }
            } else {
                for (int i4 = 0; i4 < uInt; i4++) {
                    byte[] bArr2 = this.buffer;
                    int i5 = this.position;
                    char c2 = (char) ((bArr2[i5 + 1] & 255) + (bArr2[i5] << 8));
                    this.charsBuffer[i4] = c2;
                    i = (i << 5) + i + c2;
                    this.position = i5 + 2;
                }
            }
            int length = (Wson.globalStringBytesCache.length - 1) & i;
            String str = Wson.globalStringBytesCache[length];
            if (str != null && str.length() == uInt) {
                for (int i6 = 0; i6 < uInt; i6++) {
                    if (this.charsBuffer[i6] == str.charAt(i6)) {
                    }
                }
                return str;
            }
            String str2 = new String(this.charsBuffer, 0, uInt);
            if (uInt < 64) {
                Wson.globalStringBytesCache[length] = str2;
            }
            return str2;
        }

        private final Object readObject() {
            byte type = readType();
            if (type == 48) {
                return null;
            }
            if (type == 70) {
                return readFloat();
            }
            if (type == 91) {
                return readArray();
            }
            if (type == 105) {
                return Integer.valueOf(readVarInt());
            }
            if (type == 108) {
                return Long.valueOf(readLong());
            }
            if (type == 123) {
                return readMap();
            }
            if (type == 115) {
                return readUTF16String();
            }
            if (type == 116) {
                return Boolean.TRUE;
            }
            switch (type) {
                case 100:
                    return readDouble();
                case PickerConfig.PICKER_IMAGE_VIDEO /* 101 */:
                    return new BigDecimal(readUTF16String());
                case 102:
                    return Boolean.FALSE;
                case 103:
                    return new BigInteger(readUTF16String());
                default:
                    throw new RuntimeException("wson unhandled type " + ((int) type) + Operators.SPACE_STR + this.position + " length " + this.buffer.length);
            }
        }

        private final byte readType() {
            byte[] bArr = this.buffer;
            int i = this.position;
            byte b = bArr[i];
            this.position = i + 1;
            return b;
        }

        private final int readUInt() {
            int i = 0;
            int i2 = 0;
            do {
                byte[] bArr = this.buffer;
                int i3 = this.position;
                byte b = bArr[i3];
                if ((b & ByteCompanionObject.MIN_VALUE) == 0) {
                    this.position = i3 + 1;
                    return i | (b << i2);
                }
                i |= (b & ByteCompanionObject.MAX_VALUE) << i2;
                i2 += 7;
                this.position = i3 + 1;
            } while (i2 <= 35);
            throw new IllegalArgumentException("Variable length quantity is too long");
        }

        private final String readUTF16String() {
            int uInt = readUInt() / 2;
            if (this.charsBuffer.length < uInt) {
                this.charsBuffer = new char[uInt];
            }
            if (Wson.IS_NATIVE_LITTLE_ENDIAN) {
                for (int i = 0; i < uInt; i++) {
                    byte[] bArr = this.buffer;
                    int i2 = this.position;
                    this.charsBuffer[i] = (char) ((bArr[i2] & 255) + (bArr[i2 + 1] << 8));
                    this.position = i2 + 2;
                }
            } else {
                for (int i3 = 0; i3 < uInt; i3++) {
                    byte[] bArr2 = this.buffer;
                    int i4 = this.position;
                    this.charsBuffer[i3] = (char) ((bArr2[i4 + 1] & 255) + (bArr2[i4] << 8));
                    this.position = i4 + 2;
                }
            }
            return new String(this.charsBuffer, 0, uInt);
        }

        private final int readVarInt() {
            int uInt = readUInt();
            return (uInt & Integer.MIN_VALUE) ^ ((((uInt << 31) >> 31) ^ uInt) >> 1);
        }

        private Parser(byte[] bArr) {
            this.position = 0;
            this.buffer = bArr;
            char[] cArr = (char[]) Wson.localCharsBufferCache.get();
            this.charsBuffer = cArr;
            if (cArr != null) {
                Wson.localCharsBufferCache.set(null);
            } else {
                this.charsBuffer = new char[512];
            }
        }
    }

    static {
        IS_NATIVE_LITTLE_ENDIAN = ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN;
        localCharsBufferCache = new ThreadLocal<>();
        globalStringBytesCache = new String[2048];
        methodsCache = new LruCache<>(128);
        fieldsCache = new LruCache<>(128);
        specialClass = new LruCache<>(16);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final List<Field> getBeanFields(String str, Class cls) throws SecurityException {
        List<Field> arrayList = fieldsCache.get(str);
        if (arrayList == null) {
            Field[] fields = cls.getFields();
            arrayList = new ArrayList<>(fields.length);
            for (Field field : fields) {
                if ((field.getModifiers() & 8) == 0) {
                    if (field.getAnnotation(JSONField.class) != null) {
                        throw new UnsupportedOperationException("getBeanMethod JSONField Annotation Not Handled, Use toJSON");
                    }
                    arrayList.add(field);
                }
            }
            fieldsCache.put(str, arrayList);
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final List<Method> getBeanMethod(String str, Class cls) throws SecurityException {
        List<Method> arrayList = methodsCache.get(str);
        if (arrayList == null) {
            arrayList = new ArrayList<>();
            for (Method method : cls.getMethods()) {
                if (method.getDeclaringClass() != Object.class && (method.getModifiers() & 8) == 0) {
                    String name = method.getName();
                    if (name.startsWith(METHOD_PREFIX_GET) || name.startsWith(METHOD_PREFIX_IS)) {
                        if (method.getAnnotation(JSONField.class) != null) {
                            throw new UnsupportedOperationException("getBeanMethod JSONField Annotation Not Handled, Use toJSON");
                        }
                        arrayList.add(method);
                    }
                }
            }
            methodsCache.put(str, arrayList);
        }
        return arrayList;
    }

    public static final Object parse(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        try {
            Parser parser = new Parser(bArr);
            Object obj = parser.parse();
            parser.close();
            return obj;
        } catch (Exception e) {
            WXLogUtils.e("parseWson", e);
            return null;
        }
    }

    public static final byte[] toWson(Object obj) {
        if (obj == null) {
            return null;
        }
        Builder builder = new Builder();
        byte[] wson = builder.toWson(obj);
        builder.close();
        return wson;
    }
}
