package com.chenguang.courserasearch.network;

import com.chenguang.courserasearch.network.api.RestApi;

public class RestImplFactory {

    public static RestApi createRestImpl(String baseUrl) {
        return new RestImpl(baseUrl);
    }
}
