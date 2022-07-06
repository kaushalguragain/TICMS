package com.texasimaginology.ticms;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.texasimaginology.ticms.Login.LoginFragment;
import com.texasimaginology.ticms.R;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainFragment extends Fragment {

    private SliderLayout mSlider;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        init(view);
        return view;
    }

    @Override
    public void onStop() {
        mSlider.stopAutoCycle();
        super.onStop();
    }

    private void init(View view) {
        mSlider = view.findViewById(R.id.daimajia_slider_image);
        TextSliderView textSliderView1 = new TextSliderView(getContext());
        TextSliderView textSliderView2 = new TextSliderView(getContext());
        TextSliderView textSliderView3 = new TextSliderView(getContext());
        textSliderView1.description("").image(R.drawable.texasbanner1);
        textSliderView2.description("").image(R.drawable.texasbanner2);
        textSliderView3.description("").image(R.drawable.texasbanner3);
        mSlider.addSlider(textSliderView1);
        mSlider.addSlider(textSliderView2);
        mSlider.addSlider(textSliderView3);


    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        CheckInternetConnection checkInternetConnection = new CheckInternetConnection(getActivity());
//        checkInternetConnection.execute();
//        Log.d("NetworkStatus", checkInternetConnection.NETWORK_STATUS.toString());
//        if(!checkInternetConnection.NETWORK_STATUS){
//            Toast.makeText(getContext(), "No internet Connection", Toast.LENGTH_SHORT).show();
//        }
//    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.main, menu);
        MenuItem showNotification = menu.findItem(R.id.act_notification);
        SharedPreferences sharedPref = getActivity().getSharedPreferences("loginDetails", Context.MODE_PRIVATE);
        if (!("".equals(sharedPref.getString("token", "")))) {
            showNotification.setVisible(true);
        }else
            showNotification.setVisible(false);
    }
}
