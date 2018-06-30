package weekendfever.riva.com.view.fragmentos;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import weekendfever.riva.com.R;
import weekendfever.riva.com.model.Bar;
import weekendfever.riva.com.util.ui.ViewHolderStructureDesign;
import weekendfever.riva.com.view.activity.detalle.Detalle;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentKaraoke extends Fragment {


    private RecyclerView mRecyclers;
    private DatabaseReference mDatabase;
    private ProgressDialog mProgress;

    public FragmentKaraoke() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_bar, container, false);

        mProgress = new ProgressDialog(getContext());

        init(view);


        return view;
    }

    private void init(View view) {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Karaoke");
        mDatabase.keepSynced(true);

        LinearLayoutManager layoutManagerTrending
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        layoutManagerTrending.setReverseLayout(true);
        layoutManagerTrending.setStackFromEnd(true);

        mRecyclers = (RecyclerView) view.findViewById(R.id.mRecycler);
        mRecyclers.setHasFixedSize(true);

        mRecyclers.setLayoutManager(layoutManagerTrending);

        FirebaseRecyclerAdapter<Bar, ViewHolderStructureDesign> firebaseRecyclerAdapterAsesinosSeriales = new FirebaseRecyclerAdapter<Bar, ViewHolderStructureDesign>(
                Bar.class,
                R.layout.design_structure_relato_menu,
                ViewHolderStructureDesign.class,
                mDatabase

        ) {
            @Override
            protected void populateViewHolder(ViewHolderStructureDesign viewHolder, final Bar model, final int position) {
                final String post_key = getRef(position).getKey();
                viewHolder.setTitle(model.getTitle());
                viewHolder.setStatus(model.getStatus());
                viewHolder.setImage(getContext(), model.getImage());

                viewHolder.mViewStructure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mProgress.setMessage("Accediendo...");
                        // mProgress.show();
                        //Toast.makeText(getContext(),"dale "+post_key,Toast.LENGTH_SHORT).show();
                        Intent singleBlogIntent = new Intent(getContext(), Detalle.class);
                        singleBlogIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        singleBlogIntent.putExtra("blog_id", post_key);
                        singleBlogIntent.putExtra("categoria", "Karaoke");

                        startActivity(singleBlogIntent);
                        Log.v("ida","id"+post_key);
                    }
                });
            }
        };

        mRecyclers.setAdapter(firebaseRecyclerAdapterAsesinosSeriales);
    }

}
