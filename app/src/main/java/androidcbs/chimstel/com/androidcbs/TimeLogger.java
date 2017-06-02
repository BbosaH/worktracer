package androidcbs.chimstel.com.androidcbs;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import android.content.Context;
import android.provider.Settings;

import java.util.Date;
import android.location.LocationListener;
import android.location.Location;
import android.os.Bundle;
import android.location.LocationManager;
import android.telephony.TelephonyManager;

/**
 * Created by dove on 2/15/2016.
 */
public class TimeLogger  {
    LocationManager locationManager;
    TelephonyManager telephonyManager;

    private  DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    //object properties
    private Date networktime;
    private String locationn;
    private String IMEI;
    private String serial;

    public static TimeLogger timeLogger = TimeLogger.getInstance();

    private TimeLogger(){

    }
    public Date getNetworkTime(Context context){
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
    public String getCurrentLocation(){
       Location phonelocation = NetworkLocationService.previousBestLocation;
        double latitude =phonelocation.getLatitude();
        double longitude =phonelocation.getLongitude();
        String loc = latitude + "," + longitude;
        return loc;

    }

    public String getphoneIMEI (Context context )
    {
         telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        String phoneIMEI=telephonyManager.getDeviceId();
        return phoneIMEI;
    }
    public String getSimSerial(Context context)
    {
        telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        String serial=telephonyManager.getSimSerialNumber();
        return serial;
    }

    public static TimeLogger getInstance(){
        return new TimeLogger();
    }

    public void makelog(Context context)
    {

        LogObject logObject = new LogObject();
        if(logObject != null) {
            logObject.setIMEI(getphoneIMEI(context));
            logObject.setLocationn(getCurrentLocation());
            logObject.setSerial(getSimSerial(context));
            logObject.setNetworktime(getNetworkTime(context).toString());
        }
        LoggerRepository loggerRepository = new LoggerRepository(new DatabaseHelper(context));

            loggerRepository.insert(logObject);
            try{
                LogTransmitterService logTransmitterService = new LogTransmitterService();
                Integer result = logTransmitterService.sendLogObject(logObject);
                switch(result){
                    case 200:case 409:
                        loggerRepository.delete(logObject.getLoggerMsgId());
                        break;
                    default: break;
                }
            }catch(Exception e){
                e.printStackTrace();
            }


    }



}
