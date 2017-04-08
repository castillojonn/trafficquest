package pkc.trafficquest.sccapstone.trafficquest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SavedSearchesActivity extends AppCompatActivity {

    private static final String TAG = SavedSearchesActivity.class.getSimpleName();

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_searches);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_left);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl(MainActivity.FIREBASE_URL).child("users").child("" + mAuth.getCurrentUser().getUid()).child("addresses").child("accidents");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Address> addresses = new ArrayList<>();
                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    addresses.add(postSnapshot.getValue(pkc.trafficquest.sccapstone.trafficquest.Address.class));
                }
                setUpRecycler(addresses);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(SavedSearchesActivity.this,
                        databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setUpRecycler(List<Address> addresses) {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        SavedSearchesAdapter adapter = new SavedSearchesAdapter(addresses);
        adapter.setOnItemClickListener(new SavedSearchesAdapter.OnItemClickListener() {
            @Override
            public void onClick(Address address) {
                startActivity(MapsActivity.getIntent(SavedSearchesActivity.this, address));
            }
        });
        recyclerView.setAdapter(adapter);

    }

}
