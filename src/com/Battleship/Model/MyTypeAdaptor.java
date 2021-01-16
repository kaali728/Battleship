package com.Battleship.Model;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * The type My type adaptor.
 *
 * @param <T> the type parameter
 */
public class MyTypeAdaptor<T> extends TypeAdapter<T> {
    public T read(JsonReader reader) throws IOException {
        return null;
    }

    public void write(JsonWriter writer, T obj) throws IOException {
        if (obj == null) {
            writer.nullValue();
            return;
        }
        writer.value(obj.toString());
    }
}
