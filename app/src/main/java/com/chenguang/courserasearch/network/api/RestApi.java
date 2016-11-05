package com.chenguang.courserasearch.network.api;

import com.chenguang.courserasearch.network.data.CourseQueryResult;
import com.chenguang.courserasearch.network.data.QueryResult;
import com.chenguang.courserasearch.network.data.SpecializationQueryResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RestApi {

    interface QueryApi {
        @GET("/api/catalogResults.v2?q=search&fields=courseId,onDemandSpecializationId,courses.v1(name,description,photoUrl,partnerIds),onDemandSpecializations.v1(name,description,logo,courseIds,partnerIds),partners.v1(name)&includes=courseId,onDemandSpecializationId,courses.v1(partnerIds)")
        Call<QueryResult> getQueryResult(@Query("query") String queryKeyword, @Query("start") int startIndex, @Query("limit") int limitNumber);
    }

    interface CourseQueryApi {
        @GET("/api/courses.v1/{courseId}?fields=photoUrl,description")
        Call<CourseQueryResult> getCourseQueryResult(@Path("courseId") String courseId);
    }

    interface SpecializationQueryApi {
        @GET("/api/onDemandSpecializations.v1/{specializationId}?fields=logo,description")
        Call<SpecializationQueryResult> getSpecializationQueryResult(@Path("specializationId") String specializationId);
    }

    QueryApi getQueryApi();

    CourseQueryApi getCourseQueryApi();

    SpecializationQueryApi getSpecializationQueryApi();
}
