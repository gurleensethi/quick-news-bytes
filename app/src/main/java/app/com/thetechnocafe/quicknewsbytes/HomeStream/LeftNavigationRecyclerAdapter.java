package app.com.thetechnocafe.quicknewsbytes.HomeStream;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import app.com.thetechnocafe.quicknewsbytes.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gurleensethi on 24/12/16.
 */

public class LeftNavigationRecyclerAdapter extends RecyclerView.Adapter<LeftNavigationRecyclerAdapter.LeftNavigationRecyclerViewHolder> {
    //Interface for onclick callbacks
    public interface OnOptionItemSelectedListener {
        void onOptionClicked(String string);
    }

    private String[] mOptions;
    private Context mContext;
    private OnOptionItemSelectedListener mOnOptionItemSelectedListener;

    public LeftNavigationRecyclerAdapter(Context context, String[] options, OnOptionItemSelectedListener listener) {
        mOptions = options;
        mContext = context;
        mOnOptionItemSelectedListener = listener;
    }

    @Override
    public LeftNavigationRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_left_navigation, parent, false);
        return new LeftNavigationRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LeftNavigationRecyclerViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return mOptions.length;
    }

    //View holder
    class LeftNavigationRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.option_text_view)
        TextView mOptionTextView;
        private int mPosition;

        LeftNavigationRecyclerViewHolder(View view) {
            super(view);

            view.setOnClickListener(this);

            //Bind butterknife
            ButterKnife.bind(this, view);
        }

        public void bindData(int position) {
            mPosition = position;

            mOptionTextView.setText(mOptions[position]);
        }

        @Override
        public void onClick(View view) {
            mOnOptionItemSelectedListener.onOptionClicked(mOptions[mPosition]);
        }
    }
}
