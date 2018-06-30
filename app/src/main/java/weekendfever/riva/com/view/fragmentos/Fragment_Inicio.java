package weekendfever.riva.com.view.fragmentos;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import weekendfever.riva.com.R;
import weekendfever.riva.com.adapter.ArticleAdapter;
import weekendfever.riva.com.api.network.RetrofitInstance;
import weekendfever.riva.com.api.service.ServiceApiNew;
import weekendfever.riva.com.model.Article;
import weekendfever.riva.com.model.ArticleResponse;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Inicio extends Fragment {




    public Fragment_Inicio() {
      
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View  v = inflater.inflate(R.layout.fragment_fragment_inicio, container, false);


        return v;
    }




}
