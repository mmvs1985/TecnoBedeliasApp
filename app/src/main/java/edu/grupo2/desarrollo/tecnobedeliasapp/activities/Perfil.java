package edu.grupo2.desarrollo.tecnobedeliasapp.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;

import edu.grupo2.desarrollo.tecnobedeliasapp.ConfigSingletton;
import edu.grupo2.desarrollo.tecnobedeliasapp.R;

public class Perfil extends AppCompatActivity {

    private static final String ETIQUETA = "Perfil";
    TextView nombre,apellido, mail;
    ImageView fotoiv;
    Bitmap foto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        fotoiv=(ImageView)findViewById(R.id.fotoperfiliv);
        nombre=(TextView)findViewById(R.id.nombretv);
        apellido=(TextView)findViewById(R.id.apellidotv);
        mail=(TextView)findViewById(R.id.mailtv);
        ConfigSingletton sing=ConfigSingletton.getInstance();
        nombre.setText(sing.consultaUsuarioLogueado(getApplicationContext()).getNombre());
        apellido.setText(sing.consultaUsuarioLogueado(getApplicationContext()).getApellido());
        mail.setText(sing.consultaUsuarioLogueado(getApplicationContext()).getEmail());
        Log.e(ETIQUETA,"Esta es la foto:"+sing.consultaUsuarioLogueado(getApplicationContext()).getFoto()  );

        try {



        String encodedDataString =sing.consultaUsuarioLogueado(getApplicationContext()).getFoto();

        encodedDataString = encodedDataString.replace("data:image/jpeg;base64,","");

        byte[] imageAsBytes = Base64.decode(encodedDataString.getBytes(), 0);
        fotoiv.setImageBitmap(BitmapFactory.decodeByteArray(
                imageAsBytes, 0, imageAsBytes.length));
        }
        catch (Exception e){
            e.printStackTrace();
        }

        if (foto!=null){
            fotoiv.setImageBitmap(foto);
        }



    }
}
