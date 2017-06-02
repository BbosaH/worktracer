package androidcbs.chimstel.com.androidcbs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class PeriodicSMSBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("Periodic Broadcast Rec", "Periodic broadcast receiver initiated");
        Intent service = new Intent(context, PeriodicSMSTransmitterService.class);
        context.startService(service);
    }
}
