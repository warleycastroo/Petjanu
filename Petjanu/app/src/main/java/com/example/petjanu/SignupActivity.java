import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class SignupActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView profileImage;
    private Button uploadImageButton;
    private Uri imageUri;
    private FirebaseAuth auth;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Inicialize o Firebase
        auth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference("profile_images");
        databaseReference = FirebaseDatabase.getInstance().getReference("user_profiles");

        profileImage = findViewById(R.id.profileImage);
        uploadImageButton = findViewById(R.id.uploadImageButton);

        uploadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImageChooser();
            }
        });

        Button signupButton = findViewById(R.id.signupButton);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadUserProfile();
            }
        });
    }

    private void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            profileImage.setImageURI(imageUri);
        }
    }

    private void uploadUserProfile() {
        if (imageUri != null) {
            final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            final StorageReference imageRef = storageReference.child(userId + ".jpg");

            imageRef.putFile(imageUri)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            imageRef.getDownloadUrl().addOnCompleteListener(this, uriTask -> {
                                if (uriTask.isSuccessful()) {
                                    // Imagem de perfil foi carregada com sucesso, agora você pode salvar outras informações do perfil no Realtime Database
                                    saveUserProfile(uriTask.getResult().toString());
                                }
                            });
                        } else {
                            // Lidar com erros
                            Toast.makeText(SignupActivity.this, "Erro ao carregar a imagem de perfil.", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            // Se nenhuma imagem for selecionada, salve o perfil sem uma imagem
            saveUserProfile("");
        }
    }

    private void saveUserProfile(String profileImageUrl) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null)
