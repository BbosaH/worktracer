package androidcbs.chimstel.com.androidcbs;

public class SMSMessage {

    public static String SMS_MESSAGE_TABLE = "logged_sms_messages";
    public static String SMS_MESSAGE_CONTENT = "content";
    public static String SMS_MESSAGE_DEVICE_IMEI = "device_imei";
    public static String SMS_MESSAGE_ID = "id";
    public static String SMS_MESSAGE_LATITUDE = "latitude";
    public static String SMS_MESSAGE_LOG_TIME = "log_time";
    public static String SMS_MESSAGE_LONGITUDE = "longitude";
    public static String SMS_MESSAGE_SIM_CARD_SERIAL = "sim_card_serial";
    public static String SMS_MESSAGE_REFERENCE_TIME = "reference_time";

    public String getSMSMessageContent() {
        return SMSMessageContent;
    }

    public void setSMSMessageContent(String SMSMessageContent) {
        this.SMSMessageContent = SMSMessageContent;
    }

    public String getSMSMessageDeviceIMEI() {
        return SMSMessageDeviceIMEI;
    }

    public void setSMSMessageDeviceIMEI(String SMSMessageDeviceIMEI) {
        this.SMSMessageDeviceIMEI = SMSMessageDeviceIMEI;
    }

    public String getSMSMessageId() {
        return SMSMessageId;
    }

    public void setSMSMessageId(String SMSMessageId) {
        this.SMSMessageId = SMSMessageId;
    }

    public String getSMSMessageLogTime() {
        return SMSMessageLogTime;
    }

    public void setSMSMessageLogTime(String SMSMessageLogTime) {
        this.SMSMessageLogTime = SMSMessageLogTime;
    }

    public String getSMSMessageLongitude() {
        return SMSMessageLongitude;
    }

    public void setSMSMessageLongitude(String SMSMessageLongitude) {
        this.SMSMessageLongitude = SMSMessageLongitude;
    }

    public String getSMSMessageSimCardSerial() {
        return SMSMessageSimCardSerial;
    }

    public void setSMSMessageSimCardSerial(String SMSMessageSimCardSerial) {
        this.SMSMessageSimCardSerial = SMSMessageSimCardSerial;
    }

    public String getSMSMessageLatitude() {
        return SMSMessageLatitude;
    }

    public void setSMSMessageLatitude(String SMSMessageLatitude) {
        this.SMSMessageLatitude = SMSMessageLatitude;
    }

    public String getSMSMessageReferenceTime() {
        return SMSMessageReferenceTime;
    }

    public void setSMSMessageReferenceTime(String SMSMessageReferenceTime){
        this.SMSMessageReferenceTime = SMSMessageReferenceTime;
    }

    private String SMSMessageContent;
    private String SMSMessageDeviceIMEI;
    private String SMSMessageId;
    private String SMSMessageLatitude;
    private String SMSMessageLogTime;
    private String SMSMessageLongitude;
    private String SMSMessageSimCardSerial;
    private String SMSMessageReferenceTime;

    @Override
    public String toString() {
        return
                getSMSMessageDeviceIMEI() + " : " + getSMSMessageContent() + " : " + getSMSMessageId() + " : " + getSMSMessageLatitude() + " : " +
                getSMSMessageLogTime() + " : " + getSMSMessageLongitude() + " : " + getSMSMessageSimCardSerial() + " : " + getSMSMessageReferenceTime();
    }
}
