package pis2015.ub.com.clinicaltrialsdiary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import pis2015.ub.com.clinicaltrialsdiary.Modelo.Sintoma;

/**
 * Created by jguillza13.alumnes on 04/05/15.
 */
public class ListSintomasAdapter extends ArrayAdapter<ListSintoma> {
    private int resource;
    private LayoutInflater inflater;
    private Context context;
    public ListSintomasAdapter(Context ctx, int resourceId, List<ListSintoma> objects){
        super( ctx, resourceId, objects);
        resource = resourceId;
        inflater = LayoutInflater.from( ctx );
        context = ctx;

    }

    public View getView ( int position, View convertView, ViewGroup parent ) {
        convertView = (RelativeLayout) inflater.inflate( resource, null );
        ListSintoma Legend = getItem( position );
        TextView legendName = (TextView) convertView.findViewById(R.id.legendName);
        legendName.setText(Legend.getName());

        TextView legendBorn = (TextView) convertView.findViewById(R.id.legendBorn);
        legendBorn.setText(Legend.getData());

        TextView textView = (TextView) convertView.findViewById(R.id.textView3);
        textView.setText(Legend.getFecha());

        return convertView;
    }
}
