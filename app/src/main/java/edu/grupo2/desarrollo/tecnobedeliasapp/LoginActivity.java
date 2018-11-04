package edu.grupo2.desarrollo.tecnobedeliasapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import edu.grupo2.desarrollo.tecnobedeliasapp.api.ApiServiceInterface;
import edu.grupo2.desarrollo.tecnobedeliasapp.dbSQLite.SQLiteHelper;
import edu.grupo2.desarrollo.tecnobedeliasapp.modelos.AsignaturaCarrera;
import edu.grupo2.desarrollo.tecnobedeliasapp.modelos.Carrera;
import edu.grupo2.desarrollo.tecnobedeliasapp.modelos.Curso;
import edu.grupo2.desarrollo.tecnobedeliasapp.modelos.RespuestaApiLogin;
import edu.grupo2.desarrollo.tecnobedeliasapp.modelos.Usuario;
import edu.grupo2.desarrollo.tecnobedeliasapp.dbSQLite.Constantes;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;
    final String  ETIQUETA="intentoLogin";
    private Usuario logueado;
    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private SQLiteHelper connectordb;
    //tokenloguedao usuario
    //Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJlc3R1ZGlhbnRlIiwiZXhwIjoxNTQxNjM1Mzk5LCJyb2xlcyI6W3siYXV0aG9yaXR5IjoiUk9MRV9FU1RVRElBTlRFIn1dfQ.h-MTuLus3z0kyi0L6y91Yr66U71Gs6I0aRBuNx_lN4SJneAgwmlKfB3X-tmA614EDcuHG6dfc4q47dyerwr9jA
    //director
    //Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkaXJlIiwiZXhwIjoxNTQxNjM3NzMxLCJyb2xlcyI6W3siYXV0aG9yaXR5IjoiUk9MRV9ESVJFQ1RPUiJ9XX0.kjLXmOtgOJd1U9es0_5rTEwQwSYevSqh_YdyPEon38nYgeAuh1PfPwsWZkU-1Pm9J3NPHpy3TmweuhybbwAsfw
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        ConfigSingletton sing=ConfigSingletton.getInstance();
        if (getSharedPreferences("usuarioLogueado",MODE_PRIVATE).contains(Constantes.CAMPOUSERNAME))
            {
                String token=getSharedPreferences("usuarioLogueado",MODE_PRIVATE).getString("APIAUTH",null);
                String username=getSharedPreferences("usuarioLogueado",MODE_PRIVATE).getString(Constantes.CAMPOUSERNAME,null);
                Log.e(ETIQUETA,"SETEO EL USUARIO: "+username+" con token: "+token);
                sing.setUsernameUsuarioLogueado(username);
                sing.setTokenUsuarioLogueado(token);

                traerUsuarioLogueado(sing.getRetro(),sing.getRetro().create(edu.grupo2.desarrollo.tecnobedeliasapp.api.ApiServiceInterface.class));

                Intent in=new Intent(LoginActivity.this,MenuPrincipal.class);
                startActivity(in);
            }
            else{

        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        populateAutoComplete();
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        }
    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            //showProgress(true);
            if(isOnline()) {
                Log.e(ETIQUETA, "Esta online intento");
                intentoLogin(email, password);
            }
            else{
                Toast.makeText(getApplicationContext(), "sin red disponible", Toast.LENGTH_SHORT).show();
                Log.e(ETIQUETA, "no hay red");
            }
            /*mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);*/
        }
    }

    private void intentoLogin(String username, String pass) {
        Log.e(ETIQUETA, "intento logear con usr y cpass: "+username+" "+pass );

        final Retrofit retro = ConfigSingletton.getInstance().getRetro();
        //armo los datos de usuario para mandar
        JsonObject datosjson= new JsonObject();
        datosjson.addProperty("username",username);
        datosjson.addProperty("password",pass);
        //creo y llamo a la api
        final ApiServiceInterface interfaz= retro.create(edu.grupo2.desarrollo.tecnobedeliasapp.api.ApiServiceInterface.class);
        Log.e(ETIQUETA, "intento logear con este json: "+datosjson.toString());
        Call<RespuestaApiLogin> respuestacall=interfaz.obtenerrespuesta(datosjson);
        //al ponerse enqueue se realiza asyncronicamente.
        respuestacall.enqueue(new Callback<RespuestaApiLogin>() {
            @Override
            public void onResponse(Call<RespuestaApiLogin> call, Response<RespuestaApiLogin> response) {
                if (response.isSuccessful()){
                    RespuestaApiLogin r=response.body();
                    boolean resultado=r.getAutorizado();
                    Log.e(ETIQUETA, "en respuesta RESULTADOfin: " + resultado);
                    if (resultado){
                        ConfigSingletton.getInstance().setUsernameUsuarioLogueado(response.body().getUsername());
                        ConfigSingletton.getInstance().setTokenUsuarioLogueado(response.headers().get("Authorization"));
                        showProgress(false);

                        Log.e(ETIQUETA, "en respuesta entro: " + resultado);

                        Log.e(ETIQUETA, "en respuesta nomb usr res: " + response.body().getUsername());
                        Log.e(ETIQUETA, "en respuesta token res: " + response.headers().get("Authorization"));
                        traerUsuarioLogueado(retro,interfaz);

                        if(logueado== null){
                            Log.e(ETIQUETA, "sige siendo null: " );;
                        }

                    }
                    else{
                        showProgress(false);
                        View focusView = null;
                        mPasswordView.setError(getString(R.string.error_incorrect_password));
                        focusView = mPasswordView;
                        focusView.requestFocus();
                        Log.e(ETIQUETA, "en respuesta nomb usr res: " + response.body().getUsername());;
                    }

                }
                else {
                    if (response.code()==401){
                        Toast.makeText(getApplicationContext(), "usuario o contraseña erronea", Toast.LENGTH_SHORT).show();
                    Log.e(ETIQUETA, "en respuesta error: " + response.errorBody());

                    }

                }
            }

            @Override
            public void onFailure(Call<RespuestaApiLogin> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "error de comunicacion", Toast.LENGTH_SHORT).show();
                showProgress(false);
                Log.e(ETIQUETA, "en falla:" + t.getMessage());
            }
        });

    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                finish();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

    /*CONTROLA SI ESTÁ ONLINE O NO */

    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

    public void traerUsuarioLogueado(Retrofit retro,ApiServiceInterface interfaz) {
        // Log.e(ETIQUETA, "traer el usuario: "+usernameUsuarioLogueado +" token :"+getTokenUsuarioLogueado());
        /*Retrofit retro=ConfigSingletton.getInstance().getRetro();
        ApiServiceInterface interfaz= retro.create(edu.grupo2.desarrollo.tecnobedeliasapp.api.ApiServiceInterface.class);*/
        Call<Usuario> respuestacall=interfaz.obtenerUsuarioLogueado(ConfigSingletton.getInstance().getTokenUsuarioLogueado());
        //final Usuario[] logeado = {null};

/*
        {
        try {
            usuarioLogueado = respuestacall.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        }
        setUsuarioLogueado(logeado);*/
        //al ponerse enqueue se realiza asyncronicamente.
        respuestacall.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()){
                    connectordb= new SQLiteHelper(getApplicationContext(), Constantes.NOMBREBD,null,1);

                    Usuario r=response.body();
                    Log.e(ETIQUETA, "en respuesta RESULTADOfin: " + r.getEmail());
                    ConfigSingletton.getInstance().setUsuarioLogueado(r);
                    //r=ConfigSingletton.getInstance().getUsuarioLogueado();
                    logueado =r;
                    Log.e(ETIQUETA, "y el apellido es..: " + logueado.getApellido());
                    /*if(persisitirUsuario(r))
                        Toast.makeText(getApplicationContext(),"se guardo correctamente: ",Toast.LENGTH_LONG).show();*/
                    loguearUsuario(r);


                    Intent in=new Intent(LoginActivity.this,MenuPrincipal.class);
                    startActivity(in);

                }
                else {
                    if (response.code()==401){
                        //Toast.makeText(, "usuario o contraseña erronea", Toast.LENGTH_SHORT).show();
                        Log.e(ETIQUETA, "en respuesta error: " + response.errorBody());

                    }

                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                //Toast.makeText(getApplicationContext(), "no se pudo obtener el usuario", Toast.LENGTH_SHORT).show();
                Log.e(ETIQUETA, "en falla:" + t.getMessage());
            }
        });
        //return r;

    }

    private void loguearUsuario(Usuario usr){
        SharedPreferences sp=getSharedPreferences("usuarioLogueado",MODE_PRIVATE);
        SharedPreferences.Editor spEditor = sp.edit();
        spEditor.putInt(Constantes.CAMPOID,usr.getId());
        spEditor.putString(Constantes.CAMPOAPELLIDO,usr.getApellido());
        spEditor.putString(Constantes.CAMPOAPPTOKEN,usr.getAppToken());
        spEditor.putString(Constantes.CAMPOCEDULA,usr.getCedula());
        spEditor.putString(Constantes.CAMPOEMAIL,usr.getEmail());
        spEditor.putString(Constantes.CAMPONOMBRE,usr.getNombre());
        spEditor.putString(Constantes.CAMPOFNAC,usr.getFechaNacimiento());
        spEditor.putString(Constantes.CAMPOPASSWORD,usr.getPassword());
        spEditor.putString(Constantes.CAMPORESETTOKEN,usr.getResetToken());
        spEditor.putString(Constantes.CAMPOUSERNAME,usr.getUsername());
        spEditor.putString(Constantes.CAMPOFOTO,usr.getFoto());
        spEditor.putString("APIAUTH",ConfigSingletton.getInstance().getTokenUsuarioLogueado());


        spEditor.commit();
        ArrayList<Carrera> carrerasUsr= (ArrayList<Carrera>)usr.getCarreras();//.get(0).getAsignaturaCarrera().get(1).getAsignatura().getCursos().get(0).getSemestre().toString();
        ArrayList<AsignaturaCarrera> asigcarrerasUsr;
        ArrayList<Curso> cursosUsr;
        String topico;
        for(Carrera c: carrerasUsr ){
            asigcarrerasUsr=(ArrayList<AsignaturaCarrera>)c.getAsignaturaCarrera();
            for(AsignaturaCarrera ac: asigcarrerasUsr ){
                cursosUsr=(ArrayList<Curso>)ac.getAsignatura().getCursos();
                for (Curso cu: cursosUsr){
                    topico=ac.getAsignatura().getNombre().replace(" ","_") +"-"+String.valueOf(cu.getSemestre()+"-"+String.valueOf(cu.getAnio()));
                    FirebaseMessaging.getInstance().subscribeToTopic(topico);
                    Log.d(ETIQUETA,"me inscribi al topico: "+topico);
                    //asignatura_semestre_año
                }
            }
        }
    }

/*
    private boolean persisitirUsuario(Usuario usr) {

        SQLiteDatabase db= connectordb.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Constantes.CAMPOID,usr.getId());
        cv.put(Constantes.CAMPOAPELLIDO,usr.getApellido());
        cv.put(Constantes.CAMPOAPPTOKEN,usr.getAppToken());
        cv.put(Constantes.CAMPOCEDULA,usr.getCedula());
        cv.put(Constantes.CAMPOEMAIL,usr.getEmail());
        cv.put(Constantes.CAMPONOMBRE,usr.getNombre());
        cv.put(Constantes.CAMPOFNAC,usr.getFechaNacimiento());
        cv.put(Constantes.CAMPOPASSWORD,usr.getPassword());
        cv.put(Constantes.CAMPORESETTOKEN,usr.getResetToken());
        cv.put(Constantes.CAMPOUSERNAME,usr.getUsername());
        long idResultante=db.insert(Constantes.TABLAUSUARIOS,Constantes.CAMPOID,cv);

        Toast.makeText(getApplicationContext(),"se inserto con el id: "+idResultante,Toast.LENGTH_LONG).show();
        db.close();
        return (idResultante!=-1);
    }*//*

    private boolean consultarUsuario(){


        SQLiteDatabase db=connectordb.getReadableDatabase();

        String [] parametros=null;
        String [] requeridos=null;




        return true;

    }*/


}

