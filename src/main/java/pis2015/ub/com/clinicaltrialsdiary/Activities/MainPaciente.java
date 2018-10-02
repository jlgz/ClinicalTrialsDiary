package pis2015.ub.com.clinicaltrialsdiary.Activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import pis2015.ub.com.clinicaltrialsdiary.Controlador.Controlador;
import pis2015.ub.com.clinicaltrialsdiary.ListCita;
import pis2015.ub.com.clinicaltrialsdiary.ListCitaAdapter;
import pis2015.ub.com.clinicaltrialsdiary.Modelo.Paciente;
import pis2015.ub.com.clinicaltrialsdiary.R;
import pis2015.ub.com.clinicaltrialsdiary.ServicioAlarma;


public class MainPaciente extends ActionBarActivity {
    private ListView listViewTrial;
    private Context ctx;
    private Controlador c;
    private boolean notificacion;
    private int[] horasCitas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Controlador c = (Controlador) getApplication();
        setContentView(R.layout.activity_main_pacient);
        Button sintomas = (Button) findViewById(R.id.sintomas);
        Button dosis = (Button) findViewById(R.id.dossis);
        Button cuestionarios = (Button) findViewById(R.id.cuestionario);
        TextView nombreUsuario = (TextView) findViewById(R.id.NombreUsuario);
        TextView nombreDoctor = (TextView) findViewById(R.id.DoctorPaciente);
        TextView hospital = (TextView) findViewById(R.id.ClinicaPaciente);
        String[] infoPaciente = c.getInfoPaciente();
        nombreUsuario.setText(infoPaciente[0]);
        nombreDoctor.setText(infoPaciente[1]);
        hospital.setText(infoPaciente[2]);

        sintomas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runSintomas();
            }
        });
        dosis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rundosis();
            }
        });
        cuestionarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runCuestionarios();
            }
        });
        ctx=this;
        List<ListCita> legendList= new ArrayList<ListCita>();
        ArrayList<String[]> stringList = c.getListCitasPac();
        listViewTrial = ( ListView ) findViewById( R.id.listView8);
        

        for(int i=0;i<stringList.size();i++){
            legendList.add(new ListCita(stringList.get(i)[0],stringList.get(i)[1],stringList.get(i)[2]));
        }
        listViewTrial.setAdapter(new ListCitaAdapter(ctx, R.layout.main_list, legendList));
        SharedPreferences prefs = getSharedPreferences("Alarma", Context.MODE_PRIVATE);
        notificacion = prefs.getBoolean("notific",false);
        if (notificacion) {
            horasCitas = c.getNextCitaPac();
            if (horasCitas[0]!=-1){
                activarAlarma();
            }

        }
        listViewTrial.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }
    private void rundosis(){
        Intent i = new Intent(this, TomaDosis.class);
        startActivity(i);
        finish();
    };
    private void runSintomas(){
        Intent i = new Intent(this, PacienteSintoma.class);
        startActivity(i);
        finish();
    }
    private void runCuestionarios() {
        Intent i = new Intent(this, PacienteCuestionarios.class);
        startActivity(i);
        finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_paciente, menu);
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
        else if(id == R.id.Salir){

            Intent i = new Intent(this,MainActivity.class);
            startActivity(i);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            Intent i = new Intent(this,MainActivity.class);
            startActivity(i);
            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    private void activarAlarma(){

        PendingIntent pendingIntent;
        Calendar calendar = Calendar.getInstance();

        int mes = horasCitas[1]-1;
        int dia = horasCitas[0];
        int year = horasCitas[2];
        int hora = horasCitas[3];
        int min = horasCitas[4];
        if(min<calendar.get(Calendar.MINUTE) && hora<calendar.get(Calendar.HOUR_OF_DAY) && mes<calendar.get(Calendar.MONTH) && dia<calendar.get(Calendar.DAY_OF_MONTH) && year<calendar.get(Calendar.YEAR)){
            Intent myIntent = new Intent(MainPaciente.this, ServicioAlarma.class);
            stopService(myIntent);
        }

        if (dia == 1){
            if(mes == 1){
                dia = 31;
                mes = 12;
                year = year - 1;
            }
            else{
                dia = 30;
                mes = mes - 1;
            }
        }
        else{
            dia = dia -1;
        }

        calendar.set(Calendar.MONTH, mes);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.DAY_OF_MONTH, dia);

        calendar.set(Calendar.HOUR_OF_DAY, horasCitas[3]);
        calendar.set(Calendar.MINUTE, horasCitas[4]);
        calendar.set(Calendar.SECOND, 30);

        calendar.set(Calendar.AM_PM,horasCitas[5]);
        Intent myIntent = new Intent(MainPaciente.this, ServicioAlarma.class);
        pendingIntent = PendingIntent.getBroadcast(MainPaciente.this, 0, myIntent,0);
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
    }
}
