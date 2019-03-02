package com.greencustard.datadata.android.data;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

import io.reactivex.Single;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.*;

public class ApacheRestClientITTest {

    private ApacheRestClient mClient;

    @Before
    public void setUp(){
        mClient = new ApacheRestClient();
    }

    @Ignore //Inappropriately slow to leave in without messing with default timeouts etc
    @Test (expected = Throwable.class)
    public void getNonsenseUrl() throws MalformedURLException {
        Single<String> result = mClient.getUrl(new URL("http://192.192.192.192/path/that/does/not/exist"));
        result.blockingGet();
    }

    @Test
    public void getValidUrl() throws MalformedURLException {
        Single<String> result = mClient.getUrl(new URL("http://www.google.com"));
        String body = result.blockingGet();
        assertThat(body.length(),greaterThan(0));
    }
}