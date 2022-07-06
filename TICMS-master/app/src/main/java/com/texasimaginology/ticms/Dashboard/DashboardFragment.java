package com.texasimaginology.ticms.Dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.texasimaginology.ticms.Courses.CourseListFragment;
import com.texasimaginology.ticms.CurrentUserProfileActivity;
import com.texasimaginology.ticms.Subjects.SubjectListFragment;
import com.texasimaginology.ticms.MainFragment;
import com.texasimaginology.ticms.Users.UserListFragment;
import com.texasimaginology.ticms.R;
import com.texasimaginology.ticms.Students.StudentListFragment;
import com.texasimaginology.ticms.Teachers.TeacherListFragment;


public class DashboardFragment extends Fragment {
    private NavigationView navigationView;
    private ImageView iBtnUsers, iBtnTeachers, iBtnSubjects, iBtnStudents,iBtnCourses;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        init(view);

        navigationView = getActivity().findViewById(R.id.nav_view);
        //navigationView.setNavigationItemSelectedListener();


        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        menu.clear();
        inflater.inflate(R.menu.main, menu);
        MenuItem userProfile = menu.findItem(R.id.profile);
        MenuItem home = menu.findItem(R.id.main_fragment);
        MenuItem showNotification = menu.findItem(R.id.act_notification);
        showNotification.setVisible(true);
        home.setVisible(true);
        //MenuItem notification = menu.findItem(R.id.action_Notifications);
        userProfile.setVisible(true);
        //notification.setVisible(false);

        home.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, new MainFragment());
                fragmentTransaction.commit();
                return true;
            }
        });

        userProfile.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent profileIntent = new Intent(getContext(), CurrentUserProfileActivity.class);
                startActivity(profileIntent);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }
/*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MenuItem =

        *//*userProfile = menu.findItem(R.id.profile);
        userProfile.setVisible(true);*//*
        if(item.getItemId() == userProfile){

        }

        return super.onOptionsItemSelected(item);
    }*/

    private void init(View view) {
        iBtnUsers = view.findViewById(R.id.users);
        iBtnSubjects = view.findViewById(R.id.subjects);
        iBtnStudents = view.findViewById(R.id.students);
        iBtnTeachers = view.findViewById(R.id.teachers);
        iBtnCourses=view.findViewById(R.id.courses);

        iBtnUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.addToBackStack("userlisting");
                ft.replace(R.id.content_frame, new UserListFragment());
                ft.commit();
            }
        });

        iBtnStudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, new StudentListFragment());
                ft.addToBackStack("studentlisting");
                ft.commit();
            }
        });

        iBtnTeachers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, new TeacherListFragment());
                ft.addToBackStack("teacherlisting");
                ft.commit();
            }
        });

        iBtnSubjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, new SubjectListFragment());
                ft.addToBackStack("subjectListing");
                ft.commit();
            }
        });
        iBtnCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, new CourseListFragment());
                ft.addToBackStack("courseListing");
                ft.commit();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Dashboard");

    }
}
