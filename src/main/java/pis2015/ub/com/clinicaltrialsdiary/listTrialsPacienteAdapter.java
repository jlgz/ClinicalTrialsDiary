package pis2015.ub.com.clinicaltrialsdiary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by user on 01/04/2015.
 */
public class listTrialsPacienteAdapter extends ArrayAdapter<listTrialsPaciente> {
    private int resource;
    private LayoutInflater inflater;
    private Context context;
    public listTrialsPacienteAdapter(Context ctx, int resourceId, List<listTrialsPaciente> objects){
        super( ctx, resourceId, objects);
        resource = resourceId;
        inflater = LayoutInflater.from( ctx );
        context = ctx;

    }

    public View getView ( int position, View convertView, ViewGroup parent ) {
        convertView = (RelativeLayout) inflater.inflate( resource, null );
        listTrialsPaciente Legend = getItem( position );
        TextView legendName = (TextView) convertView.findViewById(R.id.legendName);
        legendName.setText(Legend.getName());

        TextView legendBorn = (TextView) convertView.findViewById(R.id.legendBorn);
        legendBorn.setText(Legend.getReporte());

        TextView textView = (TextView) convertView.findViewById(R.id.textView3);
        textView.setText(Legend.getEdad());

        //falta el id


        return convertView;
    }
}
