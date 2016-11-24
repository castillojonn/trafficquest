package pkc.trafficquest.sccapstone.trafficquest;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String API_KEY = "AmJHdhFiW4EQCdWrgEoTk5-vo8zW-96v2LBmeBgnc0z_FV0Ru-gZizGCLfhtRtrJ";
    public static final String ENDPOINT = "http://dev.virtualearth.net";
    private FirebaseAuth mAuth;
    ArrayList<Accidents> accidentses = new ArrayList<Accidents>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Hello welcome to the code
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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
            return true;
        }
        else if(id == R.id.action_logout){
            FirebaseUser user = mAuth.getCurrentUser();
            Toast.makeText(getApplicationContext(),user.getEmail().toString()+ " has signed out",Toast.LENGTH_SHORT).show();
            mAuth.signOut();
            startActivity(new Intent(getApplicationContext(),DispatchActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void toastMaker(String toast){
        Toast.makeText(getApplicationContext(),toast,Toast.LENGTH_SHORT).show();
    }
    protected boolean isOnline(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()){
            return true;
        }
        else{
            return false;
        }
    }
    protected  void requestData(){
        myTask task = new myTask();
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"param 1","param 2","param 3");
    }
    private class myTask extends AsyncTask<String,String,String>{
        @Override
        protected void onPreExecute() {
            //super.onPreExecute();
            toastMaker("Task started");
        }
        @Override
        protected String doInBackground(String... strings) {
            return "task Complete";
        }

        @Override
        protected void onPostExecute(String s) {
            //super.onPostExecute(s);
            toastMaker(s);
        }
    }
}
