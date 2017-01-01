package app.com.thetechnocafe.quicknewsbytes.InitialSetUp.ViewPagerFragments.WelcomeFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import app.com.thetechnocafe.quicknewsbytes.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gurleensethi on 01/01/17.
 */

public class WelcomeFragment extends Fragment {

    @BindView(R.id.proceed_button)
    Button mProceedButton;

    public static WelcomeFragment getInstance() {
        return new WelcomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welcome_fragment, container, false);

        ButterKnife.bind(this, view);

        setUpEventListeners();

        return view;
    }

    private void setUpEventListeners() {
        mProceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Get the view pager from the activity and send to next fragment
                ViewPager viewPager = (ViewPager) getActivity().findViewById(R.id.setup_view_pager);
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            }
        });
    }
}
