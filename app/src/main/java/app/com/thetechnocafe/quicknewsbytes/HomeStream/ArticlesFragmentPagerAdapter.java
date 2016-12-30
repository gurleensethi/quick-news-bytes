package app.com.thetechnocafe.quicknewsbytes.HomeStream;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import app.com.thetechnocafe.quicknewsbytes.Models.ArticleModel;

/**
 * Created by gurleensethi on 31/12/16.
 */

public class ArticlesFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<ArticleModel> mArticlesList;

    public ArticlesFragmentPagerAdapter(FragmentManager fragmentManager, List<ArticleModel> articlesList) {
        super(fragmentManager);

        mArticlesList = articlesList;
    }

    @Override
    public Fragment getItem(int position) {
        return ArticlesPagerFragment.getInstance(mArticlesList.get(position));
    }

    @Override
    public int getCount() {
        return mArticlesList.size();
    }
}
