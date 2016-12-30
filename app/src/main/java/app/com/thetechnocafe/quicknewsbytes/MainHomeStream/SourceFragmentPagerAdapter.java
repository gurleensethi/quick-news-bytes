package app.com.thetechnocafe.quicknewsbytes.MainHomeStream;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import app.com.thetechnocafe.quicknewsbytes.Models.SourceModel;
import app.com.thetechnocafe.quicknewsbytes.SourceNewsStream.SourceNewsStreamFragment;

/**
 * Created by gurleensethi on 30/12/16.
 */

public class SourceFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<SourceModel> mSourcesList;

    public SourceFragmentPagerAdapter(FragmentManager fragmentManager, List<SourceModel> sourceList) {
        super(fragmentManager);

        mSourcesList = sourceList;
    }

    @Override
    public Fragment getItem(int position) {
        return SourceNewsStreamFragment.getInstance(mSourcesList.get(position).getID());
    }

    @Override
    public int getCount() {
        return mSourcesList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mSourcesList.get(position).getName();
    }
}
