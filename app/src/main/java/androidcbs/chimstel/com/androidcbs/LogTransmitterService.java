package androidcbs.chimstel.com.androidcbs;

import java.util.concurrent.ExecutionException;

/**
 * Created by dove on 2/15/2016.
 */
public class LogTransmitterService {
    public static final String APPLICATION_BASE_URL = "chimstest.nftconsult.com";
    public static final String LOG_DELIVERY_URL = "http://chimstest.nftconsult.com/api/shortmessages";

    /**
     *
     * @param ; logObject - the log Message to transmit to the server
     * @return - an integer that represents the status of the HttpResponse 200 for success, 409 for conflict and default for anything else
     */
    public Integer sendLogObject (LogObject logObject) {
        LogMessageAsync sendLogMessageAsync = new LogMessageAsync(logObject);
        try{
            return sendLogMessageAsync.execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
