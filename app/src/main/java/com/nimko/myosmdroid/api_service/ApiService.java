package com.nimko.myosmdroid.api_service;


import android.app.Application;
import android.util.Log;

import com.nimko.myosmdroid.models.fromApi.ClubNewsStatus;
import com.nimko.myosmdroid.models.fromApi.Root;

import org.osmdroid.bonuspack.routing.OSRMRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.Polyline;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {

    private List<ClubNewsStatus> getClubStatusList;
    private RestApiRequest apiRequest;
    public ApiService() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ap.sportforall.gov.ua/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiRequest = retrofit.create(RestApiRequest.class);

    }

    public List<ClubNewsStatus> getGetClubStatusList() {
        return getClubStatusList;
    }

    public Single<ArrayList<ClubNewsStatus>> getClubNewsStatus(){
        return Single.create(new SingleOnSubscribe<ArrayList<ClubNewsStatus>>() {
            @Override
            public void subscribe(SingleEmitter<ArrayList<ClubNewsStatus>> emitter) throws Exception {
                try {
                    apiRequest.getData().execute();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                apiRequest.getData().enqueue(new Callback<Root>() {
                    @Override
                    public void onResponse(Call<Root> call, Response<Root> response) {
                        assert response.body() != null;
                        getClubStatusList = response.body().clubNewsStatuses;
                    }

                    @Override
                    public void onFailure(Call<Root> call, Throwable t) {
                        getClubStatusList = null;
                    }
                });
            }
        });
    }
}
