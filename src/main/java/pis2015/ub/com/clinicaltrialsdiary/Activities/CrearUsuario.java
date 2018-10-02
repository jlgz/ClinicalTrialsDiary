package pis2015.ub.com.clinicaltrialsdiary.Activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

import pis2015.ub.com.clinicaltrialsdiary.Controlador.Controlador;
import pis2015.ub.com.clinicaltrialsdiary.R;


public class CrearUsuario extends ActionBarActivity implements View.OnClickListener {
    private ImageButton ib;
    private Calendar cal;
    private int day;
    private int month;
    private int year;
    final Context context = this;
    private EditText et;
    private Controlador c;
    private EditText name;
    private EditText apellidos;
    private Spinner sexo;
    private Spinner tipo;
    private EditText tlf;
    private EditText email;
    private boolean error = false;
    private String nombreRegistro;
    private String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_usuario);
        et=(EditText) findViewById(R.id.editText10);
        name = (EditText) findViewById(R.id.editText8);
        apellidos = (EditText) findViewById(R.id.editText9);
        sexo = (Spinner) findViewById(R.id.spinner3);
        tipo = (Spinner) findViewById(R.id.spinner4);
        tlf = (EditText) findViewById(R.id.editText11);
        email = (EditText) findViewById(R.id.editText12);
        c = (Controlador) getApplication();
        Button confirm = (Button) findViewById(R.id.button2);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LongOperation().execute();
            }

        });
        ib = (ImageButton) findViewById(R.id.imageButton);
        cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);
        ib.setOnClickListener(this);
    }



    public void onClick(View v) {
        showDialog(0);
    }

    protected Dialog onCreateDialog(int id) {
        return new DatePickerDialog(this, datePickerListener, year, month, day);
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            et.setText(selectedDay + "/" + (selectedMonth + 1) + "/"
                    + selectedYear);
        }
    };

    class LongOperation extends AsyncTask<String, Void, String> {
        private int i;
        private boolean error = true;
        ProgressDialog Asycdialog = new ProgressDialog(CrearUsuario.this); //Declaramos el dialog de cargando

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            Asycdialog.setMessage(getString(R.string.cargando)); //Escribimos el mensaje que mostrara el dialog
            Asycdialog.show(); //Mostramos el dialogo de cargando
        }

        @Override
        protected String doInBackground(String... params) {

            if (name.getText().toString().equals("") || apellidos.getText().toString().equals("") || tlf.getText().toString().equals("") || email.getText().toString().equals("")){
                error = true;
            }
            else {
                error = false;
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            Context context = getApplicationContext();
            if (!error) {
                if (c.checkConnectivity()) {
                    createDialog();
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

    private void createDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String[] datosRetorno;




            datosRetorno = c.crearUsuario(name.getText().toString(), apellidos.getText().toString(), et.getText().toString(), sexo.getSelectedItem().toString(), tipo.getSelectedItem().toString(), tlf.getText().toString(), email.getText().toString());
            nombreRegistro = datosRetorno[0];
            password = datosRetorno[1];
            builder.setMessage(getString(R.string.infoNombreRegistro) + "\t" + datosRetorno[0] + "\n" + getString(R.string.infoPasswordRegistro) + "\t" + datosRetorno[1] + getString(R.string.infoRegistro));
            builder.setTitle(getString(R.string.Confirmar));
            builder.setCancelable(false);
            builder.setPositiveButton(getString(R.string.aceptar), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    volverActivityAnterior();
                }
            });
            /*builder.setNegativeButton(getString(R.string.cancelar), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //Faltaria llamar a un metodo para eliminar el usuario creado...
                    dialog.cancel();
                }
            });*/
            AlertDialog alert = builder.create();
            alert.show();
        /*
        dialogo.setTitle("Add paciente");
        dialogo.setMessage("¿Deseas confirmar?, el ID del usuario sera "+//c.nextId());
        dialogo.setPositiveButton(getString(R.string.add_pacientes_aceptar).toString(), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                c.crearUsuario(name.getText().toString(),apellidos.getText().toString(),sexo.getSelectedItem().toString(),tipo.getSelectedItem().toString(),tlf.getText().toString(),email.getText().toString());
                dialog.cancel();
            }
        });
        dialogo.setNegativeButton(getString(R.string.add_pacientes_cancelar).toString(), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        dialogo.create();

        dialogo.show();
        */
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_crear_usuario, menu);
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            Intent i = new Intent(this,AddPacientes.class);
            startActivity(i);
            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    public void volverActivityAnterior(){
        Intent i = new Intent(this,AddPacientes.class);
        i.putExtra("id", nombreRegistro);
        startActivity(i);
        finish();
    }
}
