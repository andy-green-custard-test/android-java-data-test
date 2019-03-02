package com.greencustard.datadata.android.data;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import io.reactivex.Single;
import io.reactivex.subjects.SingleSubject;

public class ApacheRestClient implements RestClient {

    @Override
    public Single<String> getUrl(URL url) {

        SingleSubject<String> result = SingleSubject.create();

        try {
            final HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            if (connection.getResponseCode() != 200) {
                result.onError(new RuntimeException("Non 200 response received"));
            }
            else {
                final StringWriter writer = new StringWriter();
                IOUtils.copy(connection.getInputStream(), writer);
                final String body = writer.toString();

                result.onSuccess(body);
            }
        } catch (IOException e) {
            result.onError(e);
        }

        return result;
    }
}
