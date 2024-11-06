package com.example.bismillah;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
        FishType fish = fishList.get(position);
        holder.fishName.setText(fish.getName());
        holder.fishCategory.setText(fish.getCategory());
        holder.fishImage.setImageResource(fish.getImageResId());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, FishDetailActivity.class);
            intent.putExtra("fishName", fish.getName());
            intent.putExtra("imageResId", fish.getImageResId());
            // Add other fish details if needed
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return fishList.size();
    }

    public static class FishViewHolder extends RecyclerView.ViewHolder {
        TextView fishName, fishCategory;
        ImageView fishImage;

        public FishViewHolder(@NonNull View itemView) {
            super(itemView);
            fishName = itemView.findViewById(R.id.fishName);
            fishCategory = itemView.findViewById(R.id.fishCategory);
            fishImage = itemView.findViewById(R.id.fishImage);
        }
    }
}
