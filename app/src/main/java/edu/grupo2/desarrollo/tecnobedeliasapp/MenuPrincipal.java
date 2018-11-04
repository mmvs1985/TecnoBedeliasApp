package edu.grupo2.desarrollo.tecnobedeliasapp;

//import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

import edu.grupo2.desarrollo.tecnobedeliasapp.Fragments.CarrerasFragment;
import edu.grupo2.desarrollo.tecnobedeliasapp.activities.CarreraListActivity;
import edu.grupo2.desarrollo.tecnobedeliasapp.activities.Perfil;
import edu.grupo2.desarrollo.tecnobedeliasapp.dbSQLite.Constantes;
import edu.grupo2.desarrollo.tecnobedeliasapp.modelos.Carrera;
import edu.grupo2.desarrollo.tecnobedeliasapp.modelos.Usuario;
import edu.grupo2.desarrollo.tecnobedeliasapp.noticias.MiServiciodeMensajeriaFirebase;

public class MenuPrincipal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, CarrerasFragment.OnFragmentInteractionListener {

    private static final String ETIQUETA = "Menu principal" ;
    private Usuario usrlogeado;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        usrlogeado=ConfigSingletton.getInstance().consultaUsuarioLogueado(getApplicationContext());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        TextView headerName=navigationView.getHeaderView(0).findViewById(R.id.tvnavheadernombre);
        TextView headerMail=navigationView.getHeaderView(0).findViewById(R.id.tvnavheadermail);
        /*try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        headerName.setText(usrlogeado.getNombre());
       /* List<Carrera> listacarrera=ConfigSingletton.getInstance().getUsuarioLogueado().getCarreras();
        for (Carrera c:listacarrera) {
            Log.e(ETIQUETA,"CARRERA NOMBRE: "+c.getNombre());
            
        }*/

        headerMail.setText(usrlogeado.getEmail());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        boolean itemselected=false;
        Fragment seleccionado=null;

        if (id == R.id.nav_carrera) {
            if(isOnline()){
            Intent in= new Intent(MenuPrincipal.this,CarreraListActivity.class);
            startActivity(in);}
            else {
                Toast.makeText(getApplicationContext(),"No hay red",Toast.LENGTH_LONG).show();
            }
            // Handle the camera action
        } else if (id == R.id.nav_asignatura) {
            Intent in= new Intent(MenuPrincipal.this,NoticiaListActivity.class);
            startActivity(in);

        } else if (id == R.id.nav_previa) {
            Intent in2= new Intent(MenuPrincipal.this,MainActivity.class);
            startActivity(in2);
        } else if (id == R.id.nav_insc_cur) {

        } else if (id == R.id.nav_insc_exm) {

        } else if (id == R.id.nav_perfil) {
            if(ConfigSingletton.getInstance().consultaUsuarioLogueado(getApplicationContext())==null){
                Toast.makeText(getApplicationContext(),"no se obtubo el usuario",Toast.LENGTH_LONG).show();

            }else {
                Intent in5= new Intent(MenuPrincipal.this,Perfil.class);
                startActivity(in5);
            }


        } else if (id == R.id.nav_logout) {
            desloguear();
            Intent in6= new Intent(MenuPrincipal.this,LoginActivity.class);
            startActivity(in6);

        }
        if(itemselected){
            getSupportFragmentManager().beginTransaction().replace(R.id.noticia_list,seleccionado).commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void desloguear(){
        Usuario nologueado=new Usuario();
        ConfigSingletton.getInstance().setUsuarioLogueado(nologueado);
        Log.e(ETIQUETA,"me estoy deslogueando");

        SharedPreferences sp=getSharedPreferences("usuarioLogueado",MODE_PRIVATE);
        sp.edit().clear().apply();
    }
}
