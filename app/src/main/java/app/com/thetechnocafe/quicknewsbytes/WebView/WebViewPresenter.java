package app.com.thetechnocafe.quicknewsbytes.WebView;

/**
 * Created by gurleensethi on 20/12/16.
 */

public class WebViewPresenter implements WebViewContract.Presenter {

    private WebViewContract.View mWebView;

    public WebViewPresenter(WebViewContract.View view) {
        mWebView = view;
    }

    @Override
    public void onStart() {

    }
}
