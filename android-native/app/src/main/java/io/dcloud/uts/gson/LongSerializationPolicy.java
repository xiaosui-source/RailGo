package io.dcloud.uts.gson;

/* loaded from: classes2.dex */
public enum LongSerializationPolicy {
    DEFAULT { // from class: io.dcloud.uts.gson.LongSerializationPolicy.1
        @Override // io.dcloud.uts.gson.LongSerializationPolicy
        public JsonElement serialize(Long l) {
            if (l == null) {
                return JsonNull.INSTANCE;
            }
            return new JsonPrimitive(l);
        }
    },
    STRING { // from class: io.dcloud.uts.gson.LongSerializationPolicy.2
        @Override // io.dcloud.uts.gson.LongSerializationPolicy
        public JsonElement serialize(Long l) {
            if (l == null) {
                return JsonNull.INSTANCE;
            }
            return new JsonPrimitive(l.toString());
        }
    };

    public abstract JsonElement serialize(Long l);
}
