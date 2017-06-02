package androidcbs.chimstel.com.androidcbs;

/**
 * Created by dove on 2/15/2016.
 */
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;

public class LogMessageAsync extends AsyncTask<URL, Integer, Integer> {

    private LogObject logObject;

    /**
     *
     * @param logObject - the SMS Message to be sent is injected within the constructor of this AsyncTask
     */
    public LogMessageAsync(LogObject logObject){
        this.logObject = logObject;
    }

    /**
     * Before pre-execution - not used for anything
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    /**
     *
     * @param params - the parameter is actually not used (Ignore)
     * @return - an integer representation of the HttpStatus Code on success, null on failure
     */
    @SuppressWarnings("deprecation")
    @Override
    protected Integer doInBackground(URL... params) {

        boolean internetAvailable = internetIsAvailable();
        Log.i("Internet availability", "" + internetAvailable);
        if(internetAvailable){
            HttpURLConnection httpURLConnection= null;
            try {
                if(logObject.getLoggerMsgId()== null || logObject.getNetworktime() == null) return null;
                String [] latitudelongitude=logObject.getLocationn().split(",");
                String param =
                        "logMessageId=" + URLEncoder.encode(logObject.getLoggerMsgId(), "UTF-8") +
                                "&SimCardSerialNumber=" + URLEncoder.encode(logObject.getSerial(), "UTF-8") +
                                "&DeviceImeiNumber=" + URLEncoder.encode(logObject.getIMEI(), "UTF-8") +
                                "&Latitude=" + URLEncoder.encode(String.valueOf(latitudelongitude[0]), "UTF-8") +
                                "&Longitude=" + URLEncoder.encode(String.valueOf(latitudelongitude[1]), "UTF-8") +
                                "&NetworkLogTimeString=" + URLEncoder.encode(logObject.getNetworktime(), "UTF-8");

                URL url = new URL(LogTransmitterService.LOG_DELIVERY_URL);
                httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                httpURLConnection.setRequestProperty("Accept", "application/json");
                httpURLConnection.setFixedLengthStreamingMode(param.getBytes().length);

                httpURLConnection.setUseCaches(false);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);

                PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
                printWriter.write(param);
                printWriter.flush();
                printWriter.close();

                int statusCode = httpURLConnection.getResponseCode();
                switch (statusCode) {
                    case 200: case 409: return statusCode;
                    default: return statusCode;
                }
            }catch(MalformedURLException mex){
                Log.i("MalformedURLException", mex.getMessage());
            }catch(IOException ioex){
                Log.i("IOException", ioex.getMessage());
            }catch(Exception ex) {
                ex.printStackTrace();
            }finally {
                if(httpURLConnection != null) httpURLConnection.disconnect();
            }
        }else{
            Log.i("Internet Availability", "No");
        }
        return 0;
    }

    /**
     *
     * @param integer - Not used for anything (though it might have been useful - Marks the end of execution called after do in background completes)
     */
    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
    }

    /**
     * checks if the device has an active internet connection to the server that we intend to transmit data to
     * @return - true if a connection to the CHIMS web server exists, false otherwise
     */
    private boolean internetIsAvailable(){
        try{
            InetAddress inetAddress = InetAddress.getByName(LogTransmitterService.APPLICATION_BASE_URL);
            if(inetAddress == null){
                Log.i("Inet Address", "InetAddress is null");
                return false;
            }
            Log.i("Inet Address", inetAddress.toString());
            return !inetAddress.toString().equals("");
        }catch(UnknownHostException uhex){
            return false;
        }
    }
}
