package androidcbs.chimstel.com.androidcbs;

import java.util.concurrent.ExecutionException;

/**
 * The background service for transmitting sms messages to the backend
 */
public class SMSTransmitterService {

    public static final String APPLICATION_BASE_URL = "chimstest.nftconsult.com";
    public static final String SMS_DELIVERY_URL = "http://chimstest.nftconsult.com/api/shortmessages";

    /**
     *
     * @param smsMessage - the SMS Message to transmit to the server
     * @return - an integer that represents the status of the HttpResponse 200 for success, 409 for conflict and default for anything else
     */
    public Integer SendSMSMessage (SMSMessage smsMessage) {
        SendSMSMessageAsync sendSMSMessageAsync = new SendSMSMessageAsync(smsMessage);
        try{
            return sendSMSMessageAsync.execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
