package edu.grupo2.desarrollo.tecnobedeliasapp.activities.listarCarreras;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.grupo2.desarrollo.tecnobedeliasapp.R;
import edu.grupo2.desarrollo.tecnobedeliasapp.modelos.Carrera;

/**
 * A fragment representing a single Carrera detail screen.
 * This fragment is either contained in a {@link CarreraListActivity}
 * in two-pane mode (on tablets) or a {@link CarreraDetailActivity}
 * on handsets.
 */
public class CarreraDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item";
    private static final String ETIQUETA ="CarreraDetailFragment" ;

    /**
     * The dummy content this fragment is presenting.
     */
    private Carrera mItem;
    private boolean esdelusuario;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CarreraDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = (Carrera) getArguments().getSerializable(ARG_ITEM_ID);
            esdelusuario = (Boolean)getArguments().getBoolean("estaanotado");
            if (esdelusuario)
                Log.e(ETIQUETA,"el usuario esta anotado a esta carrera");

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.getNombre());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.carrera_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.carrera_detail)).setText(mItem.getDescripcion());

        }

        return rootView;
    }
}
