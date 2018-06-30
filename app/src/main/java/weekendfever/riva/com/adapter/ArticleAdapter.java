package weekendfever.riva.com.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import weekendfever.riva.com.R;
import weekendfever.riva.com.model.Article;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder>{

    ArrayList<Article> mData;
    Context context;

    public ArticleAdapter(Context context) {
        mData = new ArrayList<>();
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_design_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Article article = mData.get(position);
        holder.mTitle.setText(article.getTitle());
        Glide.with(context).load(article.getUrlToImage()).into(holder.mImage);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void passDataAdapter(ArrayList<Article> articlelista) {
        mData.addAll(articlelista);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.design_item_title)
        TextView mTitle;

        @BindView(R.id.design_item_image)
        ImageView mImage;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }
}