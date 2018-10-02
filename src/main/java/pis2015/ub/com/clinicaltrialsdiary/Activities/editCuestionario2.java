package pis2015.ub.com.clinicaltrialsdiary.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import pis2015.ub.com.clinicaltrialsdiary.Controlador.Controlador;
import pis2015.ub.com.clinicaltrialsdiary.Modelo.Cuestionario;
import pis2015.ub.com.clinicaltrialsdiary.R;
import pis2015.ub.com.clinicaltrialsdiary.StringsAdapter;


public class editCuestionario2 extends ActionBarActivity {
    private ListView listViewTrial;
    private Context ctx;
    private ArrayList<Integer> pacientes;
    private String fecha;
    private String nombre;
    private ArrayList<String> preguntas;
    private boolean notificar;
    private String diasRespuest;
    private Controlador c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_cuestionario2);
        c = (Controlador) getApplication();
        ctx = this;
        Bundle extras = getIntent().getExtras();
        pacientes = extras.getIntegerArrayList("pacientes");
        fecha = extras.getString("fecha");
        nombre = extras.getString("nombre");
        notificar = extras.getBoolean("notificacion");
        diasRespuest = extras.getString("diasResp");
        preguntas = new ArrayList<>();
        listViewTrial = (ListView) findViewById(R.id.listView4);
        Button addP = (Button) findViewById(R.id.button);
        final EditText preg = (EditText) findViewById(R.id.editText7);
        addP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preguntas.add(preg.getText().toString());
                listViewTrial.setAdapter(new StringsAdapter(ctx,R.layout.list_pregunta,preguntas));

            }
        });
        listViewTrial.setAdapter(new StringsAdapter(ctx,R.layout.list_pregunta,preguntas));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_cuestionario2, menu);
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
            c.editCuestionario(nombre,fecha,pacientes,preguntas,diasRespuest,notificar);
            Intent i = new Intent(this, Cuestionarios.class);
            startActivity(i);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            Intent i = new Intent(this,editCuestionario.class);
            startActivity(i);
            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
