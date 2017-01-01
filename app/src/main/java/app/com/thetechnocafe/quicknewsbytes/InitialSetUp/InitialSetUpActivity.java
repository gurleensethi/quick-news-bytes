package app.com.thetechnocafe.quicknewsbytes.InitialSetUp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import app.com.thetechnocafe.quicknewsbytes.R;
import butterknife.ButterKnife;

public class InitialSetUpActivity extends AppCompatActivity implements InitialSetUpContract.View {

    private InitialSetUpContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_set_up);

        ButterKnife.bind(this);

        mPresenter = new InitialSetUpPresenter(this);
    }

    @Override
    public Context getAppContext() {
        return getApplicationContext();
    }
}
