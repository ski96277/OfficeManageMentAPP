package com.rocketechit.officemanagementapp.Activity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rocketechit.officemanagementapp.FragmentClass.Employee.Employee_Home;
import com.rocketechit.officemanagementapp.FragmentClass.Employee.Employee_Profile_F;
import com.rocketechit.officemanagementapp.FragmentClass.Employee.My_Attendance_F;
import com.rocketechit.officemanagementapp.FragmentClass.Employee.ViewCalender_F;
import com.rocketechit.officemanagementapp.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import es.dmoral.toasty.Toasty;

import android.view.Menu;
import android.view.MenuItem;

public class MainActivity_Employee extends AppCompatActivity{

    Fragment fragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__employee);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//Navigation off
      /*  DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);*/

        //set default Fragment
        fragment = new Employee_Home();
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.screen_Area_For_Employee, fragment);
            fragmentTransaction.commit();
        }
    }

 /*   @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity__employee, menu);
        return true;
    }

    //menu icon action
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.view_calender_Employee:
                fragment = new ViewCalender_F();
                fragmentReplace(fragment);
                break;
            case R.id.view_profile_Employee:

                fragment = new Employee_Profile_F();
                fragmentReplace(fragment);

                break;
            case R.id.view_MY_Attendance:

                fragment = new My_Attendance_F();
                fragmentReplace(fragment);

                break;
            case R.id.change_password_Employee:
                startActivity(new Intent(this, ChangePasswordActivity.class));
                break;
            case R.id.log_out_Employee:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    // Navigation off
/*    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }*/

    //replace Fragment name
    private void fragmentReplace(Fragment fragment) {

        if (fragment != null) {

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.addToBackStack("");
            fragmentTransaction.replace(R.id.screen_Area_For_Employee, fragment);
            fragmentTransaction.commit();
        }
    }

    //call oncreate method to check  this email is verified
    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()) {

        } else {
            String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Alert");
            builder.setMessage("Click Logout and Verify Email address\nCheck this Email : " + email);
            builder.setCancelable(false);
            builder.setNegativeButton("Log Out", (dialog, which) -> {

                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                firebaseUser.sendEmailVerification().addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toasty.info(MainActivity_Employee.this, "Email check", Toasty.LENGTH_SHORT).show();
                    }
                });
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity_Employee.this, LoginActivity.class));
                finish();
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        }
    }
}
