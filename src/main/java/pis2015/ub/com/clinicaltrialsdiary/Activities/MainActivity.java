package pis2015.ub.com.clinicaltrialsdiary.Activities;


import android.app.ActionBar;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import pis2015.ub.com.clinicaltrialsdiary.Controlador.Controlador;
import pis2015.ub.com.clinicaltrialsdiary.ServicioAlarma;
import pis2015.ub.com.clinicaltrialsdiary.R;
import com.parse.Parse;

import java.lang.reflect.Field;
import java.util.Calendar;


public class MainActivity extends ActionBarActivity {
    private Controlador c;
    private String nombre = null;
    private String pass = null;
    private EditText editText;
    private EditText editText2;
    final Context context = this;
    private boolean errorResetPassword;
    private String vieneAtras = "0";
    private PendingIntent pendingIntent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        makeActionOverflowMenuShown();
        c = (Controlador) getApplication();
        Parse.initialize(this, "JvPH2s6Pz8AyDtUwCeYCA7DZzrhjjzHdXyPEMeKz", "OLk4K4nDudlvvu681HFjTXkM796UvnqSJy8JMR05");

        editText  = (EditText) findViewById(R.id.editText1); //Nombre usuario
        editText2 = (EditText) findViewById(R.id.editText2); // Password

        nombre = getIntent().getStringExtra("nombre");
        pass = getIntent().getStringExtra("password");
        vieneAtras = getIntent().getStringExtra("vieneAtras");

        if(nombre != null) {
            editText.setText(nombre);
            editText2.setText(pass); //No se escribe en el editText la password
            if (vieneAtras != "1"){intentarEntrar(nombre,pass);}
        }
        else{
            SharedPreferences prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE); //Cargamos de SharedPreferences
            nombre = prefs.getString("name", null); //Si no se encuentra nada se carga "null"
            pass = prefs.getString("pass", null);
            if (nombre != null) {
                editText.setText(nombre);
                editText2.setText(pass);
                if (vieneAtras != "1") {
                    //intentarEntrar(nombre,pass);
                }

            }
        }


        Button button = (Button) findViewById(R.id.button_registro);

        Button buttonLog = (Button) findViewById(R.id.button1);
        button.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        ejecutar();
                    }
                }
        );
        buttonLog.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        if (nombre == null || !nombre.equals(editText.getText().toString())|| !pass.equals(editText2.getText().toString())){
                            nombre = editText.getText().toString();
                            pass = editText2.getText().toString();
                        }

                        if (c.checkConnectivity()){
                            new LongOperation().execute(nombre, pass);
                        }
                        else{
                            Context context = getApplicationContext();
                            CharSequence text = getString(R.string.errorConexion);
                            int duration = Toast.LENGTH_LONG;

                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                        }
                    }
                }
        );

    }

    public void ejecutar() {
        Intent i = new Intent(this, registro_medico.class);
        startActivity(i);
        finish();
    }


    class LongOperation extends AsyncTask<String, Void, String> {
        private int i;
        ProgressDialog Asycdialog = new ProgressDialog(MainActivity.this); //Declaramos el dialog de cargando

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            Asycdialog.setMessage(getString(R.string.loading)); //Escribimos el mensaje que mostrara el dialog
            Asycdialog.show(); //Mostramos el dialogo de cargando
        }

        @Override
        protected String doInBackground(String... params) {


            i = c.logar(params[0], params[1]);


            return null;
        }

        @Override
        protected void onPostExecute(String result) {

            if (i == 1) {
                SharedPreferences settings = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("name", nombre);
                editor.putString("pass", pass);
                editor.commit();
                exe2(); //entras como paciente
            } else if (i == 0) {

                Context context = getApplicationContext();
                CharSequence text = "Error de login";
                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            } else {
                SharedPreferences settings = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("name", nombre);
                editor.putString("pass", pass);
                editor.commit();
                exe1(); // como doctor
            }
            Asycdialog.dismiss(); //Quitamos el dialog de cargando
        }
    }

    public void exe1(){

        Intent i = new Intent(this, medico_principal.class);
        startActivity(i);
        finish();
    }
    public void exe2(){
        Intent i = new Intent(this, MainPaciente.class);
        startActivity(i);
        finish();
    }


    public void dialogPassword(){
        final Dialog dialog = new Dialog(context);

        dialog.setContentView(R.layout.restart_password);
        dialog.setTitle(R.string.OlvidarContrasenya);
        final Button aceptarButton = (Button) dialog.findViewById(R.id.aceptar);
        dialog.show();
        aceptarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (c.checkConnectivity()) {
                    EditText email = (EditText) dialog.findViewById(R.id.email);
                    errorResetPassword = c.resetearPassword(email.getText().toString());
                    if (!errorResetPassword){
                        Toast.makeText(context, getString(R.string.enviadoCorrecto), Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(context, getString(R.string.errorCorreoNoEncontrado), Toast.LENGTH_LONG).show();
                    }
                    dialog.dismiss();

                }
                else{
                    Context context = getApplicationContext();
                    CharSequence text = getString(R.string.errorConexion);
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });
        final Button cancelarButton = (Button) dialog.findViewById(R.id.cancelar);
        cancelarButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                dialog.dismiss();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            SharedPreferences settings = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("name", "");
            editor.putString("pass", "");
            editor.commit();
            editText.setText("");
            editText2.setText("");

        }
        else if (id == R.id.resetPassword){
            dialogPassword();


        }

        return super.onOptionsItemSelected(item);
    }


    public void intentarEntrar(String nombre, String password){
        new LongOperation().execute(nombre, password);

    }

    private void makeActionOverflowMenuShown() {
        //devices with hardware menu button (e.g. Samsung Note) don't show action overflow menu
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);

            }
        } catch (Exception e) {

        }
    }
}