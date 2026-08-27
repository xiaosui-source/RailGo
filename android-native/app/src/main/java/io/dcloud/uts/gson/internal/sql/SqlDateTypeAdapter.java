package io.dcloud.uts.gson.internal.sql;

import io.dcloud.uts.gson.Gson;
import io.dcloud.uts.gson.JsonSyntaxException;
import io.dcloud.uts.gson.TypeAdapter;
import io.dcloud.uts.gson.TypeAdapterFactory;
import io.dcloud.uts.gson.reflect.TypeToken;
import io.dcloud.uts.gson.stream.JsonReader;
import io.dcloud.uts.gson.stream.JsonToken;
import io.dcloud.uts.gson.stream.JsonWriter;
import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/* loaded from: classes2.dex */
final class SqlDateTypeAdapter extends TypeAdapter<Date> {
    static final TypeAdapterFactory FACTORY = new TypeAdapterFactory() { // from class: io.dcloud.uts.gson.internal.sql.SqlDateTypeAdapter.1
        @Override // io.dcloud.uts.gson.TypeAdapterFactory
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
            if (typeToken.getRawType() == Date.class) {
                return new SqlDateTypeAdapter();
            }
            return null;
        }
    };
    private final DateFormat format;

    private SqlDateTypeAdapter() {
        this.format = new SimpleDateFormat("MMM d, yyyy");
    }

    @Override // io.dcloud.uts.gson.TypeAdapter
    /* renamed from: read, reason: avoid collision after fix types in other method */
    public synchronized Date read2(JsonReader jsonReader) throws IOException {
        if (jsonReader.peek() == JsonToken.NULL) {
            jsonReader.nextNull();
            return null;
        }
        try {
            return new Date(this.format.parse(jsonReader.nextString()).getTime());
        } catch (ParseException e) {
            throw new JsonSyntaxException(e);
        }
    }

    @Override // io.dcloud.uts.gson.TypeAdapter
    public synchronized void write(JsonWriter jsonWriter, Date date) throws IOException {
        jsonWriter.value(date == null ? null : this.format.format((java.util.Date) date));
    }
}
