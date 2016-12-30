package app.com.thetechnocafe.quicknewsbytes.MainHomeStream;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.com.thetechnocafe.quicknewsbytes.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gurleensethi on 30/12/16.
 */

public class MainHomeStreamFragment extends Fragment implements MainHomStreamContract.View {

    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.sources_view_pager)
    ViewPager mSourcesViewPager;

    private static final String TAG = MainHomeStreamFragment.class.getSimpleName();
    private MainHomStreamContract.Presenter mPresenter;

    public static MainHomeStreamFragment getInstance() {
        return new MainHomeStreamFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_home_stream, container, false);

        ButterKnife.bind(this, view);

        mPresenter = new MainHomeStreamPresenter(this);

        //Make view pager work with tab layout
        mTabLayout.setupWithViewPager(mSourcesViewPager);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }
}
