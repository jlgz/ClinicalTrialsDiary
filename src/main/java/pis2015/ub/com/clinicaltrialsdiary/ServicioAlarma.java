package pis2015.ub.com.clinicaltrialsdiary;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import pis2015.ub.com.clinicaltrialsdiary.Alarma;

/**
 * Created by marc on 28/05/2015.
 */
public class ServicioAlarma extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent){
        Intent service1 = new Intent(context, Alarma.class);
        context.startService(service1);

    }
}
