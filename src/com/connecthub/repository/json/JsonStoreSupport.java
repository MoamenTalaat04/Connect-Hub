package com.connecthub.repository.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

final class JsonStoreSupport {
    private JsonStoreSupport() {
    }

    static ObjectMapper createMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }

    static <T> List<T> readList(ObjectMapper mapper, String filePath, Class<T[]> type) {
        try {
            File file = new File(filePath);
            if (!file.exists() || file.length() == 0) {
                mapper.writeValue(file, new ArrayList<>());
                return new ArrayList<>();
            }
            T[] data = mapper.readValue(file, type);
            return new ArrayList<>(Arrays.asList(data));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    static void writeList(ObjectMapper mapper, String filePath, List<?> data) {
        try {
            mapper.writeValue(new File(filePath), data);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
