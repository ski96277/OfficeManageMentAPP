package com.rocketechit.officemanagementapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.rocketechit.officemanagementapp.JavaClass.Entry_Exit_Time;
import com.rocketechit.officemanagementapp.R;

import java.util.Calendar;
import java.util.TimeZone;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ScanResultActivity extends AppCompatActivity {

    @BindView(R.id.textView3)
    TextView titleTV_ID;
    @BindView(R.id.EntryTime_id)
    TextView entryTimeTV_ID;


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    String title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_result);
        ButterKnife.bind(this);
        getScannerOption();

        Intent intent = getIntent();
        title = intent.getStringExtra("value");
        titleTV_ID.setText(title);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void getScannerOption() {

        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("Rocketech IT Ltd");
        integrator.setOrientationLocked(false);
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(false);
        integrator.initiateScan();


    }

    // Get the results:
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "You cancelled the scanning", Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, MainActivity_Employee.class));
                finish();
            } else {
                entryTimeTV_ID.setText(result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @OnClick({R.id.cancel_button, R.id.submit_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_button:
                startActivity(new Intent(this, MainActivity_Employee.class));
                finish();
                break;
            case R.id.submit_btn:

                if (title.equals("Your Entry Time")) {

                    Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
                    int year  = calendar.get(Calendar.YEAR);
                    int month  = calendar.get(Calendar.MONTH);
                    int day  = calendar.get(Calendar.DAY_OF_MONTH);
                    Toast.makeText(this, "Submitted", Toast.LENGTH_SHORT).show();

                    databaseReference.child("Attendance").child(getUserID())
                            .child(String.valueOf(year)).child(String.valueOf(month+1))
                            .child(String.valueOf(day)).child("Entry").child("entryTime").setValue(entryTimeTV_ID.getText().toString());

                } else if (title.equals("Your Exit Time")) {


                    Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
                    int year  = calendar.get(Calendar.YEAR);
                    int month  = calendar.get(Calendar.MONTH);
                    int day  = calendar.get(Calendar.DAY_OF_MONTH);
                    Toast.makeText(this, "Submitted", Toast.LENGTH_SHORT).show();

                    databaseReference.child("Attendance").child(getUserID())
                            .child(String.valueOf(year)).child(String.valueOf(month+1))
                            .child(String.valueOf(day)).child("Exit").child("exitTime").setValue(entryTimeTV_ID.getText().toString());



                }

                break;
        }
    }


    //get Current Admin User ID
    private String getUserID() {

        FirebaseAuth firebaseAuth;
        FirebaseUser firebaseUser;
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        String userID = firebaseUser.getUid();
        return userID;
    }

}
