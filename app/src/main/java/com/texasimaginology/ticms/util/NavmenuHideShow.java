package com.texasimaginology.ticms.util;

import android.support.design.widget.NavigationView;
import android.view.Menu;
import android.view.MenuItem;

import com.texasimaginology.ticms.R;

public class NavmenuHideShow {

    public static void showHideNavMenuAccordingToLoginRole(NavigationView navigationView, String loginType) {
        Menu menu = navigationView.getMenu();
        MenuItem login = menu.findItem(R.id.nav_login);
        MenuItem logout = menu.findItem(R.id.nav_logout);
        MenuItem sendNotification = menu.findItem(R.id.nav_send_notification);
        MenuItem classRoutine = menu.findItem(R.id.nav_classRoutine);
        MenuItem myRoutine = menu.findItem(R.id.nav_my_routine);
        MenuItem counseling = menu.findItem(R.id.nav_counseling);
        MenuItem dashboard = menu.findItem(R.id.nav_dashboard);
        login.setVisible(false);
        logout.setVisible(true);

        switch (loginType){
            case "ADMIN":
                dashboard.setVisible(true);
                counseling.setVisible(true);
                sendNotification.setVisible(true);
                classRoutine.setVisible(true);
                //Invisible item to admin
                myRoutine.setVisible(false);
                break;

            case "SUPERADMIN":
                dashboard.setVisible(true);
                counseling.setVisible(true);
                sendNotification.setVisible(true);
                classRoutine.setVisible(true);
                //Invisible item to Super admin
                myRoutine.setVisible(false);
                break;

            case "TEACHER":
                myRoutine.setVisible(true);

                //Invisible item to admin
                dashboard.setVisible(false);
                sendNotification.setVisible(false);
                classRoutine.setVisible(false);
                counseling.setVisible(false);

                break;

            case "STUDENT":
                classRoutine.setVisible(true);
                dashboard.setVisible(true);

                //Invisible item to admin
                myRoutine.setVisible(false);
                sendNotification.setVisible(false);
                counseling.setVisible(false);
                break;

        }


    }
}
