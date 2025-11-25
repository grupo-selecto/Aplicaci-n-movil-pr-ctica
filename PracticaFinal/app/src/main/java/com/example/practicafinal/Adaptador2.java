package com.example.practicafinal;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adaptador2 extends RecyclerView.Adapter<Adaptador2.MyViewHolder> {

    public ArrayList<Pelicula> peliculas;
    public ArrayList<Pelicula> favoritos;

    int selectedPos = RecyclerView.NO_POSITION;

    ActivityResultLauncher<Intent> launcher;

    ListadoCompleto actividad;

    public int getSelectedPos() {
        return selectedPos;
    }

    public void setSelectedPos(int nuevaPos) {
        if (nuevaPos == this.selectedPos) {
            this.selectedPos = RecyclerView.NO_POSITION;
            notifyItemChanged(nuevaPos);
        } else {
            if (this.selectedPos >= 0) {
                notifyItemChanged(this.selectedPos);
            }
            this.selectedPos = nuevaPos;
            notifyItemChanged(nuevaPos);
        }
    }

    public Adaptador2(ArrayList<Pelicula> peliculas,ArrayList<Pelicula> favoritos, ListadoCompleto actividad) {
        this.peliculas = peliculas;
        this.favoritos = favoritos;
        this.actividad=actividad;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.celda2, parent, false);
        return new Adaptador2.MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Pelicula p =peliculas.get(position);
        holder.getPortada().setImageResource(p.getPortada());
        holder.getDirector().setText(p.getDirector());
        holder.getEstreno().setText(p.getFecha().toString());
        holder.getSala().setText(p.getSala());
        holder.getClasi().setImageResource(p.getClasi());
        holder.getDuracion().setText(p.getDuracion() + " min");
        if (favoritos.contains(p)){
            holder.btnFav.setImageResource(R.drawable.star);
        }else{
            holder.btnFav.setImageResource(R.drawable.graystar);
        }
    }

    @Override
    public int getItemCount() {
        return peliculas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView portada, clasi;
        private TextView director, estreno, duracion, sala;
        private ImageButton btnFav;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.portada = itemView.findViewById(R.id.portada);
            this.clasi = itemView.findViewById((R.id.clasi));
            this.director = itemView.findViewById(R.id.director);
            this.estreno = itemView.findViewById((R.id.estreno));
            this.duracion = itemView.findViewById(R.id.duracion);
            this.sala = itemView.findViewById(R.id.sala);
            this.btnFav=itemView.findViewById(R.id.btnFav);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAbsoluteAdapterPosition();
                    setSelectedPos(pos);
                    Intent intent = new Intent(actividad, ListadoCompleto2.class);
                    intent.putExtra("peli",peliculas.get(pos));
                    actividad.launcher.launch(intent);
                }
            });

            btnFav.setOnClickListener(v -> {
                int pos = getAbsoluteAdapterPosition();
                if (pos>=0){
                    Pelicula p = peliculas.get(pos);
                    if (favoritos.contains(p)){
                        favoritos.remove(p);
                        btnFav.setImageResource(R.drawable.graystar);
                    }else{
                        favoritos.add(p);
                        btnFav.setImageResource(R.drawable.star);
                    }
                    notifyItemChanged(pos);
                }
            });
        }

        public TextView getDirector() {
            return director;
        }

        public ImageView getPortada() {
            return portada;
        }

        public ImageView getClasi() {
            return clasi;
        }

        public TextView getDuracion() {
            return duracion;
        }

        public TextView getEstreno() {
            return estreno;
        }

        public TextView getSala() {
            return sala;
        }
    }

}
