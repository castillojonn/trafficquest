package pkc.trafficquest.sccapstone.trafficquest;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.io.File;
import java.util.ArrayList;

public class LogActivity extends AppCompatActivity {
    private ArrayList<String> accidentList;
    private ListView listView;
    public static final int REQUEST_CODE_MAIN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        Intent logIntent = getIntent();
        if (logIntent.getExtras() != null){
            accidentList = logIntent.getStringArrayListExtra("accidentList");
            ListAdapter accAdapter = new ArrayAdapter<String>(this, R.layout.accident_list, accidentList);
            listView = (ListView) findViewById(R.id.aListview);
            listView.setAdapter(accAdapter);
        }
        setOnClickListener();

       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        GoogleMap mgoogleMap;
        //Hello welcome to the code


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();*/
    }

    //Will create an email intent, and send the requested csv file
    //  after it creates it, to the email intent.
    public void sendEmail() {
        //String fileName = "Stuff";
        //File csvFile = /*put csv creating code here*/;
        //Uri path = Uri.fromFile(csvFile);
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "TrafficQuest: CSV");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Here is a csv, as you requested.");
        //emailIntent.putExtra(Intent.EXTRA_STREAM, path);

        startActivity(Intent.createChooser(emailIntent, "Send email..."));
        Toast.makeText(this, "Email Sent", Toast.LENGTH_SHORT).show();

    }
    private void setOnClickListener() {
        findViewById(R.id.buttonSend).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            }
        });
    }


    public void toastMaker(String toast) {
        Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_SHORT).show();
    }
}
