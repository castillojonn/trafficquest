package pkc.trafficquest.sccapstone.trafficquest;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by pkcho on 10/30/2016.
 */
public class DispatchActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Button retryButton; // retry button to relaunch activity if no Internet connection is detected

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (isNetworkAvailable(getApplicationContext())) { // if there is Internet connection
            if (user != null) { // if a user is signed in, launch main activity
                Toast.makeText(getApplicationContext(), "User: " + user.getEmail(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), MainActivity.class)); //user is logged in
                finish();
            } else { // otherwise start SignUpOrLoginActivity
                startActivity(new Intent(getApplicationContext(), SignUporLoginActivity.class));//not logged in
                finish();
            }
        }
        else { // if there is no Internet connection, display message to turn on wi-fi or cell phone data
            setContentView(R.layout.activity_dispatch); // set content view
            retryButton = (Button) findViewById(R.id.retryButton); // initialize button
            retryButton.setOnClickListener(new View.OnClickListener() { // set listener
                @Override
                public void onClick(View v) { // if clicked, restart the activity
                    startActivity(new Intent(getApplicationContext(), DispatchActivity.class));
                    finish();
                }
            });
        }
    }
  /*      mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth fbauth){
                FirebaseUser user = fbauth.getCurrentUser();
                if(isNetworkAvailable(getApplicationContext())) {
                    if (user != null) {
                        Toast.makeText(getApplicationContext(), "User: " + user.getEmail(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class)); //user is logged in
                        finish();
                    } else {
                        //Toast.makeText(getApplicationContext(), "No user detected", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), SignUporLoginActivity.class));//not logged in
                        finish();
                    }
                }else {
                    new CountDownTimer(5000, 1000) {

                        public void onTick(long millisUntilFinished) {

                            Toast.makeText(getApplicationContext(), "The app will self destruct in: 5 seconds", Toast.LENGTH_SHORT).show();
                            Toast.makeText(getApplicationContext(),"You dont have internet!! Call an ISP now!",Toast.LENGTH_SHORT).show();
                            Toast.makeText(getApplicationContext(),"The app will self destruct in: 1 second",Toast.LENGTH_SHORT).show();
                        }

                        public void onFinish() {
                            System.exit(1);
                        }
                    }.start();
                }
            }
        };*/



    //}}
   /* @Override
    public void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    @Override
    public void onStop(){
        super.onStop();
        if(mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }*/
    public boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
