package pis2015.ub.com.clinicaltrialsdiary.Activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import pis2015.ub.com.clinicaltrialsdiary.Activities.MainActivity;
import pis2015.ub.com.clinicaltrialsdiary.Controlador.Controlador;
import pis2015.ub.com.clinicaltrialsdiary.R;


public class registro_medico extends ActionBarActivity {
    private Spinner spinner1;
    private String[] datosRetorno;
    private String password;
    private String nombreRegistro;
    //public static String base = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ@!#$";
    private Controlador c;
    private EditText nomTxt;
    private EditText apellidoTxt;
    private EditText hospital;
    private EditText departamento;
    private Spinner sexo;
    private EditText correo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_medico);
        c = (Controlador) getApplication();

        spinner1 = (Spinner) findViewById(R.id.spinner);
        String.valueOf(spinner1.getSelectedItem());
        nomTxt = (EditText) findViewById(R.id.editText_NombreRegistro);
        apellidoTxt = (EditText) findViewById(R.id.editText_ApellidosRegistro);
        hospital = (EditText) findViewById(R.id.editText3);
        departamento = (EditText) findViewById(R.id.editText4);
        sexo = (Spinner) findViewById(R.id.spinner);
        correo = (EditText) findViewById(R.id.editTextCorreo);

        Button button = (Button) findViewById(R.id.buttonRegistro);
        button.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        //datosRetorno = c.registrarDoctor(nomTxt.getText().toString(), apellidoTxt.getText().toString(), sexo.toString(), hospital.getText().toString(), departamento.getText().toString());
                        new LongOperation().execute();
                        /*try{ Thread.sleep(1200);}
                        catch (InterruptedException e) {
                            Thread.interrupted();

                        }*/
                        /*nombreRegistro = datosRetorno[0];
                        password = datosRetorno[1];*/

                        //ejecutar(v);


                        //Controlador.registrarDoctor();
                        // LLAMAR CONTROLADOR con parametros


                        
                        //int largoPassword=Integer.parseInt("6");
                        //Controlador.registrarPaciente(nombre)
                        /*
                        int longitud = base.length();
                        for(int i=0; i<largoPassword;i++){ //1
                            int numero = (int)(Math.random()*(longitud)); //2
                            String caracter=base.substring(numero, numero+1); //3
                            password=password+caracter; //4
                        }
                         */

                        /* ARNAU ACABA DE MODIFICAR ESTO QUE PETA XD--->
                        controlador.registrarDoctor(nombre, apellidos, sexo, hospital, String departamento){
                        */

                    }
                }
        );
    }


    class LongOperation extends AsyncTask<String, Void, String> {
        private int i;
        private boolean error = true;
        ProgressDialog Asycdialog = new ProgressDialog(registro_medico.this); //Declaramos el dialog de cargando

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            Asycdialog.setMessage(getString(R.string.cargando)); //Escribimos el mensaje que mostrara el dialog
            Asycdialog.show(); //Mostramos el dialogo de cargando
        }

        @Override
        protected String doInBackground(String... params) {

            if (nomTxt.getText().toString().equals("") || apellidoTxt.getText().toString().equals("") || hospital.getText().toString().equals("") || departamento.getText().toString().equals("") || correo.getText().toString().equals("")){
                error = true;
            }
            else {
                datosRetorno = c.registrarDoctor(nomTxt.getText().toString(), apellidoTxt.getText().toString(), sexo.getSelectedItem().toString(), hospital.getText().toString(), departamento.getText().toString(), correo.getText().toString());
                error = false;
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            Context context = getApplicationContext();
            if (!error) {
                if (c.checkConnectivity()) {
                    nombreRegistro = datosRetorno[0];
                    password = datosRetorno[1];
                    ejecutar();
                }
                else{
                    Toast.makeText(context, getString(R.string.errorConexion), Toast.LENGTH_LONG).show();
                }
            }
            else{
                Toast.makeText(context, getString(R.string.noPermitido), Toast.LENGTH_LONG).show();
            }
            Asycdialog.dismiss(); //Quitamos el dialog de cargando
        }
    }


    public void ejecutar(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.infoNombreRegistro) +"\t" + nombreRegistro +"\n"+ getString(R.string.infoPasswordRegistro) +"\t"+password + getString(R.string.infoRegistro));
        builder.setTitle(getString(R.string.Confirmar));
        builder.setCancelable(false);
        builder.setPositiveButton(getString(R.string.aceptar), new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                exe();
            }
        });
        builder.setNegativeButton(getString(R.string.cancelar),new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void exe() {
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("nombre", nombreRegistro);
        i.putExtra("password",password);
        startActivity(i);
        finish();
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



    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            Intent i = new Intent(this,MainActivity.class);
            i.putExtra("vieneAtras","1");
            startActivity(i);
            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
