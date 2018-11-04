package edu.grupo2.desarrollo.tecnobedeliasapp.Fragments;

import android.support.annotation.NonNull;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.grupo2.desarrollo.tecnobedeliasapp.R;
import edu.grupo2.desarrollo.tecnobedeliasapp.modelos.Carrera;

public class CarrerasAdapter extends RecyclerView.Adapter<CarrerasAdapter.ViewholderCarreras> {

    public List<Carrera> listacarrera;

    public CarrerasAdapter(List<Carrera> listacarrera) {
        this.listacarrera = listacarrera;
    }

    @NonNull
    @Override
    public CarrerasAdapter.ViewholderCarreras onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_carrera,null,false);
        return new ViewholderCarreras(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull CarrerasAdapter.ViewholderCarreras holder, int position) {
        Log.e("List Part adp","pos: "+position+" de "+listacarrera.get(position).getNombre());
        holder.nombre.setText(listacarrera.get(position).getNombre());
        holder.creditos.setText(listacarrera.get(position).getCreditosMinimos().toString());
        //holder.descripcion.setText(listacarrera.get(position).getDescripcion());

    }

    @Override
    public int getItemCount() {
        return listacarrera.size();
    }

    public static class ViewholderCarreras extends RecyclerView.ViewHolder {
        TextView nombre,descripcion,creditos;


        public ViewholderCarreras(View itemView) {
            super(itemView);
            nombre=(TextView) itemView.findViewById(R.id.nombCarreratv);
            //descripcion=(TextView) itemView.findViewById(R.id.descripcionCarreratv);
            creditos=(TextView) itemView.findViewById(R.id.creditost);
        }
    }
}