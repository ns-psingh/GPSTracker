package com.example.premal2.gpstracker;

import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Map extends AppCompatActivity implements OnMapReadyCallback {
    private MapView mapView;
    private GoogleMap gmap;
    private TextToSpeech tts;
    private int result;
    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("e","entered");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        tts=new TextToSpeech(Map.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(i==TextToSpeech.SUCCESS)
                    result=tts.setLanguage(Locale.ENGLISH);
            }
        });
        speakOut();
        Bundle mapViewBundle=null;
        if(savedInstanceState!=null)
        {
            mapViewBundle=savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }
        mapView=findViewById(R.id.mapView);
        mapView.setVisibility(View.INVISIBLE);
        ProgressBar x=findViewById(R.id.progressBar2);
        x.setVisibility(View.VISIBLE);
        try
        {
            Thread.sleep(1000);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
        x.setVisibility(View.INVISIBLE);
        mapView.setVisibility(View.VISIBLE);
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);
    }
    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        Bundle mapViewBundle= outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if(mapViewBundle==null)
        {
            mapViewBundle=new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY,mapViewBundle);
        }
        mapView.onSaveInstanceState(mapViewBundle);
    }
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }
    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }
    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
    private void speakOut()
    {
        Log.d("e","working all fine");
        tts.speak("5 Rescuers Nearby",TextToSpeech.QUEUE_FLUSH,null);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap=googleMap;
        gmap.setMinZoomPreference(12);
        UiSettings uiSettings=gmap.getUiSettings();
        uiSettings.setIndoorLevelPickerEnabled(true);
        uiSettings.setMyLocationButtonEnabled(true);
        uiSettings.setMapToolbarEnabled(true);
        uiSettings.setCompassEnabled(true);
        uiSettings.setZoomControlsEnabled(true);
        LatLng ny=new LatLng(MainActivity.latitude,MainActivity.longitude);

        LatLng ny1=new LatLng(MainActivity.latitude+0.01,MainActivity.longitude+0.01);

        LatLng ny2=new LatLng(MainActivity.latitude+0.01,MainActivity.longitude-0.01);

        LatLng ny3=new LatLng(MainActivity.latitude-0.01,MainActivity.longitude+0.01);

        LatLng ny4=new LatLng(MainActivity.latitude-0.01,MainActivity.longitude-0.01);
        gmap.addMarker(new MarkerOptions().position(ny).title("C"));
        gmap.addMarker(new MarkerOptions().position(ny1).title("C"));
        gmap.addMarker(new MarkerOptions().position(ny2).title("C"));
        gmap.addMarker(new MarkerOptions().position(ny3).title("C"));
        gmap.addMarker(new MarkerOptions().position(ny4).title("C"));
        gmap.moveCamera(CameraUpdateFactory.newLatLng(ny));
    }
}
