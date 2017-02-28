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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
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
    private List<String> names = new ArrayList<String>(); // a list of strings used in the listview in LogActivity
    Intent logIntent; // intent to launch logActivity
    private DatabaseReference mDatabase; // reference to the Firebase
    // text boxes to enter latitude and longitude to search
    EditText searchLat;
    EditText searchLng;
    // double version of searchLat and searchLng
    double searchLatDouble;
    double searchLngDouble;
    private boolean isLogRequest = false; // used to launch the log activity if true

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
        searchLatDouble = Double.parseDouble(searchLat.getText().toString()); // converts the contents of the EditText to a double
        searchLngDouble = Double.parseDouble(searchLng.getText().toString());

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) { // if request data is pressed launch a new activity with the listview of traffic accidents
            if (isOnline()) {
                isLogRequest = true;
                Toast.makeText(this, "Results:", Toast.LENGTH_LONG).show();
                requestData(searchLatDouble, searchLngDouble); // requests data by the lattitude and longitude entered in the editText fields
                finish(); // ends the activity
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
        } else if (id == R.id.action_Maps) {
            startActivity(new Intent(getApplicationContext(), MapsActivity.class));
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
     */
    protected void requestData(double lat, double lng) {
        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(ENDPOINT).addConverterFactory(GsonConverterFactory.create()).build();
        AccidentsAPI api = restAdapter.create(AccidentsAPI.class);
        logIntent = new Intent(getApplicationContext(), LogActivity.class);
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
                            for (int j = 0; j < accidents.size(); j++) {
                                Accidents accObj = new Accidents();
                                accObj = accidents.get(j);
                                // adds the description, start and end time, and severity of accident to a list of strings
                                // that will be displayed in the listview
                                names.add("Description: " + accObj.getDescription() +
                                        "\n Start time(epoch): " + accObj.getStart() +
                                        "\n End time(epoch): " + accObj.getEnd() +
                                        "\n Severity: " + accObj.getSeverity());

                            }
                            //mDatabase.child("users").child("" + mAuth.getCurrentUser().getUid()).setValue(names); // stores the requested list into the database
                            mDatabase.child("users").child("" + mAuth.getCurrentUser().getUid()).child("Accidents").setValue(accidents); // stores the requested list into the database

                            toastMaker("Task Completed");

                        }
                        logIntent.putStringArrayListExtra("accidentList", (ArrayList<String>) names); // places the names array so it can be displayed in a listview in LogActivity
                        // checks if the LogActivity needs to be launched based on if request data was pressed,
                        // intended to prevent LogActivity from launching if another activity needs to use requestData
                        if (isLogRequest){
                            startActivity(logIntent);
                            isLogRequest = false;
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

    public void saveData(ArrayList<Accidents> accList) {

        mDatabase.child("users").child("Accidents").child("" + mAuth.getCurrentUser().getUid()).setValue(accList); // stores the requested list into the database

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


