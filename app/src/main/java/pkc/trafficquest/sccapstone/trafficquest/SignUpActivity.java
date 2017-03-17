package pkc.trafficquest.sccapstone.trafficquest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    private EditText emailAddress;
    private EditText password;
    private FirebaseAuth mAuth;
    private FirebaseAuth mAuthListener;
    private Button mSignUpButton;
    private EditText retypePass;
    private FirebaseAuth.AuthStateListener mAuthlistener;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        emailAddress = (EditText) findViewById(R.id.email_in);
        password = (EditText)findViewById(R.id.password_in);
        mSignUpButton = (Button) findViewById(R.id.email_signup_button_in);
        retypePass = (EditText) findViewById(R.id.retypepasswrd_in);
        mAuth = FirebaseAuth.getInstance();
        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });


    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    public void signIn(){
        String email = emailAddress.getText().toString();
        String pass = password.getText().toString();
        String repass = retypePass.getText().toString();
        if (pass.equals(repass)){
            mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Sign up succesfully complete!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Username already taken", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
        else {

            Toast.makeText(getApplicationContext(),"Passwords do not match",Toast.LENGTH_SHORT).show();
        }
    }

    public void onStart(){
        super.onStart();

    }
    @Override
    public void onStop(){
        super.onStop();

    }

}
