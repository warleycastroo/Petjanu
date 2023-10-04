import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Switch;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SettingsActivity extends AppCompatActivity {

    private Switch notificationSwitch;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        notificationSwitch = findViewById(R.id.notificationSwitch);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        // Verifique o estado atual da configuração de notificação e atualize o switch
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            DatabaseReference userSettingsRef = databaseReference.child("user_settings").child(userId);

            userSettingsRef.child("notifications_enabled").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Boolean notificationsEnabled = dataSnapshot.getValue(Boolean.class);
                    if (notificationsEnabled != null) {
                        notificationSwitch.setChecked(notificationsEnabled);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Trate os erros aqui
                }
            });
        }

        // Configure um ouvinte para o switch de notificação
        notificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                // Salve o estado da configuração de notificação no Firebase
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    String userId = user.getUid();
                    DatabaseReference userSettingsRef = databaseReference.child("user_settings").child(userId);
                    userSettingsRef.child("notifications_enabled").setValue(isChecked);
                }
            }
        });
    }
}
