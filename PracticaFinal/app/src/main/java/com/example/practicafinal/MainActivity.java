package com.example.practicafinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Pelicula> peliculas = Datos.rellenaPeliculas();
    ArrayList<Pelicula> favoritos = new ArrayList<>();
    RecyclerView rv;
    Adaptador1 ad1;
    Toolbar tb;
    Menu menuPrincipal;
    ImageButton btn;
    TextView tv;
    boolean iconChanged = false;
    boolean normal = true;


    RecyclerView.LayoutManager manager1;
    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new
            ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK) {
                Intent data = result.getData();
                if (data != null) {
                    if (data.hasExtra("peliculas")) {
                        ArrayList<Pelicula> peliculasActualizadas = (ArrayList<Pelicula>) data.getSerializableExtra("peliculas");
                        if (peliculasActualizadas != null && peliculas != peliculasActualizadas) {
                            peliculas.clear();
                            peliculas.addAll(peliculasActualizadas);
                            ad1.notifyDataSetChanged();
                        }
                    }
                    if (data.hasExtra("favoritos")) {
                        ArrayList<Pelicula> favoritosActualizados = (ArrayList<Pelicula>) data.getSerializableExtra("favoritos");
                        if (favoritosActualizados != null) {
                            favoritos.clear();
                            favoritos.addAll(favoritosActualizados);
                            ad1.notifyDataSetChanged();
                        }
                    }
                    if (data.hasExtra("peli")) {
                        Pelicula peli = (Pelicula) data.getSerializableExtra("peli");
                        if (peli != null) {
                            peliculas.add(peli);
                            ad1.notifyDataSetChanged();
                        }
                    }

                }
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        tb = findViewById(R.id.tbPrincipal);
        setSupportActionBar(tb);
        getSupportActionBar().setTitle("Peliculas");
        rv = findViewById(R.id.rv);
        tv = findViewById(R.id.textView);
        ad1 = new Adaptador1(peliculas, favoritos, tv, normal);
        manager1 = new LinearLayoutManager(this);
        rv.setLayoutManager(manager1);
        rv.setAdapter(ad1);
        rv.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
//        ad1.setOnItemSelectedListener(pos -> {
//            if (ad1.getSelectedPos() >= 0) {
//                getSupportActionBar().setTitle(peliculas.get(ad1.getSelectedPos()).getTitulo());
//            } else {
//                getSupportActionBar().setTitle("Peliculas");
//            }
//        });


        btn = findViewById(R.id.imgBtnHide);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getSupportActionBar().isShowing()) {
                    getSupportActionBar().hide();
                    btn.setImageResource(R.drawable.mostrar2);
                } else {
                    getSupportActionBar().show();
                    btn.setImageResource(R.drawable.ocultar2);
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menuprincipal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.listadoCompleto) {
            Intent intent = new Intent(MainActivity.this, ListadoCompleto.class);
            intent.putExtra("peliculas", peliculas);
            intent.putExtra("favoritos", favoritos);
            launcher.launch(intent);
            return true;
        } else if (id == R.id.favoritos) {
            Intent intent = new Intent(MainActivity.this, ListadoFavoritos.class);
            intent.putExtra("peliculas", peliculas);
            intent.putExtra("favoritos", favoritos);
            launcher.launch(intent);
            return true;
        } else if (id == R.id.añadirPelicula) {
            Intent intent = new Intent(MainActivity.this, NuevaPelicula.class);
            intent.putExtra("peliculas", peliculas);
            launcher.launch(intent);
            return true;
        } else if (id == R.id.mostrarColumnas) {

            if (!iconChanged) {
                item.setIcon(R.drawable.img_1);
                item.setTitle("Mostrar en 1 columna");
                iconChanged = true;
                rv.setLayoutManager(new GridLayoutManager(this, 2));
            } else {
                item.setIcon(R.drawable.img);
                rv.setLayoutManager(manager1);
                item.setTitle("Mostrar en 2 columnas");
                iconChanged = false;
            }
            return true;
        } else if (id == R.id.soloFavoritos) {
            if (normal) {
                ad1.setPeliculas(favoritos);
                normal = false;
            } else {
                ad1.setPeliculas(peliculas);
                normal = true;
            }
            ad1.setNormal(normal);
            ad1.notifyDataSetChanged();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}