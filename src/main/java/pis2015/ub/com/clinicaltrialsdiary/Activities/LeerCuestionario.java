package pis2015.ub.com.clinicaltrialsdiary.Activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import pis2015.ub.com.clinicaltrialsdiary.Controlador.Controlador;
import pis2015.ub.com.clinicaltrialsdiary.R;


public class LeerCuestionario extends ActionBarActivity {
    private Controlador c;
    private ArrayList<String> preguntas;
    private ArrayList<String> respuestas;
    private int cont=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        c = (Controlador) getApplication();
        preguntas = c.getPreguntas();
        respuestas = c.getRespuestas();
        setContentView(R.layout.activity_leer_cuestionario);
        Button siguiente = (Button) findViewById(R.id.button9);
        Button anterior = (Button) findViewById(R.id.button10);
        final TextView preg = (TextView) findViewById(R.id.textView11);
        final TextView resp = (TextView) findViewById(R.id.textView29);
        preg.setText(preguntas.get(0));
        resp.setText(respuestas.get(0));
        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cont +=1;
                if(cont >preguntas.size()-1) {
                    cont = 0;
                }
                preg.setText(preguntas.get(cont));
                resp.setText(respuestas.get(cont));
            }
        });
        anterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cont -=1;
                if(cont <0) {
                    cont = preguntas.size()-1;
                }
                preg.setText(preguntas.get(cont));
                resp.setText(respuestas.get(cont));
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_leer_cuestionario, menu);
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
            Intent i = new Intent(this,CuestionariosPaciente.class);
            startActivity(i);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            Intent i = new Intent(this,CuestionariosPaciente.class);
            startActivity(i);
            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
