package pis2015.ub.com.clinicaltrialsdiary.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import pis2015.ub.com.clinicaltrialsdiary.Controlador.Controlador;
import pis2015.ub.com.clinicaltrialsdiary.R;


public class TomaDosis extends ActionBarActivity {
    private Switch mySwitch;
    //private TextView text;
    private TextView farmaco;
    private TextView dosis; //nº tomas
    private TextView pastillas;
    private TextView horarios;//preferiblemente
    private Controlador c;
    private String[] datosDosis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        c = (Controlador) getApplication();
        datosDosis = c.getDatosDosisPaciente();
        setContentView(R.layout.activity_toma_dosis);
        //text = (TextView) findViewById(R.id.textView);
        farmaco = (TextView) findViewById(R.id.textView35);
        dosis = (TextView) findViewById(R.id.textView28);
        pastillas = (TextView) findViewById(R.id.textView33);
        horarios = (TextView) findViewById(R.id.textView34);
        int frecuencia = Integer.parseInt(datosDosis[2])/24;
        //text.setText("Recuerde que debe tomarse "+ datosDosis[3] + " pastilla de " + datosDosis[0] + " cada " + frecuencia + " horas " + datosDosis[1]);
        farmaco.setText("Farmaco: "+datosDosis[0]);
        dosis.setText("Tomas al dia: "+datosDosis[2]);
        pastillas.setText("Pastillas por dosis: "+datosDosis[3]);
        horarios.setText("Preferiblemente: "+datosDosis[1]);

        mySwitch = (Switch) findViewById(R.id.mySwitch);
        mySwitch.setChecked(true);

        mySwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    SharedPreferences prefs = getSharedPreferences("Alarma", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean("notific", true);
                    editor.commit();
                }
                else{
                    SharedPreferences prefs = getSharedPreferences("Alarma", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean("notific", false);
                    editor.commit();
                }


            }
        });
        //check the current state before we display the screen

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_toma_dosis, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.guardar) {
            Intent i = new Intent(this, MainPaciente.class);
            startActivity(i);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

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
