package pkc.trafficquest.sccapstone.trafficquest;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {


    public static final String API_KEY = "AmJHdhFiW4EQCdWrgEoTk5-vo8zW-96v2LBmeBgnc0z_FV0Ru-gZizGCLfhtRtrJ";
    public static final String ENDPOINT = "http://dev.virtualearth.net";
    public static final String FIREBASE_URL = "https://trafficquest-9b525.firebaseio.com/";
    public static final int REQUEST_CODE_LOG = 1;
    private FirebaseAuth mAuth;
    private ArrayList<Accidents> accidents = new ArrayList<>(); // arraylist of accidents
    private ArrayList<Accidents> getAccidents = new ArrayList<>();
    private List<String> names = new ArrayList<String>(); // a list of strings used in the listview in LogActivity
    Intent logIntent; // intent to launch LogActivity
    Intent mapIntent; // intent to launch MapsActivity
    private DatabaseReference mDatabase; // reference to the Firebase
    private DatabaseReference mAccidentsReference;
    Accidents newAccident;
    // text boxes to enter latitude and longitude to search
    EditText searchLat;
    EditText searchLng;
    // double version of searchLat and searchLng
    double searchLatDouble;
    double searchLngDouble;
    private boolean isLogRequest = false; // used to launch the log activity if true
    private boolean isMapRequest = false; // used to launch the log activity if true

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    GoogleMap mgoogleMap;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl(FIREBASE_URL); // reference to the Firebase path
        mAccidentsReference = mDatabase.child("users").child("" + mAuth.getCurrentUser().getUid()).child("Accidents"); // reference to the users path of last requested accidents
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        GoogleMap mgoogleMap;
        // Text boxes to enter the latitude and longitude to search
        searchLat = (EditText) findViewById(R.id.editLattitude);
        searchLng = (EditText) findViewById(R.id.editLongitude);

        //view = (ListView) findViewById(R.id.aListview);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.Maps);
        mapFragment.getMapAsync(this);

    }

    public boolean googleServiceAvailable() {
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
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mgoogleMap = googleMap;

    }

    // ATTENTION: This was auto-generated to implement the App Indexing API.
    // See https://g.co/AppIndexing/AndroidStudio for more information.

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (!(searchLat.getText().toString().equals("")) && !(searchLng.getText().toString().equals(""))){
            searchLatDouble = Double.parseDouble(searchLat.getText().toString()); // converts the contents of the EditText to a double
            searchLngDouble = Double.parseDouble(searchLng.getText().toString());
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) { // if request data is pressed launch a new activity with the listview of traffic accidents
            if (isOnline()) {
                isLogRequest = true; // used in the requestData method to check if it is a request to display the log
                Toast.makeText(this, "Results:", Toast.LENGTH_LONG).show();
                requestData(searchLatDouble, searchLngDouble); // requests data by the lattitude and longitude entered in the editText fields
                names.clear(); // clear the string list so it won't keep adding to the existing list
                accidents.clear(); // clear the accident list
            } else {
                Toast.makeText(getApplicationContext(), "NETWORK IS NOT AVAILABLE", Toast.LENGTH_SHORT).show();
            }
            return true;
        } else if (id == R.id.action_logout) { // if logout is pressed, sign the user out
            FirebaseUser user = mAuth.getCurrentUser();
            Toast.makeText(getApplicationContext(), user.getEmail().toString() + " has signed out", Toast.LENGTH_SHORT).show();
            mAuth.signOut();
            startActivity(new Intent(getApplicationContext(), DispatchActivity.class));
            return true;
        } else if (id == R.id.action_Maps) { // launch maps activity
            if (searchLat.getText().toString().equals("") && searchLng.getText().toString().equals("")){ // if the text fields are empty, view the map with no request for data
                startActivity(new Intent(getApplicationContext(), MapsActivity.class));
            }
            else { // otherwise, assign isMapRequest true, request data from coordinates, then clear the lists being sent to MapsActivity
                isMapRequest = true; // used in the requestData method to check if it is a request to check the query results in a map
                requestData(searchLatDouble, searchLngDouble); // requests data by the lattitude and longitude entered in the editText fields
                names.clear(); // clear the string list so it won't keep adding to the existing list
                accidents.clear(); // clear the accident list
            }
        }
        else if (id == R.id.action_getFromFirebase) { // if get from Firebase is requested, get and display the last queried results
            getDataFromFirebase();
        }

        return super.onOptionsItemSelected(item);
    }

    public void toastMaker(String toast) {
        Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_SHORT).show();
    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }
    /*
    Requests traffic incidents around the given latitude and longitude
    and saves the accidents on Firebase
    @param lat The latitude to search
    @param lng The longitude to search
     */
    protected void requestData(double lat, double lng) {
        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(ENDPOINT).addConverterFactory(GsonConverterFactory.create()).build();
        AccidentsAPI api = restAdapter.create(AccidentsAPI.class);
        logIntent = new Intent(getApplicationContext(), LogActivity.class);
        mapIntent = new Intent(getApplicationContext(), MapsActivity.class);
        double distanceLat = 160.934,
               distanceLng = 160.934; // in km = 100 mi
        distanceLat /= 110.574; // converts the distance to degrees latitude
        distanceLng /= 111.32*Math.cos(Math.toDegrees(lat)); // converts the distance to degrees longitude
        // coordinates used to search a bounding box of 100mi around the entered latitude and longitude
        double southLat = lat - distanceLat;
        double westLng = lng - distanceLng;
        double northLat = lat + distanceLat;
        double eastLng = lng + distanceLng;
        Call<RequestPackage> acc = api.getIncidents(southLat,westLng,northLat,eastLng);
        acc.enqueue(new Callback<RequestPackage>() {
            @Override
            public void onResponse(Call<RequestPackage> call, Response<RequestPackage> response) {
                RequestPackage res = response.body();
                try {
                    if (response != null) {
                        Toast.makeText(getApplicationContext(), "Message: " + response.message() + ": " + response.code(), Toast.LENGTH_LONG).show();
                        //ArrayList<Accidents> accidents = new ArrayList<Accidents>();
                        ArrayList<ResourceSet> rSet = new ArrayList<ResourceSet>();
                        for (int i = 0; i < res.getResourceSets().size(); i++) {
                            rSet.add(res.getResourceSets().get(i));
                        }
                        //ResourceSet rSetobj = new ResourceSet();
                        for (int i = 0; i < rSet.size(); i++) {
                            ResourceSet rSetobj = new ResourceSet();
                            rSetobj = rSet.get(i);
                            accidents = rSetobj.getResources();
                            //ArrayList<String> names = new ArrayList<String>();
                            for (int j = 0; j < accidents.size(); j++) { // loop through accidents to add them to a list of Strings
                                Accidents accObj /*= new Accidents()*/;
                                accObj = accidents.get(j); // get accident j
                                // adds the description, start and end time, severity, and coordinates of accident to a list of strings
                                // that will be displayed in the listview
                                names.add("Coordinates: " + accObj.getPoint().getCoordinates().get(0) // add latitude to the list of Strings
                                        + "," + accObj.getPoint().getCoordinates().get(1) + "\n"// add longitude to the list of Strings
                                        + "Type: " + interpretType2(accObj) + "\n"
                                        + "Description: " + accObj.getDescription()  + "\n" // add description to the list of Strings
                                        + "Start time: " + interpretTime(accObj.getStart()) + "\n" // add start time to the list of Strings
                                        + "End time: " + interpretTime(accObj.getEnd()) + "\n"  // add end time to the list of Strings
                                        + "Severity: " + interpretSeverity(accObj) + "\n" // add severity to the list of Strings
                                        );

                            }
                            //mDatabase.child("users").child("" + mAuth.getCurrentUser().getUid()).setValue(names); // stores the requested list into the database
                            mDatabase.child("users").child("" + mAuth.getCurrentUser().getUid()).child("Accidents").setValue(accidents); // stores the requested list into the database
                            toastMaker("Task Completed");

                        }
                        logIntent.putStringArrayListExtra("accidentList", (ArrayList<String>) names); // places the names array so it can be displayed in a listview in LogActivity
                        logIntent.putExtra("logAccidentList", accidents);
                        mapIntent.putStringArrayListExtra("stringAccidentList", (ArrayList<String>) names); // allows the mapsActivity to get the Sting list of accidents
                        mapIntent.putExtra("accidentsList", accidents);
                        // checks if the LogActivity needs to be launched based on if request data was pressed,
                        // intended to prevent LogActivity from launching if another activity needs to use requestData
                        if (isLogRequest && !isMapRequest){
                            startActivity(logIntent);
                            isLogRequest = false;
                        }
                        else if (isMapRequest && !isLogRequest){ // if the Maps option is selected
                            startActivity(mapIntent);
                            isMapRequest = false;
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Message: " + response.message() + ": " + response.code(), Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RequestPackage> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Message: " + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("Message", t.getMessage());
                t.printStackTrace();
            }
        });

    }

    /*
    Interprets the time into a human readable format
    @param t the string from the accident list to interpret
    @return the converted time
     */
    public String interpretTime (String t) {
        String timeString = t.substring(6, t.length()-2); // gets rid of the leading and trailing slashes and parenthesis
        String date; // value to return
        long time = Long.parseLong(timeString); // parse the string as a long
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy 'at' HH:mm:ss z"); // sets the format
        date = sdf.format(new Date(time)); // sets the entered string as the SimpleDateFormat

        return date; // return the date
    }

    /*
    Interprets the severity codes received from the request
    @param acc the accident to get the severity data from
    @return the interpreted severity code
     */
    public String interpretSeverity (Accidents acc) {
        int severity = acc.getSeverity(); // the type code from the accident
        String sevString; // the value to return
        switch (severity) {
            case 1: sevString = "Low Impact";
                break;
            case 2: sevString = "Minor";
                break;
            case 3: sevString = "Moderate";
                break;
            case 4: sevString = "Serious";
                break;
            default: sevString = "Incorrect value";
                break;
        }
        return sevString; // return the severity code
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

    public void saveData(ArrayList<Accidents> accList) {

        mDatabase.child("users").child("Accidents").child("" + mAuth.getCurrentUser().getUid()).setValue(accList); // stores the requested list into the database

    }

    /*
    Gets the results of the last query made that was saved to Firebase
     */
    public void getDataFromFirebase() {
        logIntent = new Intent(getApplicationContext(), LogActivity.class); // intent to launch log activity
        mAccidentsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot acc: dataSnapshot.getChildren()) { // loop through each child of Accidents in user's Firebase, add them to a different list
                    Accidents getAccident = acc.getValue(Accidents.class);
                    getAccidents.add(getAccident);
                }
                if (getAccidents.size() != 0) { // adds the results to a list of strings to display in the log activity, much like the request data method
                    for (int i = 0; i < getAccidents.size(); i++) {
                        Accidents accObj = getAccidents.get(i);
                        names.add("Coordinates: " + accObj.getPoint().getCoordinates().get(0) // add latitude to the list of Strings
                                + "," + accObj.getPoint().getCoordinates().get(1) + "\n"// add longitude to the list of Strings
                                + "Type: " + interpretType2(accObj) + "\n"
                                + "Description: " + accObj.getDescription() + "\n" // add description to the list of Strings
                                + "Start time: " + interpretTime(accObj.getStart()) + "\n" // add start time to the list of Strings
                                + "End time: " + interpretTime(accObj.getEnd()) + "\n"  // add end time to the list of Strings
                                + "Severity: " + interpretSeverity(accObj) + "\n" // add severity to the list of Strings
                        );
                    }

                    logIntent.putStringArrayListExtra("accidentList", (ArrayList<String>) names); // places the names array so it can be displayed in a listview in LogActivity
                    logIntent.putExtra("logAccidentList", getAccidents);
                    startActivity(logIntent); // starts log activity with the list of accidents saved on Firebase
                }
                else {
                    Toast.makeText(getApplicationContext(), "list empty", Toast.LENGTH_SHORT).show();
                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "onCancelled called", Toast.LENGTH_SHORT).show();

            }
        });

    }


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_LOG){
            if(resultCode == RESULT_OK){
                data.getStringArrayListExtra("accidentList");
            }
        }
    }
}

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */


