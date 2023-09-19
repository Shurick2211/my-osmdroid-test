package com.nimko.myosmdroid.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nimko.myosmdroid.databinding.FragmentItemBinding;
import com.nimko.myosmdroid.models.fromApi.ClubNewsStatus;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ItemHolder> {
    private List<ClubNewsStatus> clubNewsStatusList = new ArrayList<>();

    public MyRecyclerViewAdapter(List<ClubNewsStatus> clubNewsStatusList) {
        this.clubNewsStatusList = clubNewsStatusList;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FragmentItemBinding v = FragmentItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ItemHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        holder.bind(clubNewsStatusList.get(position));
    }

    @Override
    public int getItemCount() {
        return clubNewsStatusList.size();
    }

    static class ItemHolder extends RecyclerView.ViewHolder{
        FragmentItemBinding binding;
        public ItemHolder(@NonNull FragmentItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(ClubNewsStatus item){
            binding.id.setText(item.id);
            binding.title.setText(item.title);

        }

    }




}

