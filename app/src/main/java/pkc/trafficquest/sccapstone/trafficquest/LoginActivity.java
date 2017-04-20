package pkc.trafficquest.sccapstone.trafficquest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity  {

    /**
     * Id to identity READ_CONTACTS permission request.
     */


    // UI references.
    private EditText mEmail;
    private EditText mPassword;
    private Button mLogin;
    private FirebaseAuth mAuth;
    //private FirebaseAuth.AuthStateListener mAuthListener; //detects changes in the authentication state

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmail = (EditText) findViewById(R.id.email_login);
        mLogin = (Button) findViewById(R.id.mlogin_button);
        mAuth = FirebaseAuth.getInstance();
        mPassword= (EditText) findViewById(R.id.password_login);
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
        Button mBackButton = (Button) findViewById(R.id.backToStart);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    public void signIn(){
        if (mEmail.getText().toString().equals("") || mPassword.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(),"Please enter a valid email and password",Toast.LENGTH_SHORT).show();
        }
        else {
            mAuth.signInWithEmailAndPassword(mEmail.getText().toString(), mPassword.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "User sign in successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Incorrect password and email combination", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    }


