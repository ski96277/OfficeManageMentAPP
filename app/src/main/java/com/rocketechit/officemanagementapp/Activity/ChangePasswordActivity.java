package com.rocketechit.officemanagementapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.rocketechit.officemanagementapp.JavaClass.CheckNetwork;
import com.rocketechit.officemanagementapp.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class ChangePasswordActivity extends AppCompatActivity {

    @BindView(R.id.login_email_ET)
    EditText resetEmailET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.reset_btn)
    public void onClick() {
        String email = resetEmailET.getText().toString();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (TextUtils.isEmpty(email)) {
            resetEmailET.setError("Email...!");
            resetEmailET.requestFocus();
            return;
        }

        if (!validEmail(email)) {
            resetEmailET.setError("Enter Valid email address...!");
            resetEmailET.requestFocus();
            return;
        }
//check internet then send the mail for reset
        if (CheckNetwork.isInternetAvailable(this)) {
            auth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {

                Toasty.info(ChangePasswordActivity.this, "Check Your mail", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(ChangePasswordActivity.this, LoginActivity.class));
                finish();

            });
        } else {
            Toasty.error(this, "NetWork Error", Toast.LENGTH_SHORT).show();
        }
    }

    //E-mail validation
    private boolean validEmail(String email) {
        String email_pattern = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        Pattern pattern = Pattern.compile(email_pattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
