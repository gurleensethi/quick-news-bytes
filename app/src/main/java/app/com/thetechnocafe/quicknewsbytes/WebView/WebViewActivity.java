package app.com.thetechnocafe.quicknewsbytes.WebView;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;

import app.com.thetechnocafe.quicknewsbytes.R;
import butterknife.BindView;

public class WebViewActivity extends AppCompatActivity implements WebViewContract.View {

    @BindView(R.id.web_view)
    WebView mWebView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private WebViewContract.Presenter mWebViewPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        mWebViewPresenter = new WebViewPresenter(this);

        //Set up toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
