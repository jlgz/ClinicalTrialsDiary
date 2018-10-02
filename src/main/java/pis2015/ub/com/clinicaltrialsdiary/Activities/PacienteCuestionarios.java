package pis2015.ub.com.clinicaltrialsdiary.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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


public class PacienteCuestionarios extends ActionBarActivity {
    private ListView listViewTrial;
    private Context ctx;
    private Controlador c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuestionarios_paciente);
        c = (Controlador) getApplication();
        ctx = this;
        List<listTrialsCuestionario> legendList = new ArrayList<listTrialsCuestionario>();
        ArrayList<String[]> info = c.getInfoListCuestPac();
        for(int i= 0; i< info.size();i++){
            legendList.add(new listTrialsCuestionario(info.get(i)[0], info.get(i)[1]));
        }
        listViewTrial = (ListView) findViewById(R.id.listView5);
        listViewTrial.setAdapter(new listTrialsCuestionarioAdapter(ctx, R.layout.main_list, legendList));
        listViewTrial.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                runDoCuest(position);
            }

        });
    }
    private void runDoCuest(int position){
        c.setCuestionarioAct(position);
        if (c.cuestionarioRespondido(position)) { //si ha sido respondido muestra un mensaje
            AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
            dialogo.setTitle(getString(R.string.error));
            dialogo.setMessage(getString(R.string.cuestionario_respondido));
            dialogo.setPositiveButton(getString(R.string.aceptar).toString(), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //se cierra
                }
            });
            dialogo.create();
            dialogo.show();
        } else {
            Intent i = new Intent(this, PacienteCuestionario.class);
            startActivity(i);
        }
        //finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_paciente_cuestionarios, menu);
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
