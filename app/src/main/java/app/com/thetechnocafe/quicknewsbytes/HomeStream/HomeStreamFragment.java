package app.com.thetechnocafe.quicknewsbytes.HomeStream;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.com.thetechnocafe.quicknewsbytes.Networking.NetworkRequests;
import app.com.thetechnocafe.quicknewsbytes.R;

/**
 * Created by gurleensethi on 18/12/16.
 */

public class HomeStreamFragment extends Fragment implements HomeStreamContract.View, NetworkRequests.SourceNewsListener {

    private HomeStreamContract.Presenter mPresenter;

    public static HomeStreamFragment getInstance() {
        return new HomeStreamFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_stream, container, false);

        mPresenter = new HomeStreamPresenter(this);

        new NetworkRequests().fetchNewsFromSource(this, "techcrunch");

        return view;
    }

    @Override
    public void onNewsFetched(boolean isSuccessful) {

    }
}
