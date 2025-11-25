package com.example.practicafinal;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adaptador1 extends RecyclerView.Adapter<Adaptador1.MyViewHolder> {
    ArrayList<Pelicula> peliculas;
    ArrayList<Pelicula> favoritos;
    TextView tv;
    Boolean normal;
    int selectedPos = RecyclerView.NO_POSITION;
    boolean fav = false;

    public Adaptador1(ArrayList<Pelicula> peliculas, ArrayList<Pelicula> favoritos, TextView tv, Boolean normal) {
        this.peliculas = peliculas;
        this.favoritos = favoritos;
        this.tv = tv;
        this.normal = normal;
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
        if (p.getPortada() != 0) {
            holder.getPortada().setImageResource(p.getPortada());
        } else {
            holder.getPortada().setImageResource(R.drawable.portada_default);
        }
        switch (p.getClasi()) {

            case 1:
                holder.getClasificacion().setImageResource(R.drawable.g);
                break;
            case 2:
                holder.getClasificacion().setImageResource(R.drawable.nc17);
                break;
            case 3:
                holder.getClasificacion().setImageResource(R.drawable.pg);
                break;
            case 4:
                holder.getClasificacion().setImageResource(R.drawable.pg13);
                break;
            default:
                holder.getClasificacion().setImageResource(p.getClasi());
                break;
        }
        if (normal) {
            holder.btnFav.setVisibility(View.VISIBLE);
        } else {
            holder.btnFav.setVisibility(View.GONE);
        }
        if (favoritos.contains(p)) {
            holder.btnFav.setImageResource(R.drawable.star);
        } else {
            holder.btnFav.setImageResource(R.drawable.graystar);
        }
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

    public void setPeliculas(ArrayList<Pelicula> favoritos) {
        this.peliculas = favoritos;
    }

    public void setNormal(boolean normal) {
        this.normal = normal;
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
        private ImageButton btnFav;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.titulo = itemView.findViewById((R.id.tvTitulo));
            this.director = itemView.findViewById(R.id.tvDirector);
            this.portada = itemView.findViewById(R.id.imgvPortada);
            this.clasificacion = itemView.findViewById(R.id.imgvclasificacion);
            this.btnFav = itemView.findViewById(R.id.imgFav);
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
            btnFav.setOnClickListener(v -> {
                int pos = getAbsoluteAdapterPosition();
                if (!(pos == RecyclerView.NO_POSITION)) {
                    Pelicula peli = peliculas.get(pos);
                    if (favoritos.contains(peli)) {
                        favoritos.remove(peli);
                        btnFav.setImageResource(R.drawable.graystar);
                    } else {
                        favoritos.add(peli);
                        btnFav.setImageResource(R.drawable.star);
                    }
                    notifyItemChanged(pos);

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
