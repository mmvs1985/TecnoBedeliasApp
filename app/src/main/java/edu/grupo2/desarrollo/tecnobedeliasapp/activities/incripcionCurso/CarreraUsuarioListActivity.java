package edu.grupo2.desarrollo.tecnobedeliasapp.activities.incripcionCurso;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import edu.grupo2.desarrollo.tecnobedeliasapp.ConfigSingletton;
import edu.grupo2.desarrollo.tecnobedeliasapp.R;
import edu.grupo2.desarrollo.tecnobedeliasapp.activities.listarCarreras.CarreraDetailFragment;
import edu.grupo2.desarrollo.tecnobedeliasapp.modelos.Carrera;
import edu.grupo2.desarrollo.tecnobedeliasapp.modelos.Curso;

/**
 * An activity representing a list of CarrerasUsuario. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a  representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class CarreraUsuarioListActivity extends AppCompatActivity {

    CarreraUsuarioListActivity.SimpleItemRecyclerViewAdapter adaptador;
    private ArrayList<Carrera> listacarrera;
    private ArrayList<Curso> listacurso;

    private String seleccion;
    private static final String ETIQUETA = "carreralista";

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    //private boolean mTwoPane;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrerausuario_list);
        seleccion = getIntent().getStringExtra("eleccion");



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());


        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

       /* if (findViewById(R.id.carrerausuario_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }*/

        View recyclerView = findViewById(R.id.carrerausuario_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        listacarrera=new ArrayList<>();
        obtenerCarrerasDelUsuario();
        adaptador= new SimpleItemRecyclerViewAdapter(this, listacarrera,seleccion);
        recyclerView.setAdapter(adaptador);

        //con esto pongo la linea divisoria entre los items
        LinearLayoutManager lmgr=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(lmgr);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,lmgr.getOrientation()));
    }


    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final CarreraUsuarioListActivity mParentActivity;
        private final ArrayList<Carrera> mValues;
        private final String seleccion;
       // private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Carrera item = (Carrera) view.getTag();
                Log.e(ETIQUETA,"DATOS DE LA CARRERA: "+item.getId()+item.getCreditosMinimos()+item.getDescripcion()+item.getNombre());
                /*if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putSerializable(CarreraDetailFragment.ARG_ITEM_ID, item);
                    CarreraUsuarioDetailFragment fragment = new CarreraUsuarioDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.carrerausuario_detail_container, fragment)
                            .commit();
                } else {*/

                    Context context = view.getContext();
                    Intent intent = new Intent(context, AsignaturaCarreraListActivity.class);
                    intent.putExtra("eleccion",seleccion);
                    intent.putExtra(CarreraDetailFragment.ARG_ITEM_ID, item);

                    context.startActivity(intent);
                //}
            }
        };

        SimpleItemRecyclerViewAdapter(CarreraUsuarioListActivity parent,
                                      ArrayList<Carrera> items, String seleccion) {
            mValues = items;
            mParentActivity = parent;
            this.seleccion=seleccion;
           // mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.carrerausuario_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mIdView.setText(mValues.get(position).getNombre());
            holder.mContentView.setText(String.valueOf(mValues.get(position).getCreditosMinimos()));

            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
            final TextView mContentView;

            ViewHolder(View view) {
                super(view);
                mIdView = (TextView) view.findViewById(R.id.id_text);
                mContentView = (TextView) view.findViewById(R.id.content);
            }
        }
    }
    private void obtenerCarrerasDelUsuario() {

        listacarrera= (ArrayList<Carrera>) ConfigSingletton.getInstance().getUsuarioLogueado().getCarreras();


    }
}
