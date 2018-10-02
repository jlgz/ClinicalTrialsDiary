package pis2015.ub.com.clinicaltrialsdiary;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;

import pis2015.ub.com.clinicaltrialsdiary.Activities.MainActivity;
import pis2015.ub.com.clinicaltrialsdiary.R;
import pis2015.ub.com.clinicaltrialsdiary.Controlador.Controlador;

/**
 * Created by marc on 28/05/2015.
 */
public class Alarma extends Service{
    private Controlador c;
    private Context context = this;
    private String hora;

    @Override
    public IBinder onBind(Intent arg0)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate()
    {
        // TODO Auto-generated method stub
        super.onCreate();
        c = (Controlador) getApplication();
    }


    @SuppressWarnings("static-access")
    @Override
    public void onStart(Intent intent, int startId)
    {
        super.onStart(intent, startId);

        //hora = c.getHora();


        long[] pattern = new long[]{3000,500,3000};

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setLargeIcon((((BitmapDrawable) getResources()
                                .getDrawable(R.drawable.ic_launcher)).getBitmap()))
                        .setContentTitle(getString(R.string.recordatorioManyana))
                        .setContentText(getString(R.string.visitaMedica))
                        .setTicker(getString(R.string.recordatorio2))
                        .setAutoCancel(true)
                        .setLights(0xFF0000, 3000, 500)
                        .setVibrate(pattern);

        Intent notIntent =
                new Intent(context, MainActivity.class);

        PendingIntent contIntent =
                PendingIntent.getActivity(
                        context, 0, notIntent, 0);

        mBuilder.setContentIntent(contIntent);


        NotificationManager mNotification =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mNotification.notify(11111, mBuilder.build());

        //Destruimos el servicio una vez ejecutado
        //onDestroy();


    }

    @Override
    public void onDestroy()
    {
        // TODO Auto-generated method stub
        super.onDestroy();
    }
}
