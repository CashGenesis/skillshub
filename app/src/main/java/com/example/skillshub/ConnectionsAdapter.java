package com.example.skillshub;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ConnectionsAdapter extends RecyclerView.Adapter<ConnectionsAdapter.ConnectionViewHolder> {

    private Context context;
    private List<ViewAllActivity.ConnectionItem> connections;

    public ConnectionsAdapter(Context context, List<ViewAllActivity.ConnectionItem> connections) {
        this.context = context;
        this.connections = connections;
    }

    @NonNull
    @Override
    public ConnectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_connection, parent, false);
        return new ConnectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConnectionViewHolder holder, int position) {
        ViewAllActivity.ConnectionItem connection = connections.get(position);

        holder.txtName.setText(connection.getName());
        holder.txtLocation.setText("📍 " + connection.getLocation());
        holder.txtMatchPercentage.setText(connection.getMatchPercentage() + "%");
        holder.imgProfile.setImageResource(connection.getProfileImage());

        // Set teaches tags
        holder.txtTeaches1.setText(connection.getTeaches1());
        if (connection.getTeaches2() != null && !connection.getTeaches2().isEmpty()) {
            holder.txtTeaches2.setText(connection.getTeaches2());
            holder.txtTeaches2.setVisibility(View.VISIBLE);
        } else {
            holder.txtTeaches2.setVisibility(View.GONE);
        }

        // Set wants tags
        holder.txtWants1.setText(connection.getWants1());
        if (connection.getWants2() != null && !connection.getWants2().isEmpty()) {
            holder.txtWants2.setText(connection.getWants2());
            holder.txtWants2.setVisibility(View.VISIBLE);
        } else {
            holder.txtWants2.setVisibility(View.GONE);
        }

        // Connect button - Show match screen
        holder.btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to match confirmation screen
                Intent intent = new Intent(context, MatchConfirmationActivity.class);
                intent.putExtra("match_name", connection.getName());
                intent.putExtra("match_skill", connection.getTeaches1());
                intent.putExtra("user_skill", connection.getWants1());
                intent.putExtra("match_score", connection.getMatchPercentage());
                context.startActivity(intent);
            }
        });

        // Message button - Open messages screen
        holder.btnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to messages screen
                Intent intent = new Intent(context, MessagesActivity.class);
                intent.putExtra("match_name", connection.getName());
                intent.putExtra("match_skill", connection.getTeaches1());
                context.startActivity(intent);
            }
        });

        // Bookmark button
        holder.btnBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Bookmarked " + connection.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return connections.size();
    }

    static class ConnectionViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imgProfile;
        TextView txtName, txtLocation, txtMatchPercentage;
        TextView txtTeaches1, txtTeaches2;
        TextView txtWants1, txtWants2;
        Button btnConnect, btnMessage;
        ImageView btnBookmark;

        public ConnectionViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProfile = itemView.findViewById(R.id.img_profile);
            txtName = itemView.findViewById(R.id.txt_name);
            txtLocation = itemView.findViewById(R.id.txt_location);
            txtMatchPercentage = itemView.findViewById(R.id.txt_match_percentage);
            txtTeaches1 = itemView.findViewById(R.id.txt_teaches_1);
            txtTeaches2 = itemView.findViewById(R.id.txt_teaches_2);
            txtWants1 = itemView.findViewById(R.id.txt_wants_1);
            txtWants2 = itemView.findViewById(R.id.txt_wants_2);
            btnConnect = itemView.findViewById(R.id.btn_connect);
            btnMessage = itemView.findViewById(R.id.btn_message);
            btnBookmark = itemView.findViewById(R.id.btn_bookmark);
        }
    }
}
