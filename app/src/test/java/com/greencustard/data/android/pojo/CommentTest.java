package com.greencustard.data.android.pojo;

import com.greencustard.data.android.data.JacksonJsonDeserialiser;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class CommentTest {

    private JacksonJsonDeserialiser mDeserialiser;

    @Before
    public void setUp() {
        mDeserialiser = new JacksonJsonDeserialiser();
    }

    @Test
    public void parseSingle() {
        String source = "{\n" +
                "    \"postId\": 1,\n" +
                "    \"id\": 1,\n" +
                "    \"name\": \"id labore ex et quam laborum\",\n" +
                "    \"email\": \"Eliseo@gardner.biz\",\n" +
                "    \"body\": \"laudantium enim quasi est quidem magnam voluptate ipsam eos\"\n" +
                "  }";

        Comment comment = mDeserialiser.deserialise(Comment.class, source);

        assertThat(comment.getPostId(),is(1));
        assertThat(comment.getId(),is(1));
        assertThat(comment.getName(),is("id labore ex et quam laborum"));
        assertThat(comment.getEmail(),is("Eliseo@gardner.biz"));
        assertThat(comment.getBody(),is("laudantium enim quasi est quidem magnam voluptate ipsam eos"));
    }

}