package com.example.practicafinal;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ListadoCompleto2 extends AppCompatActivity {

    ImageView imv;
    TextView tv;
    Toolbar tb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main3);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        imv=findViewById(R.id.imgvPortadaGrande);
        tv=findViewById(R.id.tvSinopsis);
        tb=findViewById(R.id.toolbar);
        Pelicula p = (Pelicula) getIntent().getSerializableExtra("peli");
        tb.setTitle(p.getTitulo());
        imv.setImageResource(p.getPortada());
        tv.setText(p.getSinopsis());




    }
}