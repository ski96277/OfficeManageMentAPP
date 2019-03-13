package com.rocketechit.officemanagementapp.FragmentClass.Employee;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.flags.IFlagProvider;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rocketechit.officemanagementapp.JavaClass.CheckNetwork;
import com.rocketechit.officemanagementapp.JavaClass.Employee_Information;
import com.rocketechit.officemanagementapp.R;
import com.squareup.picasso.Picasso;

import java.net.URL;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class Edit_employee_profile extends Fragment {

    @BindView(R.id.employee_profileview_position)
    TextView employeeProfileviewPosition;
    @BindView(R.id.employee_profileview_name_ET)
    EditText employeeProfileviewNameET;
    @BindView(R.id.employee_profileview_phoneNumber_ET)
    EditText employeeProfileviewPhoneNumberET;
    @BindView(R.id.employee_profileview_email_ET)
    EditText employeeProfileviewEmailET;
    @BindView(R.id.employee_profileview_join_Date_TV)
    TextView employeeProfileviewJoinDateTV;
    @BindView(R.id.employee_profileview_image)
    CircleImageView employeeProfileviewImage;
    private String designation;
    private String email;
    private String imagelink;
    private String join_date;
    private String name_employee;
    private String password_employee;
    private String phoneNumber;
    private String userID;
    private String userID_company;
    int request_code = 100;

    Uri uri;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_employee_profile, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        Employee_Information employee_information = (Employee_Information) bundle.getSerializable("employee_information");

        designation = employee_information.getDesignation();
        email = employee_information.getEmail_Employee();
        imagelink = employee_information.getImageLink();
        join_date = employee_information.getJoin_Date();
        name_employee = employee_information.getName_Employee();
        password_employee = employee_information.getPassword_Employee();
        phoneNumber = employee_information.getPhone();
        userID = employee_information.getUserID_Employee();
        userID_company = employee_information.getUserID_company();


        Picasso.get().load(imagelink).placeholder(R.drawable.progress_animation).error(R.drawable.man).into(employeeProfileviewImage);
        employeeProfileviewPosition.setText(designation);
        employeeProfileviewNameET.setText(name_employee);
        employeeProfileviewPhoneNumberET.setText(phoneNumber);
        employeeProfileviewEmailET.setText(email);
        employeeProfileviewJoinDateTV.setText(join_date);


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @OnClick({R.id.employee_profileview_image, R.id.employee_profileview_Save_button})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.employee_profileview_image:
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType("image/*");
                startActivityForResult(i, request_code);
                break;

            case R.id.employee_profileview_join_Date_TV:
                selectDate();
                break;

            case R.id.employee_profileview_Save_button:
                name_employee = employeeProfileviewNameET.getText().toString();
                phoneNumber = employeeProfileviewPhoneNumberET.getText().toString();
                email = employeeProfileviewEmailET.getText().toString();
                //internet Check
                if (CheckNetwork.isInternetAvailable(getContext())) {
                    if (uri != null) {

                        profileImageUpload(uri);
                    } else {
                        upload_Updated_Data(designation, email, imagelink, join_date, name_employee, password_employee, phoneNumber, userID, userID_company);

                    }
                } else {
                    Toast.makeText(getContext(), "check internet ", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    private void profileImageUpload(Uri uri) {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Uploading...");
        progressDialog.show();
        progressDialog.setCancelable(false);

        StorageReference mStorageRef;
        mStorageRef = FirebaseStorage.getInstance().getReference();
        StorageReference filepath = mStorageRef.child("Employee_Image").child(userID).child(uri.getLastPathSegment());
        filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String downloadLink = uri.toString();
                        imagelink = downloadLink;
                        upload_Updated_Data(designation, email, imagelink, join_date, name_employee, password_employee, phoneNumber, userID, userID_company);
                        progressDialog.dismiss();
                    }
                });
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void selectDate() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        employeeProfileviewJoinDateTV.setText(String.valueOf(day) + "-" + String.valueOf(month) + "-" + String.valueOf(year));

    }

    private void upload_Updated_Data(String designation, String email, String imagelink, String join_date, String name_employee, String password_employee, String phoneNumber, String userID, String userID_company) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        DatabaseReference databaseReference1 = firebaseDatabase.getReference();

        Employee_Information employee_information = new Employee_Information(userID, userID_company, email, password_employee,
                name_employee, join_date, imagelink, designation, phoneNumber);

        databaseReference.child("Employee_List").child(userID).setValue(employee_information);
        databaseReference1.child("Company_Employee").child(userID_company).child(userID).setValue(employee_information);

    }/*
    private void upload_Updated_Data(String designation, String email, String imagelink, String join_date, String name_employee, String password_employee, String phoneNumber, String userID, String userID_company) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        DatabaseReference databaseReference1 = firebaseDatabase.getReference();

        Employee_Information employee_information = new Employee_Information(userID, userID_company, email, password_employee,
                name_employee, join_date, imagelink, designation, phoneNumber);

        databaseReference.child("Employee_List").child(userID).setValue(employee_information);
        databaseReference1.child("Company_Employee").child(userID_company).child(userID).setValue(employee_information);

    }*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == request_code && data != null) {
            uri = data.getData();
            employeeProfileviewImage.setImageURI(uri);
        }
    }
}
