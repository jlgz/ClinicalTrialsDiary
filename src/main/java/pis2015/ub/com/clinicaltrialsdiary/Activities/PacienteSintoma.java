package pis2015.ub.com.clinicaltrialsdiary.Activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import pis2015.ub.com.clinicaltrialsdiary.Controlador.Controlador;
import pis2015.ub.com.clinicaltrialsdiary.R;
import pis2015.ub.com.clinicaltrialsdiary.StringSintomaCheck;
import pis2015.ub.com.clinicaltrialsdiary.listSintomaSelectAdapter;


public class PacienteSintoma extends ActionBarActivity implements View.OnClickListener {
    private ImageButton ib;
    private Calendar cal;
    private int day;
    private int month;
    private int size;
    private int year;
    private EditText et;
    private ListView listViewTrial;
    private Context ctx;
    private EditText hora;
    private ArrayList<Integer> sintomas;
    private Controlador c;
    private ImageButton min;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_administrar_sintoma);
        ib = (ImageButton) findViewById(R.id.imageButtonSintoma);
        min = (ImageButton) findViewById(R.id.imageButton3);
        min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(PacienteSintoma.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        hora.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Selecciona la hora:");
                mTimePicker.show();

            }
        });
        cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);
        et = (EditText) findViewById(R.id.fecha);
        ib.setOnClickListener(this);
        hora= (EditText) findViewById(R.id.editText20);
        setTitle(getString(R.string.title_activity_sintomas).toString());
        c = (Controlador) getApplication();
        ctx=this;
        List<String> str = c.getListaSintomasPac();
        List legendList = new ArrayList<StringSintomaCheck>();
        size = str.size();
        for(String s: str){
            legendList.add(new StringSintomaCheck(s));
        }
        listViewTrial = ( ListView ) findViewById( R.id.listView6);
        listViewTrial.setAdapter( new listSintomaSelectAdapter(ctx, R.layout.select_sintoma_content, legendList));
        listViewTrial.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = (String) parent.getItemAtPosition(position);
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
            et.setText(selectedDay + " / " + (selectedMonth + 1) + " / "
                    + selectedYear);
        }
    };




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_paciente_sintoma, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.enviarSintoma) {
            ArrayList<String> sintomas =  getStringSintomas();
            for(String s: sintomas){
                c.addSintoma(s,et.getText().toString(),hora.getText().toString());
            }
            Intent i = new Intent(this, MainPaciente.class);
            startActivity(i);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public ArrayList<String> getStringSintomas(){
        ArrayList<String> strings = new ArrayList<String>();
        for(int i = 0; i< size;i++){
            StringSintomaCheck st = (StringSintomaCheck) listViewTrial.getAdapter().getItem(i);
            if(st.getCheck()){
                strings.add(st.getString());
            }
        }
        return strings;
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            Intent i = new Intent(this,MainPaciente.class);
            startActivity(i);
            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
