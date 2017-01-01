package app.com.thetechnocafe.quicknewsbytes.InitialSetUp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import app.com.thetechnocafe.quicknewsbytes.HomeStream.HomeActivity;
import app.com.thetechnocafe.quicknewsbytes.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class InitialSetUpActivity extends AppCompatActivity implements InitialSetUpContract.View {

    @BindView(R.id.setup_view_pager)
    ViewPager mSetUpViewPager;

    private InitialSetUpContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_set_up);

        ButterKnife.bind(this);

        mPresenter = new InitialSetUpPresenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onStart();
    }

    @Override
    public Context getAppContext() {
        return getApplicationContext();
    }

    @Override
    public void setUpViewPager() {
        FragmentStatePagerAdapter adapter = new SetUpFragmentPagerStateAdapter(getSupportFragmentManager());
        mSetUpViewPager.setAdapter(adapter);
    }

    @Override
    public void startNewsFeed() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
