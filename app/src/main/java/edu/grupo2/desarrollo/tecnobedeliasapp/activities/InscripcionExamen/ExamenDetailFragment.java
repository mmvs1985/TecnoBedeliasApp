package edu.grupo2.desarrollo.tecnobedeliasapp.activities.InscripcionExamen;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.grupo2.desarrollo.tecnobedeliasapp.R;
import edu.grupo2.desarrollo.tecnobedeliasapp.modelos.Examen;

/**
 * A fragment representing a single Examen detail screen.
 * This fragment is either contained in a {@link ExamenListActivity}
 * in two-pane mode (on tablets) or a {@link ExamenDetailActivity}
 * on handsets.
 */
public class ExamenDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
    private static final String ETIQUETA ="ExamenDetailFragment" ;

    /**
     * The dummy content this fragment is presenting.
     */
    private Examen mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ExamenDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = (Examen)getArguments().getSerializable(ARG_ITEM_ID);

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.nombreAsignatura);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.examen_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.examen_detail)).setText(formatofecha(mItem.fecha));
        }

        return rootView;
    }

    private  String formatofecha(String fsinformato){

        Date fechaformateada= new Date();
        //el que lee
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        //el que le da el formato
        SimpleDateFormat formateador=new SimpleDateFormat("dd-MM-yyyy");
        String retorno="";

        try {
            //fechaformateada=sdf.parse(fsinformato);
            fechaformateada=sdf.parse(fsinformato);
            retorno= formateador.format(fechaformateada);

            Log.e(ETIQUETA,"fecha formateada: "+fechaformateada.toString());
            // Date hoy= new Date();
        } catch (ParseException e) {
            e.printStackTrace();
        }




        return retorno;//fechaformateada.toString();
    }
}

