package androidcbs.chimstel.com.androidcbs;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.logging.Logger;

/**
 * Created by dove on 2/15/2016.
 */
public class LoggerRepository {
    private DatabaseHelper databaseHelper;
    public LoggerRepository(DatabaseHelper databaseHelper){
        this.databaseHelper = databaseHelper;
    }


    public int insert(LogObject logObject)
    {
        int returnVal = Integer.MIN_VALUE;
        try{
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(LogObject.LOGGER_MESSAGE_DEVICE_IMEI, logObject.getIMEI());
            values.put(LogObject.LOGGER_MESSAGE_SIM_SERIAL, logObject.getSerial());
            values.put(LogObject.LOGGER_MESSAGE_LOCATION,logObject.getLocationn());
            values.put(LogObject.LOGGER_MESSAGE_NETWORKTIME, logObject.getNetworktime());

            long logMsgId = db.insert(LogObject.LOGGER_TABLE, null, values);
            db.close();
            returnVal = (int) logMsgId;
        }catch(Exception e){
            e.printStackTrace();
        }
        return returnVal;

    }

    public void delete(String logMsgId) throws Exception {
        try{
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            String sql = "DELETE FROM " + LogObject.LOGGER_TABLE + " WHERE " +LogObject.LOGGER_MESSAGE_ID  + "= ?";
            db.execSQL(sql, new String[]{logMsgId});
            db.close();
            Log.i("LOG Repository", "LOG Deleted");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
