package com.nimko.myosmdroid.api_service;

import com.nimko.myosmdroid.models.fromApi.Root;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RestApiRequest {
    @GET("dictionaries")
    Call<Root> getData();
}
