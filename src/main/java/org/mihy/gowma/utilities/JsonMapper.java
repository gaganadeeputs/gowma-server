/*
 * Copyright 2017 mihy,org.
 * All rights reserved.
 */
package org.mihy.gowma.utilities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.io.StringWriter;


public class JsonMapper {
    private final ObjectMapper mapper = new ObjectMapper();

    public JsonMapper() {
        this.mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    }

    public static JsonMapper getDefault() {
        JsonMapper m = (new JsonMapper()).configure(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS, false).configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false).configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
        return m;
    }

    public ObjectMapper getMapper() {
        return this.mapper;
    }

    public String toJSON(Object o) {
        try {
            StringWriter w = new StringWriter();
            this.mapper.writeValue(w, o);
            return w.toString();
        } catch (IOException var3) {
            throw new IllegalStateException(var3);
        }
    }

    public <T> T fromJSON(String json, Class<T> classType) {
        try {
            return this.mapper.readValue(json, classType);
        } catch (IOException var4) {
            throw new IllegalStateException(var4);
        }
    }

    public <T> T fromJSON(String json, TypeReference ref) {
        try {
            return this.mapper.readValue(json, ref);
        } catch (IOException var4) {
            throw new IllegalStateException(var4);
        }
    }

    public <T> T fromJSON(String json, JavaType type) {
        try {
            return this.mapper.readValue(json, type);
        } catch (IOException var4) {
            throw new IllegalStateException(var4);
        }
    }

    public JsonMapper configure(SerializationFeature f, boolean state) {
        this.mapper.configure(f, state);
        return this;
    }

    public JsonMapper configure(DeserializationFeature f, boolean state) {
        this.mapper.configure(f, state);
        return this;
    }
}