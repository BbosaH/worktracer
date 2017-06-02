package androidcbs.chimstel.com.androidcbs;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SMSReceiver extends BroadcastReceiver {

    @SuppressLint("SimpleDateFormat")
    private static DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    private static final String MessageAddress = "MTNMobMoney";

    public SMSReceiver(){
        super();
    }

    @Override
    public void onReceive(Context context,  Intent intent){

        String referenceDate = dateFormat.format(NetworkTime.getNetworkDateTime(context));
        Location deviceLocation = NetworkLocationService.previousBestLocation;
        if(deviceLocation == null){
            Log.i("SMS Receiver", "Device Location is null");
        }else{
            Log.i("SMS Receiver", "Device Location has a value");
        }

        TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        Collection<SMSMessage> smsMessageCollection = RetrieveSmsMessage(intent).values();
        if(smsMessageCollection.isEmpty()) return;
        SMSMessage smsMessage = (SMSMessage)smsMessageCollection.toArray()[0];
        if(smsMessage != null){
            smsMessage.setSMSMessageDeviceIMEI(telephonyManager.getDeviceId());
            smsMessage.setSMSMessageLatitude(deviceLocation != null ? String.valueOf(deviceLocation.getLatitude()) : String.valueOf(0));
            smsMessage.setSMSMessageLongitude(deviceLocation != null ? String.valueOf(deviceLocation.getLongitude()) : String.valueOf(0));
            smsMessage.setSMSMessageSimCardSerial(telephonyManager.getSimSerialNumber());
            smsMessage.setSMSMessageReferenceTime(referenceDate);

            Log.i("SMS Receiver", smsMessage.toString());

            SMSRepository smsRepository = new SMSRepository(new DatabaseHelper(context));
            smsRepository.insert(smsMessage);

            try{
                SMSTransmitterService smsTransmitterService = new SMSTransmitterService();
                Integer result = smsTransmitterService.SendSMSMessage(smsMessage);
                switch(result){
                    case 200:case 409:
                        smsRepository.delete(smsMessage.getSMSMessageId());
                        break;
                    default: break;
                }
            }catch(Exception e){
                e.printStackTrace();
            }

        }else{
            Log.i("SMS Receiver", "The message is null, probably could not be concatenated or cast");
        }
    }

    /**
     *
     * @param intent - the intent from which to extract the SMS Message
     * @return - a Map consisting of an SMS Message
     *
     */
    private static Map<String, SMSMessage> RetrieveSmsMessage(Intent intent){
        Map<String, SMSMessage> sms = null;
        SmsMessage[] smsMessages;
        Bundle bundle = intent.getExtras();

        if(bundle != null && bundle.containsKey("pdus")){
            Object[] pdus = (Object[]) bundle.get("pdus");

            if(pdus != null){
                int numberOfPdus = pdus.length;
                sms = new HashMap<>(numberOfPdus);
                smsMessages = new SmsMessage[numberOfPdus];

                for(int i = 0; i<numberOfPdus; i++){
                    //noinspection deprecation
                    smsMessages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    String originatingAddress = smsMessages[i].getOriginatingAddress();

                    if(originatingAddress.equalsIgnoreCase(MessageAddress)) {
                        // check if the index with the originating address exists
                        if (!sms.containsKey(originatingAddress)) {

                            // Index with number does not exist
                            // Save string into associative array with originating address as the key
                            Date date = new Date(smsMessages[i].getTimestampMillis());
                            String dateSent = dateFormat.format(date);
                            SMSMessage smsMessage = new SMSMessage();
                            smsMessage.setSMSMessageContent(smsMessages[i].getMessageBody());
                            smsMessage.setSMSMessageId(UUID.randomUUID().toString());
                            smsMessage.setSMSMessageLogTime(dateSent);
                            sms.put(originatingAddress, smsMessage);
                        } else {
                            // just add part of the current PDU
                            SMSMessage previousSMSMessage = sms.get(originatingAddress);
                            String messagePart = previousSMSMessage.getSMSMessageContent() + smsMessages[i].getMessageBody();
                            previousSMSMessage.setSMSMessageContent(messagePart);
                            sms.put(originatingAddress, previousSMSMessage);
                        }
                    }else{
                        Log.i("SMS Receiver", "Un-required SMS");
                    }
                }
            }
        }
        return sms;
    }
}
