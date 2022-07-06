package com.texasimaginology.ticms.NavigationViewItems.News;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.texasimaginology.ticms.R;


public class NewsFragment extends Fragment {

    private static final String URL ="http://www.texasintl.edu.np/category/news-events.html";
    private WebView mNewsView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        mNewsView = view.findViewById(R.id.news_view);
        mNewsView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = mNewsView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mNewsView.loadUrl(URL);
    }

}
