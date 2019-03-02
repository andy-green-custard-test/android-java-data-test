package com.greencustard.datadata.android.data;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class JacksonJsonDeserialiserTest {

    private JacksonJsonDeserialiser mDeserialiser;

    @Before
    public void setUp() {
        mDeserialiser = new JacksonJsonDeserialiser();
    }

    @Test
    public void deserialiseMap() { 
        String jsonString = "{\"hello\":\"world\"}";
        Map<String,String> result = mDeserialiser.deserialise(Map.class,jsonString);
        assertThat(result,is((Map<String,String>)ImmutableMap.of("hello","world")));
    }

    @Test (expected = Throwable.class)
    public void deserialiseNonsense() {
        String jsonString = "I am not JSON";
        Map<String,String> result = mDeserialiser.deserialise(Map.class,jsonString);
    }


}