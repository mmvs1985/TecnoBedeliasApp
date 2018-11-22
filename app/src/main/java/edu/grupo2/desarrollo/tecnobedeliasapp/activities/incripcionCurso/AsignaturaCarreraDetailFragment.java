package edu.grupo2.desarrollo.tecnobedeliasapp.activities.incripcionCurso;

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
import edu.grupo2.desarrollo.tecnobedeliasapp.activities.listarCarreras.CarreraDetailFragment;
import edu.grupo2.desarrollo.tecnobedeliasapp.modelos.Curso;

/**
 * A fragment representing a single AsignaturaCarrera detail screen.
 * This fragment is either contained in a {@link AsignaturaCarreraListActivity}
 * in two-pane mode (on tablets) or a {@link AsignaturaCarreraDetailActivity}
 * on handsets.
 */
public class AsignaturaCarreraDetailFragment extends Fragment {
    private static final String ETIQUETA ="AsigCarrDetailFrag" ;
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
   // public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private Curso mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AsignaturaCarreraDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(CarreraDetailFragment.ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = (Curso) getArguments().getSerializable(CarreraDetailFragment.ARG_ITEM_ID);
           // Curso asign=(Asignatura)getArguments().getSerializable("asignatura");


            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.getNombreAsignatura());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.asignaturacarrera_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
/*
            ArrayList<Curso>cursos= (ArrayList<Curso>) mItem.getCursos();
            Curso cursodisponible=null;
            for (Curso c:cursos) {
               Log.e(ETIQUETA, c.getFechaFin());
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date fechafin=sdf.parse(c.getFechaFin());

                    Log.e(ETIQUETA,"fecha formateada: "+fechafin.toString());
                    Date hoy= new Date();
                    Log.e(ETIQUETA,"la fecha formateada es despues de hoy? : "+fechafin.after(hoy));
                    if(fechafin.after(hoy)){
                        cursodisponible=c;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
*/

            ///cursos. if (c.getFechaFin())


                String texto="id: "+mItem.getId()+"\nAño: "+mItem.getAnio()+"\nSemestre: "+mItem.getSemestre()+"\nInicia: "+formatofecha(mItem.getFechaInicio())+"\nFinaliza: "+formatofecha(mItem.getFechaFin());
                ((TextView) rootView.findViewById(R.id.asignaturacarrera_detail)).setText(texto);


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
