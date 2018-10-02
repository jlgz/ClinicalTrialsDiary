package pis2015.ub.com.clinicaltrialsdiary.Activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

import pis2015.ub.com.clinicaltrialsdiary.Controlador.Controlador;
import pis2015.ub.com.clinicaltrialsdiary.R;


public class AddPacientes extends ActionBarActivity {
    private Controlador c;
    private EditText id;
    final Context context = this;
    private String nombre;
    private String idRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pacientes);
        idRegistro = getIntent().getStringExtra("id");
        id = (EditText) findViewById(R.id.add_paciente_edit_text);
        id.setText(idRegistro);
        c = (Controlador) getApplication();
        setTitle(getString(R.string.title_activity_add_pacientes).toString());
        Button confirm = (Button) findViewById(R.id.add_paciente_confirmar);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog();
            }

        });
    }

    private void createDialog(){
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle(getString(R.string.addPaciente));
        dialogo.setMessage(getString(R.string.addPacienteAEnsayo));
        dialogo.setPositiveButton(getString(R.string.add_pacientes_aceptar).toString(), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                new LongOperation().execute();

            }
        });
        dialogo.setNegativeButton(getString(R.string.add_pacientes_cancelar).toString(), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        dialogo.create();

        dialogo.show();
    }


    class LongOperation extends AsyncTask<String, Void, String> {
        private boolean b;
        private boolean error = true;
        ProgressDialog Asycdialog = new ProgressDialog(AddPacientes.this); //Declaramos el dialog de cargando

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            Asycdialog.setMessage(getString(R.string.cargando)); //Escribimos el mensaje que mostrara el dialog
            Asycdialog.show(); //Mostramos el dialogo de cargando
        }

        @Override
        protected String doInBackground(String... params) {

            if (id.getText().toString() == ""){
                error = true;
            }
            else {
                b = c.addPacToEnsayo(id.getText().toString());
                error = false;
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            Context context = getApplicationContext();
            if(b){

                CharSequence text = getString(R.string.correcto);

                Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
                toast.show();


            } else {
                CharSequence text = getString(R.string.idError);

                Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
                toast.show();
            }
            Intent i = new Intent(context,EnsayoSelected.class);
            startActivity(i);
            finish();
            Asycdialog.dismiss(); //Quitamos el dialog de cargando

        }
    }


    public void exeAddPaciente(){

        boolean b = c.addPacToEnsayo(id.getText().toString());
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;

        if(b){

            CharSequence text = getString(R.string.correcto);

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();


        } else {
            CharSequence text = getString(R.string.idError);

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        Intent i = new Intent(this,EnsayoSelected.class);
        startActivity(i);
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_paciente, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add_paciente_crear_user) {
            Intent i = new Intent(this, CrearUsuario.class);
            startActivity(i);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            Intent i = new Intent(this,EnsayoSelected.class);
            startActivity(i);
            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
