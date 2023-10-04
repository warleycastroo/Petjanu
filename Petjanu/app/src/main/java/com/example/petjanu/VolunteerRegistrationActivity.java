import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class VolunteerRegistrationActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText emailEditText;
    private Button registerButton;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_registration);

        // Inicialize o Firebase
        mDatabase = FirebaseDatabase.getInstance().getReference();

        nameEditText = findViewById(R.id.nameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        registerButton = findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtenha os valores do nome e do email
                String nome = nameEditText.getText().toString();
                String email = emailEditText.getText().toString();

                // Crie um objeto de voluntário com nome e e-mail
                Voluntario voluntario = new Voluntario(nome, email);

                // Salve os dados do voluntário no banco de dados Firebase
                mDatabase.child("voluntarios").push().setValue(voluntario);

                // Navegue para a próxima tela ou execute ação desejada após o registro
                // Por exemplo, você pode navegar para a tela de boas-vindas ou exibir uma mensagem de sucesso.
                // **Alteração:** Adicione um `finish()` após `startActivity(welcomeIntent)` para fechar a tela de registro após a navegação.
                Intent welcomeIntent = new Intent(VolunteerRegistrationActivity.this, WelcomeActivity.class);
                startActivity(welcomeIntent);
                finish();
            }
        });
    }
}
