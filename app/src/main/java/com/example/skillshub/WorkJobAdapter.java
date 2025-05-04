package com.example.skillshub;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class WorkJobAdapter extends RecyclerView.Adapter<WorkJobAdapter.JobViewHolder> {

    private Context context;
    private ArrayList<WorkActivity.WorkJobItem> jobList;

    public WorkJobAdapter(Context context, ArrayList<WorkActivity.WorkJobItem> jobList) {
        this.context = context;
        this.jobList = jobList;
    }

    @NonNull
    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.job_item, parent, false);
        return new JobViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobViewHolder holder, int position) {
        WorkActivity.WorkJobItem job = jobList.get(position);

        holder.title.setText(job.getTitle());
        holder.description.setText(job.getDescription());
        holder.rating.setText(String.valueOf(job.getRating()));
        holder.tag1.setText(job.getTag1());
        holder.tag2.setText(job.getTag2());
        holder.price.setText("₹" + job.getPrice());
        holder.image.setImageResource(job.getImageResourceId());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProfileWorkActivityy.class);
            intent.putExtra("title", job.getTitle());
            intent.putExtra("description", job.getDescription());
            intent.putExtra("rating", job.getRating());
            intent.putExtra("tag1", job.getTag1());
            intent.putExtra("tag2", job.getTag2());
            intent.putExtra("price", job.getPrice());
            intent.putExtra("imageResourceId", job.getImageResourceId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }

    public static class JobViewHolder extends RecyclerView.ViewHolder {
        TextView title, description, rating, tag1, tag2, price;
        ImageView image;

        public JobViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.job_title);
            description = itemView.findViewById(R.id.job_description);
            rating = itemView.findViewById(R.id.job_rating);
            tag1 = itemView.findViewById(R.id.job_tag1);
            tag2 = itemView.findViewById(R.id.job_tag2);
            price = itemView.findViewById(R.id.job_price);
            image = itemView.findViewById(R.id.job_image);
        }
    }
}
