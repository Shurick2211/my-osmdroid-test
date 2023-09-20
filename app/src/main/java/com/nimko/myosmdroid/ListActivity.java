package com.nimko.myosmdroid;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.nimko.myosmdroid.adapter.MyRecyclerViewAdapter;
import com.nimko.myosmdroid.api_service.ApiService;
import com.nimko.myosmdroid.databinding.ActivityListBinding;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ListActivity extends AppCompatActivity {
    private MyRecyclerViewAdapter adapter;
    private ActivityListBinding binding;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(new ArrayList<>());
        binding.recycler.setAdapter(adapter);

        ApiService api = new ApiService();
        api.getClubNewsStatus()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    adapter.setClubNewsStatusList(s);
                });


    }
}