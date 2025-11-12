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
    RecyclerView rv;
    Adaptador1 ad1;
    Toolbar tb;
    Menu menuPrincipal;
    ImageButton btn;
    TextView tv;
    boolean iconChanged = false;


    RecyclerView.LayoutManager manager1;
    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new
            ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK) {
                Intent intent = result.getData();
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
        ad1 = new Adaptador1(peliculas, tv);
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
            startActivity(intent);
            return true;
        } else if (id == R.id.favoritos) {

            return true;
        } else if (id == R.id.añadirPelicula) {

            return true;
        } else if (id == R.id.mostrarColumnas) {

            if (!iconChanged) {
                item.setIcon(R.drawable.img_1);
                item.setTitle("Mostrar en 1 columna");
                iconChanged = true;
                rv.setLayoutManager(new GridLayoutManager(this,2));
            } else {
                item.setIcon(R.drawable.img);
                rv.setLayoutManager(manager1);
                item.setTitle("Mostrar en 2 columnas");
                iconChanged = false;
            }
            return true;
        } else if (id == R.id.soloFavoritos) {

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}