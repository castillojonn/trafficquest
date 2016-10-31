package pkc.trafficquest.sccapstone.trafficquest;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by pkcho on 10/30/2016.
 */
public class DispatchActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth fbauth){
                FirebaseUser user = fbauth.getCurrentUser();
                if(isNetworkAvailable(getApplicationContext())) {
                    if (user != null) {
                        Toast.makeText(getApplicationContext(), "User is already signed with " + user.getDisplayName(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class)); //user is logged in
                    } else {
                        Toast.makeText(getApplicationContext(), "No user detected", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), SignUporLoginActivity.class));//not logged in
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
        };



    }
    @Override
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
    }
    public boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
