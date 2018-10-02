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
import pis2015.ub.com.clinicaltrialsdiary.ListCita;
import pis2015.ub.com.clinicaltrialsdiary.ListCitaAdapter;
import pis2015.ub.com.clinicaltrialsdiary.R;
import pis2015.ub.com.clinicaltrialsdiary.listTrials;
import pis2015.ub.com.clinicaltrialsdiary.listTrialsAdapter;


public class ProximasCitas extends ActionBarActivity {
    private ListView listViewTrial;
    private Context ctx;
    private Controlador c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        c = (Controlador) getApplication();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proximas_citas);
        ctx=this;
        List<ListCita> legendList= new ArrayList<ListCita>();
        ArrayList<String[]> stringList = c.getListCitasMedico();
        listViewTrial = ( ListView ) findViewById( R.id.listView);
        for(int i=0;i<stringList.size();i++){
            legendList.add(new ListCita(stringList.get(i)[0],stringList.get(i)[1],stringList.get(i)[2]));
        }
        listViewTrial.setAdapter(new ListCitaAdapter(ctx, R.layout.main_list, legendList));
        listViewTrial.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_proximas_citas, menu);
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
            Intent i = new Intent(this,EnsayoSelected.class);
            startActivity(i);
            finish();
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
