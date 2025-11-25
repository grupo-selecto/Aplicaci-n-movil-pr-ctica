package com.example.practicafinal;

import android.content.Intent;
import android.os.Bundle;
import android.util.AndroidException;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.time.Duration;
import java.util.Date;

public class NuevaPelicula extends AppCompatActivity {

    String[] salas = {"Sala 1", "Sala 2", "Sala 3", "Sala 4", "Sala 5"};

    Spinner sp;
    EditText etTitulo, etDirector, etDuracion, etDate;
    Button btnGuardar;
    RadioButton rb1, rb2, rb3, rb4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_nueva_pelicula);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etTitulo = findViewById(R.id.txtTitulo);
        etDirector = findViewById(R.id.txtDirector);
        etDuracion = findViewById(R.id.txtDuracion);
        etDate = findViewById(R.id.etDate);
        rb1 = findViewById(R.id.rb1);
        rb2 = findViewById(R.id.rb2);
        rb3 = findViewById(R.id.rb3);
        rb4 = findViewById(R.id.rb4);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, salas);
        sp = findViewById(R.id.spinner);
        sp.setAdapter(adapter);

        btnGuardar = findViewById(R.id.btnGuardar);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titulo = etTitulo.getText().toString().trim();
                String director = etDirector.getText().toString().trim();
                String duracionStr = etDuracion.getText().toString().trim();
                String fechaStr = etDate.getText().toString().trim();
                String sala = sp.getSelectedItem().toString();

                if (titulo.isEmpty()) titulo = "Título por defecto";
                if (director.isEmpty()) director = "Director por defecto";
                int duracion;
                try {
                    duracion = Integer.parseInt(duracionStr);
                } catch (NumberFormatException e) {
                    duracion = 120;
                }
                Date fecha;
                try {
                    fecha = new java.text.SimpleDateFormat("dd/MM/yyyy").parse(fechaStr);
                } catch (java.text.ParseException e) {
                    fecha = new Date(); // Fecha por defecto = hoy
                }
                int clasi = 0;
                if (rb1.isChecked()) clasi = 1;
                else if (rb2.isChecked()) clasi = 2;
                else if (rb3.isChecked()) clasi = 3;
                else if (rb4.isChecked()) clasi = 4;
                if (clasi == 0) clasi = 1;
                int portada = 0;

                Pelicula p = new Pelicula(titulo, director, duracion, fecha, sala, clasi, portada);
                Intent data = new Intent();
                data.putExtra("peli", p);
                setResult(RESULT_OK, data);
                Toast.makeText(getApplicationContext(), "Se ha guardado la pelicula", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_volver, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}