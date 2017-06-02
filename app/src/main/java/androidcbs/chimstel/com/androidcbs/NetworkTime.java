package androidcbs.chimstel.com.androidcbs;

import android.content.Context;
import android.provider.Settings;

import java.util.Date;

/**
 * Created by edward.kigongo on 12/9/2015.
 */
public class NetworkTime {
    public static Date getNetworkDateTime(Context context){
        Date date = new Date();

        try{
            String timeSettings = android.provider.Settings.System.getString(context.getContentResolver(), Settings.System.AUTO_TIME);
            if(timeSettings.contentEquals("0"))
                android.provider.Settings.System.putString(context.getContentResolver(), Settings.System.AUTO_TIME, "1");

            Date now = new Date(System.currentTimeMillis());
            return now;
        }catch(Exception e){
            e.printStackTrace();
        }

        return date;
    }
}
