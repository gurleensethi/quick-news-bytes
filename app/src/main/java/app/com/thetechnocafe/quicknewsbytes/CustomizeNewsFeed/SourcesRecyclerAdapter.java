package app.com.thetechnocafe.quicknewsbytes.CustomizeNewsFeed;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import app.com.thetechnocafe.quicknewsbytes.Models.SourceModel;
import app.com.thetechnocafe.quicknewsbytes.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gurleensethi on 28/12/16.
 */

public class SourcesRecyclerAdapter extends RecyclerView.Adapter<SourcesRecyclerAdapter.SourceViewHolder> {
    //Interface for callbacks
    public interface OnSourcesItemSelectedListener {
        void onSourceItemSelected(int position, SourceModel model);
    }

    private List<SourceModel> mSourcesList;
    private Context mContext;
    private OnSourcesItemSelectedListener mOnSourcesItemSelectedListener;

    public SourcesRecyclerAdapter(Context context, List<SourceModel> list, OnSourcesItemSelectedListener listener) {
        mContext = context;
        mSourcesList = list;
        mOnSourcesItemSelectedListener = listener;
    }

    @Override
    public SourceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_customize_source, parent, false);
        return new SourceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SourceViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return mSourcesList.size();
    }

    class SourceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.source_image_view)
        ImageView mSourceImageView;
        @BindView(R.id.source_name_text_view)
        TextView mSourceNameTextView;
        @BindView(R.id.selected_layer_view)
        View mSelectedLayerView;
        @BindView(R.id.selected_layer_image_view)
        ImageView mSelectedLayerImageView;
        private int mPosition;

        SourceViewHolder(View view) {
            super(view);

            view.setOnClickListener(this);

            ButterKnife.bind(this, view);
        }

        public void bindData(int position) {
            mPosition = position;

            //Load image using Glide
            Glide.with(mContext)
                    .load(mSourcesList.get(position).getUrlsToLogos().getMediumImageUrl())
                    .into(mSourceImageView);

            mSourceNameTextView.setText(mSourcesList.get(position).getName());

            toggleSelectionState();
        }

        @Override
        public void onClick(View view) {
            mOnSourcesItemSelectedListener.onSourceItemSelected(mPosition, mSourcesList.get(mPosition));
        }

        private void toggleSelectionState() {
            //Check if selected then show the selection layer, vice versa
            if (mSourcesList.get(mPosition).isSaved()) {
                mSelectedLayerView.setVisibility(View.VISIBLE);
                mSelectedLayerImageView.setVisibility(View.VISIBLE);
            } else {
                mSelectedLayerView.setVisibility(View.GONE);
                mSelectedLayerImageView.setVisibility(View.GONE);
            }
        }
    }

    public void updateList(List<SourceModel> list) {
        mSourcesList = list;
    }
}
