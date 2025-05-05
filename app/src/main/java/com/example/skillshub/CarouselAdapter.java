package com.example.skillshub;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Adapter class for binding carousel items to the RecyclerView
 */
public class CarouselAdapter extends RecyclerView.Adapter<CarouselAdapter.CarouselViewHolder> {

    private Context context;
    private List<CarouselItem> carouselItems;

    public CarouselAdapter(Context context, List<CarouselItem> carouselItems) {
        this.context = context;
        this.carouselItems = carouselItems;
    }

    @NonNull
    @Override
    public CarouselViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_carousel_card, parent, false);
        return new CarouselViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarouselViewHolder holder, int position) {
        CarouselItem item = carouselItems.get(position);

        // Set the image resource and title
        holder.imageView.setImageResource(item.getImageResource());
        holder.titleTextView.setText(item.getTitle());

        // Set click listener for the item
        holder.frameLayout.setOnClickListener(v -> {
            // Show a toast for feedback
            Toast.makeText(context, "Selected: " + item.getTitle(), Toast.LENGTH_SHORT).show();

            // Create an intent to navigate to the same activity
            Intent intent = new Intent(context, CourseLearn.class);

            // Pass any data if needed (e.g., title or image resource)
            intent.putExtra("title", item.getTitle());
            intent.putExtra("imageResource", item.getImageResource());

            // Start the activity
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return carouselItems.size();
    }

    // ViewHolder class
    public static class CarouselViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleTextView;
        FrameLayout frameLayout;

        public CarouselViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.carouselItemImage);
            titleTextView = itemView.findViewById(R.id.carouselItemTitle);
            frameLayout = (FrameLayout) itemView;
        }
    }
}
