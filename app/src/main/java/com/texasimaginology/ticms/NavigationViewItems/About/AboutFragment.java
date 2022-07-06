package com.texasimaginology.ticms.NavigationViewItems.About;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.texasimaginology.ticms.R;


public class AboutFragment extends Fragment {

    private static final String URL ="http://www.texasintl.edu.np/our-team";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        WebView mAboutView = view.findViewById(R.id.about_view);
        mAboutView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = mAboutView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mAboutView.loadUrl(URL);
    }

}
