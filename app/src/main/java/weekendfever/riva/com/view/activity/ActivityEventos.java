package weekendfever.riva.com.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import weekendfever.riva.com.R;
import weekendfever.riva.com.model.Bar;
import weekendfever.riva.com.util.ui.ViewHolderStructureDesign;
import weekendfever.riva.com.util.ui.ViewHolderStructureDesignEventos;
import weekendfever.riva.com.view.activity.detalle.Detalle;

public class ActivityEventos extends AppCompatActivity {


    private RecyclerView mRecyclers;
    private DatabaseReference mDatabase;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos);

        mProgress = new ProgressDialog(this);

        init();
    }
    private void init() {


        setToolbar("Eventos");

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Eventos");
        mDatabase.keepSynced(true);

        LinearLayoutManager layoutManagerTrending
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);

        layoutManagerTrending.setReverseLayout(true);
        layoutManagerTrending.setStackFromEnd(true);

        mRecyclers = (RecyclerView) findViewById(R.id.mRecycler);
        mRecyclers.setHasFixedSize(true);

        mRecyclers.setLayoutManager(layoutManagerTrending);

        FirebaseRecyclerAdapter<Bar, ViewHolderStructureDesignEventos> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Bar, ViewHolderStructureDesignEventos>(
                Bar.class,
                R.layout.design_structure_eventos_menu,
                ViewHolderStructureDesignEventos.class,
                mDatabase

        ) {
            @Override
            protected void populateViewHolder(ViewHolderStructureDesignEventos viewHolder, Bar model, int position) {
                final String post_key = getRef(position).getKey();
                viewHolder.setTitle(model.getTitle());
                viewHolder.setStatus(model.getStatus());
                viewHolder.setImage(getApplicationContext(), model.getImage());

                viewHolder.mViewStructure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mProgress.setMessage("Accediendo...");
                        // mProgress.show();
                        //Toast.makeText(getContext(),"dale "+post_key,Toast.LENGTH_SHORT).show();
                        Intent singleBlogIntent = new Intent(getApplicationContext(), Detalle.class);
                        singleBlogIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        singleBlogIntent.putExtra("blog_id", post_key);
                        singleBlogIntent.putExtra("categoria", "Eventos");
                        startActivity(singleBlogIntent);
                        Log.v("ida","id"+post_key);
                    }
                });
            }

        };

        mRecyclers.setAdapter(firebaseRecyclerAdapter);
    }

    private void setToolbar(String title) {
        // Añadir la Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);

    }
}
