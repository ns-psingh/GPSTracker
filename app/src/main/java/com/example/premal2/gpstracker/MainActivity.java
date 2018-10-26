package com.example.premal2.gpstracker;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements LocationListener {

    public static double latitude;
    public static double longitude;
   // private TextView lat;
  //  private TextView lon;
    private LocationManager locationManager;
    private ProgressBar progressBar;
    private Button send;
    private EditText ipadd;
    private String urllip;
    private Location location;
    private Button mapbtn;
    @Override
    public void onProviderEnabled(String s) {

    }
    @Override
    public void onStatusChanged(String s, int i, Bundle bundle)
    {

    }
    @Override
    public void onProviderDisabled(String s)
    {

    }
    @Override
    public void onLocationChanged(Location location)
    {
        latitude=location.getLatitude();
        longitude=location.getLongitude();
        Toast.makeText(getApplicationContext(),"location send "+latitude+" "+longitude,Toast.LENGTH_SHORT).show();
        Log.d("e",latitude+"");
        Log.d("e",longitude+"");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapbtn=(Button) findViewById(R.id.mapbtn);
        mapbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLocationChanged(location);
                startActivity(new Intent(MainActivity.this,Map.class));
            }
        });
      //  onLocationChanged(location);

        progressBar=(ProgressBar) findViewById(R.id.progressBar);
        send=(Button) findViewById(R.id.sendbtn);
        ipadd=(EditText) findViewById(R.id.ipaddress);
        progressBar.setVisibility(View.INVISIBLE);
        //lat.setText("hello");
        locationManager =(LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }
        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Log.d("e","hello");
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("e","works");
                progressBar.setVisibility(View.VISIBLE);
                Log.d("e","working");
                onLocationChanged(location);
                Log.d("e","working2");
                progressBar.setVisibility(View.INVISIBLE);
                new tempval().execute();
            }
        });

    }
    public class tempval extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        Element x;
        @Override
        protected Void doInBackground(Void... voids) {
            String url=ipadd.getText().toString();
            url="http://"+url+"/index.php?lat=43&lon=89";
            //url="http://www.google.com";
            Log.d("e",url);
            try {

                Jsoup.connect(url).get();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressBar.setVisibility(View.INVISIBLE);
            super.onPostExecute(aVoid);
        }
    }
}
