/*
 * Copyright 2017 mihy,org.
 * All rights reserved.
 */
package org.mihy.gowma.utilities;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.List;

public final class JSONUtils {
    private static JsonMapper defaultMapper = JsonMapper.getDefault();
    private static JsonMapper mapperWithFormatting;

    static {
        mapperWithFormatting = JsonMapper.getDefault().configure(SerializationFeature.INDENT_OUTPUT, true);
    }

    private JSONUtils() {
    }

    public static String toJSON(Object o) {
        return defaultMapper.toJSON(o);
    }

    public static String toJSONWithFormatting(Object o) {
        return mapperWithFormatting.toJSON(o);
    }

    public static String toJSONUsingMixInAnnotations(Object o, Class mixinSource) {
        JsonMapper mapper = JsonMapper.getDefault();
        mapper.getMapper().addMixIn(o.getClass(), mixinSource);
        return mapper.toJSON(o);
    }

    public static String toJSONUsingMixInAnnotations(Object o, Class mixinSource, Class... classesToConfigure) {
        JsonMapper mapper = JsonMapper.getDefault();
        Class[] var4 = classesToConfigure;
        int var5 = classesToConfigure.length;

        for (int var6 = 0; var6 < var5; ++var6) {
            Class classToConfigure = var4[var6];
            mapper.getMapper().addMixIn(classToConfigure, mixinSource);
        }

        return mapper.toJSON(o);
    }

    public static <T> T fromJSON(String json, Class<T> classType) {
        return defaultMapper.fromJSON(json, classType);
    }

    public static <T> T fromJSON(String json, TypeReference ref) {
        return defaultMapper.fromJSON(json, ref);
    }

    public static <T> T fromJSONUsingMixInAnnotations(String json, Class<T> classType, Class mixinSource) {
        JsonMapper mapper = JsonMapper.getDefault();
        mapper.getMapper().addMixIn(classType.getClass(), mixinSource);
        return mapper.fromJSON(json, classType);
    }

    public static <T> List<T> fromJSONList(String json, Class<T> type) {
        JavaType javaType = defaultMapper.getMapper().getTypeFactory().constructParametrizedType(List.class, List.class, new Class[]{type});
        return (List) defaultMapper.fromJSON(json, javaType);
    }
}
