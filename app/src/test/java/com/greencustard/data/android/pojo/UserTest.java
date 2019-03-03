package com.greencustard.data.android.pojo;

import com.greencustard.data.android.data.JacksonJsonDeserialiser;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class UserTest {

    private JacksonJsonDeserialiser mDeserialiser;

    @Before
    public void setUp() {
        mDeserialiser = new JacksonJsonDeserialiser();
    }

    @Test
    public void parseSingle() {
        String source = "{\n" +
                "    \"id\": 1,\n" +
                "    \"name\": \"Leanne Graham\",\n" +
                "    \"username\": \"Bret\",\n" +
                "    \"email\": \"Sincere@april.biz\",\n" +
                "    \"address\": {\n" +
                "      \"street\": \"Kulas Light\",\n" +
                "      \"suite\": \"Apt. 556\",\n" +
                "      \"city\": \"Gwenborough\",\n" +
                "      \"zipcode\": \"92998-3874\",\n" +
                "      \"geo\": {\n" +
                "        \"lat\": \"-37.3159\",\n" +
                "        \"lng\": \"81.1496\"\n" +
                "      }\n" +
                "    },\n" +
                "    \"phone\": \"1-770-736-8031 x56442\",\n" +
                "    \"website\": \"hildegard.org\",\n" +
                "    \"company\": {\n" +
                "      \"name\": \"Romaguera-Crona\",\n" +
                "      \"catchPhrase\": \"Multi-layered client-server neural-net\",\n" +
                "      \"bs\": \"harness real-time e-markets\"\n" +
                "    }\n" +
                "  }";

        User user = mDeserialiser.deserialise(User.class, source);

        assertThat(user.getId(),is(1));
        assertThat(user.getName(),is("Leanne Graham"));
        assertThat(user.getUsername(),is("Bret"));
        assertThat(user.getEmail(),is("Sincere@april.biz"));
    }
}