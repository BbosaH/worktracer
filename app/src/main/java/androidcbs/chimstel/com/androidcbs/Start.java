package androidcbs.chimstel.com.androidcbs;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.widget.TextView;
import android.util.Log;
import android.content.pm.PackageManager;
import  android.Manifest;

public class Start extends ActionBarActivity {
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    String provider;
    protected String latitude,longitude;
    protected boolean gps_enabled,network_enabled;
    TextView txtLat;
    String lat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Intent smsTransmitterIntent = new Intent(this, PeriodicSMSTransmitterService.class);
        this.startService(smsTransmitterIntent);



    }







}
