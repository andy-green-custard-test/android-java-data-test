package com.greencustard.data.android.data;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JacksonJsonDeserialiser implements Deserialiser {

    private final ObjectMapper mObjectMapper = new ObjectMapper();

    @Override
    public <T> T deserialise(Class<T> expectedType, String body) throws IllegalArgumentException {
        try {
            return mObjectMapper.readValue(body, expectedType);
        } catch (IOException e) {
            throw new IllegalArgumentException("Jackson was unable to parse this",e);
        }
    }
}
