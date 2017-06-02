package androidcbs.chimstel.com.androidcbs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "sms_db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_TABLE_SMS_MESSAGE = "CREATE TABLE " + SMSMessage.SMS_MESSAGE_TABLE + "("
                + SMSMessage.SMS_MESSAGE_ID + " VARCHAR(40) PRIMARY KEY, "
                + SMSMessage.SMS_MESSAGE_CONTENT + " VARCHAR (200) NOT NULL, "
                + SMSMessage.SMS_MESSAGE_DEVICE_IMEI + " VARCHAR (20) NOT NULL, "
                + SMSMessage.SMS_MESSAGE_SIM_CARD_SERIAL + " VARCHAR (30) NOT NULL, "
                + SMSMessage.SMS_MESSAGE_LATITUDE + " VARCHAR (30) NULL, "
                + SMSMessage.SMS_MESSAGE_LONGITUDE + " VARCHAR (30) NULL, "
                + SMSMessage.SMS_MESSAGE_LOG_TIME + " VARCHAR (40) NOT NULL,"
                + SMSMessage.SMS_MESSAGE_REFERENCE_TIME + " VARCHAR (40) NOT NULL)";
        db.execSQL(CREATE_TABLE_SMS_MESSAGE);

        String CREATE_TABLE_LOG_MESSAGE = "CREATE TABLE " + LogObject.LOGGER_TABLE + "("
                + LogObject.LOGGER_MESSAGE_ID + " VARCHAR(40) PRIMARY KEY, "
                + LogObject.LOGGER_MESSAGE_NETWORKTIME + " VARCHAR (200) NOT NULL, "
                + LogObject.LOGGER_MESSAGE_SIM_SERIAL + " VARCHAR (20) NOT NULL, "
                + LogObject.LOGGER_MESSAGE_LOCATION + " VARCHAR (30) NOT NULL, "
                + LogObject.LOGGER_MESSAGE_DEVICE_IMEI + " VARCHAR (40) NOT NULL)";
        db.execSQL(CREATE_TABLE_LOG_MESSAGE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        // Drop older table if it existed, all data will be gone
        String DELETE_TABLE_SMS_MESSAGE = "DROP TABLE IF EXISTS " + SMSMessage.SMS_MESSAGE_TABLE;
        String DELETE_TABLE_LOG_MESSAGE = "DROP TABLE IF EXISTS " + LogObject.LOGGER_TABLE ;
        db.execSQL(DELETE_TABLE_SMS_MESSAGE);
        db.execSQL(DELETE_TABLE_LOG_MESSAGE);
    }
}
