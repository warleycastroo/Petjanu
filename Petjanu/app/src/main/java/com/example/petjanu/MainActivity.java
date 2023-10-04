package com.example.petjanu;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                Intent intent = null;

                switch (id) {
                    case R.id.nav_home:
                        // Navegue para a tela inicial
                        intent = new Intent(MainActivity.this, HomeActivity.class);
                        break;
                    case R.id.nav_adoption:
                        // Navegue para a tela de adoção
                        intent = new Intent(MainActivity.this, AdoptionActivity.class);
                        break;
                    case R.id.nav_profile:
                        // Navegue para a tela de perfil
                        intent = new Intent(MainActivity.this, ProfileActivity.class);
                        break;
                    case R.id.nav_search:
                        // Navegue para a tela de pesquisa
                        intent = new Intent(MainActivity.this, SearchActivity.class);
                        break;
                    // Adicione mais casos conforme necessário

                    default:
                        // Não faça nada se o item do menu não estiver mapeado
                        break;
                }

                if (intent != null) {
                    startActivity(intent);
                }

                drawer.closeDrawer(Gravity.START);
                return true;
            }
        });
    }
}
