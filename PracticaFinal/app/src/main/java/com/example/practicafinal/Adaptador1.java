package com.example.practicafinal;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Adaptador1 extends RecyclerView.Adapter<Adaptador1.MyViewHolder> {
    ArrayList<Pelicula> peliculas;
    TextView tv;
    int selectedPos = RecyclerView.NO_POSITION;

    public Adaptador1(ArrayList<Pelicula> peliculas, TextView tv) {
        this.peliculas = peliculas;
        this.tv = tv;
    }

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

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.celda, parent, false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Pelicula p = this.peliculas.get(position);
        holder.getDirector().setText(p.getDirector());
        holder.getTitulo().setText(p.getTitulo());
        holder.getPortada().setImageResource(p.getPortada());
        holder.getClasificacion().setImageResource(p.getClasi());
        if (selectedPos == position) {
            holder.itemView.setBackgroundResource(R.color.seleccionado);
        } else {
            holder.itemView.setBackgroundResource(R.color.white);

        }
    }

    @Override
    public int getItemCount() {
        return peliculas.size();
    }

//    public interface OnItemSelectedListener {
//        void onItemSelected(int position);
//    }
//
//    private OnItemSelectedListener listener;
//
//    public void setOnItemSelectedListener(OnItemSelectedListener listener) {
//        this.listener = listener;
//    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView titulo, director;
        private ImageView portada, clasificacion;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.titulo = itemView.findViewById((R.id.tvTitulo));
            this.director = itemView.findViewById(R.id.tvDirector);
            this.portada = itemView.findViewById(R.id.imgvPortada);
            this.clasificacion = itemView.findViewById(R.id.imgvclasificacion);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAbsoluteAdapterPosition();
                    setSelectedPos(pos);
                    if (getSelectedPos() >= 0) {
                        tv.setText(peliculas.get(getSelectedPos()).getTitulo());
                    } else {
                        tv.setText("Peliculas");
                    }
//                    if (listener != null) {
//                        listener.onItemSelected(selectedPos); // avisamos al MainActivity
//                    }

                }
            });
        }

        public TextView getTitulo() {
            return titulo;
        }

        public TextView getDirector() {
            return director;
        }

        public ImageView getPortada() {
            return portada;
        }

        public ImageView getClasificacion() {
            return clasificacion;
        }
    }
}
