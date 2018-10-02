package pis2015.ub.com.clinicaltrialsdiary.Activities;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import pis2015.ub.com.clinicaltrialsdiary.Controlador.Controlador;
import pis2015.ub.com.clinicaltrialsdiary.R;
import pis2015.ub.com.clinicaltrialsdiary.StringSintomaCheck;
import pis2015.ub.com.clinicaltrialsdiary.listSintomaSelectAdapter;


public class Sintomas extends ActionBarActivity {
    private ListView listViewTrial;
    private Context ctx;
    private Controlador c;
    private int size;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sintomas);

        c  = (Controlador) getApplication();
        setTitle(getString(R.string.title_activity_sintomas).toString());
        final Controlador c = (Controlador) getApplication();
        ctx=this;
        List<String> str = c.getListaSintomasDoctor();
        size = str.size();
        List<String> str2 = c.getListaSintomas();
        final List legendList  = new ArrayList<StringSintomaCheck>();
        for(String s: str){
            legendList.add(new StringSintomaCheck(s));
        }
        for(String s: str2){
            for(Object sc: legendList){
               StringSintomaCheck sctmp = (StringSintomaCheck)sc;
               sctmp.isChecked(s);
            }
        }

        listViewTrial = ( ListView ) findViewById( R.id.listView7);
        listViewTrial.setAdapter( new listSintomaSelectAdapter(ctx, R.layout.select_sintoma_content, legendList));
        listViewTrial.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = (String) parent.getItemAtPosition(position);
            }
        });
        Button addSintoma = (Button) findViewById(R.id.button6);
        final EditText newSintoma = (EditText) findViewById(R.id.editText21);
        addSintoma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                size +=1;
                String st = newSintoma.getText().toString();
                c.newSintomaDoctor(st);
                legendList.add(new StringSintomaCheck(st));
                listViewTrial.setAdapter(new listSintomaSelectAdapter(ctx, R.layout.select_sintoma_content, legendList));
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sintomas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.option_fin_sint) {
            ArrayList<String> sintomas = getCheckeds();
            c.setSintomasEnsayo(sintomas);
            Intent i = new Intent(this, EnsayoSelected.class);
            startActivity(i);
            finish();
            return true; //
        }

        return super.onOptionsItemSelected(item);
    }
    private ArrayList<String> getCheckeds(){
        ArrayList<String> sintomas= new ArrayList<>();
        for(int i = 0; i< size;i++) {
            StringSintomaCheck o = (StringSintomaCheck)listViewTrial.getAdapter().getItem(i);
            if(o.getCheck()){
                sintomas.add(o.getString());
            }
        }
        return sintomas;
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
