package pis2015.ub.com.clinicaltrialsdiary.Activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import pis2015.ub.com.clinicaltrialsdiary.Controlador.Controlador;
import pis2015.ub.com.clinicaltrialsdiary.PacienteAdapter2;
import pis2015.ub.com.clinicaltrialsdiary.R;
import pis2015.ub.com.clinicaltrialsdiary.listTrialsPaciente;
import pis2015.ub.com.clinicaltrialsdiary.listTrialsPacienteAdapter;


public class NuevoCuestionario1 extends ActionBarActivity implements View.OnClickListener {
    private Controlador c;
    DatePicker pickerDate;
    private ImageButton ib;
    private Calendar cal;
    private int day;
    private int month;
    private int year;
    private EditText nombre;
    private EditText fecha;
    private  ArrayList<Integer> pacientes;
    private ListView listViewTrial;
    private Context ctx;
    private int size;
    private EditText diasResp;
    private CheckBox notificacion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        c =  (Controlador) getApplication();
        pacientes = new ArrayList<>();
        setContentView(R.layout.activity_nuevo_cuestionario1);
        nombre = (EditText) findViewById(R.id.editText13);
        fecha = (EditText) findViewById(R.id.editText14);
        diasResp = (EditText) findViewById(R.id.editText15);
        notificacion = (CheckBox)findViewById(R.id.checkBox);
        ib = (ImageButton) findViewById(R.id.imageButton2);
        cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);
        ctx=this;
        ib.setOnClickListener(this);
        List<listTrialsPaciente> legendList= new ArrayList<listTrialsPaciente>();
        ArrayList<String[]> stringList = c.getStringPacienteListEnsayo();
        listViewTrial = (ListView) findViewById( R.id.listView3);
        size = stringList.size();
        for(int i= 0; i< size;i++){
            legendList.add(new listTrialsPaciente(stringList.get(i)[0],stringList.get(i)[1],stringList.get(i)[2],stringList.get(i)[3]));
        }
        // legendList.add(new listTrialsPaciente("Jaime","Nausias fuertes","55 años","1001"));
        listViewTrial.setAdapter( new PacienteAdapter2(ctx, R.layout.paciente_for_select_cuestionario, legendList));
        listViewTrial.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listTrialsPaciente o = (listTrialsPaciente) parent.getItemAtPosition(position);

            }
        });
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
            fecha.setText(selectedDay + " / " + (selectedMonth + 1) + " / "
                    + selectedYear);
        }
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_nuevo_cuestionario1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.option_run_newc2) {
            getPacientesChecked();
            Intent i = new Intent(this, NuevoCuestionario2.class);
            i.putExtra("pacientes",pacientes);
            i.putExtra("nombre",nombre.getText().toString());
            i.putExtra("fecha",fecha.getText().toString());
            i.putExtra("notificacion",notificacion.isChecked());
            i.putExtra("diasResp",diasResp.getText().toString());
            startActivity(i);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void getPacientesChecked(){
        for(int i = 0; i< size;i++) {
            listTrialsPaciente o = (listTrialsPaciente)listViewTrial.getAdapter().getItem(i);
            if(o.getCheck()){
                pacientes.add(i);
            }
        }
    }
}
