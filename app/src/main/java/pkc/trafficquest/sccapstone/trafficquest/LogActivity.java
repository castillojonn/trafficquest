package pkc.trafficquest.sccapstone.trafficquest;

import android.content.Intent;
import android.net.Uri;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import com.google.firebase.auth.FirebaseAuth;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class LogActivity extends AppCompatActivity {
    private ArrayList<String> accidentList; // used for the String version of the accident list
    private ArrayList<Accidents> accidents; // used for the list of type Accidents
    private ListView listView;
    public static final int REQUEST_CODE_MAIN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String csvString;
        setContentView(R.layout.activity_log);
        Intent logIntent = getIntent();
        Bundle data = logIntent.getExtras();
        // checks to see if there is data from the intent, if there is, instantiate list of names and set up the list view
        if (logIntent.getExtras() != null){
            accidentList = logIntent.getStringArrayListExtra("accidentList");
            accidents = data.getParcelableArrayList("logAccidentList");
            ListAdapter accAdapter = new ArrayAdapter<String>(this, R.layout.accident_list, accidentList);
            listView = (ListView) findViewById(R.id.aListview);
            listView.setAdapter(accAdapter);
            csvString = createCSV(accidents);
            saveCSV(csvString);
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

    /*
    Creates a csv file for the list of requested accidents
    @param A list of accidents to create a csv for
    @return the csv of requested accidents
     */
    public String createCSV(ArrayList<Accidents> accList){
        String csv = "";
        for (int i=0; i<accList.size(); i++){ // loop through all accidents and add each detail to the csv
            Accidents accident = accList.get(i); // get individual accident
            csv += "\"" + accident.getPoint().getCoordinates().get(0) + ", " + accident.getPoint().getCoordinates().get(1) + "\"," + // add latitude and longitude, separated by a space to the list
                   "\"" + accident.getToPoint().getCoordinates().get(0) + ", " + accident.getToPoint().getCoordinates().get(1) + "\"," + // add the toPoint(where the accident ends) to the list, latitude and longitude separated with a space
                    accident.getDescription() + "," + // add the accident description to the list
                    accident.getRoadClosed() + "," + // add if road is closed (boolean value)
                    interpretSeverity(accident) + "," + // add severity of accident to list
                    interpretTime(accident.getStart()) + "," + // add the start time of accident to list
                    interpretTime(accident.getEnd()) + "," + // add the end time of accident to list
                    interpretType2(accident) + "\n"; // add the type of the accident to the list (accident, weather, hazard, etc.) and go to next line
        }
        return csv;
    }

    /*
    Write the file to internal storage
    @param data String data to write
     */
    public void saveCSV(String data) {
        File file = null;
        File root = Environment.getExternalStorageDirectory();
        if (root.canWrite()) {
            File dir = new File(root.getAbsolutePath() + "/AccidentData");
            dir.mkdirs();
            file = new File(dir, "data.csv");
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(file);
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                out.write(data.getBytes());
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            try {
                out.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            Toast.makeText(getApplicationContext(), "File failed to save.", Toast.LENGTH_SHORT).show();
        }
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
