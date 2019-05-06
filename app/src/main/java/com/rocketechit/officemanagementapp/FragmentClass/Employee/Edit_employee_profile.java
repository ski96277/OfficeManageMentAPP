package com.rocketechit.officemanagementapp.FragmentClass.Employee;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rocketechit.officemanagementapp.Activity.MainActivity_Employee;
import com.rocketechit.officemanagementapp.JavaClass.CheckNetwork;
import com.rocketechit.officemanagementapp.JavaClass.Employee_Information;
import com.rocketechit.officemanagementapp.R;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

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
        getActivity().setTitle("Edit Profile");

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

    @OnClick({R.id.employee_profileview_image, R.id.employee_profileview_Save_button})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.employee_profileview_image:
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType("image/*");
                startActivityForResult(i, request_code);
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
                    Toasty.info(getContext(), "Check Internet", Toast.LENGTH_SHORT, true).show();
                }

                break;
        }
    }

    //    Showing Date picker popup
    private void CallDatePicker() {

        final Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            }

        };
        int day = myCalendar.get(Calendar.DAY_OF_MONTH);
        int month = myCalendar.get(Calendar.MONTH);
        int year = myCalendar.get(Calendar.YEAR);
        Toast.makeText(getContext(), "" + date, Toast.LENGTH_SHORT).show();
        employeeProfileviewJoinDateTV.setText(String.valueOf(day + "-" + month + "-" + year));
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
                        startActivity(new Intent(getContext(), MainActivity_Employee.class));
                        getActivity().finish();
                    }
                });
            }
        });
    }

    private void upload_Updated_Data(String designation, String email, String imagelink, String join_date, String name_employee, String password_employee, String phoneNumber, String userID, String userID_company) {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Uploading...");
        progressDialog.show();
        progressDialog.setCancelable(false);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        DatabaseReference databaseReference1 = firebaseDatabase.getReference();

        Employee_Information employee_information = new Employee_Information(userID, userID_company, email, password_employee,
                name_employee, join_date, imagelink, designation, phoneNumber);

        databaseReference.child("Employee_List").child(userID).setValue(employee_information);
        databaseReference1.child("Company_Employee").child(userID_company).child(userID).setValue(employee_information);

        progressDialog.dismiss();

        startActivity(new Intent(getContext(), MainActivity_Employee.class));
        getActivity().finish();

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
