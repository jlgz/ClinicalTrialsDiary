package pis2015.ub.com.clinicaltrialsdiary.Activities;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import pis2015.ub.com.clinicaltrialsdiary.Controlador.Controlador;
import pis2015.ub.com.clinicaltrialsdiary.R;
import pis2015.ub.com.clinicaltrialsdiary.listTrials;
import pis2015.ub.com.clinicaltrialsdiary.listTrialsAdapter;


public class medico_principal extends ActionBarActivity {
    ListView listView ;
    private ListView listViewTrial;
    private Context ctx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int i;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medico_principal);
        TextView infomed = (TextView) findViewById(R.id.textViewInfoGeneralMedico);
        final Controlador c = (Controlador) getApplication();
        infomed.setText(c.getStringMedicoPrincipal());
        ctx=this;
        List<listTrials> legendList= new ArrayList<listTrials>();
        ArrayList<String[]> stringList = c.getStringListEnsayo();
        listViewTrial = ( ListView ) findViewById( R.id.trial_list);
        for(i=0;i<stringList.size();i++){
            legendList.add(new listTrials(stringList.get(i)[0],stringList.get(i)[1],getString(R.string.NumPac)+stringList.get(i)[2]));
        }
        // AÑADIR COMO ENSAYO
       // legendList.add(new listTrials("Cancer colon","15 febrero 2015","Pacientes: 4"));
        listViewTrial.setAdapter( new listTrialsAdapter(ctx, R.layout.main_list, legendList));
        listViewTrial.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listTrials o = (listTrials) parent.getItemAtPosition(position);
                c.setEnsayoAct(position);
                exeListPacientes();
            }

        });
    }


    public void exeListPacientes() {

        Intent i = new Intent(this, EnsayoSelected.class);
        startActivity(i);
        finish();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_medico_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.option_run_addt) {
           // Controlador.ensayoActual(id);
            Intent i = new Intent(this, addTrial.class);
            startActivity(i);
            finish();
            return true;
        }
        else if (id == R.id.Salir){
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            Intent i = new Intent(this,MainActivity.class);
            i.putExtra("vieneAtras", "1");
            startActivity(i);
            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


}
