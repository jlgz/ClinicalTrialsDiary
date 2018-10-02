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
 * Created by alfred on 31/03/2015.
 */
public class listTrialsCuestionarioAdapter extends ArrayAdapter<listTrialsCuestionario> {
    private int resource;
    private LayoutInflater inflater;
    private Context context;
    public listTrialsCuestionarioAdapter(Context ctx, int resourceId, List<listTrialsCuestionario> objects){
        super( ctx, resourceId, objects);
        resource = resourceId;
        inflater = LayoutInflater.from( ctx );
        context = ctx;

    }

    public View getView ( int position, View convertView, ViewGroup parent ) {
        convertView = (RelativeLayout) inflater.inflate( resource, null );
        listTrialsCuestionario Legend = getItem( position );
        TextView legendName = (TextView) convertView.findViewById(R.id.legendName);
        legendName.setText(Legend.getName());

        TextView legendBorn = (TextView) convertView.findViewById(R.id.legendBorn);
        legendBorn.setText(Legend.getData());

        return convertView;
    }
}
