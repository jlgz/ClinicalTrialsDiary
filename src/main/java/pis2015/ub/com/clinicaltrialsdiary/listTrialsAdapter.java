package pis2015.ub.com.clinicaltrialsdiary;

import java.util.List;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class listTrialsAdapter extends ArrayAdapter<listTrials>{
    private int resource;
    private LayoutInflater inflater;
    private Context context;
    public listTrialsAdapter(Context ctx, int resourceId, List<listTrials> objects){
        super( ctx, resourceId, objects);
        resource = resourceId;
        inflater = LayoutInflater.from( ctx );
        context = ctx;

    }

    public View getView ( int position, View convertView, ViewGroup parent ) {
        convertView = ( RelativeLayout ) inflater.inflate( resource, null );
        listTrials Legend = getItem( position );
        TextView legendName = (TextView) convertView.findViewById(R.id.legendName);
        legendName.setText(Legend.getName());

        TextView legendBorn = (TextView) convertView.findViewById(R.id.legendBorn);
        legendBorn.setText(Legend.getData());

        TextView textView = (TextView) convertView.findViewById(R.id.textView3);
        textView.setText(Legend.getPacientes());

        return convertView;
    }
}
