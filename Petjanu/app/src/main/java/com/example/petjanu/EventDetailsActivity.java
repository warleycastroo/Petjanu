package com.example.petjanu;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EventDetailsActivity extends AppCompatActivity {

    private TextView eventNameTextView;
    private Button participateButton;
    private Button shareButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        eventNameTextView = findViewById(R.id.eventNameTextView);
        participateButton = findViewById(R.id.participateButton);
        shareButton = findViewById(R.id.shareButton);

        // Obtenha o nome do evento da intent
        Intent intent = getIntent();
        String eventName = intent.getStringExtra("eventName");
        eventNameTextView.setText(eventName);

        // Configurar ação de participação no evento
        participateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Implemente a lógica para permitir que os usuários marquem participação no evento
            }
        });

        // Configurar ação de compartilhamento nas redes sociais
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Implemente a lógica para compartilhar o evento nas redes sociais
            }
        });
    }
}
