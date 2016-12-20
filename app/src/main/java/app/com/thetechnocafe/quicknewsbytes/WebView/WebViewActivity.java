package app.com.thetechnocafe.quicknewsbytes.WebView;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import app.com.thetechnocafe.quicknewsbytes.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class WebViewActivity extends AppCompatActivity implements WebViewContract.View {

    @BindView(R.id.web_view)
    WebView mWebView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private WebViewContract.Presenter mWebViewPresenter;
    public static final String WEB_VIEW_URL_EXTRA = "webviewurlextra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        //Bind butterknife
        ButterKnife.bind(this);

        mWebViewPresenter = new WebViewPresenter(this);

        //Set up toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        configureWebView();
    }

    private void configureWebView() {
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new CustomWebViewClient());
    }

    @Override
    protected void onResume() {
        super.onResume();
        mWebViewPresenter.onStart();
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

    @Override
    public void loadURLInWebView() {
        //Get url from intent and load into the web view
        String URL = getIntent().getStringExtra(WEB_VIEW_URL_EXTRA);
        mWebView.loadUrl(URL);
    }

    /**
     * Custom Web View Client
     * If user clicks on a link on the page the page will be loaded in the same webview
     * */
    private class CustomWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
