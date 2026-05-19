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

public class ViewAllMatchesAdapter extends RecyclerView.Adapter<ViewAllMatchesAdapter.MatchViewHolder> {

    private Context context;
    private List<ViewAllMatchesActivity.MatchItem> matchList;

    public ViewAllMatchesAdapter(Context context, List<ViewAllMatchesActivity.MatchItem> matchList) {
        this.context = context;
        this.matchList = matchList;
    }

    @NonNull
    @Override
    public MatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_match_card, parent, false);
        return new MatchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchViewHolder holder, int position) {
        ViewAllMatchesActivity.MatchItem match = matchList.get(position);

        holder.txtName.setText(match.getName());
        holder.txtSkillCategory.setText(match.getSkillCategory());
        holder.txtDescription.setText(match.getDescription());
        holder.txtRating.setText("★ " + match.getRating());
        holder.txtTeaches.setText("TEACHES: " + match.getTeaches());
        holder.txtWantsLearn.setText("WANTS: " + match.getWantsToLearn());
        holder.imgProfile.setImageResource(match.getImageResourceId());
    }

    @Override
    public int getItemCount() {
        return matchList.size();
    }

    public static class MatchViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProfile;
        TextView txtName, txtSkillCategory, txtDescription, txtRating, txtTeaches, txtWantsLearn;

        public MatchViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProfile = itemView.findViewById(R.id.img_profile);
            txtName = itemView.findViewById(R.id.txt_name);
            txtSkillCategory = itemView.findViewById(R.id.txt_skill_category);
            txtDescription = itemView.findViewById(R.id.txt_description);
            txtRating = itemView.findViewById(R.id.txt_rating);
            txtTeaches = itemView.findViewById(R.id.txt_teaches);
            txtWantsLearn = itemView.findViewById(R.id.txt_wants_learn);
        }
    }
}
