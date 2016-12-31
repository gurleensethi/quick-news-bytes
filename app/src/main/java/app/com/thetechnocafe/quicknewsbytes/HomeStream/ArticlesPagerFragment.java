package app.com.thetechnocafe.quicknewsbytes.HomeStream;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import app.com.thetechnocafe.quicknewsbytes.Models.ArticleModel;
import app.com.thetechnocafe.quicknewsbytes.R;
import app.com.thetechnocafe.quicknewsbytes.WebView.WebViewActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gurleensethi on 31/12/16.
 */

public class ArticlesPagerFragment extends Fragment {

    @BindView(R.id.article_image_view)
    ImageView mArticleImageView;
    @BindView(R.id.title_text_view)
    TextView mTitleTextView;

    private static final String ARGS_ARTICLE_MODEL = "articlemodel";
    private ArticleModel ARTICLE_MODEL;
    private String ARTICLE_IMAGE_URL;
    private String ARTICLE_TITLE;

    public static ArticlesPagerFragment getInstance(ArticleModel model) {
        //Create arguments for fragment
        Bundle args = new Bundle();
        args.putParcelable(ARGS_ARTICLE_MODEL, model);

        //Create fragment and apply arguments
        ArticlesPagerFragment fragment = new ArticlesPagerFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_articles_view_pager, container, false);

        ButterKnife.bind(this, view);

        //Get the article model
        ARTICLE_MODEL = getArguments().getParcelable(ARGS_ARTICLE_MODEL);
        if (ARTICLE_MODEL != null) {
            ARTICLE_IMAGE_URL = ARTICLE_MODEL.getUrlToImage();
            ARTICLE_TITLE = ARTICLE_MODEL.getTitle();
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra(WebViewActivity.WEB_VIEW_PARCELABLE_EXTRA, ARTICLE_MODEL);
                getActivity().startActivity(intent);
            }
        });

        bindData();

        return view;
    }

    private void bindData() {
        Glide.with(getActivity())
                .load(ARTICLE_IMAGE_URL)
                .into(mArticleImageView);

        mTitleTextView.setText(ARTICLE_TITLE);

    }
}
