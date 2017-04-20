package pkc.trafficquest.sccapstone.trafficquest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class SignUporLoginActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    //private FirebaseAuth.AuthStateListener mAuthListener;
    private Button signIn;
    private Button signUp;
    @Override
    public void onClick(View view) {

    }

    public SignUporLoginActivity() {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_upor_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        signUp = (Button) findViewById(R.id.email_signup_button);
        signIn = (Button) findViewById(R.id.email_login_button);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),SignUpActivity.class));
            }
        });
    }


}
