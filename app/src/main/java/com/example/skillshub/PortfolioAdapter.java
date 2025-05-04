package com.example.skillshub;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skillshub.R;
import com.example.skillshub.PortfolioItem;

import java.util.List;

public class PortfolioAdapter extends RecyclerView.Adapter<PortfolioAdapter.PortfolioViewHolder> {

    private Context context;
    private List<PortfolioItem> portfolioItems;

    public PortfolioAdapter(Context context, List<PortfolioItem> portfolioItems) {
        this.context = context;
        this.portfolioItems = portfolioItems;
    }

    @NonNull
    @Override
    public PortfolioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_portfolio, parent, false);
        return new PortfolioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PortfolioViewHolder holder, int position) {
        PortfolioItem item = portfolioItems.get(position);

        holder.txtTitle.setText("#" + (position + 1) + "   " + item.getTitle());
        holder.txtDescription.setText(item.getDescription());

        if (item.getLink() != null && !item.getLink().isEmpty()) {
            holder.txtLink.setVisibility(View.VISIBLE);
            holder.txtLink.setText("Link...");

            holder.txtLink.setOnClickListener(v -> {
                if (item.getLink().startsWith("http")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(item.getLink()));
                    context.startActivity(intent);
                }
            });
        } else {
            holder.txtLink.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return portfolioItems != null ? portfolioItems.size() : 0;
    }

    public void updateData(List<PortfolioItem> newItems) {
        this.portfolioItems = newItems;
        notifyDataSetChanged();
    }

    public static class PortfolioViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle, txtDescription, txtLink;

        public PortfolioViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtPortfolioTitle);
            txtDescription = itemView.findViewById(R.id.txtPortfolioDescription);
            txtLink = itemView.findViewById(R.id.txtPortfolioLink);
        }
    }
}