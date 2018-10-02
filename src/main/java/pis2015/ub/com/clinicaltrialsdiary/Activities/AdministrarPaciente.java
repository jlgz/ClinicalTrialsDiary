package pis2015.ub.com.clinicaltrialsdiary.Activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import pis2015.ub.com.clinicaltrialsdiary.Controlador.Controlador;
import pis2015.ub.com.clinicaltrialsdiary.R;
import pis2015.ub.com.clinicaltrialsdiary.ListSintoma;
import pis2015.ub.com.clinicaltrialsdiary.ListSintomasAdapter;

public class AdministrarPaciente extends ActionBarActivity {

    private ListView listViewTrial;
    private Context ctx;
    private Button contact;
    public Intent callIntent;
    private Controlador c;
    private Calendar cal;
    private int day;
    private int month;
    private int year;
    private int hour;
    private int min;
    private EditText fecha;
    private EditText hora;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrar_paciente);
        int i;
        ctx=this;
        c =  (Controlador) getApplication();
        Button cuestionarios = (Button) findViewById(R.id.button3);
        Button newcita = (Button) findViewById(R.id.button5);
        contact = (Button) findViewById(R.id.button4);
        cuestionarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               runCuest();
            }
        });
        newcita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogCita();
            }
        });
        String[] str= c.getInfoAdmPaciente();
        TextView t1 = (TextView) findViewById(R.id.textView27);
        TextView t2 = (TextView) findViewById(R.id.sfd);
        TextView t3 = (TextView) findViewById(R.id.textView21);
        TextView t4 = (TextView) findViewById(R.id.textView22);
        TextView t5 = (TextView) findViewById(R.id.textView23);
        TextView t6 = (TextView) findViewById(R.id.textView24);
        TextView t7 = (TextView) findViewById(R.id.textView25);
        TextView t8 = (TextView) findViewById(R.id.textView26);
        t1.setText(str[0]); //Nombre ensayo
        t2.setText("Nombre y apellidos: "+str[1]);
        t3.setText(getString(R.string.sexu)+str[2]);
        t4.setText("Edad: "+str[3]);
        t5.setText(getString(R.string.tipu)+str[4]);
        t6.setText("Próximas citas: "+str[5]);
        t7.setText(getString(R.string.telefonu)+ str[6]);
        t8.setText("Email: "+str[7]);
        List<ListSintoma> legendList= new ArrayList<>();
        ArrayList<String[]> stringList = c.getStringListSintomaAdmPaciente();
        listViewTrial = ( ListView ) findViewById( R.id.listView2);
        for(i=0;i<stringList.size();i++){
            legendList.add(new ListSintoma(stringList.get(i)[0],stringList.get(i)[1],stringList.get(i)[2]));
        }
        listViewTrial.setAdapter( new ListSintomasAdapter(ctx, R.layout.main_list, legendList));
        listViewTrial.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListSintoma o = (ListSintoma) parent.getItemAtPosition(position);
            }
        });

        contact.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                createDialog();
            }
        });


    }





    private void createDialog(){
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);

        dialogo.setTitle(getString(R.string.comoContactar));
        dialogo.setMessage(getString(R.string.metodoContacto));
        dialogo.setPositiveButton(getString(R.string.contactar_paciente_correo).toString(), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                //CONTACTAR POR CORREO
                String correoPaciente = c.getCorreoPaciente(); //Obtener mail paciente

                String[] to= {correoPaciente}; //Obtener valor del parse
                String[] cc= {};
                String asunto = getString(R.string.asunto);
                String mensaje = "";
                enviar(to,cc,asunto,mensaje);
            }
        });
        dialogo.setNegativeButton(getString(R.string.contactar_paciente_llamar).toString(), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //CONTACTAR POR TELEFONO

                String telefono = "tel:"+ c.getTelefonoPaciente();
                //int telefono = Integer.parseInt(tel);
                call(telefono);
            }
        });

        dialogo.create();

        dialogo.show();
    }

    private void call(String telefono) {
        try {
            callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse(String.valueOf(telefono))); //Obtener valor del parse
            startActivity(callIntent);
        } catch (ActivityNotFoundException activityException) {
            Log.e("dialing-example", "Call failed", activityException);
        }
    }

    private void enviar (String[] to, String[]cc, String asunto, String mensaje){
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        //String[] to = direccionesEmail;
        //String[] cc = copias;
        emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
        emailIntent.putExtra(Intent.EXTRA_CC, cc);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, asunto);
        emailIntent.putExtra(Intent.EXTRA_TEXT, mensaje);
        emailIntent.setType("message/rfc822");
        startActivity(Intent.createChooser(emailIntent, "Email "));
    }
    private void runCuest(){
        Intent i = new Intent(this, CuestionariosPaciente.class);
        startActivity(i);
        finish();
    }

    private void dialogCita(){
        final Dialog d = new Dialog(this);
        d.setTitle(getString(R.string.añadir_cita));
        d.setContentView(R.layout.dialog_cont);

        fecha = (EditText) d.findViewById(R.id.editText18);
        hora = (EditText) d.findViewById(R.id.editText19);
        final ImageButton data = (ImageButton) d.findViewById(R.id.iconoFecha);
        final ImageButton hour = (ImageButton) d.findViewById(R.id.icono_hora);
        data.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                cal = Calendar.getInstance();
                day = cal.get(Calendar.DAY_OF_MONTH);
                month = cal.get(Calendar.MONTH);
                year = cal.get(Calendar.YEAR);
                showDialog(0);
            }
        });
        hour.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AdministrarPaciente.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        hora.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Selecciona la hora:");
                mTimePicker.show();

            }
        });
        Button butAcpt = (Button) d.findViewById(R.id.button11);
        Button butCanc = (Button) d.findViewById(R.id.button12);
        butCanc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.cancel();
            }
        });
        butAcpt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.addCita(fecha.getText().toString(), hora.getText().toString());
                d.cancel();
            }
        });
        d.show();
    }

    protected Dialog onCreateDialog(int id) {
        return new DatePickerDialog(this, datePickerListener, year, month, day);
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            fecha.setText(selectedDay + "/" + (selectedMonth + 1) + "/"
                    + selectedYear);
        }
    };
/*
    protected Dialog onCreateDialogo(int id){
        return new TimePickerDialog(this, timePickerListener, hour, min);
    }

    private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener(){
        public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute){
            hora.setText(selectedHour + ":" + selectedMinute);
        }
    }*/



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_administrar_paciente, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.option_exit_admpac) {
            Intent i = new Intent(this, EnsayoSelected.class);
            startActivity(i);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

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
