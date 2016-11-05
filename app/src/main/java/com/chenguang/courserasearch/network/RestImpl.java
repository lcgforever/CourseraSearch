package com.chenguang.courserasearch.network;

import com.chenguang.courserasearch.network.api.RestApi;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestImpl implements RestApi {

    private Retrofit retrofit;

    RestImpl(String baseUrl) {
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .build();
    }

    @Override
    public QueryApi getQueryApi() {
        return retrofit.create(QueryApi.class);
    }

    @Override
    public CourseQueryApi getCourseQueryApi() {
        return retrofit.create(CourseQueryApi.class);
    }

    @Override
    public SpecializationQueryApi getSpecializationQueryApi() {
        return retrofit.create(SpecializationQueryApi.class);
    }
}
