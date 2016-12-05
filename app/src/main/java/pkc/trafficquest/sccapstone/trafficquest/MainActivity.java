package pkc.trafficquest.sccapstone.trafficquest;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity{





    public static final String API_KEY = "AmJHdhFiW4EQCdWrgEoTk5-vo8zW-96v2LBmeBgnc0z_FV0Ru-gZizGCLfhtRtrJ";
    public static final String ENDPOINT = "http://dev.virtualearth.net";
    public static final String FIREBASE_URL = "https://trafficquest-9b525.firebaseio.com/";
    private FirebaseAuth mAuth;
    Response<ArrayList<Accidents>> accidentses;
    private ListView view;
    private DatabaseReference mDatabase; // reference to the Firebase
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl(FIREBASE_URL); // reference to the Firebase path
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Hello welcome to the code
        view = (ListView) findViewById(R.id.aListview);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.


    }

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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            if (isOnline()) {
                requestData();
            } else {
                Toast.makeText(getApplicationContext(), "NETWORK IS NOT AVAILABLE", Toast.LENGTH_SHORT).show();
            }
            return true;
        } else if (id == R.id.action_logout) {
            FirebaseUser user = mAuth.getCurrentUser();
            Toast.makeText(getApplicationContext(), user.getEmail().toString() + " has signed out", Toast.LENGTH_SHORT).show();
            mAuth.signOut();
            startActivity(new Intent(getApplicationContext(), DispatchActivity.class));
            return true;
        }
        else if(id == R.id.action_Maps){
            startActivity(new Intent(getApplicationContext(),MapsActivity.class));
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

    protected void requestData() {
        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(ENDPOINT).addConverterFactory(GsonConverterFactory.create()).build();
        AccidentsAPI api = restAdapter.create(AccidentsAPI.class);
        final Call<RequestPackage> acc = api.soontobedecided();
        acc.enqueue(new Callback<RequestPackage>() {
            @Override
            public void onResponse(Call<RequestPackage> call, Response<RequestPackage> response) {
                RequestPackage res = response.body();
                //res = response.body();
                try {
                    if (response != null) {
                        Toast.makeText(getApplicationContext(), "Message: " + response.message() + ": " + response.code(), Toast.LENGTH_LONG).show();
                        ArrayList<Accidents> accidents = new ArrayList<Accidents>();
                        ArrayList<ResourceSet> rSet = new ArrayList<ResourceSet>();
                        for (int i = 0; i < res.getResourceSets().size(); i++) {
                            rSet.add(res.getResourceSets().get(i));
                        }
                        //ResourceSet rSetobj = new ResourceSet();
                        for (int i = 0; i < rSet.size(); i++) {
                            ResourceSet rSetobj = new ResourceSet();
                            rSetobj = rSet.get(i);
                            accidents = rSetobj.getResources();
                            ArrayList<String> names = new ArrayList<String>();
                            for (int j = 0; j < accidents.size(); j++) {
                                Accidents accObj = new Accidents();
                                accObj = accidents.get(j);
                                names.add(accObj.getDescription());

                            }
                            ArrayAdapter ap = new ArrayAdapter(getApplicationContext(), R.layout.accident_list, names);
                            //mDatabase.child("users").child("" + mAuth.getCurrentUser().getUid()).setValue(names); // stores the requested list into the database
                            mDatabase.child("users").child("" + mAuth.getCurrentUser().getUid()).child("Accidents").setValue(accidents); // stores the requested list into the database

                            view.setAdapter(ap);
                            toastMaker("Task Completed");

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


        /*
        acc.enqueue(new Callback<ArrayList<RequestPackage>>() {
            @Override
            public void onResponse(Call<ArrayList<Accidents>> call, Response<ArrayList<Accidents>> response) {
                accidentses = response;

                try{
                if (accidentses.body().size() < 0){
                    Toast.makeText(getApplicationContext(),"There is no data",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"There is data",Toast.LENGTH_LONG).show();
                }

                ArrayList<Accidents> accident = new ArrayList<Accidents>();
                for (int i = 0; i < accidentses.body().size(); i++){
                    accident.add(accidentses.body().get(i));
                }
                ArrayAdapter ap = new ArrayAdapter(getApplicationContext(),R.layout.accident_list,accident);
                view.setAdapter(ap);
                toastMaker("Task Completed");
                }catch(Exception e){
                    Toast.makeText(getApplicationContext(),"The list is null",Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Accidents>> call, Throwable t) {

            }
        });
        */
        /*api.groupList(new Call<ArrayList<Accidents>>() {
            @Override
            public void onResponse(Call<ArrayList<Accidents>> call, Response<ArrayList<Accidents>> response) {
                accidentses = response;
                ArrayList<Accidents> accident = new ArrayList<Accidents>();
                for (int i = 0; i < accidentses.body().size(); i++){
                    accident.add(accidentses.body().get(i));
                }
                ArrayAdapter ap = new ArrayAdapter(getApplicationContext(),R.layout.accident_list,accident);
                view.setAdapter(ap);
                toastMaker("Task Completed");
            }

            @Override
            public void onFailure(Call<ArrayList<Accidents>> call, Throwable t) {

            }
        });
        */
    }

    public void saveData(ArrayList<Accidents> accList) {

        mDatabase.child("users").child("Accidents").child("" + mAuth.getCurrentUser().getUid()).setValue(accList); // stores the requested list into the database

    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

}
