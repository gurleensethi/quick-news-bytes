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

import app.com.thetechnocafe.quicknewsbytes.Models.SourceModel;
import app.com.thetechnocafe.quicknewsbytes.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gurleensethi on 22/12/16.
 */

public class SourcesRecyclerAdapter extends RecyclerView.Adapter<SourcesRecyclerAdapter.SourcesRecyclerViewHolder> {
    //Interface for on click callbacks
    interface SourcesEventListener {
        void onSourceItemClicked(SourceModel item);
    }

    public List<SourceModel> mSourcesList;
    private Context mContext;
    private SourcesEventListener mSourcesEventListener;

    public SourcesRecyclerAdapter(Context context, List<SourceModel> sourcesList, SourcesEventListener listener) {
        mContext = context;
        mSourcesList = sourcesList;
        mSourcesEventListener = listener;
    }

    @Override
    public SourcesRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_sources_navigation_drawer, parent, false);
        return new SourcesRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SourcesRecyclerViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return mSourcesList.size();
    }

    //View Holder for sources recycler adapter
    class SourcesRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.source_name_text_view)
        TextView mSourceNameTextView;
        @BindView(R.id.source_image_view)
        ImageView mSourceImageView;
        private int mPosition;

        public SourcesRecyclerViewHolder(View view) {
            super(view);

            //Bind butterknife
            ButterKnife.bind(this, view);

            view.setOnClickListener(this);
        }

        public void bindData(int position) {
            mPosition = position;
            mSourceNameTextView.setText(mSourcesList.get(position).getName());
            Glide.with(mContext)
                    .load(mSourcesList.get(position).getUrlsToLogos().getSmallImageUrl())
                    .into(mSourceImageView);
        }

        @Override
        public void onClick(View view) {
            mSourcesEventListener.onSourceItemClicked(mSourcesList.get(mPosition));
        }
    }

    //Change the list
    public void updateList(List<SourceModel> updatedList) {
        mSourcesList = updatedList;
    }
}
