package androidcbs.chimstel.com.androidcbs;

import android.view.LayoutInflater;

import java.util.Date;

/**
 * Created by dove on 2/15/2016.
 */
public class LogObject {
    public static String LOGGER_TABLE="logger";
    //object properties
    private String networktime;
    private String locationn;
    private String IMEI;
    private String serial;
    private String loggerMsgId;

    //columns
    public static String LOGGER_MESSAGE_ID = "message_id";
    public static String LOGGER_MESSAGE_LOCATION = "location";
    public static String LOGGER_MESSAGE_DEVICE_IMEI = "device_imei";
    public static String LOGGER_MESSAGE_SIM_SERIAL = "sim_serial";
    public static String LOGGER_MESSAGE_NETWORKTIME = "latitude";

    public String getNetworktime()
    {
        return networktime;
    }

    public void setNetworktime(String networktime){
        this.networktime = networktime;
    }

    public String getIMEI()
    {
        return  IMEI;
    }
    public void setIMEI(String IMEI)
    {
        this.IMEI =IMEI;
    }

    public String getSerial()
    {
        return  serial;
    }
    public void setSerial(String serial)
    {
        this.serial = serial;
    }

    public String getLocationn()
    {
        return locationn;
    }
    public void setLocationn(String locationn)
    {
        this.locationn=locationn;
    }

    public String getLoggerMsgId()
    {
        return this.loggerMsgId;
    }
    public void setLoggerMsgId(String loggerMsgId)
    {
        this.loggerMsgId=loggerMsgId;
    }


}
