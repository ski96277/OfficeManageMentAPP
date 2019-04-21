package com.rocketechit.officemanagementapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.login_email_ET)
    EditText loginEmailET;
    @BindView(R.id.login_password)
    EditText loginPassword;
    @BindView(R.id.submit_login_btn)
    Button lin;
    @BindView(R.id.Go_signupTV)
    TextView GoSignupTV;

    FirebaseAuth firebaseAuth;
    @BindView(R.id.login_progress)
    ProgressBar loginProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//hide action bar
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        initialize();
    }

    private void initialize() {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @OnClick({R.id.submit_login_btn, R.id.Go_signupTV})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.submit_login_btn:
                String email = loginEmailET.getText().toString();
                String password = loginPassword.getText().toString();
                if (email.isEmpty()) {
                    loginEmailET.requestFocus();
                    loginEmailET.setError("Email ?");
                    return;
                }
                if (password.isEmpty()) {
                    loginPassword.requestFocus();
                    loginPassword.setError("password ?");
                    return;
                }
                //show login progress bar
                loginProgress.setVisibility(View.VISIBLE);
                if (CheckNetwork.isInternetAvailable(this)) {
                    firebaseAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(this, task -> {
                                if (task.isSuccessful()) {
                                    if (CheckNetwork.isInternetAvailable(this)) {
                                        checkUserID();
                                    } else {
                                        Toasty.error(this, "Internet Error", Toast.LENGTH_SHORT, true).show();
                                        loginProgress.setVisibility(View.GONE);
                                    }
                                } else {
                                    loginProgress.setVisibility(View.GONE);
                                    Toasty.error(this, "Failed to Login", Toast.LENGTH_SHORT, true).show();

                                }
                            });
                } else {
                    Toasty.error(this, "NetWork Error", Toast.LENGTH_SHORT, true).show();
                    loginProgress.setVisibility(View.GONE);

                }
                break;
            case R.id.Go_signupTV:
                startActivity(new Intent(this, SignUPActivity.class));

                break;
        }
    }

    //check user after login
    public void checkUserID() {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            String userID = currentUser.getUid();
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = firebaseDatabase.getReference();
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child("Company").hasChild(userID)) {
                        loginProgress.setVisibility(View.GONE);
                        startActivity(new Intent(LoginActivity.this, MainActivity_Admin.class));
                        finish();
                    }
                    if (dataSnapshot.child("Employee_List").hasChild(userID)) {
                        Toasty.success(LoginActivity.this, "Success", Toasty.LENGTH_SHORT).show();
                        loginProgress.setVisibility(View.GONE);
                        startActivity(new Intent(LoginActivity.this, MainActivity_Employee.class));
                        finish();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }
    }

    @OnClick(R.id.forget_password_TV)
    public void onClick() {
        startActivity(new Intent(LoginActivity.this, ChangePasswordActivity.class));
        finish();
    }
}
