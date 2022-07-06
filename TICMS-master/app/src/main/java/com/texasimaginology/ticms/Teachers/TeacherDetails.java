package com.texasimaginology.ticms.Teachers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.texasimaginology.ticms.CheckError;
import com.texasimaginology.ticms.Teachers.TeacherListDto;
import com.texasimaginology.ticms.R;
import com.texasimaginology.ticms.Users.UserDetails;

import static java.lang.String.valueOf;

public class TeacherDetails extends AppCompatActivity {
    TextView teacherFullName;
    TextView teacherEmail, teacherPhone, teacherZone, teacherDistrict, teacherVdc,teacherWard;
    TeacherListDto.Content teacherListDto;
    private final String TAG = UserDetails.class.getSimpleName();
    ImageView show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_details);
        Toolbar toolbar = findViewById(R.id.teacher_details_toolbar);
        toolbar.setTitle("Teacher Profile");
        setSupportActionBar(toolbar);

        teacherFullName=findViewById(R.id.teacher_full_name);
        teacherEmail=findViewById(R.id.teacher_email);
        teacherPhone=findViewById(R.id.teacher_phone);
        teacherDistrict= findViewById(R.id.district);
        teacherVdc = findViewById(R.id.vdc);
        teacherWard = findViewById(R.id.ward);
        teacherZone = findViewById(R.id.zone);
        show=findViewById(R.id.teacher_image_view);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            teacherFullName.setText(bundle.get("firstName")+" "+bundle.get("lastName"));
            teacherEmail.setText(bundle.get("email").toString());
            teacherPhone.setText(bundle.get("phone").toString());
            teacherDistrict.setText(bundle.get("district").toString());
            teacherVdc.setText(bundle.get("vdc").toString());
            teacherWard.setText(bundle.get("ward").toString());
            teacherZone.setText(bundle.get("zone").toString());

            String picUrl=String.valueOf(bundle.get("profilePicture"));

            if("----".equals(picUrl)){
                Picasso.with(TeacherDetails.this)
                        .load(R.drawable.user)
                        .into(show);
            }else{
                Picasso.with(TeacherDetails.this)
                        .load(picUrl)
                        .into(show);
            }
            //show.setImageResource(R.drawable.user);
            /*String stringPicture=valueOf(bundle.get("profilePicture"));

            //Decode Base64 to Bitmap and display in circle view
            if(" ".equals(stringPicture)) {
                show.setImageResource(R.drawable.user);
            }else
            {
                byte[] picCode = Base64.decode(stringPicture, Base64.DEFAULT);
                Bitmap pic = BitmapFactory.decodeByteArray(picCode, 0, picCode.length);
                show.setImageBitmap(pic);
            }*/
            Log.d(TAG, "inside bundle not null");
        } else {
            Log.e(TAG, "error receiving intent");
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
