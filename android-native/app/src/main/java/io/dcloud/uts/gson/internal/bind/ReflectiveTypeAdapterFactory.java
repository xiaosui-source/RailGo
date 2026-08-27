package io.dcloud.uts.gson.internal.bind;

import io.dcloud.uts.JsonNotNull;
import io.dcloud.uts.gson.FieldNamingStrategy;
import io.dcloud.uts.gson.Gson;
import io.dcloud.uts.gson.JsonParseException;
import io.dcloud.uts.gson.JsonSyntaxException;
import io.dcloud.uts.gson.TypeAdapter;
import io.dcloud.uts.gson.TypeAdapterFactory;
import io.dcloud.uts.gson.annotations.JsonAdapter;
import io.dcloud.uts.gson.annotations.SerializedName;
import io.dcloud.uts.gson.internal.C$Gson$Types;
import io.dcloud.uts.gson.internal.ConstructorConstructor;
import io.dcloud.uts.gson.internal.Excluder;
import io.dcloud.uts.gson.internal.ObjectConstructor;
import io.dcloud.uts.gson.internal.Primitives;
import io.dcloud.uts.gson.internal.reflect.ReflectionAccessor;
import io.dcloud.uts.gson.reflect.TypeToken;
import io.dcloud.uts.gson.stream.JsonReader;
import io.dcloud.uts.gson.stream.JsonToken;
import io.dcloud.uts.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public final class ReflectiveTypeAdapterFactory implements TypeAdapterFactory {
    private final ReflectionAccessor accessor = ReflectionAccessor.getInstance();
    private final ConstructorConstructor constructorConstructor;
    private final Excluder excluder;
    private final FieldNamingStrategy fieldNamingPolicy;
    private final JsonAdapterAnnotationTypeAdapterFactory jsonAdapterFactory;

    public ReflectiveTypeAdapterFactory(ConstructorConstructor constructorConstructor, FieldNamingStrategy fieldNamingStrategy, Excluder excluder, JsonAdapterAnnotationTypeAdapterFactory jsonAdapterAnnotationTypeAdapterFactory) {
        this.constructorConstructor = constructorConstructor;
        this.fieldNamingPolicy = fieldNamingStrategy;
        this.excluder = excluder;
        this.jsonAdapterFactory = jsonAdapterAnnotationTypeAdapterFactory;
    }

    public boolean excludeField(Field field, boolean z) {
        return excludeField(field, z, this.excluder);
    }

    static boolean excludeField(Field field, boolean z, Excluder excluder) {
        return (excluder.excludeClass(field.getType(), z) || excluder.excludeField(field, z)) ? false : true;
    }

    private List<String> getFieldNames(Field field) {
        SerializedName serializedName = (SerializedName) field.getAnnotation(SerializedName.class);
        if (serializedName == null) {
            return Collections.singletonList(this.fieldNamingPolicy.translateName(field));
        }
        String strValue = serializedName.value();
        String[] strArrAlternate = serializedName.alternate();
        if (strArrAlternate.length == 0) {
            return Collections.singletonList(strValue);
        }
        ArrayList arrayList = new ArrayList(strArrAlternate.length + 1);
        arrayList.add(strValue);
        for (String str : strArrAlternate) {
            arrayList.add(str);
        }
        return arrayList;
    }

    @Override // io.dcloud.uts.gson.TypeAdapterFactory
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
        Class<? super T> rawType = typeToken.getRawType();
        if (Object.class.isAssignableFrom(rawType)) {
            return new Adapter(this.constructorConstructor.get(typeToken), getBoundFields(gson, typeToken, rawType));
        }
        return null;
    }

    private BoundField createBoundField(final Gson gson, final Field field, String str, final TypeToken<?> typeToken, boolean z, boolean z2) {
        final boolean zIsPrimitive = Primitives.isPrimitive(typeToken.getRawType());
        JsonAdapter jsonAdapter = (JsonAdapter) field.getAnnotation(JsonAdapter.class);
        TypeAdapter<?> typeAdapter = jsonAdapter != null ? this.jsonAdapterFactory.getTypeAdapter(this.constructorConstructor, gson, typeToken, jsonAdapter) : null;
        final boolean z3 = typeAdapter != null;
        if (typeAdapter == null) {
            typeAdapter = gson.getAdapter(typeToken);
        }
        final TypeAdapter<?> typeAdapter2 = typeAdapter;
        final JsonNotNull jsonNotNull = (JsonNotNull) field.getAnnotation(JsonNotNull.class);
        return new BoundField(str, z, z2) { // from class: io.dcloud.uts.gson.internal.bind.ReflectiveTypeAdapterFactory.1
            @Override // io.dcloud.uts.gson.internal.bind.ReflectiveTypeAdapterFactory.BoundField
            void write(JsonWriter jsonWriter, Object obj) throws IllegalAccessException, IOException, IllegalArgumentException {
                (z3 ? typeAdapter2 : new TypeAdapterRuntimeTypeWrapper(gson, typeAdapter2, typeToken.getType())).write(jsonWriter, field.get(obj));
            }

            @Override // io.dcloud.uts.gson.internal.bind.ReflectiveTypeAdapterFactory.BoundField
            void read(JsonReader jsonReader, Object obj) throws IllegalAccessException, IOException, IllegalArgumentException {
                Object obj2 = typeAdapter2.read2(jsonReader);
                if (obj2 == null && zIsPrimitive) {
                    return;
                }
                field.set(obj, obj2);
            }

            @Override // io.dcloud.uts.gson.internal.bind.ReflectiveTypeAdapterFactory.BoundField
            boolean isShouldNotNull() {
                return jsonNotNull != null;
            }

            @Override // io.dcloud.uts.gson.internal.bind.ReflectiveTypeAdapterFactory.BoundField
            public boolean writeField(Object obj) throws IllegalAccessException, IOException {
                return this.serialized && field.get(obj) != obj;
            }
        };
    }

    private Map<String, BoundField> getBoundFields(Gson gson, TypeToken<?> typeToken, Class<?> cls) {
        ReflectiveTypeAdapterFactory reflectiveTypeAdapterFactory = this;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        if (!cls.isInterface()) {
            typeToken.getType();
            TypeToken<?> typeToken2 = typeToken;
            Class<?> rawType = cls;
            while (rawType != Object.class) {
                Field[] declaredFields = rawType.getDeclaredFields();
                int length = declaredFields.length;
                int i = 0;
                while (i < length) {
                    Field field = declaredFields[i];
                    boolean zExcludeField = reflectiveTypeAdapterFactory.excludeField(field, true);
                    boolean zExcludeField2 = reflectiveTypeAdapterFactory.excludeField(field, false);
                    if (zExcludeField || zExcludeField2) {
                        reflectiveTypeAdapterFactory.accessor.makeAccessible(field);
                        Type typeResolve = C$Gson$Types.resolve(typeToken2.getType(), rawType, field.getGenericType());
                        List<String> fieldNames = reflectiveTypeAdapterFactory.getFieldNames(field);
                        int size = fieldNames.size();
                        BoundField boundField = null;
                        int i2 = 0;
                        while (i2 < size) {
                            String str = fieldNames.get(i2);
                            if (i2 != 0) {
                                zExcludeField = false;
                            }
                            int i3 = i2;
                            int i4 = size;
                            boolean z = zExcludeField;
                            BoundField boundFieldCreateBoundField = reflectiveTypeAdapterFactory.createBoundField(gson, field, str, TypeToken.get(typeResolve), z, zExcludeField2);
                            if (!linkedHashMap.containsKey(str)) {
                                BoundField boundField2 = (BoundField) linkedHashMap.put(str, boundFieldCreateBoundField);
                                if (boundField == null) {
                                    boundField = boundField2;
                                }
                            }
                            i2 = i3 + 1;
                            reflectiveTypeAdapterFactory = this;
                            zExcludeField = z;
                            size = i4;
                        }
                    }
                    i++;
                    reflectiveTypeAdapterFactory = this;
                }
                typeToken2 = TypeToken.get(C$Gson$Types.resolve(typeToken2.getType(), rawType, rawType.getGenericSuperclass()));
                rawType = typeToken2.getRawType();
                reflectiveTypeAdapterFactory = this;
            }
        }
        return linkedHashMap;
    }

    static abstract class BoundField {
        final boolean deserialized;
        final String name;
        final boolean serialized;

        abstract boolean isShouldNotNull();

        abstract void read(JsonReader jsonReader, Object obj) throws IllegalAccessException, IOException;

        abstract void write(JsonWriter jsonWriter, Object obj) throws IllegalAccessException, IOException;

        abstract boolean writeField(Object obj) throws IllegalAccessException, IOException;

        protected BoundField(String str, boolean z, boolean z2) {
            this.name = str;
            this.serialized = z;
            this.deserialized = z2;
        }
    }

    public static final class Adapter<T> extends TypeAdapter<T> {
        private final Map<String, BoundField> boundFields;
        private final ObjectConstructor<T> constructor;
        private Map<String, BoundField> notReadFields;

        @Override // io.dcloud.uts.gson.TypeAdapter
        public void reset() {
            this.notReadFields = new HashMap(this.boundFields);
        }

        Adapter(ObjectConstructor<T> objectConstructor, Map<String, BoundField> map) {
            this.constructor = objectConstructor;
            this.boundFields = map;
        }

        @Override // io.dcloud.uts.gson.TypeAdapter
        /* renamed from: read */
        public T read2(JsonReader jsonReader) throws IOException {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            T tConstruct = this.constructor.construct();
            reset();
            try {
                jsonReader.beginObject();
                while (jsonReader.hasNext()) {
                    String strNextName = jsonReader.nextName();
                    BoundField boundField = this.boundFields.get(strNextName);
                    if (boundField == null || !boundField.deserialized) {
                        jsonReader.skipValue();
                    } else {
                        boundField.read(jsonReader, tConstruct);
                        this.notReadFields.remove(strNextName);
                    }
                }
                jsonReader.endObject();
                if (!this.notReadFields.isEmpty()) {
                    Iterator<Map.Entry<String, BoundField>> it = this.notReadFields.entrySet().iterator();
                    while (it.hasNext()) {
                        BoundField value = it.next().getValue();
                        if (value != null && value.isShouldNotNull()) {
                            throw new JsonParseException("Value of non-nullable member " + value.name + " cannot be null");
                        }
                    }
                }
                return tConstruct;
            } catch (IllegalAccessException e) {
                throw new AssertionError(e);
            } catch (IllegalStateException e2) {
                throw new JsonSyntaxException(e2);
            }
        }

        @Override // io.dcloud.uts.gson.TypeAdapter
        public void write(JsonWriter jsonWriter, T t) throws IOException {
            if (t == null) {
                jsonWriter.nullValue();
                return;
            }
            jsonWriter.beginObject();
            try {
                for (BoundField boundField : this.boundFields.values()) {
                    if (boundField.writeField(t)) {
                        jsonWriter.name(boundField.name);
                        boundField.write(jsonWriter, t);
                    }
                }
                jsonWriter.endObject();
            } catch (IllegalAccessException e) {
                throw new AssertionError(e);
            }
        }
    }
}
