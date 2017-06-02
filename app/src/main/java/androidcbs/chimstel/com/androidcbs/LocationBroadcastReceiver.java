package androidcbs.chimstel.com.androidcbs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class LocationBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent locationIntent  = new Intent(context, NetworkLocationService.class);
        context.startService(locationIntent);
    }
}
