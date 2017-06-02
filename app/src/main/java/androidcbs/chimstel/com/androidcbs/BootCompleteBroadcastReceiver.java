package androidcbs.chimstel.com.androidcbs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Receives the broadcast that a device has finished booting and starts the background service for transmitting sms messages
 * received by the device
 */
public class BootCompleteBroadcastReceiver extends BroadcastReceiver {

    // restart service after every 5 seconds
    private static final long REPEAT_TIME = 1000 * 5;

    private Timer timer;
    private TimerTask timerTask;

    public BootCompleteBroadcastReceiver() {
    }

    @Override
    public void onReceive(final Context context, Intent intent) {

        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {


            final Context mycontext = context;
            timerTask = new TimerTask() {
                public void run() {
                    //do something and:
                   String [] hourminsec=TimeLogger.getInstance().getNetworkTime(context).toString().split(":");
                    double hour =Double.parseDouble(hourminsec[0])+(Double.parseDouble(hourminsec[1]))/60;
                    double morninghour =8+(15.0/60);
                    double eveninghour = 17.0;
                    double nighthour = 20.0;
                    if(hour >=morninghour-(1/60) || hour <= morninghour+(1/60) )
                    {
                        TimeLogger.getInstance().makelog(mycontext);
                    }else if (hour >=eveninghour-(1/60) || hour <= eveninghour+(1/60))
                    {
                        TimeLogger.getInstance().makelog(mycontext);
                    }else if (hour >=nighthour-(1/60) || hour <= nighthour+(1/60))
                    {
                        TimeLogger.getInstance().makelog(mycontext);
                    }else{

                    }



                }

            };
            timer = new Timer();
            timer.schedule(timerTask, 60 * 1000);

            Intent smsTransmitterIntent = new Intent(context, PeriodicSMSTransmitterService.class);
            context.startService(smsTransmitterIntent);

        }
    }

}
