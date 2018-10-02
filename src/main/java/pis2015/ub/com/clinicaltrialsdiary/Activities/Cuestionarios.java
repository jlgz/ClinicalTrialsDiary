package pis2015.ub.com.clinicaltrialsdiary.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import pis2015.ub.com.clinicaltrialsdiary.Activities.NuevoCuestionario1;
import pis2015.ub.com.clinicaltrialsdiary.Activities.PacienteCuestionario;
import pis2015.ub.com.clinicaltrialsdiary.Controlador.Controlador;
import pis2015.ub.com.clinicaltrialsdiary.R;
import pis2015.ub.com.clinicaltrialsdiary.listTrials;
import pis2015.ub.com.clinicaltrialsdiary.listTrialsCuestionario;
import pis2015.ub.com.clinicaltrialsdiary.listTrialsCuestionarioAdapter;


public class Cuestionarios extends ActionBarActivity {

    ListView listView;
    private ListView listViewTrial;
    private Context ctx;
    private Controlador c;
    private TextView titulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuestionarios);
        c  =(Controlador) getApplication();
        ctx = this;
        titulo = (TextView) findViewById(R.id.textView5);
        titulo.setText(c.getStringEnsayo());
        List<listTrialsCuestionario> legendList = new ArrayList<listTrialsCuestionario>();
        ArrayList<String[]> info = c.getInfoListCuest();
        for(int i= 0; i< info.size();i++){
            legendList.add(new listTrialsCuestionario(info.get(i)[0], info.get(i)[1]));
        }
        //legendList.add(new listTrialsCuestionario("Cuestionario 2", "07/02/2015 - 11:30"));
        listViewTrial = (ListView) findViewById(R.id.listViewCuest);
        listViewTrial.setAdapter(new listTrialsCuestionarioAdapter(ctx, R.layout.main_list, legendList));
        listViewTrial.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*listTrialsCuestionario o = (listTrialsCuestionatio) parent.getItemAtPosition(position); */
                exe(position);
            }

        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cuestionarios, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.option_run_newc) {
            Intent i = new Intent(this, NuevoCuestionario1.class);
            startActivity(i);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void exe(int p) {
        c.setCuestionarioAct(p);
        Intent i = new Intent(this, editCuestionario.class);
        startActivity(i);
        finish();
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

