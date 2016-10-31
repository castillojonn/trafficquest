package pkc.trafficquest.sccapstone.trafficquest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

public class SignUpActivity extends AppCompatActivity {
    private EditText fName;
    private EditText lName;
    private EditText emailAddress;
    private EditText password;
    private EditText pNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



    }

}
