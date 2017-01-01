package app.com.thetechnocafe.quicknewsbytes.InitialSetUp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import app.com.thetechnocafe.quicknewsbytes.InitialSetUp.ViewPagerFragments.SelectSourcesFragment.SelectSourcesFragment;
import app.com.thetechnocafe.quicknewsbytes.InitialSetUp.ViewPagerFragments.WelcomeFragment.WelcomeFragment;

/**
 * Created by gurleensethi on 01/01/17.
 */

public class SetUpFragmentPagerStateAdapter extends FragmentStatePagerAdapter {

    public SetUpFragmentPagerStateAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: {
                return WelcomeFragment.getInstance();
            }
            case 1: {
                return SelectSourcesFragment.getInstance();
            }
            default: {
                return null;
            }
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
