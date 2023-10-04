import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WelcomeActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Lê um valor do banco de dados
        mDatabase.child("welcome_message").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String welcomeMessage = dataSnapshot.getValue(String.class);
                TextView welcomeText = findViewById(R.id.welcomeText);
                welcomeText.setText(welcomeMessage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Trate os erros aqui
            }
        });

        Button loginButton = findViewById(R.id.loginButton);
        Button signupButton = findViewById(R.id.signupButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navegar para a tela de login
                Intent loginIntent = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navegar para a tela de cadastro
                Intent signupIntent = new Intent(WelcomeActivity.this, SignupActivity.class);
                startActivity(signupIntent);
            }
        });
    }
}
