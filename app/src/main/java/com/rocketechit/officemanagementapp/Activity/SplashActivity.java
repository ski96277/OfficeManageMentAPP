package com.rocketechit.officemanagementapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rocketechit.officemanagementapp.JavaClass.CheckNetwork;
import com.rocketechit.officemanagementapp.R;

public class SplashActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //hide action bar
        getSupportActionBar().hide();
        ButterKnife.bind(this);

        firebaseAuth = FirebaseAuth.getInstance();

        if (CheckNetwork.isInternetAvailable(this)) {
            checkUserID();
        } else {
            Toasty.error(this, "Check Internet", Toast.LENGTH_SHORT, true).show();

        }
    }


    public void checkUserID() {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            String userID = currentUser.getUid();
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = firebaseDatabase.getReference();
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child("Company").hasChild(userID)) {
                        startActivity(new Intent(SplashActivity.this, MainActivity_Admin.class));
                        finish();
                    }
                    if (dataSnapshot.child("Employee_List").hasChild(userID)) {
                        startActivity(new Intent(SplashActivity.this, MainActivity_Employee.class));
                        finish();
                    }
                    if (dataSnapshot.child("Receptionist").hasChild(userID)) {
                        Toasty.success(SplashActivity.this, "Please Login other app", Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        } else {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }
            }, 2000);
        }
    }

}
