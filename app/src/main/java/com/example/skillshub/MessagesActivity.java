package com.example.skillshub;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessagesActivity extends AppCompatActivity {

    private ImageView btnBack, btnMore, btnSend;
    private CircleImageView imgProfile;
    private TextView txtName, txtStatus;
    private RecyclerView messagesRecyclerView;
    private EditText editMessage;
    
    private String matchName;
    private String matchSkill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        
        setContentView(R.layout.activity_messages);

        // Get data from intent
        matchName = getIntent().getStringExtra("match_name");
        matchSkill = getIntent().getStringExtra("match_skill");

        // Initialize views
        initViews();

        // Set up UI
        setupUI();

        // Set click listeners
        setClickListeners();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btn_back);
        btnMore = findViewById(R.id.btn_more);
        btnSend = findViewById(R.id.btn_send);
        imgProfile = findViewById(R.id.img_profile);
        txtName = findViewById(R.id.txt_name);
        txtStatus = findViewById(R.id.txt_status);
        messagesRecyclerView = findViewById(R.id.messages_recycler_view);
        editMessage = findViewById(R.id.edit_message);
    }

    private void setupUI() {
        // Set match name
        if (matchName != null) {
            txtName.setText(matchName);
        }

        // Setup RecyclerView
        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        
        // TODO: Set up messages adapter when ready
        // For now, show empty state
        Toast.makeText(this, "Chat with " + matchName, Toast.LENGTH_SHORT).show();
    }

    private void setClickListeners() {
        btnBack.setOnClickListener(v -> finish());

        btnMore.setOnClickListener(v -> {
            Toast.makeText(this, "More options", Toast.LENGTH_SHORT).show();
        });

        btnSend.setOnClickListener(v -> sendMessage());
    }

    private void sendMessage() {
        String message = editMessage.getText().toString().trim();
        
        if (message.isEmpty()) {
            Toast.makeText(this, "Please enter a message", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO: Implement actual message sending to Firebase
        Toast.makeText(this, "Message sent: " + message, Toast.LENGTH_SHORT).show();
        editMessage.setText("");
    }
}
