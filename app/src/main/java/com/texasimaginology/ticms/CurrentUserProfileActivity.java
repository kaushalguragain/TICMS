package com.texasimaginology.ticms;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class CurrentUserProfileActivity extends AppCompatActivity {
    TextView email,userName,userRole,name,showUsername;
    ImageView show;
    Bitmap pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        init();
    }

    private void init() {
        name = findViewById(R.id.user_full_name);
        email=findViewById(R.id.user_email);
        userName=findViewById(R.id.user_username);
        userRole=findViewById(R.id.user_role);
        show=findViewById(R.id.user_image_view);
        showUsername=findViewById(R.id.username);
        Typeface robotoNormalFont= Typeface.createFromAsset(getAssets(),"fonts/Roboto-Regular.ttf");
        name.setTypeface(robotoNormalFont);
        email.setTypeface(robotoNormalFont);
        userName.setTypeface(robotoNormalFont);
        userRole.setTypeface(robotoNormalFont);

        SharedPreferences sharedPreferences = getSharedPreferences("loginDetails" , Context.MODE_PRIVATE);
        name.setText(sharedPreferences.getString("firstname", "") + " " + sharedPreferences.getString("lastname",""));
        email.setText(sharedPreferences.getString("email",""));
        userName.setText("@"+sharedPreferences.getString("username",""));
        showUsername.setText("@"+sharedPreferences.getString("username",""));
        userRole.setText(sharedPreferences.getString("userrole",""));
        String picUrl=sharedPreferences.getString("profilePicture","nopic");

        if("nopic".equals(picUrl)){
            Picasso.with(this)
                    .load(R.drawable.user)
                    .into(show);
        }else{
            Picasso.with(this)
                    .load(picUrl)
                    .into(show);
        }

       // show.setImageResource(R.drawable.user);
        /*String stringPicture=sharedPreferences.getString("profilePicture", "");
        Log.d("picCheck:::",stringPicture.toString());
        //Display profile Picture of current logged in user
        if("nopic".equals(stringPicture)) {
            show.setImageResource(R.drawable.user);
        }else
        {
            byte[] picCode = Base64.decode(stringPicture, Base64.DEFAULT);
            Bitmap pic = BitmapFactory.decodeByteArray(picCode, 0, picCode.length);
            show.setImageBitmap(pic);
        }*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

}
