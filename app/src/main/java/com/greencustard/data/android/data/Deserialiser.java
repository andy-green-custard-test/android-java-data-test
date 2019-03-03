package com.greencustard.data.android.data;

public interface Deserialiser {
    <T> T deserialise(Class<T> expectedType, String body);
}
