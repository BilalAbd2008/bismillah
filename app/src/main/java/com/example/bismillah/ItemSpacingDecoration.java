package com.example.bismillah;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemSpacingDecoration extends RecyclerView.ItemDecoration {
    private int spacing;

    public ItemSpacingDecoration(int spacing) {
        this.spacing = spacing;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                               @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.right = spacing;

        // Tambahkan spacing di kiri hanya untuk item pertama
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.left = spacing;
        }
    }
}
