import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class DonationActivity extends AppCompatActivity {

    private RadioGroup donationTypeRadioGroup;
    private EditText donationAmountEditText;
    private Button donateButton;
    private DatabaseReference mDatabase;
    private StorageReference mStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation);

        // Inicialize as referências ao Firebase
        mDatabase = FirebaseDatabase.getInstance().getReference().child("donations");
        mStorage = FirebaseStorage.getInstance().getReference().child("donation_images");

        donationTypeRadioGroup = findViewById(R.id.donationTypeRadioGroup);
        donationAmountEditText = findViewById(R.id.donationAmountEditText);
        donateButton = findViewById(R.id.donateButton);

        donateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtenha o tipo de doação selecionado
                int selectedRadioId = donationTypeRadioGroup.getCheckedRadioButtonId();
                RadioButton selectedRadioButton = findViewById(selectedRadioId);
                String donationType = selectedRadioButton.getText().toString();

                // Obtenha o valor da doação
                String donationAmount = donationAmountEditText.getText().toString();

                // Crie um novo nó no banco de dados para armazenar os detalhes da doação
                DatabaseReference newDonationRef = mDatabase.push();
                newDonationRef.child("type").setValue(donationType);
                newDonationRef.child("amount").setValue(donationAmount);

                // Limpe o campo de valor da doação
                donationAmountEditText.setText("");

                // Implemente a lógica para fazer upload de imagens da doação para o Firebase Storage, se necessário

                // Exiba uma mensagem de confirmação para o usuário
                // Você pode usar um AlertDialog ou Toast para isso
            }
        });
    }
}
