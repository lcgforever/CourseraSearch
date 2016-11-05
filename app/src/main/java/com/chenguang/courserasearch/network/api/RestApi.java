package com.chenguang.courserasearch.network.api;

import com.chenguang.courserasearch.network.data.QueryResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestApi {

    interface QueryApi {
        @GET("/api/catalogResults.v2?q=search&fields=courseId,onDemandSpecializationId,courses.v1(name,photoUrl,partnerIds),onDemandSpecializations.v1(name,logo,courseIds,partnerIds),partners.v1(name)&includes=courseId,onDemandSpecializationId,courses.v1(partnerIds)")
        Call<QueryResult> getQueryResult(@Query("query") String queryKeyword, @Query("start") int startIndex, @Query("limit") int limitNumber);
    }

    QueryApi getQueryApi();
}
