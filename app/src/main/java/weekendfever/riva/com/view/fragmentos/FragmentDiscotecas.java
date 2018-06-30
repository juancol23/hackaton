package weekendfever.riva.com.view.fragmentos;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
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
public class FragmentDiscotecas extends Fragment {

    RecyclerView mRecyclerView;
    ArticleAdapter mArticleAdapter;
    RecyclerView.LayoutManager mLayoutManager;

//    @BindView(R.id.refresh)
//    SwipeRefreshLayout mSwipeRefreshLayout;

    public FragmentDiscotecas() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_fragment_discotecas, container, false);

        mRecyclerView = view.findViewById(R.id.mRecyclerDiscteca);

//       mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
//       mSwipeRefreshLayout.setRefreshing(true);
        mRecyclerView.hasFixedSize();

        mArticleAdapter = new ArticleAdapter(getActivity().getApplicationContext());

        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());

        mRecyclerView.setAdapter(mArticleAdapter);

        mRecyclerView.setLayoutManager(mLayoutManager);

        obtenerData();

        return view;
    }

    private void obtenerData() {
        ServiceApiNew service = RetrofitInstance.getRetrofitInstance().create(ServiceApiNew.class);
        Call<ArticleResponse> articleResponseCall = service.obtenerListaAnunciosApi();
        articleResponseCall.enqueue(new Callback<ArticleResponse>() {
            @Override
            public void onResponse(Call<ArticleResponse> call, Response<ArticleResponse> response) {
                ArticleResponse listaArticle = response.body();
                ArrayList<Article> articlelista = listaArticle.getArticles();

                for (int i = 0; i<articlelista.size();i++){
                    Article articleList = articlelista.get(i);
                    Log.v("TAG_FRAGMENTO","\n"+articleList.getTitle());
                    Log.v("TAG_FRAGMENTO","\n"+articleList.getUrlToImage());
                }

                mArticleAdapter.passDataAdapter(articlelista);
//                mSwipeRefreshLayout.setRefreshing(false);

            }

            @Override
            public void onFailure(Call<ArticleResponse> call, Throwable t) {
                Log.v("TAG_FRAGMENTO","FALLÓ");

            }
        });
    }

}
