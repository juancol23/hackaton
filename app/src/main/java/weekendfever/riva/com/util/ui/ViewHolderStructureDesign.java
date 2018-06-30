package weekendfever.riva.com.util.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import weekendfever.riva.com.R;

public class ViewHolderStructureDesign extends RecyclerView.ViewHolder{

    public View mViewStructure;
    public TextView mItem_recycler_structure_title;
//    public TextView mItem_recycler_structure_category;
    public TextView mItem_recycler_structure_status;
    public ImageView mPost_image;

    public ViewHolderStructureDesign(View itemView) {
        super(itemView);
        mViewStructure = itemView ;
    }

    public void setTitle(String title){
        //mItem_recycler_structure_title.setTypeface(Pacifico);
        mItem_recycler_structure_title = mViewStructure.findViewById(R.id.item_recycler_structure_title);
        mItem_recycler_structure_title.setText(title);
    }

//    public void setCatergory(String category){
//        mItem_recycler_structure_category = mViewStructure.findViewById(R.id.item_recycler_structure_category);
//        mItem_recycler_structure_category.setText(category);
//    }
    public void setStatus(String status){
        mItem_recycler_structure_status = mViewStructure.findViewById(R.id.item_recycler_structure_status);
        mItem_recycler_structure_status.setText(status);
    }

    public void setImage(Context context, String image){
        mPost_image = mViewStructure.findViewById(R.id.item_recycler_structure_imagen);

        Glide.with(context)
                .load(image)
                .thumbnail(Glide.with(context).load(R.drawable.item_placeholder))
                .into(mPost_image);

    }

}
