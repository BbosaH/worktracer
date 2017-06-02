package androidcbs.chimstel.com.androidcbs;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * The repository - an abstraction of the underlying data-source that holds our SMS messages
 */
public class SMSRepository  {

    private DatabaseHelper databaseHelper;

    /**
     *
     * @param databaseHelper - a wrapper around the database (more like an access helper)
     */
    public SMSRepository(DatabaseHelper databaseHelper){
        this.databaseHelper = databaseHelper;
    }

    /**
     *
     * @param smsMessage - an SMS to be inserted into the local repository
     * @return - an Integer that indicates the status of the application
     */
    public int insert(SMSMessage smsMessage) {
        int returnVal = Integer.MIN_VALUE;
        try{
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(SMSMessage.SMS_MESSAGE_CONTENT, smsMessage.getSMSMessageContent());
            values.put(SMSMessage.SMS_MESSAGE_DEVICE_IMEI, smsMessage.getSMSMessageDeviceIMEI());
            values.put(SMSMessage.SMS_MESSAGE_ID, smsMessage.getSMSMessageId());
            values.put(SMSMessage.SMS_MESSAGE_LATITUDE, smsMessage.getSMSMessageLatitude());
            values.put(SMSMessage.SMS_MESSAGE_LOG_TIME, smsMessage.getSMSMessageLogTime());
            values.put(SMSMessage.SMS_MESSAGE_LONGITUDE, smsMessage.getSMSMessageLatitude());
            values.put(SMSMessage.SMS_MESSAGE_SIM_CARD_SERIAL, smsMessage.getSMSMessageSimCardSerial());
            values.put(SMSMessage.SMS_MESSAGE_REFERENCE_TIME, smsMessage.getSMSMessageReferenceTime());
            long smsMessageId = db.insert(SMSMessage.SMS_MESSAGE_TABLE, null, values);
            db.close();
            returnVal = (int) smsMessageId;
        }catch(Exception e){
            e.printStackTrace();
        }
        return returnVal;
    }

    /**
     *
     * @param smsMessageId - the unique identifier of the SMS to be deleted from the repository
     * @throws Exception - an Exception in case anything goes wrong
     */
    public void delete(String smsMessageId) throws Exception {
        try{
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            String sql = "DELETE FROM " + SMSMessage.SMS_MESSAGE_TABLE + " WHERE " + SMSMessage.SMS_MESSAGE_ID  + "= ?";
            db.execSQL(sql, new String[]{smsMessageId});
            db.close();
            Log.i("SMS Repository", "SMS Deleted");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     *
     * @return - a list of SMS Messages that are saved in the repository at any given time
     */
    public List<SMSMessage> getAllSMSMessages(){
        List<SMSMessage> smsMessageList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + SMSMessage.SMS_MESSAGE_TABLE;
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{
                SMSMessage smsMessage = new SMSMessage();
                smsMessage.setSMSMessageId(cursor.getString(0));
                smsMessage.setSMSMessageContent(cursor.getString(1));
                smsMessage.setSMSMessageDeviceIMEI(cursor.getString(2));
                smsMessage.setSMSMessageSimCardSerial(cursor.getString(3));
                smsMessage.setSMSMessageLatitude(cursor.getString(4));
                smsMessage.setSMSMessageLongitude(cursor.getString(5));
                smsMessage.setSMSMessageLogTime(cursor.getString(6));
                smsMessage.setSMSMessageReferenceTime(cursor.getString(7));
                smsMessageList.add(smsMessage);
            }while(cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return smsMessageList;
    }
}
