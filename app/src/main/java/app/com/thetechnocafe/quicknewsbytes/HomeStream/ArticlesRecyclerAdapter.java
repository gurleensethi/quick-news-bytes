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

import app.com.thetechnocafe.quicknewsbytes.Database.DataManager;
import app.com.thetechnocafe.quicknewsbytes.Models.ArticleModel;
import app.com.thetechnocafe.quicknewsbytes.Models.SourceModel;
import app.com.thetechnocafe.quicknewsbytes.R;
import app.com.thetechnocafe.quicknewsbytes.Utils.DateFormattingUtils;
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
        @BindView(R.id.author_name_text_view)
        TextView mAuthorNameTextView;
        @BindView(R.id.time_ago_text_view)
        TextView mTimeAgoTextView;

        public ArticlesViewHolder(View view) {
            super(view);

            //Bind butterknife
            ButterKnife.bind(this, view);
        }

        public void bindData(int position) {
            mTitleTextView.setText(mList.get(position).getTitle());
            mDescriptionTextView.setText(mList.get(position).getDescription());
            mAuthorNameTextView.setText(mList.get(position).getAuthorName());
            mTimeAgoTextView.setText(DateFormattingUtils.getInstance().convertToTimeElapsedString(mContext, mList.get(position).getPublishedAt()));

            //Load the images with Glide
            Glide.with(mContext)
                    .load(mList.get(position).getUrlToImage())
                    .into(mArticleImageView);

            //Find corresponding source model
            SourceModel source = DataManager.getInstance(mContext).getSourceFromId(mList.get(position).getSourceId());

            if (source != null) {
                Glide.with(mContext)
                        .load(source.getUrlsToLogos().getMediumImageUrl())
                        .into(mSourceImageView);

                mSourceTextView.setText(source.getName());
            }
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

    //Change the list of articles
    public void updateList(List<ArticleModel> list) {
        mList = list;
    }
}
