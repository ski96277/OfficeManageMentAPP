package com.rocketechit.officemanagementapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.rocketechit.officemanagementapp.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class ScanResultActivity extends AppCompatActivity {

    @BindView(R.id.textView3)
    TextView titleTV_ID;
    @BindView(R.id.EntryTime_id)
    TextView entry_TimeTV_ID;


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    String title = null;
    String scanresult = "";
    /*String am_pm_St = null;
    String time = null;*/


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
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        scanresult = result.getContents();
                        String value = null;
                        value = dataSnapshot.child("QRCode").child("number").getValue().toString();
                        if (scanresult.equals(value)) {
                            scanresult=value;
                        }else {
                            Toast.makeText(ScanResultActivity.this, "QR code is invalid", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(ScanResultActivity.this, MainActivity_Employee.class));
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
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
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH);
                    int day = calendar.get(Calendar.DAY_OF_MONTH);


                    DateFormat df = new SimpleDateFormat("h:mm a");
                    String time = df.format(Calendar.getInstance().getTime());
                    databaseReference.child("Attendance").child(getUserID())
                            .child(String.valueOf(year)).child(String.valueOf(month + 1))
                            .child(String.valueOf(day)).child("Entry").child("entryTime").setValue(time);
                    Toasty.success(this, "submitted", Toast.LENGTH_SHORT).show();
                   scanresult="";
                    startActivity(new Intent(this, MainActivity_Employee.class));
                    finish();

                } else if (title.equals("Your Exit Time")) {
                    Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH);
                    int day = calendar.get(Calendar.DAY_OF_MONTH);


                    DateFormat dfa = new SimpleDateFormat("h:mm a");
                    String time = dfa.format(Calendar.getInstance().getTime());
                    Toasty.success(this, "submitted", Toast.LENGTH_SHORT).show();

                    databaseReference.child("Attendance").child(getUserID())
                            .child(String.valueOf(year)).child(String.valueOf(month + 1))
                            .child(String.valueOf(day)).child("Exit").child("exitTime").setValue(time);
                    startActivity(new Intent(this, MainActivity_Employee.class));
                    finish();
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
