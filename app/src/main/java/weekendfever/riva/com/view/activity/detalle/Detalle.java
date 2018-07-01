package weekendfever.riva.com.view.activity.detalle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import weekendfever.riva.com.R;

public class Detalle extends AppCompatActivity implements OnMapReadyCallback {


    private GoogleMap googleMap;

    @BindView(R.id.postTitleDetails)
    TextView mPostTitleDetails;
    @BindView(R.id.postDescDetails)
    TextView mPostDescDetails;

    @BindView(R.id.image_paralax)
    ImageView mImage_paralax;


    private String mPost_key = null;
    private String mCategoria = null;

    DatabaseReference mDatabase;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        ButterKnife.bind(this);
        init();
        initMapa();

    }

    private void initMapa() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    private void init() {
        mPost_key = getIntent().getExtras().getString("blog_id");
        mCategoria = getIntent().getExtras().getString("categoria");
        mDatabase = FirebaseDatabase.getInstance().getReference().child(mCategoria);


        mDatabase.child(mPost_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                String post_title = (String) dataSnapshot.child("title").getValue();
                String post_desc = (String) dataSnapshot.child("desc").getValue();
                String post_image = (String) dataSnapshot.child("image").getValue();

                mPostTitleDetails.setText(post_title);
                mPostDescDetails.setText(post_desc);
                Glide.with(getApplicationContext())
                        .load(post_image)
                        .into(mImage_paralax);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-11.991382, -77.006932);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}




















