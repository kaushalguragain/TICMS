package com.texasimaginology.ticms.NavigationViewItems.Contact;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.texasimaginology.ticms.R;


public class ContactFragment extends Fragment {

    private static final String URL ="http://www.texasintl.edu.np/contactus.html";
    WebView  webView;
    String html;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        html="<iframe src=\"https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3532.158377643052!2d85.3424053150622!3d27.712395882789853!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x39eb1970a9ff7041%3A0xfcaa45db29104458!2sTexas+International+College!5e0!3m2!1sen!2snp!4v1546001635117\" width=\"280\" height=\"280\" frameborder=\"0\" style=\"border:0\" allowfullscreen></iframe>";
        webView = (WebView) view.findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadData(html,"text/html", null);

        //init(view);
        return view;
    }

    /*private void init(View view) {
        WebView mContactView = view.findViewById(R.id.contact_view);
        mContactView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = mContactView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mContactView.loadUrl(URL);
    }*/

}
