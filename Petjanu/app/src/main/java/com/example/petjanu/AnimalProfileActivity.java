package com.example.petjanu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AnimalProfileActivity extends AppCompatActivity {

    private ListView commentsListView;
    private TextView descriptionTextView;
    private Button interestedButton;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    private List<Comment> commentsList;
    private CommentAdapter commentAdapter;
    private String animalId; // Suponha que você tenha o animalId de alguma forma

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_profile);

        commentsListView = findViewById(R.id.commentsListView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        interestedButton = findViewById(R.id.interestedButton);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        commentsList = new ArrayList<>();
        commentAdapter = new CommentAdapter(this, commentsList);
        commentsListView.setAdapter(commentAdapter);

        loadAnimalData();

        interestedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Implemente a lógica para iniciar um chat com o responsável pelo animal
                // Por exemplo, você pode abrir uma nova atividade de chat aqui
            }
        });
    }

    private void loadAnimalData() {
        DatabaseReference animalRef = mDatabase.child("animais").child(animalId);
        animalRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Animal animal = dataSnapshot.getValue(Animal.class);
                    descriptionTextView.setText(animal.getDescricao());
                    // Configure outros campos conforme necessário

                    // **Corrected code:**
                    // Adicione a data atual ao comentário
                    Date now = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    String dataAtual = sdf.format(now);

                    Comment comment = new Comment(animalId, "Interessado!", dataAtual);
                    commentsList.add(comment);
                    commentAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
            }
        });

        DatabaseReference comentariosRef = mDatabase.child("comentarios").orderByChild("animalId").equalTo(animalId);
        comentariosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                commentsList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Comment comment = snapshot.getValue(Comment.class);
                    commentsList.add(comment);
                }
                commentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
            }
        });
    }
}
