package com.example.practicafinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import java.util.ArrayList;

public class ListadoFavoritos extends AppCompatActivity {

    ArrayList<Pelicula> peliculas;
    ArrayList<Pelicula> favoritos = new ArrayList<>();

    ArrayList<String> lista = new ArrayList<>();
    ArrayAdapter<String> adapter;
    ListView lv;
    Toolbar tb;
    ActionBar ab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_listado_favoritos);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        peliculas = (ArrayList<Pelicula>) getIntent().getSerializableExtra("peliculas");
        favoritos = (ArrayList<Pelicula>) getIntent().getSerializableExtra("favoritos") != null ? (ArrayList<Pelicula>) getIntent().getSerializableExtra("favoritos") : favoritos;
        lv = findViewById(R.id.listView);
        for (Pelicula pelicula : peliculas) {
            lista.add(pelicula.getTitulo() + "  " + pelicula.getDirector());
        }
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice, lista);
        lv.setAdapter(adapter);
        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        tb = findViewById(R.id.toolbar2);
        setSupportActionBar(tb);
        ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        for (int i = 0; i < peliculas.size(); i++) {
            if (favoritos.contains(peliculas.get(i))) {
                lv.setItemChecked(i, true);
            }
        }
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Pelicula p = peliculas.get(position);
                if (lv.isItemChecked(position)) {
                    if (!favoritos.contains(p)) {
                        favoritos.add(p);
                    }
                } else {
                    favoritos.remove(p);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_volver, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            Intent data = new Intent();
            data.putExtra("favoritos", favoritos);
            setResult(RESULT_OK, data);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
