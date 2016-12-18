package app.com.thetechnocafe.quicknewsbytes.HomeStream;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import app.com.thetechnocafe.quicknewsbytes.Models.ArticleModel;
import app.com.thetechnocafe.quicknewsbytes.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gurleensethi on 18/12/16.
 */

public class ArticlesRecyclerAdapter extends RecyclerView.Adapter<ArticlesRecyclerAdapter.ArticlesViewHolder> {
    private Context mContext;
    private List<ArticleModel> mList;

    public ArticlesRecyclerAdapter(Context context, List<ArticleModel> list) {
        mContext = context;
        mList = list;
    }

    //View holder for recycler view
    class ArticlesViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title_text_view)
        TextView mTitleTextView;
        @BindView(R.id.description_text_view)
        TextView mDescriptionTextView;
        @BindView(R.id.source_name_text_view)
        TextView mSourceTextView;
        @BindView(R.id.source_image_view)
        ImageView mSourceImageView;
        @BindView(R.id.article_image_view)
        ImageView mArticleImageView;

        public ArticlesViewHolder(View view) {
            super(view);

            //Bind butterknife
            ButterKnife.bind(this, view);
        }

        public void bindData(int position) {
            mTitleTextView.setText(mList.get(position).getTitle());
            mDescriptionTextView.setText(mList.get(position).getDescription());

            //Load the images with Glide
            Glide.with(mContext)
                    .load(mList.get(position).getUrlToImage())
                    .into(mArticleImageView);
        }
    }

    @Override
    public ArticlesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_news_list, parent, false);
        return new ArticlesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ArticlesViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
