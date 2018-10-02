package pis2015.ub.com.clinicaltrialsdiary.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import pis2015.ub.com.clinicaltrialsdiary.Controlador.Controlador;
import pis2015.ub.com.clinicaltrialsdiary.R;
import pis2015.ub.com.clinicaltrialsdiary.listTrialsCuestionario;
import pis2015.ub.com.clinicaltrialsdiary.listTrialsCuestionarioAdapter;


public class CuestionariosPaciente extends ActionBarActivity {
    private Context ctx;
    private ListView listViewTrial;
    private Controlador c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx =this;
        c = (Controlador) getApplication();
        List<listTrialsCuestionario> legendList = new ArrayList<listTrialsCuestionario>();
        ArrayList<String[]> info = c.getInfoPacselect();
        for(int i= 0; i< info.size();i++){
            legendList.add(new listTrialsCuestionario(info.get(i)[0], info.get(i)[1]));
        }
        setContentView(R.layout.activity_cuestionarios_paciente);
        listViewTrial = (ListView) findViewById(R.id.listView5);
        listViewTrial.setAdapter(new listTrialsCuestionarioAdapter(ctx, R.layout.main_list, legendList));
        listViewTrial.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                runLeer(position);
            }
        });
    }
    private void runLeer(int p){
        c.setCuestionarioAct(p);
        Intent i = new Intent(this, LeerCuestionario.class);
        startActivity(i);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cuestionarios_paciente, menu);
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
            Intent i = new Intent(this,AdministrarPaciente.class);
            startActivity(i);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            Intent i = new Intent(this,AdministrarPaciente.class);
            startActivity(i);
            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
