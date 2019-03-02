package com.greencustard.datadata.android.data;

import java.net.URL;

import io.reactivex.Single;

public interface RestClient {

    Single<String> getUrl(URL url);

}
