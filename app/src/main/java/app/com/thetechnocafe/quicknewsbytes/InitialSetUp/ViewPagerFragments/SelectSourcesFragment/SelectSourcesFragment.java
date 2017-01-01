package app.com.thetechnocafe.quicknewsbytes.InitialSetUp.ViewPagerFragments.SelectSourcesFragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

import app.com.thetechnocafe.quicknewsbytes.CustomizeNewsFeed.SourcesRecyclerAdapter;
import app.com.thetechnocafe.quicknewsbytes.HomeStream.HomeActivity;
import app.com.thetechnocafe.quicknewsbytes.Models.SourceModel;
import app.com.thetechnocafe.quicknewsbytes.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gurleensethi on 01/01/17.
 */

public class SelectSourcesFragment extends Fragment implements SelectSourcesFragmentContract.View {

    @BindView(R.id.button_finish)
    FloatingActionButton mFinishButton;
    @BindView(R.id.sources_recycler_view)
    RecyclerView mSourcesRecyclerView;
    @BindView(R.id.sources_search_edit_text)
    EditText mSearchEditText;
    @BindView(R.id.linear_layout)
    LinearLayout mContentLinearLayout;
    @BindView(R.id.error_linear_layout)
    LinearLayout mErrorLinearLayout;
    @BindView(R.id.retry_button)
    Button mRetryButton;

    private static int RECYCLER_VIEW_GRID_SIZE = 2;
    private int mLastSelectedPosition = -1;
    private SelectSourcesFragmentContract.Presenter mPresenter;
    private SourcesRecyclerAdapter mSourcesRecyclerAdapter;

    public static SelectSourcesFragment getInstance() {
        return new SelectSourcesFragment();
    }

    @Override
    public Context getAppContext() {
        return getActivity().getApplicationContext();
    }

    @Override
    public void displaySourcesList(List<SourceModel> list) {
        setUpOrRefreshRecyclerView(list);
    }

    @Override
    public void displayError() {
        mErrorLinearLayout.setVisibility(View.VISIBLE);
        mContentLinearLayout.setVisibility(View.GONE);
        mFinishButton.setVisibility(View.GONE);
    }

    @Override
    public void startNewsFeed() {
        Intent intent = new Intent(getContext(), HomeActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void displayToast(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_sources, container, false);

        ButterKnife.bind(this, view);

        mPresenter = new SelectSourcesFragmentPresenter(this);

        setUpEventListeners();

        return view;
    }

    private void setUpEventListeners() {
        mRetryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.retryRequest();
                Toast.makeText(getContext(), R.string.retrying, Toast.LENGTH_SHORT).show();
            }
        });

        mSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mPresenter.refreshListOnSearch(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mFinishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.checkForSubmission();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.onStart();
    }

    private void setUpOrRefreshRecyclerView(List<SourceModel> list) {
        if (mSourcesRecyclerAdapter == null) {
            mSourcesRecyclerAdapter = new SourcesRecyclerAdapter(getContext(), list, new SourcesRecyclerAdapter.OnSourcesItemSelectedListener() {
                @Override
                public void onSourceItemSelected(int position, SourceModel model) {
                    mLastSelectedPosition = position;
                    mPresenter.onSourceItemSelected(model, mSearchEditText.getText().toString());
                }
            });
            mSourcesRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), RECYCLER_VIEW_GRID_SIZE));
            mSourcesRecyclerView.setAdapter(mSourcesRecyclerAdapter);

            mErrorLinearLayout.setVisibility(View.GONE);
            mContentLinearLayout.setVisibility(View.VISIBLE);
            mFinishButton.setVisibility(View.VISIBLE);
        } else {
            mSourcesRecyclerAdapter.updateList(list);

            //Check if the item was clicked or list obtained from presenter
            if (mLastSelectedPosition >= 0) {
                mSourcesRecyclerAdapter.notifyItemChanged(mLastSelectedPosition);

                //Reset value back to -1 so that next time if the search is initiated
                //execution does't enters back into this branch because we would want to call notifyDataSetChanged()
                //due to change in the complete list
                mLastSelectedPosition = -1;
            } else {
                mSourcesRecyclerAdapter.notifyDataSetChanged();
            }
        }
    }
}
