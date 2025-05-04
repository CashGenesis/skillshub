package com.example.skillshub;

import com.example.skillshub.ExperienceItem;

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
import com.example.skillshub.ExperienceItem;

import java.util.List;

public class ExperienceAdapter extends RecyclerView.Adapter<ExperienceAdapter.ExperienceViewHolder> {

    private Context context;
    private List<ExperienceItem> experienceItems;

    public ExperienceAdapter(Context context, List<ExperienceItem> experienceItems) {
        this.context = context;
        this.experienceItems = experienceItems;
    }

    @NonNull
    @Override
    public ExperienceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_experience, parent, false);
        return new ExperienceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExperienceViewHolder holder, int position) {
        ExperienceItem item = experienceItems.get(position);

        holder.txtTitle.setText("#" + (position + 1) + "   " + item.getTitle());
        holder.txtDescription.setText(item.getDescription());

        if (item.getCertificateLink() != null && !item.getCertificateLink().isEmpty()) {
            holder.txtCertificateLink.setVisibility(View.VISIBLE);
            holder.txtCertificateLink.setText("Certificate link...");

            holder.txtCertificateLink.setOnClickListener(v -> {
                if (item.getCertificateLink().startsWith("http")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(item.getCertificateLink()));
                    context.startActivity(intent);
                }
            });
        } else {
            holder.txtCertificateLink.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return experienceItems != null ? experienceItems.size() : 0;
    }

    public void updateData(List<ExperienceItem> newItems) {
        this.experienceItems = newItems;
        notifyDataSetChanged();
    }

    public static class ExperienceViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle, txtDescription, txtCertificateLink;

        public ExperienceViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtExperienceTitle);
            txtDescription = itemView.findViewById(R.id.txtExperienceDescription);
            txtCertificateLink = itemView.findViewById(R.id.txtCertificateLink);
        }
    }
}