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
 * Created by jguillza13.alumnes on 04/05/15.
 */
public class listSintomaSelectAdapter extends ArrayAdapter<StringSintomaCheck> {
    private int resource;
    private LayoutInflater inflater;
    private Context context;
    public listSintomaSelectAdapter(Context ctx, int resourceId, List<StringSintomaCheck> objects){
        super( ctx, resourceId, objects);
        resource = resourceId;
        inflater = LayoutInflater.from( ctx );
        context = ctx;

    }
    public View getView ( final int position, View convertView, ViewGroup parent ) {
        convertView =  inflater.inflate( resource, null );
        String Legend = getItem( position ).getString();
        CheckBox legendName = (CheckBox) convertView.findViewById(R.id.checkBox4);
        legendName.setChecked(getItem(position).getCheck());
        legendName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getItem(position).setCheck();
            }
        });
        legendName.setText(Legend);
        return convertView;
    }
}
