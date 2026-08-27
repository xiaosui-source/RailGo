package io.dcloud.uts.gson.internal.sql;

import io.dcloud.uts.gson.Gson;
import io.dcloud.uts.gson.TypeAdapter;
import io.dcloud.uts.gson.TypeAdapterFactory;
import io.dcloud.uts.gson.reflect.TypeToken;
import io.dcloud.uts.gson.stream.JsonReader;
import io.dcloud.uts.gson.stream.JsonWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

/* loaded from: classes2.dex */
class SqlTimestampTypeAdapter extends TypeAdapter<Timestamp> {
    static final TypeAdapterFactory FACTORY = new TypeAdapterFactory() { // from class: io.dcloud.uts.gson.internal.sql.SqlTimestampTypeAdapter.1
        @Override // io.dcloud.uts.gson.TypeAdapterFactory
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
            if (typeToken.getRawType() == Timestamp.class) {
                return new SqlTimestampTypeAdapter(gson.getAdapter(Date.class));
            }
            return null;
        }
    };
    private final TypeAdapter<Date> dateTypeAdapter;

    private SqlTimestampTypeAdapter(TypeAdapter<Date> typeAdapter) {
        this.dateTypeAdapter = typeAdapter;
    }

    @Override // io.dcloud.uts.gson.TypeAdapter
    /* renamed from: read, reason: avoid collision after fix types in other method */
    public Timestamp read2(JsonReader jsonReader) throws IOException {
        Date date = this.dateTypeAdapter.read2(jsonReader);
        if (date != null) {
            return new Timestamp(date.getTime());
        }
        return null;
    }

    @Override // io.dcloud.uts.gson.TypeAdapter
    public void write(JsonWriter jsonWriter, Timestamp timestamp) throws IOException {
        this.dateTypeAdapter.write(jsonWriter, timestamp);
    }
}
