package pkc.trafficquest.sccapstone.trafficquest;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private static final int ERROR_DIALOG_REQUEST = 9001;
    GoogleMap mMap;
    private static final double
            COLUMBIA_LAT = 33.99882,
            COLUMBIA_LNG = -81.04537;
    private GoogleApiClient client;
    private ArrayList<Accidents> accidents; // list of accidents
    private ArrayList<String> names; // String version of accident list
    private double lat; // latitude
    private double lng; // longitude

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Intent mapIntent = getIntent(); // get the intent
        Bundle data = mapIntent.getExtras(); // bundle to receive data from the main activity
        if (mapIntent.getExtras() != null){ // if the extras is not null, instantiate the accidents and names lists
            accidents = data.getParcelableArrayList("accidentsList"); // gets the requested list of accidents from the main activity
            names = mapIntent.getStringArrayListExtra("stringAccidentList"); // gets a String version of the requested accident list
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.Maps);
        mapFragment.getMapAsync(this);
    }

    // Obtain the SupportMapFragment and get notified when the map is ready to be used.
       /* client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        if (googleServiceAvailable()) {
            Toast.makeText(this, "Perfect!", Toast.LENGTH_LONG).show();
            setContentView(R.layout.activity_maps);
            initMap();
        } else {
            // No Google Maps Layout
        }

        /*if (servicesOK()) {
            setContentView(R.layout.activity_maps);
            if (initMap()) {
                Toast.makeText(this, "Ready to Map!", Toast.LENGTH_SHORT).show();
                gotoLocation(COLUMBIA_LAT, COLUMBIA_LNG, 10);
            } else {
                Toast.makeText(this, "Map no connected!", Toast.LENGTH_SHORT).show();
            }


        }*/


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        // loop through list, get coordinates, and place markers where accidents are
        if (accidents != null) {
            for (int i = 0; i < accidents.size(); i++) {
                Accidents accident = accidents.get(i); // individual accident
                // initialize coordinates from the requested accident list
                lat = accident.getPoint().getCoordinates().get(0); // get latitude
                lng = accident.getPoint().getCoordinates().get(1); // get longitude
                LatLng searchLatLng = new LatLng(lat, lng);
                mMap.addMarker(new MarkerOptions() // add markers from requested list
                        .position(searchLatLng)
                        .title("Type: " + interpretType2(accident)) // title of the marker is the type of accident
                        .snippet(accident.getDescription() + "\n" // print description of the accident
                                + "at " + lat + ", " + lng + "\n" // print latitude and longitude
                                + "Start Time: " + accident.getStart() + "\n" // print the start time of the accident
                                + "End Time: " + accident.getEnd() + "\n" // print the end time of the accident
                                + "Severity: " + accident.getSeverity()) // print the severity of the accident
                );

            }
        }
    }

    /*
    interprets what each type code means
    @param acc The Accidents object to get the type code from
    @return the interpreted type of accident
     */
    public String interpretType2(Accidents acc){
        int type = acc.getType2(); // the type code from the accident
        String typeString; // the value to return
        switch (type) {
            case 1: typeString = "Accident";
                break;
            case 2: typeString = "Congestion";
                break;
            case 3: typeString = "Disabled Vehicle";
                break;
            case 4: typeString = "Mass Transit";
                break;
            case 5: typeString = "Miscellaneous";
                break;
            case 6: typeString = "Other News";
                break;
            case 7: typeString = "Planned Event";
                break;
            case 8: typeString = "Road Hazard";
                break;
            case 9: typeString = "Construction";
                break;
            case 10: typeString = "Alert";
                break;
            case 11: typeString = "Weather";
                break;
            default: typeString = "Incorrect value";
                break;
        }
        return typeString;
    }

 /*   public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }*/

   /*  private class myTask extends AsyncTask<RequestPackage,String,String> {
         @Override
         protected void onPreExecute() {
             //super.onPreExecute();

         }
         @Override
         protected String doInBackground(RequestPackage... strings) {
             return "task Complete";
         }

         @Override
         protected void onPostExecute(String s) {
             //super.onPostExecute(s);
             toastMaker(s);
         }
     }
*/

   /* public boolean googleServiceAvailable() {
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int isAvailable = api.isGooglePlayServicesAvailable(this);
        if (isAvailable == ConnectionResult.SUCCESS) {
            return true;
        } else if (api.isUserResolvableError(isAvailable)) {
            Dialog dialog = api.getErrorDialog(this, isAvailable, 0);
            dialog.show();
        } else {
            Toast.makeText(this, "Can't Connect to play Services", Toast.LENGTH_LONG).show();
        }
        return false;
    }*/

    public Boolean servicesOK () {
        int isAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (isAvailable == ConnectionResult.SUCCESS) {
            return true;
        }
        else if (GooglePlayServicesUtil.isUserRecoverableError(isAvailable)) {
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(isAvailable, this, ERROR_DIALOG_REQUEST);
            dialog.show();
        }
        else {
            Toast.makeText(this, "Can't connect to mapping service", Toast.LENGTH_SHORT).show();
        }

        return false;
    }
    private boolean initMap() {
        if (mMap == null) {
            MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.Maps);
            mapFragment.getMapAsync(this);
        }
        return (mMap != null);
    }

    private void gotoLocation(double lat, double lng, float zoom) {
        LatLng latLng = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, zoom);
        mMap.moveCamera(update);
    }

    //Action for the button on click, takes input from editText field and does a location
    //  search with that input. Places a marker there and then sets camera over that location
    public void onMapSearch(View view) {
        EditText locationSearch = (EditText) findViewById(R.id.editText);
        String location = locationSearch.getText().toString();
        Geocoder geocoder = new Geocoder(this);
        List<android.location.Address> addressList = null;

        try {
            addressList = geocoder.getFromLocationName(location, 1);

        } catch (IOException e) {
            e.printStackTrace();
        }
        android.location.Address address = addressList.get(0);
        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
        mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
    }
}

/*
    private void gotoLocation(double lat, double lng) {
        LatLng latLng = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLng(latLng);
        mMap.moveCamera(update);
    }
    private void gotoLocationZoom(double lat, double lng, float zoom) {
        LatLng latLng = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, zoom);
        mMap.moveCamera(update);
    }


    public void geoLocate(View view) throws IOException {
        EditText et = (EditText) findViewById(R.id.editText);
        String location = et.getText().toString();

        Geocoder gc = new Geocoder(this);
        List<android.location.Address> list = gc.getFromLocationName(location, 1);
        android.location.Address address = list.get(0);
        String locality = address.getLocality();

        Toast.makeText(this, locality, Toast.LENGTH_LONG).show();

        double lat = address.getLatitude();
        double lng = address.getLongitude();
        gotoLocationZoom(lat, lng, 15);


    }
}


*/