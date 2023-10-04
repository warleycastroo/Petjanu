package com.example.petjanu;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private Spinner animalTypeSpinner, breedSpinner, sizeSpinner, ageSpinner, sortBySpinner;
    private Button applyFiltersButton;
    private ListView animalListView;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        databaseReference = FirebaseDatabase.getInstance().getReference("animals");

        // Inicialize os componentes da interface do usuário
        animalTypeSpinner = findViewById(R.id.spinnerAnimalType);
        breedSpinner = findViewById(R.id.spinnerBreed);
        sizeSpinner = findViewById(R.id.spinnerSize);
        ageSpinner = findViewById(R.id.spinnerAge);
        applyFiltersButton = findViewById(R.id.applyFiltersButton);
        animalListView = findViewById(R.id.animalListView);
        sortBySpinner = findViewById(R.id.sortBySpinner);

        // Inicialize e preencha os spinners com dados de filtro e classificação
        ArrayAdapter<CharSequence> animalTypeAdapter = ArrayAdapter.createFromResource(this, R.array.animal_types, android.R.layout.simple_spinner_item);
        animalTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        animalTypeSpinner.setAdapter(animalTypeAdapter);

        // Preencha os outros spinners da mesma forma

        // Configurar um ouvinte de clique para o botão de aplicar filtros
        applyFiltersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applyFilters();
            }
        });

        // Configurar um ouvinte de seleção para o spinner de classificação
        sortBySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                sortBy();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Não é necessário fazer nada aqui
            }
        });
    }

    private void applyFilters() {
        // Obtenha os filtros selecionados dos spinners
        String selectedAnimalType = animalTypeSpinner.getSelectedItem().toString();
        String selectedBreed = breedSpinner.getSelectedItem().toString();
        String selectedSize = sizeSpinner.getSelectedItem().toString();
        String selectedAge = ageSpinner.getSelectedItem().toString();

        // Crie uma consulta do Firebase Realtime Database para pesquisar os animais filtrados
        Query query = databaseReference;

        // Aplique os filtros selecionados à consulta
        if (!selectedAnimalType.isEmpty()) {
            query = query.orderByChild("animalType").equalTo(selectedAnimalType);
        }

        // Implemente a lógica para aplicar os outros filtros

        // Adicione um ouvinte de evento à consulta para obter os resultados da pesquisa
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Limpe a lista de animais
                List<Animal> filteredAnimals = new ArrayList<>();

                // Itere pelos resultados da pesquisa e adicione-os à lista
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Animal animal = snapshot.getValue(Animal.
