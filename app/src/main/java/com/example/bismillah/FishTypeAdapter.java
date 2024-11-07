package com.example.bismillah;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FishTypeAdapter extends RecyclerView.Adapter<FishTypeAdapter.FishViewHolder> {
    private List<FishType> fishList;
    private Context context;

    public FishTypeAdapter(List<FishType> fishList, Context context) {
        this.fishList = fishList;
        this.context = context;
    }

    @NonNull
    @Override
    public FishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fish_type, parent, false);
        return new FishViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FishViewHolder holder, int position) {
        // Get fish data for left card
        int leftIndex = position * 2;
        if (leftIndex < fishList.size()) {
            FishType leftFish = fishList.get(leftIndex);
            holder.fishNameLeft.setText(leftFish.getName());
            holder.fishCategoryLeft.setText(leftFish.getCategory());
            holder.fishImageLeft.setImageResource(leftFish.getImageResId());
            holder.cardViewLeft.setVisibility(View.VISIBLE);
            
            holder.cardViewLeft.setOnClickListener(v -> {
                Intent intent = new Intent(context, FishDetailActivity.class);
                intent.putExtra("fishName", leftFish.getName());
                intent.putExtra("imageResId", leftFish.getImageResId());
                context.startActivity(intent);
            });
        } else {
            holder.cardViewLeft.setVisibility(View.GONE);
        }

        // Get fish data for right card
        int rightIndex = leftIndex + 1;
        if (rightIndex < fishList.size()) {
            FishType rightFish = fishList.get(rightIndex);
            holder.fishNameRight.setText(rightFish.getName());
            holder.fishCategoryRight.setText(rightFish.getCategory());
            holder.fishImageRight.setImageResource(rightFish.getImageResId());
            holder.cardViewRight.setVisibility(View.VISIBLE);
            
            holder.cardViewRight.setOnClickListener(v -> {
                Intent intent = new Intent(context, FishDetailActivity.class);
                intent.putExtra("fishName", rightFish.getName());
                intent.putExtra("imageResId", rightFish.getImageResId());
                context.startActivity(intent);
            });
        } else {
            holder.cardViewRight.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return (fishList.size() + 1) / 2;
    }

    public static class FishViewHolder extends RecyclerView.ViewHolder {
        TextView fishNameLeft, fishCategoryLeft;
        TextView fishNameRight, fishCategoryRight;
        ImageView fishImageLeft, fishImageRight;
        CardView cardViewLeft, cardViewRight;

        public FishViewHolder(@NonNull View itemView) {
            super(itemView);
            fishNameLeft = itemView.findViewById(R.id.fishNameLeft);
            fishCategoryLeft = itemView.findViewById(R.id.fishCategoryLeft);
            fishImageLeft = itemView.findViewById(R.id.fishImageLeft);
            cardViewLeft = itemView.findViewById(R.id.cardViewLeft);

            fishNameRight = itemView.findViewById(R.id.fishNameRight);
            fishCategoryRight = itemView.findViewById(R.id.fishCategoryRight);
            fishImageRight = itemView.findViewById(R.id.fishImageRight);
            cardViewRight = itemView.findViewById(R.id.cardViewRight);
        }
    }
}