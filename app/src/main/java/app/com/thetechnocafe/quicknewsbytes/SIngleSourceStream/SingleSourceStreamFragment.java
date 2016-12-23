package app.com.thetechnocafe.quicknewsbytes.SIngleSourceStream;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.com.thetechnocafe.quicknewsbytes.R;
import butterknife.ButterKnife;

/**
 * Created by gurleensethi on 23/12/16.
 */

public class SingleSourceStreamFragment extends Fragment implements SingleSourceStreamContract.View {
    private static final String ARG_SOURCE_ID = "sourceid";

    public Fragment getInstance(String sourceID) {
        //Create arguments
        Bundle args = new Bundle();
        args.putString(ARG_SOURCE_ID, sourceID);

        //Create fragment and set arguments
        Fragment fragment = new SingleSourceStreamFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_single_source_stream, container, false);

        //Bind butterknife
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public Context getViewContext() {
        return getContext();
    }
}
