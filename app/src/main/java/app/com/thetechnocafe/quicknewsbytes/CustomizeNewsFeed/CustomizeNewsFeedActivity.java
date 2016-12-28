package app.com.thetechnocafe.quicknewsbytes.CustomizeNewsFeed;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import app.com.thetechnocafe.quicknewsbytes.R;
import butterknife.ButterKnife;

public class CustomizeNewsFeedActivity extends AppCompatActivity implements CustomizeNewFeedContract.View {

    private CustomizeNewFeedContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize_news_feed);

        ButterKnife.bind(this);

        mPresenter = new CustomizeNewsFeedPresenter(this);
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
