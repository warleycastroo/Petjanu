import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SupportActivity extends AppCompatActivity {

    private EditText messageEditText;
    private Button sendButton;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);

        messageEditText = findViewById(R.id.messageEditText);
        sendButton = findViewById(R.id.sendButton);

        // Inicialize o Firebase
        mDatabase = FirebaseDatabase.getInstance().getReference("mensagens_suporte");

        sendButton.setOnClickListener(view -> {
            // Obtenha o texto da mensagem
            String mensagem = messageEditText.getText().toString().trim();

            // Verifique se a mensagem não está vazia
            if (!mensagem.isEmpty()) {
                // Crie um novo nó no Firebase com a mensagem de suporte
                DatabaseReference novaMensagemRef = mDatabase.push();

                // Obtenha a data atual
                Date data = new Date();

                // Crie uma nova mensagem de suporte
                MensagemSuporte mensagemSuporte = new MensagemSuporte(mensagem, data);

                // Defina o campo `data` da mensagem de suporte
                novaMensagemRef.setValue(mensagemSuporte);

                // Limpe o campo de texto
                messageEditText.setText("");

                // Exiba uma mensagem de sucesso ou execute outra ação após o envio
                // Por exemplo, você pode redirecionar o usuário de volta à tela principal.
                Toast.makeText(this, "Mensagem enviada com sucesso!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

class MensagemSuporte {

    private String mensagem;
    private Date data;

    public MensagemSuporte(String mensagem, Date data) {
        this.mensagem = mensagem;
        this.data = data;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
}
