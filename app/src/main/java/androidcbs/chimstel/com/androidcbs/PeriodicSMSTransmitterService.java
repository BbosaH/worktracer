package androidcbs.chimstel.com.androidcbs;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.List;

public class PeriodicSMSTransmitterService extends Service {

    private List<SMSMessage> smsMessageList;
    private SMSRepository smsRepository;

    @Override
    public IBinder onBind(Intent intent) { throw new UnsupportedOperationException("Not yet implemented"); }

    @Override
    public void onCreate() {
        super.onCreate();
        if(smsRepository == null) smsRepository = new SMSRepository(new DatabaseHelper(getBaseContext()));
        smsMessageList = smsRepository.getAllSMSMessages();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        final android.os.Handler handler = new android.os.Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.i("Period SMS Transmitter", "Sms transmitter onHandle Intent - runnable section");
                SyncData();
                handler.postDelayed(this, 5 * 60 * 1000);
            }
        }, 60 * 1000);
        return START_STICKY;
    }

    /**
     * Synchronizes the data with the backend in the case that we have any un-logged transactions' data
     */
    private void SyncData(){
        try{
            // check if we have anything to transmit
            if(smsMessageList.isEmpty()) return;

            // read an sms from the list and transmit it
            for(SMSMessage sm : smsMessageList){
                Log.i("Unsent SMS", sm.toString());
                Integer result = new SMSTransmitterService().SendSMSMessage(sm);
                switch(result){
                    case 200:case 409:
                        smsRepository.delete(sm.getSMSMessageId());
                        break;
                    default: break;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            smsMessageList = smsRepository.getAllSMSMessages();
        }
    }
}
