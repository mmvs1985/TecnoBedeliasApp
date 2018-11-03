package edu.grupo2.desarrollo.tecnobedeliasapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.grupo2.desarrollo.tecnobedeliasapp.CarreraFragment.OnListFragmentInteractionListener;

import edu.grupo2.desarrollo.tecnobedeliasapp.modelos.Carrera;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link } and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class CarreraRecyclerViewAdapter extends RecyclerView.Adapter<CarreraRecyclerViewAdapter.ViewHolder> {

    private final List<Carrera> mValues;
    private final OnListFragmentInteractionListener mListener;

    public CarreraRecyclerViewAdapter(List<Carrera> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_carrera, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItemCarrera = mValues.get(position);
        holder.mnombrecarrera.setText(mValues.get(position).getNombre());
        holder.mdescripcion.setText(mValues.get(position).getDescripcion());
        holder.mcreditos.setText(mValues.get(position).getCreditosMinimos());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.

                    //LO QUE YO LE PASE ACA COMO PARAMETRO VIAJA AL LISTENER DEL FRAGMENT
                    mListener.onListFragmentInteraction(holder.mItemCarrera, holder.mView.isSelected());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mnombrecarrera;
        public final TextView mdescripcion;
        public final TextView mcreditos;
        public Carrera mItemCarrera;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mnombrecarrera = (TextView) view.findViewById(R.id.nombCarreratxt);
            mdescripcion = (TextView) view.findViewById(R.id.descripcionCarreratxt);
            mcreditos = (TextView) view.findViewById(R.id.creditostxt);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mnombrecarrera.getText() + "'";
        }
    }
}
