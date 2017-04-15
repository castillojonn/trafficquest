package pkc.trafficquest.sccapstone.trafficquest;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.location.LocationServices;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class LogActivity extends AppCompatActivity {
    private static final int PERMISSION_WRITE_STORAGE_SAVE = 3; // code for when user selects download csv
    private static final int PERMISSION_WRITE_STORAGE_EMAIL = 4; // code for when user selects email csv
    private ArrayList<String> accidentList; // used for the String version of the accident list
    private ArrayList<Accidents> accidents; // used for the list of type Accidents
    private ListView listView; // view to display results of accidents
    private String csvString; // String used create the csv
    private boolean isDownload = false; // checks if download csv is selected
    private boolean isEmail = false; // checks if email csv is selected
    public static final int REQUEST_CODE_MAIN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
            csvString = createCSV(accidents); // makes a csv out of the list of accidents

        }

       /*  // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.log_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { // menu to select between downloading or emailing a csv of requested accidents
        int id = item.getItemId();
        if (id == R.id.action_download){ // downloads csv of requested accidents if selected
            isDownload = true;
            if (checkWriteStoragePermission()) { // check storage permission and download csv if permission granted
                saveCSV(csvString);
            }
            isDownload = false;
        }
        else if (id == R.id.action_email){ // downloads and emails csv of requested accidents if selected
            isEmail = true;
            if (checkWriteStoragePermission()){ // check write external storage permission and send email if permission granted
                sendEmail(csvString);
            }
            isEmail = false;
        }
        return super.onOptionsItemSelected(item);
    }

    /*
    Checks if app has access to the user's external storage, if not requests to enable the permission
     */
    public boolean checkWriteStoragePermission() {
        if (isDownload && ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) { // checks if permission is granted for writing to external storage
            ActivityCompat.requestPermissions(LogActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_WRITE_STORAGE_SAVE); // request permission if not granted, handles PERMISSION_WRITE_STORAGE_SAVE case in onRequestPermissionResult
            isDownload = false;
            return false;
        }
        else if (isEmail && ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){ // checks if permission is granted for writing to external storage
            ActivityCompat.requestPermissions(LogActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_WRITE_STORAGE_EMAIL); // request permission if not granted, handles PERMISSION_WRITE_STORAGE_EMAIL case in onRequestPermissionResult
            isEmail = false;
            return false;
        }
        else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_WRITE_STORAGE_SAVE: { // handles case for when user selects download csv
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, get the latitude and longitude
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) { // if permission granted, save csv
                        saveCSV(csvString);
                    }

                } else {
                    Toast.makeText(LogActivity.this, "Permission not Granted", Toast.LENGTH_LONG).show();
                }
                return;
            }

            case PERMISSION_WRITE_STORAGE_EMAIL: { // handles case for when user selects email csv
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, get the latitude and longitude
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) { // if permission granted, email csv
                        sendEmail(csvString);
                    }

                } else {
                    Toast.makeText(LogActivity.this, "Permission not Granted", Toast.LENGTH_LONG).show();
                }
                return;
            }
            }

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
        return csv; // return the String in csv format
    }

    /*
    Write the file to internal storage
    @param data String data to write
     */
    public void saveCSV(String data) {
        File file = null;
        File root = Environment.getExternalStorageDirectory(); // path of root directory
        if (root.canWrite()) { // checks if the application can modify the path
            File dir = new File(root.getAbsolutePath() + "/AccidentData"); // new directory
            dir.mkdirs();
            file = new File(dir, "data.csv"); // file to be wrote to
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(file); // output stream to file location
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                out.write(data.getBytes()); // write encoded string to file
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            try {
                out.close(); // close output stream
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            Toast.makeText(getApplicationContext(), "Downloaded.", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getApplicationContext(), "File failed to write.", Toast.LENGTH_SHORT).show(); // prints message if file does not write to file
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
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy 'at' HH:mm z"); // sets the format
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

    /*
    Will create an email intent, and send the requested csv file after it creates it, to the email intent.
    @param data String data to send as csv
     */
    public void sendEmail(String data) {
        File file = null;
        File root = Environment.getExternalStorageDirectory(); // path of root directory
        if (root.canWrite()) { // checks if the application can modify the path
            File dir = new File(root.getAbsolutePath() + "/AccidentData"); // new directory
            dir.mkdirs();
            file = new File(dir, "data.csv"); // file to be wrote to
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(file); // output stream to file location
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                out.write(data.getBytes()); // write encoded string to file
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            try {
                out.close(); // close output stream
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            Toast.makeText(getApplicationContext(), "File failed to write.", Toast.LENGTH_SHORT).show(); // prints message if file does not write to file
        }

        Uri u = null;
        u = Uri.fromFile(file); // get contents of file
        Intent emailIntent = new Intent(Intent.ACTION_SEND); // create an intent to send the csv
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "TrafficQuest: CSV"); // subject of email
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Here is a csv, as you requested."); // body of email
        emailIntent.putExtra(Intent.EXTRA_STREAM, u); // add the attachment csv
        emailIntent.setType("text/plain"); // sets type to plain, supports csv files

        startActivity(Intent.createChooser(emailIntent, "Send email with:"));

    }

    public void toastMaker(String toast) {
        Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_SHORT).show();


    }
}
