package com.nimko.myosmdroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;

import com.nimko.myosmdroid.adapter.MyRecyclerViewAdapter;
import com.nimko.myosmdroid.api_service.ApiService;
import com.nimko.myosmdroid.databinding.ActivityListBinding;
import com.nimko.myosmdroid.models.fromApi.ClubNewsStatus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ListActivity extends AppCompatActivity {

    private ActivityListBinding binding;
    private List<ClubNewsStatus> listFromApi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));


        ApiService api = new ApiService();
        api.getClubNewsStatus()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    listFromApi = api.getGetClubStatusList();
                    Log.d("FROM API", listFromApi.toString());
                    binding.recycler.setAdapter(new MyRecyclerViewAdapter(listFromApi));
                });


    }
}