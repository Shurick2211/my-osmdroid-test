package com.nimko.myosmdroid.api_service;


import android.util.Log;

import androidx.annotation.NonNull;

import com.nimko.myosmdroid.models.fromApi.ClubNewsStatus;
import com.nimko.myosmdroid.models.fromApi.Root;

import java.util.ArrayList;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {

    private final RestApiRequest apiRequest;
    public ApiService() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ap.sportforall.gov.ua/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiRequest = retrofit.create(RestApiRequest.class);

    }


    public Single<ArrayList<ClubNewsStatus>> getClubNewsStatus(){

        return Single.create(emitter -> apiRequest.getData().enqueue(new Callback<Root>() {
            @Override
            public void onResponse(@NonNull Call<Root> call, @NonNull Response<Root> response) {
                assert response.body() != null;
                emitter.onSuccess(response.body().clubNewsStatuses);
            }

            @Override
            public void onFailure(@NonNull Call<Root> call, @NonNull Throwable t) {
                Log.d("API_SERVICE", t.toString());
            }
        }));
    }
}
