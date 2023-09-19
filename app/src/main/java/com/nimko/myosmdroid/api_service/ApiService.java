package com.nimko.myosmdroid.api_service;


import android.util.Log;

import com.nimko.myosmdroid.models.fromApi.ClubNewsStatus;
import com.nimko.myosmdroid.models.fromApi.Root;

import java.util.ArrayList;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {

    private  RestApiRequest apiRequest;
    public ApiService() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ap.sportforall.gov.ua/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiRequest = retrofit.create(RestApiRequest.class);

    }


    public Single<ArrayList<ClubNewsStatus>> getClubNewsStatus(){

        return Single.create(new SingleOnSubscribe<ArrayList<ClubNewsStatus>>() {
            @Override
            public void subscribe(SingleEmitter<ArrayList<ClubNewsStatus>> emitter) throws Exception {

                apiRequest.getData().enqueue(new Callback<Root>() {
                    @Override
                    public void onResponse(Call<Root> call, Response<Root> response) {
                        Log.d("API_SERVICE", response.body().toString());
                        emitter.onSuccess(response.body().clubNewsStatuses);
                    }

                    @Override
                    public void onFailure(Call<Root> call, Throwable t) {
                        Log.d("API_SERVICE", t.toString());
                    }
                });
            }
        });
    }
}
