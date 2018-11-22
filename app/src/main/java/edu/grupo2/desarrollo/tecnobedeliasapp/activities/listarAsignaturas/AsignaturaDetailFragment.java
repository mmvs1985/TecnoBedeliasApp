package edu.grupo2.desarrollo.tecnobedeliasapp.activities.listarAsignaturas;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.grupo2.desarrollo.tecnobedeliasapp.R;
import edu.grupo2.desarrollo.tecnobedeliasapp.modelos.Asignatura;

/**
 * A fragment representing a single Asignatura detail screen.
 * This fragment is either contained in a {@link AsignaturaListActivity}
 * in two-pane mode (on tablets) or a {@link AsignaturaDetailActivity}
 * on handsets.
 */
public class AsignaturaDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item";
    private static final String ETIQUETA ="AsignaturaDetailFragment" ;

    /**
     * The dummy content this fragment is presenting.
     */
    private Asignatura mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AsignaturaDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = (Asignatura)getArguments().getSerializable(ARG_ITEM_ID);

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
        View rootView = inflater.inflate(R.layout.asignatura_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.asignatura_detail)).setText(mItem.getDescripcion());
        }

        return rootView;
    }
}
