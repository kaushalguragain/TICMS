package com.texasimaginology.ticms.Users;

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
import com.texasimaginology.ticms.CheckError;
import com.texasimaginology.ticms.R;

public class UserDetails extends AppCompatActivity {
    TextView userFullName, userEmail,userUsername,userRole, userPhone, showUsername;
    ImageView show;

    public static final String STUDENT = "users";
    UserListDto.Content userListDto;
    private final String TAG = UserDetails.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = findViewById(R.id.user_details_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("User Profile");


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        userFullName = findViewById(R.id.user_full_name);
        userEmail = findViewById(R.id.user_email);
        userUsername = findViewById(R.id.user_username);
        userPhone = findViewById(R.id.user_phone);
        userRole = findViewById(R.id.user_role);
        showUsername = findViewById(R.id.username);
        show=findViewById(R.id.user_image_view);
        Typeface robotoRegularFont=Typeface.createFromAsset(getAssets(),"fonts/Roboto-Regular.ttf");
        userFullName.setTypeface(robotoRegularFont);
        userEmail.setTypeface(robotoRegularFont);
        userUsername.setTypeface(robotoRegularFont);
        userRole.setTypeface(robotoRegularFont);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String firstName= (String)bundle.get("firstName");
            String lastName=(String)bundle.get("lastName");
            userFullName.setText(CheckError.checkNullString(firstName)+" "+CheckError.checkNullString(lastName));
            userEmail.setText(CheckError.checkNullString(bundle.get("email").toString()));
            //userPhone.setText(CheckError.checkNullString(bundle.get("phone").toString()));
            userRole.setText(CheckError.checkNullString(bundle.get("userRole").toString()));
            userUsername.setText("@"+CheckError.checkNullString(String.valueOf(bundle.get("userName"))));
            showUsername.setText("@"+CheckError.checkNullString(String.valueOf(bundle.get("userName"))));
            String picUrl=CheckError.checkNullString(String.valueOf(bundle.get("profilePicture")));

            if("----".equals(picUrl)){
                Picasso.with(this)
                        .load(R.drawable.user)
                        .into(show);
            }else{
                Picasso.with(this)
                        .load(picUrl)
                        .into(show);
            }

           // show.setImageResource(R.drawable.user);
            //Decode Base64 to Bitmap and display in circle view
                //String stringPicture=bundle.get("profilePicture").toString();
                /*if(" ".equals(stringPicture)) {
                  show.setImageResource(R.drawable.user);
                }else
                {
                    Log.d("ConvertingTo", "base 64");
                    byte[] picCode = Base64.decode(stringPicture, Base64.DEFAULT);
                    Bitmap pic = BitmapFactory.decodeByteArray(picCode, 0, picCode.length);
                    show.setImageBitmap(pic);
                }*/


//            userListDto = bundle.getParcelable(STUDENT);
            Log.d(TAG, "inside bundle not null");
            /*Log.d(TAG,"student info"+userListDto.getContents().getFirstName());*/
        } else {
            Log.e(TAG, "error receiving intent");
        }

        /*Toast.makeText(this, userListDto.getContents().getFirstName(), Toast.LENGTH_SHORT).show();*/
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        //Log.d("FirstName:", userListDto.getFirstName());
//        Log.d("Lastname:", userListDto.getLastName());
//        userFullName.setText(userListDto.getFirstName() + " " + userListDto.getLastName());
//        userEmail.setText(userListDto.getEmail());
//        userUsername.setText(userListDto.getEmail());
//        userRole.setText(userListDto.getUserRole());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
