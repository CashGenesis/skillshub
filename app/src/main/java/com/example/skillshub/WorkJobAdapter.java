package com.example.skillshub;
import android.annotation.SuppressLint;
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
import java.util.ArrayList;

public class WorkJobAdapter extends RecyclerView.Adapter<WorkJobAdapter.JobViewHolder> {
    private ArrayList<WorkActivity.WorkJobItem> jobList;
    private Context context;

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
    public void onBindViewHolder(@NonNull JobViewHolder holder, @SuppressLint("RecyclerView") int position) {
        WorkActivity.WorkJobItem currentJob = jobList.get(position);

        holder.jobImage.setImageResource(currentJob.getImageResourceId());
        holder.jobTitle.setText(currentJob.getTitle());
        holder.jobDescription.setText(currentJob.getDescription());
        holder.jobRating.setText(String.valueOf(currentJob.getRating()));
        holder.jobTag1.setText(currentJob.getTag1());
        holder.jobTag2.setText(currentJob.getTag2());
        holder.jobPrice.setText("₹ " + currentJob.getPrice());

        // Set click listener for the card
        holder.jobCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, JobDetailActivity.class);

                // Pass job details to the detail activity
                intent.putExtra("JOB_TITLE", currentJob.getTitle());
                intent.putExtra("JOB_DESCRIPTION", currentJob.getDescription());
                intent.putExtra("JOB_RATING", currentJob.getRating());
                intent.putExtra("JOB_TAG1", currentJob.getTag1());
                intent.putExtra("JOB_TAG2", currentJob.getTag2());
                intent.putExtra("JOB_PRICE", currentJob.getPrice());
                intent.putExtra("JOB_IMAGE", currentJob.getImageResourceId());
                intent.putExtra("JOB_POSITION", position);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }

    public static class JobViewHolder extends RecyclerView.ViewHolder {
        ImageView jobImage;
        TextView jobTitle, jobDescription, jobRating, jobTag1, jobTag2, jobPrice;
        CardView jobCard;

        public JobViewHolder(@NonNull View itemView) {
            super(itemView);
            jobCard = itemView.findViewById(R.id.job_card);
            jobImage = itemView.findViewById(R.id.job_image);
            jobTitle = itemView.findViewById(R.id.job_title);
            jobDescription = itemView.findViewById(R.id.job_description);
            jobRating = itemView.findViewById(R.id.job_rating);
            jobTag1 = itemView.findViewById(R.id.job_tag1);
            jobTag2 = itemView.findViewById(R.id.job_tag2);
            jobPrice = itemView.findViewById(R.id.job_price);
        }
    }
}