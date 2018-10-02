package pis2015.ub.com.clinicaltrialsdiary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by user on 16/05/2015.
 */
public class PacienteAdapter2 extends ArrayAdapter<listTrialsPaciente> {
    private int resource;
    private LayoutInflater inflater;
    private Context context;

    public PacienteAdapter2(Context ctx, int resourceId, List<listTrialsPaciente> objects){
        super( ctx, resourceId, objects);
        resource = resourceId;
        inflater = LayoutInflater.from( ctx );
        context = ctx;


    }

    public View getView ( int position, View convertView, ViewGroup parent ) {
        convertView = inflater.inflate( resource, null );
        final listTrialsPaciente Legend = getItem( position );
        CheckBox legendName = (CheckBox) convertView.findViewById(R.id.checkBox5);
        if(Legend.getCheck()){
            legendName.setChecked(Legend.getCheck());
        }
        legendName.setText(Legend.getName());
        legendName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Legend.check();
            }
        });
        TextView legendBorn = (TextView) convertView.findViewById(R.id.textView2);
        legendBorn.setText(Legend.getReporte());

        TextView textView = (TextView) convertView.findViewById(R.id.textView30);
        textView.setText(Legend.getEdad());



        return convertView;
    }
}
