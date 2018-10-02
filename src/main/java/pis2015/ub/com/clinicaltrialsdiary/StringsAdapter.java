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
 * Created by jguillza13.alumnes on 28/05/15.
 */
public class StringsAdapter extends ArrayAdapter<String> {
    private int resource;
    private LayoutInflater inflater;
    private Context context;
    public StringsAdapter(Context ctx, int resourceId, List<String> objects){
        super( ctx, resourceId, objects);
        resource = resourceId;
        inflater = LayoutInflater.from( ctx );
        context = ctx;

    }

    public View getView ( int position, View convertView, ViewGroup parent ) {
        convertView =  inflater.inflate( resource, null );
        String Legend = getItem( position );
        TextView legendName = (TextView) convertView.findViewById(R.id.textView31);
        legendName.setText(Legend);


        return convertView;
    }
}
