package pis2015.ub.com.clinicaltrialsdiary.Activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import pis2015.ub.com.clinicaltrialsdiary.Controlador.Controlador;
import pis2015.ub.com.clinicaltrialsdiary.R;

public class PacienteCuestionario extends ActionBarActivity {
    private Controlador c;
    private ArrayList<String> preguntas;
    private ArrayList<String> respuestas;
    private int cont=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_cuestionario);
        c =  (Controlador) getApplication();
        preguntas = c.getPreguntasPac();
        respuestas = new ArrayList<>();
        final TextView preg= (TextView)findViewById(R.id.textView10);
        preg.setText(preguntas.get(0));
        Button contestar = (Button) findViewById(R.id.button7);
        final EditText resp = (EditText) findViewById(R.id.editText16);
        contestar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cont+=1;
                respuestas.add(resp.getText().toString());
                if(cont == preguntas.size()){
                    c.guardarRespuesta(respuestas);
                    end(); //llama al metodo end que termina el Intent, vuelve a PacienteCuestionarios
                }
                else{
                    preg.setText(preguntas.get(cont));
                }
            }
        });
    }

    public void end() {
        CharSequence text = getString(R.string.cuestionario_respondido_exito);
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(this, text, duration);
        toast.show();
        Intent i = new Intent(this,PacienteCuestionarios.class);
        startActivity(i);
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_paciente_cuestionario, menu);
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
            Intent i = new Intent(this,PacienteCuestionarios.class);
            startActivity(i);
            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
