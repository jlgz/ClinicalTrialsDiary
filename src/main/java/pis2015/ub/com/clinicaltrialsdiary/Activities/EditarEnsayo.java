package pis2015.ub.com.clinicaltrialsdiary.Activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import java.util.Calendar;

import pis2015.ub.com.clinicaltrialsdiary.Controlador.Controlador;
import pis2015.ub.com.clinicaltrialsdiary.R;


public class EditarEnsayo extends ActionBarActivity implements View.OnClickListener{
    DatePicker pickerDate;
    private ImageButton ib;
    private Calendar cal;
    private int day;
    private int month;
    private int year;
    private EditText et;
    private EditText name;
    private Spinner spinner1;
    private Controlador c;
    private String[] datos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        c = (Controlador) getApplication();
        setContentView(R.layout.activity_add_trial);
        datos = c.getStringEnsayoEdit();
        et =(EditText) findViewById(R.id.fecha);
        name = (EditText) findViewById(R.id.editText5);
        spinner1 = (Spinner) findViewById(R.id.spinner);
        ib = (ImageButton) findViewById(R.id.imageButton1);
        cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);
        name.setText(datos[0]);
        et.setText(datos[1]);
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
            et.setText(selectedDay + " / " + (selectedMonth + 1) + " / "
                    + selectedYear);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_trial, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.option_exit_addt) {
            c.editEnsayo(name.getText().toString(),et.getText().toString(),String.valueOf(spinner1.getSelectedItem()));
            Intent i = new Intent(this,medico_principal.class);
            startActivity(i);
            finish();
            return true;
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
