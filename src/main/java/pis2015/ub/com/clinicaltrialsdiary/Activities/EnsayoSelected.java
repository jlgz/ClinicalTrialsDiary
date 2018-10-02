package pis2015.ub.com.clinicaltrialsdiary.Activities;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import pis2015.ub.com.clinicaltrialsdiary.Controlador.Controlador;
import pis2015.ub.com.clinicaltrialsdiary.R;
import pis2015.ub.com.clinicaltrialsdiary.listTrialsPaciente;
import pis2015.ub.com.clinicaltrialsdiary.listTrialsPacienteAdapter;


public class EnsayoSelected extends ActionBarActivity {
    ListView listView ;
    private ListView listViewTrial;
    private Context ctx;
    private  Controlador c;
    private DrawerLayout mDrawer;
    private ListView mDrawerOptions;
    private static final String[] values = {"Drawer 1", "Drawer 2", "Drawer 3"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ensayo_selected);


        int i;
        ctx=this;
        c = (Controlador) getApplication();
        TextView t1 = (TextView) findViewById(R.id.Textviewens);
        TextView t2 = (TextView) findViewById(R.id.textView13);
        TextView t3 = (TextView) findViewById(R.id.textView14);
        TextView t4 = (TextView) findViewById(R.id.textView15);
        TextView t6 = (TextView) findViewById(R.id.textView17);
        TextView t7 = (TextView) findViewById(R.id.textView18);
        String[] st = c.getInfoMed();
        t1.setText(st[0]);
        t2.setText(st[1]);
        t3.setText(st[2]);
        t4.setText(getString(R.string.fechacrec)+st[3]);
        t6.setText(getString(R.string.numPac)+ st[4]);
        t7.setText(getString(R.string.tipo_ensayo)+st[5]);
        List<listTrialsPaciente> legendList= new ArrayList<listTrialsPaciente>();
        ArrayList<String[]> stringList = c.getStringPacienteListEnsayo();
        listViewTrial = ( ListView ) findViewById( R.id.listensselect);
        for(i= 0; i< stringList.size();i++){
            legendList.add(new listTrialsPaciente(stringList.get(i)[0],stringList.get(i)[1],stringList.get(i)[2],stringList.get(i)[3]));
        }
       // legendList.add(new listTrialsPaciente("Jaime","Nausias fuertes","55 años","1001"));
        listViewTrial.setAdapter( new listTrialsPacienteAdapter(ctx, R.layout.main_list, legendList));
        listViewTrial.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listTrialsPaciente o = (listTrialsPaciente) parent.getItemAtPosition(position);
                c.setPacienteSelect(position);
                exePaciente();
            }

        });
    }

    private void exePaciente(){
        Intent i = new Intent(this, AdministrarPaciente.class);
        startActivity(i);
        finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ensayo_selected, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent i;
        switch (id){
            case(R.id.option_run_addp):
                i = new Intent(this, AddPacientes.class);
                startActivity(i);
                finish();
                break;
            case(R.id.option_run_cuestionarios):
                i = new Intent(this,Cuestionarios.class);
                startActivity(i);
                finish();
                break;
            case(R.id.option_run_dosis):
                i = new Intent(this, Dosis.class);
                startActivity(i);
                finish();
                break;
            case(R.id.option_run_sintomas):
                i = new Intent(this, Sintomas.class);
                startActivity(i);
                finish();
                break;
            case(R.id.option_run_proxcitas):
                i = new Intent(this, ProximasCitas.class);
                startActivity(i);
                finish();
                break;
            case(R.id.option_run_ensayosedit):
                i = new Intent(this, EditarEnsayo.class);
                startActivity(i);
                finish();
                break;
            case android.R.id.home:
                if (mDrawer.isDrawerOpen(mDrawerOptions)){
                    mDrawer.closeDrawers();
                }else{
                    mDrawer.openDrawer(mDrawerOptions);
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            Intent i = new Intent(this,medico_principal.class);
            startActivity(i);
            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(this, "Pulsado " + values[i], Toast.LENGTH_SHORT).show();
        mDrawer.closeDrawers();
    }



}
