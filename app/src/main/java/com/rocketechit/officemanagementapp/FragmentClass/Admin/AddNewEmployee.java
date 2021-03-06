package com.rocketechit.officemanagementapp.FragmentClass.Admin;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kinda.alert.KAlertDialog;
import com.rocketechit.officemanagementapp.JavaClass.CheckNetwork;
import com.rocketechit.officemanagementapp.JavaClass.Employee_Information;
import com.rocketechit.officemanagementapp.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class AddNewEmployee extends Fragment {
    @BindView(R.id.add_employee_Email)
    EditText addEmployeeEmail;
    @BindView(R.id.add_employee_designation_user_ET)
    EditText addEmployeeDesignationUserET;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference1, databaseReference2;
    FirebaseAuth firebaseAuth;
    String userID_Admin;
    String email_Admin;
    String password_Admin;
    String password = "123456";
    @BindView(R.id.progressbar_employee_add)
    ProgressBar progressbarEmployeeAdd;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_new_employee_f, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Add New Employee");


        initialize();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        userID_Admin = firebaseUser.getUid();
        email_Admin = firebaseUser.getEmail();
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                password_Admin = dataSnapshot.child("Company").child(userID_Admin).child("password").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void initialize() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference1 = firebaseDatabase.getReference();
        databaseReference2 = firebaseDatabase.getReference();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @OnClick(R.id.add_employee_button_ID)
    public void onViewClicked() {

        String email = addEmployeeEmail.getText().toString();
        String designation = addEmployeeDesignationUserET.getText().toString();

        if (email.isEmpty()) {
            addEmployeeEmail.requestFocus();
            addEmployeeEmail.setError("Email ?");
            return;
        }
        if (designation.isEmpty()) {
            addEmployeeDesignationUserET.requestFocus();
            addEmployeeDesignationUserET.setError("Designation ?");
            return;
        }

        if (CheckNetwork.isInternetAvailable(getContext())) {
            //progressbar start
            progressbarEmployeeAdd.setVisibility(View.VISIBLE);
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(getActivity(), task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            String userID_Employee = firebaseUser.getUid();
                            Employee_Information employee_information = new
                                    Employee_Information(userID_Employee, userID_Admin, email, password, "Null", "Null",
                                    "Null", designation, "null");
                            //admin Login again
                            FirebaseAuth.getInstance().signOut();
                            successAlert();
                            firebaseAuth.signInWithEmailAndPassword(email_Admin, password_Admin)
                                    .addOnCompleteListener(getActivity(), task1 -> {
                                        if (task.isSuccessful()) {

                                            databaseReference1.child("Company_Employee")
                                                    .child(userID_Admin).child(userID_Employee).setValue(employee_information);
                                            databaseReference2.child("Employee_List").child(userID_Employee).setValue(employee_information);

                                         /*   Fragment fragment = new AddNewEmployee();
                                            if (fragment != null) {
                                                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                                                fragmentTransaction.replace(R.id.screen_Area_For_Admin, fragment);
                                                fragmentTransaction.commit();
                                            }*/
                                            //progressbar start
                                            progressbarEmployeeAdd.setVisibility(View.GONE);
                                            Log.e("TAG", "onViewClicked: " + "Again Login");
                                        } else {
                                            Toast.makeText(getContext(), "sorry", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else {
                            progressbarEmployeeAdd.setVisibility(View.GONE);
                            Toasty.error(getContext(), "Failed.", Toast.LENGTH_SHORT, true).show();
                        }
                    });
        }
    }
//success alert show method
    private void successAlert() {
        KAlertDialog dialog = new KAlertDialog(getContext(), KAlertDialog.SUCCESS_TYPE);
        dialog.setTitleText("Good job!");
        dialog.setContentText("successfully added.");
        dialog.setConfirmClickListener(new KAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(KAlertDialog kAlertDialog) {
                addEmployeeEmail.setText("");
                addEmployeeDesignationUserET.setText("");
                dialog.dismissWithAnimation();
            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }

}
