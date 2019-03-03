package com.greencustard.data.android.pojo;

import com.greencustard.data.android.data.JacksonJsonDeserialiser;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class PostTest {

    private JacksonJsonDeserialiser mDeserialiser;

    @Before
    public void setUp() {
        mDeserialiser = new JacksonJsonDeserialiser();
    }

    @Test
    public void parseSingle() {
        String source = "{\n" +
                "    \"userId\": 1,\n" +
                "    \"id\": 1,\n" +
                "    \"title\": \"sunt aut facere repellat provident occaecati excepturi optio reprehenderit\",\n" +
                "    \"body\": \"quia et suscipit\"" +
                "  }";

        Post post = mDeserialiser.deserialise(Post.class, source);

        assertThat(post.getUserId(),is(1));
        assertThat(post.getId(),is(1));
        assertThat(post.getTitle(),is("sunt aut facere repellat provident occaecati excepturi optio reprehenderit"));
        assertThat(post.getBody(),is("quia et suscipit"));
    }
}