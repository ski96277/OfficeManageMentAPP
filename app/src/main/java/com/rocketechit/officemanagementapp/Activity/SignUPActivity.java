package com.rocketechit.officemanagementapp.Activity;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rocketechit.officemanagementapp.JavaClass.CheckNetwork;
import com.rocketechit.officemanagementapp.JavaClass.Company_Information;
import com.rocketechit.officemanagementapp.R;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class SignUPActivity extends AppCompatActivity {

    @BindView(R.id.company_name_ET)
    EditText companyNameET;
    @BindView(R.id.company_email)
    EditText companyEmail;
    @BindView(R.id.company_password)
    EditText companyPassword;
    @BindView(R.id.company_phone)
    EditText companyPhone;
    @BindView(R.id.company_office_time_start)
    EditText companyOfficeTimeStart;
    @BindView(R.id.company_office_time_end)
    EditText companyOfficeTimeEnd;
    @BindView(R.id.signUp_btn)
    Button signUpBtn;
    @BindView(R.id.go_login)
    TextView goLogin;

    ProgressDialog registrationProgress;

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
//hide action bar
        getSupportActionBar().hide();

        ButterKnife.bind(this);

        registrationProgress = new ProgressDialog(this);
        registrationProgress.setTitle("SignUp loading...");

        registrationProgress.setCancelable(false);

        initialize();
    }

    private void initialize() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    @OnClick({R.id.company_office_time_start, R.id.company_office_time_end, R.id.signUp_btn, R.id.go_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.company_office_time_start:
                String title = "Select Entry Time";
                String key = "Entry";
                selectTime(title, key);
                break;
            case R.id.company_office_time_end:
                title = "Select Exit Time";
                key = "Exit";
                selectTime(title, key);
                break;
            case R.id.signUp_btn:
                String company_name = companyNameET.getText().toString();
                String company_email = companyEmail.getText().toString();
                String company_password = companyPassword.getText().toString();
                String company_phone = companyPhone.getText().toString();
                String company_start_time = companyOfficeTimeStart.getText().toString();
                String company_end_time = companyOfficeTimeEnd.getText().toString();

                if (company_name.isEmpty()) {
                    companyNameET.requestFocus();
                    companyNameET.setError("Company Name ?");
                    return;
                }
                if (company_email.isEmpty()) {
                    companyEmail.requestFocus();
                    companyEmail.setError("Email ?");
                    return;
                }
                if (!validEmail(company_email)) {

                    companyEmail.setError("Enter valid E-mail");

                    companyEmail.requestFocus();

                    return;

                }
                if (company_password.isEmpty() && company_password.length() <= 5) {
                    companyPassword.requestFocus();
                    companyPassword.setError("Password should be 6 char");
                    return;
                }
                if (company_phone.isEmpty() && company_phone.length() <= 10) {
                    companyPassword.requestFocus();
                    companyPassword.setError("Phone Number should be 6 char ?");
                    return;
                }
                if (company_start_time.isEmpty()) {
                    companyOfficeTimeStart.requestFocus();
                    companyOfficeTimeStart.setError("office Time start ?");
                    return;
                }
                if (company_end_time.isEmpty()) {
                    companyOfficeTimeEnd.requestFocus();
                    companyOfficeTimeEnd.setError("office Time End ?");
                    return;
                }
                registrationProgress.show();

                if (CheckNetwork.isInternetAvailable(this)) {
                    firebaseAuth.createUserWithEmailAndPassword(company_email, company_password)
                            .addOnCompleteListener(this, task -> {
                                if (task.isSuccessful()) {
                                    String userID = firebaseAuth.getCurrentUser().getUid();

                                    Company_Information company_information = new Company_Information(company_name, company_email,
                                            company_password, company_phone, company_start_time, company_end_time, userID);

                                    registrationProgress.dismiss();
                                    databaseReference.child("Company").child(userID).setValue(company_information);
                                    Toasty.success(this, "Success", Toast.LENGTH_SHORT, true).show();
                                    startActivity(new Intent(SignUPActivity.this, MainActivity_Admin.class));
                                    finish();
                                    Toasty.info(SignUPActivity.this, "Click menu to Add new User", Toast.LENGTH_SHORT, true).show();

                                } else {
                                    registrationProgress.dismiss();
                                    Toasty.error(this, "Failed to SignUp", Toast.LENGTH_SHORT, true).show();
                                }
                            });

                } else {
                    registrationProgress.dismiss();
                    Toasty.error(this, "NetWork Error", Toast.LENGTH_SHORT, true).show();

                }

                break;
            case R.id.go_login:
                finish();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
        }
    }


    private void selectTime(String title, final String key) {
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view, hourOfDay, minute1) -> {
            String hour1 = String.valueOf(hourOfDay);
            String min = String.valueOf(minute1);
            if (minute1 < 10) {
                min = 0 + "" + min;
            }
            String am_pm;
            if (key.equals("Entry")) {
                if (hourOfDay > 11) {
                    am_pm = "pm";
                    companyOfficeTimeStart.setText(hour1 + ":" + min + " " + am_pm);
                } else {
                    am_pm = "am";
                    companyOfficeTimeStart.setText(hour1 + ":" + min + " " + am_pm);

                }
            }

            if (key.equals("Exit")) {

                if (hourOfDay > 11) {
                    am_pm = "pm";
                    companyOfficeTimeEnd.setText(hour1 + ":" + min + " " + am_pm);
                } else {
                    am_pm = "am";
                    companyOfficeTimeEnd.setText(hour1 + ":" + min + " " + am_pm);

                }
            }
        }, hour, minute, true);
        timePickerDialog.setTitle(title);

        timePickerDialog.show();

    }

    //E-mail validation
    private boolean validEmail(String email) {

        String email_pattern = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

        Pattern pattern = Pattern.compile(email_pattern);

        Matcher matcher = pattern.matcher(email);

        return matcher.matches();

    }
}
