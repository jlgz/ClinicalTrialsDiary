package pis2015.ub.com.clinicaltrialsdiary.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import pis2015.ub.com.clinicaltrialsdiary.Controlador.Controlador;
import pis2015.ub.com.clinicaltrialsdiary.R;


public class Dosis extends ActionBarActivity {
    private EditText name;
    private Context context;
    private EditText nPastillas;
    private  EditText nTomas;
    private EditText xHoras;
    private Controlador c;
    private String[] dosis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Controlador c = (Controlador) getApplication();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dosis);
        final Button confirm = (Button) findViewById(R.id.button13);//CAMBIAR a button13
        name = (EditText) findViewById(R.id.editText);
        nPastillas = (EditText) findViewById(R.id.editText2);
        nTomas = (EditText) findViewById(R.id.editText3);
        xHoras = (EditText) findViewById(R.id.editText17);
        dosis = c.getDatosDosis();
        if(dosis[0]!="") { //entonces esta inicializado
            name.setText(dosis[0]);
            nPastillas.setText(dosis[3]);
            nTomas.setText(dosis[2]);
            xHoras.setText(dosis[1]);
        }
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString()!="" || nPastillas.getText().toString() != "" || nTomas.getText().toString()!= "" || xHoras.getText().toString() != "") {
                    addDosis();
                }
                else{
                    Toast.makeText(context, getString(R.string.noPermitido), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void addDosis(){
        c.addDosis(name.getText().toString(),nPastillas.getText().toString(),nTomas.getText().toString(),xHoras.getText().toString());
        Toast toast = Toast.makeText(this, getString(R.string.Guardar), Toast.LENGTH_LONG);
        toast.show();
        Intent i = new Intent(this, EnsayoSelected.class);
        startActivity(i);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dosis, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id==R.id.action_settings){
            if(name.getText().toString()!="" || nPastillas.getText().toString() != "" || nTomas.getText().toString()!= "" || xHoras.getText().toString() != "") {
                addDosis();
            }
            else{
                Toast.makeText(context, getString(R.string.noPermitido), Toast.LENGTH_LONG).show();
            }
        }
        else if(id == R.id.cancelar){
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
