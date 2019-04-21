package com.rocketechit.officemanagementapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.rocketechit.officemanagementapp.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class ForgetPassWordActivity extends AppCompatActivity {

    @BindView(R.id.email_forget_Password_ET)
    EditText emailForgetPasswordET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass_word);
        ButterKnife.bind(this);


        getSupportActionBar().setTitle("Reset Password");
    }

    @OnClick(R.id.forget_Password_btn)
    public void onClick() {
        String email = emailForgetPasswordET.getText().toString();

        if (email.isEmpty()) {
            emailForgetPasswordET.requestFocus();
            emailForgetPasswordET.setError("Email ?");
            return;
        }
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toasty.info(this, "Check Email", Toasty.LENGTH_SHORT).show();
                        startActivity(new Intent(ForgetPassWordActivity.this, LoginActivity.class));
                        finish();
                    }
                });
    }
}
