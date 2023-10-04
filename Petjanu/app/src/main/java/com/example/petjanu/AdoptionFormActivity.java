package com.example.petjanu;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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

public class AdoptionFormActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText addressEditText;
    private ImageView proofOfResidenceImageView;
    private Button uploadProofButton;
    private Button submitButton;

    private FirebaseAuth auth;
    private FirebaseUser currentUser;
    private DatabaseReference mDatabase;
    private StorageReference storageReference;

    private Uri proofImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adoption_form);

        nameEditText = findViewById(R.id.nameEditText);
        addressEditText = findViewById(R.id.addressEditText);
        proofOfResidenceImageView = findViewById(R.id.proofOfResidenceImageView);
        uploadProofButton = findViewById(R.id.uploadProofButton);
        submitButton = findViewById(R.id.submitButton);

        // Adiciona a importação do Firebase
        importFirebase();

        // Instancia o Firebase Authentication
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();

        // Instancia o Firebase Database
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Instancia o Firebase Storage
        storageReference = FirebaseStorage.getInstance().getReference();

        uploadProofButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitAdoptionForm();
            }
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            proofImageUri = data.getData();
            proofOfResidenceImageView.setImageURI(proofImageUri);
        }
    }

    private void submitAdoptionForm() {
        final String name = nameEditText.getText().toString().trim();
        final String address = addressEditText.getText().toString().trim();

        if (name.isEmpty() || address.isEmpty() || proofImageUri == null) {
            Toast.makeText(this, "Preencha todos os campos e faça o upload do comprovante de residência.", Toast.LENGTH_SHORT).show();
            return;
        }

        final String userId = currentUser.getUid();
        final String adoptionStatus = "Em andamento"; // Pode ser "Em andamento", "Aprovado", "Rejeitado", etc.

        // Upload do comprovante de residência
        final StorageReference fileReference = storageReference.child("proofs/" + System.currentTimeMillis() + ".jpg");
        fileReference.putFile(proofImageUri).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri downloadUri) {
                        // Save the download URL to the database.
                    }
                });
            }
        });
    }
}
