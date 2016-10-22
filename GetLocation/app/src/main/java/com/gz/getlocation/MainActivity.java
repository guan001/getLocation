package com.gz.getlocation;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.media.audiofx.BassBoost;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends Activity {
    TextView longitude;
    TextView latitude;
    TextView high,gc;
    RadioGroup radioGroup;
    RadioButton gps,net;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final long minTime = 5000;
        final float minDistance = 5;
        longitude = (TextView) findViewById(R.id.textView1);
        latitude = (TextView) findViewById(R.id.textView2);
        high = (TextView) findViewById(R.id.TextView3);
        radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
        gps = (RadioButton) findViewById(R.id.gps);
        net = (RadioButton) findViewById(R.id.net);
//        gc = (TextView) findViewById(R.id.gc);
        final LocationManager locationManager = (LocationManager)
                getSystemService(LOCATION_SERVICE);
        final LocationProvider locationProvider1 = locationManager.getProvider
                (LocationManager.GPS_PROVIDER) ;
        LocationProvider locationProvider2 = locationManager.getProvider
                (LocationManager.NETWORK_PROVIDER) ;
       final LocationListener locationlistener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
//                Log.e("调用方法","位置改变");
                    longitude.setText("The longitude is "+location.getLongitude());
                latitude.setText("The latitude is "+location.getLatitude());
                high.setText("The high is "+location.getAltitude());
//                String addressline = null;
                Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
//                List<Address> locationlist = null;
//                try{
//                    locationlist = geocoder.getFromLocation(location.getLatitude(),location.getLongitude()
//                    ,1);
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//                Address address = locationlist.get(0);
//                gc.setText("you are in "+address.getCountryName()+" "+address.getLocality()+"."
//                );
//
//                for (int i =0;address.getAddressLine(i)!=null;i++){
//                    addressline = address.getAddressLine(i);
//                }
//                gc.append(addressline);
//
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
//                Log.e("调用方法","status改变");
            }

            @Override
            public void onProviderEnabled(String s) {
//                Log.e("调用方法","provider启用");
            }

            @Override
            public void onProviderDisabled(String s) {
//                Log.e("调用方法","provider失效");
            }
        };
        if (locationManager.getProvider(LocationManager.GPS_PROVIDER)!=null||
                locationManager
                .getProvider(LocationManager.NETWORK_PROVIDER)!=null){
                    radioGroup.setOnCheckedChangeListener(new RadioGroup.
                            OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup radioGroup, int i) {
                            int RadioButtonID = radioGroup.getCheckedRadioButtonId();
                            RadioButton rb = (RadioButton) findViewById(RadioButtonID);
                            if (rb.getId()==R.id.gps){

                                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                                        minTime,minDistance,locationlistener);
//                                Log.d("GPS","sUCCEDD");
                            }else if (rb.getId()==R.id.net){

                                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                                        minTime,minDistance,locationlistener);
//                                Log.d("NET","sUCCEDD");
                            }
                        }
                    });
        }else {
            Toast.makeText(this,
                    "Please open the Location-based services", Toast.LENGTH_SHORT).show();
            Intent i =new Intent();
            i.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(i);
        }
    }
}
