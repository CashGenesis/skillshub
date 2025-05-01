package com.example.skillshub;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

    private List<CardItem> cardItems;
    private Context context;
    // ✅ Updated constructor to accept Context
    public CardAdapter(Context context, List<CardItem> cardItems) {
        this.context = context;
        this.cardItems = cardItems;
    }
    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.carousel_item, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        CardItem item = cardItems.get(position);

        holder.appIcon.setImageResource(item.getIconResource());
        holder.titleText.setText(item.getTitle());
        holder.subtitleText.setText(item.getSubtitle());
        holder.priceChip.setText(item.getPrice());
        holder.descriptionText.setText(item.getDescription());
        holder.ratingText.setText(String.valueOf(item.getRating()));

        // Handle tags - assuming we have exactly 3 tags for simplicity
        List<String> tags = item.getTags();
        if (tags != null && tags.size() >= 3) {
            holder.tag1.setText(tags.get(0));
            holder.tag2.setText(tags.get(1));
            holder.tag3.setText(tags.get(2));
        }
    }

    @Override
    public int getItemCount() {
        return cardItems.size();
    }

    static class CardViewHolder extends RecyclerView.ViewHolder {
        ImageView appIcon;
        TextView titleText, subtitleText, priceChip, descriptionText, ratingText;
        TextView tag1, tag2, tag3;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);

            appIcon = itemView.findViewById(R.id.app_icon);
            titleText = itemView.findViewById(R.id.title_text);
            subtitleText = itemView.findViewById(R.id.subtitle_text);
            priceChip = itemView.findViewById(R.id.price_chip);
            descriptionText = itemView.findViewById(R.id.description_text);
            ratingText = itemView.findViewById(R.id.rating_text);
            tag1 = itemView.findViewById(R.id.tag1);
            tag2 = itemView.findViewById(R.id.tag2);
            tag3 = itemView.findViewById(R.id.tag3);
        }
    }
}